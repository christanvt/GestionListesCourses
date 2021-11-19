package fr.eni.javaee.gestionlistescourses.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.gestionlistescourses.BusinessException;
import fr.eni.javaee.gestionlistescourses.bo.Article;
import fr.eni.javaee.gestionlistescourses.bo.ListeCourse;

public class ListeCourseDAOJdbcImpl implements ListeCourseDAO {

	private static final String SELECT_ALL = "SELECT id, nom FROM LISTES";
	private static final String SELECT_BY_ID =	"select " + 
												"	l.id as id_liste, " + 
												"	l.nom as nom_liste, " + 
												"	a.id as id_article, " + 
												"	a.nom as nom_article, " + 
												"	a.coche " +
												"from " + 
												"	listes l " + 
												"	left join articles a on l.id=a.id_liste "+
												"where l.id=?";
	private static final String INSERT_LISTE = "insert into LISTES(nom) values(?);";
	private static final String INSERT_ARTICLE = "insert into ARTICLES(nom, id_liste) values(?,?);";
	private static final String DELETE_ARTICLE = "delete from ARTICLES where id=?";
	private static final String DELETE_LISTE = "delete from LISTES where id=?";
	private static final String UPDATE_COCHE_ARTICLE="update ARTICLES set coche=1 where id=?";
	private static final String UPDATE_DECOCHE_ARTICLE="update ARTICLES set coche=0 where id=?";
	private static final String UPDATE_DECOCHE_ARTICLES="update ARTICLES set coche=0 where id_liste=?";
	
	@Override
	public void insert(ListeCourse listeCourse) throws BusinessException {
		if(listeCourse==null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;
				if(listeCourse.getId()==0)
				{
					pstmt = cnx.prepareStatement(INSERT_LISTE, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, listeCourse.getNom());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if(rs.next())
					{
						listeCourse.setId(rs.getInt(1));
					}
					rs.close();
					pstmt.close();
				}
				if(listeCourse.getArticles().size()==1)
				{
					pstmt = cnx.prepareStatement(INSERT_ARTICLE);
					pstmt.setString(1, listeCourse.getArticles().get(0).getNom());
					pstmt.setInt(2, listeCourse.getId());
					pstmt.executeUpdate();
					pstmt.close();
				}
				cnx.commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}
		
	}

	@Override
	public void delete(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_LISTE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_LISTE_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public List<ListeCourse> selectAll() throws BusinessException {
		List<ListeCourse> listesCourse = new ArrayList<ListeCourse>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				listesCourse.add(new ListeCourse(rs.getInt("id"), rs.getString("nom")));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTES_ECHEC);
			throw businessException;
		}
		return listesCourse;
	}

	@Override
	public ListeCourse selectById(int id) throws BusinessException {
		ListeCourse listeCourse = new ListeCourse();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne=true;
			while(rs.next())
			{
				if(premiereLigne)
				{
					listeCourse.setId(rs.getInt("id_liste"));
					listeCourse.setNom(rs.getString("nom_liste"));
					premiereLigne=false;
				}
				if(rs.getString("nom_article")!=null)
				{
					listeCourse.getArticles().add(new Article(rs.getInt("id_article"), rs.getString("nom_article"), rs.getBoolean("coche")));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTE_ECHEC);
			throw businessException;
		}
		if(listeCourse.getId()==0)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTE_INEXISTANTE);
			throw businessException;
		}
		
		return listeCourse;
	}

	@Override
	public void deleteArticle(int idArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ARTICLE_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public void cocherArticle(int idArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_COCHE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.COCHE_ARTICLE_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public void decocherArticle(int idArticle) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_DECOCHE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DECOCHE_ARTICLE_ERREUR);
			throw businessException;
		}
		
	}

	@Override
	public void decocherListeCourse(int idListeCourse) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_DECOCHE_ARTICLES);
			pstmt.setInt(1, idListeCourse);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DECOCHE_ARTICLES_ERREUR);
			throw businessException;
		}
		
	}

}











