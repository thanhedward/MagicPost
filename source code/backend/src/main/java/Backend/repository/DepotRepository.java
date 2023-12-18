package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Long> {
    Optional<Depot> findByProvince(Province province);

    Boolean existsByProvince(Province province);
}
