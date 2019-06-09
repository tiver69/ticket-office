function setCurrentUpdate(currentUpdatedId) {
    var id =  "currentUpdate"+currentUpdatedId;
    document.getElementById(id).style.display='inherit';
    document.getElementById("currentUpdateInfo"+currentUpdatedId).style.display='none';
}

function unsetCurrentUpdate(currentUpdatedId) {
    var id =  "currentUpdate"+currentUpdatedId;
    document.getElementById(id).style.display='none';
    document.getElementById("currentUpdateInfo"+currentUpdatedId).style.display='inherit';
}