const tripsDiv = document.getElementById("trips");

document.addEventListener("DOMContentLoaded", async () => {
    const trips = await getTrips("/api/tours/tourmanager");
    await makeTrips(trips, tripsDiv);
});