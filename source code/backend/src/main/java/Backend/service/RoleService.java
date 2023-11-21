package Backend.service;

import Backend.entity.Role;
import Backend.utilities.ERole;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
