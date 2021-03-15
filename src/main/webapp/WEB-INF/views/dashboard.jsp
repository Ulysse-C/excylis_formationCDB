<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${numberComputers}" />
				Computers found
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Delete</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<form id="orderForm" action="#" method="POST">
			<input type="hidden" name="orderAttribute" value=""/> 
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
						<th>Computer name <a href="#"
							id="orderAttributesComputerName"
							onclick="$.fn.orderBy('COMPUTER_NAME');"><i
								class="fa fa-fw fa-sort"></i></a>
						</th>
						<th>Introduced date <a href="#"
							id="orderAttributesIntroduced"
							onclick="$.fn.orderBy('COMPUTER_INTRODUCED');"><i
								class="fa fa-fw fa-sort"></i></a></th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date <a href="#"
							id="orderAttributesDiscontinued"
							onclick="$.fn.orderBy('COMPUTER_DISCONTINUED');"><i
								class="fa fa-fw fa-sort"></i></a></th>
						<!-- Table header for Company -->
						<th>Company <a href="#" id="orderAttributesCompanyName"
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
	<script src="../../formationCDB/js/jquery.min.js"></script>
	<script src="../../formationCDB/js/bootstrap.min.js"></script>
	<script src="../../formationCDB/js/dashboard.js"></script>

</body>
</html>