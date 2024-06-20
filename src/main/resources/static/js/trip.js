let currentSlide = 0;

let imageSlides = document.querySelectorAll(".carousel-image");

const REVIEW_TEMPLATE = `
    <div class="review">
        <div class="profile">
            <span class="material-symbols-rounded md-24">
                person
            </span>
        </div>
        
        <div class="review-info">
            <div class="review-title">
                <h3 class="review-user"></h3>
                <p class="review-data"></p>      
            </div>
            
            <div class="trip-info review">
                <p class="review-text"></p>
            </div>
        </div>
    </div>
`;

const nextSlideButton = document.querySelector(".carousel-button.next");
const prevSlideButton = document.querySelector(".carousel-button.prev");
const imageCountBlock = document.querySelector(".image-count-block");

const reviewsContainer = document.querySelector(".reviews-container");
const reviewsDiv = document.querySelector("#reviews");
const reviewsButtonsDiv = document.querySelector("#reviews-buttons");
const reviewsEmpty = document.querySelector("#reviews-empty"); // reviewsEmpty

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

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

function formatTimestamp(dateString) {
    const date = new Date(dateString);

    const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: "numeric",
        minute: "numeric",
        second: "numeric"
    };

    return new Intl.DateTimeFormat(document.documentElement.lang, options).format(date);
}

function createCalendarEvent(title, description, startTime, endTime) {
    startTime = new Date(startTime);
    endTime = new Date(endTime);

    let href = encodeURI(
        'data:text/calendar;charset=utf8,' + [
            'BEGIN:VCALENDAR',
            'VERSION:2.0',
            'BEGIN:VEVENT',
            'URL:' + document.URL,
            'SUMMARY:' + title,
            'DESCRIPTION:' + description,
            'DTSTART:' + startTime.toISOString().replace(/-|:|\.\d\d\d/g, ""),
            'DTEND:' + endTime.toISOString().replace(/-|:|\.\d\d\d/g, ""),
            'LOCATION:' + document.querySelector("#trip-country-span").innerText + ", " + document.querySelector("#trip-city-span").innerText,
            'END:VEVENT',
            'END:VCALENDAR'
        ].join('\n'));

    return href;
}

function updateSlides() {
    imageCountBlock.textContent = `${currentSlide + 1}/${imageSlides.length}`;

    imageSlides.forEach((slide, index) => {
        slide.style.transform = `translateX(${100 * (index - currentSlide)}%)`;
    });
}

async function getReviewsDto(page = 0) {
    let reviews;

    const url = new URL(window.location.origin + `/api/tours/${document.head.getAttribute("idtrip")}/reviews`);

    url.searchParams.append("page", page);
    url.searchParams.append("elementsInPage", 10);

    await fetch(url)
        .then(response => response.json())
        .then(data => { reviews = data; });

    return reviews;
}

function makeReviewList(reviewsDto) {
    reviewsDiv.innerHTML = "";

    for (const review of reviewsDto.reviews) {
        const divReview = createElementFromHTML(REVIEW_TEMPLATE);

        divReview.querySelector(".review-user").innerText = review.user.surname + " " + review.user.name + " " + review.user.patronymic;
        divReview.querySelector(".review-text").innerText = review.text;
        divReview.querySelector(".review-data").innerText = formatTimestamp(review.date);

        reviewsDiv.appendChild(divReview);
    }
}

async function loadReviewsButton(page) {
    reviewsContainer.classList.add("loading");

    const reviewsDto = await getReviewsDto(page);
    await makeReviewList(reviewsDto, reviewsDiv);

    reviewsContainer.classList.remove("loading");
}

function createReviewsButtons(reviewsDto) {
    reviewsButtonsDiv.innerHTML = "";

    for (let i = 0; i < reviewsDto.totalPages; i++) {
        const button = document.createElement("button");
        button.innerText = i + 1;

        button.addEventListener("click", async () => {
            if (button.classList.contains("active")) {
                return;
            }

            for (const button of reviewsButtonsDiv.children) {
                button.classList.remove("active");
            }

            button.classList.add("active");

            await loadReviewsButton(i);
            document.querySelector(".reviews-container").scrollIntoView({ behavior: "smooth", block: "start" });
        });

        reviewsButtonsDiv.appendChild(button);
    }

    if (reviewsButtonsDiv.children.length > 0)
        reviewsButtonsDiv.children[0].classList.add("active");
}

document.addEventListener("DOMContentLoaded", async () => {
    document.querySelector("#startDate").innerText = formatDate(document.head.getAttribute("startDate"));
    document.querySelector("#endDate").innerText = formatDate(document.head.getAttribute("endDate"));

    updateSlides();

    reviewsContainer.classList.add("loading");

    const reviewsDto = await getReviewsDto();
    makeReviewList(reviewsDto);
    createReviewsButtons(reviewsDto);

    if (reviewsDto.reviews.length === 0)
        reviewsEmpty.classList.add("show");
    else
        reviewsEmpty.classList.remove("show");

    reviewsContainer.classList.remove("loading");

    const price = document.querySelector("#price");
    price.innerText = addThousandsSeparator(price.innerText);

    const totalEarned = document.querySelector("#totalEarnedSpan");
    if (totalEarned !== null)
        totalEarned.innerText = addThousandsSeparator(totalEarned.innerText);

    const deleteTripForm = document.querySelector("#delete-trip-form");
    if (deleteTripForm !== null) {
        deleteTripForm.addEventListener('submit', function (e) {
            if (!confirm(translates["trip-delete-message"]))
                e.preventDefault();
        });
    }

    const addToCalendar = document.querySelector("#add-to-calendar");
    if (addToCalendar !== null) {
        const title = document.querySelector(".title-trip").innerText;
        const description = document.querySelector(".trip-info.description p").innerText;

        addToCalendar.href = createCalendarEvent(title, description, document.head.getAttribute("startDate"), document.head.getAttribute("endDate"));
        addToCalendar.download = 'event.ics';
    }
});