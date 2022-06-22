package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {
    ResponseOutputBody getFreeTimeOnDayAndRoom(GetFreeTimeRequest getFreeTimeRequest);
}
