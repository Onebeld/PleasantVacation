const tripTemplate = `
    <div class="trip">
        <h3 class="trip-header">Trip</h3>
        <p class="trip-description"></p>
    </div>
`;

Element.prototype.getFirstElementByClassName = function (className) {
    return this.getElementsByClassName(className)[0];
};

async function getTrips(page = 0) {
    let trips;

    await fetch(window.location.origin + "/api/trips?page=" + page + "&elementsInPage=10")
        .then(res => res.json())
        .then(data => trips = data);

    return trips;
}

async function makeTripList() {
    let trips = await getTrips();
    console.log(trips);

    for (const trip of trips) {
        const newDiv = fromHTML(tripTemplate);

        newDiv.getFirstElementByClassName("trip-description").innerText = trip.description;
        newDiv.getFirstElementByClassName("trip-header").innerText = trip.name;

        document.getElementById("trips").appendChild(newDiv);
    }
}

window.onload = async function () {
    await makeTripList();
}