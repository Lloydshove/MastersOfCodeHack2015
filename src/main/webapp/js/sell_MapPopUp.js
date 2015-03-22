var map;
var marker;
var myPosition = {};

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
                myPosition.coords = {
                    latitude: event.latLng.lat(),
                    longitude: event.latLng.lng()
                };

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
            myPosition = {
                coords: {
                    latitude: 22,
                    longitude: 110
                }
            };
            var mapProp = {
                center: convertPositionToGoogleLatLng(myPosition),
                zoom: 14,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            map = new google.maps.Map(document.getElementById("googleMap"), mapProp);

            setMarker(convertPositionToGoogleLatLng(myPosition));
            setUpListeners();
        }
    }

    if ((typeof myPosition.coords) == 'undefined') {
        getCurrentLocation();
    }
}
//google.maps.event.addDomListener(window, 'load', initialize);
var lightBox = $('#mapLightbox');
function popUpMapPage() {
    //window.open("map.html", "_blank", "height=400,width=400, status=yes,toolbar=no,menubar=no,location=no");
    console.log('a');
    lightBox.modal(/*{
     backdrop: true,
     keyboard: true,
     show: true
     }*/);
    lightBox.modal('show');
    initialize();
}

$(document).ready(function () {


    //var divXPosition = $('[name="xPosition"]');
    //var divYPosition = $('[name="yPosition"]');
    //google.maps.event.addDomListener(divXPosition, 'click', initialize);
    //google.maps.event.addDomListener(divYPosition, 'click', initialize);
    //divXPosition.click(popUpMapPage);
    //divYPosition.click(popUpMapPage);

    $('#btnUseCurrentPosition').click(function () {
        //if (typeof window.opener != undefined) {
        //    if (window.opener !==  null) {
        //        window.opener.$("[name=xPosition]").val(myPosition.coords.latitude);
        //        window.opener.$("[name=yPosition]").val(myPosition.coords.longitude)
        //self.close();
        //}
        //}
        $("[name=xPosition]").val(myPosition.coords.latitude);
        $("[name=yPosition]").val(myPosition.coords.longitude);
        $("[name=xPosition]").change();
        $("[name=yPosition]").change();

        $('#mapLightbox').modal('hide');
    });

    $('.modal').on('shown.bs.modal', function(){
        if ((typeof map) !== 'undefined') {
            google.maps.event.trigger(map, 'resize');
            map.setCenter(convertPositionToGoogleLatLng(myPosition));
        }
    });

});