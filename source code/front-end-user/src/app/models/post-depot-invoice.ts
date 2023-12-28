import { sendParcel } from "./send-parcel"

export class postDepotInvoice {
    parcels: sendParcel[];
    constructor(parcels: sendParcel[]) {
      this.parcels = parcels;
    }
  }