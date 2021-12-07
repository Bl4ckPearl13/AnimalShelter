package de.vmeerkamp.animalshelter.gui;

import de.vmeerkamp.animalshelter.logic.db.DbManager;
import de.vmeerkamp.animalshelter.logic.export.CsvFileHandler;
import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Rabbit;
import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.Sex;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.AppTexts;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Diese Klasse nimmt alle Events des rabbit_layout.fxml entgegen und leitet die weitere Logik ein.
 */

public class RabbitController extends MainController implements Initializable {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private Rabbit currentRabbit;
	//endregion
	
	//region 2. Initialisieren
	
	/**
	 * Die Methode wird aufgerufen, nachdem das Root-Element vollstaendig verarbeitet wurde.
	 * Sie initialisiert die {@link javax.swing.text.html.ListView} und die {@link javafx.scene.control.ChoiceBox}.
	 *
	 * @param location  Der Ort, der verwendet wird, um relative Pfade für das
	 *                  Stammobjekt aufzulösen, oder null, wenn der Ort nicht bekannt ist.
	 * @param resources Die Ressourcen, die zur Lokalisierung des Stammobjekts
	 *                  verwendet werden, oder null, wenn die Ressourcen nicht bekannt sind.
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Listview Zellen formatieren und Listener setzen
		lvAnimal.setCellFactory(c -> new ListViewCell());
		lvAnimal.getSelectionModel().selectedItemProperty().addListener(this::onCellSelection);
		
		initializeComboBox();
		
		showRabbitList();
	}
	//endregion
	
	//region 3. CREATE/UPDATE
	
	/**
	 * Die Methode speichert ein neues Kaninchen oder aendert das in der
	 * {@link javax.swing.text.html.ListView} gewaehlte Kaninchen in der DB.
	 */
	
	@FXML
	private void save() {
		
		clearUserMessage();
		
		Rabbit rabbitFromGui = getRabbitFromGui();
		
		if (currentRabbit == null) {
			try {
				DbManager.getInstance().insertObjectIntoDb(rabbitFromGui);
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		} else {
			try {
				rabbitFromGui.getIdentity().setId(currentRabbit.getIdentity().getId());
				DbManager.getInstance().updateObjectInDb(rabbitFromGui);
				
				currentRabbit = null;
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		}
		
		emptyInputFields();
		showRabbitList();
	}
	//endregion
	
	//region 4. Eingabefelder leeren
	
	/**
	 * Die Methode leert alle Eingabefelder, setzt currentRabbit auf null und
	 * entfernt die Auswahl in der {@link javafx.scene.control.ListView}.
	 */
	
	@FXML
	private void emptyInputFields() {
		
		txtAnimalId.clear();
		txtName.clear();
		txtDateOfBirth.clear();
		cmbSex.setValue("");
		chkNeutered.setSelected(false);
		txtBreed.clear();
		txtColour.clear();
		txtAdmissionDate.clear();
		txtAdoptionDate.clear();
		txtDescription.clear();
		
		currentRabbit = null;
		showRabbitList();
	}
	//endregion
	
	//region 5. DELETE
	
	/**
	 * Die Methode loescht das in der {@link javafx.scene.control.ListView} gewaehlte Kaninchen aus der DB.
	 */
	
	@FXML
	private void delete() {
		
		clearUserMessage();
		
		if (currentRabbit != null) {
			
			try {
				DbManager.getInstance().deleteObjectInDb(DbManager.CMD_RABBIT, currentRabbit.getIdentity().getId());
				
				emptyInputFields();
				showRabbitList();
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_DATABASE_ACCESS_ERROR, true);
			}
		} else {
			showUserMessage(AppTexts.USER_WARNING_NO_ANIMAL_SELECTED_TO_DELETE, true);
		}
	}
	//endregion
	
	//region 6. CSV-Export
	
	/**
	 * Die Methode exportiert den Inhalt der DB-Tabelle rabbit in eine CSV-Datei.
	 */
	
	@FXML
	private void csvExport() {
		
		clearUserMessage();
		
		try {
			List<Rabbit> rabbitList = DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_RABBIT);
			
			if (CsvFileHandler.getInstance().saveAnimalsToCsvFile(rabbitList)) {
				
				showUserMessage(AppTexts.USER_MSG_EXPORT_SUCCESSFUL, false);
			} else {
				showUserMessage(AppTexts.USER_WARNING_EXPORT_FAILED, true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			showUserMessage(AppTexts.USER_WARNING_DATABASE_ACCESS_ERROR, true);
		}
		
	}
	//endregion
	
	//region 7. Hilfsmethoden
	
	/**
	 * Die Methode zeigt alle Daten des gewaehlten Kaninchens in den entsprechenden Textfeldern, Choice-
	 * und Check-Boxen an und weist currentRabbit das ausgewaehlte Kaninchen zu.
	 *
	 * @param observable ermoeglicht die Ueberwachung der {@link javafx.scene.control.ListView}.
	 * @param oldValue   zuletzt ausgewaehltes Kaninchen.
	 * @param newValue   aktuell ausgewaehltes Kaninchen.
	 */
	
	private void onCellSelection(ObservableValue<? extends AAnimal> observable, AAnimal oldValue, AAnimal newValue) {
		
		clearUserMessage();
		
		if ((newValue != null) && (!newValue.equals(oldValue))) {
			
			//IMPLIZITER CAST
			currentRabbit = (Rabbit) newValue;
			
			txtAnimalId.setText(String.valueOf(currentRabbit.getIdentity().getId()));
			txtName.setText(currentRabbit.getIdentity().getName());
			txtDateOfBirth.setText(currentRabbit.getIdentity().getDateOfBirth());
			cmbSex.setValue(currentRabbit.getIdentity().getSex().getSex());
			chkNeutered.setSelected(currentRabbit.getIdentity().getSex().isNeutered());
			txtBreed.setText(currentRabbit.getAppearance().getBreed());
			txtColour.setText(currentRabbit.getAppearance().getColour());
			txtAdmissionDate.setText(currentRabbit.getShelterStay().getAdmissionDate());
			txtAdoptionDate.setText(currentRabbit.getShelterStay().getAdoptionDate());
			txtDescription.setText(currentRabbit.getShelterStay().getDescription());
		}
	}
	
	/**
	 * Die Methode zeigt alle in der DB-Tabelle rabbit enthaltenen Kaninchen in der
	 * {@link javafx.scene.control.ListView} an.
	 */
	
	private void showRabbitList() {
		
		try {
			List<Rabbit>           rabbitListFromDb  =
					DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_RABBIT);
			ObservableList<Rabbit> observableRabbitList = FXCollections.observableList(rabbitListFromDb);
			
			lvAnimal.getItems().clear();
			lvAnimal.getItems().setAll(observableRabbitList);
		} catch (SQLException e) {
			e.printStackTrace();
			showUserMessage(AppTexts.USER_WARNING_DATABASE_ACCESS_ERROR, true);
		}
	}
	
	/**
	 * Die Funktion erzeugt aus den Daten der GUI ein neues Kaninchen-Objekt und liefert
	 * dieses zurueck.
	 *
	 * @return rabbitFromGui - aus den Eingabedaten der GUI angelegtes Kaninchen oder null.
	 */
	
	private Rabbit getRabbitFromGui() {
		
		Rabbit rabbitFromGui = null;
		
		String  strName          = txtName.getText();
		String  strDateOfBirth   = txtDateOfBirth.getText();
		String  strSex           = cmbSex.getValue();
		boolean isNeutered       = chkNeutered.isSelected();
		String  strBreed         = txtBreed.getText();
		String  strColour        = txtColour.getText();
		String  strAdmissionDate = txtAdmissionDate.getText();
		String  strAdoptionDate  = txtAdoptionDate.getText();
		String  strDescription   = txtDescription.getText();
		
		if (checkDate(strDateOfBirth) && checkDate(strAdmissionDate) && checkDate(strAdoptionDate)) {
			
			if (!strName.isEmpty() && !strSex.isEmpty() && !strAdmissionDate.isEmpty()) {
				
				Sex         sex         = new Sex(strSex, isNeutered);
				Identity    identity    = new Identity(strName, strDateOfBirth, sex);
				Appearance  appearance  = new Appearance(strBreed, strColour);
				ShelterStay shelterStay = new ShelterStay(strAdmissionDate, strAdoptionDate, strDescription);
				
				rabbitFromGui = new Rabbit(identity, appearance, shelterStay);
				
			} else {
				showUserMessage(AppTexts.USER_WARNING_INPUT_MISSING, true);
			}
		} else {
			showUserMessage(AppTexts.USER_WARNING_WRONG_DATE, true);
		}
		
		return rabbitFromGui;
	}
	//endregion
}
