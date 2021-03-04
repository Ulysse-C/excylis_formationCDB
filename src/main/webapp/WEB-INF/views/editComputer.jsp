<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value="/dashboard" />">
				Application - Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computerDTO.computerId }</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST"
						onsubmit="return validateDates(introduced.value, discontinued.value)">
						<input type="hidden" value="${computerDTO.computerId }" id="id"
							name="computerId" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computerDTO.computerName }"
									placeholder="Computer name">
									
								<span class="error">${computer.computerName }<c:out
										value="${errors['computerName']}" /></span>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									value="${computerDTO.introducedDate }"
									id="introduced" class="form-control" type="text"
									placeholder="Introduced date"
									onfocus="(this.type='date')" onblur="(this.type='text')"
									id="introduced" name="introduced" /> <span class="error"
									id="dateError"><c:out value="${errors['introduced']}" /></span>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									value="${computerDTO.discontinuedDate }"
									placeholder="Discontinued date"
									id="discontinued" class="form-control" type="text"
									onfocus="(this.type='date')" onblur="(this.type='text')"
									id="discontinued" name="discontinued" />
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
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
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href=<c:url value="dashboard" /> " class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="../../formationCDB/js/dateValidator.js"></script>

</body>
</html>