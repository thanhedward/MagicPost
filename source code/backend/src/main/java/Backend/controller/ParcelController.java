package Backend.controller;

import Backend.dto.ParcelDto;
import Backend.dto.ParcelResultDto;
import Backend.dto.ServiceResult;
import Backend.dto.TrackingDto;
import Backend.entity.*;
import Backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.*;


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

    @GetMapping(value = "/get/post-office/depot")
    @PreAuthorize("hasRole('POST_OFFICE_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByPostOfficeToDepot() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
        Depot depot = postOffice.getDepot();
        return parcelService.getParcelListByPostOfficeToDepot(postOffice, depot);
    }

    @GetMapping(value = "/get/depot/depot")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByDepotToDepotByProvince(@RequestParam String provinceName) {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot startDepot = currentUser.getDepot();
        List<ParcelResultDto> parcelResults = parcelService.getParcelListByDepotToDepot(startDepot);

        parcelResults.removeIf(parcelResult -> !parcelResult.getToAddress().equals(provinceName));
        return parcelResults;
    }

    @GetMapping(value = "/get/depot/depot/get/province")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<Province> getProvinceHaveParcelFromDepotToDepot() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot startDepot = currentUser.getDepot();
        List<ParcelResultDto> parcelResults = parcelService.getParcelListByDepotToDepot(startDepot);

        Set<Province> province = new HashSet<>();
        for(ParcelResultDto parcel: parcelResults) {
            province.add(new Province(parcel.getToAddress()));
        }
        return province.stream().toList();
    }

    @GetMapping(value = "/get/depot/post-office")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByDepotToPostOffice(@RequestParam String districtName) {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot endDepot = currentUser.getDepot();
        List<ParcelResultDto> parcelResults = parcelService.getParcelListByDepotToPostOffice(endDepot);

        parcelResults.removeIf(parcelResult -> !parcelResult.getToAddress().replaceAll(",(.*)", "").equals(districtName));
        return parcelResults;
    }

    @GetMapping(value = "/get/depot/post-office/get/district")
    @PreAuthorize("hasRole('DEPOT_EMPLOYEE')")
    public List<Province> getDistrictHaveParcelFromDepotToPostOffice() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot startDepot = currentUser.getDepot();
        List<ParcelResultDto> parcelResults = parcelService.getParcelListByDepotToPostOffice(startDepot);

        Set<Province> district = new HashSet<>();
        for(ParcelResultDto parcel: parcelResults) {
            district.add(new Province(parcel.getToAddress().replaceAll(",(.*)", "")));
        }
        return district.stream().toList();
    }

    @GetMapping(value = "/get/post-office/home")
    @PreAuthorize("hasRole('POST_OFFICE_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByPostOfficeToHome() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
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

            return ResponseEntity.ok(parcelService.createParcels(parcelDto, endPostOffice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping(value = "/get/complete/all")
    @PreAuthorize("hasAnyRole('CEO')")
    public List<Parcel> getAllParcelCompleted() {
        return parcelService.getAllParcelSucceed();
    }

    @GetMapping(value = "/get/fail/all")
    @PreAuthorize("hasAnyRole('CEO')")
    public List<Parcel> getAllParcelFailed() {
        return parcelService.getAllParcelFailed();
    }

    @GetMapping(value = "/get/complete/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER', 'POST_OFFICE_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByCompleted() {
        return parcelService.getParcelListSucceedByPostOffice();
    }

    @GetMapping(value = "get/fail/post-office")
    @PreAuthorize("hasAnyRole('POST_OFFICE_MANAGER', 'POST_OFFICE_EMPLOYEE')")
    public List<ParcelResultDto> getParcelByFailed() {
        return parcelService.getParcelListFailedByPostOffice();
    }

//    @GetMapping(value = "/information")
//    public

    @GetMapping(value = "/tracking")
    public TrackingDto getParcelTracking(@RequestParam String name) {
        return parcelService.tracking(name);
    }

}
