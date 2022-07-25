package br.com.pilares.storage.model.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Anexo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private UUID hash;
	private String nome;
	private String extensao;
	@Enumerated(EnumType.STRING)
	private Sistema sistema;
	
	public Anexo() {
		// TODO Auto-generated constructor stub
	}
	
	public Anexo(String nome, String extensao, Sistema sistema) {
		this.hash = UUID.randomUUID();
		this.nome = nome;
		this.extensao = extensao;
		this.sistema = sistema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getHash() {
		return hash;
	}

	public void setHash(UUID hash) {
		this.hash = hash;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	
}
