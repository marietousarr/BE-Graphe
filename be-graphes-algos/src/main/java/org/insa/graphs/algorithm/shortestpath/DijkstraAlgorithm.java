package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
       
        
        // TODO:
        
        BinaryHeap<Label> tas = new BinaryHeap();
        
        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        Label[] labels = new Label[nbNodes];
        
        //initialisation des labels
        int i;
        for (i=0;i<nbNodes;i++) {
        	labels[i] = new Label(i);
        }
        
        //marquage de l'origine
        int idOrigin = (data.getOrigin()).getId();
        labels[idOrigin].setCost(0);
        labels[idOrigin].mark();
        tas.insert(labels[idOrigin]);
       
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // on commence l'algorithme
        boolean notReached = true;
        
        while (notReached && !tas.isEmpty()) {
            Label labelsommetMin = tas.deleteMin();
            int idMin = labelsommetMin.getId();
            Node sommetMin = graph.get(idMin);
            labels[idMin].mark();
            
            for (Arc arc: sommetMin.getSuccessors()) {
            	Node noeudCourant = arc.getDestination();
            	int id = (arc.getDestination()).getId();
            	
                // Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
                
                if (!labels[id].isMarked()) {
                
                    double w = data.getCost(arc);
                    double oldCost = labels[id].getCost();
                    double newCost = labels[idMin].getCost() + w;
                    
                    if (Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
                        notifyNodeReached(noeudCourant);
                    }
                    
                    if (newCost < oldCost) {
                    	labels[id].setCost(newCost);
                    	labels[id].setFather(arc);
                    	if (Double.isFinite(oldCost)) {
                    		tas.remove(labels[id]);
                    	}
                    	tas.insert(labels[id]);
                    }
                }
            }
            
            //on verifie si on est arrivé à destination
            notReached = false;
        	if (!labels[data.getDestination().getId()].isMarked()) {
        		notReached = true;
        	}
        }
        	
    	//on construit la solution
    	
    	if (labels[data.getDestination().getId()].arcPere==null) {
    		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
    	} else {
    		
    		// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
         // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            
            int encours = data.getDestination().getId();
            Arc arcPath = null;
            while (labels[encours].arcPere!=null) {
            	arcPath = labels[encours].arcPere;
            	arcs.add(arcPath);
            	encours = arcPath.getOrigin().getId();
            }
    	
    	
    	 // Reverse the path...
        Collections.reverse(arcs);

        // Create the final solution.
        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
    }
            
            
    return solution;
    }

}
