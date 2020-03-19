function slide(num){
    $('.carousel').carousel(num);
};
let i=0;
function black(){
  if(i==0){
  document.getElementById("body").style.background = "white";
  document.getElementById("html").style.background = "white";
  i=1;
  }else{
  document.getElementById("body").style.background = "black";
  document.getElementById("html").style.background = "black";
  i=0;
  }
  
};