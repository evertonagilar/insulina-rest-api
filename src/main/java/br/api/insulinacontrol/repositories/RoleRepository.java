package br.api.insulinacontrol.repositories;

import br.api.insulinacontrol.enums.RoleName;
import br.api.insulinacontrol.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    Optional<RoleModel> findByRoleName(RoleName roleName);
}
