axios.get('https://digital-queue-4040.herokuapp.com/services/')
    .then(response => {
        var services = response.data._embedded.serviceList;
        services.forEach(service => {
            $('#services').append(`<option>` + service.name + `</option>`);
        });
        localStorage.setItem('services', JSON.stringify(services));
    })

function saveTurn(turn) {
    axios.post("https://digital-queue-4040.herokuapp.com/turns", turn)
        .then(response => {
            alert('Your turn code is ' + turn.code)
            localStorage.setItem('currentTurn', turn.code);
            window.addEventListener('beforeunload', event => {
                event.preventDefault();
                event.returnValue = '';
            })
            window.addEventListener("unload", event => {
                cancelCurrentTurn();
                event.returnValue = '';
            })
        });
}

function request() {
    var service = JSON.parse(localStorage.getItem('services')).find(service => {
        return service.name == $('#services').val();
    })
    axios.get('https://digital-queue-4040.herokuapp.com/turns/countTurns?service=' + service.name)
        .then(response => {
            var code = service.identifier + (response.data + 1);
            var turn = {
                code: code,
                clientName: $('#name').val(),
                requestedDateTime: new Date(),
                attendedDateTime: null,
                service: service,
                attended: false,
                cancelled: false,
                attentionPoint: null
            }
            saveTurn(turn);
        });
}

function cancelCurrentTurn() {
    axios.put("https://digital-queue-4040.herokuapp.com/turns/cancel/" + localStorage.getItem('currentTurn'));
}