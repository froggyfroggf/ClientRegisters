<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Patient Information</h3>
	<div class="btn-group">
		<a href="/update?id=${register.id}" class="btn btn-primary btn-sm">
			<i class="glyphicon glyphicon-edit"></i> Edit register
		</a> <a href="/delete?id=${register.id}" class="btn btn-danger btn-sm">
			<i class="glyphicon glyphicon-trash"></i> Delete register
		</a>
	</div>

	<div class="media">
		<div class="media-body">
			<h4 class="book-title">
				${fn:escapeXml(register.year)} ${fn:escapeXml(register.year)}
				<small>${fn:escapeXml(register.district)}</small>
			</h4> 
			<p> Subdistrict: ${fn:escapeXml(register.subdistrict)} </p>
			<p> Facility: ${fn:escapeXml(register.facility)} </p>
		</div>
	</div>
</div>
<!-- [END view] -->
