import { Depot } from "./depot";

export class PostOffice {
    district: string;
    depot: Depot;
    constructor(district: string, depot: Depot) {
      this.district = district;
      this.depot = depot;
    }
  }
  