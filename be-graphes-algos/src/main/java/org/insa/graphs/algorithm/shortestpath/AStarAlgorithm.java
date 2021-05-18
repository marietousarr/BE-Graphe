package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        Node destination = data.getDestination();
        
        double vitesseMax=0.0; //vitesse max est non nul si le cout est par rapport à la longueur
        if (data.getMode()== AbstractInputData.Mode.TIME)
        	if (data.getGraph().getGraphInformation().hasMaximumSpeed())
        		vitesseMax = data.getGraph().getGraphInformation().getMaximumSpeed()/3.6;
        	else 
        		vitesseMax = 130/3.6;
        
        //initialisation des labels
        for (Node noeud : (data.getGraph()).getNodes()) {
        	//dans ce tableau un id correspond à l'index du noeud
        	labels[noeud.getId()] = new LabelStar(noeud, destination,vitesseMax);
        }
    }

}
