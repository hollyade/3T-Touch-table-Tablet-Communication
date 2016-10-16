var prev;
var prevManager;

var drawing = false;

// get a reference to an element
function setObjectTouch(object){ 
  drawing = false;
  console.log("image selection");

  if (prev != null) {
    console.log("destroy prev manager");
    prev.style.border = "0px solid #FFFFFF";
    prevManager.destroy();
  }

  prev = object;
  var manager = new Hammer.Manager(object);
  prevManager = manager;

  object.style.border = "2px solid #FFFFFF";

  var Pan = new Hammer.Pan();
  var Rotate = new Hammer.Rotate();
  var Pinch = new Hammer.Pinch();
  var Press = new Hammer.Press();

  Rotate.recognizeWith([Pan]);
  Pinch.recognizeWith([Rotate]);

  // add the recognizers
  manager.add(Pan);
  manager.add(Rotate);
  manager.add(Pinch);
  manager.add(Press);

  // subscribe to events
  var liveScale = 0.2;
  var currentRotation = 0;

  manager.on('rotateend', function(e) {
    // cache the rotation
    console.log("rotateend");
    var deltaXYStr = object.style.transform;

    console.log(deltaXYStr)

    if (deltaXYStr.includes("rotateZ")) {
      var iStart = deltaXYStr.indexOf("rotateZ");
      var iEnd = deltaXYStr.indexOf(")", deltaXYStr.indexOf("rotateZ"));
      var sliced = deltaXYStr.slice(iStart, iEnd+1);
      deltaXYStr = deltaXYStr.replace(sliced, "");
    }

    currentRotation = currentRotation + Math.round(liveScale * e.rotation);
    var translateStr = " rotateZ("+currentRotation+"deg)";
    deltaXYStr = deltaXYStr.concat(translateStr);
    object.style.transform  = deltaXYStr;

  });   

 var prevX = 0;
 var prevY = 0;

  manager.on('panend', function(e) {
    console.log('panend');
    var deltaXYStr = object.style.transform;

    console.log(deltaXYStr);

    if (deltaXYStr.includes("translateX")) {
      var num = deltaXYStr.substring(deltaXYStr.indexOf("translateX(")+11, deltaXYStr.indexOf("px)"));
      var deltaX = parseInt(num);
      var num = deltaXYStr.substring(deltaXYStr.indexOf("translateY(")+11, deltaXYStr.indexOf("px)", deltaXYStr.indexOf("translateY(")));
      var deltaY = parseInt(num);
    } else {
      var deltaX = 0;
      var deltaY = 0;
    }

    deltaX = deltaX + (e.deltaX);
    deltaY = deltaY + (e.deltaY);
      
    var translateStr = "translateX("+deltaX+"px) translateY("+deltaY+"px)";
    var tempRotate = "";
    var tempScale = ""

    console.log(translateStr);


    if (deltaXYStr.includes("rotateZ")) {
      var iStart = deltaXYStr.indexOf("rotateZ");
      var iEnd = deltaXYStr.indexOf(")", deltaXYStr.indexOf("rotateZ"));
      var sliced = deltaXYStr.slice(iStart, iEnd+1);
      translateStr = translateStr.concat(" ");
      translateStr = translateStr.concat(sliced);
      //console.log('panend, rotate, deltaXYStr:  '+tempRotate);
    } 
    if (deltaXYStr.includes("scale")) {
      var iStart = deltaXYStr.indexOf("scale");
      var iEnd = deltaXYStr.indexOf(")", deltaXYStr.indexOf("scale"))        
      var sliced = deltaXYStr.slice(iStart, iEnd+1);
      translateStr = translateStr.concat(" ");
      translateStr = translateStr.concat(sliced);
    }

    object.style.transform  = translateStr;
    console.log(translateStr);
  });


  var currentScale = 0.5;
  function getRelativeScale(scale) {
    return scale * currentScale;
  }

  manager.on('pinchend', function(e) {
    // cache the scale
    console.log('pinchend');
    var deltaXYStr = object.style.transform;
    console.log(deltaXYStr)

    if (deltaXYStr.includes("scale")) {
      var iStart = deltaXYStr.indexOf("scale");
      var iEnd = deltaXYStr.indexOf(")", deltaXYStr.indexOf("scale"));
      var sliced = deltaXYStr.slice(iStart, iEnd+1);
      deltaXYStr = deltaXYStr.replace(sliced, "");
    }
    currentScale = getRelativeScale(e.scale);
    liveScale = currentScale; 

    var translateStr = " scale("+currentScale+")";
    deltaXYStr = deltaXYStr.concat(translateStr);
    object.style.transform  = deltaXYStr;
  });

  manager.on('press', function(e) {
    // cache the scale
    console.log('press');
    var deltaXYStr = object.style.transform;

    if (deltaXYStr.includes("scale")) {
      var iStart = deltaXYStr.indexOf("scale");
      var iEnd = deltaXYStr.indexOf(")", deltaXYStr.indexOf("scale"));
      var sliced = deltaXYStr.slice(iStart, iEnd+1);
      deltaXYStr = deltaXYStr.replace(sliced, "");
    }

    currentScale = getRelativeScale(2);
    liveScale = currentScale; 

    var translateStr = " scale("+currentScale+")";
    deltaXYStr = deltaXYStr.concat(translateStr);
    object.style.transform  = deltaXYStr;

    var username = object.id.toString();
    var comment = prompt("Please enter your comment", "");
    var myFirebaseRef = new Firebase("https://hollyadehonours.firebaseio.com/");
    console.log((new Date).getTime());
    var commentKey = username.concat('_', (new Date).getTime());
    myFirebaseRef.child(commentKey).set(comment);
  });

  function mult(a, b) {
    return Math.round(a * b);
  }
}

function setCanvasOn(object){
  drawing = true;
  console.log("Canvas!");

  if (prev != null) {
    console.log("destroy prev manager");
    prev.style.border = "0px solid #FFFFFF";
    prevManager.destroy();
    prev = null;
    prevManager = null;
  }

  var myFirebaseRef = new Firebase("https://hollyadehonours.firebaseio.com/");

  myFirebaseRef.child("Screenshot").on("child_added", function(snapshot, prevChildKey) {
    var username = snapshot.key();
    var val = snapshot.val();
    html2canvas(document.body, {
      onrendered: function(canvas) {
        if (val=="Request") {
          var image = new Image();
          image.src = document.getElementById('c').toDataURL("image/png");
          var context = canvas.getContext('2d');
          context.drawImage(image, 0, 0);
          var imageFinal = canvas.toDataURL();

          var imageStr = imageFinal.split(",");
          myFirebaseRef.child("Screenshot").child(username).set(imageStr[1]);
              //document.body.appendChild(canvas);
        }
      }
    });
  });

}

var prevX = -1;
var prevY = -1;

jQuery(function() {
  var $ = jQuery,
    canvas = document.getElementById('c'),
    ctx = canvas.getContext('2d'),
    $log = $('#log').val('');
    resizeCanvas();

  // resize the canvas to fill browser window dynamically
  window.addEventListener('resize', resizeCanvas, false);

  function resizeCanvas() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;  
  }

  function log(line) {
    $log.val($log.val() + line + '\r');
  }

  // Colors from http://kuler.adobe.com/#themeID/1943143
  var COLORS = [ '#384E66', '#C4B44D', '#661E3B', '#B8442F', '#E87F2C' ],
    colorIndex = 0,
    pointerColors = {},
    CIRCLE_RADIUS = 3;

  function drawPointer(event) {
    // get canvas relative coordinates
    var x = event.pointer.x,
        y = event.pointer.y;

    // dict keys must have a char prefix
    colorKey = 'c-' + event.pointer.id,
    color = pointerColors[colorKey];

    // choose a color and advance the color index
    if (!color) {
      pointerColors[colorKey] = color = COLORS[colorIndex];
      colorIndex = (colorIndex + 1) % COLORS.length;
      prevX =-1;
      prevY =-1
    }

    if (drawing) {
      ctx.beginPath();
      if (prevX == -1) {
        ctx.arc(x, y, CIRCLE_RADIUS, 0, 2 * Math.PI, false);
        ctx.fillStyle = color;
        ctx.fill();
      } else {
        ctx.moveTo(prevX,prevY);
        ctx.lineTo(x,y);
        ctx.lineWidth = 2;
        ctx.strokeStyle = color;
        ctx.stroke();
      }
    }

    prevX = x;
    prevY = y;
  }

  $('#touchArea').on({        
    pxpointerstart: function(event) {
      drawPointer(event);    
    },
    pxpointermove: function(event) {
      drawPointer(event);    
      // prevent panning the screen
      event.preventDefault();
    },
    pxpointerend: function(event) {
      drawPointer(event);
      delete pointerColors['c-' + event.pointer.id];
    },
  });
});