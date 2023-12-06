package Backend.service;

import Backend.entity.Location;
import Backend.repository.LocationRepository;
import Backend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public List<Location> getLocationList() {
        return locationRepository.findAll();
    }

    @Override
    public Page<Location> getLocationListByPage(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    @Override
    public void saveLocation(Location Location) {
        locationRepository.save(Location);
    }

    @Override
    public void delete(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return  locationRepository.existsByName(name);
    }

    @Override
    public boolean existsById(Long id) {
        return locationRepository.existsById(id);
    }


    @Override
    public List<Location> getLocationByType(String type) {
        return locationRepository.findAllByType(type);
    }




}
