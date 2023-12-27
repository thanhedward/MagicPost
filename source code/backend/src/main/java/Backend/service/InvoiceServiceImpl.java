package Backend.service;

import Backend.entity.Invoice;
import Backend.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository){
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id){
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getInvoiceList(){
        return invoiceRepository.findAll();
    }

//    @Override
//    public List<Invoice> getInvoiceByCreateUsername(String username){
//        return invoiceRepository.findAllByCreateByUsername(username);
//    }
//
//    @Override
//    public List<Invoice> getInvoiceByConfirmUsername(String username){
//        return invoiceRepository.findAllByConfirmByUsername(username);
//    }

    @Override
    public void saveInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }
}
