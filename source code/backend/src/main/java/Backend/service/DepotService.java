package Backend.service;

import Backend.entity.Depot;
import Backend.entity.Province;

import java.util.List;
import java.util.Optional;

public interface DepotService {
    List<Depot> getDepotList();
    Optional<Depot> getDepotByID(Long id);
    Optional<Depot> getDepotByProvince(Province province);
    Boolean existsByProvince(Province province);
    void saveDepot(Depot depot);
    void delete(Long id);
}
