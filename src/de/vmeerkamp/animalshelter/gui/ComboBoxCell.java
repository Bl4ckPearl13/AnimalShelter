package de.vmeerkamp.animalshelter.gui;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

/**
 * Die Klasse repraesentiert eine Zelle in der ComboBox und uebernimmt die Formatierung
 * des Zellinhalts.
 */

public class ComboBoxCell extends ListCell<String> {
	
	/**
	 * Zeigt entweder nichts an oder den mitgelieferten {@link String}.
	 * Dieser kommt dann von der {@link javafx.scene.control.ComboBox} selbst.
	 *
	 * @param strChoice String, der angezeigt werden soll.
	 * @param empty gibt an ob die Zelle leer sein soll.
	 */
	
	@Override
	protected void updateItem(String strChoice, boolean empty) {
		super.updateItem(strChoice, empty);
		
		if (empty || strChoice == null) {
			setText(null);
		} else {
			setFont(Font.font(16D));
			setText(strChoice);
		}
	}
}
