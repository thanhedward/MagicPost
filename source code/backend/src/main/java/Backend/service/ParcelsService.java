package Backend.service;


import Backend.entity.Parcel;

import java.util.List;
import java.util.Optional;


public interface ParcelsService {

    Optional<Parcel> getParcelById(Long id);
    List<Parcel> getParcelList();

    List<Parcel> getParcelByAcceptedUserUsername(String username);
//    List<Parcels> getParcelsByParcelStatus(String parcelStatus);
//    List<Parcels> getParcelByStartPostOfficeId(Long start_post_id);
//    List<Parcels> getParcelByEndPostOfficeId(Long start_post_id);

    void saveParcels(Parcel parcel);

    void delete(Long id);

//    boolean existsByName(String name);

//    boolean existsById(Long id);

}
