package Backend.controller;

import Backend.entity.Invoice;
import Backend.entity.Parcel;
import Backend.entity.User;
import Backend.service.*;
import Backend.utilities.InvoiceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/invoice")
@Slf4j
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final UserService userService;
    private final ParcelService parcelService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService, ParcelService parcelService){
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.parcelService = parcelService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('CEO')")
    public List<Invoice> getAllInvoice(){
        return invoiceService.getInvoiceList();
    }

    // TODO: add a parameter for api to choose type of invoice
    @PostMapping(value = "/create-invoice")
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody Invoice invoice, @RequestParam String type){
        try {
            String username = userService.getUserName();
            User user = userService.getUserByUsername(username).get();
            invoice.setCreateBy(user);

            //TODO: Check if parcel exist?, send to the same location?
            Set<Parcel> parcels = invoice.getParcels().stream().map(parcel -> parcelService.getParcelById(parcel.getId()).get()).collect(Collectors.toSet());
            invoice.setParcels(parcels);


            switch (type) {
                    case "POST_OFFICE_TO_DEPOT": {
                    invoice.setType(InvoiceType.POST_OFFICE_TO_DEPOT);
                    invoice.setPostOffice(user.getPostOffice());
                    invoice.setFirstDepot(user.getPostOffice().getDepot());
                    break;
                }
                case "DEPOT_TO_DEPOT": {
                    invoice.setType(InvoiceType.DEPOT_TO_DEPOT);
                    invoice.setFirstDepot(user.getDepot());
                    for(Parcel parcel: parcels) {
                        invoice.setSecondDepot(parcel.getEndDepot());
                        break;
                    }
                    break;
                }
                case "DEPOT_TO_POST_OFFICE": {
                    invoice.setType(InvoiceType.DEPOT_TO_POST_OFFICE);
                    invoice.setFirstDepot(user.getDepot());
                    for(Parcel parcel: parcels) {
                        invoice.setPostOffice(parcel.getEndPostOffice());
                        break;
                    }
                    break;
                }
                case "POST_OFFICE_TO_HOME": {
                    invoice.setType(InvoiceType.POST_OFFICE_TO_HOME);
                    invoice.setPostOffice(user.getPostOffice());
                    for(Parcel parcel: parcels) {
                        invoice.setEndAddress(parcel.getEndAddress());
                        break;
                    }
                }
            }

            invoiceService.saveInvoice(invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/confirm")
    //@PreAuthorize("hasAnyRole('CEO', 'DEPOT_MANAGER', 'POST_OFFICE_MANAGER', 'DEPOT_EMPLOYEE', 'POST_OFFICE_EMPLOYEE')")
    public ResponseEntity<?> confirmInvoice(@RequestParam String name) {
        try {
//            List<Invoice> invoice = invoiceService.getInvoiceByCreateUsername(name);

//            String username = userService.getUserName();
////            User user = userService.getUserByUsername(username).get();
////            invoice.setConfirmBy(user);
////            invoice.setConfirmDate(LocalDateTime.now());
////            invoice.setConfirmed(true);
////
////            Set<Parcel> parcels = invoice.getParcels();
////            for(Parcel parcel: parcels) {
////                switch (parcel.getStatus()) {
////                    case START_POS: {
////                        parcel.setStatus(ParcelStatus.FIRST_DEPOT);
////                        break;
////                    }
////                    case FIRST_DEPOT: {
////                        parcel.setStatus(ParcelStatus.LAST_DEPOT);
////                        break;
////                    }
////                    case LAST_DEPOT: {
////                        parcel.setStatus(ParcelStatus.END_POS);
////                        break;
////                    }
////                    case END_POS: {
////                        parcel.setStatus(ParcelStatus.SUCCESS);
////                    }
////                }
////            }
////            invoiceService.saveInvoice(invoice);
////            return ResponseEntity.ok(invoice);
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

//    @GetMapping(value = "/invoice/get-invoice-by-create-user")
//    @PreAuthorize("hasRole('CEO')")
//    public List<Invoice> getAllInvoiceByCreator(@RequestParam String username) {
//        return invoiceService.getInvoiceByCreateUsername(username);
//    }
}
