package Backend.service;

import Backend.entity.District;
import Backend.entity.Province;

import java.util.List;
import java.util.Optional;

public interface DistrictService {
    List<District> getDistrictList();
    Optional<District> getDistrictById(Long id);
    List<District> getDistrictListByProvince(Province province);
    District getDistrictByProvinceAndName(Province province, String name);
    Boolean existsById(Long id);
    Boolean existsByProvinceAndName(Province province, String name);
    void saveDistrict(District district);
    void delete(District district);
}
