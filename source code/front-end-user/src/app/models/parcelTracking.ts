export class ParcelTracking {
    name: string;
    time: Date;
    startPost: string;
    firstDepot: string;
    secondDepot: string;
    endPost: string;
    deliverySuccess: boolean;
    currentStep: number;
    constructor(
        name: string,
        time: Date,
        startPost: string,
        firstDepot: string,
        secondDepot: string,
        endPost: string,
        deliverySuccess: boolean,
        currentStep: number
    ) {
      this.name = name;
      this.time = time;
      this.startPost = startPost;
      this.firstDepot = firstDepot;
      this.secondDepot = secondDepot;
      this.endPost = endPost;
      this.deliverySuccess = deliverySuccess;
      this.currentStep = currentStep;
    }
  }
  