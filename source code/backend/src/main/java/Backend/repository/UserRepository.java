package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.PostOffice;
import Backend.entity.User;
import Backend.utilities.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    Boolean existsByEmailOrUsername(String email, String username);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByRoles_Name(ERole role, Pageable pageable);

    Page<User> findAllByDepot(Depot depot, Pageable pageable);

    Page<User> findAllByPostOffice(PostOffice postOffice, Pageable pageable);

    Page<User> findAllByDeleted(boolean deleted, Pageable pageable);

    Page<User> findAllByDeletedAndUsernameContains(boolean deleted, String username, Pageable pageable);
    Page<User> findAllByUsernameContainsOrEmailContains(String username, String email, Pageable pageable);

    //    public Page<User> findUsersByDeletedAndUsernameIsContainingOrEmailIsContaining(boolean deleted, String username, String email, Pageable pageable);
    List<User> findAllByDeleted(boolean statusDeleted);

//    List<User> findAllByIntakeId(Long id);
    List<User> findByDeletedIsFalseOrderByCreatedDateDesc();


}
