package kz.singularity.hackaton.backendhackatonvegetables.util;

import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class DayTimes {
    private List<String> timeList = new ArrayList<>();
    public DayTimes() {
        Arrays.stream(ETime.values()).forEach(x -> timeList.add(x.getTime()));
    }

}
