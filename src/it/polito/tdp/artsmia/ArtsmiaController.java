/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxLUN"
	private ChoiceBox<Integer> boxLUN; // Value injected by FXMLLoader

	@FXML // fx:id="btnCalcolaComponenteConnessa"
	private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

	@FXML // fx:id="btnCercaOggetti"
	private Button btnCercaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalizzaOggetti"
	private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

	@FXML // fx:id="txtObjectId"
	private TextField txtObjectId; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

	@FXML
	void doAnalizzaOggetti(ActionEvent event) {
		
		    txtObjectId.setDisable(false);
		    btnCalcolaComponenteConnessa.setDisable(false);
		    btnCercaOggetti.setDisable(false);
		    boxLUN.setDisable(false);
		    
		    model.creaGrafo();
		    
		    txtResult.setText("Grafo creato : "+model.getGraphSize()+" vertici, "+ model.getNumberEdges()+" archi.");
		    
		   // btnAnalizzaOggetti.setDisable(true);
	}

	@FXML
	void doCalcolaComponenteConnessa(ActionEvent event) {
		
		txtResult.clear();
		
		String input = txtObjectId.getText();
		
		if(!input.equals("")) {
			if(input.matches("[0-9]+")) {
				
				int sizeComponente = model.getComponenteConnessa(Integer.parseInt(input));
				
				if(sizeComponente<0) {
					txtResult.setText("Errore! l'oggetto cercato non esiste..");
					return ;
				}
				else {
					
					if(sizeComponente ==1) {
						txtResult.appendText("L'oggetto selezionato è isolato... si prega di selezionare un'altro oggetto");
						return;
					}
						
					
			    	txtResult.setText("La componente connessa, avente vertice: "+input+" ha dimensione: "+sizeComponente);
			    	
			    	caricaBox(sizeComponente);
				}
			}
			else {
				txtResult.setText("L'ID è composto dai soli caratteri alfanumerici");
				return ;
	              	}
			}
		else {
			txtResult.setText("Si prega di inserire un ID di un oggetto, grazie");
			return ;
		}
			
		//txtObjectId.setEditable(false);
		
	}

	private void caricaBox(int sizeComponente) {
	
		List<Integer> result = new ArrayList<>();
		for(Integer i = 2; i<=sizeComponente;i++)
			result.add(i);
		
		boxLUN.setItems(FXCollections.observableList(result));
	
	}
	

	@FXML
	void doCercaOggetti(ActionEvent event) {
		txtResult.setText("doCercaOggetti");
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";
		
	    txtObjectId.setDisable(true);
	    btnCalcolaComponenteConnessa.setDisable(true);
	    btnCercaOggetti.setDisable(true);
		

	}

	public void setModel(Model model) {
		this.model = model;
		
	}
}
