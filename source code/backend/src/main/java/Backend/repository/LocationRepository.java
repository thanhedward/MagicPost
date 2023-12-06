package Backend.repository;

import Backend.entity.Course;
import Backend.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByName(String s);

    boolean existsById(Long id);

    Page<Location> findAll(Pageable pageable); // Page or List ?

//    @Query(value = "select * from location c join course_intake c_i on c.id = c_i.course_id where c_i.intake_id=:intakeId", nativeQuery = true)
//    List<Course> findAllByIntakeId(Long intakeId);

    List<Location> findAllByType(String type);

//    @Query(value="select * from course c join part p on c.id = p.course_id where p.id=:partId", nativeQuery=true)
//    Course findCourseByPartId(Long partId);


}