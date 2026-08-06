<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/produit" method="post">
    <input type="text" value="${produit.id}" name="id" hidden="">
    <label for="">Libelle</label>
    <input type="text" name="libelle" value="${produit.libelle}"/>
    <label for="">Prix</label>
    <input type="number" name="prix" value="${produit.prix}"/>
    <button type="submit">Enregistrer</button>
</form>