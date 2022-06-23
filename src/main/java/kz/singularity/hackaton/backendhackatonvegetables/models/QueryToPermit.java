package kz.singularity.hackaton.backendhackatonvegetables.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "to_permit")
@NoArgsConstructor
@Getter @Setter
public class QueryToPermit {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String room;
    @Column(name = "week_day")
    private String weekDay;
    private String why;

}
