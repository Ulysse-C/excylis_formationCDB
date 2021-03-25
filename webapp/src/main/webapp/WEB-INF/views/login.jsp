<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%><html>
<head>
<title><fmt:message key="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body onload='document.loginForm.username.focus();'>
	<%@include file="header.jsp"%>
	<section id="main">
		<div class="container">
			<h1>Spring Security 5 - Login Form</h1>

			<c:if test="${not empty errorMessge}">
				<div style="color: red; font-weight: bold; margin: 30px 0px;">${errorMessge}</div>
			</c:if>

			<form name='login' action="login" method='POST'>
				<table>
					<tr>
						<td>UserName:</td>
						<td><input type='text' name='username' value=''></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><input type='password' name='password' /></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" type="submit"
							value="submit" /></td>
					</tr>
				</table>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>
	</section>
</body>
</html>