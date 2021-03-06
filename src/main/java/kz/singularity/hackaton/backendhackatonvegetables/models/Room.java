package kz.singularity.hackaton.backendhackatonvegetables.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ERoom room;
    private String description;
    private Boolean isPermissionNeed;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(  name = "room_dates",
//            joinColumns = @JoinColumn(name = "room_id"),
//            inverseJoinColumns = @JoinColumn(name = "week_id"))
//    private Set<Week> weekDays = new HashSet<>();

    public Room(ERoom room) {
        this.room = room;
        this.description = room.getDescription();
        this.isPermissionNeed = room.isPermission();
    }

    public String getRoom(ERoom room) {
        return room.name();
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", room=" + room +
                ", description='" + description + '\'' +
                ", isPermissionNeed=" + isPermissionNeed +
                '}';
    }
}
