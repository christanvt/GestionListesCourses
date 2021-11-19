package fr.eni.javaee.gestionlistescourses.dal;

public abstract class DAOFactory {
	
	public static ListeCourseDAO getListeCourseDAO()
	{
		return new ListeCourseDAOJdbcImpl();
	}
}
	