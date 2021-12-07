package de.vmeerkamp.animalshelter.logic.db;

import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Dog;
import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.Sex;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.AppTexts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist ein Data Access Object fuer die Tabelle dog.
 */

public class DaoDog extends ADao {
	
	//region 0.0 Tabellen und Spalten Konstanten
	
	private static final String TABLE_NAME = "dog";
	
	private static final String COLUMN_NAME_PRIMARY_KEY_DOG_ID         = "dog_id";
	private static final String COLUMN_NAME_DOG_NAME                   = "dog_name";
	private static final String COLUMN_NAME_DOG_DATE_OF_BIRTH          = "dog_date_of_birth";
	private static final String COLUMN_NAME_DOG_SEX                    = "dog_sex";
	private static final String COLUMN_NAME_DOG_IS_NEUTERED            = "dog_is_neutered";
	private static final String COLUMN_NAME_DOG_BREED                  = "dog_breed";
	private static final String COLUMN_NAME_DOG_COLOUR                 = "dog_colour";
	private static final String COLUMN_NAME_DOG_ADMISSION_DATE         = "dog_admission_date";
	private static final String COLUMN_NAME_DOG_ADOPTION_DATE          = "dog_adoption_date";
	private static final String COLUMN_NAME_DOG_DESCRIPTION            = "dog_description";
	private static final String COLUMN_NAME_DOG_IS_REGULATED_DOG_BREED = "dog_is_regulated_dog_breed";
	//endregion
	
	//region 0.1 Statement Konstanten
	
	//SELECT * FROM dog
	private static final String DOG_SELECT_ALL = SELECT_ALL_DATA_FROM + TABLE_NAME;
	
	/**
	 * Prepared Select-Statement:<br>
	 * <ol>
	 *     <li>dogId</li>
	 * </ol>
	 */
	//SELECT * FROM dog WHERE dog_id = ?;
	private static final String DOG_PREPARED_SELECT_WHERE_DOG_ID =
			DOG_SELECT_ALL + WHERE_WITH_BLANKS +
					COLUMN_NAME_PRIMARY_KEY_DOG_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	
	/**
	 * Prepared Insert-Statement:<br>
	 * <ol>
	 *     <li>dogName</li>
	 *     <li>dogDateOfBirth</li>
	 *     <li>dogSex</li>
	 *     <li>dogIsNeutered</li>
	 *     <li>dogBreed</li>
	 *     <li>dogColour</li>
	 *     <li>dogAdmissionDate</li>
	 *     <li>dogAdoptionDate</li>
	 *     <li>dogDescription</li>
	 *     <li>dogIsRegulatedDogBreed</li>
	 * </ol>
	 */
	/*
	INSERT INTO dog(
	dog_name,
	dog_date_of_birth,
	dog_sex,
	dog_is_neutered,
	dog_breed,
	dog_colour,
	dog_admission_date,
	dog_adoption_date,
	dog_description,
	dog_is_regulated_dog_breed
	) VALUES (
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?,
	?);
	 */
	private static final String DOG_PREPARED_INSERT =
			INSERT_INTO_WITH_BLANK + TABLE_NAME + CHAR_OPEN_BRACKET +
					COLUMN_NAME_DOG_NAME + CHAR_COMMA +
					COLUMN_NAME_DOG_DATE_OF_BIRTH + CHAR_COMMA +
					COLUMN_NAME_DOG_SEX + CHAR_COMMA +
					COLUMN_NAME_DOG_IS_NEUTERED + CHAR_COMMA +
					COLUMN_NAME_DOG_BREED + CHAR_COMMA +
					COLUMN_NAME_DOG_COLOUR + CHAR_COMMA +
					COLUMN_NAME_DOG_ADMISSION_DATE + CHAR_COMMA +
					COLUMN_NAME_DOG_ADOPTION_DATE + CHAR_COMMA +
					COLUMN_NAME_DOG_DESCRIPTION + CHAR_COMMA +
					COLUMN_NAME_DOG_IS_REGULATED_DOG_BREED +
					CHAR_CLOSE_BRACKET + VALUES_WITH_BLANKS + CHAR_OPEN_BRACKET +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK_WITH_COMMA +
					CHAR_QUESTION_MARK + CHAR_CLOSE_BRACKET_SEMICOLON;
	
	/**
	 * Prepared Update-Statement:<br>
	 * <ol>
	 *     <li>dogName</li>
	 *     <li>dogDateOfBirth</li>
	 *     <li>dogSex</li>
	 *     <li>dogIsNeutered</li>
	 *     <li>dogBreed</li>
	 *     <li>dogColour</li>
	 *     <li>dogAdmissionDate</li>
	 *     <li>dogAdoptionDate</li>
	 *     <li>dogDescription</li>
	 *     <li>dogIsRegulatedDogBreed</li>
	 *     <li>dogId</li>
	 * </ol>
	 */
	/*
	UPDATE dog SET
	dog_name = ?,
	dog_date_of_birth = ?,
	dog_sex = ?,
	dog_is_neutered = ?,
	dog_breed = ?,
	dog_colour = ?,
	dog_admission_date = ?,
	dog_adoption_date = ?,
	dog_description = ?,
	dog_is_regulated_dog_breed = ?
	WHERE dog_id = ?;
	 */
	private static final String DOG_PREPARED_UPDATE = UPDATE_WITH_BLANK + TABLE_NAME + SET_WITH_BLANKS +
			COLUMN_NAME_DOG_NAME + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_DATE_OF_BIRTH + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_SEX + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_IS_NEUTERED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_BREED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_COLOUR + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_ADMISSION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_ADOPTION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_DESCRIPTION + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_DOG_IS_REGULATED_DOG_BREED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			WHERE_WITH_BLANKS + COLUMN_NAME_PRIMARY_KEY_DOG_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			CHAR_SEMICOLON;
	
	/**
	 * Prepared Delete-Statement:<br>
	 * <ol>
	 *     <li>dogId</li>
	 * </ol>
	 */
	//DELETE FROM dog WHERE dog_id = ?;
	private static final String DOG_PREPARED_DELETE = DELETE_FROM_WITH_BLANK + TABLE_NAME + WHERE_WITH_BLANKS +
			COLUMN_NAME_PRIMARY_KEY_DOG_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	//endregion
	
	//region 1. CREATE
	
	/**
	 * Die Methode fuegt das uebergebene {@link Dog}-Objekt in die DB-Tabelle dog ein.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToInsertIntoDb Hund, der gespeichert werden soll.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordIntoDb(Connection dbConnection, AAnimal animalToInsertIntoDb)
			throws ClassCastException {
		
		if (animalToInsertIntoDb instanceof Dog) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(DOG_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				Dog castedDogToInsertIntoDb = (Dog) animalToInsertIntoDb;
				
				preppedInsert.setString(1, castedDogToInsertIntoDb.getIdentity().getName());
				preppedInsert.setString(2, castedDogToInsertIntoDb.getIdentity().getDateOfBirth());
				preppedInsert.setString(3, castedDogToInsertIntoDb.getIdentity().getSex().getSex());
				preppedInsert.setBoolean(4, castedDogToInsertIntoDb.getIdentity().getSex().isNeutered());
				preppedInsert.setString(5, castedDogToInsertIntoDb.getAppearance().getBreed());
				preppedInsert.setString(6, castedDogToInsertIntoDb.getAppearance().getColour());
				preppedInsert.setString(7, castedDogToInsertIntoDb.getShelterStay().getAdmissionDate());
				preppedInsert.setString(8, castedDogToInsertIntoDb.getShelterStay().getAdoptionDate());
				preppedInsert.setString(9, castedDogToInsertIntoDb.getShelterStay().getDescription());
				preppedInsert.setBoolean(10, castedDogToInsertIntoDb.isRegulatedDogBreed());
				
				preppedInsert.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode fuegt die uebergebene {@link List}e von {@link Dog}-Objekt in die DB-Tabelle dog ein.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToInsertIntoDb Hunde, die gespeichert werden sollen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordsIntoDb(Connection dbConnection, List<? extends AAnimal> animalsToInsertIntoDb)
			throws ClassCastException {
		
		if (animalsToInsertIntoDb.get(0) instanceof Dog) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(DOG_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				List<Dog> castedDogsToInsertIntoDb = (List<Dog>) animalsToInsertIntoDb;
				
				for (Dog dog : castedDogsToInsertIntoDb) {
					
					preppedInsert.setString(1, dog.getIdentity().getName());
					preppedInsert.setString(2, dog.getIdentity().getDateOfBirth());
					preppedInsert.setString(3, dog.getIdentity().getSex().getSex());
					preppedInsert.setBoolean(4, dog.getIdentity().getSex().isNeutered());
					preppedInsert.setString(5, dog.getAppearance().getBreed());
					preppedInsert.setString(6, dog.getAppearance().getColour());
					preppedInsert.setString(7, dog.getShelterStay().getAdmissionDate());
					preppedInsert.setString(8, dog.getShelterStay().getAdoptionDate());
					preppedInsert.setString(9, dog.getShelterStay().getDescription());
					preppedInsert.setBoolean(10, dog.isRegulatedDogBreed());
					
					preppedInsert.executeUpdate();
				}
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	//endregion
	
	//region 2. READ
	
	/**
	 * Die Funktion liest alle Eintraege der Tabelle dog aus und gibt sie als
	 * {@link List}e von {@link Dog}-Objekten zurueck.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 *
	 * @return dogsFromDb - Liste mit Hunden aus der DB oder leere Liste,
	 * wenn keine Eintraege vorhanden sind oder die DB-Verbindung fehlschlaegt.
	 */
	
	protected List<Dog> getAllDataRecordsFromDb(Connection dbConnection) {
		
		List<Dog> allDogsFromDb = new ArrayList<>();
		Dog       dog;
		
		try (Statement statement = dbConnection.createStatement();
		     ResultSet resultSet = statement.executeQuery(DOG_SELECT_ALL)) {
			
			while (resultSet.next()) {
				
				dog = getModelFromResultSet(resultSet);
				
				allDogsFromDb.add(dog);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allDogsFromDb;
	}
	
	/**
	 * Die Funktion liest dem Eintrag mit der uebergebenen ID aus der Tabelle dog aus
	 * und gibt ihn als {@link Dog} zurueck. Wurde kein Eintrag gefunden wird null
	 * zurueckgegeben.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des auszulesenden Hundes.
	 *
	 * @return dogFromDbById - gefundener Hund oder null.
	 */
	
	protected Dog getDataRecordFromDbById(Connection dbConnection, int iId) {
		
		Dog dog = null;
		
		try (PreparedStatement preppedSelect = dbConnection.prepareStatement(DOG_PREPARED_SELECT_WHERE_DOG_ID)) {
			
			preppedSelect.setInt(1, iId);
			
			try (ResultSet resultSet = preppedSelect.executeQuery()) {
				
				if (resultSet.next()) {
					
					dog = getModelFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dog;
	}
	//endregion
	
	//region 3. UPDATE
	
	/**
	 * Die Methode aendert das uebergebene {@link Dog}-Objekt in der Tabelle dog.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToUpdateInDb Hund mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordInDb(Connection dbConnection, AAnimal animalToUpdateInDb)
			throws ClassCastException {
		
		if (animalToUpdateInDb instanceof Dog) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(DOG_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				Dog castedDogToUpdateInDb = (Dog) animalToUpdateInDb;
				
				preppedUpdate.setString(1, castedDogToUpdateInDb.getIdentity().getName());
				preppedUpdate.setString(2, castedDogToUpdateInDb.getIdentity().getDateOfBirth());
				preppedUpdate.setString(3, castedDogToUpdateInDb.getIdentity().getSex().getSex());
				preppedUpdate.setBoolean(4, castedDogToUpdateInDb.getIdentity().getSex().isNeutered());
				preppedUpdate.setString(5, castedDogToUpdateInDb.getAppearance().getBreed());
				preppedUpdate.setString(6, castedDogToUpdateInDb.getAppearance().getColour());
				preppedUpdate.setString(7, castedDogToUpdateInDb.getShelterStay().getAdmissionDate());
				preppedUpdate.setString(8, castedDogToUpdateInDb.getShelterStay().getAdoptionDate());
				preppedUpdate.setString(9, castedDogToUpdateInDb.getShelterStay().getDescription());
				preppedUpdate.setBoolean(10, castedDogToUpdateInDb.isRegulatedDogBreed());
				preppedUpdate.setInt(11, castedDogToUpdateInDb.getIdentity().getId());
				
				preppedUpdate.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode aendert die uebergebene {@link List}e von {@link Dog}-Objekten in der Tabelle dog.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToUpdateInDb Liste von Hunden mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordsInDb(Connection dbConnection, List<? extends AAnimal> animalsToUpdateInDb)
			throws ClassCastException {
		
		if (animalsToUpdateInDb.get(0) instanceof Dog) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(DOG_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				List<Dog> castedDogsToUpdateInDb = (List<Dog>) animalsToUpdateInDb;
				
				for (Dog dog : castedDogsToUpdateInDb) {
					
					preppedUpdate.setString(1, dog.getIdentity().getName());
					preppedUpdate.setString(2, dog.getIdentity().getDateOfBirth());
					preppedUpdate.setString(3, dog.getIdentity().getSex().getSex());
					preppedUpdate.setBoolean(4, dog.getIdentity().getSex().isNeutered());
					preppedUpdate.setString(5, dog.getAppearance().getBreed());
					preppedUpdate.setString(6, dog.getAppearance().getColour());
					preppedUpdate.setString(7, dog.getShelterStay().getAdmissionDate());
					preppedUpdate.setString(8, dog.getShelterStay().getAdoptionDate());
					preppedUpdate.setString(9, dog.getShelterStay().getDescription());
					preppedUpdate.setBoolean(10, dog.isRegulatedDogBreed());
					preppedUpdate.setInt(11, dog.getIdentity().getId());
					
					preppedUpdate.executeUpdate();
				}
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	//endregion
	
	//region 4. DELETE
	
	/**
	 * Die Methode loescht den Hund mit der uebergebenen ID aus der Tabelle dog.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des zu loeschenden Hundes.
	 */
	
	protected void deleteDataRecordInDb(Connection dbConnection, int iId) {
		
		try (PreparedStatement preppedDelete = dbConnection.prepareStatement(DOG_PREPARED_DELETE)) {
			
			preppedDelete.setInt(1, iId);
			
			preppedDelete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//endregion
	
	//region 5. Model aus ResultSet erzeugen
	
	/**
	 * Die Funktion generiert aus dem uebergebenen {@link ResultSet} ein {@link Dog}-
	 * Objekt und gibt es zurueck. Dabei werden die Attribute unabhaengig von der Reihenfolge
	 * der Spalten im {@link ResultSet} korrekt gesetzt.
	 *
	 * @param currentResultSet ResultSet, das den zu erzeugenden Hund enthaelt.
	 *
	 * @return dogFromResultSet - erzeugtes Hunde-Objekt.
	 *
	 * @throws SQLException wenn ein Fehler bei der Verbindung zur oder beim Zugriff auf die DB auftritt.
	 */
	
	protected Dog getModelFromResultSet(ResultSet currentResultSet) throws SQLException {
		
		final int iColumnIndexDogId               = currentResultSet.findColumn(COLUMN_NAME_PRIMARY_KEY_DOG_ID);
		final int iColumnIndexDogName             = currentResultSet.findColumn(COLUMN_NAME_DOG_NAME);
		final int iColumnIndexDogDateOfBirth      = currentResultSet.findColumn(COLUMN_NAME_DOG_DATE_OF_BIRTH);
		final int iColumnIndexDogSex              = currentResultSet.findColumn(COLUMN_NAME_DOG_SEX);
		final int iColumnIndexDogIsNeutered       = currentResultSet.findColumn(COLUMN_NAME_DOG_IS_NEUTERED);
		final int iColumnIndexDogBreed            = currentResultSet.findColumn(COLUMN_NAME_DOG_BREED);
		final int iColumnIndexDogColour           = currentResultSet.findColumn(COLUMN_NAME_DOG_COLOUR);
		final int iColumnIndexDogAdmissionDate    = currentResultSet.findColumn(COLUMN_NAME_DOG_ADMISSION_DATE);
		final int iColumnIndexDogAdoptionDate     = currentResultSet.findColumn(COLUMN_NAME_DOG_ADOPTION_DATE);
		final int iColumnIndexDogDescription      = currentResultSet.findColumn(COLUMN_NAME_DOG_DESCRIPTION);
		final int iColumnIndexDogIsRegulatedBreed = currentResultSet.findColumn(COLUMN_NAME_DOG_IS_REGULATED_DOG_BREED);
		
		final int     iDogId               = currentResultSet.getInt(iColumnIndexDogId);
		final String  strDogName             = currentResultSet.getString(iColumnIndexDogName);
		final String  strDogDateOfBirth      = currentResultSet.getString(iColumnIndexDogDateOfBirth);
		final String  strDogSex              = currentResultSet.getString(iColumnIndexDogSex);
		final boolean dogIsNeutered       = currentResultSet.getBoolean(iColumnIndexDogIsNeutered);
		final String  strDogBreed            = currentResultSet.getString(iColumnIndexDogBreed);
		final String  strDogColour           = currentResultSet.getString(iColumnIndexDogColour);
		final String  strDogAdmissionDate    = currentResultSet.getString(iColumnIndexDogAdmissionDate);
		final String  strDogAdoptionDate     = currentResultSet.getString(iColumnIndexDogAdoptionDate);
		final String  strDogDescription      = currentResultSet.getString(iColumnIndexDogDescription);
		final boolean dogIsRegulatedBreed = currentResultSet.getBoolean(iColumnIndexDogIsRegulatedBreed);
		
		Sex         sex         = new Sex(strDogSex, dogIsNeutered);
		Identity    identity    = new Identity(iDogId, strDogName, strDogDateOfBirth, sex);
		Appearance  appearance  = new Appearance(strDogBreed, strDogColour);
		ShelterStay shelterStay = new ShelterStay(strDogAdmissionDate, strDogAdoptionDate, strDogDescription);
		
		return new Dog(identity, appearance, shelterStay, dogIsRegulatedBreed);
	}
	//endregion
}
