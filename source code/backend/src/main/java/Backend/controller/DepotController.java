package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.Depot;
import Backend.service.DepotService;
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
@RequestMapping(value = "/api/depot")
@Slf4j
public class DepotController {
    private final DepotService depotService;
    private final ProvinceService provinceService;

    @Autowired
    public DepotController(DepotService depotService, ProvinceService provinceService) {
        this.depotService = depotService;
        this.provinceService = provinceService;
    }

    @GetMapping(value = "/get-depot")
    public List<Depot> getAllDepot() {
        return depotService.getDepotList();
    }

    @PostMapping(value = "/create-depot")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createDepot(@Valid @RequestBody Depot depot){
        try {
            if(!provinceService.getProvinceList().contains(depot.getProvince())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có địa danh này!", ""));
            }
            if(depotService.existsByProvince(depot.getProvince())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Điểm tập kết đã tồn tại!", ""));
            }
            depotService.saveDepot(depot);
            return ResponseEntity.ok(depot);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
