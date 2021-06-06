import {UserDTO} from "./user-dto";

export class StudyGroupDTO {
  id!: number;
  name!: string;
  users!: UserDTO[];
  historicalTimelines!: object;
}
