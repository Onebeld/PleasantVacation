const tripTemplate = `
    <div class="trip">
        <h3 class="trip-header">Trip</h3>
        <p class="trip-description"></p>
    </div>
`;

Element.prototype.getFirstElementByClassName = function (className) {
    return this.getElementsByClassName(className)[0];
};

async function getTrips() {
    const response = await fetch("/hub/trips?page=0&elementsInPage=10");

    if (!response.ok) {
        throw new Error(response.statusText);
    }

    return await response.json();
}

async function makeTripList() {
    let trips = await getTrips();

    for (const trip in trips) {
        const newDiv = fromHTML(tripTemplate);

        newDiv.getFirstElementByClassName("trip-description").innerText = trip.description;
        newDiv.getFirstElementByClassName("trip-header").innerText = trip.name;

        document.getElementById("trips").appendChild(newDiv);
    }
}

await makeTripList();