
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Client Register Rows</h3>
	<a href="/create" class="btn btn-success btn-sm"> <i
		class="glyphicon glyphicon-plus"></i> Add Register Rows
	</a>
	<c:choose>
		<c:when test="${empty registerrows rows}">
			<p>No register rows found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${registerrowss}" var="registerrows">
				<div class="media">
					<a href="/read?id=${registerrows.id}">
						<div class="media-body">
							<h4>${fn:escapeXml(registerrows.registerid)}
								${fn:escapeXml(registerrows.patientid)}</h4>
							<p>${fn:escapeXml(registerrows.parity)}</p>
							<p>${fn:escapeXml(registerrows.maritalstatus)}</p>
							<p>${fn:escapeXml(registerrows.method)}</p>
						</div>
					</a>
				</div>
			</c:forEach>
			<c:if test="${not empty cursor}">
				<nav>
					<ul class="pager">
						<li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
					</ul>
				</nav>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>

