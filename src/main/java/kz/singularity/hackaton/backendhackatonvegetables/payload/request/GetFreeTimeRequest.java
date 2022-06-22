package kz.singularity.hackaton.backendhackatonvegetables.payload.request;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Setter
@Getter
public class GetFreeTimeRequest {
    ERoom roomId;
    @Valid
    EWeek weekDay;
}
