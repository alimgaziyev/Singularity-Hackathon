package kz.singularity.hackaton.backendhackatonvegetables.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "to_permit")
@NoArgsConstructor
@Getter @Setter
@Builder
@AllArgsConstructor
public class QueryToPermit {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String room;
    @Column(name = "week_day")
    private String weekDay;
    private String time;
    @Column(name = "meeting_name")
    private String meetingName;

}
