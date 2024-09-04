package model;

import java.util.Date;

public class Chamada{
	private String numero;
	private String nome;
	private Date data;
	
	public Chamada() {}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
}




//Lista historico = {[nome, numero],}
//update contato -> mudar no historico
// rian 1 -> ligsr (adiciona historico)
// leticia 2 ->
//update contato rian -> notifica observer -> modifica lista historico

























