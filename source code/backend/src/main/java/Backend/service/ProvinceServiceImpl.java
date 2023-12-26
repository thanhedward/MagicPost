package Backend.service;

import Backend.entity.Province;
import Backend.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImpl implements ProvinceService{
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }


    @Override
    public Optional<Province> getProvinceById(String id){
        return provinceRepository.findById(id);
    }
    @Override
    public List<Province> getProvinceList() {
        return provinceRepository.findAll();
    }

}
