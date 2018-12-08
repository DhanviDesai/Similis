var usability = 0;
//#ffce00
  $("#logIn").click(function(){
    usability++;
    $(".blocker").css("display","block");
    $(".loginSite").css("display","block");
  });
  $("#signUp").click(function(){
    usability++;
    $(".blocker").css("display","block");
    $(".signupSite").css("display","block");
  });
  $("body").click(function(){
    if(usability>1){
    if($(".blocker").css("display") == "block"){
      $(".loginSite").css("display","none");
      $(".blocker").css("display","none");
      $(".signupSite").css("display","none");
      usability=0;
    }
  }else if(usability==1){
    usability++;
  }
  });
  $(".loginSite").click(function(){
    usability = 1;
  });
  $(".signupSite").click(function(){
    usability = 1;
  });
window.onload = checkSession();

function checkSession(){
    console.log("In the checksession");
    $.get("CheckSession",function(data){
        if(data!=="New"){
            window.location= "/Similis/home.html";
        }
       console.log(data); 
    });
}