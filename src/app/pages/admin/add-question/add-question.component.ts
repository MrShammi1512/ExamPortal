import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/services/question.service';

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.css']
})
export class AddQuestionComponent implements OnInit {

  qId : any;
  qTitle : any ;
  constructor( private _route : ActivatedRoute, private quesService : QuestionService) { }

 
  question : any={
    quiz:{
      qId : ''
    },
    content :'' ,
    option1 :'',
    option2 :'',
    option3 :'',
    option4 :'',
    answer : ''

    
}
  ngOnInit(): void {
    this.qId=this._route.snapshot.params['qid'];
    this.qTitle=this._route.snapshot.params['title'];
    this.question.quiz['qId']=this.qId;
    console.log( "qUIZ ID "+this.question.quiz['qId']);
  }

  addQuestions()
  {
    if(this.question.content.trim()==''  || this.question.content== null)
    {
      return ;
    }
    if(this.question.option1.trim()==''  || this.question.option1== null)
    {
      return ;
    }
    if(this.question.option2.trim()==''  || this.question.option3== null)
    {
      return ;
    }
    this.quesService.addQuestion(this.question).subscribe(
      (data)=>
      {
        console.log(data);
      }
    )
  }

}
