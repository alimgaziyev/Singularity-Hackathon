package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERole;
import kz.singularity.hackaton.backendhackatonvegetables.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
  Set<Role> findAllByName(ERole eRole);
}
