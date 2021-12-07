package de.vmeerkamp.animalshelter.gui;

import de.vmeerkamp.animalshelter.model.AAnimal;
import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

/**
 * Die Klasse repraesentiert eine Zelle in der ListView und uebernimmt die Formatierung
 * des Zellinhalts.
 */

public class ListViewCell extends ListCell<AAnimal> {
	
	/**
	 * Zeigt entweder nichts an oder den Namen des {@link AAnimal}, das mitgeliefert wird.
	 * Dieses kommt dann von der {@link javafx.scene.control.ListView} selbst.
	 *
	 * @param aAnimalToShowInCell {@link AAnimal}, das angezeigt werden soll.
	 * @param empty gibt an ob die Zelle leer sein soll.
	 */
	
	@Override
	protected void updateItem(AAnimal aAnimalToShowInCell, boolean empty) {
		super.updateItem(aAnimalToShowInCell, empty);
		
		if (empty || aAnimalToShowInCell == null) {
			this.setText(null);
			this.setGraphic(null);
		} else {
			this.setFont(Font.font(16D));
			this.setText(aAnimalToShowInCell.getIdentity().getName());
		}
	}
}
