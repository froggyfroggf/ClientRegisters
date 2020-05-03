<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Patient Information</h3>
	<div class="btn-group">
		<a href="/update?id=${registerrow.id}" class="btn btn-primary btn-sm">
			<i class="glyphicon glyphicon-edit"></i> Edit registerRow
		</a> <a href="/delete?id=${registerrow.id}" class="btn btn-danger btn-sm">
			<i class="glyphicon glyphicon-trash"></i> Delete registerRow
		</a>
	</div>

	<div class="media">
		<div class="media-body">
			<h4 class="book-title">
				${fn:escapeXml(registerrow.registerid)} ${fn:escapeXml(registerrow.registerid)}
				<small>${fn:escapeXml(registerrow.patientid)}</small>
			</h4> 
			<p> Parity: ${fn:escapeXml(registerrow.parity)} </p>
			<p> Marital Status: ${fn:escapeXml(registerrow.maritalstatus)} </p>
			<p> Method: ${fn:escapeXml(registerrow.method)} </p>
		</div>
	</div>
</div>
<!-- [END view] -->
