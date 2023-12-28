package Backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceDtoFromPost {
    private Long id;
    private String postOfficeDistrict;
    private String postOfficeProvince;
    private String createdBy;
    private List<ParcelDto> listParcel;
}
