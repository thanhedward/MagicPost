package Backend.service;

import Backend.entity.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {

    Optional<Province> getProvinceById(String id);
    List<Province> getProvinceList();
    void saveProvince(Province province);
    void delete(Province province);
}
