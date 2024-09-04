package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Chamada;
import model.ChamadaService;
import model.Contato;
import model.ContatoCRUD;
import observer.ChamadasObserver;

@WebServlet("/contatoController/*")
public class ContatoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContatoCRUD contatoCRUD = new ContatoCRUD();
	private ChamadaService chamadaService = new ChamadaService(contatoCRUD);
	private ChamadasObserver observer = new ChamadasObserver(chamadaService);
	private boolean observerAdicionado = false;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
	        String nome = req.getParameter("nome");
	        String telefone = req.getParameter("telefone");
	        String email = req.getParameter("email");
	
	        Contato novoContato = contatoCRUD.criarContato(nome, telefone, email);
	        resp.setContentType("application/json");
	        resp.getWriter().write("{ \"id\": " + novoContato.getId() + ", \"nome\": \"" + novoContato.getNome() + "\", \"telefone\": \"" + novoContato.getTelefone() + "\", \"email\": \"" + novoContato.getEmail() + "\" }");
			if(!observerAdicionado) {
				contatoCRUD.adicionarObserver(observer);
				observerAdicionado = true;
			}
        }else if(pathInfo.equals("/ligar")) {
        	String telefone = req.getParameter("telefone");
        	chamadaService.ligar(telefone);
        	resp.setContentType("application/json");
            resp.getWriter().write("{ \"ligacao feita para\": " + telefone + "\" }");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Contato> contatos = contatoCRUD.listarContatos();
            PrintWriter out = resp.getWriter();
            out.write("[");
            for (int i = 0; i < contatos.size(); i++) {
                Contato contato = contatos.get(i);
                out.write("{ \"id\": " + contato.getId() + ", \"nome\": \"" + contato.getNome() + "\", \"telefone\": \"" + contato.getTelefone() + "\", \"email\": \"" + contato.getEmail() + "\" }");
                if (i < contatos.size() - 1) out.write(",");
            }
            out.write("]");
           
        } else if(pathInfo.equals("/historico")) {
        	List<Chamada> chamadas = chamadaService.verHistorico();
            PrintWriter out = resp.getWriter();
            out.write("[");
            for (int i = 0; i < chamadas.size(); i++) {
                Chamada chamada = chamadas.get(i);
                out.write("{ \"numero\": " + chamada.getNumero() + ", \"nome\": \"" + chamada.getNome() + "\", \"data\": \"" + chamada.getData() + "\" }");
                if (i < chamadas.size() - 1) out.write(",");
            }
            out.write("]");
        }
        else {
            String[] splits = pathInfo.split("/");
            int id = Integer.parseInt(splits[1]);
            Contato contato = contatoCRUD.buscarContatoPorId(id);

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
        
        if(nome == null && telefone == null && email == null) {
        	resp.setContentType("application/json");
        	resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{ \"error\": \"É necessario alterar algum campo\" }");
        }else {
        	boolean atualizado = contatoCRUD.atualizarContato(id, nome, telefone, email);

	        resp.setContentType("application/json");
	        if (atualizado) {
	            resp.getWriter().write("{ \"status\": \"Contato atualizado com sucesso\" }");
	        } else {
	            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
	            resp.getWriter().write("{ \"error\": \"Contato não encontrado\" }");
	        }
        }
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] splits = pathInfo.split("/");
        int id = Integer.parseInt(splits[1]);

        boolean deletado = contatoCRUD.deletarContato(id);

        resp.setContentType("application/json");
        if (deletado) {
            resp.getWriter().write("{ \"status\": \"Contato deletado com sucesso\" }");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{ \"error\": \"Contato não encontrado\" }");
        }
    }
}
