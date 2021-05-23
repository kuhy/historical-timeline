import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {handleError} from "../script/error";
import {catchError} from "rxjs/operators";
import {Observable} from "rxjs";
import {StudyGroupDTO} from "../dto/study-group-dto";
import {UserDTO} from "../dto/user-dto";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  apiURL = `/pa165/rest/users`;

  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.get(`${this.apiURL}/login/${username}/${password}`).pipe(catchError(error => {
      return handleError(error,"Login unsuccessful!");
    }));
  }

  getLoggedInUser(): Observable<any> {
    return this.http.get(`${this.apiURL}/logged_in_user`).pipe(catchError(error => {
      return handleError(error);
    }));
  }

  isTeacher(): Observable<any> {
    return this.http.get(`${this.apiURL}/is_teacher`)
  }

  isUserLoggedIn(): Observable<any> {
    return this.http.get(`${this.apiURL}/is_logged_in`).pipe(catchError(error => {
      return handleError(error)
    }))
  }

  logout() {
    return this.http.get(`${this.apiURL}/logout`).pipe(catchError(error => {
      return handleError(error,"Logout unsuccessful!");
    }));
  }

  getAllUsers(): Observable<any>{
    return this.http.get(`${this.apiURL}`).pipe(catchError(error => {
      return handleError(error)
    }))
  }

  deleteUser(id:number): Observable<any>{
    return this.http.delete(`${this.apiURL}/${id}`).pipe(catchError(error => {
      return handleError(error)
    }))
  }

  updateUser(updateUserDTO: UserDTO): Observable<any> {
    return this.http.put(`${this.apiURL}/${updateUserDTO.id}`, updateUserDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }

  createUser(createUserDTO: UserDTO, unencryptedPassword:String): Observable<any> {
    return this.http.post(`${this.apiURL}/register/${unencryptedPassword}`, createUserDTO).pipe(catchError(error => {
      return handleError(error)
    }));
  }
}
