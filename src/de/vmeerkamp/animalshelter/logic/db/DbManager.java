package de.vmeerkamp.animalshelter.logic.db;

import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Cat;
import de.vmeerkamp.animalshelter.model.Dog;
import de.vmeerkamp.animalshelter.model.Rabbit;
import de.vmeerkamp.animalshelter.settings.AppTexts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Diese Klasse ist fuer den Zugriff auf die Datenbank zustaendig. Der Datenbankzugriff ist threadsicher.
 * <p>
 * Diese Klasse ist ein Singleton, da sie selbst ihre einzige Instanz
 * beim ersten Gebrauch anlegt und auch ansonsten nur diese zurueckliefert.<br><br>
 * <b>Die von dieser Klasse hergestellte DB-Verbindung muss nach der Nutzung geschlossen werden.
 * Bei Nutzung der Methoden:<br>
 * <ul>
 *     <li>{@link DbManager#insertObjectIntoDb(AAnimal)}</li>
 *     <li>{@link DbManager#insertObjectsIntoDb(List)}</li>
 *     <li>{@link DbManager#getAllObjectsFromDb(int)}</li>
 *     <li>{@link DbManager#getObjectFromDbById(int, int)}</li>
 *     <li>{@link DbManager#updateObjectInDb(AAnimal)}</li>
 *     <li>{@link DbManager#updateObjectsInDb(List)}</li>
 *     <li>{@link DbManager#deleteObjectInDb(int, int)}</li>
 * </ul><br>
 * wird die Verbindung automatisch geschlossen.</b>
 */

public class DbManager implements AutoCloseable {
	
	//region 0. Konstanten
	
	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	
	private static final String DB_LOCAL_SERVER_IP_ADDRESS = "127.0.0.1";
	private static final String DB_LOCAL_NAME              = "/animalshelter";
	private static final String DB_LOCAL_USER_NAME         = "root";
	private static final String DB_LOCAL_USER_PW           = "";
	
	private static final String DB_LOCAL_CONNECTION_URL =
			"jdbc:mariadb://" + DB_LOCAL_SERVER_IP_ADDRESS + DB_LOCAL_NAME;
	
	public static final int CMD_CAT    = 0;
	public static final int CMD_DOG    = 1;
	public static final int CMD_RABBIT = 2;
	//endregion
	
	//region 1. Klassenvariablen
	
	/**
	 * Verbindung zur Datenbank.
	 */
	private Connection dbConnection;
	
	/**
	 * Einzige Instanz, die von dieser Klasse existieren wird. Wird in der
	 * {@link DbManager#getInstance()}-Funktion einmal generiert.
	 */
	private static DbManager instance;
	
	/**
	 * Zugriff auf die DB-Tabelle Bill.
	 */
	private final DaoCat    daoCat;
	private final DaoDog    daoDog;
	private final DaoRabbit daoRabbit;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Privater Konstruktor. Das Erzeugen einer Instanz von ausserhalb der Klasse
	 * ist somit nicht moeglich.
	 * <p>
	 * Im Konstruktor wird der Datenbanktreiber geladen und die Verbindung zur
	 * Datenbank hergestellt.
	 */
	
	private DbManager() {
		
		daoCat    = new DaoCat();
		daoDog    = new DaoDog();
		daoRabbit = new DaoRabbit();
		
		try {
			Class.forName(JDBC_DRIVER);
			
			dbConnection = DriverManager.getConnection(DB_LOCAL_CONNECTION_URL, DB_LOCAL_USER_NAME, DB_LOCAL_USER_PW);
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println(AppTexts.WARNING_NO_DRIVER_CLASS);
			classNotFoundException.printStackTrace();
		}
	}
	//endregion
	
	//region 3. getInstance
	
	/**
	 * Die Funktion erzeugt beim ersten Aufruf eine Instanz dieser Klasse und liefert diese
	 * zurueck. Ist bei weiteren Aufrufen schon eine Instanz vorhanden, wird keine weitere mehr
	 * angelegt, sondern nur die vorhandene zurueckgeliefert.
	 * Die Funktionen und Methoden dieser Klasse koennen somit sowohl threadsicher,
	 * als auch zugriffssicher genutzt werden.
	 *
	 * @return dbManager - einzige Instanz dieser Klasse zur Laufzeit.
	 */
	
	public static synchronized DbManager getInstance() {
		
		if (instance == null) {
			
			instance = new DbManager();
		}
		
		return instance;
	}
	//endregion
	
	//region 4. CREATE
	
	/**
	 * Die Methode fuegt das uebergebene Objekt in die DB ein und schließt die
	 * erzeugte DB-Verbindung wieder.
	 * Das Einfuegen erfolgt abhaengig von der Klasse des uebergebenen Tieres durch
	 * die Methoden:
	 * <ul>
	 *     <li>{@link DaoCat#insertDataRecordIntoDb(Connection, AAnimal)}</li>
	 *     <li>{@link DaoDog#insertDataRecordIntoDb(Connection, AAnimal)}</li>
	 *     <li>{@link DaoRabbit#insertDataRecordIntoDb(Connection, AAnimal)}</li>
	 * </ul>
	 *
	 * @param aAnimal In die Datenbank einzufuegendes {@link AAnimal}.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public void insertObjectIntoDb(AAnimal aAnimal) throws SQLException{
		
		try {
			
			if (dbConnection.isValid(5)) {
				
				if (aAnimal instanceof Cat) {
					daoCat.insertDataRecordIntoDb(dbConnection, aAnimal);
				} else if (aAnimal instanceof Dog) {
					daoDog.insertDataRecordIntoDb(dbConnection, aAnimal);
				} else if (aAnimal instanceof Rabbit) {
					daoRabbit.insertDataRecordIntoDb(dbConnection, aAnimal);
				} else {
					throw new NoSuchMethodException(AppTexts.WARNING_NO_SUCH_METHOD);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	/**
	 * Die Methode fuegt die uebergebene {@link List}e von {@link AAnimal}s in die DB ein
	 * und schließt die erzeugte DB-Verbindung wieder.
	 * Das Einfuegen erfolgt abhaengig von der Klasse des uebergebenen Tieres durch
	 * die Methoden:
	 * <ul>
	 *     <li>{@link DaoCat#insertDataRecordsIntoDb(Connection, List)}</li>
	 *     <li>{@link DaoDog#insertDataRecordsIntoDb(Connection, List)}</li>
	 *     <li>{@link DaoRabbit#insertDataRecordsIntoDb(Connection, List)}</li>
	 * </ul>
	 *
	 * @param aAnimals Liste von in die Datenbank einzufuegenden Objekten.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public void insertObjectsIntoDb(List<? extends AAnimal> aAnimals) throws SQLException{
		
		try {
			
			if (dbConnection.isValid(5)) {
				
				if (aAnimals.get(0) instanceof Cat) {
					daoCat.insertDataRecordsIntoDb(dbConnection, aAnimals);
				} else if (aAnimals.get(0) instanceof Dog) {
					daoDog.insertDataRecordsIntoDb(dbConnection, aAnimals);
				} else if (aAnimals.get(0) instanceof Rabbit) {
					daoRabbit.insertDataRecordsIntoDb(dbConnection, aAnimals);
				} else {
					throw new NoSuchMethodException(AppTexts.WARNING_NO_SUCH_METHOD);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	//endregion
	
	//region 5. READ
	
	/**
	 * Die Funktion liest alle Eintraege der Tabelle aus, die mit dem uebergebenen
	 * Befehl ausgewaehlt wurde, und gibt sie als Liste zurück. Wurde keine gueltige
	 * Tabelle ausgewaehlt, wird eine {@link NoSuchMethodException} geworfen und null
	 * zurueckgegeben. Abschliessend wird die erzeugte DB-Verbindung wieder geschlossen.
	 *
	 * @param iCmdTable Befehl fuer gewaehlte Tabelle.
	 *
	 * @return allObjectsFromDb - Liste aller Objekte aus der DB.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public List getAllObjectsFromDb(int iCmdTable) throws SQLException{
		
		try {
			
			if (dbConnection.isValid(5)) {
				
				switch (iCmdTable) {
					
					case CMD_CAT -> {
						return daoCat.getAllDataRecordsFromDb(dbConnection);
					}
					case CMD_DOG -> {
						return daoDog.getAllDataRecordsFromDb(dbConnection);
					}
					case CMD_RABBIT -> {
						return daoRabbit.getAllDataRecordsFromDb(dbConnection);
					}
					default -> throw new NoSuchMethodException(AppTexts.WARNING_NO_VALID_TABLE_SELECTED);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
		
		return null;
	}
	
	/**
	 * Die Funktion liest den Eintrag mit der uebergebenen ID aus der Tabelle aus,
	 * die mit dem uebergebenen Befehl ausgewaehlt wurde, und gibt das Objekt zurueck.
	 * Wurde keine gueltige Tabelle ausgewaehlt wird  eine {@link NoSuchMethodException}
	 * geworfen und null zurueckgegeben. Abschliessend wird die erzeugte DB-Verbindung
	 * wieder geschlossen.
	 *
	 * @param iCmdTable Befehl fuer gewaehlte Tabelle.
	 * @param iId ID des gewaehlten Objektes.
	 *
	 * @return objectFromDbById - Objekt aus der DB.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public AAnimal getObjectFromDbById(int iCmdTable, int iId) throws SQLException{
		
		try {
			
			if (dbConnection.isValid(5)) {
				
				switch (iCmdTable) {
					case CMD_CAT -> {
						return daoCat.getDataRecordFromDbById(dbConnection, iId);
					}
					case CMD_DOG -> {
						return daoDog.getDataRecordFromDbById(dbConnection, iId);
					}
					case CMD_RABBIT -> {
						return daoRabbit.getDataRecordFromDbById(dbConnection, iId);
					}
					default -> throw new NoSuchMethodException(AppTexts.WARNING_NO_VALID_TABLE_SELECTED);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return null;
	}
	//endregion
	
	//region 6. UPDATE
	
	/**
	 * Die Methode aendert das uebergebene Objekt in der DB und schließt die
	 * erzeugte DB-Verbindung wieder.
	 *
	 * @param aAnimal Objekt mit geaenderten Attributen.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public void updateObjectInDb(AAnimal aAnimal) throws SQLException {
		
		try {
			if (dbConnection.isValid(5)) {
				
				if (aAnimal instanceof Cat) {
					daoCat.updateDataRecordInDb(dbConnection, aAnimal);
				} else if (aAnimal instanceof Dog) {
					daoDog.updateDataRecordInDb(dbConnection, aAnimal);
				} else if (aAnimal instanceof Rabbit) {
					daoRabbit.updateDataRecordInDb(dbConnection, aAnimal);
				} else {
					throw new NoSuchMethodException(AppTexts.WARNING_NO_SUCH_METHOD);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	/**
	 * Die Methode aendert die uebergebene {@link List}e von Objekten in der DB und schließt die
	 * erzeugte DB-Verbindung wieder.
	 *
	 * @param aAnimals {@link AAnimal}s mit geaenderten Attributen.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public void updateObjectsInDb(List<? extends AAnimal> aAnimals) throws SQLException{
		
		try {
			if (dbConnection.isValid(5)) {
				
				if (aAnimals.get(0) instanceof Cat) {
					daoCat.updateDataRecordsInDb(dbConnection, aAnimals);
				} else if (aAnimals.get(0) instanceof Dog) {
					daoDog.updateDataRecordsInDb(dbConnection, aAnimals);
				} else if (aAnimals.get(0) instanceof Rabbit) {
					daoRabbit.updateDataRecordsInDb(dbConnection, aAnimals);
				} else {
					throw new NoSuchMethodException(AppTexts.WARNING_NO_SUCH_METHOD);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	//endregion
	
	//region 7. DELETE
	
	/**
	 * Die Methode loescht das Objekt mit der uebergebenen ID aus der uebergebenen Tabelle.
	 * Wurde keine gueltige Tabelle ausgewaehlt wird  eine {@link NoSuchMethodException}
	 * geworfen. Abschliessend wird die erzeugte DB-Verbindung wieder geschlossen.
	 *
	 * @param iCmdTable Befehl fuer gewaehlte Tabelle.
	 * @param iId ID des gewaehlten Tieres.
	 *
	 * @throws SQLException wenn die Verbindung zur Datenbank nicht moeglich ist.
	 */
	
	public void deleteObjectInDb(int iCmdTable, int iId) throws SQLException{
		
		try {
			
			if (dbConnection.isValid(5)) {
				
				switch (iCmdTable) {
					case CMD_CAT -> daoCat.deleteDataRecordInDb(dbConnection, iId);
					case CMD_DOG -> daoDog.deleteDataRecordInDb(dbConnection, iId);
					case CMD_RABBIT -> daoRabbit.deleteDataRecordInDb(dbConnection, iId);
					default -> throw new NoSuchMethodException(AppTexts.WARNING_NO_VALID_TABLE_SELECTED);
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	//endregion
	
	//region 8. Datenbankverbindung schließen
	
	/**
	 * Die Methode schliesst die {@link Connection} zur Datenbank,
	 * solange sie nicht schon null ist und setzt die Instanz der Klasse
	 * auf null.
	 */
	
	@Override
	public void close() {
		
		try {
			
			if (dbConnection != null) {
				
				dbConnection.close();
				
				instance = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//endregion
}
