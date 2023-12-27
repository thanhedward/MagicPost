package Backend.dto;

import lombok.Data;

@Data
public class ParcelDto {
    private String name;
    private String sender;
    private String startAddress;
    private String endAddress;
    private int weight;
    private String endProvinceName;
    private String endDistrictName;
}
