package com.microservicio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.dao.IUsuarioDao;
import com.microservicio.models.Usuario;
import com.microservicio.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuarioController {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private JWTUtil jwtUtil;

	/**
	 * Recupera todos los usuarios de Base de datos
	 * 
	 * @return lista de usuarios
	 */
	@RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {
		if (!validarToken(token)) {
			return null;
		}

		return usuarioDao.getUsuarios();
	}

	/**
	 * Elimina usuario de Base de datos por ID
	 * 
	 * @param id del usuario
	 */
	@RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
	public void eliminarUsuario(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
		if (!validarToken(token)) {
			return;
		}
		usuarioDao.eliminarUsuario(id);
	}

	/**
	 * Metodo para registrar usuarios
	 * 
	 * @param usuario de registro
	 */
	@RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
	public void registarUsuario(@RequestBody Usuario usuario) {

		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
		usuario.setPassword(hash);

		usuarioDao.registrar(usuario);
	}

	/**
	 * Valida si el token de la sesion es correcto
	 * @param token
	 * @return
	 */
	private boolean validarToken(String token) {
		String usuarioId = jwtUtil.getKey(token);

		return usuarioId != null;

	}
}
