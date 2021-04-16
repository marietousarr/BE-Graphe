package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label{
	
	//attributs
	protected int sommetCourant; //sera égal à l'id du noeud pour l'association
	
	private boolean marque;
	
	private double cout;
	
	private Arc arcPere;
	
	
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

}
