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
     private List<ParcelResultDto> parcels;

     public InvoiceDto(Invoice invoice) {
          this.id = invoice.getId();
          this.createdBy = invoice.getCreateBy().getUsername();
          this.parcels = new ArrayList<>();
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
          for(Parcel parcel: invoice.getParcels()) {
               ParcelResultDto parcelResultDto = new ParcelResultDto(parcel);
               switch (invoice.getType()) {
                    case POST_OFFICE_TO_DEPOT, POST_OFFICE_TO_HOME:
                         parcelResultDto.setFromAddress(this.fromProvince + ", " + this.fromDistrict);
                         break;
                    case DEPOT_TO_DEPOT, DEPOT_TO_POST_OFFICE:
                         parcelResultDto.setFromAddress(this.fromProvince);
                         break;
               }
               this.parcels.add(parcelResultDto);
          }
     }
}
