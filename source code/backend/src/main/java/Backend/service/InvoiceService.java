package Backend.service;

import Backend.entity.Invoice;
import Backend.utilities.InvoiceType;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> getInvoiceById(Long id);
    List<Invoice> getInvoiceList();

    List<Invoice> getInvoiceByFirstDepot();
    List<Invoice> getInvoiceBySecondDepot();
    List<Invoice> getInvoiceByEndPostOffice();
    List<Invoice> getInvoiceByEndPostOfficeToHome();

    Invoice createInvoice(Invoice invoice, InvoiceType invoiceType);

    void confirmInvoice(Invoice invoice, boolean fail);
}
