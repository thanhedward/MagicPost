package Backend.service;

import Backend.entity.*;
import Backend.repository.InvoiceRepository;
import Backend.utilities.InvoiceType;
import Backend.utilities.ParcelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final ParcelService parcelService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserService userService, ParcelService parcelService){
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
        this.parcelService = parcelService;
    }

    @Override
    public Optional<Invoice> getInvoiceById(Long id){
        return invoiceRepository.findById(id);
    }

    @Override
    public List<Invoice> getInvoiceList(){
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> getInvoiceByPostOffice(PostOffice postOffice) {
        return invoiceRepository.findByPostOfficeAndConfirmed(postOffice, false);
    }

    @Override
    public List<Invoice> getInvoiceByFirstDepot(Depot depot) {
        return invoiceRepository.findByFirstDepotAndConfirmed(depot, false);
    }

    @Override
    public List<Invoice> getInvoiceBySecondDepot(Depot depot) {
        return invoiceRepository.findBySecondDepotAndConfirmed(depot, false);
    }


    @Override
    public void createInvoice(Invoice invoice, InvoiceType invoiceType){
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();
        invoice.setCreateBy(user);

        //TODO: Check if parcel exist?, send to the same location?
        Set<Parcel> parcels = invoice.getParcels().stream().map(parcel -> parcelService.getParcelById(parcel.getId()).get()).collect(Collectors.toSet());
        invoice.setParcels(parcels);

        switch (invoiceType) {
            case POST_OFFICE_TO_DEPOT: {
                invoice.setType(InvoiceType.POST_OFFICE_TO_DEPOT);
                invoice.setPostOffice(user.getPostOffice());
                invoice.setFirstDepot(user.getPostOffice().getDepot());
                break;
            }
            case DEPOT_TO_DEPOT: {
                invoice.setType(InvoiceType.DEPOT_TO_DEPOT);
                invoice.setFirstDepot(user.getDepot());
                for(Parcel parcel: parcels) {
                    invoice.setSecondDepot(parcel.getEndDepot());
                    break;
                }
                break;
            }
            case DEPOT_TO_POST_OFFICE: {
                invoice.setType(InvoiceType.DEPOT_TO_POST_OFFICE);
                invoice.setFirstDepot(user.getDepot());
                for(Parcel parcel: parcels) {
                    invoice.setPostOffice(parcel.getEndPostOffice());
                    break;
                }
                break;
            }
            case POST_OFFICE_TO_HOME: {
                invoice.setType(InvoiceType.POST_OFFICE_TO_HOME);
                invoice.setPostOffice(user.getPostOffice());
                for(Parcel parcel: parcels) {
                    invoice.setEndAddress(parcel.getEndAddress());
                    break;
                }
            }
        }

        invoiceRepository.save(invoice);
    }

    @Override
    public void confirmInvoice(Invoice invoice) {
        String username = userService.getUserName();
        User user = userService.getUserByUsername(username).get();

        invoice.setConfirmBy(user);
        invoice.setConfirmDate(LocalDateTime.now());
        invoice.setConfirmed(true);

        Set<Parcel> parcels = invoice.getParcels();
        for(Parcel parcel: parcels) {
            switch (parcel.getStatus()) {
                case START_POS: {
                    parcel.setStatus(ParcelStatus.FIRST_DEPOT);
                    break;
                }
                case FIRST_DEPOT: {
                    parcel.setStatus(ParcelStatus.LAST_DEPOT);
                    break;
                }
                case LAST_DEPOT: {
                    parcel.setStatus(ParcelStatus.END_POS);
                    break;
                }
                case END_POS: {
                    parcel.setStatus(ParcelStatus.SUCCESS);
                }
            }
        }
    }


}
