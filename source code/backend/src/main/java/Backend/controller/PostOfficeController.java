package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.PostOffice;
import Backend.service.DepotService;
import Backend.service.DistrictService;
import Backend.service.PostOfficeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/post-office")
@Slf4j
public class PostOfficeController {
    private static final Logger logger = LoggerFactory.getLogger(PostOfficeController.class);
    private final PostOfficeService postOfficeService;
    private final DepotService depotService;
    private final DistrictService districtService;

    @Autowired
    public PostOfficeController(PostOfficeService postOfficeService, DepotService depotService, DistrictService districtService) {
        this.postOfficeService = postOfficeService;
        this.depotService = depotService;
        this.districtService = districtService;
    }

    @GetMapping(value = "/get-post-office")
    public List<PostOffice> getAllPostOffice() {
        return postOfficeService.getPostOfficeList();
    }

    @PostMapping(value = "/create-post-office")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createPostOffice(@Valid @RequestBody PostOffice postOffice){
        try {
            if(!depotService.existsByProvince(postOffice.getDepot().getProvince())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có điểm tập kết này!", ""));
            } else if(!districtService.existsById(postOffice.getDistrict().getId())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không có địa danh này!", ""));
            }
            if(postOfficeService.existsByDepotAndDistrict(postOffice.getDepot(), postOffice.getDistrict())) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Điểm giao dịch đã tồn tại!", ""));
            }
            postOfficeService.savePostOffice(postOffice);
            return ResponseEntity.ok(postOffice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
