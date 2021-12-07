package de.vmeerkamp.animalshelter.model.parameterobject;

import de.vmeerkamp.animalshelter.settings.DefaultValues;

/**
 * Diese Klasse ist ein Model und beinhaltet alle Daten, die das Geschlecht
 * eines Tieres im Tierheim betreffen.
 */

public class Sex {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private String  strSex;
	private boolean isNeutered;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Sex() {
		this.strSex     = DefaultValues.DEF_VALUE_STRING;
		this.isNeutered = DefaultValues.DEF_VALUE_BOOLEAN;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen des Geschlechts
	 * eines Tieres im Tierheim.
	 *
	 * @param strSex Geschlecht des Tieres
	 * @param isNeutered true, wenn das Tier kastriert ist
	 */
	
	public Sex(String strSex, boolean isNeutered) {
		this.strSex     = strSex;
		this.isNeutered = isNeutered;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public String getSex() {
		return strSex;
	}
	
	public void setSex(String strGender) {
		this.strSex = strGender;
	}
	
	public boolean isNeutered() {
		return isNeutered;
	}
	
	public void setNeutered(boolean neutered) {
		this.isNeutered = neutered;
	}
	//endregion
	
	//region 4. toString
	
	/**
	 * Die Funktion gibt alle Attribute zum Geschlecht eines Tieres als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute zum Geschlecht des Tieres als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Sex{" +
				"strSex='" + strSex + '\'' +
				", isNeutered=" + isNeutered +
				'}';
	}
	//endregion
}
