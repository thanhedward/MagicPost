package Backend.service;

import Backend.entity.Province;
import Backend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceServiceImpl implements ProvinceService{
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<Province> getProvinceList() {
        return provinceRepository.findAll();
    }

    @Override
    public void saveProvince(Province province) {
        provinceRepository.save(province);
    }

    @Override
    public void delete(Province province) {
        provinceRepository.delete(province);
    }


}
