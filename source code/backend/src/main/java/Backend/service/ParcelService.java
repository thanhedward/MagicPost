package Backend.service;

import Backend.entity.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ParcelService {
    Optional<Parcel> getParcelById(Long id);

    List<Parcel> getParcelList();

    Page<Parcel> getParcelListByPage(Pageable pageable);

    void saveParcel(Parcel Parcel);

    void delete(Long id);

//    boolean existsByName(String name);

    boolean existsById(Long id);

    public List<Parcel> getParcelListByStartLocationId(Long start_location_id);

    public List<Parcel> getParcelListByEndLocationId(Long start_location_id);

    public Page<Parcel> findAllByCreatedBy_Username(Pageable pageable, String username);

    List<Parcel> findAllBySenderId(Long senderId);


//    List<Parcel> getParcelByType (String type);

//    public Page<Parcel> getParcelByTypePage (String type, Pageable pageable);
}

