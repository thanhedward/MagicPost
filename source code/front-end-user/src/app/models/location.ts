export class Location {
    id: number;
    name: string;
    type: string;

    constructor(name: string, type: string) {
        this.name = name;
        this.type = type;
      }
}