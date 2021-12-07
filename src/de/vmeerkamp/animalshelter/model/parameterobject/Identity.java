package de.vmeerkamp.animalshelter.model.parameterobject;

import de.vmeerkamp.animalshelter.settings.DefaultValues;
import javafx.scene.control.TextField;

/**
 * Diese Klasse ist ein Model und beinhaltet alle Daten zur Identitaet
 * eines Tieres im Tierheim.
 */

public class Identity {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	private int    iId;
	private String strName;
	private String strDateOfBirth;
	private Sex    sex;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Identity() {
		this.iId            = DefaultValues.DEF_VALUE_INTEGER;
		this.strName        = DefaultValues.DEF_VALUE_STRING;
		this.strDateOfBirth = DefaultValues.DEF_VALUE_STRING;
		this.sex            = new Sex();
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient der Aufnahme einer bestehenden
	 * Identitaet aus der DB fuer ein Tier im Tierheim.
	 *
	 * @param iId ID-Nummer des Tieres, wird in der DB automatisch gesetzt
	 * @param strName Name des Tieres
	 * @param strDateOfBirth Geburtsdatum des Tieres
	 * @param sex Geschlecht des Tieres und Informationen zur Kastration
	 */
	
	public Identity(int iId, String strName, String strDateOfBirth, Sex sex) {
		this.iId            = iId;
		this.strName        = strName;
		this.strDateOfBirth = strDateOfBirth;
		this.sex            = sex;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen einer neuen
	 * Identitaet fuer ein Tier im Tierheim, das in die DB eingefuegt werden soll.
	 *
	 * @param strName Name des Tieres
	 * @param strDateOfBirth Geburtsdatum des Tieres
	 * @param sex Geschlecht des Tieres und Informationen zur Kastration
	 */
	
	public Identity(String strName, String strDateOfBirth, Sex sex) {
		this.strName        = strName;
		this.strDateOfBirth = strDateOfBirth;
		this.sex            = sex;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public int getId() {
		return iId;
	}
	
	public void setId(int iId) {
		this.iId = iId;
	}
	
	public String getName() {
		return strName;
	}
	
	public void setName(String strName) {
		this.strName = strName;
	}
	
	public String getDateOfBirth() {
		return strDateOfBirth;
	}
	
	public void setDateOfBirth(String strDateOfBirth) {
		this.strDateOfBirth = strDateOfBirth;
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	//endregion
	
	//region 4. toString
	
	/**
	 * Die Funktion gibt alle Attribute der Identitaet eines Tieres als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute zur Identitaet des Tieres als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Identity{" +
				"iId=" + iId +
				", strName='" + strName + '\'' +
				", strDateOfBirth='" + strDateOfBirth + '\'' +
				", sex=" + sex +
				'}';
	}
	//endregion
}
