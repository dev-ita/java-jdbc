package br.com.model;

public class Userposjava {
	private Long id;
	private String nome;
	private String email;

	public Userposjava() {}

	public Userposjava(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	public Userposjava(String nome, String email) {
		this.email = email;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Userposjava [id=" + id + ", nome=" + nome + ", email=" + email + "]";
	}
}