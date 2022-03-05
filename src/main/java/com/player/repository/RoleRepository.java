package com.player.repository;

import java.util.Optional;

import com.player.model.Role;
import com.player.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName role);
}
