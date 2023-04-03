import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShowimageComponent } from './components/showimage.component';


const routes: Routes = [
  
  {path:'get/:postId', component: ShowimageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }