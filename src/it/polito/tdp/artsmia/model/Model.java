package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {

	private SimpleWeightedGraph<ArtObject, DefaultWeightedEdge> graph ;
	private Map<Integer, ArtObject> idArtMap;
	private ArtsmiaDAO dao;

	public Model() {
		
		//puo essere utile una idMap
		this.idArtMap = new HashMap<Integer, ArtObject>();
		this.dao = new ArtsmiaDAO();
	}
	
	public void creaGrafo() {
		
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class) ;
		
		//aggiungo i vertci attraverso il dao
		dao.loadAllObjects(idArtMap, graph);
		
		//aggiungo gli archi attraverso il dao
		dao.loadEdges(idArtMap,graph);
		
	}
	
	public int getGraphSize() {
		return graph.vertexSet().size();
	}
	
	public int getNumberEdges() {
		return graph.edgeSet().size();
	}

	public int getComponenteConnessa(int idObject) {
		
		if (graph != null && idArtMap.containsKey(idObject)) {
			
	
		ConnectivityInspector<ArtObject, DefaultWeightedEdge> ci = 
				new ConnectivityInspector<ArtObject, DefaultWeightedEdge>(graph);
	       
	        int  ris = ci.connectedSetOf(idArtMap.get(idObject)).size();
	        
	        System.out.println(ris);
	        System.out.println(ci.isGraphConnected());
     	        return ris;
	        
	        
	}
		
		return -1;
	}
	
	
	
	
}
