package models;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import userinterface.WindowPosition;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	//private Librarian theLibrarian;
	private TreeLotCoordinator tlc;
	
	private Stage mainStage;

	public void start(Stage primaryStage) {

		MainStageContainer.setStage(primaryStage, "Tree Sales System");
		mainStage = MainStageContainer.getInstance();

		mainStage.setOnCloseRequest(new EventHandler<javafx.stage.WindowEvent>() {
			@Override
			public void handle(javafx.stage.WindowEvent event) {
				System.exit(0);
			}
		});

		try {
			tlc = new TreeLotCoordinator();
		} catch (Exception e) {
			System.out.println("Could not create TLC View");
			e.printStackTrace();
		}

		WindowPosition.placeCenter(mainStage);
		mainStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
