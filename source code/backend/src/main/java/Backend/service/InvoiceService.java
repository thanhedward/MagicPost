package Backend.service;

import Backend.dto.InvoiceDto;
import Backend.entity.Invoice;
import Backend.utilities.InvoiceType;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Optional<Invoice> getInvoiceById(Long id);
    List<Invoice> getInvoiceList();

    List<InvoiceDto> getInvoiceByFirstDepot();
    List<InvoiceDto> getInvoiceBySecondDepot();
    List<InvoiceDto> getInvoiceByEndPostOffice();
    List<InvoiceDto> getInvoiceByEndPostOfficeToHome();

    Invoice createInvoice(Invoice invoice, InvoiceType invoiceType);

    void confirmInvoice(Invoice invoice, boolean fail);
}
