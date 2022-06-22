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

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EWeek weekDay;

    @Override
    public String toString() {
        return "Week{" +
                "id=" + id +
                ", weekDay=" + weekDay +
                '}';
    }
}
