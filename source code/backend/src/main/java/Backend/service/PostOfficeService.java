package Backend.service;

import Backend.entity.Depot;
import Backend.entity.District;
import Backend.entity.PostOffice;

import java.util.List;
import java.util.Optional;

public interface PostOfficeService {
    List<PostOffice> getPostOfficeList();
    Optional<PostOffice> getPostOfficeByID(Long id);
    List<PostOffice> getPostOfficeListByDepot(Depot depot);
    Optional<PostOffice> getPostOfficeByDepotAndDistrict(Depot depot, District district);
    Boolean existsByDepotAndDistrict(Depot depot, District district);
    void savePostOffice(PostOffice postOffice);
    void delete(Long id);
}
