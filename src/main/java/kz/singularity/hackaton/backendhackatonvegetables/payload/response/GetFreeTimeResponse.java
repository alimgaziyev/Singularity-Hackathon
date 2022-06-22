package kz.singularity.hackaton.backendhackatonvegetables.payload.response;


import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GetFreeTimeResponse {
    List<ETime> freeTime;
}
