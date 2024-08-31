package org.exemple.servlet;

import org.exemple.controller.ContatoController;
import org.exemple.model.Contato;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



@WebServlet("/contatos/*")
public class ContatoServlet extends HttpServlet {
    private ContatoController controller = new ContatoController();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String telefone = req.getParameter("telefone");
        String email = req.getParameter("email");

        Contato novoContato = controller.criarContato(nome, telefone, email);

        resp.setContentType("application/json");
        resp.getWriter().write("{ \"id\": " + novoContato.getId() + ", \"nome\": \"" + novoContato.getNome() + "\", \"telefone\": \"" + novoContato.getTelefone() + "\", \"email\": \"" + novoContato.getEmail() + "\" }");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Contato> contatos = controller.listarContatos();
            PrintWriter out = resp.getWriter();
            out.write("[");
            for (int i = 0; i < contatos.size(); i++) {
                Contato contato = contatos.get(i);
                out.write("{ \"id\": " + contato.getId() + ", \"nome\": \"" + contato.getNome() + "\", \"telefone\": \"" + contato.getTelefone() + "\", \"email\": \"" + contato.getEmail() + "\" }");
                if (i < contatos.size() - 1) out.write(",");
            }
            out.write("]");
        } else {
            String[] splits = pathInfo.split("/");
            int id = Integer.parseInt(splits[1]);
            Contato contato = controller.buscarContatoPorId(id);

            if (contato != null) {
                resp.getWriter().write("{ \"id\": " + contato.getId() + ", \"nome\": \"" + contato.getNome() + "\", \"telefone\": \"" + contato.getTelefone() + "\", \"email\": \"" + contato.getEmail() + "\" }");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{ \"error\": \"Contato não encontrado\" }");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] splits = pathInfo.split("/");
        int id = Integer.parseInt(splits[1]);

        String nome = req.getParameter("nome");
        String telefone = req.getParameter("telefone");
        String email = req.getParameter("email");

        boolean atualizado = controller.atualizarContato(id, nome, telefone, email);

        resp.setContentType("application/json");
        if (atualizado) {
            resp.getWriter().write("{ \"status\": \"Contato atualizado com sucesso\" }");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{ \"error\": \"Contato não encontrado\" }");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] splits = pathInfo.split("/");
        int id = Integer.parseInt(splits[1]);

        boolean deletado = controller.deletarContato(id);

        resp.setContentType("application/json");
        if (deletado) {
            resp.getWriter().write("{ \"status\": \"Contato deletado com sucesso\" }");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{ \"error\": \"Contato não encontrado\" }");
        }
    }
}
