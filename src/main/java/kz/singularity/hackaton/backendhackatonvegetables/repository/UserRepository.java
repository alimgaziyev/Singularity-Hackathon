package kz.singularity.hackaton.backendhackatonvegetables.repository;

import kz.singularity.hackaton.backendhackatonvegetables.models.Role;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  List<User> findUsersByRoles(Role roles);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
