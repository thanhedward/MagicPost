package Backend.controller;

import Backend.dto.ServiceResult;
import Backend.entity.Parcel;
import Backend.entity.PostOffice;
import Backend.entity.User;
import Backend.service.ParcelsService;
import Backend.service.PostOfficeService;
import Backend.service.UserService;
import Backend.utilities.ParcelStatus;
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
@RequestMapping(value = "/api")
@Slf4j
public class ParcelController {
    private final ParcelsService parcelsService;
    private final UserService userService;
    private final PostOfficeService postOfficeService;

    @Autowired
    public ParcelController(ParcelsService parcelsService, UserService userService, PostOfficeService postOfficeService){
        this.parcelsService = parcelsService;
        this.userService = userService;
        this.postOfficeService = postOfficeService;
    }

    @GetMapping(value = "/parcel")
    @PreAuthorize("hasRole('CEO')")
    public List<Parcel> getAllParcels() {
        return parcelsService.getParcelList();
    }

    @PostMapping(value = "/parcel/create-parcel")
    @PreAuthorize("hasAnyRole('CEO', 'POST_OFFICE_MANAGER', 'POST_OFFICE_EMPLOYEE')")
    public ResponseEntity<Object> createParcel(@Valid @RequestBody Parcel parcel){
        try {
            String username = userService.getUserName();
            User user = userService.getUserByUsername(username).get();
            parcel.setAcceptedBy(user);
            parcel.setStatus(ParcelStatus.START_POS);
            parcel.setStartPostOffice(user.getPostOffice());
            parcel.setStartDepot(user.getPostOffice().getDepot());

            if(postOfficeService.getPostOfficeByID(parcel.getEndPostOffice().getId()).isEmpty()) {
                return ResponseEntity.badRequest().body(new ServiceResult(HttpStatus.CONFLICT.value(), "Không tồn tại điểm giao dịch này!", ""));
            }
            PostOffice postOffice = postOfficeService.getPostOfficeByID(parcel.getEndPostOffice().getId()).get();
            parcel.setEndPostOffice(postOffice);
            parcel.setEndDepot(postOffice.getDepot());

            parcelsService.saveParcels(parcel);
            return ResponseEntity.ok(parcel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


    @GetMapping(value = "/parcel/get-parcels-by-sender")
    @PreAuthorize("hasRole('CEO')")
    public List<Parcel> getAllParcelsBySender(@RequestParam String username) {
        return parcelsService.getParcelByAcceptedUserUsername(username);
    }


}
