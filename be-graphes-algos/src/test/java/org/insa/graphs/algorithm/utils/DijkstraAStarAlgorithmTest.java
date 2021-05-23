package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.insa.graphs.algorithm.AbstractSolution;
import org.insa.graphs.algorithm.AlgorithmFactory;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.utils.PriorityQueueTest.MutableInteger;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.algorithm.shortestpath.LabelStar;

import org.insa.graphs.algorithm.weakconnectivity.WeaklyConnectedComponentsData;
import org.insa.graphs.algorithm.weakconnectivity.WeaklyConnectedComponentsSolution;
import org.insa.graphs.algorithm.weakconnectivity.WeaklyConnectedComponentsAlgorithm;


public abstract class DijkstraAStarAlgorithmTest {
	
    protected String arg;
    
    private static Graph[] graphs;
    
    public abstract String nomAlgo();
    
    @Before
    public void init() {
    	this.arg = this.nomAlgo();
    }
    
    @BeforeClass
    public static void initAll() throws IOException {
    	
	   	 graphs = new Graph[3];
	   	 
	   	 String mapDirectory = "C:\\Users\\Mariétou SARR\\Documents\\INSA Toulouse Cours\\Graphes\\BE Graphe\\Maps\\";
	   	 String mapCarre = mapDirectory + "carre.mapgr";
	   	 String mapMidiPyrenees = mapDirectory + "midi-pyrenees.mapgr";
	   	 String mapToulouse = mapDirectory + "toulouse.mapgr";
	   	 
	   	 GraphReader readerCarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
	   	 Graph g1 = readerCarre.read();
	   	 
	   	 GraphReader readerToulouse = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapToulouse))));
	   	 Graph g2 = readerToulouse.read();
	   	 
	   	 GraphReader readerMidiPyrenees = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapMidiPyrenees))));
	   	 Graph g3 = readerMidiPyrenees.read();
	   	 
	   	 graphs[0]=g1;
	   	 graphs[1]=g2;
	   	 graphs[2]=g3;
   	 
    }
    
    
    @Test
    public void TestValid() throws Exception {
   	 int i=0;
   	 while (i<50) {
   	 
	    	 Random rand = new Random();
	    	 int graphaleatoire = rand.nextInt(3);
	    	 
	    	 int nbnodes = graphs[graphaleatoire].size();
	
	    	 int randomnode1 = rand.nextInt(nbnodes);
	    	 int randomnode2 = rand.nextInt(nbnodes);
	    	 
	    	 
	    	 int mode=rand.nextInt(4);
	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
	    	 Node noeud2= graphs[graphaleatoire].get(randomnode2);
	    	 
	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	    	 
	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	    	 
	    	 ShortestPathSolution solution = null;
	    	 solution = spAlgorithm.run();
	    	 
	    	 if (solution.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	    		 i++;
		    	 Path pathsolution =solution.getPath();
		    	 assertEquals(true, pathsolution.isValid());
		    	 
	         }
   	 	}
		 
    }
    
    @Test
    public void TestLength() throws Exception {
   	 int i=0;
   	 while (i<50) {
   	 
	    	 Random rand = new Random();
	    	 int graphaleatoire = rand.nextInt(3);
	    	 
	    	 int nbnodes = graphs[graphaleatoire].size();
	    	 
	    	 int randomnode1 = rand.nextInt(nbnodes);
	    	 int randomnode2 = rand.nextInt(nbnodes);
	    	 
	    	 int mode=rand.nextInt(2);
	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
	    	 Node noeud2= graphs[graphaleatoire].get(randomnode2);
	    	 
	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	    	 
	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	    	 
	    	 ShortestPathSolution solution = null;
	    	 solution = spAlgorithm.run();
	      
	         if (solution.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	        	 i++;
		    	 Path pathsolution =solution.getPath();
		    	 int indexDest = pathsolution.getDestination().getId();
		    	 Label dest = spAlgorithm.labels[indexDest];

		    	 double costLength = dest.getTotalCost(); // à la destination le cout estime est nul le cout total est donc egal à dest.cout
		    	 
		    	 assertEquals(pathsolution.getLength(), costLength,1); // on prend un delta de 1 car getLength retourne des floats
		    	 
	         }
   	 	}
		 
    }
    
    @Test
    public void TestDuration() throws Exception {
   	 int i=0;
   	 
   	 while (i<50) {
   	 
	    	 Random rand = new Random();
	    	 int graphaleatoire = rand.nextInt(3);
	    	 
	    	 int nbnodes = graphs[graphaleatoire].size();
	    	 
	
	    	 int randomnode1 = rand.nextInt(nbnodes);
	    	 int randomnode2 = rand.nextInt(nbnodes);
	    	 
	    	 int mode=rand.nextInt(2)+2;
	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
	    	 Node noeud2= graphs[graphaleatoire].get(randomnode2);
	    	 
	    	 
	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	    	 
	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	    	 
	    	 ShortestPathSolution solution = null;
	    	 solution = spAlgorithm.run();
	      
	         if (solution.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	        	 i++;
		    	 Path pathsolution =solution.getPath();
		    	 int indexDest = pathsolution.getDestination().getId();
		    	 Label dest = spAlgorithm.labels[indexDest];

		    	 double costDuration = dest.getCost(); // à la destination le cout estime est nul le cout total est donc egal à dest.cout

		    	 double speed = data.getMaximumSpeed();
		    	 assertEquals(pathsolution.getTravelTime(speed), costDuration,0.0001);
		    	 
	         }
   	 	}
		 
    }
    
    
    
    @Test
    public void TestInfeasible() throws Exception {
    	
    	WeaklyConnectedComponentsData wcompdata = new WeaklyConnectedComponentsData(graphs[1]);
    	
    	WeaklyConnectedComponentsAlgorithm wcompalgo = (WeaklyConnectedComponentsAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(WeaklyConnectedComponentsAlgorithm.class, "WCC basic"), wcompdata);
    	
    	WeaklyConnectedComponentsSolution wcompsolution = null;
    	wcompsolution = wcompalgo.run();
    	
    	int taille=wcompsolution.getComponents().size();
    	
    	int i;
    	for (i=0; i<50; i++) {
    		 ArrayList<Node> weakNodes= wcompsolution.getComponents().get(i);
    	
	    	 Random rand = new Random();
	    	 int j = rand.nextInt(taille);
	    	 while (j==i) {
	    		 rand = new Random();
	    		 j = rand.nextInt(taille);
	    	 }
			 int randomnode1 = rand.nextInt(weakNodes.size());
	    	 int randomnode2 = rand.nextInt(wcompsolution.getComponents().get(j).size());
	    	 
	    	 int mode=rand.nextInt(5);
	    	 Node noeud1= wcompsolution.getComponents().get(i).get(randomnode1);
	    	 Node noeud2= wcompsolution.getComponents().get(j).get(randomnode2);
	    	 
	    	 ShortestPathData data= new ShortestPathData(graphs[1], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	    	 
	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	    	 
	    	 ShortestPathSolution solution = null;
	    	 solution = spAlgorithm.run();
	    	 
	    	 assertEquals(AbstractSolution.Status.INFEASIBLE, solution.getStatus());
  
    	}
    }
    
    @Test
    public void TestNullCost() throws Exception {
   	 // origine = destination
    	int i=0;
      	while (i<50) {
      	 
   	    	 Random rand = new Random();
   	    	 int graphaleatoire = rand.nextInt(3);
   	    	 
   	    	 int nbnodes = graphs[graphaleatoire].size();
   	    	 
   	
   	    	 int randomnode1 = rand.nextInt(nbnodes);
   	    	 
   	    	 int mode=rand.nextInt(4);
   	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
   	    	 
   	    	 
   	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud1, ArcInspectorFactory.getAllFilters().get(mode));
   	    	 
   	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
   	    	 
   	    	 ShortestPathSolution solution = null;
   	    	 solution = spAlgorithm.run();
   	      
	    	 Path pathsolution =solution.getPath();
	    	 int indexDest = pathsolution.getDestination().getId();
	    	 Label dest = spAlgorithm.labels[indexDest];

	    	 double costLength = dest.getCost();
	    	 assertEquals(0, costLength,0);
   		     i++;
      	 }
   		 
       }
    
    @Test
    public void TestCompareBellmanFord() throws Exception {
    	 int cpt=0;
    	 int mode=0;
  		 for (mode=0; mode < 5; mode++) {
  			 cpt = 0; 
  			 while (cpt<20) { 
  				 
	   	    	 Random rand = new Random();
	   	    	 int graphaleatoire = rand.nextInt(2); //on utilise soit la carte carre soit la carte de toulouse qui sont de petite cartes pour l'application de BF
	   	    	 
	   	    	 int nbnodes = graphs[graphaleatoire].size();
	   	
	   	    	 int randomnode1 = rand.nextInt(nbnodes);
	   	    	 int randomnode2 = rand.nextInt(nbnodes);
	   	    	 
	   	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
	   	    	 Node noeud2= graphs[graphaleatoire].get(randomnode2);
	   	    	 
	   	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	   	    	
	   	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	   	    	 ShortestPathSolution solution = null;
	   	    	 solution = spAlgorithm.run();
	   	    	 
	   	    	 //System.out.println(AlgorithmFactory.getAlgorithmNames(ShortestPathAlgorithm.class));
	   	    	 BellmanFordAlgorithm spAlgorithmBF = (BellmanFordAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, "Bellman-Ford"), data);
	   	    	 ShortestPathSolution solutionBF = null;
	  	    	 solutionBF = spAlgorithmBF.run();
	   	    	 
	   	    	 //System.out.println("PCC entre "+noeud1.getId()+" et "+noeud2.getId()+" avec le mode "+ArcInspectorFactory.getAllFilters().get(mode)+" et le status "+solution.getStatus());
	
	  	    	 //comparaison avec Bellman-Ford
	   	    	 Path pathsolu =  solution.getPath();
	   	    	 Path pathsoluBF =  solutionBF.getPath();
	   	    	 
	  	    	 assertEquals(solutionBF.getStatus(), solution.getStatus());  // on verifie d'abord si les solutions ont le même statut
	  	    	 
	  	    	 if (solutionBF.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	  	    		 
	  	    		cpt++;
	  	    		
	  	    		if (solutionBF.getInputData().getDestination()!=solutionBF.getInputData().getOrigin()) { // si origine = destination alors les solutions sont forcément les mêmes car l'origine est définie dans ShortestPathData
		  	    			 
	  	    			try {
	  	    				
	  	    				//on teste si les paths sont les mêmes. Un path est défini par ses différents arcs on regarde donc si la liste des arcs est la même
	  	    				 assertEquals(true, pathsolu.getArcs().equals(pathsoluBF.getArcs()));
	  	    				
	  	    			} catch(AssertionError e) {
	  	    				/*dans certains cas ce n'est pas pareil comme dans les cartes non routières (ex carte carrée) et il
	  	    				plusieurs chemins optimaux. Dans ces cas on vérifie que les chemins ont bien la même taille ou la même
	  	    				durée (avec le Bellman-Ford implémenté on peut pas récupérer le coût du chemin)
	  	    				*/
	  	    				
	  	    				if (mode < 3)    //quand c'est la longueur du chemin
	  	    					assertEquals(solutionBF.getPath().getLength(),solution.getPath().getLength(), 1);
	  	    				
	  	    				else {    //quand c'est le temps de trajet
	  	    					double speed = data.getMaximumSpeed();
	  	    					assertEquals(solutionBF.getPath().getTravelTime(speed),solution.getPath().getTravelTime(speed),0.0001);
	  	    				}
	  	    			}
	  	    			 
	  	    		 }
	  	    	 }
	  	    	 
      		 }
      	 }
    	
   	 
    }
    
    
    @Test
    public void TestSansOracle() throws Exception {
   	 
   	 int cpt=0;
   	 int mode=0;
   	 
 		 for (mode=0; mode < 4; mode+=3) { //ce test n'est applicable que sur les filtres all roads allowed
 			 cpt = 0; 
 			 while (cpt<50) { 
 				 
	   	    	 Random rand = new Random();
	   	    	 int graphaleatoire = rand.nextInt(3); 
	   	    	 
	   	    	 int nbnodes = graphs[graphaleatoire].size();
	   	
	   	    	 int randomnode1 = rand.nextInt(nbnodes);
	   	    	 int randomnode2 = rand.nextInt(nbnodes);
	   	    	 
	   	    	 Node noeud1= graphs[graphaleatoire].get(randomnode1);
	   	    	 Node noeud2= graphs[graphaleatoire].get(randomnode2);
	   	    	 
	   	    	 ShortestPathData data= new ShortestPathData(graphs[graphaleatoire], noeud1, noeud2, ArcInspectorFactory.getAllFilters().get(mode));
	   	    	 
	   	    	 DijkstraAlgorithm spAlgorithm = (DijkstraAlgorithm) AlgorithmFactory.createAlgorithm(AlgorithmFactory.getAlgorithmClass(ShortestPathAlgorithm.class, arg), data);
	   	    	 ShortestPathSolution solution = null;
	   	    	 solution = spAlgorithm.run();
	   	    	 
	   	    	Path pathsolu=null;
	   	    	
	  	    	 if (solution.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	  	    		cpt++;

	  	    		pathsolu =  solution.getPath();
	  	    		
	  	    		//on récupere les noeuds du chemin de la solution
	  	    		int nbnodesPath = pathsolu.size();
	  	    		ArrayList<Node> nodesSolu= new ArrayList<Node>();
	  	    		nodesSolu.add(pathsolu.getOrigin());
	  	    		int k;
	  	    		for (k=0; k<nbnodesPath-1;k++) {
	  	    			nodesSolu.add(pathsolu.getArcs().get(k).getDestination());
	  	    		}
	  	    		
	  	    		/* La condition de validité d'un plus court chemin est que en prenant deux noeuds du pcc total, le pcc 
	  	    		entre ces deux noeuds soit compris dans le pcc total
	  	    		
	  	    		Ici pour vérifier, on pourrait générer toutes les paires de noeuds (origine-destination) du chemin de la solution
	  	    		en respectant l'ordre des sommets et à l'aide des méthodes createFastest et create Shortest, générer tous les chemins 
	  	    		possibles dans le graphe entre ces deux noeuds et comparer leur coût avec celui la portion du chemin de la solution. S'ils lui sont tous inférieurs 
	  	    		ou égaux alors la solution est bien optimale. On pourrait améliorer cet algorithme avec la programmation dynamique et en délimitant la surface de recherche 
	  	    		de chemin comme nous sommes sur les cartes mais la complexité restrait assez grande.
	  	    		Néanmoins nous avons vérifié qu'entre deux noeuds successifs du chemin de la solution, l'arc est bien celui avec le coût le plus bas.
	  	    		Ce n'est pas un test validant la solution, mais dans le cas où il est faux, on est sûr que la solution n'est pas optimale.
	  	    		*/
	  	    		
	  	    		Path pathToCompare=null;
	  	    		
	  	    		double vit = data.getMaximumSpeed(); // si jamais il y a une vitesse max dans le ShortestPathData
	  	    		
	  	    		if (mode == 0) //si le coût est en distance
	  	    			pathToCompare = Path.createShortestPathFromNodes(graphs[graphaleatoire], nodesSolu);
	  	    		
	  	    		else //si le coût est en temps
	  	    			pathToCompare = Path.createFastestPathFromNodes2(graphs[graphaleatoire], nodesSolu, vit);
	  	    		
	  	    		assertEquals(true, (pathsolu.getArcs()).equals(pathToCompare.getArcs()));
	  	    		
	  	    		
	  	    		}
  	    	 }
	  	    	 
		 }
	}
	   	    	 
   	 	
    
}
