package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.email.EmailService;
import kz.singularity.hackaton.backendhackatonvegetables.models.*;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.MyReservationResponse;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.repository.*;
import kz.singularity.hackaton.backendhackatonvegetables.security.jwt.JwtUtils;
import kz.singularity.hackaton.backendhackatonvegetables.util.AllDayActivity;
import kz.singularity.hackaton.backendhackatonvegetables.util.ConstantMessages;
import kz.singularity.hackaton.backendhackatonvegetables.util.DayTimes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final WeekDayRepository weekDayRepository;
    private final TimeRepository timeRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PermitService permitService;
    private final EmailService emailService;

    private final String timestamp;

    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = dtf.format(localDateTime);
    }

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository,
                              WeekDayRepository weekDayRepository, TimeRepository timeRepository,
                              JwtUtils jwtUtils, UserRepository userRepository,
                              PermitService permitService, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.weekDayRepository = weekDayRepository;
        this.timeRepository = timeRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.permitService = permitService;
        this.emailService = emailService;
    }

    public ResponseOutputBody getFreeTimeOnDayAndRoom(GetFreeTimeRequest getFreeTimeRequest) {
        Room room = roomRepository.findByRoom(ERoom.valueOf(getFreeTimeRequest.getRoom().toUpperCase()));
        if (room == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }
        Week week = weekDayRepository.findByWeekDay(EWeek.valueOf(getFreeTimeRequest.getWeekday().toUpperCase()));
        if (week == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }
        List<ReservedRoom> reservedRooms = bookingRepository.findAllByRoomAndDay(room, week);

        DayTimes dayTimes = new DayTimes();
        List<String> freeTimes = dayTimes.getTimeList();

        reservedRooms.forEach(x -> freeTimes.remove(x.getTime().getTime().time));

        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                freeTimes
        );
    }





    @Transactional
    @Override
    public ResponseOutputBody createBooking(BookingRequest bookingRequest, String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByUsername(username).get();
        Room room = roomRepository.findByRoom(ERoom.valueOf(bookingRequest.getRoom().toUpperCase()));

        if (room.getIsPermissionNeed()) {
            permitService.sendToPermitForRoomOnWeekDay(bookingRequest, user);
            return new ResponseOutputBody(
                    ConstantMessages.ROOM_NEEDS_PERMISSION + ConstantMessages.GET_PERMISSION,
                    timestamp,
                    Response.Status.OK,
                    null);
        }

        if (Objects.equals(bookingRequest.getRoom(), ERoom.R403.name())) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }

        if (room == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }
        Week day = weekDayRepository.findByWeekDay(EWeek.valueOf(bookingRequest.getWeekDay().toUpperCase()));
        if (day == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }

        List<Time> reservedTimes = new ArrayList<>();
        bookingRepository.findAllByRoomAndDay(room, day)
                .forEach(x -> reservedTimes.add(x.getTime()));

        for (String s : bookingRequest.getTimeList()) {

            String[] time_H_M = s.split(":");
            Time time = timeRepository.findByTime(
                    ETime.valueOf("T_" + time_H_M[0] + "_" + time_H_M[1]));

            if (reservedTimes.contains(time)) continue;

            ReservedRoom reservedRoom = new ReservedRoom();
            reservedRoom.setRoom(room);
            reservedRoom.setDay(day);
            reservedRoom.setTime(time);
            reservedRoom.setUser(user);
            reservedRoom.setActivityDescription(bookingRequest.getMeetingName());
            bookingRepository.save(reservedRoom);
        }
        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                null
        );
    }

    @Override
    public ResponseOutputBody getAllDayActivity(String room, String day) {
        Room roomActivity = roomRepository.findByRoom(ERoom.valueOf(room.toUpperCase()));
        Week dayActivity = weekDayRepository.findByWeekDay(EWeek.valueOf(day.toUpperCase()));
        List<ReservedRoom> reservedRooms = bookingRepository.findAllByRoomAndDay(roomActivity, dayActivity);

        Map<String, AllDayActivity> listOfActivity = new LinkedHashMap<>();

        DayTimes dayTimes = new DayTimes();
        List<String> freeTimes = dayTimes.getTimeList();

        for (String time : freeTimes) {
            AllDayActivity activity = new AllDayActivity();

            activity.setMeetingDescription(null);
            activity.setFirstName(null);
            activity.setLastName(null);
            listOfActivity.put(time, activity);
        }


        for (ReservedRoom reservedRoom : reservedRooms) {
            String [] fullName = reservedRoom.getUser().getUsername().split("\\.");
            AllDayActivity activity = new AllDayActivity();
            activity.setMeetingDescription(reservedRoom.getActivityDescription());
            activity.setReserved(true);
            activity.setFirstName(fullName[0]);
            activity.setLastName(fullName[1]);
            listOfActivity.replace(reservedRoom.getTime().getTime().time, activity);
        }


        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                listOfActivity
        );
    }

    @Override
    public ResponseOutputBody getMyReservation(String token) {
        String username = jwtUtils.getUserNameFromJwtToken(token);

        User user = userRepository.findByUsername(username).get();
        List<ReservedRoom> reservedRooms = bookingRepository.findAllByUser(user);
        List<MyReservationResponse> myReservations = new ArrayList<>();
        String[] fullName = user.getUsername().split("\\.");
        for (ReservedRoom reservedRoom : reservedRooms) {
            String[] time_H_M = reservedRoom.getTime().getTime().name().split("_");


            MyReservationResponse myReservation = new MyReservationResponse();
            myReservation.setDay(reservedRoom.getDay().getWeekDay().name());
            myReservation.setTime(time_H_M[1] + ":" + time_H_M[2]);
            myReservation.setRoom(reservedRoom.getRoom().getRoom().name());
            myReservation.setLastName(fullName[1]);
            myReservation.setFirstName(fullName[0]);
            myReservations.add(myReservation);
        }

        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                myReservations
        );
    }
}
