package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        Node destination = data.getDestination();
        boolean type=false; //type est true si le cout est par rapport à la longueur et false sinon
        if (data.getMode()== AbstractInputData.Mode.LENGTH)
        	type = true;
        	
        //initialisation des labels
        for (Node noeud : (data.getGraph()).getNodes()) {
        	//dans ce tableau un id correspond à l'index du noeud
        	labels[noeud.getId()] = new LabelStar(noeud, destination,type);
        }
    }

}
