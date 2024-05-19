let currentSlide = 0;

let imageSlides = document.querySelectorAll(".carousel-image");

const REVIEW_TEMPLATE = `
    <div class="review">
        <h2 class="review-user"></h2>
        <p class="review-text"></p>
        
        <p class="review-data"></p>
    </div>
`;

const nextSlideButton = document.querySelector(".carousel-button.next");
const prevSlideButton = document.querySelector(".carousel-button.prev");
const imageCountBlock = document.querySelector(".image-count-block");

const reviewsDiv = document.querySelector("#reviews");

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

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

function updateSlides() {
    imageCountBlock.textContent = `${currentSlide + 1}/${imageSlides.length}`;

    imageSlides.forEach((slide, index) => {
        slide.style.transform = `translateX(${100 * (index - currentSlide)}%)`;
    });
}

async function getReviews(page = 0) {
    let reviews;

    const url = new URL(window.location.origin + `/api/tours/${document.head.getAttribute("idtrip")}/reviews`);

    url.searchParams.append("page", page);
    url.searchParams.append("elementsInPage", 10);

    console.log(url)

    await fetch(url)
        .then(response => response.json())
        .then(data => { reviews = data; });

    return reviews;
}

function makeReviewList(reviews) {
    reviewsDiv.innerHTML = "";

    for (const review of reviews) {
        const divReview = createElementFromHTML(REVIEW_TEMPLATE);

        divReview.querySelector(".review-user").innerText = review.user.surname + " " + review.user.name + " " + review.user.patronymic;
        divReview.querySelector(".review-text").innerText = review.text;
        divReview.querySelector(".review-data").innerText = formatDate(review.date);

        reviewsDiv.appendChild(divReview);
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    updateSlides();

    const reviews = await getReviews();
    makeReviewList(reviews);
});