package fr.eni.javaee.gestionlistescourses.servlets;

import java.io.IOException;
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
import fr.eni.javaee.gestionlistescourses.bo.ListeCourse;

/**
 * Servlet implementation class ServletListe
 */
@WebServlet("/listes")
public class ServletListes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletListes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ListeCourseManager listeCourseManager = new ListeCourseManager();
		List<Integer> listeCodesErreur=new ArrayList<>();
		int idListeCourse=0;
		//Dois-je supprimer une liste?
		if(request.getParameter("supprimer")!=null)
		{
			idListeCourse = this.lireParametreSupprimer(request, listeCodesErreur);
		}
		if(listeCodesErreur.size()>0)
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);
		}
		else
		{
			try {
				listeCourseManager.supprimerListeCourse(idListeCourse);
			} catch (BusinessException e) {
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				e.printStackTrace();
			}
		}
		
		
		try {
			request.setAttribute("listesCourse", listeCourseManager.selectionnerListes());
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/listes.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private int lireParametreSupprimer(HttpServletRequest request, List<Integer> listeCodesErreur) {
		int idListeCourse=0;
		try
		{
			if(request.getParameter("supprimer")!=null)
			{
				idListeCourse = Integer.parseInt(request.getParameter("supprimer"));
			}
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_ID_LISTE_ERREUR);
		}
		return idListeCourse;
	}

}









