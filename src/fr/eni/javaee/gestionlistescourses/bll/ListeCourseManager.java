package fr.eni.javaee.gestionlistescourses.bll;

import java.util.List;

import fr.eni.javaee.gestionlistescourses.BusinessException;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.ListeCourse;
import fr.eni.javaee.gestionlistescourses.dal.DAOFactory;
import fr.eni.javaee.gestionlistescourses.dal.ListeCourseDAO;

public class ListeCourseManager {
	private ListeCourseDAO listeCourseDAO;
	
	public ListeCourseManager() {
		this.listeCourseDAO=DAOFactory.getListeCourseDAO();
	}
	
	public List<ListeCourse> selectionnerListes() throws BusinessException
	{
		return this.listeCourseDAO.selectAll();
	}

	public ListeCourse selectionnerListe(int idListeCourse) throws BusinessException {
		return this.listeCourseDAO.selectById(idListeCourse);
	}

	public void ajouterArticle(int idListeCourse, String nomArticle) throws BusinessException{
		BusinessException businessException = new BusinessException();
		this.validerNomArticle(nomArticle, businessException);
				
		if(!businessException.hasErreurs())
		{
			ListeCourse listeCourse = new ListeCourse();
			listeCourse.setId(idListeCourse);
			
			Article article = new Article(nomArticle.trim());
			listeCourse.getArticles().add(article);
			this.listeCourseDAO.insert(listeCourse);
		}
		else
		{
			throw businessException;
		}
	}
	
	public ListeCourse ajouterListeEtArticle(String nomListe, String nomArticle) throws BusinessException {
		BusinessException businessException = new BusinessException();
		
		this.validerNomListe(nomListe, businessException);
		this.validerNomArticle(nomArticle, businessException);
		
		ListeCourse listeCourse=null;
		
		if(!businessException.hasErreurs())
		{
			listeCourse = new ListeCourse();
			listeCourse.setNom(nomListe);
		
			Article article = new Article(nomArticle.trim());
			listeCourse.getArticles().add(article);
			this.listeCourseDAO.insert(listeCourse);
		}
		else
		{
			throw businessException;
		}
		return listeCourse;
	}

	public void supprimerArticle(int idArticle) throws BusinessException{
		this.listeCourseDAO.deleteArticle(idArticle);
	}
	public void supprimerListeCourse(int idListeCourse) throws BusinessException {
		this.listeCourseDAO.delete(idListeCourse);
	}
	public void cocherArticle(int idArticle) throws BusinessException
	{
		this.listeCourseDAO.cocherArticle(idArticle);
	}
	public void decocherArticle(int idArticle) throws BusinessException
	{
		this.listeCourseDAO.decocherArticle(idArticle);
	}
	public void decocherListe(int idListeCourse) throws BusinessException
	{
		this.listeCourseDAO.decocherListeCourse(idListeCourse);
	}
	
	private void validerNomListe(String nomListe, BusinessException businessException) {
		if(nomListe==null || nomListe.trim().length()>50)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_LISTE_NOM_ERREUR);
		}
	}

	private void validerNomArticle(String nomArticle, BusinessException businessException) {
		if(nomArticle==null || nomArticle.trim().length()>50)
		{
			businessException.ajouterErreur(CodesResultatBLL.REGLE_ARTICLE_NOM_ERREUR);
		}
	}

	

	
}
