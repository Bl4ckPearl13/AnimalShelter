package de.vmeerkamp.animalshelter.gui;

import de.vmeerkamp.animalshelter.logic.db.DbManager;
import de.vmeerkamp.animalshelter.logic.export.CsvFileHandler;
import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Dog;
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
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Diese Klasse nimmt alle Events des dog_layout.fxml entgegen und leitet die weitere Logik ein.
 */

public class DogController extends MainController implements Initializable {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private Dog currentDog;
	
	/**
	 * Zeigt an ob der gewaehlte Hund ein Listenhund ist oder nimmt den Listenhundstatus fuer
	 * einen neuen Hund entgegen.
	 */
	@FXML
	private CheckBox chkRegulatedDogBreed;
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
		
		showDogList();
	}
	//endregion
	
	//region 3. CREATE/UPDATE
	
	/**
	 * Die Methode speichert einen neuen Hund oder aendert den in der
	 * {@link javax.swing.text.html.ListView} gewaehlten Hund in der DB.
	 */
	
	@FXML
	private void save() {
		
		clearUserMessage();
		
		Dog dogFromGui = getDogFromGui();
		
		if (currentDog == null) {
			try {
				DbManager.getInstance().insertObjectIntoDb(dogFromGui);
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		} else {
			try {
				dogFromGui.getIdentity().setId(currentDog.getIdentity().getId());
				DbManager.getInstance().updateObjectInDb(dogFromGui);
				
				currentDog = null;
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		}
		
		emptyInputFields();
		showDogList();
	}
	//endregion
	
	//region 4. Eingabefelder leeren
	
	/**
	 * Die Methode leert alle Eingabefelder, setzt currentDog auf null und
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
		chkRegulatedDogBreed.setSelected(false);
		txtDescription.clear();
		
		currentDog = null;
		showDogList();
	}
	//endregion
	
	//region 5. DELETE
	
	/**
	 * Die Methode loescht den in der {@link javafx.scene.control.ListView} gewaehlten Hund aus der DB.
	 */
	
	@FXML
	private void delete() {
		
		clearUserMessage();
		
		if (currentDog != null) {
			
			try {
				DbManager.getInstance().deleteObjectInDb(DbManager.CMD_DOG, currentDog.getIdentity().getId());
				
				emptyInputFields();
				showDogList();
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
	 * Die Methode exportiert den Inhalt der DB-Tabelle dog in eine CSV-Datei.
	 */
	
	@FXML
	private void csvExport() {
		
		clearUserMessage();
		
		try {
			List<Dog> dogList = DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_DOG);
			
			if (CsvFileHandler.getInstance().saveAnimalsToCsvFile(dogList)) {
				
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
	 * Die Methode zeigt alle Daten des gewaehlten Hundes in den entsprechenden Textfeldern, Choice-
	 * und Check-Boxen an und weist currentDog den ausgewaehlten Hund zu.
	 *
	 * @param observable ermoeglicht die Ueberwachung der {@link javafx.scene.control.ListView}.
	 * @param oldValue zuletzt ausgewaehlter Hund.
	 * @param newValue aktuell ausgewaehlter Hund.
	 */
	
	private void onCellSelection(ObservableValue<? extends AAnimal> observable, AAnimal oldValue, AAnimal newValue) {
		
		clearUserMessage();
		
		if ((newValue != null) && (!newValue.equals(oldValue))) {
			
			//IMPLIZITER CAST
			currentDog = (Dog) newValue;
			
			txtAnimalId.setText(String.valueOf(currentDog.getIdentity().getId()));
			txtName.setText(currentDog.getIdentity().getName());
			txtDateOfBirth.setText(currentDog.getIdentity().getDateOfBirth());
			cmbSex.setValue(currentDog.getIdentity().getSex().getSex());
			chkNeutered.setSelected(currentDog.getIdentity().getSex().isNeutered());
			txtBreed.setText(currentDog.getAppearance().getBreed());
			txtColour.setText(currentDog.getAppearance().getColour());
			txtAdmissionDate.setText(currentDog.getShelterStay().getAdmissionDate());
			txtAdoptionDate.setText(currentDog.getShelterStay().getAdoptionDate());
			chkRegulatedDogBreed.setSelected(currentDog.isRegulatedDogBreed());
			txtDescription.setText(currentDog.getShelterStay().getDescription());
		}
	}
	
	/**
	 * Die Methode zeigt alle in der DB-Tabelle dog enthaltenen Hunde in der
	 * {@link javafx.scene.control.ListView} an.
	 */
	
	private void showDogList() {
		
		try {
			List<Dog>           dogListFromDb     = DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_DOG);
			ObservableList<Dog> observableDogList = FXCollections.observableList(dogListFromDb);
			
			lvAnimal.getItems().clear();
			lvAnimal.getItems().setAll(observableDogList);
		} catch (SQLException e) {
			e.printStackTrace();
			showUserMessage(AppTexts.USER_WARNING_DATABASE_ACCESS_ERROR, true);
		}
	}
	
	/**
	 * Die Funktion erzeugt aus den Daten der GUI ein neues Hunde-Objekt und liefert
	 * dieses zurueck.
	 *
	 * @return dogFromGui - aus den Eingabedaten der GUI angelegter Hund oder null.
	 */
	
	private Dog getDogFromGui() {
		
		Dog dogFromGui = null;
		
		String  strName             = txtName.getText();
		String  strDateOfBirth      = txtDateOfBirth.getText();
		String  strSex              = cmbSex.getValue();
		boolean isNeutered          = chkNeutered.isSelected();
		String  strBreed            = txtBreed.getText();
		String  strColour           = txtColour.getText();
		String  strAdmissionDate    = txtAdmissionDate.getText();
		String  strAdoptionDate     = txtAdoptionDate.getText();
		boolean isRegulatedDogBreed = chkRegulatedDogBreed.isSelected();
		String  strDescription      = txtDescription.getText();
		
		if (checkDate(strDateOfBirth) && checkDate(strAdmissionDate) && checkDate(strAdoptionDate)) {
			
			if (!strName.isEmpty() && !strSex.isEmpty() && !strAdmissionDate.isEmpty()) {
				
				Sex         sex         = new Sex(strSex, isNeutered);
				Identity    identity    = new Identity(strName, strDateOfBirth, sex);
				Appearance  appearance  = new Appearance(strBreed, strColour);
				ShelterStay shelterStay = new ShelterStay(strAdmissionDate, strAdoptionDate, strDescription);
				
				dogFromGui = new Dog(identity, appearance, shelterStay, isRegulatedDogBreed);
				
			} else {
				showUserMessage(AppTexts.USER_WARNING_INPUT_MISSING, true);
			}
		} else {
			showUserMessage(AppTexts.USER_WARNING_WRONG_DATE, true);
		}
		
		return dogFromGui;
	}
	//endregion
}
