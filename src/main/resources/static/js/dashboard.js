getTotalTurns();
getTotalWaiting();


function getTotalTurns() {
    axios.get('https://digital-queue-4040.herokuapp.com/turns/totalTickets').then(function (result) {
        var res = result.data
        //console.log(res)
        var totalTickets = document.getElementById("totalTickets")
        totalTickets.innerHTML = res
    });
}

function getTotalWaiting() {

    axios.get('https://digital-queue-4040.herokuapp.com/turns/totalWaiting').then(function (result) {
        var res = result.data
        //console.log(res)
        var totalWaiting = document.getElementById("totalWaiting")
        totalWaiting.innerHTML = res
    });
}

function getTotalCancelled() {
    /*
    axios.get('https://digital-queue-4040.herokuapp.com/turns/totalWaiting').then(function (result) {
        var res = result.data
        //console.log(res)
        var totalWaiting = document.getElementById("totalWaiting")
        totalWaiting.innerHTML = res
    });
    */
}