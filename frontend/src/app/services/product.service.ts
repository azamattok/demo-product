import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {apiUrl} from '../../environments/environment';
import {Product} from '../models/Product';

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    private productUrl = `${apiUrl}/products`;


    constructor(private http: HttpClient) {
    }

    getAllInPage(page: number, size: number): Observable<any> {
        const url = `${this.productUrl + '/all'}?page=${page}&size=${size}`;
        return this.http.get(url)
            .pipe(
                // tap(_ => console.log(_)),
            )
    }


    getDetail(id: number): Observable<Product> {
        const url = `${this.productUrl}/${id}`;
        return this.http.get<Product>(url).pipe(
            catchError(_ => {
                console.log("Get Detail Failed");
                return of(new Product());
            })
        );
    }

    update(productInfo: Product): Observable<Product> {
        const url = `${this.productUrl}/${productInfo.uid}`;
        return this.http.post<Product>(url, productInfo);
    }

  addPhoto(id: number, photoUUid: string): Observable<Product> {
    const url = `${this.productUrl + '/addPhoto'}/${id}`;
    return this.http.post<Product>(url, photoUUid);
  }

    create(productInfo: Product): Observable<Product> {
        const url = `${this.productUrl}`;
        return this.http.post<Product>(url, productInfo);
    }


    delelte(productInfo: Product): Observable<any> {
        const url = `${this.productUrl}/${productInfo}`;
        return this.http.delete(url);
    }


    /**
     * Handle Http operation that failed.
     * Let the app continue.
     * @param operation - name of the operation that failed
     * @param result - optional value to return as the observable result
     */
    private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {

            console.error(error); // log to console instead

            // Let the app keep running by returning an empty result.
            return of(result as T);
        };
    }
}
