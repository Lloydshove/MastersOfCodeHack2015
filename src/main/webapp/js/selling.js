var map;
var marker;
var supermarketIcon = "../img/bank.png";
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
                        zoom: 12,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };
                    map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

                    setMarker(convertPositionToGoogleLatLng(myPosition));
                    //setMarkerWithIcon(convertPositionToGoogleLatLng(myPosition), supermarketIcon);

                    $.ajax({
                        url: 'http://localhost:8080/dynamic/api/findAllFurther?xPosition=' + myPosition['coords'].longitude + '&yPosition=' + myPosition['coords'].latitude + '&start=01011000010101&end=01011300010101&buyOrSell=BUY',
                        method: "POST"
                    }).done(function (msg) {
                        renderSelling(msg);
                    });

                    //setUpListeners();
                }
            );
        } else {
            console.log("Geolocation is not supported by this browser.");
            $.ajax({
                url: 'http://localhost:8080/dynamic/api/findAllFurther?xPosition=' + myPosition['coords'].longitude + '&yPosition=' + myPosition['coords'].latitude + '&start=01011000010101&end=01011300010101&buyOrSell=BUY',
                method: "POST"
            }).done(function (msg) {
                renderSelling(msg);
            });
        }
    }


    getCurrentLocation();
}
google.maps.event.addDomListener(window, 'load', initialize);

$(document).ready(function () {


    }
);

function loadPreviewImage(result, button, generatedMarker) {
    var imageUrl = 'upload/' + result.id + '.jpg';
    return $.ajax({
        url: imageUrl,
        type: 'HEAD',
        error: function () {
            //do something depressing
        },
        success: function () {
            button.find("img").attr("src", imageUrl);

            var imgIcon = {
                url: imageUrl, // url
                scaledSize: new google.maps.Size(32, 32), // scaled size
                origin: new google.maps.Point(0, 0), // origin
                anchor: new google.maps.Point(16, 32) // anchor
            };
            generatedMarker.setIcon(imgIcon);
        }
    });
}

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

function renderSelling(jsonResponse) {
    //alert(JSON.stringify(jsonResponse));
    var results = jsonResponse;
    var sellingList = $("#sellingList");
    sellingList.append('<ul class="list-group">');
    $.each(results, function (key, result) {
        // alert('keyword: ' + result.keyword + ' cust:' + result.customer);
        var button = $('<div class="list-group-item">' +
        '<span class="col-md-3 pull-right"><img src="../img/No_Image.png"></span>' +
        '<h4 class="list-group-item-heading">' + result.title + '</h4>' +
        '<p class="list-group-item-text">Seller : ' + 'You' + '</p>' +
        '</a>');
        button.appendTo($("#sellingList"));

        var generatedMarker = setMarkerWithIcon(new google.maps.LatLng(result.yPosition, result.xPosition), supermarketIcon);
        //var generatedMarker = setMarker(new google.maps.LatLng(result.yPosition, result.xPosition));
        loadPreviewImage(result, button, generatedMarker);


        var infowindow = new google.maps.InfoWindow();
        //infowindow.setContent("Click here for details - " + result.title + " by " + result.customer);

        var url = 'listing.html?id=' + result.id;
        infowindow.setContent('<a href="' + url + '" class="btn btn-primary">' +
        'Click here for details - ' + result.title +
        '</a>');

        google.maps.event.addListener(infowindow, 'closeclick', function () {
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
    sellingList.append('</ul>');
}