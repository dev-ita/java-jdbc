package br.com.pos_java_jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.conexaojdbc.SingleConnection;
import br.com.dao.UserPosDAO;
import br.com.model.BeanUserFone;
import br.com.model.Telefone;
import br.com.model.Userposjava;

public class TesteBancoJdbc {
	@Test
	public void initBanco() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava("brandão", "brandao@gmail.com");
		userPosDAO.salvar(userposjava);
		System.out.println(userposjava.getId());
	}

	@Test
	public void initListar() {
		UserPosDAO userPosDAO = new UserPosDAO();
		List<Userposjava> lista = userPosDAO.listar();

		for (Userposjava item : lista) {
			System.out.println(item.getEmail());
		}
	}

	@Test
	public void initBuscar() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava usuario = new Userposjava();
		usuario = userPosDAO.buscar(1L);

		System.out.println(usuario);
	}

	@Test
	public void initAtualizar() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = userPosDAO.buscar(4L);
		userposjava.setNome("Rubens atualizado");
		userPosDAO.atualizar(userposjava);
	}

	@Test
	public void initDelete() {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = userPosDAO.delete(13L);
		System.out.println("Usuario deletado: " + userposjava );
	}
	
	@Test
	public void initTesteInsertTelefone() {
		Telefone telefone = new Telefone("(91) 977569282", "celular", 13L); 	
		UserPosDAO userPosDAO = new UserPosDAO();
		userPosDAO.salvarTelefone(telefone);
	}
	
	@Test
	public void initTesteInnerJoin() {
		UserPosDAO userPosDAO = new UserPosDAO();
		List<BeanUserFone> telefones = new ArrayList<BeanUserFone>();
		telefones = userPosDAO.listaUserFone(15L);
		for (BeanUserFone item : telefones) {
			System.out.println(item);
		}
	}
	
	@Test
	public void initTesteDeleteUserFone() {
		UserPosDAO userPosDAO = new UserPosDAO();		
		try {
			userPosDAO.deleteFonesPorUser(13L);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}