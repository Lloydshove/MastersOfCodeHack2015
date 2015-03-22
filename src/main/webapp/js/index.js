var map;
var marker;
var supermarketIcon = "../img/supermarket.png";
var currentlyOpenedInfoWindow;

var myPosition = {
    coords: {
        latitude: 22,
        longitude: 114
    }
};

function setMarker(currPos) {
    marker = new google.maps.Marker({
        position: currPos,
        map: map
    });
}

function setMarkerWithIcon(pos, icon) {
    return marker = new google.maps.Marker({
        position: pos,
        map: map,
        icon: icon
    });
}

function convertPositionToGoogleLatLng(myPosition) {
    return new google.maps.LatLng(myPosition.coords.latitude, myPosition.coords.longitude);
}

function initialize() {

    /*   function setUpListeners() {
     google.maps.event.addListener(map, 'click',
     function (event) {
     console.log("lat:" + event.latLng.lat() + "," + "long:" + event.latLng.lng());
     if (typeof marker != 'undefined') {
     marker.setMap(null);
     }
     marker = new google.maps.Marker({position: event.latLng, map: map});
     //calcRoute(event.latLng);
     });
     }*/

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
                    //setMarkerWithIcon(convertPositionToGoogleLatLng(myPosition), supermarketIcon);

                    $.ajax({
                        url: 'http://localhost:8080/dynamic/api/findAllNearby?xPosition=' + myPosition['coords'].longitude + '&yPosition=' + myPosition['coords'].latitude + '&start=01011000010101&end=01011300010101&buyOrSell=BUY',
                        method: "POST"
                    }).done(function (msg) {
                        renderNearby(msg);
                    });

                    //setUpListeners();
                }
            );
        } else {
            console.log("Geolocation is not supported by this browser.");
            $.ajax({
                url: 'http://localhost:8080/dynamic/api/findAllNearby?xPosition=' + myPosition['coords'].longitude + '&yPosition=' + myPosition['coords'].latitude + '&start=01011000010101&end=01011300010101&buyOrSell=BUY',
                method: "POST"
            }).done(function (msg) {
                renderNearby(msg);
            });
        }
    }


    getCurrentLocation();
}
google.maps.event.addDomListener(window, 'load', initialize);

$(document).ready(function () {


    }
);

function highlightOneButton(button) {
    $(".highlighted").removeClass("highlighted");
    button.addClass("highlighted");
}

function setInfoWindowForMarker(infowindow, marker) {
    if (typeof currentlyOpenedInfoWindow !== 'undefined') {
        currentlyOpenedInfoWindow.close();
    }
    infowindow.open(map, marker);
    currentlyOpenedInfoWindow = infowindow;
}

function goToListingPage(url) {
    document.location.href = url;
}

function renderNearby(jsonResponse) {
    //alert(JSON.stringify(jsonResponse));
    var results = jsonResponse;
    var nearbyList = $("#nearbyList");
    nearbyList.append('<ul class="list-group">');
    $.each(results, function (key, result) {
        // alert('keyword: ' + result.keyword + ' cust:' + result.customer);
        var url = 'listing.html?id=' + result.id;

        var button = $('<a href="' + url + '" class="list-group-item">' +
        '<h4 class="list-group-item-heading">' + result.title + '</h4>' +
        '<p class="list-group-item-text">Seller : ' + result.customer + '</p>' +
        '</a>');
        button.appendTo($("#nearbyList"));

        var generatedMarker = setMarkerWithIcon(new google.maps.LatLng(result.yPosition, result.xPosition), supermarketIcon);
        //var generatedMarker = setMarker(new google.maps.LatLng(result.yPosition, result.xPosition));

        var infowindow = new google.maps.InfoWindow();
        //infowindow.setContent("Click here for details - " + result.title + " by " + result.customer);
        infowindow.setContent('<a href="' + url + '" class="btn btn-primary">' +
        'Click here for details - ' + result.title + ' by ' + result.customer +
        '</a>');

        google.maps.event.addListener(infowindow,'closeclick',function(){
            $(".highlighted").removeClass("highlighted");
        });

        /*google.maps.event.addListener(infowindow, "click", function (e) {
            e.preventDefault();
            goToListingPage(url);`
        });*/

        google.maps.event.addListener(generatedMarker, "click", function () {
            highlightOneButton(button);
            return setInfoWindowForMarker(infowindow, generatedMarker);
        });

        /*
         google.maps.event.addListener(generatedMarker, "mouseover", function () {
         //button.addClass("highlighted");
         });

         google.maps.event.addListener(generatedMarker, "mouseout", function () {
         //$(".highlighted").removeClass("highlighted");
         });
         */

    });
    nearbyList.append('</ul>');
}