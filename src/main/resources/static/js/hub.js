const tripTemplate = `
    <div class="trip">
        <h3 class="trip-header">Trip</h3>
        <p class="trip-description"></p>
    </div>
`;

Element.prototype.getFirstElementByClassName = function (className) {
    return this.getElementsByClassName(className)[0];
};

let currentPage = 0;

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

    for (const trip of trips) {
        const div = createElementFromHTML(tripTemplate);

        div.getFirstElementByClassName("trip-description").innerText = trip.description;
        div.getFirstElementByClassName("trip-header").innerText = trip.name;

        document.getElementById("trips").appendChild(div);
    }

    currentPage++;
}

window.addEventListener("load", async () => {
    await makeTripList();
});