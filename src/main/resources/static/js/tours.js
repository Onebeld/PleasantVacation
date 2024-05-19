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

const MAX_PRICE = 999999;
const MIN_PRICE = 0;
const PRICE_GAP = 500;

/* Slider */
const rangeValue = document.querySelector(".slider-container .price-slider");
const rangeInputValues = document.querySelectorAll(".range-input input");

let currentPage = 0;

// Adding event listeners to price input elements
const priceInputValue = document.querySelectorAll(".price-input input");

for (let i = 0; i < priceInputValue.length; i++) {
    priceInputValue[i].addEventListener("input", e => {
        // Parse min and max values of the range input
        let minPrice = parseInt(priceInputValue[0].value);
        let maxPrice = parseInt(priceInputValue[1].value);
        let diff = maxPrice - minPrice

        if (minPrice < MIN_PRICE) {
            priceInputValue[0].value = MIN_PRICE;
            minPrice = MIN_PRICE;
        }

        // Validate the input values
        if (maxPrice > MAX_PRICE) {
            priceInputValue[1].value = MAX_PRICE;
            maxPrice = MAX_PRICE;
        }

        if (minPrice > maxPrice - PRICE_GAP) {
            priceInputValue[0].value = maxPrice - PRICE_GAP;
            minPrice = maxPrice - PRICE_GAP;

            if (minPrice < MIN_PRICE) {
                priceInputValue[0].value = MIN_PRICE;
                minPrice = MIN_PRICE;
            }
        }

        // Check if the price gap is met
        // and max price is within the range
        if (diff >= PRICE_GAP && maxPrice <= rangeInputValues[1].max) {
            if (e.target.className === "min-input") {
                rangeInputValues[0].value = minPrice;
                let value1 = rangeInputValues[0].max;
                rangeValue.style.left = `${(minPrice / value1) * 100}%`;
            }
            else {
                rangeInputValues[1].value = maxPrice;
                let value2 = rangeInputValues[1].max;
                rangeValue.style.right = `${100 - (maxPrice / value2) * 100}%`;
            }
        }
    });

    // Add event listeners to range input elements
    for (const rangeInputValue of rangeInputValues) {
        rangeInputValue.addEventListener("input", e => {
            let minValue = parseInt(rangeInputValues[0].value);
            let maxValue = parseInt(rangeInputValues[1].value);

            let diff = maxValue - minValue

            // Check if the price gap is exceeded
            if (diff < PRICE_GAP) {
                // Check if the input is the min range input
                if (e.target.className === "min-range")
                    rangeInputValues[0].value = maxValue - PRICE_GAP;
                else
                    rangeInputValues[1].value = minValue + PRICE_GAP;
            }
            else {
                // Update price inputs and range progress
                priceInputValue[0].value = minValue;
                priceInputValue[1].value = maxValue;
                rangeValue.style.left = `${(minValue / rangeInputValues[0].max) * 100}%`;
                rangeValue.style.right = `${100 - (maxValue / rangeInputValues[1].max) * 100}%`;

                console.log(`${(minValue / rangeInputValues[0].max) * 100}%`);
                console.log(`${100 - (maxValue / rangeInputValues[1].max) * 100}%`);
            }
        });
    }
}

Element.prototype.getFirstElementByClassName = function (className) {
    return this.getElementsByClassName(className)[0];
};

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

async function getTrips(page = 0) {
    let trips;

    const url = new URL(window.location.origin + "/api/tours");

    url.searchParams.append("page", page);
    url.searchParams.append("elementsInPage", 10);

    await fetch(url)
        .then(result => result.json())
        .then(data => trips = data);

    return trips;
}

async function makeTripList() {
    let trips = await getTrips(currentPage);
    console.log(trips);

    for (const trip of trips) {
        const divTrip = createElementFromHTML(TRIP_TEMPLATE);

        divTrip.getFirstElementByClassName("trip-header").innerText = trip.name;
        divTrip.getFirstElementByClassName("trip-info-start").innerText = formatDate(trip.startDate);
        divTrip.getFirstElementByClassName("trip-info-end").innerText = formatDate(trip.endDate);
        divTrip.getFirstElementByClassName("trip-info-price").innerText = addThousandsSeparator(trip.price);

        divTrip.addEventListener("click", () => {
            window.location.href = window.location.origin + "/tours/" + trip.id;
        });

        document.getElementById("trips").appendChild(divTrip);
    }

    currentPage++;
}

function clearTripList() {
    document.getElementById("trips").innerHTML = "";
    currentPage = 0;
}

window.addEventListener("load", async () => {
    await makeTripList();
});

window.addEventListener("DOMContentLoaded", () => {
    rangeValue.style.left = `${(rangeInputValues[0].value / rangeInputValues[0].max) * 100}%`;
    rangeValue.style.right = `${100 - (rangeInputValues[1].value / rangeInputValues[1].max) * 100}%`;
});