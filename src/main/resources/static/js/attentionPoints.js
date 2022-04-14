var loading = document.getElementById('loading');
var mensaje = document.getElementById('mensaje');
var boton = document.getElementById('json_post');

var serviceName = getParameterByName('serviceName');
console.log(serviceName)
if (!serviceName) {
    serviceName = "";
} else {
    serviceName = "?serviceName=" + serviceName;
}
console.log(serviceName)

axios.get('https://digital-queue-4040.herokuapp.com/services')
    .then(response => {
        var services = response.data._embedded.serviceList;

        services.forEach(service => {
            $('#services').append(`<option>` + service.name + `</option>`);
        });
        localStorage.setItem('services', JSON.stringify(services));
    });


axios.get('https://digital-queue-4040.herokuapp.com/users/')
    .then(response => {
        var users = response.data._embedded.userList;

        users.forEach(user => {
            $('#users').append(`<option>` + user.email + `</option>`);
        });
        localStorage.setItem('users', JSON.stringify(users));
    });

axios.get('https://digital-queue-4040.herokuapp.com/attentionPoints'+serviceName)
    .then(response => {
        mydata = response.data;
        mydata = mydata._embedded.attentionPointList;
        console.log(mydata)
        mydata.forEach(attentionPoint => {
            $('#attentionPointsTable').append(`
                <tr>
                    <td>` + attentionPoint.code + `</td>
                    <td>` + (attentionPoint.enable ? 'True' : 'False') + `</td>
                    <td>` + attentionPoint.service.name + `</td>
                    <td>` + attentionPoint.user.name + `</td>
                    <td>
                        <button type="button" onclick="deleteAttentionPoint(`+ attentionPoint.id + `)" class="btn btn-danger">
                            <i class="far fa-trash-alt"></i>
                        </button>
                    </td>

                </tr>

            `)
        });
    })
    .catch(e => {
        // Capturamos los errores
    }
    )

boton.addEventListener('click', function () {
    loading.style.display = 'block';

    var agent = JSON.parse(localStorage.getItem('users')).find(user => {
        return user.email == $('#users').val()
    });
    var service = JSON.parse(localStorage.getItem('services')).find(service => {
        return service.name == $('#services').val()
    })
    var attentionPoint = {
        code: $('#recipient-code').val(),
        user: agent,
        service: service,
        enable: true
    }
    console.log(attentionPoint);
    axios.post('https://digital-queue-4040.herokuapp.com/attentionPoints', attentionPoint)
        .then(res => {
            if (res.status == 201) {
                mensaje.innerHTML = 'El nuevo Post ha sido almacenado con id: ' + res.data.id;
            }
        })
        .catch(function (err) {
            console.log(err);
        })
    setTimeout(() => { window.location.reload(); }, 500);
});


function deleteAttentionPoint(id) {
    axios.delete("https://digital-queue-4040.herokuapp.com/attentionPoints/" + id)
        .then(function (response) {
            setTimeout(() => { window.location.reload(); }, 500);
        })

}


function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}