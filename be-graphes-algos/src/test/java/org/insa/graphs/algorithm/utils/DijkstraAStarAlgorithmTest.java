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
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
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
	   	 
	   	 GraphReader readerMidiPyrenees = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapMidiPyrenees))));
	   	 Graph g3 = readerMidiPyrenees.read();
	   	 
	   	 GraphReader readerToulouse = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapToulouse))));
	   	 Graph g2 = readerToulouse.read();
	   	 
	   	 graphs[0]=g1;
	   	 graphs[1]=g2;
	   	 graphs[2]=g3;
   	 
    }
    
    
    @Test
    public void TestValid() throws Exception {
   	 
   	 int i=0;
   	 
   	 for (i=0;i<50;i++) {
   	 
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
	    	 
	         try {
		    	 Path pathsolution =solution.getPath();
		    	 assertEquals(true, pathsolution.isValid());
		    	 
	         } catch(NullPointerException e) { // cas ou le chemin est infaisable
	        	 assertEquals(true, true);
	         }
   	 }
		 
    }
    
    @Test
    public void TestLength() throws Exception {
   	 int i=0;
   	 
   	 for (i=0;i<50;i++) {
   	 
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
	      
	         try {
		    	 Path pathsolution =solution.getPath();
		    	 int indexDest = pathsolution.getDestination().getId();
		    	 Label dest = spAlgorithm.labels[indexDest];

		    	 double costLength = (double) Math.round(dest.getCost()*1000)/1000; // à la destination le cout estime est nul le cout total est donc egal à dest.cout
		    	 
		    	 // verifications
		    	 //System.out.println("origine : "+spAlgorithm.labels[data.getOrigin().getId()]);
		    	 //System.out.println("destination : "+dest);
		    	 
		    	 //System.out.println(pathsolution.getLength() +" "+ dest.getTotalCost());
		    	 //System.out.println("Avec arrondi  "+(double) Math.round(pathsolution.getLength()*1000)/1000 +" "+ costLength);
		    	 
		    	 assertEquals((float) Math.round(pathsolution.getLength()*1000)/1000, costLength,1);
		    	 
	         } catch(NullPointerException e) { // cas ou le chemin est infaisable
	        	 assertEquals(true, true);
	         }
   	 }
		 
    }
    
    
    
    @Test
    public void TestInfeasible() throws Exception {
    	
    	WeaklyConnectedComponentsData wcompdata = new WeaklyConnectedComponentsData(graphs[1]);
    	//System.out.println(AlgorithmFactory.getAlgorithmNames(WeaklyConnectedComponentsAlgorithm.class));
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
	    	 
	    	 
	    	 int mode=rand.nextInt(4);
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
      	 
      	 for (i=0;i<50;i++) {
      	 
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
	    	 assertEquals(Double.compare(0, costLength),0);
   		    
      	 }
   		 
       }
    
    @Test
    public void TestCompareBellmanFord() throws Exception {
    	 
    	 int cpt=0;
    	 int mode=0;
    	 
  		 for (mode=0; mode < 5; mode++) {
  			 while (cpt<50) {
  				 
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
	   	    	 
	   	    	 System.out.println("PCC entre "+noeud1.getId()+" et "+noeud2.getId()+" avec le mode "+ArcInspectorFactory.getAllFilters().get(mode)+" et le status "+solution.getStatus());
	
	  	    	 //comparaison avec Bellman-Ford
	   	    	 //on ne peut pas comparer les solutions directements il faut donc prendre certains éléments en compte
	   	    	 
	   	    	 Path pathsolu =  solution.getPath();
	   	    	 Path pathsoluBF =  solutionBF.getPath();
	   	    	 
	  	    	 assertEquals(solutionBF.getStatus(), solution.getStatus());  // on verifie d'abord si les solution ont le même statut
	  	    	 
	  	    	 if (solutionBF.getStatus() != ShortestPathSolution.Status.INFEASIBLE) {
	  	    		 
	  	    		 cpt++;
	  	    		
	  	    		 if (solutionBF.getInputData().getGraph() == graphs[0]) {
	  	    			 if (solutionBF.getInputData().getDestination()!=solutionBF.getInputData().getOrigin()) { // si origine = destination alors les solutions sont forcément les mêmes car l'origine est définie dans ShortestPathData
		  	    			 
	  	    				 assertEquals(0, solutionBF.getPath().getLength()-solution.getPath().getLength(), 0); // avec une carte non routière on peut avec différents pcc avec le même coût
		  	    			 assertEquals(0, solutionBF.getPath().getMinimumTravelTime()-solution.getPath().getMinimumTravelTime(),0); // on verifie aussi la duree par mesure de sécurité
	  	    			 
	  	    			 }
	  	    		 } else {
	  	    			 
	  	    			 if (solutionBF.getInputData().getDestination()!=solutionBF.getInputData().getOrigin()) { 
	  	    				
		  	    			 //si les arcs sont les mêmes alors le chemin est le même
		  	    			 //la méthode arrays.equals permet de comparer les deux tableaux index par index pour voir s'il ont le même contenu à la même place
		  	    			 // la méthode getArcs sur un Path nous retourne la liste des arcs du path ordonnée (cette liste n'est pas modififable c'est donc la même pour tous les chemins)
		  	    			 assertEquals(true, pathsolu.getArcs().equals(pathsoluBF.getArcs())); 
		  	    			 
	  	    			 }
	  	    		 }
	  	    	 }
	  	    	 
      		 }
      	 }
    	
   	 
    }
    
    public void TestOthers() {
   	 //verifier la solution sans BF
    }
   
    
    
    
}
