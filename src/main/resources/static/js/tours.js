const MAX_PRICE = 999999;
const MIN_PRICE = 0;
const PRICE_GAP = 500;

/* Slider */
const rangeValue = document.querySelector(".slider-container .price-slider");
const rangeInputValues = document.querySelectorAll(".range-input input");

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

function formatInput(event) {
    let value = event.target.value.replace(/,/g, '');

    // Преобразование строки в число и обратно в строку с тысячными разделителями
    if (!isNaN(value) && value !== '') {
        event.target.value = Number(value).toLocaleString('en');
    } else {
        event.target.value = '';
    }
}

window.addEventListener("load", async () => {
    await loadTrips("/api/tours")
});

window.addEventListener("DOMContentLoaded", () => {
    rangeValue.style.left = `${(rangeInputValues[0].value / rangeInputValues[0].max) * 100}%`;
    rangeValue.style.right = `${100 - (rangeInputValues[1].value / rangeInputValues[1].max) * 100}%`;

    priceInputValue[0].addEventListener("input", e => {
        formatInput(e);
    });
    priceInputValue[1].addEventListener("input", e => {
        formatInput(e);
    })
});

document.querySelector(".filter-button").addEventListener("click", async () => {
    const filter = {
        minPrice: rangeInputValues[0].value,
        maxPrice: rangeInputValues[1].value
    };

    await loadTrips("/api/tours", filter);
});