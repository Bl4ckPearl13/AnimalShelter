package de.vmeerkamp.animalshelter.gui;

import de.vmeerkamp.animalshelter.main.Main;
import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Cat;
import de.vmeerkamp.animalshelter.model.Dog;
import de.vmeerkamp.animalshelter.model.Rabbit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Diese Klasse stellt die allen Tieren gemeinsamen FXML-Attribute und Methoden
 * zur Verfuegung und ist fuer den Aufruf der Scenes der Tiere zustaendig.
 */

public class MainController {
	
	//region 0. Konstanten
	
	protected static final String SEX_INDIFFERENT = "unbestimmt";
	protected static final String SEX_MALE        = "männlich";
	protected static final String SEX_FEMALE      = "weiblich";
	
	private static final String CAT_LAYOUT    = "/layout/cat_layout.fxml";
	private static final String DOG_LAYOUT    = "/layout/dog_layout.fxml";
	private static final String RABBIT_LAYOUT = "/layout/rabbit_layout.fxml";
	//endregion
	
	//region 1.0 Switch Scenes Variablen
	
	private Stage  stage;
	private Parent parent;
	//endregion
	
	//region 1.1 FXML Variablen
	/**
	 * Zeigt alle Tiere in der entsprechenden Tabelle der Datenbank an und speichert das
	 * ausgewaehlte Element in der Variablen, die das aktuelle Element der Klasse haelt.
	 */
	@FXML
	protected ListView<AAnimal> lvAnimal;
	
	/**
	 * Zeigt die ID des gewaehlten Tieres an.
	 */
	@FXML
	protected TextField txtAnimalId;
	
	/**
	 * Zeigt den Namen des gewaehlten Tieres an oder nimmt einen neuen Namen entgegen.
	 */
	@FXML
	protected TextField txtName;
	
	/**
	 * Zeigt das Geburtsdatum des gewaehlten Tieres an oder nimmt ein neues Geburtsdatum entgegen.
	 */
	@FXML
	protected TextField txtDateOfBirth;
	
	/**
	 * Zeigt das Geschlecht des gewaehlten Tieres an oder bietet eine Geschlechts-Auswahl
	 * fuer ein neues Tier.
	 */
	@FXML
	protected ComboBox<String> cmbSex;
	
	/**
	 * Zeigt an ob das gewaehlte Tier kastriert ist oder nimmt den Kastrationsstatus fuer
	 * ein neues Tier entgegen.
	 */
	@FXML
	protected CheckBox chkNeutered;
	
	/**
	 * Zeigt die Rasse des gewaehlten Tieres an oder nimmt eine neue Rasse entgegen.
	 */
	@FXML
	protected TextField txtBreed;
	
	/**
	 * Zeigt die Farbe des gewaehlten Tieres an oder nimmt eine neue Farbe entgegen.
	 */
	@FXML
	protected TextField txtColour;
	
	/**
	 * Zeigt das Aufnahmedatum des gewaehlten Tieres im Tierheim an oder nimmt ein neues
	 * Aufnahmedatum entgegen.
	 */
	@FXML
	protected TextField txtAdmissionDate;
	
	/**
	 * Zeigt das Adoptionsdatum des gewaehlten Tieres an oder nimmt ein neues
	 * Adoptionsdatum entgegen.
	 */
	@FXML
	protected TextField txtAdoptionDate;
	
	/**
	 * Zeigt die Beschreibung des gewaehlten Tieres an oder nimmt eine neue Beschreibung entgegen.
	 */
	@FXML
	protected TextArea txtDescription;
	
	/**
	 * Zeigt Nachrichten fuer den Nutzer an.
	 */
	@FXML
	protected Label lblUserMessage;
	//endregion
	
	//region 2. Switch Scenes
	
	/**
	 * Die Methode zeigt die zur Klasse {@link Cat} gehoerige {@link Scene} auf der
	 * main{@link Stage} an.
	 *
	 * @throws IOException
	 */
	
	public void switchToCatScene() throws IOException {
		
		parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(CAT_LAYOUT)));
		stage  = Main.mainStage;
		stage.setScene(new Scene(parent));
		stage.show();
	}
	
	/**
	 * Die Methode zeigt die zur Klasse {@link Dog} gehoerige {@link Scene} auf der
	 * main{@link Stage} an.
	 *
	 * @throws IOException
	 */
	
	public void switchToDogScene() throws IOException {
		
		parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(DOG_LAYOUT)));
		stage  = Main.mainStage;
		stage.setScene(new Scene(parent));
		stage.show();
	}
	
	/**
	 * Die Methode zeigt die zur Klasse {@link Rabbit} gehoerige {@link Scene} auf der
	 * main{@link Stage} an.
	 *
	 * @throws IOException
	 */
	
	public void switchToRabbitScene() throws IOException {
		
		parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(RABBIT_LAYOUT)));
		stage  = Main.mainStage;
		stage.setScene(new Scene(parent));
		stage.show();
	}
	//endregion
	
	//region 3. Hilfsmethoden
	
	/**
	 * Die Methode initialisiert die Geschlecht-Combo-Box fuer alle Tiere.
	 */
	
	protected void initializeComboBox() {
		cmbSex.setCellFactory(c -> new ComboBoxCell());
		cmbSex.getItems().add(SEX_INDIFFERENT);
		cmbSex.getItems().add(SEX_MALE);
		cmbSex.getItems().add(SEX_FEMALE);
	}
	
	/**
	 * Die Methode gibt den uebergebenen {@link String} auf dem lblUserMessage aus.
	 * Die Anzeige erfolgt laut dem uebergebenen boolean entweder als Error-Message
	 * (roter Text) oder als User-Message (schwarzer Text).
	 *
	 * @param strUserMessage Nachricht, die ausgegeben werden soll.
	 * @param errorMessage   true, wenn es eine Error-Message ist, ansonsten false.
	 */
	
	protected void showUserMessage(String strUserMessage, boolean errorMessage) {
		
		if (errorMessage) {
			lblUserMessage.setTextFill(Paint.valueOf("red"));
		} else {
			lblUserMessage.setTextFill(Paint.valueOf("black"));
		}
		
		lblUserMessage.setText(strUserMessage);
	}
	
	/**
	 * Die Methode leert das lblUserMessage.
	 */
	
	protected void clearUserMessage() {
		
		lblUserMessage.setText("");
	}
	
	/**
	 * Die Funktion prueft das Datumsformat des uebergebenen {@link String} auf Korrektheit.
	 *
	 * @param strDateAsString Datum als String.
	 *
	 * @return isDateCorrect - true, wenn das Datum der Vorgabe entspricht oder leer ist.
	 */
	
	protected boolean checkDate(String strDateAsString) {
		
		if (strDateAsString == null || strDateAsString.isEmpty()) {
			return true;
		}
		return strDateAsString.matches("[0-3]\\d\\.[0-1]\\d\\.[1-2]\\d\\d\\d");
	}
	//endregion
}
