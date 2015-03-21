var map;
var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};

function getCountry(results) {
    var geocoderAddressComponent,addressComponentTypes,address;
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

function initialize() {
    function setUpListeners() {
        google.maps.event.addListener(map, 'click',
            function (event) {
                console.log("lat:" + event.latLng.lat() + "," + "long:" + event.latLng.lng());
                //call function to create marker
                //$("#coordinate").val(event.latLng.lat() + ", " + event.latLng.lng());
                //$("#coordinate").select();
                //delete the old marker
                if (typeof marker != 'undefined') {
                    marker.setMap(null);
                }
                //creer à la nouvelle emplacement
                marker = new google.maps.Marker({position: event.latLng, map: map});
                myPosition.coords.latitude=event.latLng.lat();
                myPosition.coords.longitude=event.latLng.lng();
                setPositionForDiv(myPosition, $("#divPosition"));
            });
    }

    var mapProp = {
        center: new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude),
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

    setUpListeners();


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
