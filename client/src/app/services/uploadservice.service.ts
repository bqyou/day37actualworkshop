import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Post, Result } from '../models';
import { Observable, firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadserviceService {

  constructor(private httpClient: HttpClient) { }

   uploadImage(file: File, comments: string) {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('comments', comments);

    return firstValueFrom(this.httpClient.post<Result>('/api/post', formData))
  }

  getImage(postId: string) {
    return firstValueFrom(
      this.httpClient.get<Post>('/api/get/' + postId)
    );
  }

  getIds(){
    return firstValueFrom(
      this.httpClient.get<Result[]>('/api/getIds')
    )
  }
}
