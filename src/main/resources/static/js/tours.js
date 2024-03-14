const tripTemplate = `
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

/* Slider */
const rangeValue = document.querySelector(".slider-container .price-slider");
const rangeInputValue = document.querySelectorAll(".range-input input");

// Set the price gap
let priceGap = 500;

// Adding event listners to price input elements
const priceInputValue = document.querySelectorAll(".price-input input");

for (let i = 0; i < priceInputValue.length; i++) {
    priceInputValue[i].addEventListener("input", e => {

        // Parse min and max values of the range input
        let minp = parseInt(priceInputValue[0].value);
        let maxp = parseInt(priceInputValue[1].value);
        let diff = maxp - minp

        if (minp < 0) {
            alert("minimum price cannot be less than 0");
            priceInputValue[0].value = 0;
            minp = 0;
        }

        // Validate the input values
        if (maxp > 10000) {
            alert("maximum price cannot be greater than 10000");
            priceInputValue[1].value = 10000;
            maxp = 10000;
        }

        if (minp > maxp - priceGap) {
            priceInputValue[0].value = maxp - priceGap;
            minp = maxp - priceGap;

            if (minp < 0) {
                priceInputValue[0].value = 0;
                minp = 0;
            }
        }

        // Check if the price gap is met
        // and max price is within the range
        if (diff >= priceGap && maxp <= rangeInputValue[1].max) {
            if (e.target.className === "min-input") {
                rangeInputValue[0].value = minp;
                let value1 = rangeInputValue[0].max;
                rangeValue.style.left = `${(minp / value1) * 100}%`;
            }
            else {
                rangeInputValue[1].value = maxp;
                let value2 = rangeInputValue[1].max;
                rangeValue.style.right = `${100 - (maxp / value2) * 100}%`;
            }
        }
    });

    // Add event listeners to range input elements
    for (let i = 0; i < rangeInputValue.length; i++) {
        rangeInputValue[i].addEventListener("input", e => {
            let minVal = parseInt(rangeInputValue[0].value);
            let maxVal = parseInt(rangeInputValue[1].value);

            let diff = maxVal - minVal

            // Check if the price gap is exceeded
            if (diff < priceGap) {

                // Check if the input is the min range input
                if (e.target.className === "min-range") {
                    rangeInputValue[0].value = maxVal - priceGap;
                }
                else {
                    rangeInputValue[1].value = minVal + priceGap;
                }
            }
            else {

                // Update price inputs and range progress
                priceInputValue[0].value = minVal;
                priceInputValue[1].value = maxVal;
                rangeValue.style.left = `${(minVal / rangeInputValue[0].max) * 100}%`;
                rangeValue.style.right = `${100 - (maxVal / rangeInputValue[1].max) * 100}%`;
            }
        });
    }
}

Element.prototype.getFirstElementByClassName = function (className) {
    return this.getElementsByClassName(className)[0];
};

let currentPage = 0;

function addThousandsSeparator(number) {
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ");
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return new Intl.DateTimeFormat(document.documentElement.lang, {dateStyle: "long"}).format(date);
}

async function getTrips(page = 0) {
    let trips;

    const url = new URL(window.location.origin + "/api/trips");

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
        const div = createElementFromHTML(tripTemplate);

        div.getFirstElementByClassName("trip-header").innerText = trip.name;
        div.getFirstElementByClassName("trip-info-start").innerText = formatDate(trip.startDate);
        div.getFirstElementByClassName("trip-info-end").innerText = formatDate(trip.endDate);
        div.getFirstElementByClassName("trip-info-price").innerText = addThousandsSeparator(trip.price);

        document.getElementById("trips").appendChild(div);
    }

    currentPage++;
}

function clearTripList() {
    document.getElementById("trips").innerHTML = "";
}

window.addEventListener("load", async () => {
    await makeTripList();
});