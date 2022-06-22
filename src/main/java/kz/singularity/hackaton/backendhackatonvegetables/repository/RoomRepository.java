package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long> {
    Room findByRoom(ERoom room);
}
