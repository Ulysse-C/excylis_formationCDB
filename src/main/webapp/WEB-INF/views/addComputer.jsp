<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../../formationCDB/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="../../formationCDB/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="../../formationCDB/css/main.css" rel="stylesheet"
	media="screen">
</head>
<body>
	<%@include file="header.jsp" %>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<fmt:message key="label.addComputer" />
					</h1>
					<form action="addComputer" method="POST"
						onsubmit="return validateDates(introduced.value, discontinued.value)">
						<span class="error"><c:out value="${errors['sqlErrors']}" /></span>
						<fieldset>
							<div class="form-group">
								<label for="computerName"><fmt:message
										key="label.computerName" /> </label> <input type="text"
									class="form-control" id="computerName"
									placeholder="<fmt:message
										key="label.computerName" />" required="required"
									name="computerName" value="${computer.computerName }">
								<span class="error"><c:out
										value="${errors['computerName']}" /></span>
							</div>
							<div class="form-group">
								<label for="introduced"><fmt:message
										key="label.introducedDate" /> </label> <input type="date"
									class="form-control" id="introduced"
									placeholder="Introduced date" name="introducedDate"
									value="${computer.introducedDate }"> <span
									class="error" id="dateError"><c:out
										value="${errors['introduced']}" /></span>
							</div>
							<div class="form-group">
								<label for="discontinued"><fmt:message
										key="label.discontinuedDate" /> </label> <input type="date"
									class="form-control" id="discontinued"
									placeholder="Discontinued date" name="discontinuedDate"
									value="${computer.discontinuedDate }">
							</div>
							<div class="form-group">
								<label for="companyId"><fmt:message
										key="label.companyName" /> </label> <select class="form-control"
									id="companyId" name="companyId">
									<option selected="selected" disabled="disabled">--</option>
									<c:forEach items="${companyList}" var="company">
										<option value="${company.id }"><c:out
												value="${company.name}" /></option>
									</c:forEach>
								</select> <span class="error"><c:out
										value="${errors['companyId']}" /></span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message key="label.add" />"
								class="btn btn-primary" on>
							<fmt:message key="label.or" />
							<a href="dashboard" class="btn btn-default"><fmt:message
									key="label.cancel" /></a>
						</div>

					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="../../formationCDB/js/dateValidator.js"></script>
	
</body>
</html>