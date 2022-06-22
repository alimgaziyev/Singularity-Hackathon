package kz.singularity.hackaton.backendhackatonvegetables.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "reserved_rooms")
public class ReservedRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Week day;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Override
    public String toString() {
        return "ReservedRoom{" +
                "id=" + id +
                ", user=" + user +
                ", day=" + day +
                ", time=" + time +
                ", room=" + room +
                '}';
    }
}
