package Backend.controller;

import Backend.entity.Parcels;
import Backend.entity.User;
import Backend.service.ParcelsService;
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
    private ParcelsService parcelsService;
    private UserService userService;

    @Autowired
    public ParcelController(ParcelsService parcelsService, UserService userService){
        this.parcelsService = parcelsService;
        this.userService = userService;
    }

    @GetMapping(value = "/parcels")
    @PreAuthorize("hasRole('CEO')")
    public List<Parcels> getAllParcels() {
        return parcelsService.getParcelList();
    }

    @PostMapping(value = "/parcels/create-parcel")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createParcel(@Valid @RequestBody Parcels parcel){
        try {
            String username = userService.getUserName();
            User user = userService.getUserByUsername(username).get();
            parcel.setAcceptedBy(user);
            parcel.setStatus(ParcelStatus.START_POS);
            parcelsService.saveParcels(parcel);
            return ResponseEntity.ok(parcel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping(value = "/parcels/get-parcels-by-sender")
    @PreAuthorize("hasRole('CEO')")
    public List<Parcels> getAllParcelsBySender(@RequestParam String username) {
        return parcelsService.getParcelByAcceptedUserUsername(username);
    }
}
