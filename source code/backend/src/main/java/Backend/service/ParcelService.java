package Backend.service;


import Backend.dto.ParcelDto;
import Backend.dto.ParcelResultDto;
import Backend.entity.Depot;
import Backend.entity.Parcel;
import Backend.entity.PostOffice;

import java.util.List;
import java.util.Optional;


public interface ParcelService {

    Optional<Parcel> getParcelById(Long id);
    List<Parcel> getParcelList();
    List<ParcelResultDto> getParcelListByPostOfficeToDepot(PostOffice postOffice, Depot depot);
    List<ParcelResultDto> getParcelListByDepotToDepot(Depot startDepot, String provinceName);
    List<ParcelResultDto> getParcelListByDepotToPostOffice(Depot endDepot, String districtName);
    List<ParcelResultDto> getParcelListByPostOfficeToHome(PostOffice postOffice);
    List<ParcelResultDto> getParcelListSucceedByPostOffice();
    List<ParcelResultDto> getParcelListFailedByPostOffice();

    List<Parcel> getAllParcelSucceed();
    List<Parcel> getAllParcelFailed();

    Parcel createParcels(ParcelDto parcelDto, PostOffice endPostOffice);
    void saveParcel(Parcel parcel);

    void delete(Long id);


}
