var map;
var marker;
var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};

function convertPositionToGoogleLatLng(myPosition) {
    return new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude);
}

function initialize() {

    function setMarker(currPos) {
        marker = new google.maps.Marker({
            position: currPos,
            map: map
        });
    }


    function setUpListeners() {
        google.maps.event.addListener(map, 'click',
            function (event) {
                console.log("lat:" + event.latLng.lat() + "," + "long:" + event.latLng.lng());
                if (typeof marker != 'undefined') {
                    marker.setMap(null);
                }
                marker = new google.maps.Marker({position: event.latLng, map: map});
                //calcRoute(event.latLng);
            });
    }

    function getCurrentLocation() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (positionData) {
                    myPosition['coords'] = positionData.coords;
                    var mapProp = {
                        center: convertPositionToGoogleLatLng(myPosition),
                        zoom: 14,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

                    setMarker(convertPositionToGoogleLatLng(myPosition));
                    setUpListeners();
                }
            );
        } else {
            console.log("Geolocation is not supported by this browser.");
        }
    }

    getCurrentLocation();
}
google.maps.event.addDomListener(window, 'load', initialize);

function loadScript() {
    var script = document.createElement("script");
    script.src = "http://maps.googleapis.com/maps/api/js?callback=initialize";
    document.body.appendChild(script);
}

$(document).ready(function () {
        $('#btnUseCurrentPosition').click(function () {
            if (typeof window.opener != undefined) {
                if (window.opener !==  null) {
                    window.opener.$("[name=xPosition]").val(myPosition.coords.latitude);
                    window.opener.$("[name=yPosition]").val(myPosition.coords.longitude)
                    self.close();
                }
            }
        })
    }
);
