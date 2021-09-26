package com.microservicio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.dao.IUsuarioDao;
import com.microservicio.models.Usuario;
import com.microservicio.utils.JWTUtil;

@RestController
public class AuthController {

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public String login(@RequestBody Usuario usuario) {

		Usuario usuarioLogeado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);

		if (usuarioLogeado != null) {
			String tokenJWT = jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
			return tokenJWT;
		}
		return "FAIL";
	}
}
