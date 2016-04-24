package views;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.MainStageContainer;
import models.Scout;
import models.TreeLotCoordinator;
import userinterface.MessageView;

// project imports
import impresario.IModel;

/** The class containing the Teller View for the ATM application */
// ==============================================================
public class WelcomeView extends View {

	// GUI stuff

	private Button englishButton;
	private Button frenchButton;
	private final Locale myLocale = new Locale("en", "US");
	private final ResourceBundle myResourceBundle = ResourceBundle.getBundle("locale/WelcomeView", myLocale);
	private final String english = new String(myResourceBundle.getString("english"));
	private final String french = new String(myResourceBundle.getString("french"));
	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public WelcomeView(IModel tlc) {

		super(tlc, "WelcomeView");
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe("LoginError", this);
	}


	// -------------------------------------------------------------
	private VBox createTitle() {

		VBox title = new VBox(15);
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(25, 25, 25, 25));

		Text titleText = new Text(english);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		Text titleTextfr = new Text(french);
		titleTextfr.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleTextfr.setTextAlignment(TextAlignment.CENTER);
		titleTextfr.setFill(Color.DARKOLIVEGREEN);
		
		
		title.getChildren().addAll(titleText, titleTextfr);
		return title;
	}
	// Create the main form contents
	// -------------------------------------------------------------
	private GridPane createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		englishButton = new Button("English");

		
		englishButton.setOnAction(e -> {	
			myModel.stateChangeRequest("SetLocale", "English");
		});
		
		grid.add(englishButton, 0, 0);

		frenchButton = new Button("Francais");
		
		frenchButton.setOnAction(e -> {	
			myModel.stateChangeRequest("SetLocale", "French");
		});
		
		grid.add(frenchButton, 0, 3);

		return grid;
	}

	// Create the status log field
	// -------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// -------------------------------------------------------------

	public void processAction(Event evt) {
		// DEBUG: System.out.println("TellerView.actionPerformed()");

	}

	/**
	 * Process userid and pwd supplied when Submit button is hit. Action is to
	 * pass this info on to the teller object
	 */
	// ----------------------------------------------------------

	public void updateState(String key, Object value) {
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true) {
			// display the passed text
			displayErrorMessage((String) value);
		}

	}

	/**
	 * Display error message
	 */
	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

}