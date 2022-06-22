package kz.singularity.hackaton.backendhackatonvegetables.payload.request;


import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetFreeTimeRequest {
    String room;
    String weekday;

    public GetFreeTimeRequest(String room, String weekday) {
        this.room = room;
        this.weekday = weekday;
    }
}
