package de.vmeerkamp.animalshelter.logic.db;

import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Rabbit;
import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.Sex;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.AppTexts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist ein Data Access Object fuer die Tabelle rabbit.
 */

public class DaoRabbit extends ADao {
	
	//region 0.0 Tabellen und Spalten Konstanten
	
	private static final String TABLE_NAME = "rabbit";
	
	private static final String COLUMN_NAME_PRIMARY_KEY_RABBIT_ID = "rabbit_id";
	private static final String COLUMN_NAME_RABBIT_NAME           = "rabbit_name";
	private static final String COLUMN_NAME_RABBIT_DATE_OF_BIRTH  = "rabbit_date_of_birth";
	private static final String COLUMN_NAME_RABBIT_SEX            = "rabbit_sex";
	private static final String COLUMN_NAME_RABBIT_IS_NEUTERED    = "rabbit_is_neutered";
	private static final String COLUMN_NAME_RABBIT_BREED          = "rabbit_breed";
	private static final String COLUMN_NAME_RABBIT_COLOUR         = "rabbit_colour";
	private static final String COLUMN_NAME_RABBIT_ADMISSION_DATE = "rabbit_admission_date";
	private static final String COLUMN_NAME_RABBIT_ADOPTION_DATE  = "rabbit_adoption_date";
	private static final String COLUMN_NAME_RABBIT_DESCRIPTION    = "rabbit_description";
	//endregion
	
	//region 0.1 Statement Konstanten
	
	//SELECT * FROM rabbit
	private static final String RABBIT_SELECT_ALL = SELECT_ALL_DATA_FROM + TABLE_NAME;
	
	/**
	 * Prepared Select-Statement:<br>
	 * <ol>
	 *     <li>rabbitId</li>
	 * </ol>
	 */
	//SELECT * FROM rabbit WHERE rabbit_id = ?;
	private static final String RABBIT_PREPARED_SELECT_WHERE_RABBIT_ID =
			RABBIT_SELECT_ALL + WHERE_WITH_BLANKS +
					COLUMN_NAME_PRIMARY_KEY_RABBIT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	
	/**
	 * Prepared Insert-Statement:<br>
	 * <ol>
	 *     <li>rabbitName</li>
	 *     <li>rabbitDateOfBirth</li>
	 *     <li>rabbitSex</li>
	 *     <li>rabbitIsNeutered</li>
	 *     <li>rabbitBreed</li>
	 *     <li>rabbitColour</li>
	 *     <li>rabbitAdmissionDate</li>
	 *     <li>rabbitAdoptionDate</li>
	 *     <li>rabbitDescription</li>
	 * </ol>
	 */
	/*
	INSERT INTO rabbit(
	rabbit_name,
	rabbit_date_of_birth,
	rabbit_Sex,
	rabbit_is_neutered,
	rabbit_breed,
	rabbit_colour,
	rabbit_admission_date,
	rabbit_adoption_date,
	rabbit_description
	) VALUES (
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
	private static final String RABBIT_PREPARED_INSERT =
			INSERT_INTO_WITH_BLANK + TABLE_NAME + CHAR_OPEN_BRACKET +
					COLUMN_NAME_RABBIT_NAME + CHAR_COMMA +
					COLUMN_NAME_RABBIT_DATE_OF_BIRTH + CHAR_COMMA +
					COLUMN_NAME_RABBIT_SEX + CHAR_COMMA +
					COLUMN_NAME_RABBIT_IS_NEUTERED + CHAR_COMMA +
					COLUMN_NAME_RABBIT_BREED + CHAR_COMMA +
					COLUMN_NAME_RABBIT_COLOUR + CHAR_COMMA +
					COLUMN_NAME_RABBIT_ADMISSION_DATE + CHAR_COMMA +
					COLUMN_NAME_RABBIT_ADOPTION_DATE + CHAR_COMMA +
					COLUMN_NAME_RABBIT_DESCRIPTION +
					CHAR_CLOSE_BRACKET + VALUES_WITH_BLANKS + CHAR_OPEN_BRACKET +
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
	 *     <li>rabbitName</li>
	 *     <li>rabbitDateOfBirth</li>
	 *     <li>rabbitSex</li>
	 *     <li>rabbitIsNeutered</li>
	 *     <li>rabbitBreed</li>
	 *     <li>rabbitColour</li>
	 *     <li>rabbitAdmissionDate</li>
	 *     <li>rabbitAdoptionDate</li>
	 *     <li>rabbitDescription</li>
	 *     <li>rabbitId</li>
	 * </ol>
	 */
	/*
	UPDATE rabbit SET
	rabbit_name = ?,
	rabbit_date_of_birth = ?,
	rabbit_Sex = ?,
	rabbit_is_neutered = ?,
	rabbit_breed = ?,
	rabbit_colour = ?,
	rabbit_admission_date = ?,
	rabbit_adoption_date = ?,
	rabbit_description = ?
	WHERE rabbit_id = ?;
	 */
	private static final String RABBIT_PREPARED_UPDATE = UPDATE_WITH_BLANK + TABLE_NAME + SET_WITH_BLANKS +
			COLUMN_NAME_RABBIT_NAME + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_DATE_OF_BIRTH + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_SEX + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_IS_NEUTERED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_BREED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_COLOUR + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_ADMISSION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_ADOPTION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_RABBIT_DESCRIPTION + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			WHERE_WITH_BLANKS + COLUMN_NAME_PRIMARY_KEY_RABBIT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			CHAR_SEMICOLON;
	
	/**
	 * Prepared Delete-Statement:<br>
	 * <ol>
	 *     <li>rabbitId</li>
	 * </ol>
	 */
	//DELETE FROM rabbit WHERE rabbit_id = ?;
	private static final String RABBIT_PREPARED_DELETE = DELETE_FROM_WITH_BLANK + TABLE_NAME + WHERE_WITH_BLANKS +
			COLUMN_NAME_PRIMARY_KEY_RABBIT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	//endregion
	
	//region 1. CREATE
	
	/**
	 * Die Methode fuegt das uebergebene {@link Rabbit}-Objekt in die DB-Tabelle rabbit ein.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToInsertIntoDb Kaninchen, das gespeichert werden soll.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordIntoDb(Connection dbConnection, AAnimal animalToInsertIntoDb)
			throws ClassCastException {
		
		if (animalToInsertIntoDb instanceof Rabbit) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(RABBIT_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				Rabbit castedRabbitToInsertIntoDb = (Rabbit) animalToInsertIntoDb;
				
				preppedInsert.setString(1, castedRabbitToInsertIntoDb.getIdentity().getName());
				preppedInsert.setString(2, castedRabbitToInsertIntoDb.getIdentity().getDateOfBirth());
				preppedInsert.setString(3, castedRabbitToInsertIntoDb.getIdentity().getSex().getSex());
				preppedInsert.setBoolean(4, castedRabbitToInsertIntoDb.getIdentity().getSex().isNeutered());
				preppedInsert.setString(5, castedRabbitToInsertIntoDb.getAppearance().getBreed());
				preppedInsert.setString(6, castedRabbitToInsertIntoDb.getAppearance().getColour());
				preppedInsert.setString(7, castedRabbitToInsertIntoDb.getShelterStay().getAdmissionDate());
				preppedInsert.setString(8, castedRabbitToInsertIntoDb.getShelterStay().getAdoptionDate());
				preppedInsert.setString(9, castedRabbitToInsertIntoDb.getShelterStay().getDescription());
				
				preppedInsert.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode fuegt die uebergebene {@link List}e von {@link Rabbit}-Objekt in die DB-Tabelle rabbit ein.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToInsertIntoDb Kaninchen, die gespeichert werden sollen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordsIntoDb(Connection dbConnection, List<? extends AAnimal> animalsToInsertIntoDb)
			throws ClassCastException {
		
		if (animalsToInsertIntoDb.get(0) instanceof Rabbit) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(RABBIT_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				List<Rabbit> castedRabbitsToInsertIntoDb = (List<Rabbit>) animalsToInsertIntoDb;
				
				for (Rabbit rabbit : castedRabbitsToInsertIntoDb) {
					
					preppedInsert.setString(1, rabbit.getIdentity().getName());
					preppedInsert.setString(2, rabbit.getIdentity().getDateOfBirth());
					preppedInsert.setString(3, rabbit.getIdentity().getSex().getSex());
					preppedInsert.setBoolean(4, rabbit.getIdentity().getSex().isNeutered());
					preppedInsert.setString(5, rabbit.getAppearance().getBreed());
					preppedInsert.setString(6, rabbit.getAppearance().getColour());
					preppedInsert.setString(7, rabbit.getShelterStay().getAdmissionDate());
					preppedInsert.setString(8, rabbit.getShelterStay().getAdoptionDate());
					preppedInsert.setString(9, rabbit.getShelterStay().getDescription());
					
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
	 * Die Funktion liest alle Eintraege der Tabelle rabbit aus und gibt sie als
	 * {@link List}e von {@link Rabbit}-Objekten zurueck.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 *
	 * @return rabbitsFromDb - Liste mit Kaninchen aus der DB oder leere Liste,
	 * wenn keine Eintraege vorhanden sind oder die DB-Verbindung fehlschlaegt.
	 */
	
	protected List<Rabbit> getAllDataRecordsFromDb(Connection dbConnection) {
		
		List<Rabbit> allRabbitsFromDb = new ArrayList<>();
		Rabbit       rabbit;
		
		try (Statement statement = dbConnection.createStatement();
		     ResultSet resultSet = statement.executeQuery(RABBIT_SELECT_ALL)) {
			
			while (resultSet.next()) {
				
				rabbit = getModelFromResultSet(resultSet);
				
				allRabbitsFromDb.add(rabbit);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allRabbitsFromDb;
	}
	
	/**
	 * Die Funktion liest dem Eintrag mit der uebergebenen ID aus der Tabelle rabbit aus
	 * und gibt ihn als {@link Rabbit} zurueck. Wurde kein Eintrag gefunden wird null
	 * zurueckgegeben.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des auszulesenden Kaninchen.
	 *
	 * @return rabbitFromDbById - gefundenes Kaninchen oder null.
	 */
	
	protected Rabbit getDataRecordFromDbById(Connection dbConnection, int iId) {
		
		Rabbit rabbit = null;
		
		try (PreparedStatement preppedSelect = dbConnection.prepareStatement(RABBIT_PREPARED_SELECT_WHERE_RABBIT_ID)) {
			
			preppedSelect.setInt(1, iId);
			
			try (ResultSet resultSet = preppedSelect.executeQuery()) {
				
				if (resultSet.next()) {
					
					rabbit = getModelFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rabbit;
	}
	//endregion
	
	//region 3. UPDATE
	
	/**
	 * Die Methode aendert das uebergebene {@link Rabbit}-Objekt in der Tabelle rabbit.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalToUpdateInDb Kaninchen mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordInDb(Connection dbConnection, AAnimal animalToUpdateInDb)
			throws ClassCastException {
		
		if (animalToUpdateInDb instanceof Rabbit) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(RABBIT_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				Rabbit castedRabbitToUpdateInDb = (Rabbit) animalToUpdateInDb;
				
				preppedUpdate.setString(1, castedRabbitToUpdateInDb.getIdentity().getName());
				preppedUpdate.setString(2, castedRabbitToUpdateInDb.getIdentity().getDateOfBirth());
				preppedUpdate.setString(3, castedRabbitToUpdateInDb.getIdentity().getSex().getSex());
				preppedUpdate.setBoolean(4, castedRabbitToUpdateInDb.getIdentity().getSex().isNeutered());
				preppedUpdate.setString(5, castedRabbitToUpdateInDb.getAppearance().getBreed());
				preppedUpdate.setString(6, castedRabbitToUpdateInDb.getAppearance().getColour());
				preppedUpdate.setString(7, castedRabbitToUpdateInDb.getShelterStay().getAdmissionDate());
				preppedUpdate.setString(8, castedRabbitToUpdateInDb.getShelterStay().getAdoptionDate());
				preppedUpdate.setString(9, castedRabbitToUpdateInDb.getShelterStay().getDescription());
				preppedUpdate.setInt(10, castedRabbitToUpdateInDb.getIdentity().getId());
				
				preppedUpdate.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode aendert die uebergebene {@link List}e von {@link Rabbit}-Objekten in der Tabelle rabbit.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param animalsToUpdateInDb Liste von Kaninchen mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordsInDb(Connection dbConnection, List<? extends AAnimal> animalsToUpdateInDb)
			throws ClassCastException {
		
		if (animalsToUpdateInDb.get(0) instanceof Rabbit) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(RABBIT_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				List<Rabbit> castedRabbitsToUpdateInDb = (List<Rabbit>) animalsToUpdateInDb;
				
				for (Rabbit rabbit : castedRabbitsToUpdateInDb) {
					
					preppedUpdate.setString(1, rabbit.getIdentity().getName());
					preppedUpdate.setString(2, rabbit.getIdentity().getDateOfBirth());
					preppedUpdate.setString(3, rabbit.getIdentity().getSex().getSex());
					preppedUpdate.setBoolean(4, rabbit.getIdentity().getSex().isNeutered());
					preppedUpdate.setString(5, rabbit.getAppearance().getBreed());
					preppedUpdate.setString(6, rabbit.getAppearance().getColour());
					preppedUpdate.setString(7, rabbit.getShelterStay().getAdmissionDate());
					preppedUpdate.setString(8, rabbit.getShelterStay().getAdoptionDate());
					preppedUpdate.setString(9, rabbit.getShelterStay().getDescription());
					preppedUpdate.setInt(10, rabbit.getIdentity().getId());
					
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
	 * Die Methode loescht das Kaninchen mit der uebergebenen ID aus der Tabelle rabbit.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId DB-ID des zu loeschenden Kaninchens.
	 */
	
	protected void deleteDataRecordInDb(Connection dbConnection, int iId) {
		
		try (PreparedStatement preppedDelete = dbConnection.prepareStatement(RABBIT_PREPARED_DELETE)) {
			
			preppedDelete.setInt(1, iId);
			
			preppedDelete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//endregion
	
	//region 5. Model aus ResultSet erzeugen
	
	/**
	 * Die Funktion generiert aus dem uebergebenen {@link ResultSet} ein {@link Rabbit}-
	 * Objekt und gibt es zurueck. Dabei werden die Attribute unabhaengig von der Reihenfolge
	 * der Spalten im {@link ResultSet} korrekt gesetzt.
	 *
	 * @param currentResultSet ResultSet, das das zu erzeugende Kaninchen enthaelt.
	 *
	 * @return rabbitFromResultSet - erzeugtes Kaninchen-Objekt.
	 *
	 * @throws SQLException wenn ein Fehler bei der Verbindung zur oder beim Zugriff auf die DB auftritt.
	 */
	
	protected Rabbit getModelFromResultSet(ResultSet currentResultSet) throws SQLException {
		
		final int iColumnIndexRabbitId            = currentResultSet.findColumn(COLUMN_NAME_PRIMARY_KEY_RABBIT_ID);
		final int iColumnIndexRabbitName          = currentResultSet.findColumn(COLUMN_NAME_RABBIT_NAME);
		final int iColumnIndexRabbitDateOfBirth   = currentResultSet.findColumn(COLUMN_NAME_RABBIT_DATE_OF_BIRTH);
		final int iColumnIndexRabbitSex           = currentResultSet.findColumn(COLUMN_NAME_RABBIT_SEX);
		final int iColumnIndexRabbitIsNeutered    = currentResultSet.findColumn(COLUMN_NAME_RABBIT_IS_NEUTERED);
		final int iColumnIndexRabbitBreed         = currentResultSet.findColumn(COLUMN_NAME_RABBIT_BREED);
		final int iColumnIndexRabbitColour        = currentResultSet.findColumn(COLUMN_NAME_RABBIT_COLOUR);
		final int iColumnIndexRabbitAdmissionDate = currentResultSet.findColumn(COLUMN_NAME_RABBIT_ADMISSION_DATE);
		final int iColumnIndexRabbitAdoptionDate  = currentResultSet.findColumn(COLUMN_NAME_RABBIT_ADOPTION_DATE);
		final int iColumnIndexRabbitDescription   = currentResultSet.findColumn(COLUMN_NAME_RABBIT_DESCRIPTION);
		
		final int     iRabbitId            = currentResultSet.getInt(iColumnIndexRabbitId);
		final String  strRabbitName          = currentResultSet.getString(iColumnIndexRabbitName);
		final String  strRabbitDateOfBirth   = currentResultSet.getString(iColumnIndexRabbitDateOfBirth);
		final String  strRabbitSex           = currentResultSet.getString(iColumnIndexRabbitSex);
		final boolean rabbitIsNeutered    = currentResultSet.getBoolean(iColumnIndexRabbitIsNeutered);
		final String  strRabbitBreed         = currentResultSet.getString(iColumnIndexRabbitBreed);
		final String  strRabbitColour        = currentResultSet.getString(iColumnIndexRabbitColour);
		final String  strRabbitAdmissionDate = currentResultSet.getString(iColumnIndexRabbitAdmissionDate);
		final String  strRabbitAdoptionDate  = currentResultSet.getString(iColumnIndexRabbitAdoptionDate);
		final String  strRabbitDescription   = currentResultSet.getString(iColumnIndexRabbitDescription);
		
		Sex         sex         = new Sex(strRabbitSex, rabbitIsNeutered);
		Identity    identity    = new Identity(iRabbitId, strRabbitName, strRabbitDateOfBirth, sex);
		Appearance  appearance  = new Appearance(strRabbitBreed, strRabbitColour);
		ShelterStay shelterStay = new ShelterStay(strRabbitAdmissionDate, strRabbitAdoptionDate, strRabbitDescription);
		
		return new Rabbit(identity, appearance, shelterStay);
	}
	//endregion
}
