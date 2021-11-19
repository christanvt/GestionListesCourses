package fr.eni.javaee.gestionlistescourses.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListeCourse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nom;
	private List<Article> articles=new ArrayList<Article>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
	public ListeCourse() {

	}
	public ListeCourse(int id, String nom) {
		this(nom);
		this.id = id;
	}
	public ListeCourse(String nom) {
		super();
		this.nom = nom;
	}
	@Override
	public String toString() {
		return "ListeCourse [id=" + id + ", nom=" + nom + "]";
	}
	
	
	
}
