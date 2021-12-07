package de.vmeerkamp.animalshelter.model;

import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.DefaultValues;

/**
 * Diese Klasse ist ein Model und repraesentiert eine Katze mit all
 * ihren Attributen.
 */

public class Cat extends AAnimal {
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private boolean     isOutdoorCat;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Cat() {
		super();
		this.isOutdoorCat = DefaultValues.DEF_VALUE_BOOLEAN;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen eines neuen
	 * Katzen-Objektes.
	 *
	 * @param identity Identitaet der Katze
	 * @param appearance Aussehen der Katze
	 * @param shelterStay Aufenthalt der Katze im Tierheim
	 * @param isOutdoorCat true, wenn die Katze ein Freigaenger ist
	 */
	
	public Cat(Identity identity, Appearance appearance, ShelterStay shelterStay, boolean isOutdoorCat) {
		this.identity     = identity;
		this.appearance   = appearance;
		this.shelterStay  = shelterStay;
		this.isOutdoorCat = isOutdoorCat;
	}
	//endregion
	
	//region 3. Getter und Setter
	
	@Override
	public Identity getIdentity() {
		return identity;
	}
	
	@Override
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
	
	@Override
	public Appearance getAppearance() {
		return appearance;
	}
	
	@Override
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}
	
	@Override
	public ShelterStay getShelterStay() {
		return shelterStay;
	}
	
	@Override
	public void setShelterStay(ShelterStay shelterStay) {
		this.shelterStay = shelterStay;
	}
	
	public boolean isOutdoorCat() {
		return isOutdoorCat;
	}
	
	public void setOutdoorCat(boolean outdoorCat) {
		this.isOutdoorCat = outdoorCat;
	}
	//endregion
	
	//region 4. Alle Attribute als CSV-String ausgeben
	
	/**
	 * Die Funktion gibt alle Attribute einer Katze als CSV-String zurueck.
	 *
	 * @return allAttributesAsCsvLine - Alle Attribute mit Trennzeichen als {@link String}
	 */
	
	public String getAllAttributesAsCsvLine() {
		return identity.getId() + SPLIT_CHAR +
				identity.getName() + SPLIT_CHAR +
				identity.getDateOfBirth() + SPLIT_CHAR +
				identity.getSex().getSex() + SPLIT_CHAR +
				identity.getSex().isNeutered() + SPLIT_CHAR +
				appearance.getBreed() + SPLIT_CHAR +
				appearance.getColour() + SPLIT_CHAR +
				shelterStay.getAdmissionDate() + SPLIT_CHAR +
				shelterStay.getAdoptionDate() + SPLIT_CHAR +
				shelterStay.getDescription() + SPLIT_CHAR +
				isOutdoorCat + "\n";
	}
	//endregion
	
	//region 5. Alle Attribute aus CSV-String einlesen
	
	/**
	 * Die Methode nimmt einen CSV-String entgegen und speichert die enthaltenen Attribute
	 * in das aktuelle Objekt.
	 *
	 * @param strCsvLine Ausgelesener {@link String} aus CSV-Datei
	 */
	
	public void setAllAttributesFromCsvLine(String strCsvLine) {
		
		String[] strAttributes = strCsvLine.split(SPLIT_CHAR);
		
		identity.setId(Integer.parseInt(strAttributes[0]));
		identity.setName(strAttributes[1]);
		identity.setDateOfBirth(strAttributes[2]);
		identity.getSex().setSex(strAttributes[3]);
		identity.getSex().setNeutered(Boolean.getBoolean(strAttributes[4]));
		appearance.setBreed(strAttributes[5]);
		appearance.setColour(strAttributes[6]);
		shelterStay.setAdmissionDate(strAttributes[7]);
		shelterStay.setAdoptionDate(strAttributes[8]);
		shelterStay.setDescription(strAttributes[9]);
		isOutdoorCat = Boolean.getBoolean(strAttributes[10]);
	}
	//endregion
	
	//region 6. toString
	
	/**
	 * Die Funktion gibt alle Attribute des Katzen-Objektes als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute der Katze als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Cat{" +
				"identity=" + identity +
				", appearance=" + appearance +
				", shelterStay=" + shelterStay +
				", isOutdoorCat=" + isOutdoorCat +
				'}';
	}
	//endregion
}
