package uz.pdp.pdpspring6thlessonhomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpspring6thlessonhomework.entity.User;
import uz.pdp.pdpspring6thlessonhomework.entity.enums.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail( String email);
    Optional<Object> findByEmail(String username);
    boolean existsByRole(Role role );

}
