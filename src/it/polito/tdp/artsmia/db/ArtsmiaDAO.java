package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadAllObjects(Map<Integer, ArtObject> idArtMap, Graph<ArtObject, DefaultWeightedEdge> graph) {
		
		String sql = "SELECT * from objects";
	
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!idArtMap.containsKey(res.getInt("object_id"))) {
				
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				idArtMap.put(res.getInt("object_id"), artObj);
				graph.addVertex(artObj);
				
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}

	public void loadEdges(Map<Integer, ArtObject> idArtMap, Graph<ArtObject, DefaultWeightedEdge> graph) {
	
		String sql = "SELECT oe1.object_id, oe2.object_id, COUNT(*) AS peso " + 
				"FROM exhibition_objects oe1,  exhibition_objects oe2 " + 
				"WHERE oe1.exhibition_id = oe2.exhibition_id AND oe2.object_id > oe1.object_id " + 
				"GROUP BY oe1.object_id, oe2.object_id";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(idArtMap.containsKey(res.getInt("oe1.object_id")) && idArtMap.containsKey(res.getInt("oe2.object_id")) ) {
				
					Graphs.addEdge(graph,idArtMap.get(res.getInt("oe1.object_id")), idArtMap.get(res.getInt("oe2.object_id")), res.getDouble("peso"));
				
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	
}
