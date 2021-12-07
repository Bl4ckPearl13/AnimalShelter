package de.vmeerkamp.animalshelter.logic.export;

import de.vmeerkamp.animalshelter.model.AAnimal;
import de.vmeerkamp.animalshelter.model.Cat;
import de.vmeerkamp.animalshelter.model.Dog;
import de.vmeerkamp.animalshelter.model.Rabbit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse dient dem Export der Daten des Programmes in mehrere
 * CSV-Dateien.<br><br>
 * Diese Klasse ist ein threadsicherer <b>Singleton</b> und gewaehrleistet
 * somit einen thread- und zugriff-sicheren Dateizugriff.
 */

public class CsvFileHandler {
	
	//region 0. Konstanten
	
	private static final String CSV_FILE_PATH_CAT    = "src/de/vmeerkamp/animalshelter/resources/csv/cats.csv";
	private static final String CSV_FILE_PATH_DOG    = "src/de/vmeerkamp/animalshelter/resources/csv/dogs.csv";
	private static final String CSV_FILE_PATH_RABBIT = "src/de/vmeerkamp/animalshelter/resources/csv/rabbits.csv";
	
	public static final int CMD_CAT    = 0;
	public static final int CMD_DOG    = 1;
	public static final int CMD_RABBIT = 2;
	
	//endregion
	
	//region 1. Klassenvariablen
	
	/**
	 * Einzige Instanz der Klasse. Wird einmalig generiert und ansonsten nur noch
	 * zurueckgeliefert.
	 */
	private static CsvFileHandler instance;
	//endregion
	
	//region 2. Konstruktor
	
	/**
	 * Privater Konstruktor. Das Anlegen einer Instanz von ausserhalb der Klasse ist
	 * somit nicht moeglich.
	 */
	
	private CsvFileHandler() {
	}
	//endregion
	
	//region 3. Getter und Setter
	
	/**
	 * Die Funktion erzeugt beim ersten Aufruf eine Instanz dieser Klasse und liefert
	 * sie ansonsten nur noch zurueck.
	 *
	 * @return instance - Einzige Instanz dieser Klasse.
	 */
	
	public static synchronized CsvFileHandler getInstance() {
		if (instance == null) {
			instance = new CsvFileHandler();
		}
		return instance;
	}
	//endregion
	
	//region 4. SAVE
	
	/**
	 * Die Methode nimmt eine {@link List}e von Tieren entgegen und fuehrt
	 * entsprechend des Typs der Liste die richtige Methode zum Speichern
	 * der Liste in der zugehoerigen CSV-Datei aus.
	 *
	 * @param animalList Liste der Tiere, die gespeichert werden sollen.
	 *
	 * @return success - true, wenn das Speichern erfolgreich war, sonst false
	 */
	
	public boolean saveAnimalsToCsvFile(List<? extends AAnimal> animalList) {
		
		if (animalList.get(0) instanceof Cat) {
			saveCatsToCsvFile((List<Cat>) animalList);
			return true;
		}
		
		if (animalList.get(0) instanceof Dog) {
			saveDogsToCsvFile((List<Dog>) animalList);
			return true;
		}
		
		if (animalList.get(0) instanceof Rabbit) {
			saveRabbitsToCsvFile((List<Rabbit>) animalList);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Die Methode speichert die uebergebene {@link List}e von {@link Cat}s in einer
	 * CSV-Datei.
	 *
	 * @param catList Liste der Katzen, die gespeichert werden sollen.
	 */
	
	private void saveCatsToCsvFile(List<Cat> catList) {
		
		File csvFile = new File(CSV_FILE_PATH_CAT);
		
		try (FileOutputStream fos = new FileOutputStream(csvFile, false);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     BufferedWriter out = new BufferedWriter(osw)) {
			
			for (Cat cat : catList) {
				out.write(cat.getAllAttributesAsCsvLine());
			}
			
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_CAT);
			e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode speichert die uebergebene {@link List}e von {@link Dog}s in einer
	 * CSV-Datei.
	 *
	 * @param dogList Liste der Hunde, die gespeichert werden sollen.
	 */
	
	private void saveDogsToCsvFile(List<Dog> dogList) {
		File csvFile = new File(CSV_FILE_PATH_DOG);
		
		try (FileOutputStream fos = new FileOutputStream(csvFile, false);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     BufferedWriter out = new BufferedWriter(osw)) {
			
			for (Dog dog : dogList) {
				out.write(dog.getAllAttributesAsCsvLine());
			}
			
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_DOG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Die Methode speichert die uebergebene {@link List}e von {@link Rabbit}s in einer
	 * CSV-Datei.
	 *
	 * @param rabbitList Liste der Kaninchen, die gespeichert werden sollen.
	 */
	
	private void saveRabbitsToCsvFile(List<Rabbit> rabbitList) {
		
		File csvFile = new File(CSV_FILE_PATH_RABBIT);
		
		try (FileOutputStream fos = new FileOutputStream(csvFile, false);
		     OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		     BufferedWriter out = new BufferedWriter(osw)) {
			
			for (Rabbit rabbit : rabbitList) {
				out.write(rabbit.getAllAttributesAsCsvLine());
			}
			
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_RABBIT);
			e.printStackTrace();
		}
	}
	//endregion
	
	//region 5. READ
	
	/**
	 * Die Funktion nimmt einen Befehl in Form eines int entgegen und fuehrt
	 * entsprechend des Befehls eine der folgenden Funktionen aus:
	 * <ul>
	 *     <li>{@link CsvFileHandler#readCatsFromCsvFile()}</li>
	 *     <li>{@link CsvFileHandler#readDogsFromCsvFile()}</li>
	 *     <li>{@link CsvFileHandler#readRabbitsFromCsvFile()}</li>
	 * </ul>
	 *
	 * @param iCmdAnimal Befehl welche Tierliste zurueckgegeben werden soll.
	 *                   Es existieren CMD Konstanten die genutzt werden sollen.
	 *
	 * @return animalList - gewaehlte Tierliste
	 */
	
	public List<? extends AAnimal> readAnimalsFromCsvFile(int iCmdAnimal) {
		
		try {
			
			switch (iCmdAnimal) {
				case CMD_CAT -> {
					return readCatsFromCsvFile();
				}
				case CMD_DOG -> {
					return readDogsFromCsvFile();
				}
				case CMD_RABBIT -> {
					return readRabbitsFromCsvFile();
				}
				default -> throw new NoSuchMethodException("Für diesen Befehl existiert keine Methode.");
			}
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Die Funktion liest die Eintraege aus der cats.csv aus und gibt sie als {@link List}e von
	 * {@link Cat}s zurueck.
	 *
	 * @return catsFromCsvFile - Liste mit allen Katzen aus der CSV-Datei oder leere Liste, wenn
	 * das Lesen fehlschlaegt.
	 */
	
	private List<Cat> readCatsFromCsvFile() {
		
		List<Cat> catListFromFile = new ArrayList<>();
		File      csvFile         = new File(CSV_FILE_PATH_CAT);
		
		try (FileInputStream fis = new FileInputStream(CSV_FILE_PATH_CAT);
		     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		     BufferedReader in = new BufferedReader(isr)) {
			
			if (csvFile.exists()) {
				
				boolean eof = false;
				
				while (!eof) {
					
					String strReadCsvLine = in.readLine();
					
					if (!(strReadCsvLine == null)) {
						
						Cat currentCat = new Cat();
						currentCat.setAllAttributesFromCsvLine(strReadCsvLine);
						
						catListFromFile.add(currentCat);
						
					} else {
						eof = true;
					}
				}
			}
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_CAT);
			e.printStackTrace();
		}
		
		return catListFromFile;
	}
	
	/**
	 * Die Funktion liest die Eintraege aus der dogs.csv aus und gibt sie als {@link List}e von
	 * {@link Dog}s zurueck.
	 *
	 * @return dogsFromCsvFile - Liste mit allen Hunden aus der CSV-Datei oder leere Liste, wenn
	 * das Lesen fehlschlaegt.
	 */
	
	private List<Dog> readDogsFromCsvFile() {
		
		List<Dog> dogListFromFile = new ArrayList<>();
		File      csvFile         = new File(CSV_FILE_PATH_DOG);
		
		try (FileInputStream fis = new FileInputStream(CSV_FILE_PATH_DOG);
		     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		     BufferedReader in = new BufferedReader(isr)) {
			
			if (csvFile.exists()) {
				
				boolean eof = false;
				
				while (!eof) {
					
					String strReadCsvLine = in.readLine();
					
					if (!(strReadCsvLine == null)) {
						
						Dog currentDog = new Dog();
						currentDog.setAllAttributesFromCsvLine(strReadCsvLine);
						
						dogListFromFile.add(currentDog);
						
					} else {
						eof = true;
					}
				}
			}
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_DOG);
			e.printStackTrace();
		}
		
		return dogListFromFile;
	}
	
	/**
	 * Die Funktion liest die Eintraege aus der rabbits.csv aus und gibt sie als {@link List}e von
	 * {@link Rabbit}s zurueck.
	 *
	 * @return rabbitsFromCsvFile - Liste mit allen Kaninchen aus der CSV-Datei oder leere Liste, wenn
	 * das Lesen fehlschlaegt.
	 */
	
	private List<Rabbit> readRabbitsFromCsvFile() {
		
		List<Rabbit> rabbitListFromFile = new ArrayList<>();
		File       csvFile          = new File(CSV_FILE_PATH_RABBIT);
		
		try (FileInputStream fis = new FileInputStream(CSV_FILE_PATH_RABBIT);
		     InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
		     BufferedReader in = new BufferedReader(isr)) {
			
			if (csvFile.exists()) {
				
				boolean eof = false;
				
				while (!eof) {
					
					String strReadCsvLine = in.readLine();
					
					if (!(strReadCsvLine == null)) {
						
						Rabbit currentRabbit = new Rabbit();
						currentRabbit.setAllAttributesFromCsvLine(strReadCsvLine);
						
						rabbitListFromFile.add(currentRabbit);
						
					} else {
						eof = true;
					}
				}
			}
		} catch (IOException e) {
			System.err.println(CSV_FILE_PATH_RABBIT);
			e.printStackTrace();
		}
		
		return rabbitListFromFile;
	}
	//endregion
}
