package org.exemple.controller;

import org.exemple.model.Contato;
import org.exemple.observer.ConcreteObserver;
import org.exemple.observer.ConcreteSubject;
import org.exemple.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ContatoController {
    private List<Contato> contatos = new ArrayList<>();
    private ConcreteSubject subject = new ConcreteSubject();
    private Observer observer = new ConcreteObserver();
    private int currentId = 1;

    // Construtor
    public ContatoController() {
        subject.addObserver(observer);
    }

    // Create
    public Contato criarContato(String nome, String telefone, String email) {
        Contato contato = new Contato(currentId++, nome, telefone, email);
        contatos.add(contato);
        subject.notifyObservers("Contato adicionado: " + contato.getNome() + " com ID: " + contato.getId());
        return contato;
    }

    // Read
    public List<Contato> listarContatos() {
        return contatos;
    }

    public Contato buscarContatoPorId(int id) {
        return contatos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    // Update
    public boolean atualizarContato(int id, String nome, String telefone, String email) {
        Contato contato = buscarContatoPorId(id);
        if (contato != null) {
            contato.setNome(nome);
            contato.setTelefone(telefone);
            contato.setEmail(email);
            subject.notifyObservers("Contato atualizado: " + contato.getNome() + " com ID: " + contato.getId());
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarContato(int id) {
        Contato contato = buscarContatoPorId(id);
        subject.notifyObservers("Contato deletado: " + contato.getNome() + " com ID: " + contato.getId());
        return contatos.removeIf(c -> c.getId() == id);
    }
}
