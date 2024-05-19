const TRIP_TEMPLATE = `
    <div class="trip">
        <div class="trip-image-block">
            <img class="trip-image" src="" alt="">
        </div>

        <div class="trip-info">
            <h3 class="trip-header">Trip</h3>
            <p class="trip-info-text">Начало: <span class="trip-info-start">2022-01-01</span></p>
            <p class="trip-info-text">Окончание: <span class="trip-info-end">2022-01-01</span></p>

            <p class="trip-info-text-price"><span class="trip-info-price">50 000</span> руб</p>
        </div>
    </div>
`;

/**
 * Creates an element from the given HTML string, optionally trimming whitespace.
 *
 * @param {string} html - the HTML string to create the element from
 * @param {boolean} [trim=true] - flag to indicate whether to trim the HTML string
 * @return {Element|NodeList} the created element or a list of created elements
 */
function createElementFromHTML(html, trim = true) {
    html = trim ? html.trim() : html;
    if (!html) return null;

    const template = document.createElement('template');
    template.innerHTML = html;
    const result = template.content.children;

    if (result.length === 1) return result[0];
    return result;
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

async function getTrips(stringUrl, page = 0) {
    let trips;

    const url = new URL(window.location.origin + stringUrl);

    url.searchParams.append("page", page);
    url.searchParams.append("elementsInPage", 10);

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

        tripDiv.addEventListener("click", () => {
            window.location.href = window.location.origin + "/tours/" + trip.id;
        });

        tripsDiv.appendChild(tripDiv);
    }
}

function openMenu() {
    const selectWrapper = document.querySelector(".select-wrapper");

    if (selectWrapper.style.pointerEvents === "all") {
        selectWrapper.style.opacity = 0;
        selectWrapper.style.pointerEvents = "none";
    }
    else {
        selectWrapper.style.opacity = 1;
        selectWrapper.style.pointerEvents = "all";
    }
}

function closeMenu() {
    const selectWrapper = document.querySelector(".select-wrapper");
    selectWrapper.style.opacity = 0;
    selectWrapper.style.pointerEvents = "none";
}

window.addEventListener("click", (event) => {
    if (!event.target.closest(".nav-button-menu") && !event.target.closest(".select-wrapper")) {
        closeMenu();
    }
});

document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".nav-button-menu").addEventListener("click", openMenu);
});