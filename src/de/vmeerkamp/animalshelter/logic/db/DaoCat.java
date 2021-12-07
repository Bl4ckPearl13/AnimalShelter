package de.vmeerkamp.animalshelter.logic.db;

import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Cat;
import de.vmeerkamp.animalshelter.model.parameterobject.Appearance;
import de.vmeerkamp.animalshelter.model.parameterobject.Identity;
import de.vmeerkamp.animalshelter.model.parameterobject.Sex;
import de.vmeerkamp.animalshelter.model.parameterobject.ShelterStay;
import de.vmeerkamp.animalshelter.settings.AppTexts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse ist ein Data Access Object fuer die Tabelle cat.
 */

public class DaoCat extends ADao {
	
	//region 0.0 Tabellen und Spalten Konstanten
	
	private static final String TABLE_NAME = "cat";
	
	private static final String COLUMN_NAME_PRIMARY_KEY_CAT_ID = "cat_id";
	private static final String COLUMN_NAME_CAT_NAME           = "cat_name";
	private static final String COLUMN_NAME_CAT_DATE_OF_BIRTH  = "cat_date_of_birth";
	private static final String COLUMN_NAME_CAT_SEX            = "cat_sex";
	private static final String COLUMN_NAME_CAT_IS_NEUTERED    = "cat_is_neutered";
	private static final String COLUMN_NAME_CAT_BREED          = "cat_breed";
	private static final String COLUMN_NAME_CAT_COLOUR         = "cat_colour";
	private static final String COLUMN_NAME_CAT_ADMISSION_DATE = "cat_admission_date";
	private static final String COLUMN_NAME_CAT_ADOPTION_DATE  = "cat_adoption_date";
	private static final String COLUMN_NAME_CAT_DESCRIPTION    = "cat_description";
	private static final String COLUMN_NAME_CAT_IS_OUTDOOR_CAT = "cat_is_outdoor_cat";
	//endregion
	
	//region 0.1 Statement Konstanten
	
	//SELECT * FROM cat
	private static final String CAT_SELECT_ALL = SELECT_ALL_DATA_FROM + TABLE_NAME;
	
	/**
	 * Prepared Select-Statement:<br>
	 * <ol>
	 *     <li>catId</li>
	 * </ol>
	 */
	//SELECT * FROM cat WHERE cat_id = ?;
	private static final String CAT_PREPARED_SELECT_WHERE_CAT_ID =
			CAT_SELECT_ALL + WHERE_WITH_BLANKS +
					COLUMN_NAME_PRIMARY_KEY_CAT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	
	/**
	 * Prepared Insert-Statement:<br>
	 * <ol>
	 *     <li>catName</li>
	 *     <li>catDateOfBirth</li>
	 *     <li>catSex</li>
	 *     <li>catIsNeutered</li>
	 *     <li>catBreed</li>
	 *     <li>catColour</li>
	 *     <li>catAdmissionDate</li>
	 *     <li>catAdoptionDate</li>
	 *     <li>catDescription</li>
	 *     <li>catIsOutdoorCat</li>
	 * </ol>
	 */
	/*
	INSERT INTO cat(
	cat_name,
	cat_date_of_birth,
	cat_sex,
	cat_is_neutered,
	cat_breed,
	cat_colour,
	cat_admission_date,
	cat_adoption_date,
	cat_description,
	cat_is_outdoor_cat
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
	private static final String CAT_PREPARED_INSERT =
			INSERT_INTO_WITH_BLANK + TABLE_NAME + CHAR_OPEN_BRACKET +
					COLUMN_NAME_CAT_NAME + CHAR_COMMA +
					COLUMN_NAME_CAT_DATE_OF_BIRTH + CHAR_COMMA +
					COLUMN_NAME_CAT_SEX + CHAR_COMMA +
					COLUMN_NAME_CAT_IS_NEUTERED + CHAR_COMMA +
					COLUMN_NAME_CAT_BREED + CHAR_COMMA +
					COLUMN_NAME_CAT_COLOUR + CHAR_COMMA +
					COLUMN_NAME_CAT_ADMISSION_DATE + CHAR_COMMA +
					COLUMN_NAME_CAT_ADOPTION_DATE + CHAR_COMMA +
					COLUMN_NAME_CAT_DESCRIPTION + CHAR_COMMA +
					COLUMN_NAME_CAT_IS_OUTDOOR_CAT +
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
	 *     <li>catName</li>
	 *     <li>catDateOfBirth</li>
	 *     <li>catSex</li>
	 *     <li>catIsNeutered</li>
	 *     <li>catBreed</li>
	 *     <li>catColour</li>
	 *     <li>catAdmissionDate</li>
	 *     <li>catAdoptionDate</li>
	 *     <li>catDescription</li>
	 *     <li>catIsOutdoorCat</li>
	 *     <li>catId</li>
	 * </ol>
	 */
	/*
	UPDATE cat SET
	cat_name = ?,
	cat_date_of_birth = ?,
	cat_sex = ?,
	cat_is_neutered = ?,
	cat_breed = ?,
	cat_colour = ?,
	cat_admission_date = ?,
	cat_adoption_date = ?,
	cat_description = ?,
	cat_is_outdoor_cat = ?
	WHERE cat_id = ?;
	 */
	private static final String CAT_PREPARED_UPDATE = UPDATE_WITH_BLANK + TABLE_NAME + SET_WITH_BLANKS +
			COLUMN_NAME_CAT_NAME + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_DATE_OF_BIRTH + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_SEX + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_IS_NEUTERED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_BREED + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_COLOUR + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_ADMISSION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_ADOPTION_DATE + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_DESCRIPTION + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK_WITH_COMMA +
			COLUMN_NAME_CAT_IS_OUTDOOR_CAT + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			WHERE_WITH_BLANKS + COLUMN_NAME_PRIMARY_KEY_CAT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK +
			CHAR_SEMICOLON;
	
	/**
	 * Prepared Delete-Statement:<br>
	 * <ol>
	 *     <li>catId</li>
	 * </ol>
	 */
	//DELETE FROM cat WHERE cat_id = ?;
	private static final String CAT_PREPARED_DELETE = DELETE_FROM_WITH_BLANK + TABLE_NAME + WHERE_WITH_BLANKS +
			COLUMN_NAME_PRIMARY_KEY_CAT_ID + EQUALS_WITH_BLANKS + CHAR_QUESTION_MARK + CHAR_SEMICOLON;
	//endregion
	
	//region 1. CREATE
	
	/**
	 * Die Methode fuegt das uebergebene {@link Cat}-Objekt in die DB-Tabelle cat ein.
	 *
	 * @param dbConnection         Verbindung fuer den DB-Zugriff.
	 * @param animalToInsertIntoDb Katze, die gespeichert werden soll.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordIntoDb(Connection dbConnection, AAnimal animalToInsertIntoDb)
			throws ClassCastException {
		
		if (animalToInsertIntoDb instanceof Cat) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(CAT_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				Cat castedCatToInsertIntoDb = (Cat) animalToInsertIntoDb;
				
				preppedInsert.setString(1, castedCatToInsertIntoDb.getIdentity().getName());
				preppedInsert.setString(2, castedCatToInsertIntoDb.getIdentity().getDateOfBirth());
				preppedInsert.setString(3, castedCatToInsertIntoDb.getIdentity().getSex().getSex());
				preppedInsert.setBoolean(4, castedCatToInsertIntoDb.getIdentity().getSex().isNeutered());
				preppedInsert.setString(5, castedCatToInsertIntoDb.getAppearance().getBreed());
				preppedInsert.setString(6, castedCatToInsertIntoDb.getAppearance().getColour());
				preppedInsert.setString(7, castedCatToInsertIntoDb.getShelterStay().getAdmissionDate());
				preppedInsert.setString(8, castedCatToInsertIntoDb.getShelterStay().getAdoptionDate());
				preppedInsert.setString(9, castedCatToInsertIntoDb.getShelterStay().getDescription());
				preppedInsert.setBoolean(10, castedCatToInsertIntoDb.isOutdoorCat());
				
				preppedInsert.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode fuegt die uebergebene {@link List}e von {@link Cat}-Objekt in die DB-Tabelle cat ein.
	 *
	 * @param dbConnection          Verbindung fuer den DB-Zugriff.
	 * @param animalsToInsertIntoDb Katzen, die gespeichert werden sollen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void insertDataRecordsIntoDb(Connection dbConnection, List<? extends AAnimal> animalsToInsertIntoDb)
			throws ClassCastException {
		
		if (animalsToInsertIntoDb.get(0) instanceof Cat) {
			
			try (PreparedStatement preppedInsert = dbConnection.prepareStatement(CAT_PREPARED_INSERT)) {
				
				//EXPLIZITER CAST
				List<Cat> castedCatsToInsertIntoDb = (List<Cat>) animalsToInsertIntoDb;
				
				for (Cat cat : castedCatsToInsertIntoDb) {
					
					preppedInsert.setString(1, cat.getIdentity().getName());
					preppedInsert.setString(2, cat.getIdentity().getDateOfBirth());
					preppedInsert.setString(3, cat.getIdentity().getSex().getSex());
					preppedInsert.setBoolean(4, cat.getIdentity().getSex().isNeutered());
					preppedInsert.setString(5, cat.getAppearance().getBreed());
					preppedInsert.setString(6, cat.getAppearance().getColour());
					preppedInsert.setString(7, cat.getShelterStay().getAdmissionDate());
					preppedInsert.setString(8, cat.getShelterStay().getAdoptionDate());
					preppedInsert.setString(9, cat.getShelterStay().getDescription());
					preppedInsert.setBoolean(10, cat.isOutdoorCat());
					
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
	 * Die Funktion liest alle Eintraege der Tabelle cat aus und gibt sie als
	 * {@link List}e von {@link Cat}-Objekten zurueck.
	 *
	 * @param dbConnection {@link Connection} Verbindung fuer den DB-Zugriff.
	 *
	 * @return catsFromDb - Liste mit Katzen aus der DB oder leere Liste,
	 * wenn keine Eintraege vorhanden sind oder die DB-Verbindung fehlschlaegt.
	 */
	
	protected List<Cat> getAllDataRecordsFromDb(Connection dbConnection) {
		
		List<Cat> allCatsFromDb = new ArrayList<>();
		Cat       cat;
		
		try (Statement statement = dbConnection.createStatement();
		     ResultSet resultSet = statement.executeQuery(CAT_SELECT_ALL)) {
			
			while (resultSet.next()) {
				
				cat = getModelFromResultSet(resultSet);
				
				allCatsFromDb.add(cat);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allCatsFromDb;
	}
	
	/**
	 * Die Funktion liest dem Eintrag mit der uebergebenen ID aus der Tabelle cat aus
	 * und gibt ihn als {@link Cat} zurueck. Wurde kein Eintrag gefunden wird null
	 * zurueckgegeben.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId          DB-ID der auszulesenden Katze.
	 *
	 * @return catFromDbById - gefundene Katze oder null.
	 */
	
	protected Cat getDataRecordFromDbById(Connection dbConnection, int iId) {
		
		Cat cat = null;
		
		try (PreparedStatement preppedSelect = dbConnection.prepareStatement(CAT_PREPARED_SELECT_WHERE_CAT_ID)) {
			
			preppedSelect.setInt(1, iId);
			
			try (ResultSet resultSet = preppedSelect.executeQuery()) {
				
				if (resultSet.next()) {
					
					cat = getModelFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cat;
	}
	//endregion
	
	//region 3. UPDATE
	
	/**
	 * Die Methode aendert das uebergebene {@link Cat}-Objekt in der Tabelle cat.
	 *
	 * @param dbConnection       Verbindung fuer den DB-Zugriff.
	 * @param animalToUpdateInDb Katze mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordInDb(Connection dbConnection, AAnimal animalToUpdateInDb)
			throws ClassCastException {
		
		if (animalToUpdateInDb instanceof Cat) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(CAT_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				Cat castedCatToUpdateInDb = (Cat) animalToUpdateInDb;
				
				preppedUpdate.setString(1, castedCatToUpdateInDb.getIdentity().getName());
				preppedUpdate.setString(2, castedCatToUpdateInDb.getIdentity().getDateOfBirth());
				preppedUpdate.setString(3, castedCatToUpdateInDb.getIdentity().getSex().getSex());
				preppedUpdate.setBoolean(4, castedCatToUpdateInDb.getIdentity().getSex().isNeutered());
				preppedUpdate.setString(5, castedCatToUpdateInDb.getAppearance().getBreed());
				preppedUpdate.setString(6, castedCatToUpdateInDb.getAppearance().getColour());
				preppedUpdate.setString(7, castedCatToUpdateInDb.getShelterStay().getAdmissionDate());
				preppedUpdate.setString(8, castedCatToUpdateInDb.getShelterStay().getAdoptionDate());
				preppedUpdate.setString(9, castedCatToUpdateInDb.getShelterStay().getDescription());
				preppedUpdate.setBoolean(10, castedCatToUpdateInDb.isOutdoorCat());
				preppedUpdate.setInt(11, castedCatToUpdateInDb.getIdentity().getId());
				
				preppedUpdate.executeUpdate();
				
			} catch (SQLException | ClassCastException e) {
				e.printStackTrace();
			}
			
		} else {
			throw new ClassCastException(AppTexts.WARNING_WRONG_INPUT_TYPE);
		}
	}
	
	/**
	 * Die Methode aendert die uebergebene {@link List}e von {@link Cat}-Objekten in der Tabelle cat.
	 *
	 * @param dbConnection        Verbindung fuer den DB-Zugriff.
	 * @param animalsToUpdateInDb Liste von Katzen mit geaenderten Attributen.
	 *
	 * @throws ClassCastException wenn das uebergebene Objekt nicht dem geforderten Typ entspricht.
	 */
	
	protected void updateDataRecordsInDb(Connection dbConnection, List<? extends AAnimal> animalsToUpdateInDb)
			throws ClassCastException {
		
		if (animalsToUpdateInDb.get(0) instanceof Cat) {
			
			try (PreparedStatement preppedUpdate = dbConnection.prepareStatement(CAT_PREPARED_UPDATE)) {
				
				//EXPLIZITER CAST
				List<Cat> castedCatsToUpdateInDb = (List<Cat>) animalsToUpdateInDb;
				
				for (Cat cat : castedCatsToUpdateInDb) {
					
					preppedUpdate.setString(1, cat.getIdentity().getName());
					preppedUpdate.setString(2, cat.getIdentity().getDateOfBirth());
					preppedUpdate.setString(3, cat.getIdentity().getSex().getSex());
					preppedUpdate.setBoolean(4, cat.getIdentity().getSex().isNeutered());
					preppedUpdate.setString(5, cat.getAppearance().getBreed());
					preppedUpdate.setString(6, cat.getAppearance().getColour());
					preppedUpdate.setString(7, cat.getShelterStay().getAdmissionDate());
					preppedUpdate.setString(8, cat.getShelterStay().getAdoptionDate());
					preppedUpdate.setString(9, cat.getShelterStay().getDescription());
					preppedUpdate.setBoolean(10, cat.isOutdoorCat());
					preppedUpdate.setInt(11, cat.getIdentity().getId());
					
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
	 * Die Methode loescht die Katze mit der uebergebenen ID aus der Tabelle cat.
	 *
	 * @param dbConnection Verbindung fuer den DB-Zugriff.
	 * @param iId          DB-ID der zu loeschenden Katze.
	 */
	
	protected void deleteDataRecordInDb(Connection dbConnection, int iId) {
		
		try (PreparedStatement preppedDelete = dbConnection.prepareStatement(CAT_PREPARED_DELETE)) {
			
			preppedDelete.setInt(1, iId);
			
			preppedDelete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//endregion
	
	//region 5. Model aus ResultSet erzeugen
	
	/**
	 * Die Funktion generiert aus dem uebergebenen {@link ResultSet} ein {@link Cat}-
	 * Objekt und gibt es zurueck. Dabei werden die Attribute unabhaengig von der Reihenfolge
	 * der Spalten im {@link ResultSet} korrekt gesetzt.
	 *
	 * @param currentResultSet ResultSet, das die zu erzeugende Katze enthaelt.
	 *
	 * @return catFromResultSet - erzeugtes Katzen-Objekt.
	 *
	 * @throws SQLException wenn ein Fehler bei der Verbindung zur oder beim Zugriff auf die DB auftritt.
	 */
	
	protected Cat getModelFromResultSet(ResultSet currentResultSet) throws SQLException {
		
		final int iColumnIndexCatId            = currentResultSet.findColumn(COLUMN_NAME_PRIMARY_KEY_CAT_ID);
		final int iColumnIndexCatName          = currentResultSet.findColumn(COLUMN_NAME_CAT_NAME);
		final int iColumnIndexCatDateOfBirth   = currentResultSet.findColumn(COLUMN_NAME_CAT_DATE_OF_BIRTH);
		final int iColumnIndexCatSex           = currentResultSet.findColumn(COLUMN_NAME_CAT_SEX);
		final int iColumnIndexCatIsNeutered    = currentResultSet.findColumn(COLUMN_NAME_CAT_IS_NEUTERED);
		final int iColumnIndexCatBreed         = currentResultSet.findColumn(COLUMN_NAME_CAT_BREED);
		final int iColumnIndexCatColour        = currentResultSet.findColumn(COLUMN_NAME_CAT_COLOUR);
		final int iColumnIndexCatAdmissionDate = currentResultSet.findColumn(COLUMN_NAME_CAT_ADMISSION_DATE);
		final int iColumnIndexCatAdoptionDate  = currentResultSet.findColumn(COLUMN_NAME_CAT_ADOPTION_DATE);
		final int iColumnIndexCatDescription   = currentResultSet.findColumn(COLUMN_NAME_CAT_DESCRIPTION);
		final int iColumnIndexCatIsOutdoorCat  = currentResultSet.findColumn(COLUMN_NAME_CAT_IS_OUTDOOR_CAT);
		
		final int     iCatId              = currentResultSet.getInt(iColumnIndexCatId);
		final String  strCatName          = currentResultSet.getString(iColumnIndexCatName);
		final String  strCatDateOfBirth   = currentResultSet.getString(iColumnIndexCatDateOfBirth);
		final String  strCatSex           = currentResultSet.getString(iColumnIndexCatSex);
		final boolean catIsNeutered       = currentResultSet.getBoolean(iColumnIndexCatIsNeutered);
		final String  strCatBreed         = currentResultSet.getString(iColumnIndexCatBreed);
		final String  strCatColour        = currentResultSet.getString(iColumnIndexCatColour);
		final String  strCatAdmissionDate = currentResultSet.getString(iColumnIndexCatAdmissionDate);
		final String  strCatAdoptionDate  = currentResultSet.getString(iColumnIndexCatAdoptionDate);
		final String  strCatDescription   = currentResultSet.getString(iColumnIndexCatDescription);
		final boolean catIsOutdoorCat     = currentResultSet.getBoolean(iColumnIndexCatIsOutdoorCat);
		
		Sex         sex         = new Sex(strCatSex, catIsNeutered);
		Identity    identity    = new Identity(iCatId, strCatName, strCatDateOfBirth, sex);
		Appearance  appearance  = new Appearance(strCatBreed, strCatColour);
		ShelterStay shelterStay = new ShelterStay(strCatAdmissionDate, strCatAdoptionDate, strCatDescription);
		
		return new Cat(identity, appearance, shelterStay, catIsOutdoorCat);
	}
	
	//endregion
}
