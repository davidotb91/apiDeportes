var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');

var penWidth=1;
var drawMode=true;

$(document).ready(function () {
    $('.myPenWidth input.ui-spinner-input').change(function (e){
        penWidth = $('.myPenWidth input.ui-spinner-input').attr('value');
    });
    
    $('form').submit(function () {
        $('input[id="odontForm:image"]').attr('value', canvas.toDataURL("image/png"));
        return true;
    });
});
            
var backimg = new Image();
backimg.src = "odont.jpg";
backimg.onload = function(){
    if (create)
        context.drawImage(backimg, 0, 0);
}

if (!create) {
    var img = new Image();
    img.src = $('input[id="odontForm:image"]').attr('value');
    img.onload = function(){
        context.drawImage(img, 0, 0);
    }
}

var painting=false;

var lastMouseX=-1;
var lastMouseY=0;
            
canvas.addEventListener('mousedown', function(evt) {
    painting=true;
}, false);
            
canvas.addEventListener('mouseup', function(evt) {
    painting=false;
    lastMouseX=-1;
}, false);

canvas.addEventListener('mousemove', function(evt) {
    if (painting) {
        var rect = canvas.getBoundingClientRect();
        var mouseX=evt.clientX - rect.left;
        var mouseY=evt.clientY - rect.top;
                
        if (lastMouseX != -1) {
            context.beginPath();                        
            context.moveTo(lastMouseX, lastMouseY);
            context.lineTo(mouseX, mouseY);
            context.lineWidth = penWidth;
            context.strokeStyle = drawMode ? '#' + $('.myColorPicker > input').attr('value') : '#ffffff';
            context.stroke();
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }
}, false);
            
function clearCanvas()
{                
    canvas.width=canvas.width;
    context.drawImage(backimg, 0, 0);
    lastMouseX=-1;
}

function saveAsImage() {
    Canvas2Image.saveAsPNG(canvas);
}

function changeDrawMode() {
    if (drawMode) {
        $('button[id="odontForm:drawEraseBtn"] .ui-button-text').text(eraseWord);
        $('.myColorPicker > button').attr('disabled', 'disabled');
    } else {
        $('button[id="odontForm:drawEraseBtn"] .ui-button-text').text(drawWord);
        $('.myColorPicker > button').removeAttribute('disabled');
    }
    drawMode = !drawMode;
}