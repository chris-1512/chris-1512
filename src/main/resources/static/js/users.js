
var loading = document.getElementById('loading');
var mensaje = document.getElementById('mensaje');
var boton = document.getElementById('json_post');
boton.addEventListener('click', () => {
    loading.style.display = 'block';
    axios.post('https://digital-queue-4040.herokuapp.com/users', {
        name: $('#name').val(),
        email: $('#email').val(),
        password: $('#password').val(),
    })
        .then(function (res) {
            if (res.status == 201) {
                mensaje.innerHTML = 'El nuevo Post ha sido almacenado con id: ' + res.data.id;
            }
        })
        .catch(function (err) {
            console.log(err);
        })
        .then(function () {
            loading.style.display = 'none';
        });
    setTimeout(() => { window.location.reload(); }, 500);
});

axios.get('https://digital-queue-4040.herokuapp.com/users')
    .then(response => {
        mydata = response.data;
        mydata = mydata._embedded.userList;
        console.log(mydata);
        mydata.forEach(employee => {
            $('#employeeTable').append(`
                <tr>
                    <td>` + employee.name + `</td>
                    <td>` + employee.email + `</td>
                    <td>
                        <button type="button" onclick="deleteUsers(`+ employee.id + `)" class="btn btn-danger">
                            <i class="far fa-trash-alt"></i>
                        </button>
                    </td>

                </tr>
            `)
        });
    })
    .catch(e => {
        // Capturamos los errores
    })


function deleteUsers(id) {
    axios.delete("https://digital-queue-4040.herokuapp.com/users/" + id)
        .then(function (response) {
            setTimeout(() => { window.location.reload(); }, 500);
        })

}