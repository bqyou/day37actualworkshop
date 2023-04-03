import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UploadserviceService } from '../services/uploadservice.service';
import { Result } from '../models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-imageupload',
  templateUrl: './imageupload.component.html',
  styleUrls: ['./imageupload.component.css']
})
export class ImageuploadComponent implements OnInit{

  form!: FormGroup
  blob!: Blob
  key!: string
  selectedFile!: File
  keys!: Result[]


  constructor(private fb: FormBuilder, private uploadService: UploadserviceService,
    private router: Router){
    
  }

  ngOnInit(): void {
    this.form = this.createForm()
    this.uploadService.getIds().then((resultArray: Result[]) =>{
      this.keys = resultArray
    })
      
  }

  createForm():FormGroup{
    return this.fb.group({
      comments: this.fb.control('',[Validators.required]),
      file: this.fb.control('', [Validators.required])
    })
  }

  onFileSelected(event: any){
    const file: File = event.target.files[0]
    this.selectedFile = file
  }

  upload(){
    console.log(this.form.value['comments'])
    console.log(this.selectedFile)
    const key = this.uploadService.uploadImage(this.selectedFile, this.form.value['comments']).then(
      (result:Result)=>{
        this.key = result.postId
      }
    )
    alert("Your upload was successful")
    console.log(this.key)
    this.uploadService.getIds().then((resultArray: Result[]) =>{
      this.keys = resultArray
    })
    this.form.reset()
  }


  isFormInvalid():boolean{
    return this.form.invalid
  }
}
