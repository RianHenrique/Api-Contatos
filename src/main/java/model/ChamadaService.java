package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChamadaService{
	private List<Chamada> chamadas = new ArrayList<>();
	private ContatoCRUD contatoCRUD;
	
	public ChamadaService(ContatoCRUD contatoCRUD) {
		this.contatoCRUD = contatoCRUD;
	}
	
	public void ligar(String telefone) {
		Chamada chamada = new Chamada();
		Contato contatoBusca = contatoCRUD.buscarContatoPorTelefone(telefone);
		chamada.setNumero(telefone);
		chamada.setData(new Date());
		if (contatoBusca != null) {
			chamada.setNome(contatoBusca.getNome());
		}else {
			chamada.setNome("Desconhecido");
		}
		chamadas.add(chamada);
	}
	
	public void atualizarHistorico(String telefone) {
		Contato contatoBusca = contatoCRUD.buscarContatoPorTelefone(telefone);
		for (Chamada chamada : chamadas) {
			if (chamada.getNumero().intern() == telefone.intern()) {
				if(contatoBusca == null) {
					chamada.setNome("Desconhecido");
				}else {
					chamada.setNome(contatoBusca.getNome());
				}
			}
		}
	}
	
	public List<Chamada> verHistorico(){
		return chamadas;
	}
	
}