package Backend.controller;

import Backend.entity.Invoice;
import Backend.entity.User;
import Backend.service.InvoiceService;
import Backend.service.UserService;
import Backend.utilities.InvoiceType;
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
public class InvoiceController {
    private InvoiceService invoiceService;
    private UserService userService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, UserService userService){
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @GetMapping(value = "/invoice")
    @PreAuthorize("hasRole('CEO')")
    public List<Invoice> getAllInvoice(){
        return invoiceService.getInvoiceList();
    }

    // TODO: add a parameter for api to choose type of invoice
    @PostMapping(value = "/invoice/create-invoice")
    @PreAuthorize("hasRole('CEO')")
    public ResponseEntity<Object> createInvoice(@Valid @RequestBody Invoice invoice){
        try {
            invoice.setType(InvoiceType.DEPOT_TO_DEPOT);
            String username = userService.getUserName();
            User user = userService.getUserByUsername(username).get();
            invoice.setCreateBy(user);
            invoiceService.saveInvoice(invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }


    @GetMapping(value = "/invoice/get-invoice-by-create-user")
    @PreAuthorize("hasRole('CEO')")
    public List<Invoice> getAllInvoiceByCreater(@RequestParam String username) {
        return invoiceService.getInvoiceByCreateUsername(username);
    }
}
