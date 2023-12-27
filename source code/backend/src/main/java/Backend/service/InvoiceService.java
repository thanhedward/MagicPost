package Backend.service;

import Backend.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> getInvoiceById(Long id);
    List<Invoice> getInvoiceList();

//    List<Invoice> getInvoiceByCreateUsername(String username);
//    List<Invoice> getInvoiceByConfirmUsername(String username);

    void saveInvoice(Invoice invoice);
}
