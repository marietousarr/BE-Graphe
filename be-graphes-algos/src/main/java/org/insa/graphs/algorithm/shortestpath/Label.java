package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label>{
	
	//attributs
	protected int sommetCourant; //sera égal à l'id du noeud pour l'association
	
	private boolean marque;
	
	private double cout;
	
	public Arc arcPere;
	
	
	//Contructeur
	public Label(int idNode) {
		this.sommetCourant = idNode;
		this.marque= false;
		this.cout = java.lang.Double.POSITIVE_INFINITY;
	}
	
	///méthode
	public double getCost() {
		return this.cout;
	}
	
	public void setCost(double c) {
		this.cout= c;
	}
	
	public boolean isMarked() {
		return this.marque;
	}
	
	public void mark() {
		this.marque = true;
	}
	
	public void setFather(Arc arc) {
		this.arcPere = arc;
	}
	
	public int getId() {
		return sommetCourant;
	}
	
	public int compareTo( Label y) {
		return Double.compare(getCost(), y.getCost());
	}

}
