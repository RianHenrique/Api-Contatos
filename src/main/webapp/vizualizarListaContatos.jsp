<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Lista de contatos</title>
</head>
<body>
	<h2>Lista de Contatos</h2>
	<table>
		<tr>
			<th>Nome</th>
			<th>Telefone</th>
			<th>Email</th>
			<th></th>
			<th></th>
		</tr>
    <c:forEach items="${listaContatos}" var="contato">
        <tr>
        	<td>${contato.nome}</td>
        	<td>${contato.telefone}</td>
        	<td> ${contato.email}</td>
        	<td>Editar</td>
        	<td>Apagar</td>
        </tr>
    </c:forEach>
	</table>
    <form action="index.jsp">
		<input type="submit" value="Voltar">
	</form>
</body>
</html>