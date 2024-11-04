const menuu = document.querySelector(".menu");
const navList = document.querySelector(".nav-list");

if (menuu) {
  menuu.addEventListener("click", () => {
    navList.classList.toggle("open");
  });
}
let cardSlideIndex = 0;
showSlides();
function showSlides(){
    let i;
    let slides = document.getElementsByClassName("slider");
    let progressDots = document.getElementsByClassName("progress");
    for (i = 0; i<slides.length; i++){
        slides[i].style.display ="none";
    }
    cardSlideIndex++;
    if (cardSlideIndex > slides.length){
        cardSlideIndex = 1
    }
    for (i = 0 ; i < progressDots.length ; i++){
        progressDots[i].className = progressDots[i].className.replace("active","");
    }
    slides[cardSlideIndex-1].style.display = "block";
    progressDots[cardSlideIndex-1].className +=" active";
    setTimeout(showSlides,3000); //for 3 seconds changing of card images
  }

  const inventoryList = /*[[${product.inventoryList}]]*/ []; // Pass the inventory list to JavaScript

  function updateColors() {
    const sizeSelect = document.getElementById('size');
    const colorSelect = document.getElementById('color');
    const selectedSize = sizeSelect.value;

    // Clear existing options
    colorSelect.innerHTML = '';

    // Filter colors based on the selected size
    const availableColors = inventoryList
      .filter(item => item.size === selectedSize)
      .map(item => item.color);

    // Add unique colors to the color dropdown
    const uniqueColors = [...new Set(availableColors)];
    uniqueColors.forEach(color => {
      const option = document.createElement('option');
      option.value = color;
      option.textContent = color;
      colorSelect.appendChild(option);
    });

    // Optional: Update price based on selected size and color
    updatePrice();
  }

  function updatePrice() {
    const sizeSelect = document.getElementById('size');
    const colorSelect = document.getElementById('color');
    const selectedSize = sizeSelect.value;
    const selectedColor = colorSelect.value;

    // Find the matching inventory item and update the price display
    const matchingItem = inventoryList.find(item => item.size === selectedSize && item.color === selectedColor);
    if (matchingItem) {
      document.getElementById('priceDisplay').textContent = matchingItem.price;
    }
  }