<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create a Task</title>
<link rel="stylesheet" href="/css/main.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Radio+Canada:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<div class="flex between header">
		<h1 class="mb-32">Create a New TV Show</h1>
	</div>
	<form:form action="/shows/new/submit" method="post" modelAttribute="show" class="w-20p">
		<form:errors path="title" class="error" />
		<form:errors path="network" class="error" />
		<form:errors path="description" class="error" />
		<div class="title">
			<form:label path="title">Title</form:label>
			<form:input path="title" />
		</div>
		<div class="network">
			<form:label path="network">Network</form:label>
			<form:input path="network" />
		</div>
		<div class="description">
			<form:label path="description">Description</form:label>
			<form:input path="description" />
		</div>
		<div class="flex-right">
			<a href="/shows" class="button mr-32">Cancel</a>
			<button class="">Submit</button>
		</div>
	</form:form>
</body>
</html>