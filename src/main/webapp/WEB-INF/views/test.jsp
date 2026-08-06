<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="diti.entity.Produit" %>
<html>
<body>
<h1>Test JSP</h1>
<%
    out.println("Java version: " + System.getProperty("java.version"));
    out.println("<br/>Produit class: " + diti.entity.Produit.class.getName());
%>
</body>
</html>
