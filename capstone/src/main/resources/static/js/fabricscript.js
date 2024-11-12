document.addEventListener('DOMContentLoaded', function () {
    const canvasContainer = document.getElementById('image-container');
    const urlParams = new URLSearchParams(window.location.search);
    const imageUrl = urlParams.get('imageUrl');

    if (imageUrl) {
        canvasContainer.style.backgroundImage = `url(${imageUrl})`;
    }

    const canvas = new fabric.Canvas('canvas', {
        width: 150,
        height: 150,
        isDrawingMode: true,
        selection: false
    });

    // Update canvas size based on selected dropdown option
    document.getElementById('canvasSize').addEventListener('change', function () {
        const [width, height] = this.value.split('x').map(Number);
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.renderAll();
    });

    // Toggle between Pen and Select mode
    document.getElementById('pen-btn').addEventListener('click', function () {
        canvas.isDrawingMode = true;
        canvas.selection = false;
        canvas.renderAll();
    });

    document.getElementById('select-btn').addEventListener('click', function () {
        canvas.isDrawingMode = false;
        canvas.selection = true;
        canvas.renderAll();
    });

    // Handle image upload and show preview
    const imageUploadInput = document.getElementById('upload-btn');
    const imagePreview = document.getElementById('image-preview');

    imageUploadInput.addEventListener('change', function (e) {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (event) {
                imagePreview.src = event.target.result;
                imagePreview.style.display = 'block';

                fabric.Image.fromURL(event.target.result, function (img) {
                    // Scale image to fit within the current canvas dimensions
                    img.scaleToWidth(canvas.width);
                    img.scaleToHeight(canvas.height);

                    // Position the image at the center of the canvas
                    img.set({
                        left: (canvas.width - img.width) / 2,
                        top: (canvas.height - img.height) / 2,
                    });

                    canvas.add(img);
                    canvas.centerObject(img); 
                    canvas.renderAll();
                });
            };
            reader.readAsDataURL(file);
        }
    });

    // Undo functionality
    let canvasStates = [];
    let currentStateIndex = -1;

    canvas.on('object:added', function () {
        canvasStates = canvasStates.slice(0, currentStateIndex + 1);
        canvasStates.push(canvas.toJSON());
        currentStateIndex++;
    });

    document.getElementById('undo-btn').addEventListener('click', function () {
        if (currentStateIndex > 0) {
            currentStateIndex--;
            canvas.loadFromJSON(canvasStates[currentStateIndex], canvas.renderAll.bind(canvas));
        }
    });

    // Delete button functionality
    document.getElementById('delete-btn').addEventListener('click', function () {
        const activeObject = canvas.getActiveObject();
        if (activeObject) {
            canvas.remove(activeObject);
            updateLayers(); // Update layer list after deletion
        }
    });

    // Fetch fonts from Google API
    async function fetchFonts() {
        const response = await fetch(`https://www.googleapis.com/webfonts/v1/webfonts?key=AIzaSyDII-VFDCnoxx63gsYtFW7X0O8GhN8DxUU`);
        const { items } = await response.json();
        const fontSelect = document.getElementById("fontStyle");
        items.forEach(font => {
            const option = document.createElement("option");
            option.value = font.family;
            option.textContent = font.family;
            fontSelect.appendChild(option);
        });
    }

    fetchFonts();

    function loadFont(fontName) {
        const link = document.createElement('link');
        link.href = `https://fonts.googleapis.com/css2?family=${fontName.replace(/ /g, '+')}&display=swap`;
        link.rel = 'stylesheet';
        document.head.appendChild(link);
    }

    document.getElementById('fontStyle').addEventListener('change', function () {
        const selectedFont = this.value;
        loadFont(selectedFont);
        canvas.getObjects('i-text').forEach(textObject => {
            textObject.set('fontFamily', selectedFont);
            canvas.renderAll();
        });
    });

    document.getElementById('text-btn').addEventListener('click', function () {
        const text = new fabric.IText('Your custom text', {
            left: 50,
            top: 50,
            fill: document.getElementById('text-color').value || '#000',
            fontFamily: document.getElementById('fontStyle').value
        });
        canvas.add(text);
        canvas.setActiveObject(text);
        canvas.renderAll();
    });

    document.getElementById('text-color').addEventListener('input', function () {
        const activeObject = canvas.getActiveObject();
        if (activeObject && activeObject.type === 'i-text') {
            activeObject.set('fill', this.value);
            canvas.renderAll();
        }
    });

    const layersList = document.getElementById('layers-list');

    // Function to update the layer list
    function updateLayers() {
        // Clear the current layer list
        layersList.innerHTML = '';

        // Loop through all canvas objects
        canvas.getObjects().forEach(function (obj, index) {
            const layerItem = document.createElement('div');
            layerItem.classList.add('layer-item');
            layerItem.setAttribute('draggable', 'true');
            layerItem.innerHTML = `
                <span>Layer ${index + 1}: ${obj.type}</span>
                <button class="select-btn">Select</button>
                <button class="delete-btn">Delete</button>
            `;
            layersList.appendChild(layerItem);

            // Attach drag and drop functionality
            layerItem.addEventListener('dragstart', function (e) {
                e.dataTransfer.setData('index', index);
            });

            layerItem.addEventListener('dragover', function (e) {
                e.preventDefault();
            });

            layerItem.addEventListener('drop', function (e) {
                e.preventDefault();
                const sourceIndex = e.dataTransfer.getData('index');
                const targetIndex = index;

                if (sourceIndex !== targetIndex) {
                    // Swap layers in canvas
                    const sourceObject = canvas.getObjects()[sourceIndex];
                    const targetObject = canvas.getObjects()[targetIndex];

                    // Swap the order of objects in canvas
                    canvas.remove(sourceObject);
                    canvas.remove(targetObject);
                    canvas.insertAt(sourceObject, targetIndex);
                    canvas.insertAt(targetObject, sourceIndex);
                    canvas.renderAll();
                    updateLayers(); // Update layer list after reordering
                }
            });

            // Add selection and delete button functionality
            layerItem.querySelector('.select-btn').addEventListener('click', function () {
                const selectedObject = canvas.getObjects()[index];
                canvas.setActiveObject(selectedObject);
                canvas.renderAll();
            });

            layerItem.querySelector('.delete-btn').addEventListener('click', function () {
                canvas.remove(canvas.getObjects()[index]);
                updateLayers(); // Update layer list after deletion
            });
        });
    }

    // Initial update of the layer list
    updateLayers();

    // Listen to canvas object changes to update layers dynamically
    canvas.on('object:added', updateLayers);
    canvas.on('object:removed', updateLayers);
});
