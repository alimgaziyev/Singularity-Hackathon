package kz.singularity.hackaton.backendhackatonvegetables.models;

public enum ETime {
    NINE_AM("9:00"),
    NINE_HALF_AM("9:30"),
    TEN_AM("10:00"),
    TEN_HALF_AM("10:30"),
    ELEVEN_AM("11:00"),
    ELEVEN_HALF_AM("11:30"),
    TWELVE_AM("12:00"),
    TWELVE_HALF_AM("12:30"),
    ONE_PM("13:00"),
    ONE_HALF_PM("13:30"),
    TWO_PM("14:00"),
    TWO_HALF_PM("14:30"),
    THREE_PM("15:00"),
    THREE_HALF_PM("15:30"),
    FOUR_PM("16:00"),
    FOUR_HALF_PM("16:30"),
    FIVE_PM("17:00"),
    FIVE_HALF_PM("17:30");

    public final String time;

    public String getTime() {
        return time;
    }

    ETime(String  time) {
        this.time = time;
    }
}