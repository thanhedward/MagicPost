package Backend.repository;

import Backend.entity.Depot;
import Backend.entity.District;
import Backend.entity.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
    List<PostOffice> findAllByDepot(Depot depot);

    Optional<PostOffice> findByDepotAndDistrict(Depot depot, District district);

    Boolean existsByDepotAndDistrict(Depot depot, District district);
}
