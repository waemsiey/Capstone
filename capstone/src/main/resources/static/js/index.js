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