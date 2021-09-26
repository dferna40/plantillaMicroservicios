package com.microservicio.dao;

import java.util.List;

import com.microservicio.models.Usuario;

public interface IUsuarioDao {

	List<Usuario> getUsuarios();

	void eliminarUsuario(Long id);

	void registrar(Usuario usuario);

	Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
	
}
