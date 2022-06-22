package kz.singularity.hackaton.backendhackatonvegetables.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "booked_time")
@NoArgsConstructor
@Setter
@Getter
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(targetClass = ETime.class)
    @CollectionTable(
            name = "booked_time",
            joinColumns = @JoinColumn(name = "time_id")
    )
    @Column(name = "etime_id")
    Set<ETime> bookedTimes = new HashSet<>();
}
