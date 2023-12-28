package Backend.service;

import Backend.entity.Depot;
import Backend.entity.Invoice;
import Backend.entity.PostOffice;
import Backend.utilities.InvoiceType;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> getInvoiceById(Long id);
    List<Invoice> getInvoiceList();

    List<Invoice> getInvoiceByPostOffice(PostOffice postOffice);
    List<Invoice> getInvoiceByFirstDepot(Depot depot);
    List<Invoice> getInvoiceBySecondDepot(Depot depot);

    void createInvoice(Invoice invoice, InvoiceType invoiceType);

    void confirmInvoice(Invoice invoice);
}
