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
	}


	// -------------------------------------------------------------
	private VBox createTitle() {

		VBox title = new VBox(15);
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(50, 25, 25, 25));

		Text titleText = new Text(english);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		Text titleTextfr = new Text(french);
		titleTextfr.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleTextfr.setTextAlignment(TextAlignment.CENTER);
		titleTextfr.setFill(Color.DARKOLIVEGREEN);
		
		
		title.getChildren().addAll(titleText, titleTextfr);
		return title;
	}
	// Create the main form contents
	// -------------------------------------------------------------
	private HBox createFormContents() {
		
		HBox hbox = new HBox(30);
		hbox.setPadding(new Insets(50, 25, 100, 25));
		hbox.setAlignment(Pos.CENTER);
		
		englishButton = new Button("English");
		englishButton.setStyle("-fx-font: 22 arial;");
		
		englishButton.setOnAction(e -> {	
			myModel.stateChangeRequest("SetLocale", "English");
		});
		
		frenchButton = new Button("Francais");
		frenchButton.setStyle("-fx-font: 22 arial;");
		frenchButton.setOnAction(e -> {	
			myModel.stateChangeRequest("SetLocale", "French");
		});
		
		hbox.getChildren().addAll(englishButton, frenchButton);
		
		return hbox;
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