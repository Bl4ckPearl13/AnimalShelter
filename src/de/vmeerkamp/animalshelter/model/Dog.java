package de.vmeerkamp.animalshelter.model;

import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.DefaultValues;

/**
 * Diese Klasse ist ein Model und repraesentiert einen Hund mit all
 * seinen Attributen.
 */

public class Dog extends AAnimal{
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	private boolean     isRegulatedDogBreed;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Dog() {
		super();
		this.isRegulatedDogBreed = DefaultValues.DEF_VALUE_BOOLEAN;
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen eines neuen
	 * Hund-Objektes.
	 *
	 * @param identity Identitaet des Hundes
	 * @param appearance Aussehen des Hundes
	 * @param shelterStay Aufenthalt des Hundes im Tierheim
	 * @param isRegulatedDogBreed true, wenn der Hund ein Listenhund ist und die Abgabe
	 *                               deswegen nur an berechtigte Personen erfolgen darf
	 */
	
	public Dog(Identity identity, Appearance appearance, ShelterStay shelterStay, boolean isRegulatedDogBreed) {
		this.identity            = identity;
		this.appearance          = appearance;
		this.shelterStay         = shelterStay;
		this.isRegulatedDogBreed = isRegulatedDogBreed;
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
	
	public boolean isRegulatedDogBreed() {
		return isRegulatedDogBreed;
	}
	
	public void setRegulatedDogBreed(boolean regulatedDogBreed) {
		this.isRegulatedDogBreed = regulatedDogBreed;
	}
	//endregion
	
	//region 4. Alle Attribute als CSV-String ausgeben
	
	/**
	 * Die Funktion gibt alle Attribute eines Hundes als CSV-String zurueck.
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
				isRegulatedDogBreed + "\n";
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
		isRegulatedDogBreed = Boolean.getBoolean(strAttributes[10]);
	}
	//endregion
	
	//region 6. toString
	
	/**
	 * Die Funktion gibt alle Attribute des Hund-Objektes als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute des Hundes als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Dog{" +
				"identity=" + identity +
				", appearance=" + appearance +
				", shelterStay=" + shelterStay +
				", isRegulatedDogBreed=" + isRegulatedDogBreed +
				'}';
	}
	//endregion
}
