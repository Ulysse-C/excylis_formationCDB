<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="<c:url value="/dashboard" />"> <fmt:message
				key="label.home" /></a>
		<div>
			<select class="form-control" id="language" name="language">
				<option selected="selected" disabled="disabled"><fmt:message
						key="label.pickLanguage" /></option>
				<option value="fr"><fmt:message key="label.lang.fr" /></option>
				<option value="en"><fmt:message key="label.lang.en" /></option>
			</select>
		</div>
	</div>
</header>
<script src="../../formationCDB/js/jquery.min.js"></script>
<script src="../../formationCDB/js/language.js"></script>
