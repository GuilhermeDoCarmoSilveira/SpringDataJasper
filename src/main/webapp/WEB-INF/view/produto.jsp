<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href='<c:url value = "./resources/style.css"/>'>
<title>Produto</title>
</head>
<body>

	<div align="center">
		<c:if test="${not empty erro }">
			<h2>
				<b> <c:out value="${erro }" />
				</b>
			</h2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<h3>
				<b> <c:out value="${saida }" />
				</b>
			</h3>
		</c:if>
	</div>


	<div align="center" class="container">
		<form action="produto" method="post">
			<p class="title">
				<b>Produto</b>
			</p>
			<table>
				<tr>
					<td colspan="3"><input class="id_input_data" type="number"
						min="0" step="1" id="codigo" name="codigo" placeholder="Codigo"
						pattern="[0-9.,]*"
						value='<c:out value="${produto.codigo }"></c:out>'></td>
					<td><input type="submit" id="botao" name="botao"
						value="Buscar"></td>
				</tr>
				<tr>
					<td colspan="4"><input class="input_data" type="text"
						id="nome" name="nome" placeholder="Nome"
						value='<c:out value="${produto.nome }"></c:out>'></td>
				</tr>
				<tr>
					<td colspan="4"><input class="input_data" type="text"
						id="valorUnitario" name="valorUnitario"
						placeholder="Valor Unitario"
						value='<c:out value="${produto.valorUnitario}" ></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4"><input class="input_data" type="number"
						id="qtdEstoque" name="qtdEstoque" placeholder="qtd. Estoque"
						value='<c:out value="${produto.qtdEstoque}"></c:out>'></td>
				</tr>

				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Cadastrar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Alterar"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Excluir"></td>
					<td><input type="submit" id="botao" name="botao"
						value="Listar"></td>
				</tr>
			</table>
		</form>
	</div>
	
	<br>
	<br>
	<br>
	
	<div align="center">
		<c:if test="${not empty produtos}">
			<table class="table_round">
				<thead>
					<tr>
						<th class="lista">Codigo</th>
						<th class="lista">Nome</th>
						<th class="lista_ultimoelemento">Valor Unitario</th>
						<th class="lista_ultimoelemento">Qtd. estoque</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${produtos}">
						<tr>
							<td class="lista"><c:out value="${p.codigo } " /></td>
							<td class="lista"><c:out value="${p.nome } " /></td>
							<td class="lista_ultimoelemento"><c:out
									value="${p.valorUnitario} " /></td>
							<td class="lista_ultimoelemento"><c:out
									value="${p.qtdEstoque} " /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
	
	<br>
	<br>

	<div align="center" class="container">
		<form action="produto" method="post">
			<p class="title">
				<b>Consultar quantidade de produtos que estao abaixo do valor
					inserido</b>
			</p>
			<table>
				<tr>
					<td><input class="input_data" type="number" id="valor"
						name="valor" placeholder="" /></td>
				</tr>

				<tr>
					<td><input type="submit" id="botao" name="botao"
						value="Verificar"></td>
				</tr>
			</table>
		</form>
	</div>

	<br>
	<br>
	<br>

	<div align="center" class="container">
		<form action="produtoRelatorio" method="post" target="_blank">
			<p class="title">
				<b>Relatorio</b>
			</p>
			<table>
				<tr>
					<td><input class="input_data" type="number"
						id="valorRelatorio" name="valorRelatorio" placeholder="" /></td>
				</tr>

				<tr>
					<td><input type="submit" id="botao" name="botao" value="Gerar">
					</td>
				</tr>
			</table>
		</form>
	</div>


	<br />
	<br />
	<br />
	
</body>
</html>