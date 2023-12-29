package Backend.service;

import Backend.dto.InvoiceDto;
import Backend.entity.*;
import Backend.repository.InvoiceRepository;
import Backend.utilities.InvoiceType;
import Backend.utilities.ParcelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<InvoiceDto> getInvoiceByEndPostOffice() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
        List<Invoice> invoices = invoiceRepository.findByPostOfficeAndConfirmedAndType(postOffice, false, InvoiceType.DEPOT_TO_POST_OFFICE);
        List<InvoiceDto> result = new ArrayList<>();

        for (Invoice invoice: invoices) {
            result.add(new InvoiceDto(invoice));
        }

        return result;
    }

    @Override
    public List<InvoiceDto> getInvoiceByEndPostOfficeToHome() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
        List<Invoice> invoices = invoiceRepository.findByPostOfficeAndConfirmedAndType(postOffice, false, InvoiceType.POST_OFFICE_TO_HOME);
        List<InvoiceDto> result = new ArrayList<>();

        for (Invoice invoice: invoices) {
            result.add(new InvoiceDto(invoice));
        }

        return result;
    }

    @Override
    public List<InvoiceDto> getInvoiceByFirstDepot() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot depot = currentUser.getDepot();

        List<Invoice> invoices = invoiceRepository.findByFirstDepotAndConfirmedAndType(depot, false, InvoiceType.POST_OFFICE_TO_DEPOT);
        List<InvoiceDto> result = new ArrayList<>();

        for(Invoice invoice: invoices) {
            result.add(new InvoiceDto(invoice));
        }

        return result;
    }

    @Override
    public List<InvoiceDto> getInvoiceBySecondDepot() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        Depot depot = currentUser.getDepot();
        List<Invoice> invoices = invoiceRepository.findBySecondDepotAndConfirmedAndType(depot, false, InvoiceType.DEPOT_TO_DEPOT);
        List<InvoiceDto> result = new ArrayList<>();

        for (Invoice invoice: invoices) {
            result.add(new InvoiceDto(invoice));
        }

        return result;
    }


    @Override
    public Invoice createInvoice(Invoice invoice, InvoiceType invoiceType){
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        invoice.setCreateBy(currentUser);

        //TODO: Check if parcel exist?, send to the same location?
        Set<Parcel> parcels = invoice.getParcels().stream().map(parcel -> parcelService.getParcelById(parcel.getId()).get()).collect(Collectors.toSet());
        invoice.setParcels(parcels);

        switch (invoiceType) {
            case POST_OFFICE_TO_DEPOT: {
                invoice.setType(InvoiceType.POST_OFFICE_TO_DEPOT);
                invoice.setPostOffice(currentUser.getPostOffice());
                invoice.setFirstDepot(currentUser.getPostOffice().getDepot());
                break;
            }
            case DEPOT_TO_DEPOT: {
                invoice.setType(InvoiceType.DEPOT_TO_DEPOT);
                invoice.setFirstDepot(currentUser.getDepot());
                for(Parcel parcel: parcels) {
                    invoice.setSecondDepot(parcel.getEndDepot());
                    break;
                }
                break;
            }
            case DEPOT_TO_POST_OFFICE: {
                invoice.setType(InvoiceType.DEPOT_TO_POST_OFFICE);
                invoice.setFirstDepot(currentUser.getDepot());
                for(Parcel parcel: parcels) {
                    invoice.setPostOffice(parcel.getEndPostOffice());
                    break;
                }
                break;
            }
            case POST_OFFICE_TO_HOME: {
                invoice.setType(InvoiceType.POST_OFFICE_TO_HOME);
                invoice.setPostOffice(currentUser.getPostOffice());
                for(Parcel parcel: parcels) {
                    invoice.setEndAddress(parcel.getEndAddress());
                    break;
                }
            }
        }

        invoiceRepository.save(invoice);
        return invoice;
    }

    @Override
    public InvoiceDto confirmInvoice(Invoice invoice, boolean fail) {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();

        invoice.setConfirmBy(currentUser);
        invoice.setConfirmDate(LocalDateTime.now());
        invoice.setConfirmed(true);

        Set<Parcel> parcels = invoice.getParcels();
        for(Parcel parcel: parcels) {
            Parcel updateParcel = parcelService.getParcelById(parcel.getId()).get();
            switch (parcel.getStatus()) {
                case START_POS: {
                    updateParcel.setStatus(ParcelStatus.FIRST_DEPOT);
                    break;
                }
                case FIRST_DEPOT: {
                    updateParcel.setStatus(ParcelStatus.LAST_DEPOT);
                    break;
                }
                case LAST_DEPOT: {
                    updateParcel.setStatus(ParcelStatus.END_POS);
                    break;
                }
                case END_POS: {
                    if(fail) updateParcel.setStatus(ParcelStatus.FAIL);
                    else {
                        updateParcel.setStatus(ParcelStatus.SUCCESS);
                        updateParcel.setReceivedTime(LocalDateTime.now());
                    }
                }
            }
            parcelService.saveParcel(updateParcel);
        }
        return new InvoiceDto(invoice);
    }
}
