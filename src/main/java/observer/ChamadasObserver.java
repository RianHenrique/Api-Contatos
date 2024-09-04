package observer;

import model.ChamadaService;
import model.ContatoCRUD;

public class ChamadasObserver implements Observer {
	private ChamadaService chamadas;
	
	public ChamadasObserver(ChamadaService chamada) {
		this.chamadas = chamada;
	}
	
    @Override
    public void update(String message) {
        chamadas.atualizarHistorico(message);
    }
}