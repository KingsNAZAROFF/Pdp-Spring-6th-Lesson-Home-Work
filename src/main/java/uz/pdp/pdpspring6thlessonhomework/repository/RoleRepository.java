package uz.pdp.pdpspring6thlessonhomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.Role;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
