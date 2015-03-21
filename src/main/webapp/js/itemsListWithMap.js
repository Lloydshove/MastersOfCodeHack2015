var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};
var googleMapMarkers = {};

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

function getCountry(country) {
    geocoder.geocode({'address': country}, function (results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}


function setPositionForDiv(positionData, div) {
    myPosition = positionData;
    div.html("Latitude: " + positionData.coords.latitude +
    "<br>Longitude: " + positionData.coords.longitude);
}


function initialize() {
    var map;

    function deleteMarker(marker) {
        //delete the old marker
        if (typeof marker != 'undefined') {
            marker.setMap(null);
        }
    }

    function setUpListeners() {
        var divPositionList = $("#positionsList");
        /*divPositionList.on("click", "a", function () {
         //var val = parseInt($(this).attr("value"));
         deleteMarker(googleMapMarkers[$(this)]);
         googleMapMarkers.splice(val, 1);
         updateItemList();
         });*/
        function removeFromItemList(marker, button, item) {
            return function () {
                deleteMarker(marker);
                button.parent().remove();
                item.remove();
            };

            //function(){
            //    deleteMarker(googleMapMarkers[button]);
            //    delete googleMapMarkers[button];
            //    button.parent().remove();
            //    item.parent().remove();
            //}
        }

        google.maps.event.addListener(map, 'click',
            function (event) {
                function addMarker(latLng) {
                    var marker = new google.maps.Marker({position: latLng, map: map});

                    var item1 = '';
                    item1 = $('<div class="col-md-6">' +
                    'Position ' + latLng.lat() + "_" + latLng.lng() +
                    '</div>');

                    item1.appendTo(divPositionList);

                    var item2 = $('<div class="col-md-2"></div>');

                    var deleteButton = $('<a class="btn btn-danger" value="'+'Position' + latLng.lat() + "_" + latLng.lng() +'">Delete</a>');

                    deleteButton.click(function (e) {
                        e.preventDefault();
                        removeFromItemList(marker, $(this), item1)();
                        //googleMapMarkers.splice(val, 1);
                    });

                    deleteButton.appendTo(item2);




                    item2.appendTo(divPositionList);
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
    //setUpDirectionDisplay(map);

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
