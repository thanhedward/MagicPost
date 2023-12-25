package Backend.service;

import Backend.dto.UserExport;
import Backend.entity.Depot;
import Backend.entity.PostOffice;
import Backend.entity.User;
import Backend.utilities.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;


public interface UserService {
    Optional<User> getUserByUsername(String username);

    String getUserName();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Page<User> findUsersByPage(Pageable pageable);

    Page<User> getUsersByRoleByPage(ERole role, Pageable pageable);

    Page<User> getUsersByDepot(Depot depot, Pageable pageable);

    Page<User> getUsersByPostOffice(PostOffice postOffice, Pageable pageable);

    Page<User> findUsersDeletedByPage(Pageable pageable, boolean deleted);

    Page<User> findAllByDeletedAndUsernameContains(boolean deleted, String username, Pageable pageable);


    User createUser(User user);

    Optional<User> findUserById(Long id);

    List<UserExport> findAllByDeletedToExport(boolean statusDelete);

    void updateUser(User user);

//    List<User> findAllByIntakeId(Long id);

    boolean requestPasswordReset(String email) throws MessagingException;

    boolean resetPassword(String token, String password);

    Page<User> findAllByUsernameContainsOrEmailContains(String username, String email, Pageable pageable);


}
