import { LocationStrategy } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { QuestionService } from 'src/services/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-start-quiz',
  templateUrl: './start-quiz.component.html',
  styleUrls: ['./start-quiz.component.css']
})
export class StartQuizComponent implements OnInit {

  qId : any;
  question : any;
  marksGot : any;
  correctAnswer=0;
  attempted=0;
  isSubmit =false;
  timer :any;

  constructor(private _locationSt : LocationStrategy, private _route : ActivatedRoute, private _quesService : QuestionService) { }

  ngOnInit(): void {
    this.preventBackButton();
    this.qId=this._route.snapshot.params['qid'];
    this.loadQuestionsOfQuiz();
    // console.log(this.qId);
    
  }


   loadQuestionsOfQuiz(){
    this._quesService.getQuestionsOfQuizForTest(this.qId).subscribe(
      (data : any)=>{
        this.question=data;
        this.timer=this.question.length*2*60; // Here we assume that each question takes 2 minutes and we take time as sec so multiply by 60
        this.question.forEach((q:any) => {
         
          this.startTimer();
        });
        console.log(this.question)
      },
      (error)=>
      {
        Swal.fire('Error','Error in loading the Quetions','error');
        console.log("Error in fetching the question");
      }
      )
   }
  preventBackButton(){
    history.pushState(null,location.href);
    this._locationSt.onPopState(()=>{
      history.pushState(null,location.href);
    })

  }

  submitQuiz(){
    Swal.fire({
      title: "Do you want to submit the quiz ?",
      showCancelButton: true,
      confirmButtonText: "Submit",
      icon:'info'
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        this.evaluateQuiz();
       
      } else if (result.isDenied) {
        Swal.fire("Changes are not saved", "", "info");
      }
    });
  }

  startTimer(){
    let t =window.setInterval(()=>{
      if(this.timer <=0){
        this.evaluateQuiz();
        clearInterval(t);
      }
      else{
        this.timer=this.timer-1;
      }
    },1000)
  }

  formattedTimer(){
    let mm=Math.floor(this.timer/60);
    let ss=this.timer-mm*60;
    return `${mm} min : ${ss} sec`
  }


  evaluateQuiz(){
    // Calculating the marks of quiz

    // Call the server to check the questions and correct answer
      this._quesService.evaluateQuiz(this.question).subscribe(
        (data : any)=>{
          console.log(data);
          this.correctAnswer=data.correctAnswer;
          this.marksGot=Number(data.marksGot).toFixed(2);
          this.attempted=data.attempted;
          this.isSubmit=true
        },
        (error)=>{
          console.log("Error in caluclating the data");
        }
      )

     
    // this.question.forEach((q:any)=>{
    //   if(q.givenAnswer == q.answer){
    //     this.correctAnswer=this.correctAnswer+1;
    //     let singleMark=this.question[0].quiz.maxMarks/this.question.length;
    //     this.marksGot+=singleMark;
    //   }
    //   if(q.givenAnswer.trim()!='')
    //   {
    //     this.attempted++;
    //   }
    // })
    // console.log("Correct answers "+this.correctAnswer)
    // console.log("marks "+this.marksGot)
    // console.log("Attempt : "+this.attempted)

  }

  printPage()
  {
    window.print();
  }

}
