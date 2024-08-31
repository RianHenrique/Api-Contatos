package org.exemple.controller;

import org.exemple.model.Contato;
import java.util.ArrayList;
import java.util.List;

public class ContatoController {
    private List<Contato> contatos = new ArrayList<>();
    private int currentId = 1;

    // Create
    public Contato criarContato(String nome, String telefone, String email) {
        Contato contato = new Contato(currentId++, nome, telefone, email);
        contatos.add(contato);
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
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletarContato(int id) {
        return contatos.removeIf(c -> c.getId() == id);
    }
}
