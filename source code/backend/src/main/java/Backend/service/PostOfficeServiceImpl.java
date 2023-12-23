package Backend.service;

import Backend.entity.Depot;
import Backend.entity.District;
import Backend.entity.PostOffice;
import Backend.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostOfficeServiceImpl implements PostOfficeService {

    private final PostOfficeRepository postOfficeRepository;

    @Autowired
    public PostOfficeServiceImpl(PostOfficeRepository postOfficeRepository) {
        this.postOfficeRepository = postOfficeRepository;
    }

    @Override
    public List<PostOffice> getPostOfficeList() {
        return postOfficeRepository.findAll();
    }

    @Override
    public Optional<PostOffice> getPostOfficeByID(Long id) {
        return postOfficeRepository.findById(id);
    }

    @Override
    public List<PostOffice> getPostOfficeListByDepot(Depot depot) {
        return postOfficeRepository.findAllByDepot(depot);
    }

    @Override
    public Optional<PostOffice> getPostOfficeByDepotAndDistrict(Depot depot, District district) {
        return postOfficeRepository.findByDepotAndDistrict(depot, district);
    }

    @Override
    public Boolean existsByDepotAndDistrict(Depot depot, District district) {
        return postOfficeRepository.existsByDepotAndDistrict(depot, district);
    }

    @Override
    public void savePostOffice(PostOffice postOffice) {
        postOfficeRepository.save(postOffice);
    }

    @Override
    public void delete(Long id) {
        postOfficeRepository.deleteById(id);
    }
}
