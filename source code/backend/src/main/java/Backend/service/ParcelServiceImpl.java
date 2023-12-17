package Backend.service;

import Backend.entity.Parcel;
import Backend.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ParcelServiceImpl implements ParcelService {
    private ParcelRepository parcelRepository;

    @Autowired
    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }


    @Override
    public Optional<Parcel> getParcelById(Long id) {
        return parcelRepository.findById(id);
    }

    @Override
    public List<Parcel> getParcelList() {
        return parcelRepository.findAll();
    }

    @Override
    public List<Parcel> getParcelListByStartLocationId(Long start_location_id) {
        return parcelRepository.findParcelsByStart_Location_Id(start_location_id);
    }

    @Override
    public List<Parcel> getParcelListByEndLocationId(Long end_location_id) {
        return parcelRepository.findAllByEndLocationId(end_location_id);
    }

    @Override
    public Page<Parcel> getParcelListByPage(Pageable pageable) {
        return parcelRepository.findAll(pageable);
    }

    @Override
    public void saveParcel(Parcel Parcel) {
        parcelRepository.save(Parcel);
    }

    @Override
    public void delete(Long id) {
        parcelRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return parcelRepository.existsById(id);
    }

    public Page<Parcel> findAllByCreatedBy_Username(Pageable pageable, String employee_username){
        return parcelRepository.findAllByCreatedBy_Username(pageable, employee_username);
    }

    public List<Parcel> findAllBySenderId(Long senderId){
        return parcelRepository.findAllBySenderId(senderId);
    }

//    @Override
//    public Page<Parcel> getParcelByTypePage (String type, Pageable pageable){
//        return parcel
//    }

}
