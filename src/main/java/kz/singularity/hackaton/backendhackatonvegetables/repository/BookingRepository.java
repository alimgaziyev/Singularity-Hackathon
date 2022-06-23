package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.ReservedRoom;
import kz.singularity.hackaton.backendhackatonvegetables.models.Room;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import kz.singularity.hackaton.backendhackatonvegetables.models.Week;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<ReservedRoom, Long> {
    List<ReservedRoom> findAllByRoomAndDay(Room room, Week day);

    List<ReservedRoom> findAllByUser(User user);
}
