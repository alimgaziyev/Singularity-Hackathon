package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeTimeRepository extends JpaRepository<Time, Long> {

}
