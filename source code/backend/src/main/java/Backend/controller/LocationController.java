package Backend.controller;

import Backend.dto.PageResult;
import Backend.dto.ServiceResult;
import Backend.entity.Location;
import Backend.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import Backend.utilities.ELocationType;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
@Slf4j
public class LocationController {
    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }



    @PreAuthorize("hasRole('CEO')")
    @PostMapping(value = "/location/add-transaction-office")
    public ResponseEntity<?> createNewLocationWarehouse(@Valid @RequestBody Location location) {
        try {
//            set type of location
            location.setType(ELocationType.TRANSACTION_OFFICE.toString());
            locationService.saveLocation(location);
            return ResponseEntity.ok().body(new ServiceResult(HttpStatus.OK.value(), "Thêm location thành công!", location));
        } catch (Exception e) {
            return ResponseEntity.ok(new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('CEO')")
    @PostMapping(value = "/location/add-warehouse")
    public ResponseEntity<?> createNewLocationOfficeTransaction(@Valid @RequestBody Location location) {
        try {
//            set type of location
            location.setType(ELocationType.WAREHOUSE.toString());
            locationService.saveLocation(location);
            return ResponseEntity.ok().body(new ServiceResult(HttpStatus.OK.value(), "Thêm location thành công!", location));
        } catch (Exception e) {
            return ResponseEntity.ok(new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null));
        }
    }

//    @PreAuthorize("hasRole('CEO')")
//    @GetMapping(value = "/location/get-list-warehouse")
//    public List<Location> getAllLocationWarehouse(){
//        List<Location> res = locationService.getLocationByType(ELocationType.WAREHOUSE.toString());
//        return res;
//    }

    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/location/get-list-warehouse")
    public PageResult getAllLocationWarehouseByPage(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable){
        Page<Location> resWarehouse = locationService.getLocationByTypePage(ELocationType.WAREHOUSE.toString(), pageable);
        return new PageResult(resWarehouse);
    }

    @PreAuthorize("hasRole('CEO')")
    @GetMapping(value = "/location/get-list-transaction-office")
    public PageResult getAllLocationTransactionOfficeByPage(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable){
        Page<Location> resTransactionOffice = locationService.getLocationByTypePage(ELocationType.TRANSACTION_OFFICE.toString(), pageable);
        return new PageResult(resTransactionOffice);
    }

    @GetMapping(value = "/location")
    public boolean test(@Valid @RequestBody Location location) {
        System.out.println("tesstttt");
        locationService.saveLocation(location);
        return true;
    }
}
