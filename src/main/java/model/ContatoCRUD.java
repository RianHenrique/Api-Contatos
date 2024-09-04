package model;

import model.Contato;
import observer.ChamadasObserver;
import observer.ConcreteObserver;
import observer.ConcreteSubject;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ContatoCRUD {
    private List<Contato> contatos = new ArrayList<>();
    private ConcreteSubject subject = new ConcreteSubject();
    private Observer observer = new ConcreteObserver();
    private int currentId = 1;

    // Construtor
    public ContatoCRUD() {
        subject.addObserver(observer);
    }
    
    public void adicionarObserver(Observer observer) {
    	subject.addObserver(observer);
    }

    // Create
    public Contato criarContato(String nome, String telefone, String email) {
        Contato contato = new Contato(currentId++, nome, telefone, email);
        contatos.add(contato);
        //subject.notifyObservers(telefone);
        return contato;
    }

    // Read
    public List<Contato> listarContatos() {
        return contatos;
    }

    public Contato buscarContatoPorId(int id) {
        return contatos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }
    
    public Contato buscarContatoPorTelefone(String telefone) {
        return contatos.stream().filter(c -> c.getTelefone() == telefone).findFirst().orElse(null);
    }

    // Update
    public boolean atualizarContato(int id, String nome, String telefone, String email) {
        Contato contato = buscarContatoPorId(id);
        String numeroAntigo = contato.getTelefone();
        if (contato != null) {
            if (nome != null) { contato.setNome(nome);}
            if (telefone != null) { contato.setTelefone(telefone);}
            if (email != null) {contato.setEmail(email);}
            subject.notifyObservers(numeroAntigo);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarContato(int id) {
        Contato contato = buscarContatoPorId(id);
        String numeroAntigo = contato.getTelefone();
        boolean retorno = contatos.removeIf(c -> c.getId() == id);
        subject.notifyObservers(numeroAntigo);
        return retorno;
    }
}
