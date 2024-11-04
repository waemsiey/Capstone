document.addEventListener('DOMContentLoaded', function () {
    console.log('This is samplefabric.html');

    const urlParams = new URLSearchParams(window.location.search);
    const imageUrl = urlParams.get('imageUrl');

    if (imageUrl) {
        const canvasContainer = document.querySelector('.canvas-container');
        canvasContainer.style.backgroundImage = `url(${imageUrl})`;
    }

    const canvas = new fabric.Canvas('canvas', {
        width: 150,
        height: 150,
        borderColor: 'black',
        isDrawingMode: true
    });

    // Keep the canvas background empty
    canvas.setBackgroundImage(null);

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
        canvasStates = canvasStates.slice(0, currentStateIndex + 1);
        canvasStates.push(json);
        currentStateIndex++;
    }

    canvas.on('object:added', function () {
        saveCanvasState();
    });
// Undo Functionality
    document.getElementById('undo-btn').addEventListener('click', function () {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            canvas.loadFromJSON(canvasStates[currentStateIndex], canvas.renderAll.bind(canvas));
        }
    });

// Delete Selected Object Functionality
    document.getElementById('delete-btn').addEventListener('click', function () {
    const activeObject = canvas.getActiveObject();
        if (activeObject) {
            canvas.remove(activeObject);
            saveCanvasState(); // Save state after deletion
        }
    });
    // Fetch Fonts
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

        const text = new fabric.IText('Your custom text', {
            left: 50,
            top: 50,
            fill: document.getElementById('text-color').value || '#000',
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
    });
});
