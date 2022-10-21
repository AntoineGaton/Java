<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Manager Dashboard</title>
<link rel="stylesheet" href="/css/main.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Radio+Canada:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
</head>
</head>
<body>
	<div class="header">
		<h1>Welcome, <c:out value="${user.name}"></c:out></h1>
		<a href="/logout" class="ml-16">log out</a>
	</div>
	<p class="mb-32">TV Shows</p>
	<table class="mb-32">
		<thead>
			<tr>
				<th>Show</th>
				<th>Network</th>
				<th>Average Rating</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="show" items="${shows}">
				<tr>
					<td><a href="/shows/${show.id}"><c:out
								value="${show.title}" /></a></td>
					<td><c:out value="${show.network}" /></td>
					<td><fmt:formatNumber type="number" maxIntegerDigits="2" value="${show.getAverageRating()}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="/shows/new" class="button mt-32">Add a Show</a>
</body>
</html>