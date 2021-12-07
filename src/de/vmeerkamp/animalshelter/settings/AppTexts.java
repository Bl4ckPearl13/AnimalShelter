package de.vmeerkamp.animalshelter.settings;

/**
 * Diese Klasse stellt ausschliesslich Programmtexte zur Verfuegung.
 */

public class AppTexts {
	
	//region 0. Konstanten
	
	public static final String USER_MSG_EXPORT_SUCCESSFUL = "Export war erfolgreich";
	
	public static final String USER_WARNING_SAVE_FAILED                  =
			"Speichern fehlgeschlagen.";
	public static final String USER_WARNING_DATABASE_ACCESS_ERROR        =
			"Fehler beim Datenbankzugriff.";
	public static final String USER_WARNING_NO_ANIMAL_SELECTED_TO_DELETE =
			"FEHLER: Kein Tier zum Löschen ausgewählt.";
	public static final String USER_WARNING_EXPORT_FAILED                =
			"Export fehlgeschlagen";
	public static final String USER_WARNING_INPUT_MISSING                =
			"FEHLER: Bitte alle Pflichtfelder ausfüllen.";
	public static final String USER_WARNING_WRONG_DATE                   =
			"FEHLER: Bitte ein gültiges Datum im Format TT.MM.JJJJ eingeben.";
	
	public static final String WARNING_WRONG_INPUT_TYPE        =
			"Das übergebene Objekt entspricht nicht dem geforderten Typ.";
	public static final String WARNING_NO_DRIVER_CLASS         =
			"Keine Treiber-Klasse!";
	public static final String WARNING_NO_SUCH_METHOD          =
			"Für diesen Datentyp ist keine passende Methode vorhanden.";
	public static final String WARNING_NO_VALID_TABLE_SELECTED =
			"Es wurde keine gültige DB-Tabelle ausgewählt";
	//endregion
	
	//region 1. Konstruktor
	
	/**
	 * Privater Konstruktor um die Objektinstantiierung von ausserhalb
	 * der Klasse zu unterbinden.
	 * Diese Klasse stellt nur Konstanten zur Verfuegung.
	 */
	
	private AppTexts() {
	}
	//endregion
}
