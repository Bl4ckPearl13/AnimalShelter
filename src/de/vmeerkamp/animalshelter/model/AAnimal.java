package de.vmeerkamp.animalshelter.model;

import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;

/**
 * Die Klasse gibt die Attribute und Methoden vor, die allen Tieren gemeinsam sind.
 */

public abstract class AAnimal {
	
	//region 0. Konstanten
	
	protected static final String SPLIT_CHAR = ";";
	//endregion
	
	//region 1. Klassenvariablen
	
	protected Identity    identity;
	protected Appearance  appearance;
	protected ShelterStay shelterStay;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public AAnimal() {
		this.identity = new Identity();
		this.appearance = new Appearance();
		this.shelterStay = new ShelterStay();
	}
	//endregion
	
	//region 3. Getter und Setter
	
	public abstract Identity getIdentity();
	
	public abstract void setIdentity(Identity identity);
	
	public abstract Appearance getAppearance();
	
	public abstract void setAppearance(Appearance appearance);
	
	public abstract ShelterStay getShelterStay();
	
	public abstract void setShelterStay(ShelterStay shelterStay);
	//endregion
	
	//region 4. Alle Attribute als CSV-String ausgeben
	
	/**
	 * Diese Funktion soll alle Attribute des Tieres mit Trennzeichen getrennt
	 * als {@link String} zurueckgeben.
	 *
	 * @return strAllAttributesAsCsvLine
	 */
	
	public abstract String getAllAttributesAsCsvLine();
	//endregion
	
	//region 5. Alle Attribute aus CSV-String einlesen
	
	/**
	 * Diese Methode soll einen mit Trennzeichen getrennten {@link String} entgegen
	 * nehmen und die Attribute des aufrufenden Objektes damit setzten.
	 *
	 * @param strCsvLine Ein CSV-String mit allen Attributen fuer das Objekt
	 */
	
	public abstract void setAllAttributesFromCsvLine(String strCsvLine);
	//endregion
	
	//region 6. toString
	
	/**
	 * Die Funktion soll alle Attribute des Objektes als fuer die Ausgabe formatierten
	 * {@link String} zurueckgeben.
	 *
	 * @return strAllAttributesAsString
	 */
	
	public abstract String toString();
	//endregion
}
