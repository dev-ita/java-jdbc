package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.conexaojdbc.SingleConnection;
import br.com.model.BeanUserFone;
import br.com.model.Telefone;
import br.com.model.Userposjava;

public class UserPosDAO {
	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Userposjava user) {
		try {
			String query = "insert into userposjava (nome, email) values (?, ?) RETURNING id";

			PreparedStatement insert = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			insert.setString(1, user.getNome());
			insert.setString(2, user.getEmail());
			insert.execute();

			ResultSet resultSet = insert.getGeneratedKeys();
			if (resultSet.next()) {
				user.setId(Long.valueOf(resultSet.getInt(1)));
			}

			connection.commit(); // salva no banco
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			String query = "insert into telefoneuser (numero, tipo, usuariopessoa) values (?, ?, ?) RETURNING id";

			PreparedStatement insert = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			insert.execute();

			ResultSet resultSet = insert.getGeneratedKeys();
			if (resultSet.next()) {
				telefone.setId(Long.valueOf(resultSet.getInt(1)));
			}

			connection.commit(); // salva no banco
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<Userposjava> listar() {
		List<Userposjava> list = new ArrayList<Userposjava>();

		String query = "select * from userposjava";

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {
				Userposjava userposjava = new Userposjava();
				userposjava.setId(resultado.getLong("id"));
				userposjava.setNome(resultado.getString("nome"));
				userposjava.setEmail(resultado.getString("email"));
				list.add(userposjava);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public Userposjava buscar(Long id) {
		Userposjava retorno = new Userposjava();
		String query = "select * from userposjava where id = " + id;

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultado = statement.executeQuery();

			while (resultado.next()) {
				retorno.setId(resultado.getLong("id"));
				retorno.setNome(resultado.getString("nome"));
				retorno.setEmail(resultado.getString("email"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retorno;
	}
	
	public List<BeanUserFone> listaUserFone(Long idUser) {
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();

		StringBuilder queryStringBuilder = new StringBuilder();
		queryStringBuilder.append("select nome, numero, email from telefoneuser as fone ");
		queryStringBuilder.append(" inner join userposjava as userp ");
		queryStringBuilder.append(" on fone.usuariopessoa = userp.id ");
		queryStringBuilder.append(" where userp.id = " + idUser);

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(queryStringBuilder.toString());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				BeanUserFone beanUserFone = new BeanUserFone(); 
				beanUserFone.setEmail(resultSet.getString("email"));
				beanUserFone.setNome(resultSet.getString("nome"));
				beanUserFone.setNumero(resultSet.getString("numero"));
				beanUserFones.add(beanUserFone);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return beanUserFones;
	}

	public void atualizar(Userposjava userposjava) {
		try {
			String query = "update userposjava set nome = ? where id = " + userposjava.getId();

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, userposjava.getNome());

			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public Userposjava delete(Long id) {
		Userposjava userposjava = null;
		try {
			userposjava = buscar(id);

			String query = "delete from userposjava where id = " + id;
			PreparedStatement deleteStatement = connection.prepareStatement(query);
			deleteStatement.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return userposjava;
	}
	
	public void deleteFonesPorUser(Long idUser) throws SQLException {
		String fonequeryString = "delete from telefoneuser where usuariopessoa = " + idUser;
		String userqueryString = "delete from userposjava where id = " + idUser;
		
		PreparedStatement statement = connection.prepareStatement(fonequeryString);
		statement.executeUpdate();
		connection.commit();

		statement = connection.prepareStatement(userqueryString);
		statement.executeUpdate();
		connection.commit();
	}
}