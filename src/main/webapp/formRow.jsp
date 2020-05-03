<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>
		<c:out value="${action}" />
		registerRow
	</h3>

	<form method="POST" action="${destination}">

		<div class="form-group">
			<label for="registerRowid">Register id</label> <input type="text"
				name="registerRowid" id="registerRowid"
				value="${fn:escapeXml(registerRowRow.registerRowid)}" class="form-control" />
		</div>

		<div class="form-group">
			<label for="patientid">Facility</label> <input type="text"
				name="patientid" id="patientid"
				value="${fn:escapeXml(registerRow.patientid)}" class="form-control" />
		</div>

		<div class="form-group">
			<label for="parity">Parity</label> <input type="text"
				name="parity" id="parity" value="${fn:escapeXml(registerRow.parity)}"
				class="form-control" />
		</div>

		<div class="form-group">
			<label for="maritalstatus">MaritalStatus</label> <input type="text"
				name="maritalstatus" id="maritalstatus" value="${fn:escapeXml(registerRow.maritalstatus)}"
				class="form-control" />
		</div>

		<div class="form-group">
			<label for="method">Method</label> <input type="text"
				name="method" id="method" value="${fn:escapeXml(registerRow.method)}"
				class="form-control" />
		</div>
		
		<div class="form-group hidden">
			<input type="hidden" name="id" value="${registerRow.id}" />
		</div>

		<button type="submit" class="btn btn-success">Save</button>
	</form>
</div>
