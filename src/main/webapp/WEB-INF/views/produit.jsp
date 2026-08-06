<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<a href="${pageContext.request.contextPath}/produit/new">Ajouter un produit</a>


<table>
    <c:forEach var="product" items="${produits}">
        <tr> <td>${product.id}</td>
            <td>${product.libelle}</td>
            <td>${product.prix}</td>
            <td>
                <a href="${pageContext.request.contextPath}/produit/edit/${product.id}">Modifier</a>
                <form action="${pageContext.request.contextPath}/produit/delete/${product.id}" method="post" style="display:inline;">
                    <input type="text" hidden  name="_method" value="DELETE">
                    <input type="submit" value="Supprimer" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce produit ?');" />
                </form>
            </td> </tr>
    </c:forEach>
</table>