"use strict"
var container = document.getElementById('map');
var options = {
    center: new kakao.maps.LatLng(36.337767, 127.457887),
    level: 3
};
var map = new kakao.maps.Map(container, options);