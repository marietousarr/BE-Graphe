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

import org.insa.graphs.algorithm.AlgorithmFactory;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.utils.PriorityQueueTest.MutableInteger;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;

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
    	
	   	 graphs = new Graph[2];
	   	 
	   	 String mapDirectory = "C:\\Users\\Mariétou SARR\\Documents\\INSA Toulouse Cours\\Graphes\\BE Graphe\\Maps\\";
	   	 String mapCarre = mapDirectory + "carre.mapgr";
	   	 String mapMidiPyrenees = mapDirectory + "midi-pyrenees.mapgr";
	 
	   	 
	   	 GraphReader readerCarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
	   	 Graph g1 = readerCarre.read();
	   	 
	   	 GraphReader readerMidiPyrenees = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapMidiPyrenees))));
	   	 Graph g2 = readerMidiPyrenees.read();
	   	 
	   	 graphs[0]=g1;
	   	 graphs[1]=g2;
   	 
    }
    
    
    @Test
    public void TestValid() throws Exception {
   	 
   	 int i=0;
   	 
   	 for (i=0;i<50;i++) {
   	 
	    	 Random rand = new Random();
	    	 int graphaleatoire = rand.nextInt(2);
	    	 
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
	         } catch(NullPointerException e) {
	        	 assertEquals(true, true);
	         }
   	 }
		 
    }
    
    @Test
    public void TestLength() throws Exception {
   	 int i=0;
   	 
   	 for (i=0;i<50;i++) {
   	 
	    	 Random rand = new Random();
	    	 int graphaleatoire = rand.nextInt(2);
	    	 
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
		    	 
	         } catch(NullPointerException e) {
	        	 assertEquals(true, true);
	         }
   	 }
		 
    }
    
    
    
    
    public void TestInfeasible() {
   	 //cas ou pas la meme composante connexe
    }
    
    
    public void TestNullCost() {
   	 // origine = destination
    }
    
    public void TestCostTime() {
   	 
    }
    
    public void TestCostLength() {
   	 
    }
    
    public void TestMode() {
   	 
    }
    
    public void TestCompareBellmanFord() {
   	 
    }
    
    public void TestOthers() {
   	 //verifier la solution sans BF
    }
   
    
    
    
}
