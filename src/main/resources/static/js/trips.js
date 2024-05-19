const TRIP_TEMPLATE = `
    <div class="trip">
        <div class="trip-image-block">
            <img class="trip-image" src="" alt="">
        </div>

        <div class="trip-info">
            <h3 class="trip-header">Trip</h3>
            <p class="trip-info-text"><span class="trip-info-start-title"></span>: <span class="trip-info-start">2022-01-01</span></p>
            <p class="trip-info-text"><span class="trip-info-end-title"></span>: <span class="trip-info-end">2022-01-01</span></p>

            <p class="trip-info-text-price"><span class="trip-info-price">50 000</span> RUB</p>
        </div>
    </div>
`;

let url;

const elementsInPage = 9;

const tripsDiv = document.getElementById("trips");
const tripsButtonsDiv = document.getElementById("trips-buttons");
const tripsContainer = document.getElementById("trips-container");
const tripsNoElements = document.getElementById("trips-empty");

const tripsCount = document.getElementById("trips-count");
const tripsTotal = document.getElementById("trips-total");

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

async function getTripsDto(stringUrl, page = 0, elementsInPage = 10, filter = null) {
    let trips;

    const url = new URL(window.location.origin + stringUrl);

    url.searchParams.append("page", page);
    url.searchParams.append("elementsInPage", elementsInPage);

    if (filter) {
        for (const [key, value] of Object.entries(filter)) {
            url.searchParams.append(key, value);
        }
    }

    await fetch(url)
        .then(result => result.json())
        .then(data => trips = data);

    return trips;
}

async function makeTrips(trips, tripsDiv) {
    tripsDiv.innerHTML = "";

    for (const trip of trips) {
        const tripDiv = createElementFromHTML(TRIP_TEMPLATE);

        tripDiv.querySelector(".trip-image").src = trip.image;
        tripDiv.querySelector(".trip-header").innerText = trip.name;
        tripDiv.querySelector(".trip-info-start").innerText = formatDate(trip.startDate);
        tripDiv.querySelector(".trip-info-end").innerText = formatDate(trip.endDate);
        tripDiv.querySelector(".trip-info-price").innerText = addThousandsSeparator(trip.price);
        tripDiv.querySelector(".trip-image").src = trip.image;

        tripDiv.querySelector(".trip-info-start-title").innerText = tripsContainer.getAttribute("translate-start-date");
        tripDiv.querySelector(".trip-info-end-title").innerText = tripsContainer.getAttribute("translate-end-date");

        tripDiv.addEventListener("click", () => {
            window.location.href = window.location.origin + "/tours/" + trip.id;
        });

        tripsDiv.appendChild(tripDiv);
    }
}

function createButtons(tripsDto) {
    tripsButtonsDiv.innerHTML = "";

    for (let i = 0; i < parseInt(tripsDto.totalPages); i++) {
        const button = document.createElement("button");

        button.innerText = i + 1;
        button.addEventListener("click", () => {
            if (button.classList.contains("active")) {
                return;
            }

            for (const button of tripsButtonsDiv.children) {
                button.classList.remove("active");
            }

            button.classList.add("active");

            loadTripsButton(i, elementsInPage);

            window.scrollTo({
                top: 0,
                left: 0,
                behavior: 'smooth'
            });
        });
        tripsButtonsDiv.appendChild(button);
    }

    if (tripsButtonsDiv.children.length > 0)
        tripsButtonsDiv.children[0].classList.add("active");
}

async function loadTripsButton(page, elementsInPage) {
    tripsContainer.classList.add("loading");

    const tripsDto = await getTripsDto(url, page, elementsInPage);
    await makeTrips(tripsDto.trips, tripsDiv);

    tripsCount.innerText = tripsDto.trips.length;
    tripsTotal.innerText = tripsDto.totalElements;

    tripsContainer.classList.remove("loading");
}

async function loadTrips(tripsUrl, filter = null) {
    url = tripsUrl;

    tripsContainer.classList.add("loading");

    const tripsDto = await getTripsDto(url, 0, elementsInPage, filter);

    await makeTrips(tripsDto.trips, tripsDiv);
    createButtons(tripsDto);

    tripsCount.innerText = tripsDto.trips.length;
    tripsTotal.innerText = tripsDto.totalElements;

    if (tripsDto.trips.length === 0)
        tripsNoElements.classList.add("show");
    else tripsNoElements.classList.remove("show");

    tripsContainer.classList.remove("loading");
}