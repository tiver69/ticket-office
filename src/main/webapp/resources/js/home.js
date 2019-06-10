function onLoadHomePage(){
    loadMenuColor("menu-home");
    document.getElementById("destinationStationSelect").value = 1;
    onDestinationChange();
    document.getElementById("departureStationSelect").value = 2;
    onDepartureChange();

    document.getElementById("dateInput").value = getCurrentDate(0);
    document.getElementById("dateInput").min = getCurrentDate(0);
    document.getElementById("dateInput").max = getCurrentDate(3);
}

function onDestinationChange() {
    var departureOptions = document.getElementById('departureStationSelect').options;
    for (var i=0, iLen=departureOptions.length; i<iLen; i++) {
        var opt = departureOptions[i];
        if (opt.value == document.getElementById('destinationStationSelect').value) {
            opt.hidden = true;
        }
        else {
            opt.hidden = false;
        }
    }
}

function onDepartureChange() {
    var destinationOptions = document.getElementById('destinationStationSelect').options;
    for (var i=0, iLen=destinationOptions.length; i<iLen; i++) {
        var opt = destinationOptions[i];
        if (opt.value == document.getElementById('departureStationSelect').value) {
            opt.hidden = true;
        }
        else {
            opt.hidden = false;
        }
    }
}

function swapStation(){
    var destinationStationId = document.getElementById('destinationStationSelect').value;
    var departureStationId = document.getElementById('departureStationSelect').value;
    
    document.getElementById('departureStationSelect').value = destinationStationId;
    onDepartureChange(document.getElementById('departureStationSelect'));
    
    document.getElementById('destinationStationSelect').value = departureStationId;
    onDestinationChange(document.getElementById('destinationStationSelect'));
}