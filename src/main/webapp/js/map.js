var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};

function initialize() {
    var mapProp = {
        center: new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude),
        zoom: 10,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
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

                    var x = $("#divPosition");

                    function getLocation() {
                        if (navigator.geolocation) {
                            navigator.geolocation.getCurrentPosition(showPosition);
                        } else {
                            x.html("Geolocation is not supported by this browser.");
                        }
                    }

                    function showPosition(position) {
                        myPosition = position;
                        x.html("Latitude: " + position.coords.latitude +
                        "<br>Longitude: " + position.coords.longitude);
                    }

                    getLocation();
                });
        }


        function setUpListeners() {
            google.maps.event.(googleMap, 'click',
                function clickMapForPosition(event) {
                    console.log("lat:" + event.latLng.lat() + "," + "long:" + event.latLng.lng());
                });
        }

        setUpButtons();
        setUpListeners();
    }
);
