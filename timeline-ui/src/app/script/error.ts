import {Observable, throwError} from "rxjs";

export function handleError(error: any, customMessage: string | null = null): Observable<never> {
  if (customMessage != null) {
    window.alert(customMessage);
    return throwError(customMessage);
  }

  let errorMessage = '';
  if (error.error instanceof ErrorEvent) {
    // Get client-side error
    errorMessage = error.error.message;
  } else {
    // Get server-side error
    errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
  }
  window.alert(errorMessage);
  return throwError(errorMessage);
}
