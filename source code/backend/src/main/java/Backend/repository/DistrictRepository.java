package Backend.repository;

import Backend.entity.District;
import Backend.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findAllByProvince(Province province);

    District findByProvinceAndName(Province province, String name);

    Boolean existsByProvinceAndName(Province province, String name);
}
