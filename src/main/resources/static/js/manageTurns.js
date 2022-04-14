var botonNextTurn = document.getElementById('nextTurn');
var botonTurnCompleted = document.getElementById('turnCompleted');
var botonCancelTurn = document.getElementById('cancelTurn');


var attentionPoint;
var service;
var turn;
localStorage.setItem('turn', "")
var attentionPointId = getParameterByName('id');

setTimeout(axios.get('https://digital-queue-4040.herokuapp.com/attentionPoints/' + attentionPointId)
.then(response => {
    attp = response.data;
    localStorage.setItem('attentionPoint', JSON.stringify(attp))
})
.catch(e => {
    //+console.log('Error en el Axios gonorrea');
    console.log(e);
}), 1000);

attentionPoint = JSON.parse(localStorage.getItem('attentionPoint'));
service = attentionPoint.service;

getNumTurns();

botonNextTurn.addEventListener('click', function () {
    if (!localStorage.getItem("turn")) {
        axios.get('https://digital-queue-4040.herokuapp.com/turns/next?service=' + service.name).then(function (result) {
            var res = result.data
            if (res) {
                localStorage.setItem('turn', JSON.stringify(res));
                alert("Next turn " + res.code + " customer's name " + res.clientName) 
            } else {
                alert("There are no more turns.")
            }
            getNumTurns();
        })
    } else {
        alert("first cancel or complete your current turn");
    }
});

botonTurnCompleted.addEventListener('click', function () {
    if (localStorage.getItem("turn")) {
        localStorage.setItem('turn', "")    
        alert("Completed turn");
    } else {
        alert("you don't have a turn to complete");
    }
    
    getNumTurns();
    

});

botonCancelTurn.addEventListener('click', function () {
    if (localStorage.getItem('turn') != "") {
        var turnToCancel = JSON.parse(localStorage.getItem("turn"));
        axios.put("https://digital-queue-4040.herokuapp.com/turns/cancel/" + turnToCancel.code).then(function (response) {
            localStorage.setItem('turn', "")
            alert("The turn " + turnToCancel.code + " has been cancelled")

        });
    } else {
        alert("There's no turn to cancel.")
    }
    getNumTurns();

});

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}



function getNumTurns() {
    axios.get('https://digital-queue-4040.herokuapp.com/turns/totalWaitingByQueue?service=' + service.name).then(function (result) {
        var res = result.data;
        var totalWaiting = document.getElementById("totalWaiting");
        totalWaiting.innerHTML = res;
    });
}

