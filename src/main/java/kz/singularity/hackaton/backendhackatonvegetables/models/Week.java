package kz.singularity.hackaton.backendhackatonvegetables.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "week_days")
@NoArgsConstructor
@Getter
@Setter
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EWeek weekDay;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "day_books",
            joinColumns = @JoinColumn(name = "week_id"),
            inverseJoinColumns = @JoinColumn(name = "time_id"))
    private Set<Time> bookedTime = new HashSet<>();
}
