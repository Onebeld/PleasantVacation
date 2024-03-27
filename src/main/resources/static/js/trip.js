let currentSlide = 0;

let imageSlides = document.querySelectorAll(".carousel-image");

const nextSlideButton = document.querySelector(".carousel-button.next");
const prevSlideButton = document.querySelector(".carousel-button.prev");
const imageCountBlock = document.querySelector(".image-count-block");

nextSlideButton.addEventListener("click", () => {
    currentSlide++;

    if (currentSlide > imageSlides.length - 1) {
        currentSlide = 0;
    }

    updateSlides();
});

prevSlideButton.addEventListener("click", () => {
    currentSlide--;

    if (currentSlide < 0) {
        currentSlide = imageSlides.length - 1;
    }

    updateSlides();
});

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

function updateSlides() {
    imageCountBlock.textContent = `${currentSlide + 1}/${imageSlides.length}`;

    imageSlides.forEach((slide, index) => {
        slide.style.transform = `translateX(${100 * (index - currentSlide)}%)`;
    });
}

document.addEventListener("DOMContentLoaded", () => {
    updateSlides();
});