package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.Invoice;
import Backend.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByFirstDepotAndConfirmed(Depot depot, Boolean confirmed);
    List<Invoice> findBySecondDepotAndConfirmed(Depot depot, Boolean confirmed);
    List<Invoice> findByPostOfficeAndConfirmed(PostOffice postOffice, Boolean confirmed);

}
