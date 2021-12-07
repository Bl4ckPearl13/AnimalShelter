package de.vmeerkamp.animalshelter.gui;

import de.vmeerkamp.animalshelter.logic.db.DbManager;
import de.vmeerkamp.animalshelter.logic.export.CsvFileHandler;
import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Cat;
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
 * Diese Klasse nimmt alle Events des cat_layout.fxml entgegen und leitet die weitere Logik ein.
 */

public class CatController extends MainController implements Initializable {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private Cat currentCat;
	
	/**
	 * Zeigt an ob die gewaehlte Katze ein Freigaenger ist oder nimmt den Freigaengerstatus fuer
	 * eine neue Katze entgegen.
	 */
	@FXML
	private CheckBox chkOutdoorCat;
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
		
		showCatList();
	}
	//endregion
	
	//region 3. CREATE/UPDATE
	
	/**
	 * Die Methode speichert eine neue Katze oder aendert die in der
	 * {@link javax.swing.text.html.ListView} gewaehlte Katze in der DB.
	 */
	
	@FXML
	private void save() {
		
		clearUserMessage();
		
		Cat catFromGui = getCatFromGui();
		
		if (currentCat == null) {
			try {
				DbManager.getInstance().insertObjectIntoDb(catFromGui);
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		} else {
			try {
				catFromGui.getIdentity().setId(currentCat.getIdentity().getId());
				DbManager.getInstance().updateObjectInDb(catFromGui);
				
				currentCat = null;
			} catch (SQLException e) {
				e.printStackTrace();
				showUserMessage(AppTexts.USER_WARNING_SAVE_FAILED, true);
			}
		}
		
		emptyInputFields();
		showCatList();
	}
	//endregion
	
	//region 4. Eingabefelder leeren
	
	/**
	 * Die Methode leert alle Eingabefelder, setzt currentCat auf null und
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
		chkOutdoorCat.setSelected(false);
		txtDescription.clear();
		
		currentCat = null;
		showCatList();
	}
	//endregion
	
	//region 5. DELETE
	
	/**
	 * Die Methode loescht die in der {@link javafx.scene.control.ListView} gewaehlte Katze aus der DB.
	 */
	
	@FXML
	private void delete() {
		
		clearUserMessage();
		
		if (currentCat != null) {
			
			try {
				DbManager.getInstance().deleteObjectInDb(DbManager.CMD_CAT, currentCat.getIdentity().getId());
				
				emptyInputFields();
				showCatList();
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
	 * Die Methode exportiert den Inhalt der DB-Tabelle cat in eine CSV-Datei.
	 */
	
	@FXML
	private void csvExport() {
		
		clearUserMessage();
		
		try {
			List<Cat> catList = DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_CAT);
			
			if (CsvFileHandler.getInstance().saveAnimalsToCsvFile(catList)) {
				
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
	 * Die Methode zeigt alle Daten der gewaehlten Katze in den entsprechenden Textfeldern, Choice-
	 * und Check-Boxen an und weist currentCat die ausgewaehlte Katze zu.
	 *
	 * @param observable ermoeglicht die Ueberwachung der {@link javafx.scene.control.ListView}.
	 * @param oldValue zuletzt ausgewaehlte Katze.
	 * @param newValue aktuell ausgewaehlte Katze.
	 */
	
	private void onCellSelection(ObservableValue<? extends AAnimal> observable, AAnimal oldValue, AAnimal newValue) {
		
		clearUserMessage();
		
		if ((newValue != null) && (!newValue.equals(oldValue))) {
			
			//IMPLIZITER CAST
			currentCat = (Cat) newValue;
			
			txtAnimalId.setText(String.valueOf(currentCat.getIdentity().getId()));
			txtName.setText(currentCat.getIdentity().getName());
			txtDateOfBirth.setText(currentCat.getIdentity().getDateOfBirth());
			cmbSex.setValue(currentCat.getIdentity().getSex().getSex());
			chkNeutered.setSelected(currentCat.getIdentity().getSex().isNeutered());
			txtBreed.setText(currentCat.getAppearance().getBreed());
			txtColour.setText(currentCat.getAppearance().getColour());
			txtAdmissionDate.setText(currentCat.getShelterStay().getAdmissionDate());
			txtAdoptionDate.setText(currentCat.getShelterStay().getAdoptionDate());
			chkOutdoorCat.setSelected(currentCat.isOutdoorCat());
			txtDescription.setText(currentCat.getShelterStay().getDescription());
		}
	}
	
	/**
	 * Die Methode zeigt alle in der DB-Tabelle cat enthaltenen Katzen in der
	 * {@link javafx.scene.control.ListView} an.
	 */
	
	private void showCatList() {
		
		try {
			List<Cat>           catListFromDb     = DbManager.getInstance().getAllObjectsFromDb(DbManager.CMD_CAT);
			ObservableList<Cat> observableCatList = FXCollections.observableList(catListFromDb);
			
			lvAnimal.getItems().clear();
			lvAnimal.getItems().setAll(observableCatList);
		} catch (SQLException e) {
			e.printStackTrace();
			showUserMessage(AppTexts.USER_WARNING_DATABASE_ACCESS_ERROR, true);
		}
	}
	
	/**
	 * Die Funktion erzeugt aus den Daten der GUI ein neues Katzen-Objekt und liefert
	 * dieses zurueck.
	 *
	 * @return catFromGui - aus den Eingabedaten der GUI angelegte Katze oder null.
	 */
	
	private Cat getCatFromGui() {
		
		Cat catFromGui = null;
		
		String  strName          = txtName.getText();
		String  strDateOfBirth   = txtDateOfBirth.getText();
		String  strSex           = cmbSex.getValue();
		boolean isNeutered       = chkNeutered.isSelected();
		String  strBreed         = txtBreed.getText();
		String  strColour        = txtColour.getText();
		String  strAdmissionDate = txtAdmissionDate.getText();
		String  strAdoptionDate  = txtAdoptionDate.getText();
		boolean isOutdoorCat     = chkOutdoorCat.isSelected();
		String  strDescription   = txtDescription.getText();
		
		if (checkDate(strDateOfBirth) && checkDate(strAdmissionDate) && checkDate(strAdoptionDate)) {
			
			if (!strName.isEmpty() && !strSex.isEmpty() && !strAdmissionDate.isEmpty()) {
				
				Sex         sex         = new Sex(strSex, isNeutered);
				Identity    identity    = new Identity(strName, strDateOfBirth, sex);
				Appearance  appearance  = new Appearance(strBreed, strColour);
				ShelterStay shelterStay = new ShelterStay(strAdmissionDate, strAdoptionDate, strDescription);
				
				catFromGui = new Cat(identity, appearance, shelterStay, isOutdoorCat);
				
			} else {
				showUserMessage(AppTexts.USER_WARNING_INPUT_MISSING, true);
			}
		} else {
			showUserMessage(AppTexts.USER_WARNING_WRONG_DATE, true);
		}
		
		return catFromGui;
	}
	//endregion
}
