package Backend.dto;

import Backend.entity.Parcel;
import Backend.utilities.ParcelStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingDto {
    private String name;
    private LocalDateTime acceptedTime;
    private LocalDateTime receivedTime;
    private String startPost;
    private String firstDepot;
    private String secondDepot;
    private String endPost;
    private boolean deliverySuccess;
    private int currentStep;

    public TrackingDto() {

    }
    public TrackingDto(Parcel parcel) {
        this.name = parcel.getName();
        this.acceptedTime = parcel.getAcceptedTime();
        this.receivedTime = parcel.getReceivedTime();
        this.startPost = parcel.getStartPostOffice().getDistrict().getName();
        this.firstDepot = parcel.getStartDepot().getProvince().getName();
        this.secondDepot = parcel.getEndDepot().getProvince().getName();
        this.endPost = parcel.getEndPostOffice().getDistrict().getName();
        this.deliverySuccess = (parcel.getStatus().equals(ParcelStatus.SUCCESS));
        switch (parcel.getStatus()) {
            case START_POS:
                this.currentStep = 1;
                break;
            case FIRST_DEPOT:
                this.currentStep = 2;
                break;
            case LAST_DEPOT:
                this.currentStep = 3;
                break;
            case END_POS:
                this.currentStep = 4;
                break;
            default:
                this.currentStep = 5;
        }
    }
}
