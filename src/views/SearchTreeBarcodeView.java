package views;

import java.text.NumberFormat;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
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
import models.Scout;
import models.ScoutCollection;
import models.Tree;
import models.TreeLotCoordinator;
import userinterface.MessageView;
import views.View;
// project imports
import impresario.IModel;

/** The class containing the Teller View for the ATM application */
// ==============================================================
public class SearchTreeBarcodeView extends View {

	// GUI stuff

    protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButton"));
    protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButton"));
	protected final String titleTextLabel = new String(myResourceBundle.getString("titleText"));
	protected final String barcodeLabel = new String(myResourceBundle.getString("barcodeLabel"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	
	
	private Button submitButton;
	private Button cancelButton;
	private TextField barcodeEntry;

	private IModel myTLC;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public SearchTreeBarcodeView(IModel tlc) {

		super(tlc, "SearchTreeBarcodeView");

		myTLC = tlc;

		// create a container for showing the contents
		VBox container = new VBox(10);
		//container.setAlignment(Pos.CENTER);
		
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
		VBox myVbox = new VBox(10);
		myVbox.setAlignment(Pos.CENTER);
		Text titleText = new Text(titleTextLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		myVbox.getChildren().add(titleText);
		
		return myVbox;
	}

	// Create the main form contents
	// -------------------------------------------------------------
	private VBox createFormContents() {
		
		VBox vbox = new VBox(15);
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text barcodeT = new Text(barcodeLabel);
		
		grid.add(barcodeT, 0, 0);
		
		barcodeEntry = new TextField();
		barcodeEntry.setEditable(true);
		grid.add(barcodeEntry, 1, 0);
		
		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		cancelButton = new Button(cancelButtonLabel);
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
		
		vbox.getChildren().add(grid);
		vbox.getChildren().add(buttons);
		
		return vbox;
	}

	// -------------------------------------------------------------
	private void processAction(Event e) {
		
		if (barcodeEntry.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			barcodeEntry.requestFocus();
		}
		
		else {
			
			try {
				Tree t = new Tree(barcodeEntry.getText());
				myModel.stateChangeRequest("showView", barcodeEntry.getText());
			} catch (InvalidPrimaryKeyException e1) {
				statusLog.displayErrorMessage("Invalid Barcode Entry");
			}
		}
	}
	
	// Create the status log field
	// -------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	public void updateState(String key, Object value) {
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
