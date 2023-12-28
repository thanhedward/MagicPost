package Backend.controller;

import Backend.dto.ParcelDto;
import Backend.dto.ServiceResult;
import Backend.entity.*;
import Backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/parcel")
@Slf4j
public class ParcelController {
    private final ParcelService parcelService;
    private final UserService userService;
    private final DepotService depotService;
    private final PostOfficeService postOfficeService;
    private final DistrictService districtService;

    @Autowired
    public ParcelController(ParcelService parcelService, UserService userService, DepotService depotService, PostOfficeService postOfficeService, DistrictService districtService){
        this.parcelService = parcelService;
        this.userService = userService;
        this.depotService = depotService;
        this.postOfficeService = postOfficeService;
        this.districtService = districtService;
    }

    @GetMapping(value = "/get")
    @PreAuthorize("hasRole('CEO')")
    public List<Parcel> getAllParcels() {
        return parcelService.getParcelList();
    }

    @GetMapping(value = "/get-parcel-by-post-office-to-depot")
    @PreAuthorize("hasRole('POST_OFFICE_EMPLOYEE')")
    public List<Parcel> getParcelByPostOfficeToDepot() {
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();
        PostOffice postOffice = user.getPostOffice();
        Depot depot = postOffice.getDepot();
        return parcelService.getParcelListByPostOfficeToDepot(postOffice, depot);
    }

    @GetMapping(value = "/get-parcel-by-depot-to-depot")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<Parcel> getParcelByDepotToDepot() {
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();
        Depot startDepot = user.getPostOffice().getDepot();
        return parcelService.getParcelListByDepotToDepot(startDepot);
    }

    @GetMapping(value = "/get-parcel-by-depot-to-post-office")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<Parcel> getParcelByDepotToPostOffice() {
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();
        Depot endDepot = user.getPostOffice().getDepot();
        return parcelService.getParcelListByDepotToPostOffice(endDepot);
    }

    @GetMapping(value = "/get-parcel-by-post-office-to-home")
    @PreAuthorize("hasRole('POST_OFFICE_EMPLOYEE')")
    public List<Parcel> getParcelByPostOfficeToHome() {
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();
        PostOffice postOffice = user.getPostOffice();
        return parcelService.getParcelListByPostOfficeToHome(postOffice);
    }

    @PostMapping(value = "/create-parcel")
    @PreAuthorize("hasAnyRole('POST_OFFICE_EMPLOYEE')")
    public ResponseEntity<Object> createParcel(@Valid @RequestBody ParcelDto parcelDto){
        try {
            if(depotService.getDepotByProvince(new Province(parcelDto.getEndProvinceName())).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không tồn tại điểm tập kết này!", ""));
            }
            Depot depot = depotService.getDepotByProvince(new Province(parcelDto.getEndProvinceName())).get();
            District district = districtService.getDistrictByProvinceAndName(new Province(parcelDto.getEndProvinceName()), parcelDto.getEndDistrictName());
            if(postOfficeService.getPostOfficeByDepotAndDistrict(depot, district).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không tồn tại điểm giao dịch này!", ""));
            }
            PostOffice endPostOffice = postOfficeService.getPostOfficeByDepotAndDistrict(depot, district).get();

            return ResponseEntity.ok(parcelService.saveParcels(parcelDto, endPostOffice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping(value = "/complete")
    @PreAuthorize("hasAnyRole('CEO')")
    public List<Parcel> getAllParcelCompleted() {
        return parcelService.getAllParcelSucceed();
    }

    @GetMapping(value = "/fail")
    @PreAuthorize("hasAnyRole('CEO')")
    public List<Parcel> getAllParcelFailed() {
        return parcelService.getAllParcelFailed();
    }

    @GetMapping(value = "/complete/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER', 'POST_OFFICE_EMPLOYEE')")
    public List<Parcel> getParcelByCompleted() {
        return parcelService.getParcelListSucceedByPostOffice();
    }

    @GetMapping(value = "/fail/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER', 'POST_OFFICE_EMPLOYEE')")
    public List<Parcel> getParcelByFailed() {
        return parcelService.getParcelListFailedByPostOffice();
    }
}
