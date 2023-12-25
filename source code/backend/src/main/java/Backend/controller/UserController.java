package Backend.controller;

import Backend.dto.*;
import Backend.entity.Profile;
import Backend.entity.Role;
import Backend.entity.User;
import Backend.service.DepotService;
import Backend.service.PostOfficeService;
import Backend.service.RoleService;
import Backend.service.UserService;
import Backend.utilities.ERole;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping(value = "/api/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final DepotService depotService;
    private final PostOfficeService postOfficeService;

//    private IntakeService intakeService;
//    private ExcelService excelService;
//    FilesStorageService filesStorageService;

//    @Autowired
//    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, ExcelService excelService, FilesStorageService filesStorageService) {
//        this.userService = userService;
//        this.roleService = roleService;
//        this.passwordEncoder = passwordEncoder;
//        this.excelService = excelService;
//        this.filesStorageService = filesStorageService;
//    }

        @Autowired
        public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, DepotService depotService, PostOfficeService postOfficeService) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.depotService = depotService;
        this.postOfficeService = postOfficeService;
    }


    @GetMapping(value = "/profile")
    public ResponseEntity<?> getUser(@RequestParam String username) {
        Optional<User> user;
        if (username.isEmpty()) {
            user = userService.getUserByUsername(userService.getUserName()  );
        } else {
            user = userService.getUserByUsername(username);
        }
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ServiceResult(HttpStatus.NOT_FOUND.value(), "Tên đăng nhâp " + username + " không tìm thấy!", null));
        }
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "Lấy thông tin user " + username + " thành công!", user));
    }

    @GetMapping("/check-username")
    public boolean checkUsername(@RequestParam("value") String value) {
        return userService.existsByUsername(value);
    }

    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam("value") String value) {

        return userService.existsByEmail(value);
    }

//    @PatchMapping("/{id}/email/updating")
//    public ResponseEntity updateEmail(@Valid @RequestBody EmailUpdate data, @PathVariable Long id) {
//        User user = userService.findUserById(id).get();
//        boolean isPassword = passwordEncoder.matches(data.getPassword(), user.getPassword());
//        log.error(String.valueOf("matching password? : " + isPassword));
//        if (isPassword == false) {
//            return ResponseEntity.ok(new ServiceResult(HttpStatus.EXPECTATION_FAILED.value(), "Password is wrong", null));
//        }
//        user.setEmail(data.getEmail());
//        userService.updateUser(user);
//        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "Update email successfully", data.getEmail()));
//    }

    @PatchMapping("/{id}/password/updating")
    public ResponseEntity<?> updatePass(@Valid @RequestBody PasswordUpdate passwordUpdate, @PathVariable Long id) {
        try {
//            log.error(passwordUpdate.toString());
            User user = userService.findUserById(id).get();
            if (passwordEncoder.matches(passwordUpdate.getCurrentPassword(), user.getPassword())) {
                if (!passwordUpdate.getCurrentPassword().equals(passwordUpdate.getNewPassword())) {
//                    OK
                    user.setPassword(passwordEncoder.encode(passwordUpdate.getNewPassword()));
                    userService.updateUser(user);
                    return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "Update password successfully", null));
                } else {
//                    New password is duplicated with current password
                    return ResponseEntity.ok(new ServiceResult(HttpStatus.CONFLICT.value(), "This is old password", null));
                }
            } else {
//                Password is wrong
                return ResponseEntity.ok(new ServiceResult(HttpStatus.BAD_REQUEST.value(), "Wrong password, please check again!", null));
            }

        } catch (Exception e) {
            return ResponseEntity.ok(new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/check-email")
    public boolean checkExistsEmailUpdate(@RequestParam("value") String value, @PathVariable Long id) {
        if (userService.existsByEmail(value)) {
//            This is my email
            return !userService.findUserById(id).get().getEmail().equals(value);
        }
        return false;
    }

    @PreAuthorize("hasRole('CEO')")
    @GetMapping("/{id}/deleted/{deleted}")
    public ResponseEntity<?> deleteTempUser(@PathVariable Long id, @PathVariable boolean deleted) {
        User user = userService.findUserById(id).get();
        user.setDeleted(deleted);
        userService.updateUser(user);
        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    @PreAuthorize("hasRole('CEO')")
    public PageResult getUsersByPage(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        Page<User> userPage = userService.findUsersByPage(pageable);
        return new PageResult(userPage);
    }

//    @GetMapping("/deleted/{status}")
//    @PreAuthorize("hasRole('CEO')")
//    public PageResult getUsersByPage(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
//        Page<User> userPage = userService.findUsersByPage(pageable);
//        return new PageResult(userPage);
//    }

    @GetMapping("/search")
    public PageResult searchUsersByUsernameOrEmail(@RequestParam(value = "search-keyword") String info, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        LOGGER.error("check search");
        Page<User> userPage = userService.findAllByUsernameContainsOrEmailContains(info, info, pageable);
        LOGGER.error(userPage.toString());
        return new PageResult(userPage);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CEO')")
        public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdate userReq, @PathVariable Long id) {
        User userUpdate = userService.findUserById(id).get();
        if (userReq.getPassword() != null) {
            userUpdate.setPassword(passwordEncoder.encode(userReq.getPassword()));
        }
        userUpdate.setEmail(userReq.getEmail());
        Profile profile = userReq.getProfile();
        profile.setId(userUpdate.getProfile().getId());
        profile.setFirstName(userReq.getProfile().getFirstName());
        profile.setLastName(userReq.getProfile().getLastName());
        userUpdate.setProfile(profile);
        userService.updateUser(userUpdate);
        return ResponseEntity.ok().body(new ServiceResult(HttpStatus.OK.value(), "Cập nhật thành công!", userUpdate));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCEO(@Valid @RequestBody User user) {
        if(getUsersByPage(PageRequest.of(1,1)).getData().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(ERole.ROLE_CEO));
            user.setRoles(roles);
            userService.createUser(user);
            return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));
        }
        return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Already have a CEO user!", ""));
    }

    private ResponseEntity<?> checkDuplicateUsernameAndEmail(User user) {

//        Check username if exists?
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Tên đăng nhập đã có người sử dụng!", ""));

        }
//        Check email if exists?
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Email đã có người sử dụng!", ""));

        }
        return null;
    }

    @PostMapping("/create/manager")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<?> createManager(@Valid @RequestBody User user, @RequestParam Boolean depot, @RequestParam Long id) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        Set<Role> roles = new HashSet<>();
        if(depot) {
            roles.add(new Role(ERole.ROLE_DEPOT_MANAGER));
            if(depotService.getDepotByID(id).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
            }
            user.setDepot(depotService.getDepotByID(id).get());
        }
        else {
            roles.add(new Role(ERole.ROLE_POST_OFFICE_MANAGER));
            if(postOfficeService.getPostOfficeByID(id).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm giao dịch này!", ""));
            }
            user.setPostOffice(postOfficeService.getPostOfficeByID(id).get());
        }
        user.setRoles(roles);
        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));

    }


    @PostMapping("/create/depot")
    @PreAuthorize("hasAnyRole('CEO','DEPOT_MANAGER')")
    public ResponseEntity<?> createDepotEmployee(@Valid @RequestBody User user, @RequestParam Long id) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_DEPOT_EMPLOYEE));
        user.setRoles(roles);
        if(depotService.getDepotByID(id).isEmpty()) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
        }
        user.setDepot(depotService.getDepotByID(id).get());

        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));
    }

    @PostMapping("/create/post-office")
    @PreAuthorize("hasAnyRole('CEO','POST_OFFICE_MANAGER')")
    public ResponseEntity<?> createPostOfficeEmployee(@Valid @RequestBody User user, @RequestParam Long id) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_POST_OFFICE_EMPLOYEE));
        user.setRoles(roles);
        if(postOfficeService.getPostOfficeByID(id).isEmpty()) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm giao dịch này!", ""));
        }
        user.setPostOffice(postOfficeService.getPostOfficeByID(id).get());

        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));
    }

    @GetMapping("deleted/{status}/export/users.csv")
    public void exportUsersToCSV(HttpServletResponse response) throws Exception {
        String fileName = "users.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        //create a csv writer
        StatefulBeanToCsv<UserExport> writer = new StatefulBeanToCsvBuilder<UserExport>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file'
        writer.write(userService.findAllByDeletedToExport(false));
    }

    public void addRoles(ERole roleName, Set<Role> roles) {
        Role userRole = roleService.findByName(roleName).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
        roles.add(userRole);
    }

}
