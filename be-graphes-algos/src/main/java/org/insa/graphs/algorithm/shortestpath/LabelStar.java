package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label implements Comparable<Label> {
	
	//cout estime
	private double coutEstime;
	
	private Node destination;
	
	private boolean type; //type est true si le cout est par rapport à la longueur et false sinon
	
	//prenons 200km/h => 55.56m/s pour la vitesse à vol d'oiseau
	
	//contructor
	
	public LabelStar(Node node, Node dest, boolean type) {
		super(node);
		this.destination = dest;
		if (type== true)
			this.coutEstime = Point.distance(this.sommetCourant.getPoint(), this.destination.getPoint());
		else
			this.coutEstime = (Point.distance(this.sommetCourant.getPoint(), this.destination.getPoint()))/55.56;
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