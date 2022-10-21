<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit My Task</title>
<link rel="stylesheet" href="/css/main.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Radio+Canada:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
</head>
</head>
<body>
	<div class="header flex-between">
		<h1>
			<c:out value="${show.title}"></c:out>
		</h1>
		<a href="/shows">Back to Dashboard</a>
	</div>
	<p class="mb-16 bold">
		Posted by:
		<c:out value="${show.user.name}" />
	</p>
	<p class="mb-16">
		Network:
		<c:out value="${show.network}" />
	</p>
	<p>Description:</p>
	<p class="mb-32 ml-16">
		<c:out value="${show.description}" />
	</p>
	<c:if test="${user.id == show.user.id}">
		<a href="/shows/${show.id}/edit" class="button mt-32 mb-32">Edit</a>
	</c:if>
	<table class="mb-32 mt-32">
		<thead>
			<tr>
				<th>Name</th>
				<th>Rating</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="rating" items="${show.getSortedRatings()}">
				<tr>
					<td><c:out value="${rating.user.name}" /></td>
					<td><fmt:formatNumber type="number" maxIntegerDigits="2"
							value="${rating.rating}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form:form action="/shows/${show.id}/review" method="post" modelAttribute="rating">
		<input type="hidden" name="_method" value="put">
		<form:input type="hidden" path="user" value="${user.id}" />
		<form:input type="hidden" path="show" value="${show.id}" />
		<div>
			<form:label path="rating">Leave a Rating:</form:label>
			<form:input path="rating" />
			<button>Rate!</button>
		</div>
		<form:errors path="rating" class="error" />
	</form:form>
</body>
</html>