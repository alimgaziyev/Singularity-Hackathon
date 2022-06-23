package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.email.EmailService;
import kz.singularity.hackaton.backendhackatonvegetables.models.*;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.PermittedRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.repository.*;
import kz.singularity.hackaton.backendhackatonvegetables.util.ConstantMessages;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PermitServiceImpl implements PermitService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermitRepository permitRepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final WeekDayRepository weekDayRepository;
    private final TimeRepository timeRepository;
    private final EmailService emailService;


    private String timestamp;
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = dtf.format(localDateTime);
    }

    @Override
    public QueryToPermit getUserToPermit() {
        QueryToPermit queryToPermit = permitRepository.getFirstById();
        return queryToPermit;
    }

    @Override
    public ResponseOutputBody doPermission(PermittedRequest permission) {
        QueryToPermit queryToPermit = permitRepository.getFirstById();
        permitRepository.delete(queryToPermit);
        if (permission.getDecision().equals("permitted")) {
            return createBookingByPermission(queryToPermit);
        } else {
            return notBooked(queryToPermit);
        }
    }

    @Override
    public void sendToPermitForRoomOnWeekDay(BookingRequest bookingRequest, User user) {
        List<User> admins = userRepository.findUsersByRoles(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());

        admins.forEach(x -> {
            emailService.sendSimpleMessage(
                    x.getEmail(),
                    "to permit",
                    String.format("student name: %s; email: %s, requests to permit #%s room, on %s at %s. Reason - %s",
                            user.getUsername(),
                            user.getEmail(),
                            bookingRequest.getRoom(),
                            bookingRequest.getWeekDay(),
                            bookingRequest.getTimeList(),
                            bookingRequest.getMeetingName()));
        });
        bookingRequest.getTimeList().forEach(time->
            permitRepository.save(QueryToPermit.builder()
                    .username(user.getUsername())
                    .room(bookingRequest.getRoom())
                    .weekDay(bookingRequest.getWeekDay()).time(time)
                    .meetingName(bookingRequest.getMeetingName())
                    .build()));
    }

    @Transactional
    public ResponseOutputBody createBookingByPermission(QueryToPermit queryToPermit) {
        Room room = roomRepository.findByRoom(ERoom.valueOf(queryToPermit.getRoom().toUpperCase()));
        Week day = weekDayRepository.findByWeekDay(EWeek.valueOf(queryToPermit.getWeekDay().toUpperCase()));

        String[] time_H_M = queryToPermit.getTime().split(":");
        Time time = timeRepository.findByTime(ETime.valueOf("T_" + time_H_M[0] + "_" + time_H_M[1]));
        User user = userRepository.findByUsername(queryToPermit.getUsername()).get();


        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setRoom(room);
        reservedRoom.setDay(day);
        reservedRoom.setTime(time);
        reservedRoom.setUser(user);
        reservedRoom.setActivityDescription(queryToPermit.getMeetingName());
        bookingRepository.save(reservedRoom);

        emailService.sendSimpleMessage(user.getEmail(),
                "Admin permitted",
                String.format("Request to permit the %s room, on %s, on time - %s",
                        queryToPermit.getRoom(),
                        queryToPermit.getWeekDay(),
                        queryToPermit.getTime())
        );

        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                null
        );
    }

    public ResponseOutputBody notBooked(QueryToPermit queryToPermit) {
        User user = userRepository.findByUsername(queryToPermit.getUsername()).get();
        emailService.sendSimpleMessage(user.getEmail(),
                "Reject",
                String.format("Request to permit the %s room, on %s, on time - %s",
                        queryToPermit.getRoom(),
                        queryToPermit.getWeekDay(),
                        queryToPermit.getTime())
        );
        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                null
        );
    }
}
