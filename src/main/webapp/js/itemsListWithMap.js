var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};
var googleMapMarkers = [];

var directionsDisplay;
var directionsService = new google.maps.DirectionsService();

function setUpDirectionDisplay(map) {
    directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(map);
}

function calcRoute(endpoint) {
    var start = new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude);
    var end = endpoint;
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


function getCountry(results) {
    var geocoderAddressComponent, addressComponentTypes, address;
    for (var i in results) {
        geocoderAddressComponent = results[i].address_components;
        for (var j in geocoderAddressComponent) {
            address = geocoderAddressComponent[j];
            addressComponentTypes = geocoderAddressComponent[j].types;
            for (var k in addressComponentTypes) {
                if (addressComponentTypes[k] == 'country') {
                    return address;
                }
            }
        }
    }
    return 'Unknown';
}

function setPositionForDiv(positionData, div) {
    myPosition = positionData;
    div.html("Latitude: " + positionData.coords.latitude +
    "<br>Longitude: " + positionData.coords.longitude);
}

function deleteMarker(marker) {
    //delete the old marker
    if (typeof marker != 'undefined') {
        marker.setMap(null);
    }
}

function initialize() {
    var map;

    function setUpListeners() {
        var divPositionList = $("#positionsList");
        divPositionList.on("click", "a", function () {
            var val = parseInt($(this).attr("value"));
            deleteMarker(googleMapMarkers[val]);
            googleMapMarkers.splice(val, 1);
            updateItemList();
        });
        function updateItemList() {
            var item = '';
            divPositionList.html('');
            for (var i = 0; i < googleMapMarkers.length; i++)
                  item +=
                     '<div class="col-md-6">' +
                     'Position ' + (i+1) +
                     '</div>' +
                     '<div class="col-md-2">' +
                     '<a class="btn btn-danger" value="' + i + '">Delete</a>' +
                     '</div>';
            divPositionList.append(item);
        }

        google.maps.event.addListener(map, 'click',
            function (event) {
                function addMarker(latLng) {
                    googleMapMarkers.push(new google.maps.Marker({position: latLng, map: map}));
                    updateItemList();
                }
                console.log("lat:" + event.latLng.lat() + "," + "long:" + event.latLng.lng());
                addMarker(event.latLng);


                //call function to create marker
                //$("#coordinate").val(event.latLng.lat() + ", " + event.latLng.lng());
                //$("#coordinate").select();
                //creer à la nouvelle emplacement
                //myPosition.coords.latitude=event.latLng.lat();
                //myPosition.coords.longitude=event.latLng.lng();
                //setPositionForDiv(myPosition, $("#divPosition"));
                //calcRoute(event.latLng);

            });
    }

    var mapProp = {
        center: new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude),
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

    setUpListeners();
    setUpDirectionDisplay(map);

}
google.maps.event.addDomListener(window, 'load', initialize);

function loadScript() {
    var script = document.createElement("script");
    script.src = "http://maps.googleapis.com/maps/api/js?callback=initialize";
    document.body.appendChild(script);
}

$(document).ready(function () {
        function setUpButtons() {
            //$("#btnLoadGoogleMap").click(loadScript);
            $("#btnUseCurrentPosition").click(
                function () {
                    var divPosition = $("#divPosition");

                    function getLocation() {
                        if (navigator.geolocation) {
                            navigator.geolocation.getCurrentPosition(function (positionData) {
                                    setPositionForDiv(positionData, divPosition);
                                }
                            );
                        } else {
                            divPosition.html("Geolocation is not supported by this browser.");
                        }
                    }

                    getLocation();
                });
        }


        setUpButtons();
    }
);
