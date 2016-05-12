package views;

import java.text.NumberFormat;
import java.util.Properties;

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
import models.ScoutCollection;
import models.TreeLotCoordinator;
import userinterface.MessageView;
import views.View;
// project imports
import impresario.IModel;

/** The class containing the Teller View for the ATM application */
// ==============================================================
public class SearchScoutNameView extends View {

	// GUI stuff

    protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButton"));
    protected final String cancelButtonLabel = new String(myResourceBundle.getString("doneButton"));
	protected final String titleTextLabel = new String(myResourceBundle.getString("titleText"));
	protected final String firstNameLabel = new String(myResourceBundle.getString("firstNameLabel"));
	protected final String lastNameLabel = new String(myResourceBundle.getString("lastNameLabel"));
	protected final String instructions = new String(myResourceBundle.getString("instructions"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
			
	
	private Button submitButton;
	private Button cancelButton;
	private TextField firstName;
	private TextField lastName;

	private IModel myTLC;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public SearchScoutNameView(IModel tlc) {

		super(tlc, "SearchScoutNameView");

		myTLC = tlc;

		// create a container for showing the contents
		VBox container = new VBox(10);

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

	// Create the label (Text) for the title of the screen
	// -------------------------------------------------------------
	private VBox createTitle() {

		VBox myVbox = new VBox(15);
		myVbox.setAlignment(Pos.CENTER);
		Text titleText = new Text(titleTextLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		myVbox.getChildren().add(titleText);
		return myVbox;
	}

	// Create the main form contents
	// -------------------------------------------------------------
	private VBox createFormContents() {
		
		VBox vbox = new VBox(15);
		vbox.setAlignment(Pos.CENTER);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 15);
		Text firstNameL = new Text(firstNameLabel);
		firstNameL.setFont(myFont);
		Text lastNameL = new Text(lastNameLabel);
		lastNameL.setFont(myFont);
		grid.add(firstNameL, 0, 0);
		
		firstName = new TextField();
		firstName.setEditable(true);
		grid.add(firstName, 1, 0);
		
		grid.add(lastNameL, 0, 1);
		lastName = new TextField();
		lastName.setEditable(true);
		grid.add(lastName, 1, 1);
		
		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");
		
		cancelButton = new Button(cancelButtonLabel);
		cancelButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");
		buttons.getChildren().addAll(submitButton, cancelButton);
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelAddScout", null);   
			}
		});

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				processAction(e);
			}
		});
		
		Text instructionText = new Text(instructions);
		instructionText.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		vbox.getChildren().addAll(instructionText, grid);
		vbox.getChildren().add(buttons);
		
		return vbox;
	}

	// -------------------------------------------------------------
	private void processAction(Event e) {
		
		if ((firstName.getText().isEmpty()) && (lastName.getText().isEmpty())) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			MainStageContainer.resizeStage();
			firstName.requestFocus();
		}
		else if (firstName.getText().isEmpty()) {
			Properties props = new Properties();
			props.setProperty("firstName", "");
			props.setProperty("lastName", lastName.getText());
			myModel.stateChangeRequest("showCollectionView", props);
		}
		else if (lastName.getText().isEmpty()) {
			Properties props = new Properties();
			props.setProperty("firstName", firstName.getText());
			props.setProperty("lastName", "");
			myModel.stateChangeRequest("showCollectionView", props);
		}
		else {
			Properties props = new Properties();
			props.setProperty("firstName", firstName.getText());
			props.setProperty("lastName", lastName.getText());
			myModel.stateChangeRequest("showCollectionView", props);
		}
	}
	
	
	// Create the status log field
	// -------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

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
