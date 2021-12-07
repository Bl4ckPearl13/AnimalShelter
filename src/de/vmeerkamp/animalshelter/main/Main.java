package de.vmeerkamp.animalshelter.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Startet die Applikation.
 */

public class Main extends Application {
    
    //region 0. Attribute
    public static Stage mainStage;
    //endregion
    
    //region 1. Start
    
    /**
     * Der Haupteinstiegspunkt für alle JavaFX-Anwendungen. Die start-Methode wird aufgerufen,
     * nachdem die init-Methode abgeschlossen ist und nachdem das System bereit ist, dass die
     * Anwendung mit der Ausführung beginnt.
     * HINWEIS: Diese Methode wird auf dem JavaFX Application Thread aufgerufen.
     *
     * @param primaryStage Die primäre {@link Stage} für diese Anwendung, auf die die Anwendungsszene
     *                     gesetzt werden kann. Anwendungen können bei Bedarf andere Stages erstellen,
     *                     aber sie sind keine primären Stages.
     *
     * @throws IOException
     */

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/cat_layout.fxml")));
    
        mainStage = primaryStage;
        
        mainStage.setTitle("HappyPaws Tierheim Verwaltung");
        mainStage.setScene(new Scene(root, 1280, 948));
        mainStage.setResizable(false);
        mainStage.show();
    }
    //endregion
    
    //region 2. Main
    
    /**
     * Einstiegspunkt in die Applikation.
     *
     * @param args Magie
     */
    
    public static void main(String[] args) {
        launch(args);
    }
    //endregion
}
