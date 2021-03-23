<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title><fmt:message key="label.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="css/main.css" rel="stylesheet"
	media="screen">
</head>
<body>
<%@include file="header.jsp" %>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDTO.computerId }</div>
					<h1><fmt:message
										key="label.editComputer" /></h1>

					<form action="editComputer" method="POST"
						onsubmit="return validateDates(introduced.value, discontinued.value)">
						<input type="hidden" value="${computerDTO.computerId }" id="id"
							name="computerId" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><fmt:message
										key="label.computerName" /></label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computerDTO.computerName }"
									placeholder="<fmt:message
										key="label.computerName" />"> <span class="error">${computer.computerName }<c:out
										value="${errors['computerName']}" /></span>
							</div>
							<div class="form-group">
								<label for="introduced"><fmt:message
										key="label.introducedDate" /></label> <input
									value="${computerDTO.introducedDate }" id="introduced"
									class="form-control" type="text" placeholder="Introduced date"
									onfocus="(this.type='date')" onblur="(this.type='text')"
									name="introducedDate" /> <span class="error" id="dateError"><c:out
										value="${errors['introduced']}" /></span>
							</div>
							<div class="form-group">
								<label for="discontinued"><fmt:message
										key="label.discontinuedDate" /></label> <input
									value="${computerDTO.discontinuedDate }"
									placeholder="Discontinued date" id="discontinued"
									class="form-control" type="text" onfocus="(this.type='date')"
									onblur="(this.type='text')" name="discontinuedDate" />
							</div>
							<div class="form-group">
								<label for="companyId"><fmt:message
										key="label.companyName" /></label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="${computerDTO.companyId }">${computerDTO.companyName }</option>
									<c:forEach items="${companyList}" var="company">
										<option value="${company.id }"><c:out
												value="${company.name}" /></option>
									</c:forEach>
								</select> <span class="error"><c:out
										value="${errors['companyId']}" /></span>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<fmt:message
										key="label.edit" />" class="btn btn-primary">
							<fmt:message
										key="label.or" /> <a href=<c:url value="dashboard" /> class="btn btn-default"><fmt:message
										key="label.cancel" /></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="js/dateValidator.js"></script>
</body>
</html>