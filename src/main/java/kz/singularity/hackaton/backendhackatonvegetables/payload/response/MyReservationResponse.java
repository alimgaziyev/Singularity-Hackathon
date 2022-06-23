package kz.singularity.hackaton.backendhackatonvegetables.payload.response;

import kz.singularity.hackaton.backendhackatonvegetables.models.Room;
import kz.singularity.hackaton.backendhackatonvegetables.models.Time;
import kz.singularity.hackaton.backendhackatonvegetables.models.Week;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyReservationResponse {
    private String day;
    private String time;
    private String room;
    private String firstName;
    private String lastName;
}
