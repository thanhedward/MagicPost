export class Parcel {
    name: string;
    sender: string;
    startAddress: string;
    endAddress: string;
    weight: number;
    endProvinceName: string;
    endDistrictName: string;
  
    constructor(
      name: string,
      sender: string,
      startAddress: string,
      endAddress: string,
      weight: number,
      endProvinceName: string,
      endDistrictName: string
    ) {
      this.name = name;
      this.sender = sender;
      this.startAddress = startAddress;
      this.endAddress = endAddress;
      this.weight = weight;
      this.endProvinceName = endProvinceName;
      this.endDistrictName = endDistrictName;
    }
  }
  