<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>
		<c:out value="${action}" />
		register
	</h3>

	<form method="POST" action="${destination}">

		<div class="form-group">
			<label for="year">Year</label> <input type="text"
				name="year" id="year"
				value="${fn:escapeXml(register.year)}" class="form-control" />
		</div>

		<div class="form-group">
			<label for="facility">Facility</label> <input type="text"
				name="facility" id="facility"
				value="${fn:escapeXml(register.facility)}" class="form-control" />
		</div>

		<div class="form-group">
			<label for="subdistrict">Subdistrict</label> <input type="text"
				name="subdistrict" id="subdistrict" value="${fn:escapeXml(register.subdistrict)}"
				class="form-control" />
		</div>

		<div class="form-group">
			<label for="district">District</label> <input type="text"
				name="district" id="district" value="${fn:escapeXml(register.district)}"
				class="form-control" />
		</div>

		<div class="form-group hidden">
			<input type="hidden" name="id" value="${register.id}" />
		</div>

		<button type="submit" class="btn btn-success">Save</button>
	</form>
</div>
