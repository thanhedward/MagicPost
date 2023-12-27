package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.Depot;
import Backend.entity.District;
import Backend.entity.PostOffice;
import Backend.entity.Province;
import Backend.service.DepotService;
import Backend.service.DistrictService;
import Backend.service.PostOfficeService;
import Backend.service.ProvinceService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api")
@Slf4j
public class PostOfficeController {
    private static final Logger logger = LoggerFactory.getLogger(PostOfficeController.class);
    private final PostOfficeService postOfficeService;
    private final DepotService depotService;
    private final ProvinceService provinceService;
    private final DistrictService districtService;

    @Autowired
    public PostOfficeController(PostOfficeService postOfficeService, DepotService depotService, ProvinceService provinceService, DistrictService districtService) {
        this.postOfficeService = postOfficeService;
        this.depotService = depotService;
        this.provinceService = provinceService;
        this.districtService = districtService;
    }

    @GetMapping(value = "/post-office/get-post-office")
    public List<PostOffice> getAllPostOffice() {
        return postOfficeService.getPostOfficeList();
    }

    @GetMapping(value = "/post-office/find-by-province")
    public List<PostOffice> findPostOfficeByDepotAndDistrict(@RequestParam String provinceName) {
        Province province = provinceService.getProvinceById(provinceName).get();
        return postOfficeService.getPostOfficeListByDepot(depotService.getDepotByProvince(province).get());
    }

    @GetMapping(value = "/post-office/find")
    public Optional<PostOffice> findPostOfficeByDepotAndDistrict(@RequestParam String provinceName, @RequestParam String districtName) {
        Province province = provinceService.getProvinceById(provinceName).get();
        return postOfficeService.getPostOfficeByDepotAndDistrict(
                depotService.getDepotByProvince(province).get(),
                districtService.getDistrictByProvinceAndName(province, districtName));
    }

    @GetMapping(value = "/district")
    public List<District> findAllDistrict() {
        return districtService.getDistrictList();
    }

    @GetMapping(value = "/district/available")
    public List<District> findAllDistrictAvailable() {
        List<District> districts = districtService.getDistrictList();
        List<PostOffice> postOffices = postOfficeService.getPostOfficeList();
        for(PostOffice p: postOffices) {
            districts.remove(p.getDistrict());
        }
        return districts;
    }

    @GetMapping(value = "/district/available-by")
    public List<District> findAllDistrictAvailableByProvince(@RequestParam String provinceName) {
        if(provinceService.getProvinceById(provinceName).isEmpty()) {
            return new ArrayList<>();
        }
        Province province = provinceService.getProvinceById(provinceName).get();
        List<District> districts = districtService.getDistrictListByProvince(province);

        if(depotService.getDepotByProvince(province).isEmpty()) {
            return districts;
        }
        Depot depot = depotService.getDepotByProvince(province).get();
        List<PostOffice> postOffices = postOfficeService.getPostOfficeListByDepot(depot);
        for(PostOffice p: postOffices) {
            districts.remove(p.getDistrict());
        }
        return districts;
    }

    @PostMapping(value = "/post-office/create-post-office")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createPostOffice(@RequestParam String provinceName, @RequestParam String districtName){
        try {
            Province province = new Province(provinceName);
            if(depotService.getDepotByProvince(province).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
            }
            if(!districtService.existsByProvinceAndName(province, districtName)) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có địa danh này!", ""));
            }
            PostOffice postOffice = new PostOffice();
            Depot depot = depotService.getDepotByProvince(province).get();
            District district = districtService.getDistrictByProvinceAndName(province, districtName);
            postOffice.setDepot(depot);
            postOffice.setDistrict(district);
            if(postOfficeService.existsByDepotAndDistrict(depot, district)) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Điểm giao dịch đã tồn tại!", ""));
            }
            postOfficeService.savePostOffice(postOffice);
            return ResponseEntity.ok(postOffice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
