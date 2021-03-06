export class BasicModel {
  id: number;
  creationTimestamp: Date;
}

export class Offer extends BasicModel{
  title: string;
  location: string;
  size: number;
  price: number;
  roomCount: number;
  visitCount: number = 0;
  description: string;
  locationX: number;
  locationY: number;
  owner: User;
  photos: Photo[];
}

export class User extends BasicModel{
  nick: string;
  email: string;
  passwordHash: string;
  phoneNumber: string;
  offers: Offer[];
  conversations: Conversation[];
}

export class Conversation extends BasicModel {
  user: User;
  offer: Offer;
  messages: Message[];
}

export class Message extends BasicModel{
  content: string;
  wasReadByOfferOwner: boolean = false;
  wasReadBySecondUser: boolean = false;
  sender: User;
  receiver: User;
}

export class Photo extends BasicModel{
  name: string;
  origin: Offer;
  data: Blob;
}

export class UserPreferences extends BasicModel{
  nick: string;
  maxPrice: number;
  minPrice: number;
  location: string;
  minNumberOfRooms: number;
  maxNumberOfRooms: number;
  minSize: number;
  maxSize: number;
  maxDaysAgo: number;
}

export class Marker {
  x: number;
  y: number;
  title: string;
}
