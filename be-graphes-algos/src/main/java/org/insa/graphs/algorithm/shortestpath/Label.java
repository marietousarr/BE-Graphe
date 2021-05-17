package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	
	//attributs
	protected Node sommetCourant; //sera égal à l'id du noeud pour l'association
	
	protected boolean marque;
	
	protected double cout;
	
	public Arc arcPere;
	
	
	//Contructeur
	public Label(Node node) {
		this.sommetCourant = node;
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
		return sommetCourant.getId();
	}
	
	public double getTotalCost() {
		return this.cout;
	}
	
	public int compareTo( Label y) {
		return Double.compare(getTotalCost(), y.getTotalCost()) ;
	}
	
	public String toString() {
		return this.getId()+" "+cout;
	}

}
