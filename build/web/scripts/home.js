//$(".knownSide").click(function(){
//  $(".friendsList").fadeOut("slow");
//  $(".textArea").fadeOut("slow");
//  $(".unknownSide").css("border-bottom","none");
//  $(".knownSide").css("border-bottom","1px solid #274F44");
//  $(".friendsList").fadeIn("slow");
//  $(".textArea").fadeIn("slow");
//});
/*$("#knownSideImg").hover(function(){
  $(this).animate({width:"50%",height:"80%"},300);
},function(){
  $(this).animate({width:"40%",height:"70%"},300);
});*/
//$(".unknownSide").click(function(){
//  $(".friendsList").fadeOut("slow");
//  $(".textArea").fadeOut("slow");
//  $(".knownSide").css("border-bottom","none");
//  $(".unknownSide").css("border-bottom","1px solid #274F44");
//    $(".friendsList").fadeIn("slow");
//    $(".textArea").fadeIn("slow");
//});
$("#musicToggleImg").hover(function(){
  $(this).animate({height:"80%",width:"70%"},150);
},function(){
  $(this).animate({height:"70%",width:"60%"},150);
});
$("#moviesToggleImg").hover(function(){
  $(this).animate({height:"80%",width:"70%"},150);
},function(){
  $(this).animate({height:"70%",width:"60%"},150);
});
$("#booksToggleImg").hover(function(){
  $(this).animate({height:"80%",width:"70%"},150);
},function(){
  $(this).animate({height:"70%",width:"60%"},150);
});
$("#settingsToggleImg").hover(function(){
  $(this).animate({height:"80%",width:"70%"},150);
},function(){
  $(this).animate({height:"70%",width:"60%"},150);
});
function progressFunction (){
      $("#firstCircle").fadeOut("slow");
      $("#secondCircle").fadeOut("slow");
      $("#thirdCircle").fadeOut("slow");
      $("#firstCircle").fadeIn("slow");
      $("#secondCircle").fadeIn("slow");
      $("#thirdCircle").fadeIn("slow");    
}
$("#musicToggleImg").click(function(){
    removeUnwanted();
    type=1;
    commonStuff();
      $.getJSON("MusicRetreiver",function(data){
          console.log("in the get part");
        $(".progressShow").css("dispaly","none");
        $("#actualContent").css("display","block");
    $.each(data, function(i, product) {
        var div = $("<div id='content'></div>");
        var contenth = $("<h3 id='contentText' class='artist_name'></h3>").text(product.artist_name).appendTo(div);
        var contentt = $("<h4 id='contentText' class='genre'></h4>").text(product.genre).appendTo(div);
       // div.append(contenth,contentt);
        $("#actualContent").append(div);
       // appendData(data);
   });
      }).fail(function(data) {
          alert("fail");
          console.log(data);
      });
  $("#MoreDetails").css("display","block");
});

$("#booksToggleImg").click(function (){
    removeUnwanted();
    console.log("in the books function");
    type=2;
    commonStuff();
      $.getJSON("BooksRetriever",function(data){
          console.log("in the get part");
        $(".progressShow").css("dispaly","none");
        $("#actualContent").css("display","block");
    $.each(data, function(i, product) {
        var div = $("<div id='content'></div>");
        var image = $("<img id='contentImage'/>").attr("src",product.ImageUrl).appendTo(div);
        var contenth = $("<h3 id='contentText' class='artist_name'></h3>").text(product.title).appendTo(div);
        var contentt = $("<h4 id='contentText' class='genre'></h4>").text(product.author).appendTo(div);
       // div.append(contenth,contentt);
        $("#actualContent").append(div);
    });
       // appendData(data);
      }).fail(function(data) {
          alert("fail");
          console.log(data);
      });
  $("#MoreDetails").css("display","block");   
});

function removeUnwanted(){
    $("div#content").each(function(i,element){
       $(element).remove(); 
    });
}

$("#moviesToggleImg").click(function (){
    removeUnwanted;
    console.log("in the movies function");
    type=3;
    commonStuff();
      $.getJSON("MoviesRetreiver",function(data){
          console.log("in the get part");
        $(".progressShow").css("dispaly","none");
        $("#actualContent").css("display","block");
    $.each(data, function(i, product) {
        var div = $("<div id='content'></div>");
        var Image = $("<img id='contentImage'/>").attr("src",product.movie_poster).appendTo(div);
        var contenth = $("<h3 id='contentText' class='artist_name'></h3>").text(product.movie_name).appendTo(div);
      //  var contentt = $("<h4 id='contentText' class='genre'></h4>").text(product.genre).appendTo(div);
       // div.append(contenth,contentt);
        $("#actualContent").append(div);
    });
        //appendData(data);
      }).fail(function(data) {
          alert("fail");
          console.log(data);
      });
  $("#MoreDetails").css("display","block");
    
});

//function appendData(data) {
//    $.each(data, function(i, product) {
//        var div = $("<div id='content'></div>");
//        var contenth = $("<h3 id='contentText' class='artist_name'></h3>").text(product.artist_name).appendTo(div);
//        var contentt = $("<h4 id='contentText' class='genre'></h4>").text(product.genre).appendTo(div);
//       // div.append(contenth,contentt);
//        $("#actualContent").append(div);
//    });
//}

function commonStuff(){
  $(".blocker").css("display","block");
  $(".progressShow").css("dispaly","block");
      $("#firstCircle").fadeOut("slow");
      $("#secondCircle").fadeOut("slow");
      $("#thirdCircle").fadeOut("slow");
      $("#firstCircle").fadeIn("slow");
      $("#secondCircle").fadeIn("slow");
      $("#thirdCircle").fadeIn("slow");
      console.log("in the click function");
  setInterval(progressFunction,1000);
    
}

var type=0;

$("#submitButton").click(function() {
    var selected = [];
    $("div#content").each(function (i,element){
        //console.log($(element).css("background-color"));
        if($(element).css("background-color")==="rgba(16, 229, 188, 0.6)"){
            console.log($(element));
            selected.push({
               name:$(this).find(".artist_name").text(),
               genre:$(this).find(".genre").text()
            });
        }
    });
    if(type===1){
        console.log("Posting to music");
        console.log(JSON.stringify(selected));
    $.post("SelectedMusicArtists",{
        username:$("#username").text(),
        selected:JSON.stringify(selected)
    },function(data){
        console.log(data);
    });
    }else if(type===2){
        console.log("Posting to books");
        console.log(JSON.stringify(selected));
    $.post("SelectedBooks",{
        username:$("#username").text(),
        selected:JSON.stringify(selected)
    },function(data){
        console.log(data);
    });

    }else if(type===3){
        console.log("Posting to movies");
        console.log(JSON.stringify(selected));
    $.post("SelectedMovies",{
        username:$("#username").text(),
        selected:JSON.stringify(selected)
    },function(data){
        console.log(data);
    });
        
    }
    console.log(JSON.stringify(selected));
  $(".blocker").css("display","none");
  $(".progressShow").css("dispaly","none");
  $("#MoreDetails").css("display","none");
});

$("#cancelButton").click(function() {
    type=0;
  $(".blocker").css("display","none");
  $(".progressShow").css("dispaly","none");
  $("#MoreDetails").css("display","none");
  $("#actualContent").css("display","none");
});
var usernameSetter;
var ws;
window.onload=setUsername;
function setUsername(){
    $.get("GetUsername",function(data){
        //console.log(data);
        usernameSetter = data;
ws = new WebSocket("ws://localhost:8080/Similis/websocketendpoint?username="+usernameSetter);
       $("#username").text(data); 
       ws.onclose=setclose;
       ws.onmessage = onMessage;
    });
    $.get("GetFriendsUsername",function(data){
       console.log(data); 
       $.each(data,function(i,product){
           
          var div = $("<div class='friend'></div>");
          $("<p id='friendNameList'></p>").text(product).appendTo(div);
         $(".friendsList").append(div);
       });
    });

}
function setclose(){
    alert("socket closed");
    console.log("socket closed");
}
function onMessage(data){
    var maindiv=$("<div style='width:100%;height:auto;display: inline-block'></div>");
        var div = $("<div id='receivedMessage'></div>");
        $("<p></p>").text(data.data).appendTo(div);
        div.appendTo(maindiv);
   $(".messageDisplay").append(maindiv); 
    console.log(data.data);
}

$("#sendButton").click(function(){
    var maindiv=$("<div style='width:100%;height:auto;display: inline-block'></div>");
        var div = $("<div id='sentMessage'></div>");
        $("<p></p>").text($("#messagePlace").val()).appendTo(div);
        div.appendTo(maindiv);
        var logged = $("#username").text();
        var friend = $("#friendName").text();
        var message = $("#messagePlace").val();
        sendMessageToUser(logged,friend,message);
        addtodatabase(logged,friend,message);
   $(".messageDisplay").append(maindiv); 
   $("#messagePlace").val("");
});

function addtodatabase(logged,friend,message){
    $.post("AddMessage",{
        uid1:logged,
        friend_uid:friend,
        message:message
    },function(data){
        console.log("Success");
    });
}

setInterval(function(){
    $.get("CheckOnline",function(data){
        console.log("success");
    }).fail(function(data){
        console.log(data);
    });
},55000);


function sendMessageToUser(loggedin,friend,message){
    var data = {
        loggedUser : loggedin,
        Friend: friend,
        Message : message
    };
    ws.send(JSON.stringify(data));
}

$("#actualContent").on("click","#content",function(){
    $(this).css("background-color","rgba(16,229,188,0.6)");
});


$(".friendsList").on("click",".friend",function() {
    $("#friendName").remove();
    $("div#sentMessage").each(function(i,element){
       $(element).remove(); 
    });
    $("div#receivedMessage").each(function(i,element){
       $(element).remove(); 
    });
    console.log("In the click function");
    $("<p id='friendName'></p>").text($(this).find("p").text()).appendTo($("#friendNameIdentifier"));
    var logged = $("#username").text();
    var friend = $(this).find("p").text();
    $.post("GetMessages",{
        loggedin: logged,
        friend : friend
    },function(data){
        $.each(data,function(i,product){
            if(product.type === 1){
                addsentmessage(product.message);
            }
            if(product.type === 2){
                addreceivedmessage(product.message);
            }
        });
    });
});

function addsentmessage(message){
    var maindiv=$("<div style='width:100%;height:auto;display: inline-block'></div>");
        var div = $("<div id='sentMessage'></div>");
        $("<p></p>").text(message).appendTo(div);
        div.appendTo(maindiv);
   $(".messageDisplay").append(maindiv); 
}

function addreceivedmessage(message){
    var maindiv=$("<div style='width:100%;height:auto;display: inline-block'></div>");
        var div = $("<div id='receivedMessage'></div>");
        $("<p></p>").text(message).appendTo(div);
        div.appendTo(maindiv);
   $(".messageDisplay").append(maindiv); 
    
}