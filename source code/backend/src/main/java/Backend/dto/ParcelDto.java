package Backend.dto;

import Backend.entity.Parcel;
import lombok.Data;

@Data
public class ParcelDto {
    private Long id;
    private String name;
    private String sender;
    private String startAddress;
    private String endAddress;
    private int weight;
    private String endProvinceName;
    private String endDistrictName;

    public ParcelDto(Parcel parcel) {
        this.id = parcel.getId();
        this.name = parcel.getName();
        this.sender = parcel.getSender();
        this.startAddress = parcel.getStartAddress();
        this.endAddress = parcel.getEndAddress();
        this.weight = parcel.getWeight();
        this.endProvinceName = parcel.getEndDepot().getProvince().getName();
        this.endDistrictName = parcel.getEndPostOffice().getDistrict().getName();
    }
}
