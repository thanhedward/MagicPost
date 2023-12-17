package Backend.service;


import Backend.entity.Parcels;

import java.util.List;
import java.util.Optional;


public interface ParcelsService {

    Optional<Parcels> getParcelById(Long id);
    List<Parcels> getParcelList();

    List<Parcels> getParcelByAcceptedUserUsername(String username);
//    List<Parcels> getParcelsByParcelStatus(String parcelStatus);
//    List<Parcels> getParcelByStartPostOfficeId(Long start_post_id);
//    List<Parcels> getParcelByEndPostOfficeId(Long start_post_id);

    void saveParcels(Parcels Parcel);

    void delete(Long id);

//    boolean existsByName(String name);

    boolean existsById(Long id);

}
