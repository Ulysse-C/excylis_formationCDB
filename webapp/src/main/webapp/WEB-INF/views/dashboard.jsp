<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
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
	<%@include file="header.jsp"%>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${numberComputers}" />
				<fmt:message key="label.computerFound" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<fmt:message key="label.selectName" />" /> <input
							type="submit" id="searchsubmit"
							value="<fmt:message key="label.filterName" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><fmt:message
							key="label.addComputer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message
							key="label.deleteComputer" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<form id="orderForm" action="#" method="POST">
			<input type="hidden" name="orderAttribute" value="" />
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><fmt:message key="label.computerName" /> <a href="#"
							id="orderAttributesComputerName"
							onclick="$.fn.orderBy('COMPUTER_NAME');"><i
								class="fa fa-fw fa-sort"></i></a></th>
						<th><fmt:message key="label.introducedDate" /> <a href="#"
							id="orderAttributesIntroduced"
							onclick="$.fn.orderBy('COMPUTER_INTRODUCED');"><i
								class="fa fa-fw fa-sort"></i></a></th>
						<!-- Table header for Discontinued Date -->
						<th><fmt:message key="label.discontinuedDate" /> <a href="#"
							id="orderAttributesDiscontinued"
							onclick="$.fn.orderBy('COMPUTER_DISCONTINUED');"><i
								class="fa fa-fw fa-sort"></i></a></th>
						<!-- Table header for Company -->
						<th><fmt:message key="label.companyName" /> <a href="#"
							id="orderAttributesCompanyName"
							onclick="$.fn.orderBy('COMPANY_NAME');"><i
								class="fa fa-fw fa-sort"></i></a></th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${computerList}" var="computerDTO">
						<tr>
							<td class="editMode"><input type="checkbox" name="delete"
								class="cb" value="${computerDTO.computerId}"></td>
							<td><a
								href="<c:url value="/editComputer"><c:param name="computerId" 
								value="${computerDTO.computerId}"/></c:url>">
									<c:out value="${computerDTO.computerName}" />
							</a></td>
							<td><c:out value="${computerDTO.introducedDate}" /></td>
							<td><c:out value="${computerDTO.discontinuedDate}" /></td>
							<td><c:out value="${computerDTO.companyName}" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="<c:url value="/dashboard">
			  		<c:param name="page" value="${pageNumber > 1 ? pageNumber - 1 : 1}"/>
			  		<c:if test="${not empty computerSearch}">
			   			<c:param name="search" value="${computerSearch}"/>
					</c:if>
					</c:url>"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>

				<c:forEach var="i" begin="${pageIndexFrom}" end="${pageIndexTo}"
					step="1">
					<li><a
						href="<c:url value="/dashboard">
				  		<c:param name="page" value="${i}"/>
				  		<c:if test="${not empty computerSearch}">
				   			<c:param name="search" value="${computerSearch}"/>
						</c:if>
						</c:url>">
							${i}</a></li>
				</c:forEach>
				<li><a
					href="<c:url value="/dashboard">
			  		<c:param name="page" value="${pageNumber == pageIndexTo ? pageNumber : pageNumber + 1}"/>
			  		<c:if test="${not empty computerSearch}">
			   			<c:param name="search" value="${computerSearch}"/>
					</c:if>
					</c:url>"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<form method=POST action=dashboard>
					<input type="submit" class="btn btn-default" name="pageSize"
						value="10" /> <input type="submit" class="btn btn-default"
						name="pageSize" value="50" /> <input type="submit"
						class="btn btn-default" name="pageSize" value="100" />
				</form>
			</div>
		</div>

	</footer>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
	<script type="text/javascript">
		var strings = new Array();
		strings['view'] = "<fmt:message key="label.view" />";
		strings['edit'] = "<fmt:message key="label.deleteComputer" />";
	</script>
</body>
</html>