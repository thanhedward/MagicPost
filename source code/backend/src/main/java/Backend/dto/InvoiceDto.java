package Backend.dto;

import Backend.entity.Invoice;
import Backend.entity.Parcel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InvoiceDto {
     private long id;
     private String fromProvince;
     private String fromDistrict;
     private String createdBy;
     private List<ParcelDto> parcels;

     public InvoiceDto(Invoice invoice) {
          this.id = invoice.getId();
          this.createdBy = invoice.getCreateBy().getUsername();
          this.parcels = new ArrayList<>();
          for(Parcel parcel: invoice.getParcels()) {
               ParcelDto parcelDto = new ParcelDto(parcel);
               this.parcels.add(parcelDto);
          }
          switch (invoice.getType()) {
               case POST_OFFICE_TO_DEPOT, POST_OFFICE_TO_HOME:
                    this.fromProvince = invoice.getPostOffice().getDepot().getProvince().getName();
                    this.fromDistrict = invoice.getPostOffice().getDistrict().getName();
                    break;
               case DEPOT_TO_DEPOT:
                    this.fromProvince = invoice.getFirstDepot().getProvince().getName();
                    this.fromDistrict = "";
                    break;
               case DEPOT_TO_POST_OFFICE:
                    this.fromProvince = invoice.getSecondDepot().getProvince().getName();
                    this.fromDistrict = "";
                    break;
          }
     }
}
