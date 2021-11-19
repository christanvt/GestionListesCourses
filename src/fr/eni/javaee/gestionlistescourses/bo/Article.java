package fr.eni.javaee.gestionlistescourses.bo;

import java.io.Serializable;

public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String nom;
	private boolean coche;
	
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
	
	public boolean isCoche() {
		return coche;
	}
	public void setCoche(boolean coche) {
		this.coche = coche;
	}
	public Article() {
	
	}
	
	public Article(int id, String nom, boolean coche) {
		this(id,nom);
		this.coche = coche;
	}
	public Article(int id, String nom) {
		this(nom);
		this.id = id;
	}
	public Article(String nom) {
		super();
		this.nom = nom;
	}
	
}
