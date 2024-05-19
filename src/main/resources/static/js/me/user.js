const tripsDiv = document.getElementById("trips");

document.addEventListener("DOMContentLoaded", async () => {
    const trips = await getTrips("/api/tours/bought");
    await makeTrips(trips, tripsDiv);
});