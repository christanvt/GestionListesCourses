<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="fr.eni.javaee.gestionlistescourses.messages.LecteurMessage" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<jsp:include page="/WEB-INF/fragments/head.jsp"></jsp:include>

<body class="container">
  
<%@ include file="./fragments/header.html" %>
    
	<div class="col-12">
		<h2 class="my-5 text-center">Nouvelle liste</h2>
	
		
      	<c:if test="${!empty listeCodesErreur}">
			<div class="alert alert-danger" role="alert">
			  <strong>Erreur!</strong>
			  <ul>
			  	<c:forEach var="code" items="${listeCodesErreur}">
			  		<li>${LecteurMessage.getMessageErreur(code)}</li>
			  	</c:forEach>
			  </ul>
			</div>
		</c:if>

		<form method="post" action="${pageContext.request.contextPath}/nouvelle" class="row justify-content-center mb-2">
      		<label for="nom_liste" class="col-2 col-form-label">Nom :</label>
  			<div class="col-10">
  				<c:if test="${ empty listeCourse}">
					<input class="form-control" type="text" id="nom_liste" name="nom_liste" value="${param.nom_liste}">
				</c:if>
				<c:if test="${ !empty listeCourse}">
					<p>${listeCourse.nom}</p>
				</c:if>
			</div>

			<div class="row">
				<ul class="list-group col-12">
					<c:if test="${ !empty listeCourse}">
						<input type="hidden" value="${listeCourse.id}" name="id"/>
					</c:if>
					<c:choose>
			    		<c:when test="${listeCourse.articles.size()>0}">
					        
					        	<c:forEach var="a" items="${listeCourse.articles}">
						            <li class="list-group-item d-flex justify-content-between align-items-center">${a.nom }
										<div>
											<a href="${pageContext.request.contextPath}/supprimer?id=${listeCourse.id}&id_article=${a.id}" class="badge text-danger" title="Supprimer"><i class="material-icons">delete</i></a>
										</div>
									</li>
						      	</c:forEach>
					    </c:when>
				        <c:otherwise>
				        	<p>Pas d'article actuellement.<p>
				        </c:otherwise>
			        </c:choose>
							<li class="list-group-item d-flex justify-content-between align-items-center">
								<input class="form-control" type="text" id="nom_article" name="nom_article" value="${!empty listeCodesErreur?param.nom_article:''}">
								<div>
									<input type="submit" class="btn btn-primary"></input>
									<a href="#add" class="badge text-success" title="Ajouter"></a>
								</div>
							</li>
					    </ul>
			</div>
		</form>

		<div class="row py-5"></div>
	</div>
	
    <!-- Footer -->
	<footer class="row bg-dark footer-demodule fixed-bottom py-1">
		<div class="col-lg-4 offset-lg-4 text-center">
			<a class="btn" href="${pageContext.request.contextPath}/listes" title="Retour à  la liste des courses"><i class="material-icons">arrow_back</i></a>
		</div>
		<!-- /.container -->
	</footer>

    <%@ include file="./fragments/footer.html" %>
  </body>

</html>
