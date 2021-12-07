package de.vmeerkamp.animalshelter.model.parameterobject;

import de.vmeerkamp.animalshelter.settings.DefaultValues;

/**
 * Diese Klasse ist ein Model und beinhaltet alle Daten, die den
 * Aufenthalt eines Tieres im Tierheim betreffen.
 */

public class ShelterStay {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private String strAdmissionDate;
	private String strAdoptionDate;
	private String strDescription;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public ShelterStay() {
		this.strAdmissionDate = DefaultValues.DEF_VALUE_STRING;
		this.strAdoptionDate  = DefaultValues.DEF_VALUE_STRING;
		this.strDescription   = DefaultValues.DEF_VALUE_STRING;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen eines neuen
	 * Aufenthaltes im Tierheim.
	 *
	 * @param strAdmissionDate Aufnahmedatum im Tierheim
	 * @param strAdoptionDate Datum der Adoption
	 * @param strDescription Beschreibung des Tieres fuer Interessenten
	 */
	
	public ShelterStay(String strAdmissionDate, String strAdoptionDate, String strDescription) {
		this.strAdmissionDate = strAdmissionDate;
		this.strAdoptionDate  = strAdoptionDate;
		this.strDescription   = strDescription;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public String getAdmissionDate() {
		return strAdmissionDate;
	}
	
	public void setAdmissionDate(String strAdmissionDate) {
		this.strAdmissionDate = strAdmissionDate;
	}
	
	public String getAdoptionDate() {
		return strAdoptionDate;
	}
	
	public void setAdoptionDate(String strAdoptionDate) {
		this.strAdoptionDate = strAdoptionDate;
	}
	
	public String getDescription() {
		return strDescription;
	}
	
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	//endregion
	
	//region 4. toString
	
	/**
	 * Die Funktion gibt alle Attribute des Tierheimaufenthaltes als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute des Tierheimaufenthaltes als {@link String}
	 */
	
	@Override
	public String toString() {
		return "ShelterStay{" +
				"strAdmissionDate='" + strAdmissionDate + '\'' +
				", strAdoptionDate='" + strAdoptionDate + '\'' +
				", strDescription='" + strDescription + '\'' +
				'}';
	}
	//endregion
}
