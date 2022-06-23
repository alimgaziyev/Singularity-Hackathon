package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.QueryToPermit;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.PermittedRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import org.springframework.stereotype.Service;

@Service
public interface PermitService {
    void sendToPermitForRoomOnWeekDay(BookingRequest bookingRequest, User user);
    QueryToPermit getUserToPermit();
    ResponseOutputBody doPermission(PermittedRequest permission);
}
