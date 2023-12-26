package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.Depot;
import Backend.entity.Province;
import Backend.service.DepotService;
import Backend.service.ProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
@Slf4j
public class DepotController {
    private final DepotService depotService;
    private final ProvinceService provinceService;


    @Autowired
    public DepotController(DepotService depotService, ProvinceService provinceService) {
        this.depotService = depotService;
        this.provinceService = provinceService;
    }

    @GetMapping(value = "/depot/get-depot")
    public List<Depot> getAllDepot() {
        return depotService.getDepotList();
    }

    @GetMapping(value = "/depot/find")
    public Optional<Depot> findDepotByProvince(@RequestParam String name) {
        return depotService.getDepotByProvince(provinceService.getProvinceById(name).get());
    }

    @GetMapping(value = "/province")
    public List<Province> findAllProvince() {
        return provinceService.getProvinceList();
    }

    @GetMapping(value = "/province/available")
    public List<Province> findAllProvinceAvailable() {
        List<Province> provinces = provinceService.getProvinceList();
        List<Depot> depots = depotService.getDepotList();
        for(Depot d: depots) {
            provinces.remove(d.getProvince());
        }
        return provinces;
    }

    @PostMapping(value = "/depot/create-depot")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createDepot(@RequestParam String provinceName){
        try {
            Province province = new Province(provinceName);
            if(!provinceService.getProvinceList().contains(province)) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có địa danh này!", ""));
            }
            if(depotService.existsByProvince(province)) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Điểm tập kết đã tồn tại!", ""));
            }
            Depot depot = new Depot();
            depot.setProvince(province);
            depotService.saveDepot(depot);
            return ResponseEntity.ok(depot);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
