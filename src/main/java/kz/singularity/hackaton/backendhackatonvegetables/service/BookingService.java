package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.ReservedRoom;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    ResponseOutputBody getFreeTimeOnDayAndRoom(GetFreeTimeRequest getFreeTimeRequest);

    ResponseOutputBody createBooking(BookingRequest bookingRequest, String token);

    ResponseOutputBody getAllDayActivity(String room, String day);

    List<ReservedRoom> getMyReservation(String token);
}
