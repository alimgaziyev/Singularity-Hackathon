package kz.singularity.hackaton.backendhackatonvegetables.util;

import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class AllDayActivity {
    private String meetingDescription;
    private boolean reserved;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return "AllDayActivity{" +
                "meetingDescription='" + meetingDescription + '\'' +
                ", reserved=" + reserved +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
