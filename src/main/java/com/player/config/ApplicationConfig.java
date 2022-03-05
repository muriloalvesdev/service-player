package com.player.config;

import com.player.model.Role;
import com.player.repository.RoleRepository;
import com.player.model.RoleName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Configuration
public class ApplicationConfig {

    private final RoleRepository roleRepository;

    @Bean
    public void createRoleWhenInitializing() {
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.findByName(roleName).isPresent()) {
                this.roleRepository.saveAndFlush(
                        Role.builder().name(roleName).build());
            }
        }
    }
}