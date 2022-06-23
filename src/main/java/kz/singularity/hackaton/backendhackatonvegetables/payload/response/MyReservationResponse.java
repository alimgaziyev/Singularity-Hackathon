package kz.singularity.hackaton.backendhackatonvegetables.payload.response;

import kz.singularity.hackaton.backendhackatonvegetables.models.Room;
import kz.singularity.hackaton.backendhackatonvegetables.models.Time;
import kz.singularity.hackaton.backendhackatonvegetables.models.Week;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyReservationResponse {
    private Week day;
    private Time time;
    private Room room;
}
