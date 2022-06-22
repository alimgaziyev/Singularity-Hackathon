package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.*;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.repository.BookingRepository;
import kz.singularity.hackaton.backendhackatonvegetables.repository.RoomRepository;
import kz.singularity.hackaton.backendhackatonvegetables.repository.WeekDayRepository;
import kz.singularity.hackaton.backendhackatonvegetables.util.ConstantMessages;
import kz.singularity.hackaton.backendhackatonvegetables.util.DayTimes;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final WeekDayRepository weekDayRepository;

    private final String timestamp;

    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = dtf.format(localDateTime);
    }

    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, WeekDayRepository weekDayRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.weekDayRepository = weekDayRepository;
    }

    public ResponseOutputBody getFreeTimeOnDayAndRoom(GetFreeTimeRequest getFreeTimeRequest) {
        System.out.println(getFreeTimeRequest.getRoom());
        System.out.println(getFreeTimeRequest.getWeekday());
        Room room = roomRepository.findByRoom(ERoom.valueOf(getFreeTimeRequest.getRoom()));
        System.out.println(1);
        System.out.println(room);
        if (room == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }
        Week week = weekDayRepository.findByWeekDay(EWeek.valueOf(getFreeTimeRequest.getWeekday()));
        System.out.println(1);
        System.out.println(week);
        if (week == null) {
            return new ResponseOutputBody(
                    ConstantMessages.BAD_CREDENTIALS,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }
        List<ReservedRoom> reservedRooms = bookingRepository.findAllByRoomAndDay(room, week);
        List<String> freeTimes = DayTimes.timeList;
        System.out.println(freeTimes);
        System.out.println(reservedRooms);
        reservedRooms.forEach(x -> freeTimes.remove(x.getTime().toString()));
        return new ResponseOutputBody(
                ConstantMessages.SUCCESS,
                timestamp,
                Response.Status.OK,
                freeTimes
        );
    }
}
