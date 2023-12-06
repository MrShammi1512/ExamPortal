import { Component, OnInit } from '@angular/core';
import { CategoryService } from 'src/services/category.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-categories',
  templateUrl: './view-categories.component.html',
  styleUrls: ['./view-categories.component.css']
})
export class ViewCategoriesComponent implements OnInit {

   categories:any = [
    
   ]
   
  constructor( private categoryService : CategoryService) { }

  ngOnInit(): void {
    this.categoryService.getAllCategory().subscribe(
      (data : any)=>
      {
        this.categories=data;
      },
      (error: any)=>
      {
        console.log(error);
        Swal.fire('Error !!','Error in loading the data !','error');
      }
    );
  }
 
  

}
