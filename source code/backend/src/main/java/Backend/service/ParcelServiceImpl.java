package Backend.service;

import Backend.entity.Depot;
import Backend.entity.Invoice;
import Backend.entity.Parcel;
import Backend.entity.PostOffice;
import Backend.repository.ParcelsRepository;
import Backend.utilities.InvoiceType;
import Backend.utilities.ParcelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ParcelServiceImpl implements ParcelService {
    Logger logger = LoggerFactory.getLogger(ParcelServiceImpl.class);

    private final ParcelsRepository parcelsRepository;

    @Autowired
    public ParcelServiceImpl(ParcelsRepository parcelsRepository){
        this.parcelsRepository = parcelsRepository;
    }

    @Override
    public Optional<Parcel> getParcelById(Long id) {
        return parcelsRepository.findById(id);
    }

    @Override
    public List<Parcel> getParcelList() {
        return parcelsRepository.findAll();
    }

    @Override
    public List<Parcel> getParcelListByPostOfficeToDepot(PostOffice postOffice, Depot depot) {
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndStartPostOffice(ParcelStatus.START_POS, postOffice);

        Iterator<Parcel> iterator = parcels.iterator();
        while(iterator.hasNext()) {
            Parcel parcel = iterator.next();
            Set<Invoice> invoices = parcel.getInvoices();
            if(invoices.isEmpty()) continue;
            for(Invoice invoice: invoices) {
                if(invoice.getType().equals(InvoiceType.POST_OFFICE_TO_DEPOT)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return parcels;
    }

    @Override
    public List<Parcel> getParcelListByDepotToDepot(Depot startDepot) {
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndStartDepotOrderByEndDepotAsc(ParcelStatus.FIRST_DEPOT, startDepot);

        Iterator<Parcel> iterator = parcels.iterator();
        while(iterator.hasNext()) {
            Parcel parcel = iterator.next();
            Set<Invoice> invoices = parcel.getInvoices();
            for(Invoice invoice: invoices) {
                if(invoice.getType().equals(InvoiceType.DEPOT_TO_DEPOT)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return parcels;
    }

    @Override
    public List<Parcel> getParcelListByDepotToPostOffice(Depot endDepot) {
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndStartDepotOrderByEndPostOfficeAsc(ParcelStatus.LAST_DEPOT, endDepot);

        Iterator<Parcel> iterator = parcels.iterator();
        while(iterator.hasNext()) {
            Parcel parcel = iterator.next();
            Set<Invoice> invoices = parcel.getInvoices();
            for(Invoice invoice: invoices) {
                if(invoice.getType().equals(InvoiceType.DEPOT_TO_POST_OFFICE)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return parcels;
    }

    @Override
    public List<Parcel> getParcelListByPostOfficeToHome(PostOffice postOffice) {
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndEndPostOffice(ParcelStatus.END_POS, postOffice);

        Iterator<Parcel> iterator = parcels.iterator();
        while(iterator.hasNext()) {
            Parcel parcel = iterator.next();
            Set<Invoice> invoices = parcel.getInvoices();
            for(Invoice invoice: invoices) {
                if(invoice.getType().equals(InvoiceType.POST_OFFICE_TO_HOME)) {
                    iterator.remove();
                    break;
                }
            }
        }
        return parcels;
    }

    @Override
    public void saveParcels(Parcel parcel){
        parcelsRepository.save(parcel);
    }

    @Override
    public void delete(Long id){
        parcelsRepository.deleteById(id);
    }

}
