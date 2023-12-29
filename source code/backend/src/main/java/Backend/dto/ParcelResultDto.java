package Backend.dto;

import Backend.entity.Parcel;
import lombok.Data;

@Data
public class ParcelResultDto {
    private Long id;
    private String name;
    private String sender;
    private int weight;
    private String fromAddress;
    private String toAddress;

    public ParcelResultDto(Parcel parcel) {
        this.id = parcel.getId();
        this.name = parcel.getName();
        this.sender = parcel.getSender();
        this.weight = parcel.getWeight();
    }
}
