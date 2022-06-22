package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.EWeek;
import kz.singularity.hackaton.backendhackatonvegetables.models.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekDayRepository extends JpaRepository<Week, Long> {
    Week findByWeekDay(EWeek weekday);
}
