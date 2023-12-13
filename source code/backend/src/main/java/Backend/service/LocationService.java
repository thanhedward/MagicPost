package Backend.service;

import Backend.entity.Course;
import Backend.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface LocationService {
    Optional<Location> getLocationById(Long id);

    List<Location> getLocationList();

    Page<Location> getLocationListByPage(Pageable pageable);

    void saveLocation(Location location);

    void delete(Long id);

    // TODO: Validation location name
    boolean existsByName(String name);

    boolean existsById(Long id);

//    List<Location> getLocationByType (String type);

    public Page<Location> getLocationByTypePage (String type, Pageable pageable);
}

