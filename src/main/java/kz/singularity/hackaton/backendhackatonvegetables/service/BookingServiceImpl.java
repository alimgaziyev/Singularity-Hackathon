package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.*;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final WeekDayRepository weekDayRepository;
    private final TimeRepository timeRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final String timestamp;

    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = dtf.format(localDateTime);
    }

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository,
                              WeekDayRepository weekDayRepository, TimeRepository timeRepository,
                              JwtUtils jwtUtils, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.weekDayRepository = weekDayRepository;
        this.timeRepository = timeRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
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

        if (Objects.equals(bookingRequest.getRoom(), ERoom.R303.name()) ||
                Objects.equals(bookingRequest.getRoom(), ERoom.R403.name())) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }

        Room room = roomRepository.findByRoom(ERoom.valueOf(bookingRequest.getRoom().toUpperCase()));
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


        for (String s : bookingRequest.getTimeList()) {
            System.out.println(s);
            String[] time_H_M = s.split(":");

            Time time = timeRepository.findByTime(ETime.valueOf("T_" + time_H_M[0] + "_" + time_H_M[1]));

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

        AllDayActivity listActivity = new AllDayActivity();
        List<AllDayActivity> listOfActivity = listActivity.getAllDayActivityList();

        for (ReservedRoom reservedRoom : reservedRooms) {
            AllDayActivity activity = new AllDayActivity();
            activity.setTime(reservedRoom.getTime().getTime().time);
            activity.setMeetingDescription(reservedRoom.getActivityDescription());
            activity.setReserved(true);
            listOfActivity.add(activity);
        }


        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                listOfActivity
        );
    }
}
