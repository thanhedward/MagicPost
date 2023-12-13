package Backend.repository;

import Backend.entity.Parcel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    List<Parcel> findAllBySenderId(Long senderId);

    @Query(value = "SELECT * FROM parcel p WHERE p.start_location_id=:startLocationId", nativeQuery=true)
    List<Parcel> findParcelsByStart_Location_Id(@Param("startLocationId") Long startLocationId);
    List<Parcel> findAllByEndLocationId(Long endLocationId);
    List<Parcel> findBySenderIdOrderByCreatedDateDesc(Long senderId);
    public Page<Parcel> findAll(Pageable pageable);
    public Page<Parcel> findAllByCreatedBy_Username(Pageable pageable, String username);
    @Transactional
    @Modifying
    @Query(value = "UPDATE exam set exam.canceled=true where exam.id=?" , nativeQuery = true)
    void cancelExam(Long id);

}