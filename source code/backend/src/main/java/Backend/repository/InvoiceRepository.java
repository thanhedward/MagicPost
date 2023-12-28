package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.Invoice;
import Backend.entity.PostOffice;
import Backend.utilities.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByFirstDepotAndConfirmedAndType(Depot depot, Boolean confirmed, InvoiceType invoiceType);
    List<Invoice> findBySecondDepotAndConfirmedAndType(Depot depot, Boolean confirmed, InvoiceType invoiceType);
    List<Invoice> findByPostOfficeAndConfirmedAndType(PostOffice postOffice, Boolean confirmed, InvoiceType invoiceType);

}
