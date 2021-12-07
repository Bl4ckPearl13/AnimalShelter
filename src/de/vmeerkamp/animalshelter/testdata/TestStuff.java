package de.vmeerkamp.animalshelter.testdata;

import de.vmeerkamp.animalshelter.logic.export.CsvFileHandler;
import de.vmeerkamp.animalshelter.model.Cat;
import de.vmeerkamp.animalshelter.model.Dog;
import de.vmeerkamp.animalshelter.model.Rabbit;
import java.util.List;

/**
 * Testklasse
 */

public class TestStuff {
	
	/**
	 * Einstiegspunkt in die Tests.
	 *
	 * @param args Magie
	 */
	
	public static void main(String[] args) {
		
		List<Cat> catListFromFile = (List<Cat>) CsvFileHandler.getInstance().readAnimalsFromCsvFile(CsvFileHandler.CMD_CAT);

		for (Cat cat : catListFromFile) {
			System.out.println(cat);
		}

		List<Dog> dogListFromFile = (List<Dog>) CsvFileHandler.getInstance().readAnimalsFromCsvFile(CsvFileHandler.CMD_DOG);

		for (Dog dog : dogListFromFile) {
			System.out.println(dog);
		}

		List<Rabbit> rabbitListFromFile = (List<Rabbit>) CsvFileHandler.getInstance().readAnimalsFromCsvFile(CsvFileHandler.CMD_RABBIT);

		for (Rabbit rabbit : rabbitListFromFile) {
			System.out.println(rabbit);
		}
	}
}
