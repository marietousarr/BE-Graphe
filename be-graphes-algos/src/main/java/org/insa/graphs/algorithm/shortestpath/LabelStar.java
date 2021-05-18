package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.Graph;

public class LabelStar extends Label implements Comparable<Label> {
	
	//cout estime
	private double coutEstime;
	
	private Node destination;
	
	private double vitMax;//vitMax est non nul si le cout est par rapport à la longueur
	
	//prenons la vitesse max du graphe 
	
	//contructor
	
	public LabelStar(Node node, Node dest, double vitMax) {
		super(node);
		this.destination = dest;
		this.vitMax = vitMax;
		if (Double.compare(0.0, this.vitMax)==0)
			this.coutEstime = Point.distance(this.sommetCourant.getPoint(), this.destination.getPoint());
		else
			this.coutEstime = (Point.distance(this.sommetCourant.getPoint(), this.destination.getPoint()))/(vitMax);
	}
	
	//methodes
	public double getCoutEstime() {
		return this.coutEstime;
	}
	
	public double getTotalCost() {
		return this.cout + this.coutEstime;
	}
	
	
	public int compareTo( LabelStar y) {
		int res = Double.compare(getTotalCost(), y.getTotalCost());
		if (res==0)
			res = Double.compare(this.coutEstime, y.getCoutEstime());
		return  res;
	}
	
	
	

}