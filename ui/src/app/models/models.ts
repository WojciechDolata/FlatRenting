export class BasicModel {
  id: number;
  creationTimestamp: Date;
}

export class Offer extends BasicModel{
  title: string;
  location: string;
  size: string;
  roomCount: number;
  description: string;
  owner: User;
  photos: number[];
}

export class User extends BasicModel{
  nick: string;
  email: string;
  passwordHash: number;
  phoneNumber: string;
  offers: Offer[];
  conversations: Conversation[];
}

export class Conversation extends BasicModel {
  user1: User;
  user2: User;
  messages: Message[];
}

export class Message extends BasicModel{
  content: string;
  sender: User;
  receiver: User;
}

export class Photo extends BasicModel{
  name: string;
  origin: number;
  data: File;
}
