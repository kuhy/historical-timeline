import {UserDTO} from "./user-dto";

export class TimelineCommentDTO {
  id!: number;
  text!: string;
  user!: UserDTO;
}
