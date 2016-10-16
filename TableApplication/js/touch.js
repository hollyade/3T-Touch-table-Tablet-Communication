// // MouseControls contains event listeners for making
// // the Collage respond to mouse and keyboard input

// window.TouchControls = new function() {
//   var self = this;
//   self.init = function() {

//   var nodeList = document.getElementsByClassName('image');
 
//   for(var i=0;i<nodeList.length;i++) {
//      var hammertime = Hammer(nodeList[i], {
//         transform_always_block: true,
//         transform_min_scale: window.initScale,
//         transform_max_scale: 1,
//         drag_block_horizontal: true,
//         drag_block_vertical: true,
//         drag_min_distance: 0,
//     });

//      hammertime.on('touch drag dragend dragstart transform release mouseleave transformend pinchin pinchout', function (ev) {
//         elemRect = nodeList[i];
//         manageMultitouch(ev);
//     });
//   }

//   var myFirebaseRef = new Firebase("https://hollyadehonours.firebaseio.com/");

//   myFirebaseRef.child("Upload").on("child_added", function(snapshot, prevChildKey) {
//     //alert(snapshot.val());  // Alerts "San Francisco"
//     console.log(snapshot.val());
//     var image = new Image();
//     var imageSrc = "data:image/jpeg;base64,";
//     imageSrc += snapshot.val();
//     image.src = imageSrc;
//     image.height = "200";
//     image.width = "200";
//     image.posX = "0";
//     image.posY = "0";

//     document.getElementById("touchArea").appendChild(image);

//     var hammertime = Hammer(image, {
//         transform_always_block: true,
//         transform_min_scale: window.initScale,
//         transform_max_scale: 1,
//         drag_block_horizontal: true,
//         drag_block_vertical: true,
//         drag_min_distance: 0,
//     });

//      hammertime.on('touch drag dragend dragstart transform release mouseleave transformend pinchin pinchout', function (ev) {
//         elemRect = image;
//         manageMultitouch(ev);
//     });

//   });

// };

// function manageMultitouch(ev) {
//         var pinchDirection;
//         ev.stopPropagation();

//         console.log(ev);

//         switch (ev.type) {
//             case 'touch':
//                 console.log('touch');

//                 last_scale = ev.scale;
//                 last_rotation = ev.rotation;
//                 lastPosX = ev.posX;
//                 lastPosY = ev.posY;

//                 break;

//             // case 'dragstart':
//             //     var target = ev.target;
//             //     var transform = target.style.WebkitTransform;
//             //     var xStart = Number(transform.indexOf("("))+1;
//             //     var xEnd = Number(transform.indexOf("p"));
//             //     var yStart = Number(transform.indexOf(" "))+1;
//             //     var yEnd = Number(transform.lastIndexOf("p"));

//             //     console.log(target.style.WebkitTransform);

//             //     console.log(transform.substring(xStart, xEnd));
//             //     lastPosX = 0;
//             //     lastPosY=0;
//             //     lastPosX = Number(transform.substring(xStart, xEnd));
//             //     lastPosY = Number(transform.substring(yStart, yEnd));

//             // break;


//             // case 'drag':
//             //     console.log('draggin');
//             //     console.log(ev.gesture.deltaX);
//             //     console.log(lastPosX);

//             //     posX = ev.gesture.deltaX + lastPosX;
//             //     posY = ev.gesture.deltaY + lastPosY;


//             //     var target = ev.target;

//             //     target.style.WebkitTransform = 'translate('+posX +'px,'+ posY+'px)';

//             //     break;

//             // case 'pinchin':

//             //     console.log('pinchin');
//             //     pinchDirection = "in";

//             //     break;

//             // case 'pinchout':
//             //     console.log('pinchout');
//             //     pinchDirection = "out";
//             //     break;

//             // case 'transform':
//             //     console.log('Transforming.');

//             //     rotation = window.rotationNeeded;// rotation + ev.gesture.rotation;//we can change this to snap rotation eventually.
//             //     console.log('Last Scale: ', last_scale);
//             //     scale = Math.max(hammertime.options.transform_min_scale, Math.min(last_scale * ev.gesture.scale, 1));
//             //     var propsImage = document.getElementById('piggy').getBoundingClientRect();
//             //     console.log(propsImage);
//             //     var propsBox = document.getElementById('piggy').getBoundingClientRect();
//             //     console.log(propsBox);


//             //     break;

//             // case 'transformend':
//             //     console.log('We are finished transforming.');
//             //     //when they finish transforming, we need to determinw what the new left reset position would be.
//             //     var propsImage = document.getElementById('piggy').getBoundingClientRect();
//             //     var propsBox = document.getElementById('piggy').getBoundingClientRect();
//             //     window.calcLeft = Math.round(window.preBounds.left - propsImage.left);
//             //     console.log(ev.type);
//             //     if (pinchDirection = "out") {

//             //         window.calcLeft = Math.round(window.calcLeft + ((propsImage.width - propsBox.width) / 2));

//             //     } else if (pinchDirection = "in") {

//             //         window.calcLeft = Math.round(window.calcLeft - ((propsImage.width - propsBox.width) / 2));
//             //     }
//             //     window.calcTop = Math.round(window.calcTop + ((propsImage.top - propsBox.top) / 2));
//             //     console.log(window.calcLeft);

//             //     break;

//             // case 'dragend':
//             //     console.log('We are finished dragging.');
//             //     //console.log(window.calcLeft);
//             //     lastPosX = posX;
//             //     lastPosY = posY;
//             //     checkBounds();
//             //     var target = ev.target;
//             //     console.log(target.style.transform);

//             //     break;

//             // case 'mouseleave':
//             //     //console.log('Release!', posX, posY);
//             //     checkBounds();
//             //     break;

//         }
//       }
//     };
