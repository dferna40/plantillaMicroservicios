// Call the dataTables jQuery plugin
$(document).ready(function() {
	// on ready
});


async function registrarUsuario() {

	let datos = {};
	datos.nombre = document.getElementById('txtNombre').value;
	datos.apellido = document.getElementById('txtApellidos').value;
	datos.email = document.getElementById('txtEmail').value;
	datos.password = document.getElementById('txtContrasena').value;
	
	let repetirPassword = document.getElementById('txtContrasena2').value;
	
	if(repetirPassword != datos.password) {
		alert('La contraseña que escribiste es diferente')
		return;
	}

	const request = await fetch('api/usuarios', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(datos)
	});
	alert("La cuenta fue creada con exito!");
	window.location.href = 'login.html';
}

