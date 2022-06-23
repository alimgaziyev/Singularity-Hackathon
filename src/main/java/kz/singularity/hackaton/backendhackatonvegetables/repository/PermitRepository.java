package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.QueryToPermit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermitRepository extends JpaRepository<QueryToPermit, Long> {
}
