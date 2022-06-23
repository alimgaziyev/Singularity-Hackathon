package kz.singularity.hackaton.backendhackatonvegetables.models;

public enum ETime {
    T_9_00("9:00"),
    T_9_30("9:30"),
    T_10_00("10:00"),
    T_10_30("10:30"),
    T_11_00("11:00"),
    T_11_30("11:30"),
    T_12_30("12:30"),
    T_13_00("13:00"),
    T_13_30("13:30"),
    T_12_00("12:00"),
    T_14_00("14:00"),
    T_14_30("14:30"),
    T_15_00("15:00"),
    T_15_30("15:30"),
    T_16_00("16:00"),
    T_16_30("16:30"),
    T_17_00("17:00"),
    T_17_30("17:30");

    public final String time;

    public String getTime() {
        return time;
    }

    ETime(String  time) {
        this.time = time;
    }
}