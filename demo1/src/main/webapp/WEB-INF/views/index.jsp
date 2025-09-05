<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Item View</title>
</head>
<body>
<h1>Item Details</h1>
<p>ID: ${item.id}</p>
<p>Name: ${item.name}</p>

<h2>Other Items</h2>
<ul>
    <c:forEach var="i" items="${items}">
        <li>${i.name} (ID: ${i.id})</li>
    </c:forEach>
</ul>
</body>
</html>