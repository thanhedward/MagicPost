package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.Province;
import Backend.service.ProvinceService;
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
@RequestMapping(value = "/api/province")
@Slf4j
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping(value = "/get-province")
    public List<Province> getAllProvince() {
        return provinceService.getProvinceList();
    }

    @PostMapping(value = "/create-province")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createProvince(@Valid @RequestBody Province province) {
        try {
            if(getAllProvince().contains(province)) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Tỉnh thành đã tồn tại!", ""));
            }
            provinceService.saveProvince(province);
            return ResponseEntity.ok(province);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


}
