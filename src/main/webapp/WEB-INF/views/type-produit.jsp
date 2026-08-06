<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<a href="${pageContext.request.contextPath}/type-produit/new">Ajouter un type de produit</a>

<table>
    <%
        java.util.List<diti.entity.TypeProduit> types = (java.util.List<diti.entity.TypeProduit>) request.getAttribute("types");
        if (types != null) {
            for (diti.entity.TypeProduit type : types) {
    %>
    <tr>
        <td><%= type.getId() %></td>
        <td><%= type.getLibelle() %></td>
        <td>
            <a href="${pageContext.request.contextPath}/type-produit/edit/<%= type.getId() %>">Modifier</a>
            <form action="${pageContext.request.contextPath}/type-produit/delete/<%= type.getId() %>" method="post" style="display:inline;">
                <input type="text" hidden name="_method" value="DELETE">
                <input type="submit" value="Supprimer" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce type ?');" />
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
