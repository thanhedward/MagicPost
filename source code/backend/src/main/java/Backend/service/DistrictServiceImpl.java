package Backend.service;

import Backend.entity.District;
import Backend.entity.Province;
import Backend.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService{
    private final DistrictRepository districtRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    @Override
    public List<District> getDistrictList() {
        return districtRepository.findAll();
    }

    @Override
    public Optional<District> getDistrictById(Long id) {
        return districtRepository.findById(id);
    }

    @Override
    public List<District> getDistrictListByProvince(Province province) {
        return districtRepository.findAllByProvince(province);
    }

    @Override
    public District getDistrictByProvinceAndName(Province province, String name) {
        return districtRepository.findByProvinceAndName(province, name);
    }

    @Override
    public Boolean existsById(Long id) {
        return districtRepository.existsById(id);
    }

    @Override
    public Boolean existsByProvinceAndName(Province province, String name) {
        return districtRepository.existsByProvinceAndName(province, name);
    }

}
