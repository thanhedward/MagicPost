package Backend.repository;

import Backend.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByCreateByUsername(String username);
    List<Invoice> findAllByConfirmByUsername(String username);
}