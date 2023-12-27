package Backend.service;


import Backend.entity.Depot;
import Backend.entity.Parcel;
import Backend.entity.PostOffice;

import java.util.List;
import java.util.Optional;


public interface ParcelService {

    Optional<Parcel> getParcelById(Long id);
    List<Parcel> getParcelList();
    List<Parcel> getParcelListByPostOfficeToDepot(PostOffice postOffice, Depot depot);
    List<Parcel> getParcelListByDepotToDepot(Depot startDepot);
    List<Parcel> getParcelListByDepotToPostOffice(Depot endDepot);
    List<Parcel> getParcelListByPostOfficeToHome(PostOffice postOffice);

    void saveParcels(Parcel parcel);

    void delete(Long id);


}
