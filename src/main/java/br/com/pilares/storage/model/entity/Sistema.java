package br.com.pilares.storage.model.entity;

import javax.persistence.Entity;

public enum Sistema {

	SISVO("sisvo"),
	SISCI("sisci"),
	INFA("infa");
	
	private String nome;
	
	Sistema(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
