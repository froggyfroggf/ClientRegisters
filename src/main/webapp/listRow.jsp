
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h3>Client Registers</h3>
	<a href="/create" class="btn btn-success btn-sm"> <i
		class="glyphicon glyphicon-plus"></i> Add Register
	</a>
	<c:choose>
		<c:when test="${empty registers}">
			<p>No registers found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${registers}" var="register">
				<div class="media">
					<a href="/read?id=${register.id}">
						<div class="media-body">
							<h4>${fn:escapeXml(register.year)}
								${fn:escapeXml(register.facility)}</h4>
							<p>${fn:escapeXml(register.subdistrict)}</p>
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

