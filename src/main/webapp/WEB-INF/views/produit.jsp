<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="diti.entity.Produit" %>
<%@ page import="diti.entity.TypeProduit" %>
<%@ page import="java.util.List" %>
<%@ page isELIgnored="false" %>
<a href="${pageContext.request.contextPath}/produit/new">Ajouter un produit</a>
<a href="${pageContext.request.contextPath}/type-produit/new">Ajouter un type de produit</a>

<%
    Object selectedTypeObj = request.getAttribute("selectedType");
    if (selectedTypeObj != null) {
        TypeProduit selectedType = (TypeProduit) selectedTypeObj;
%>
    <p>Filtre par type: <strong><%= selectedType.getLibelle() %></strong> - <a href="${pageContext.request.contextPath}/produit">Voir tous</a></p>
<%
    }
%>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Libelle</th>
        <th>Prix</th>
        <th>Type</th>
        <th>Actions</th>
    </tr>
    <%
        List<Produit> produits = (List<Produit>) request.getAttribute("produits");
        if (produits != null) {
            for (Produit product : produits) {
    %>
    <tr>
        <td><%= product.getId() %></td>
        <td><%= product.getLibelle() %></td>
        <td><%= product.getPrix() %></td>
        <td><%= product.getTypeProduit() != null ? product.getTypeProduit().getLibelle() : "Non defini" %></td>
        <td>
            <a href="${pageContext.request.contextPath}/produit/edit/<%= product.getId() %>">Modifier</a>
            <form action="${pageContext.request.contextPath}/produit/delete/<%= product.getId() %>" method="post" style="display:inline;">
                <input type="hidden" name="_method" value="DELETE">
                <input type="submit" value="Supprimer" onclick="return confirm('Supprimer ce produit ?');" />
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
