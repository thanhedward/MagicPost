package Backend.service;

import Backend.config.JwtUtils;
import Backend.dto.UserExport;
import Backend.entity.*;
import Backend.repository.PasswordResetTokenRepository;
import Backend.repository.UserRepository;
import Backend.utilities.ERole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }


    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String getUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<User> findUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getUsersByRoleByPage(ERole role, Pageable pageable) {
        return userRepository.findAllByRoles_Name(role, pageable);
    }

    @Override
    public Page<User> getUsersByDepot(Depot depot, Pageable pageable) {
        return userRepository.findAllByDepot(depot, pageable);
    }

    @Override
    public Page<User> getUsersByPostOffice(PostOffice postOffice, Pageable pageable) {
        return userRepository.findAllByPostOffice(postOffice, pageable);
    }

    @Override
    public Page<User> findUsersDeletedByPage(Pageable pageable, boolean deleted) {
        return userRepository.findAllByDeleted(deleted, pageable);
    }

    @Override
    public Page<User> findAllByDeletedAndUsernameContains(boolean deleted, String username, Pageable pageable) {
        return userRepository.findAllByDeletedAndUsernameContains(deleted, username, pageable);
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        Set<Role> reqRoles = user.getRoles();
        Set<Role> roles = new HashSet<>();

        reqRoles.forEach(role -> {
            switch (role.getName()) {
                case ROLE_CEO: {
                    addRoles(ERole.ROLE_CEO, roles);
                    break;
                }
                case ROLE_DEPOT_MANAGER: {
                    addRoles(ERole.ROLE_DEPOT_MANAGER, roles);
                    break;
                }
                case ROLE_POST_OFFICE_MANAGER: {
                    addRoles(ERole.ROLE_POST_OFFICE_MANAGER, roles);
                    break;
                }
                case ROLE_DEPOT_EMPLOYEE: {
                    addRoles(ERole.ROLE_DEPOT_EMPLOYEE, roles);
                    break;
                }
                case ROLE_POST_OFFICE_EMPLOYEE: {
                    addRoles(ERole.ROLE_POST_OFFICE_EMPLOYEE, roles);
                }
            }

        });

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserExport> findAllByDeletedToExport(boolean statusDelete) {
        List<User> userList = userRepository.findAllByDeleted(statusDelete);
        List<UserExport> userExportList = new ArrayList<>();
        userList.forEach(user -> userExportList.add(new UserExport(user.getUsername(), user.getEmail(), user.getProfile().getFirstName(), user.getProfile().getLastName())));
        return userExportList;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }


    @Override
    public boolean requestPasswordReset(String email) throws MessagingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        String token = new JwtUtils().generatePasswordResetToken(user.get().getId());
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user.get());

        passwordResetTokenRepository.save(passwordResetToken);
        emailService.resetPassword(email, token);
        return true;
    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;
        logger.error(token);
        if (new JwtUtils().hasTokenExpired(token)) {
            return false;
        }
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            return false;
        }

//        Prepare a new password
        String encodedPassword = passwordEncoder.encode(password);

//        Update user password into database
        User user = passwordResetToken.getUser();
        user.setPassword(encodedPassword);
        User userSave = userRepository.save(user);

//        verify if password was saved
        if(userSave.getPassword().equalsIgnoreCase(encodedPassword)){
            returnValue = true;
        }
        passwordResetTokenRepository.delete(passwordResetToken);
        return returnValue;
    }

    @Override
    public Page<User> findAllByUsernameContainsOrEmailContains(Role role, String username, String email, Pageable pageable) {
        role.setId(roleService.findByName(role.getName()).get().getId());
        return userRepository.findAllByRoleAndUsernameContainsOrEmailContains(role, username, email, pageable);
    }

    @Override
    public Page<User> findAllByRolesUsernameContainsOrEmailContains(Role role1, Role role2, String username, String email, Pageable pageable) {
        role1.setId(roleService.findByName(role1.getName()).get().getId());
        role2.setId(roleService.findByName(role2.getName()).get().getId());
        return userRepository.findAllByRolesAndUsernameContainsOrEmailContains(role1, role2, username, email, pageable);
    }

    public void addRoles(ERole roleName, Set<Role> roles) {
        Role userRole = roleService.findByName(roleName).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(userRole);
    }

}
