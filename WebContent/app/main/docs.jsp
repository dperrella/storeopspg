<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />

<!doctype html>
<html lang="en">
    <head>
        <title>Guide</title>
    </head>
    <body>
        <ul>
            <li>${req}</li>
            <li>${url}</li>
            <li>${uri}</li>
        </ul>
    </body>
</html>
