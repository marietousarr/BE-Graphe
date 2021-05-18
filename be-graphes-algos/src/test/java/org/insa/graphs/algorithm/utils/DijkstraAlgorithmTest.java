package org.insa.graphs.algorithm.utils;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

public class DijkstraAlgorithmTest {
	
	 // Small graph use for tests
     private static Graph[] graphs;
     
     public static void initAll() throws IOException {
    	 
    	 graphs = new Graph[2];
    	 
    	 String mapDirectory = "C:\\Users\\Mariétou SARR\\Documents\\INSA Toulouse Cours\\BE Graphe\\Maps\\";
    	 String mapCarre = mapDirectory + "carre-dense.mapgr";
    	 String mapMidiPyrenees = mapDirectory + "midi-pyrenees.mapgr";
  
    	 
    	 GraphReader readerCarre = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapCarre))));
    	 Graph g1 = readerCarre.read();
    	 
    	 GraphReader readerMidiPyrenees = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapMidiPyrenees))));
    	 Graph g2 = readerMidiPyrenees.read();
    	 
    	 graphs[0]=g1;
    	 graphs[1]=g2;
    	 
     }
     
     public void TestValide() {
    	 
     }
     
     
     public void TestInfeasible() {
    	 
     }
     
     public void TestNullCost() {
    	 
     }
     
     public void TestCompareBellmanFord() {
    	 
     }
     
     public void TestOthers() {
    	 
     }
    
     

}
