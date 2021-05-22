export class UserUpdateDto {
  id!: number;
  username!: string;
  firstName!: string;
  lastName!: string;
  isTeacher!: boolean;
  hashedPassword!: string
}
