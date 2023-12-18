package Backend.dto;

import Backend.new_entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {
    private String username;
    private String email;
    private String password;
    private Profile profile;
}
