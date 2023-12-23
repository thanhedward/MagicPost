package Backend.service;

import Backend.entity.Depot;
import Backend.entity.Province;
import Backend.repository.DepotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepotServiceImpl implements DepotService {

    private final DepotRepository depotRepository;

    @Autowired
    public DepotServiceImpl(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @Override
    public List<Depot> getDepotList() {
        return depotRepository.findAll();
    }

    @Override
    public Optional<Depot> getDepotByID(Long id) {
        return depotRepository.findById(id);
    }

    @Override
    public Optional<Depot> getDepotByProvince(Province province) {
        return depotRepository.findByProvince(province);
    }

    @Override
    public Boolean existsByProvince(Province province) {
        return depotRepository.existsByProvince(province);
    }

    @Override
    public void saveDepot(Depot depot) {
        depotRepository.save(depot);
    }

    @Override
    public void delete(Long id) {
        depotRepository.deleteById(id);
    }
}
