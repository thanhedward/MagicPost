package Backend.service;

import Backend.new_entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile createProfile(Profile profile);

    List<Profile> getAllProfiles();
}
