package kz.singularity.hackaton.backendhackatonvegetables.payload.request;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
@Setter
public class BookingRequest {
    @NotBlank
    private ERoom room;
    @NotBlank
    private EWeek weekDay;
    @NotBlank
    private List<ETime> timeList = new ArrayList<>();
    @NotBlank
    private String whyReserved;


}
