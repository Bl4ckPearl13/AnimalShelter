package de.vmeerkamp.animalshelter.model.parameterobject;

import de.vmeerkamp.animalshelter.settings.DefaultValues;

/**
 * Diese Klasse ist ein Model und beinhaltet alle Daten, die das
 * Aussehen eines Tieres im Tierheim betreffen.
 */

public class Appearance {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private String strBreed;
	private String strColour;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Appearance() {
		this.strBreed = DefaultValues.DEF_VALUE_STRING;
		this.strColour = DefaultValues.DEF_VALUE_STRING;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen des Aussehen
	 * eines Tieres im Tierheim.
	 *
	 * @param strBreed Rasse des Tieres
	 * @param strColour Farbe des Tieres
	 */
	
	public Appearance(String strBreed, String strColour) {
		this.strBreed  = strBreed;
		this.strColour = strColour;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public String getBreed() {
		return strBreed;
	}
	
	public void setBreed(String strBreed) {
		this.strBreed = strBreed;
	}
	
	public String getColour() {
		return strColour;
	}
	
	public void setColour(String strColour) {
		this.strColour = strColour;
	}
	//endregion
	
	//region 4. toString
	
	/**
	 * Die Funktion gibt alle Attribute zum Aussehen eines Tieres als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute zum Aussehen des Tieres als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Appearance{" +
				"strBreed='" + strBreed + '\'' +
				", strColour='" + strColour + '\'' +
				'}';
	}
	//endregion
}
