package Backend.service;

import Backend.entity.Province;

import java.util.List;

public interface ProvinceService {
    List<Province> getProvinceList();
    void saveProvince(Province province);
    void delete(Province province);
}
