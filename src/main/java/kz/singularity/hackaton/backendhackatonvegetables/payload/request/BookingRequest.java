package kz.singularity.hackaton.backendhackatonvegetables.payload.request;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;
import kz.singularity.hackaton.backendhackatonvegetables.models.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BookingRequest {
    @NotBlank
    private String room;
    @NotBlank
    private String weekDay;
    @NotBlank
    private List<String> timeList = new ArrayList<>();
    @NotBlank
    private String meetingName;
}
