package Backend.controller;

import Backend.entity.Invoice;
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

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/invoice")
@Slf4j
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    @GetMapping(value = "/get")
    @PreAuthorize("hasRole('CEO')")
    public List<Invoice> getAllInvoice(){
        return invoiceService.getInvoiceList();
    }

    @PostMapping(value = "/create/post-office/depot")
    @PreAuthorize("hasRole('POST_OFFICE_EMPOLYEE')")
    public ResponseEntity<Object> createInvoiceFromPostOfficeToDepot(@Valid @RequestBody Invoice invoice){
        try {
            invoiceService.createInvoice(invoice, InvoiceType.POST_OFFICE_TO_DEPOT);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/create/depot/depot")
    @PreAuthorize("hasRole('DEPOT_EMPOLYEE')")
    public ResponseEntity<Object> createInvoiceFromDepotToDepot(@Valid @RequestBody Invoice invoice){
        try {
            invoiceService.createInvoice(invoice, InvoiceType.DEPOT_TO_DEPOT);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/create/depot/post-office")
    @PreAuthorize("hasRole('DEPOT_EMPOLYEE')")
    public ResponseEntity<Object> createInvoiceFromDepotToPostOffice(@Valid @RequestBody Invoice invoice){
        try {
            invoiceService.createInvoice(invoice, InvoiceType.DEPOT_TO_POST_OFFICE);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/create/post-office/home")
    @PreAuthorize("hasRole('POST_OFFICE_EMPOLYEE')")
    public ResponseEntity<Object> createInvoiceFromPostOfficeToHome(@Valid @RequestBody Invoice invoice){
        try {
            invoiceService.createInvoice(invoice, InvoiceType.POST_OFFICE_TO_HOME);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping(value = "/confirm/{id}")
    public ResponseEntity<?> confirmInvoice(@PathVariable Long id) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(id).get();

            invoiceService.confirmInvoice(invoice);
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
