package Backend.controller;

import Backend.dto.PageResult;
import Backend.dto.ServiceResult;
import Backend.entity.Location;
import Backend.entity.Parcel;
import Backend.new_entity.User;
import Backend.service.LocationService;
import Backend.service.ParcelService;
import Backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
@Slf4j
public class ParcelController {
    private ParcelService parcelService;

    private UserService userService;

    private LocationService locationService;

    @Autowired
    public ParcelController(ParcelService parcelService, UserService userService, LocationService locationService) {
        this.locationService = locationService;
        this.parcelService = parcelService;
        this.userService = userService;
    }

    // TODO: role User
    // Khi tao don hang khong duoc set sender_id
    @PreAuthorize("hasRole('CEO') or hasRole('POST_OFFICE_MANAGER')")
    @PostMapping(value = "/parcel/add-parcel")
    public ResponseEntity<?> createParcel(@Valid @RequestBody Parcel parcel, @RequestParam Long start_location_id, @RequestParam Long end_location_id, @RequestParam String sender_username) {
        try {
//            set type of
//            parcel.setSender(userService.getUserName());
            Optional<User> senderUser = userService.getUserByUsername(sender_username);
            Optional<User> employeeUserOptional = userService.getUserByUsername(userService.getUserName());
            parcel.setCreatedBy(employeeUserOptional.get());
            parcel.setLastModifiedBy(employeeUserOptional.get());
            parcel.setSender(senderUser.get());
            Optional<Location> startLocation = locationService.getLocationById(start_location_id);
            Optional<Location> endLocation = locationService.getLocationById(end_location_id);
            parcel.setStartLocation(startLocation.get());
            parcel.setEndLocation(endLocation.get());
            parcelService.saveParcel(parcel);
            return ResponseEntity.ok().body(new ServiceResult(HttpStatus.OK.value(), "Thêm parcel thành công!", parcel));
        } catch (Exception e) {
            return ResponseEntity.ok(new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }


    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/parcel/get-parcel-by-user-id")
    public List<Parcel> getAllParcelBySenderId(@RequestParam Long sender_id){
        List<Parcel> res = parcelService.findAllBySenderId(sender_id);
        return res;
    }

    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/parcel/get-parcel-start-location-id")
    public List<Parcel> getAllParcelByStartLocationId(@RequestParam Long start_location_id){
        System.out.println("1111");
        List<Parcel> res = parcelService.getParcelListByStartLocationId(start_location_id);
        return res;
    }

    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/parcel/get-parcel-end-location-id")
    public List<Parcel> getAllParcelByEndLocationId(@RequestParam Long end_location_id){
        List<Parcel> res = parcelService.getParcelListByEndLocationId(end_location_id);
        return res;
    }


    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/parcel/get-list-parcel-by-username")
    public PageResult getAllParcelByCreatedUsername(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, @RequestParam String create_username){
        Page<Parcel> resParcel = parcelService.findAllByCreatedBy_Username(pageable, create_username);
        return new PageResult(resParcel);
    }

}
