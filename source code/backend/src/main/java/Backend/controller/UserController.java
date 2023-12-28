package Backend.controller;

import Backend.dto.*;
import Backend.entity.*;
import Backend.service.*;
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
    private final DepotService depotService;
    private final PostOfficeService postOfficeService;
    private final DistrictService districtService;

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
        public UserController(UserService userService, PasswordEncoder passwordEncoder, DepotService depotService, PostOfficeService postOfficeService, DistrictService districtService) {
            this.userService = userService;
            this.passwordEncoder = passwordEncoder;
            this.depotService = depotService;
            this.postOfficeService = postOfficeService;
            this.districtService = districtService;
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
//    @PreAuthorize("hasRole('CEO')")
    public PageResult getUsersByPage(@PageableDefault(sort = "roles") Pageable pageable) {
        Page<User> userPage = userService.findUsersByPage(pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/get-manager/depot")
    @PreAuthorize("hasRole('CEO')")
    public PageResult getDepotManagersByPage(@PageableDefault(sort = "id") Pageable pageable) {
        Page<User> userPage = userService.getUsersByRoleByPage(ERole.ROLE_DEPOT_MANAGER, pageable);
        return new PageResult(userPage);
    }


    @GetMapping("/get-manager/post-office")
    @PreAuthorize("hasRole('CEO')")
    public PageResult getPostOfficeManagersByPage(@PageableDefault(sort = "id") Pageable pageable) {
        Page<User> userPage = userService.getUsersByRoleByPage(ERole.ROLE_POST_OFFICE_MANAGER, pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/get/depot")
    @PreAuthorize("hasAnyRole('DEPOT_MANAGER')")
    public PageResult getUsersByDepotAndPage(@PageableDefault(sort = "id") Pageable pageable) {
        String username = userService.getUserName();
        User currentUser = userService.getUserByUsername(username).get();

        Page<User> userPage = userService.getUsersByDepot(currentUser.getDepot(), pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/get/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER')")
    public PageResult getUsersByPostOfficeAndPage(@PageableDefault(sort = "id") Pageable pageable) {
        String username = userService.getUserName();
        User currentUser = userService.getUserByUsername(username).get();

        Page<User> userPage = userService.getUsersByPostOffice(currentUser.getPostOffice(), pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/search/manager")
    @PreAuthorize("hasRole('CEO')")
    public PageResult searchDepotManagerByUsernameOrEmail(@RequestParam(value = "search-keyword") String info, @PageableDefault(sort = "id") Pageable pageable) {
        Role role1 = new Role(ERole.ROLE_DEPOT_MANAGER);
        Role role2 = new Role(ERole.ROLE_POST_OFFICE_MANAGER);
        Page<User> userPage = userService.findAllByRolesUsernameContainsOrEmailContains(role1, role2, info, info, pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/search/depot")
    @PreAuthorize("hasRole('DEPOT_MANAGER')")
    public PageResult searchDepotEmployeeByUsernameOrEmail(@RequestParam(value = "search-keyword") String info, @PageableDefault(sort = "id") Pageable pageable) {
        Role role = new Role(ERole.ROLE_DEPOT_EMPLOYEE);
        Page<User> userPage = userService.findAllByUsernameContainsOrEmailContains(role, info, info, pageable);
        return new PageResult(userPage);
    }

    @GetMapping("/search/post-office")
    @PreAuthorize("hasRole('POST_OFFICE_MANAGER')")
    public PageResult searchPostOfficeEmployeeByUsernameOrEmail(@RequestParam(value = "search-keyword") String info, @PageableDefault(sort = "id") Pageable pageable) {
        Role role = new Role(ERole.ROLE_DEPOT_EMPLOYEE);
        Page<User> userPage = userService.findAllByUsernameContainsOrEmailContains(role, info, info, pageable);
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

    @PostMapping("/create/depot-manager")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<?> createDepotManager(@Valid @RequestBody User user, @RequestParam String provinceName) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_DEPOT_MANAGER));
        Province province = new Province(provinceName);
        if(!depotService.existsByProvince(province)) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
        }
        user.setDepot(depotService.getDepotByProvince(province).get());
        user.setRoles(roles);
        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));

    }


    @PostMapping("/create/post-office-manager")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<?> createPostOfficeManager(@Valid @RequestBody User user, @RequestParam String provinceName, @RequestParam String districtName) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_POST_OFFICE_MANAGER));

        Province province = new Province(provinceName);
        if(!depotService.existsByProvince(province)) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
        }
        if(!districtService.existsByProvinceAndName(province, districtName)) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có quận huyện trên trong tỉnh!", ""));
        }
        Depot depot = depotService.getDepotByProvince(new Province(provinceName)).get();
        District district = districtService.getDistrictByProvinceAndName(province, districtName);

        if(!postOfficeService.existsByDepotAndDistrict(depot, district)) {
            return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm giao dịch này!", ""));
        }
        user.setPostOffice(postOfficeService.getPostOfficeByDepotAndDistrict(depot, district).get());

        user.setRoles(roles);
        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));

    }

    @PostMapping("/create/depot")
    @PreAuthorize("hasAnyRole('DEPOT_MANAGER')")
    public ResponseEntity<?> createDepotEmployee(@Valid @RequestBody User user) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        String username = userService.getUserName();
        User currentUser = userService.getUserByUsername(username).get();

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_DEPOT_EMPLOYEE));
        user.setRoles(roles);

        user.setDepot(currentUser.getDepot());

        userService.createUser(user);
        return ResponseEntity.ok(new ServiceResult(HttpStatus.OK.value(), "User created successfully!", user));
    }

    @PostMapping("/create/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER')")
    public ResponseEntity<?> createPostOfficeEmployee(@Valid @RequestBody User user) {

        if(checkDuplicateUsernameAndEmail(user) != null) {
            return checkDuplicateUsernameAndEmail(user);
        }

        String username = userService.getUserName();
        User currentUser = userService.getUserByUsername(username).get();

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_POST_OFFICE_EMPLOYEE));
        user.setRoles(roles);

        user.setPostOffice(currentUser.getPostOffice());

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


}
