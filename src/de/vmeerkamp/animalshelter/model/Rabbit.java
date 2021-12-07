package de.vmeerkamp.animalshelter.model;

import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;

/**
 * Diese Klasse ist ein Model und repraesentiert ein Kaninchen mit all
 * seinen Attributen.
 */

public class Rabbit extends AAnimal{
	
	//region 0. Konstanten
	
	//endregion
	
	//region 1. Klassenvariablen
	
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Standardkonstruktor
	 */
	
	public Rabbit() {
		super();
	}
	
	/**
	 * Dieser parametrisierte Konstruktor dient dem Anlegen eines neuen
	 * Kaninchen-Objektes.
	 *
	 * @param identity Identitaet des Kaninchen
	 * @param appearance Aussehen des Kaninchen
	 * @param shelterStay Aufenthalt des Kaninchen im Tierheim
	 */
	
	public Rabbit(Identity identity, Appearance appearance, ShelterStay shelterStay) {
		this.identity    = identity;
		this.appearance  = appearance;
		this.shelterStay = shelterStay;
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
	//endregion
	
	//region 4. Alle Attribute als CSV-String ausgeben
	
	/**
	 * Die Funktion gibt alle Attribute eines Kaninchens als CSV-String zurueck.
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
				shelterStay.getDescription() + "\n";
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
		
		/*
		 Wenn AdoptionDate und Description im CSV-String leer sind,
		 werden sie vom Split nicht berücksichtigt und das zurückgegebene
		 String[] hat nur eine Länge von 8 bzw 9.
		 Deswegen muss vor dem Setzen dieser beiden Attribute aus dem String[]
		 die Arraylänge überprüft werden.
		 */
		if (strAttributes.length > 8) {
			shelterStay.setAdoptionDate(strAttributes[8]);
		}
		if (strAttributes.length > 9) {
			shelterStay.setDescription(strAttributes[9]);
		}
	}
	//endregion
	
	//region 6. toString
	
	/**
	 * Die Funktion gibt alle Attribute des Kaninchen-Objektes als String zurueck.
	 *
	 * @return strAllAttributesAsString - Alle Attribute des Kaninchen als {@link String}
	 */
	
	@Override
	public String toString() {
		return "Rabbit{" +
				"identity=" + identity +
				", appearance=" + appearance +
				", shelterStay=" + shelterStay +
				'}';
	}
	//endregion
}
