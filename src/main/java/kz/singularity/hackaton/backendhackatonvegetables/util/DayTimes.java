package kz.singularity.hackaton.backendhackatonvegetables.util;

import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayTimes {
    public static final List<String> timeList = new ArrayList<>();
    static {
        Arrays.stream(ETime.values()).forEach(x -> timeList.add(x.getTime()));
    }

}
