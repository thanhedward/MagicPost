package Backend.service;

import Backend.entity.Parcels;
import Backend.repository.ParcelsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelServiceImpl implements ParcelsService{
    Logger logger = LoggerFactory.getLogger(ParcelServiceImpl.class);

    private ParcelsRepository parcelsRepository;

    @Autowired
    public ParcelServiceImpl(ParcelsRepository parcelsRepository){
        this.parcelsRepository = parcelsRepository;
    }

    @Override
    public Optional<Parcels> getParcelById(Long id) {
        return parcelsRepository.findById(id);
    }

    @Override
    public List<Parcels> getParcelList() {
        return parcelsRepository.findAll();
    }

    @Override
    public void saveParcels(Parcels parcel){
        parcelsRepository.save(parcel);
    }

    @Override
    public void delete(Long id){
        parcelsRepository.deleteById(id);
    }

    @Override
    public List<Parcels> getParcelByAcceptedUserUsername(String username) {
        return parcelsRepository.findAllByAcceptedBy_Username(username);
    }
}
