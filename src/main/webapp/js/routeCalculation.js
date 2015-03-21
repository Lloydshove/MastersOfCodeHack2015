var directionsDisplay;
var directionsService = new google.maps.DirectionsService();

function setUpDirectionDisplay(map) {
    directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);
}

function calcRoute(start, end) {
    //var start = new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude);
    //var end = endpoint;
    var request = {
        origin: start,
        destination: end,
        travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request, function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
}