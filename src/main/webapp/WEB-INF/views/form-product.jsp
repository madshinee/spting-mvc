<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/produit" method="post">
    <input type="text" value="${produit.id}" name="id" hidden="">
    <label for="">Libelle</label>
    <input type="text" name="libelle" value="${produit.libelle}"/>
    <label for="">Prix</label>
    <input type="number" name="prix" value="${produit.prix}"/>
    <label for="">Type de Produit</label>
    <select name="typeProduitId">
        <option value="">-- Selectionner un type --</option>
        <%
            java.util.List<diti.entity.TypeProduit> types = (java.util.List<diti.entity.TypeProduit>) request.getAttribute("types");
            diti.entity.Produit produit = (diti.entity.Produit) request.getAttribute("produit");
            if (types != null) {
                for (diti.entity.TypeProduit type : types) {
                    boolean selected = (produit != null && produit.getTypeProduit() != null && produit.getTypeProduit().getId().equals(type.getId()));
        %>
            <option value="<%= type.getId() %>" <%= selected ? "selected" : "" %>><%= type.getLibelle() %></option>
        <%
                }
            }
        %>
    </select>
    <button type="submit">Enregistrer</button>
</form>
