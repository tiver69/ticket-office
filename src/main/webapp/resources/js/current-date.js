function getCurrentDate(monthLater) {
    var today = new Date();
    var month = today.getMonth() + 1 + monthLater;
    if (month < 10) month = "0" + month.toString();
    var day = today.getDate();
    if (day < 10) day = "0" + day.toString();
    
    return today.getFullYear() + "-" + month + "-"+ day;
}