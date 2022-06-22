package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;

import java.util.List;

public interface ListingFreeTimesService {
    List<ETime> getFreeTimesByRoomAndDay(ERoom roomId, EWeek weekDay);
}
