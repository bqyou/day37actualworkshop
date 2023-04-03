import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UploadserviceService } from '../services/uploadservice.service';
import { Subscription } from 'rxjs';
import { Post } from '../models';

@Component({
  selector: 'app-showimage',
  templateUrl: './showimage.component.html',
  styleUrls: ['./showimage.component.css']
})
export class ShowimageComponent implements OnInit, OnDestroy {

  param$!: Subscription
  postId=""
  post!: Post

  constructor(private activatedRoute: ActivatedRoute,
    private uploadService: UploadserviceService){}

  ngOnInit(): void {
    this.param$=this.activatedRoute.params.subscribe(
      async (params)=>{
        this.postId=params['postId']
        
        const photo = await this.uploadService.getImage(this.postId)
        this.post= photo
      })
  }

  ngOnDestroy(): void {
      this.param$.unsubscribe()
  }

}