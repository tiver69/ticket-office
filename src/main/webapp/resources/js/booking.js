function onDestinationChange(sel) {
    document.getElementById('destinationHidden').value = sel.value;
    var departureOptions = document.getElementById('departureStationSelectItem').options;
    for (var i=0, iLen=departureOptions.length; i<iLen; i++) {
        var opt = departureOptions[i];
        if (opt.value == document.getElementById('destinationHidden').value) {
            opt.hidden = true;
        }
        else {
            opt.hidden = false;
        }
    }
}

function onDepartureChange(sel) {
    document.getElementById('departureHidden').value = sel.value;
    var destinationOptions = document.getElementById('destinationStationSelectItem').options;
    for (var i=0, iLen=destinationOptions.length; i<iLen; i++) {
        var opt = destinationOptions[i];
        if (opt.value == document.getElementById('departureHidden').value) {
            opt.hidden = true;
        }
        else {
            opt.hidden = false;
        }
    }
}

function onDateChange(sel) {
    document.getElementById('dateHidden').value = sel.value;
}

function swapStation() {
    var destinationStationId = document.getElementById('destinationHidden').value;
    var departureStationId = document.getElementById('departureHidden').value;
    
    document.getElementById('departureStationSelectItem').value = destinationStationId;
    onDepartureChange(document.getElementById('departureStationSelectItem'));
    
    document.getElementById('destinationStationSelectItem').value = departureStationId;
    onDestinationChange(document.getElementById('destinationStationSelectItem'));
}