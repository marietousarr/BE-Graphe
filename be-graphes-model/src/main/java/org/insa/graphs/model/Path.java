package org.insa.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Class representing a path between nodes in a graph.
 * </p>
 * 
 * <p>
 * A path is represented as a list of {@link Arc} with an origin and not a list
 * of {@link Node} due to the multi-graph nature (multiple arcs between two
 * nodes) of the considered graphs.
 * </p>
 *
 */
public class Path {

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
    public static Path createFastestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();
        // TODO:        
        if (nodes.size()==1) {
        	return new Path(graph, nodes.get(0));
        } else if (nodes.size()==0){
        	return new Path(graph);
        } else {
        	int i;
	        for (i=0; i< (nodes.size()-1); i++) {
	        	List<Arc> successors = (nodes.get(i)).getSuccessors();
	        	int j;
	        	int max=0;
	        	Arc arcMax=null;
	        	for (j=0; j< (successors.size()); j++) {
	        		if ((successors.get(j)).getDestination() == nodes.get(i+1) && max < ((successors.get(j)).getRoadInformation()).getMaximumSpeed()){
	        			// au cas ou deux arcs differents avec même origine et même dest aurait la même duree min
		        			max=((successors.get(j)).getRoadInformation()).getMaximumSpeed();
		        			arcMax = successors.get(j);
	        		}
	        	}
	        	if (arcMax == null)
	        		throw new IllegalArgumentException("the list of nodes is not valid");
		        arcs.add(arcMax);
	        }
	        
	        return new Path(graph, arcs);
        }
    }

    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the shortest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
    public static Path createShortestPathFromNodes(Graph graph, List<Node> nodes)
            throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>();
        // TODO: 
        if (nodes.size()==1) {
        	return new Path(graph, nodes.get(0));
        } else if (nodes.size()==0){
        	return new Path(graph);
        }else {
	        int i;
	        for (i=0; i< (nodes.size()-1); i++) {
	        	List<Arc> successors = (nodes.get(i)).getSuccessors();
	        	int j;
	        	double shortest=java.lang.Double.POSITIVE_INFINITY; //(successors.get(0)).getLength()
	        	Arc arcShort=null;
	        	for (j=0; j< (successors.size()); j++) {
	        		if ((successors.get(j)).getDestination() == nodes.get(i+1) && shortest > (successors.get(j)).getLength()){
	        			//parfois deux arcs différents ont la meme origine et la meme destination et la meme longueur
		        			shortest=(successors.get(j)).getLength();
		        			arcShort = successors.get(j);
	        			
	        			//if (shortest == (successors.get(j)).getLength())
	        			//	System.out.println("deux arcs de meme origine et meme destination ont la même longueur");
	        		}
	        	}
	        	if (arcShort == null)
	        		throw new IllegalArgumentException("the list of nodes is not valid");
		        arcs.add(arcShort);
	
	        }
	        return new Path(graph, arcs);
        }
    }

    /**
     * Concatenate the given paths.
     * 
     * @param paths Array of paths to concatenate.
     * 
     * @return Concatenated path.
     * 
     * @throws IllegalArgumentException if the paths cannot be concatenated (IDs of
     *         map do not match, or the end of a path is not the beginning of the
     *         next).
     */
    public static Path concatenate(Path... paths) throws IllegalArgumentException {
        if (paths.length == 0) {
            throw new IllegalArgumentException("Cannot concatenate an empty list of paths.");
        }
        final String mapId = paths[0].getGraph().getMapId();
        for (int i = 1; i < paths.length; ++i) {
            if (!paths[i].getGraph().getMapId().equals(mapId)) {
                throw new IllegalArgumentException(
                        "Cannot concatenate paths from different graphs.");
            }
        }
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Path path: paths) {
            arcs.addAll(path.getArcs());
        }
        Path path = new Path(paths[0].getGraph(), arcs);
        if (!path.isValid()) {
            throw new IllegalArgumentException(
                    "Cannot concatenate paths that do not form a single path.");
        }
        return path;
    }

    // Graph containing this path.
    private final Graph graph;

    // Origin of the path
    private final Node origin;

    // List of arcs in this path.
    private final List<Arc> arcs;

    /**
     * Create an empty path corresponding to the given graph.
     * 
     * @param graph Graph containing the path.
     */
    public Path(Graph graph) {
        this.graph = graph;
        this.origin = null;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path containing a single node.
     * 
     * @param graph Graph containing the path.
     * @param node Single node of the path.
     */
    public Path(Graph graph, Node node) {
        this.graph = graph;
        this.origin = node;
        this.arcs = new ArrayList<>();
    }

    /**
     * Create a new path with the given list of arcs.
     * 
     * @param graph Graph containing the path.
     * @param arcs Arcs to construct the path.
     */
    public Path(Graph graph, List<Arc> arcs) {
        this.graph = graph;
        this.arcs = arcs;
        this.origin = arcs.size() > 0 ? arcs.get(0).getOrigin() : null;
    }

    /**
     * @return Graph containing the path.
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * @return First node of the path.
     */
    public Node getOrigin() {
        return origin;
    }

    /**
     * @return Last node of the path.
     */
    public Node getDestination() {
    	if ((this.arcs).size()==0)
    		return this.origin;
    	else 
    		return arcs.get(arcs.size() - 1).getDestination();
    }

    /**
     * @return List of arcs in the path.
     */
    public List<Arc> getArcs() {
        return Collections.unmodifiableList(arcs);
    }

    /**
     * Check if this path is empty (it does not contain any node).
     * 
     * @return true if this path is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.origin == null;
    }

    /**
     * Get the number of <b>nodes</b> in this path.
     * 
     * @return Number of nodes in this path.
     */
    public int size() {
        return isEmpty() ? 0 : 1 + this.arcs.size();
    }

    /**
     * Check if this path is valid.
     * 
     * A path is valid if any of the following is true:
     * <ul>
     * <li>it is empty;</li>
     * <li>it contains a single node (without arcs);</li>
     * <li>the first arc has for origin the origin of the path and, for two
     * consecutive arcs, the destination of the first one is the origin of the
     * second one.</li>
     * </ul>
     * 
     * @return true if the path is valid, false otherwise.
     * 
     * implemented.
     */
    public boolean isValid() {
        // TODO:
    	boolean res= false;
    	
    	if (this.isEmpty()) {
    		res= true;
    	} else if ((this.arcs).size()==0){
    		res= true;
    	} else if (((this.arcs).get(0)).getOrigin()==this.origin) {
    		res=true;
    		int i=0;
    		while (res && i<=((this.arcs).size() -2)) {
    			if (((this.arcs).get(i)).getDestination()!= ((this.arcs).get(i+1)).getOrigin()) {
    				res=false;
    			}
    			i++;
    		}
    	}
        return res;
    }

    /**
     * Compute the length of this path (in meters).
     * 
     * @return Total length of the path (in meters).
     * 
     * implemented.
     */
    public float getLength() {
    	
    	float length=0;
    	//Iterator<Arc> liste_arcs = (this.arcs).iterator();
    	for ( Arc element : this.arcs) {
    		length += element.getLength();
    	}
    	
        return length;
    }

    /**
     * Compute the time required to travel this path if moving at the given speed.
     * 
     * @param speed Speed to compute the travel time.
     * 
     * @return Time (in seconds) required to travel this path at the given speed (in
     *         kilometers-per-hour).
     * 
     * implemented.
     */
    public double getTravelTime(double speed) {
    	if (speed< 0)
    		return getMinimumTravelTime();
    	double time=0;
    	for ( Arc element : this.arcs) {
    		time += element.getTravelTime(speed);
    	}
    	
        return time;
    }

    /**
     * Compute the time to travel this path if moving at the maximum allowed speed
     * on every arc.
     * 
     * @return Minimum travel time to travel this path (in seconds).
     * 
     * implemented.
     */
    public double getMinimumTravelTime() {
    	double time=0;
    	for ( Arc element : this.arcs) {
    		time += element.getMinimumTravelTime();
    	}
        return time;
    }
    
    /**
     * Create a new path that goes through the given list of nodes (in order),
     * choosing the fastest route if multiple are available.
     * 
     * @param graph Graph containing the nodes in the list.
     * @param nodes List of nodes to build the path.
     * 
     * @return A path that goes through the given list of nodes.
     * 
     * @throws IllegalArgumentException If the list of nodes is not valid, i.e. two
     *         consecutive nodes in the list are not connected in the graph.
     * 
     */
    public static Path createFastestPathFromNodes2(Graph graph, List<Node> nodes, double speed) // la premiere fonction ne donne pas le chemin le plus rapide effectif et en la modifant comme celle ci les test deviennent faux 
            throws IllegalArgumentException {
        List<Arc> arcs = new ArrayList<Arc>(); 
        if (nodes.size()==1) {
        	return new Path(graph, nodes.get(0));
        } else if (nodes.size()==0){
        	return new Path(graph);
        } else {
        	int i;
	        for (i=0; i< (nodes.size()-1); i++) {
	        	List<Arc> successors = (nodes.get(i)).getSuccessors();
	        	int j;
	        	double max=java.lang.Double.POSITIVE_INFINITY;
	        	Arc arcMax=null;
	        	
	        	for (j=0; j< (successors.size()); j++) {
	        		double duree=0;
	        		
	        		if (speed <=0) 
	        			duree = (successors.get(j)).getMinimumTravelTime();
	        		else 
	        			duree = (successors.get(j)).getTravelTime(speed);
	        		
	        		if ((successors.get(j)).getDestination() == nodes.get(i+1) && (max > duree)){
	        			// au cas ou deux arcs differents avec même origine et même dest aurait la même duree min
		        			max=duree;
		        			arcMax = successors.get(j);
	        		}
	        	}
	        	if (arcMax == null)
	        		throw new IllegalArgumentException("the list of nodes is not valid");
		        arcs.add(arcMax);
	        }
	        
	        return new Path(graph, arcs);
        }
    }
    
    public String toString() {
    	String ret= "//";
    	for (Arc arc : this.getArcs())
    		ret += "-"+arc;
    	return ret+"//";
    }

}
