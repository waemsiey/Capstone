document.addEventListener('DOMContentLoaded', function () {
    console.log('This is samplefabric.html');

    const canvas = new fabric.Canvas('canvas', {
        width: 150,
        height: 150,
        borderColor: 'black',
        isDrawingMode: true
    });

    // Change Canvas Size
    document.getElementById('canvasSize').addEventListener('change', function () {
        const size = this.value.split('x');
        const width = parseInt(size[0]);
        const height = parseInt(size[1]);

        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.renderAll();
    });

    // Save canvas state for undo functionality
    let canvasStates = [];
    let currentStateIndex = -1;

    function saveCanvasState() {
        const json = canvas.toJSON();
        canvasStates.push(json);
        currentStateIndex++;
    }

    canvas.on('object:added', function () {
        saveCanvasState();
    });

        async function fetchFonts() {
            const response = await fetch(`https://www.googleapis.com/webfonts/v1/webfonts?key=AIzaSyDII-VFDCnoxx63gsYtFW7X0O8GhN8DxUU`);
            const data = await response.json();
            return data.items; // Return the list of font items
        }

        fetchFonts().then(fonts => {
            const fontSelect = document.getElementById("fontStyle");
            fonts.forEach(font => {
                const option = document.createElement("option");
                option.value = font.family;
                option.textContent = font.family;
                fontSelect.appendChild(option);
            });
        });
    // Load Font
    function loadFont(fontName) {
        const link = document.createElement('link');
        link.href = `https://fonts.googleapis.com/css2?family=${fontName.replace(/ /g, '+')}&display=swap`;
        link.rel = 'stylesheet';
        document.head.appendChild(link);
    }

    // Change Font
    document.getElementById('fontStyle').addEventListener('change', function () {
        const selectedFont = this.value;
        loadFont(selectedFont);

        canvas.getObjects('text').forEach(function (textObject) {
            textObject.set('fontFamily', selectedFont);
            canvas.renderAll();
        });
    });

    // Add Text
    document.getElementById('text-btn').addEventListener('click', function () {
        const selectedFont = document.getElementById('fontStyle').value;

        const text = new fabric.IText('Your custom text', { //Itext for edit the text itself (Interactive )
            left: 50,
            top: 50,
            fill: document.getElementById('text-color').value || '#000', // Fixed ID
            fontFamily: selectedFont
        });

        canvas.add(text);
        canvas.renderAll();
    });

    // Text Color Change
    const textColorInput = document.getElementById('text-color');
    textColorInput.addEventListener('input', function () {
        const activeObject = canvas.getActiveObject();
        if (activeObject && activeObject.type === 'i-text') {
            activeObject.set('fill', textColorInput.value);
            canvas.renderAll();
        }
    });

    // Pen Color Change
    const penColorInput = document.getElementById('pen-color');
    penColorInput.addEventListener('input', function () {
        canvas.freeDrawingBrush.color = penColorInput.value;
    });

    canvas.freeDrawingBrush.color = penColorInput.value;

    // Toggle Drawing Mode
    const penBtn = document.getElementById('pen-btn');
    penBtn.addEventListener('click', function () {
        canvas.isDrawingMode = !canvas.isDrawingMode;
        penBtn.textContent = canvas.isDrawingMode ? 'Switch to select' : 'Switch to draw';
    });

    // Uploading Image
    const uploadBtn = document.getElementById('upload-btn');
    uploadBtn.addEventListener('change', function (e) {
        const file = e.target.files[0];
        if (file) {
            if (!file.type.startsWith('image/')) {
                alert('Upload an image file');
                return;
            }

            const reader = new FileReader();
            reader.onload = function (event) {
                fabric.Image.fromURL(event.target.result, function (img) {
                    img.scaleToWidth(canvas.width);
                    img.scaleToHeight(canvas.height);

                    img.set({
                        left: (canvas.width - img.getScaledWidth()) / 2,
                        top: (canvas.height - img.getScaledHeight()) / 2,
                        selectable: true
                    });
                    canvas.add(img);
                    canvas.setActiveObject(img);
                    canvas.renderAll();
                });
            };
            reader.readAsDataURL(file);
        } else {
            alert('No file Selected');
        }
    });

    // Undo functionality
    const undoBtn = document.getElementById('undo-btn');
    undoBtn.addEventListener('click', function () {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            const stateRestore = canvasStates[currentStateIndex];
            canvas.loadFromJSON(stateRestore, canvas.renderAll.bind(canvas));
        }
    });

    // Deleting Object
    const delBtn = document.getElementById('delete-btn');
    delBtn.addEventListener('click', function () {
        if (canvas.isDrawingMode) {
            canvas.isDrawingMode = false;
        }

        const activeObject = canvas.getActiveObject();
        if (activeObject) {
            canvas.remove(activeObject);
            saveCanvasState();
            canvas.renderAll();
        } else {
            alert("No object Selected to delete");
        }
    });
});
