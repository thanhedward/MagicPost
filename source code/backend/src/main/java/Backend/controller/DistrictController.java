package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.District;
import Backend.service.DistrictService;
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
@RequestMapping(value = "/api/district")
@Slf4j
public class DistrictController {
    private final DistrictService districtService;

    @Autowired
    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping(value = "/get-district")
    public List<District> getAllDistrict() {
        return districtService.getDistrictList();
    }

    @PostMapping(value = "/create-district")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createDistrict(@Valid @RequestBody District district) {
        try {
            if(districtService.existsByProvinceAndName(district.getProvince(), district.getName())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Huyện thị đã tồn tại!", ""));
            }
            districtService.saveDistrict(district);
            return ResponseEntity.ok(district);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
