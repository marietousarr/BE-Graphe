package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	//attributs :
	protected BinaryHeap<Label> tas;
	public Label[] labels;
	

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        tas = new BinaryHeap();
        labels = new Label[data.getGraph().size()];
        
        //initialisation des labels
        for (Node noeud : (data.getGraph()).getNodes()) {
        	//dans ce tableau un id correspond à l'index du noeud
        	labels[noeud.getId()] = new Label(noeud);
        }
    }

    @Override
    protected ShortestPathSolution doRun(){
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
       
        
        // TODO:
        
        Graph graph = data.getGraph();
        
        //marquage de l'origine
        int idOrigin = (data.getOrigin()).getId();
        labels[idOrigin].setCost(0);
        labels[idOrigin].mark();
        notifyNodeMarked(data.getOrigin());
        tas.insert(labels[idOrigin]);
       
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        //int cpt=0; //compteur de sommets explorés
        
        // on commence l'algorithme
        boolean notReached = true;
        
        while (notReached && !tas.isEmpty()) {
            Label labelsommetMin = tas.deleteMin();
            int idMin = labelsommetMin.getId();
            Node sommetMin = graph.get(idMin);
            labels[idMin].mark();
         	notifyNodeMarked(sommetMin);
         	//System.out.println("le cout du sommet min est " + labels[idMin].getTotalCost());
            
         	//cpt = 0;
         	
         	//System.out.println("le nombre de successeurs du sommet : " +sommetMin.getSuccessors().size());
            for (Arc arc: sommetMin.getSuccessors()) {
            	
            	Node noeudCourant = arc.getDestination();
            	int id = (arc.getDestination()).getId();
                
            	// Small test to check allowed roads...
                if (!data.isAllowed(arc)) {
                    continue;
                }
                
                if (!labels[id].isMarked()) {
                
                    double w = data.getCost(arc);
                    double oldCost = (labels[id]).getCost();
                    double newCost = (labels[idMin]).getCost() + w;
                    
                    if (Double.isInfinite(oldCost) && Double.isFinite(newCost)) {
                        notifyNodeReached(noeudCourant);
                    }
                    
                    if (newCost < oldCost) {
                    	if (Double.isFinite(oldCost)) {
                    		tas.remove(labels[id]);
                    	}
                    	labels[id].setCost(newCost);
                    	labels[id].setFather(arc);
                    	tas.insert(labels[id]);
                    	//if (!tas.isValid())
                    		//System.out.println(" Le tas est valide : " + false);
                    }
                }
                //cpt++;
            }
            
            //on verifie si on est arrivé à destination
            notReached = false;
        	if (!labels[data.getDestination().getId()].isMarked()) {
        		notReached = true;
        	}
        	
        	//System.out.println("le nombre de sommets exploré est :" + cpt);
        }
        	
    	//on construit la solution
    	if (labels[data.getDestination().getId()].arcPere==null && data.getOrigin()!= data.getDestination()) {
    		solution = new ShortestPathSolution(data, Status.INFEASIBLE);
    	} else {
    		
    		// The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());
            
         // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            
            int encours = data.getDestination().getId();
            
            //System.out.println(labels[encours].getTotalCost()); parfois on a NaN voir LabelStar
            
            Arc arcPath = null;
            while (labels[encours].arcPere!=null) {
            	arcPath = labels[encours].arcPere;
            	arcs.add(arcPath);
            	encours = arcPath.getOrigin().getId();
            }
    	
    	
    	 // Reverse the path...
        Collections.reverse(arcs);

        // Create the final solution.
        Path monpcc=null;
        if (data.getOrigin()==data.getDestination())
        	monpcc = new Path(graph, data.getOrigin());
        else 
        	monpcc = new Path(graph, arcs);
        
        //System.out.println(" Mon chemin est il valide ? " + monpcc.isValid() + " son cout est de " + monpcc.getLength());
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, monpcc);
    }
    	
    return solution;
    }

}
