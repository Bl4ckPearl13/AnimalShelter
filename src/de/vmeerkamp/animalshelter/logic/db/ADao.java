package de.vmeerkamp.animalshelter.logic.db;

import de.vmeerkamp.animalshelter.model.AAnimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Diese Klasse gibt die benoetigten Methoden für ein DAO vor und stellt ueber die Elternklasse
 * {@link ASqlKeyWords} die benoetigten SQL-Schluesselwoerter zur Verfuegung.
 */

public abstract class ADao extends ASqlKeyWords {
	
	//region 1. CREATE
	
	/**
	 * Mit dieser Methode soll ein Tier in die entsprechende DB-Tabelle eingetragen werden.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToInsertIntoDb Tier, das gespeichert werden soll.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected abstract void insertDataRecordIntoDb(Connection dbConnection,
	                                               AAnimal animalToInsertIntoDb) throws ClassCastException;
	
	/**
	 * Mit dieser Methode soll eine Liste von Tieren in die entsprechende DB-Tabelle eingetragen werden.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToInsertIntoDb Tiere, die gespeichert werden sollen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected abstract void insertDataRecordsIntoDb(Connection dbConnection,
	                                                List<? extends AAnimal> animalsToInsertIntoDb) throws ClassCastException;
	//endregion
	
	//region 2. READ
	
	/**
	 * Diese Funktion soll alle Tiere aus der entsprechenden DB-Tabelle als Liste zurueckgeben.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 *
	 * @return allAnimalsFromDb - alle Tiere aus der Tabelle als Liste.
	 */
	
	protected abstract List<? extends AAnimal> getAllDataRecordsFromDb(Connection dbConnection);
	
	/**
	 * Die Funktion soll das Tier mit der uebergebenen ID aus der entsprechenden Tabelle zurueckgeben.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des zu loeschenden Tieres.
	 *
	 * @return animalFromDbById - Tier mir der uebergebenen DB-ID aus der entsprechenden Tabelle.
	 */
	
	protected abstract AAnimal getDataRecordFromDbById(Connection dbConnection, int iId);
	//endregion
	
	//region 3. UPDATE
	
	/**
	 * Mit dieser Methode soll das uebergebene Tier in der entsprechenden Tabelle geaendert werden.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToUpdateInDb Tier mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected abstract void updateDataRecordInDb(Connection dbConnection,
	                                             AAnimal animalToUpdateInDb) throws ClassCastException;
	
	/**
	 * Mit dieser Methode sollen alle Tiere in der uebergebenen Liste in der entsprechenden Tabelle geaendert werden.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToUpdateInDb Liste von Tieren mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected abstract void updateDataRecordsInDb(Connection dbConnection,
	                                              List<? extends AAnimal> animalsToUpdateInDb) throws ClassCastException;
	//endregion
	
	//region 4. DELETE
	
	/**
	 * Mit dieser Methode soll das Tier mit der uebergebenen ID aus der entsprechenden DB-Tabelle geloescht werden.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des zu loeschenden Tieres.
	 */
	
	protected abstract void deleteDataRecordInDb(Connection dbConnection, int iId);
	//endregion
	
	//region 5. Model aus ResultSet erzeugen
	
	/**
	 * Diese Funktion soll aus dem uebergebenen ResultSet das entsprechende Tier-Objekt erzeugen.
	 *
	 * @param currentResultSet ResultSet, dass das zu erzeugende Tier enthaelt.
	 *
	 * @return modelFromResultSet - erzeugtes Tier-Objekt.
	 *
	 * @throws SQLException wenn ein Fehler bei der Verbindung zur oder beim Zugriff auf die DB auftritt.
	 */
	
	protected abstract AAnimal getModelFromResultSet(ResultSet currentResultSet) throws SQLException;
	//endregion
}
