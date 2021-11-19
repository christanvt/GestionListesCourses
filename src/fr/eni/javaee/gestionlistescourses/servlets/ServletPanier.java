package fr.eni.javaee.gestionlistescourses.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.gestionlistescourses.BusinessException;
import fr.eni.javaee.gestionlistescourses.bll.ListeCourseManager;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.ListeCourse;

/**
 * Servlet implementation class ServletPanier
 */
@WebServlet("/panier")
public class ServletPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPanier() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Je lis les paramètres
		cocherDecocher(request, response);
		
		int idListeCourse=0;
		List<Integer> listeCodesErreur=new ArrayList<>();
		try
		{
			idListeCourse = Integer.parseInt(request.getParameter("id"));
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_ID_LISTE_ERREUR);
		}
			
		if(listeCodesErreur.size()>0)
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);
		}
		else
		{
			//J'ai un id au bon format, je récupère la liste associée eventuelle
			ListeCourseManager listeCourseManager = new ListeCourseManager();
			ListeCourse listeCourse;
			try {
				listeCourse = listeCourseManager.selectionnerListe(idListeCourse);
				request.setAttribute("listeCourse", listeCourse);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
			
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/panier.jsp");
		rd.forward(request, response);
	}

	private void cocherDecocher(HttpServletRequest request, HttpServletResponse response) {
		ListeCourseManager listeCourseManager = new ListeCourseManager();
		if(request.getParameter("id_article")!=null)
		{
			try
			{
				int idArticle = Integer.parseInt(request.getParameter("id_article"));
				if(request.getParameter("coche")!=null)
				{
					listeCourseManager.cocherArticle(idArticle);
				}
				else
				{
					listeCourseManager.decocherArticle(idArticle);
				}
			}
			catch(BusinessException e)
			{
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
		}
		else if(request.getParameter("clear")!=null)
		{
			try
			{
				int idListeCourse = Integer.parseInt(request.getParameter("id"));
				listeCourseManager.decocherListe(idListeCourse);
			}
			catch(BusinessException e)
			{
				e.printStackTrace();
				request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
