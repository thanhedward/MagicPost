package Backend.service;

import Backend.dto.ParcelDto;
import Backend.dto.ParcelResultDto;
import Backend.entity.*;
import Backend.repository.ParcelsRepository;
import Backend.utilities.InvoiceType;
import Backend.utilities.ParcelStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParcelServiceImpl implements ParcelService {
    Logger logger = LoggerFactory.getLogger(ParcelServiceImpl.class);

    private final ParcelsRepository parcelsRepository;
    private final UserService userService;

    @Autowired
    public ParcelServiceImpl(ParcelsRepository parcelsRepository, UserService userService){
        this.parcelsRepository = parcelsRepository;
        this.userService = userService;
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
    public List<ParcelResultDto> getParcelListByPostOfficeToDepot(PostOffice postOffice, Depot depot) {
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

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(postOffice.getDepot().getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<ParcelResultDto> getParcelListByDepotToDepot(Depot startDepot, String provinceName) {
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

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            if(!parcel.getEndDepot().getProvince().getName().equals(provinceName)) {
                continue;
            }
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(parcel.getEndDepot().getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<ParcelResultDto> getParcelListByDepotToPostOffice(Depot endDepot, String districtName) {
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndEndDepotOrderByEndPostOfficeAsc(ParcelStatus.LAST_DEPOT, endDepot);

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

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            if(!parcel.getEndPostOffice().getDistrict().getName().equals(districtName)) {
                continue;
            }
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(parcel.getEndPostOffice().getDistrict().getName() + ", " + endDepot.getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<ParcelResultDto> getParcelListByPostOfficeToHome(PostOffice postOffice) {
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

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(parcel.getEndAddress() + ", " + parcel.getEndPostOffice().getDistrict().getName() + ", " + parcel.getEndDepot().getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<ParcelResultDto> getParcelListSucceedByPostOffice() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndEndPostOffice(ParcelStatus.SUCCESS, postOffice);

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(parcel.getStartAddress() + ", " +
                    parcel.getStartPostOffice().getDistrict().getName() + ", " +
                    parcel.getStartDepot().getProvince().getName());
            temp.setToAddress(parcel.getEndAddress() + ", " +
                    parcel.getEndPostOffice().getDistrict().getName() + ", " +
                    parcel.getEndDepot().getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<ParcelResultDto> getParcelListFailedByPostOffice() {
        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        PostOffice postOffice = currentUser.getPostOffice();
        List<Parcel> parcels = parcelsRepository.findAllByStatusAndEndPostOffice(ParcelStatus.FAIL, postOffice);

        List<ParcelResultDto> parcelResults = new ArrayList<>();
        for(Parcel parcel: parcels) {
            ParcelResultDto temp = new ParcelResultDto(parcel);
            temp.setToAddress(parcel.getStartAddress() + ", " +
                    parcel.getStartPostOffice().getDistrict().getName() + ", " +
                    parcel.getStartDepot().getProvince().getName());
            temp.setToAddress(parcel.getEndAddress() + ", " +
                    parcel.getEndPostOffice().getDistrict().getName() + ", " +
                    parcel.getEndDepot().getProvince().getName());
            parcelResults.add(temp);
        }
        return parcelResults;
    }

    @Override
    public List<Parcel> getAllParcelSucceed() {
        return parcelsRepository.findAllByStatus(ParcelStatus.SUCCESS);
    }

    @Override
    public List<Parcel> getAllParcelFailed() {
        return parcelsRepository.findAllByStatus(ParcelStatus.FAIL);
    }


    @Override
    public Parcel createParcels(ParcelDto parcelDto, PostOffice endPostOffice){
        Parcel parcel = new Parcel(parcelDto);

        User currentUser = userService.getUserByUsername(userService.getUserName()).get();
        parcel.setAcceptedBy(currentUser);
        parcel.setStatus(ParcelStatus.START_POS);
        parcel.setStartPostOffice(currentUser.getPostOffice());
        parcel.setStartDepot(currentUser.getPostOffice().getDepot());
        parcel.setEndPostOffice(endPostOffice);
        parcel.setEndDepot(endPostOffice.getDepot());
        if(parcel.getStartPostOffice().equals(parcel.getEndPostOffice()))
            parcel.setStatus(ParcelStatus.END_POS);

        parcelsRepository.save(parcel);
        return parcel;
    }

    @Override
    public void saveParcel(Parcel parcel) {
        parcelsRepository.save(parcel);
    }

    @Override
    public void delete(Long id){
        parcelsRepository.deleteById(id);
    }

}
