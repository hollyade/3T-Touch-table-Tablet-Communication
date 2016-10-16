// jQuery(function() {

//     var $ = jQuery,
//         canvas = document.getElementById('c'),
//         ctx = canvas.getContext('2d'),
//         $log = $('#log').val('');
//     resizeCanvas();

//     // resize the canvas to fill browser window dynamically
//     window.addEventListener('resize', resizeCanvas, false);

//     // $('#clear').click(function() {
//     //     ctx.clearRect(0, 0, canvas.width, canvas.height);
//     // });

//     function resizeCanvas() {
//         canvas.width = window.innerWidth;
//         canvas.height = window.innerHeight;  
//     }

//     function log(line) {
//         $log.val($log.val() + line + '\r');
//         //$log.scrollTop($log[0].scrollHeight);
//     }

//     function logEvent(event) {
//         log(event.type + ' ' + event.pointer.type + ' id:' + event.pointer.id +
//             ' x:' + event.pointer.x + ' y:' + event.pointer.y);
//     }

//     function logGesture(event) {
//         var numPaths = event.paths.length,
//             i, path;

//         log(event.type + ' paths: ' + numPaths);
//         for (i = 0; i < numPaths; i++) {
//             path = event.paths[i];
//             log(' ' + (i + 1) + ':' +
//                 ' [' + path.startPointer.x + ',' + path.startPointer.y + '] to' +
//                 ' [' + path.movePointer.x + ',' + path.movePointer.y + ']');
//         }
//     }

//     // Colors from http://kuler.adobe.com/#themeID/1943143
//     var COLORS = [ '#384E66', '#C4B44D', '#661E3B', '#B8442F', '#E87F2C' ],
//         colorIndex = 0,
//         pointerColors = {},
//         CIRCLE_RADIUS = 3;

//     function drawPointer(event) {
//         // get canvas relative coordinates
//         var x = event.pointer.x,
//             y = event.pointer.y,

//             // dict keys must have a char prefix
//             colorKey = 'c-' + event.pointer.id,
//             color = pointerColors[colorKey];

//         // choose a color and advance the color index
//         if (!color) {
//             pointerColors[colorKey] = color = COLORS[colorIndex];
//             colorIndex = (colorIndex + 1) % COLORS.length;
//         }

//         ctx.beginPath();
//         ctx.arc(x, y, CIRCLE_RADIUS, 0, 2 * Math.PI, false);
//         ctx.fillStyle = color;
//         ctx.fill();
//     }

//     $(canvas).on('click', function(event) {
//     });

//     $('#touchArea').on({        
//         pxpointerstart: function(event) {
//             logEvent(event);
//             drawPointer(event);    
//         },
//         pxpointermove: function(event) {
//             drawPointer(event);    
//             // prevent panning the screen
//             event.preventDefault();
//         },
//         pxpointerend: function(event) {
//             logEvent(event);
//             drawPointer(event);
//             delete pointerColors['c-' + event.pointer.id];
//         },

//         // pxgesturestart: function(event) {
//         //     logGesture(event);
//         // },
//         // pxgesturemove: function(event) {
//         //     logGesture(event);
//         // },
//         // pxgestureend: function(event) {
//         // },

//         // pxtap: function(event) {
//         // },
//         // pxholdstart: function(event) {
//         // },
//         // pxholdend: function(event) {
//         // },
//         // pxdoubletap: function(event) {
//         // },

//         // pxdragstart: function(event) {
//         //     log(event.type + ' length:' + event.length.toFixed(2));
//         // },
//         // pxdragmove: function(event) {
//         //     log(event.type + ' angle:' + event.angle.toFixed(2));
//         // },
//         // pxdragend: function(event) {
//         //     log(event.type + ' length:' + event.length.toFixed(2));
//         // },

//         // pxpinchstart: function(event) {
//         //     log(event.type + ' scale:' + event.scale.toFixed(2));
//         // },
//         // pxpinchmove: function(event) {
//         //     log(event.type + ' scale:' + event.scale.toFixed(2));
//         // },
//         // pxpinchend: function(event) {
//         //     log(event.type + ' scale:' + event.scale.toFixed(2));
//         // },

//         // pxswipestart: function(event) {
//         //     log(event.type + ' angle:' + event.angle.toFixed(2));
//         // },
//         // pxswipemove: function(event) {
//         //     //log(event.type + ' angle:' + event.angle.toFixed(2));
//         // },
//         // pxswipeend: function(event) {
//         //     log(event.type + ' angle:' + event.angle.toFixed(2));
//         // }

//     });

// });