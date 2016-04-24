package views;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
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
import models.Tree;
import models.TreeType;
import models.TreeTypeCollection;

import java.awt.Graphics;
import java.awt.Shape;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;

import exception.InvalidPrimaryKeyException;
import userinterface.MessageView;
// project imports
import impresario.IModel;

/** The class containing the Account View for the ATM application */
// ==============================================================
public class AddTreeTypeView extends View {

	// GUI components
	
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String descriptionLabel = new String(myResourceBundle.getString("descriptionLabel"));
	protected final String barcodePrefixLabel = new String(myResourceBundle.getString("barcodePrefixLabel"));
	protected final String costLabel = new String(myResourceBundle.getString("costLabel"));
	protected final String doneButtonLabel = new String(myResourceBundle.getString("doneButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	protected final String addSuccessMessage = new String(myResourceBundle.getString("addSuccessMessage"));
	protected final String invalidCostMessage = new String(myResourceBundle.getString("invalidCostMessage"));
	protected final String duplicateBarcodePrefixMessage = new String(myResourceBundle.getString("duplicateBarcodePrefixMessage"));
	protected final String invalidBarcodePrefixMessage = new String(myResourceBundle.getString("invalidBarcodePrefixMessage"));
	
	
	protected TextField barcodePrefix;
	protected TextField description;
	protected TextField cost;
	
	protected TreeType myTreeType;
	protected TreeTypeCollection myTreeTypeCol;
	protected Button submitButton;
	protected Button doneButton;
	
	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public AddTreeTypeView(IModel trans) {
		super(trans, "AddTreeTypeView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 15, 15, 15));
		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());
				
		container.getChildren().add(createStatusLog("             "));
		getChildren().add(container);	
	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);

		Text titleText = new Text(titleLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 10, 0));

		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		
		Text descLabel = new Text(descriptionLabel);
		descLabel.setFont(myFont);
		descLabel.setWrappingWidth(150);
		descLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(descLabel, 0, 0);

		description = new TextField();
		description.setEditable(true);
		grid.add(description, 1, 0);

		Text costL = new Text(costLabel);
		costL.setFont(myFont);
		costL.setWrappingWidth(150);
		costL.setTextAlignment(TextAlignment.RIGHT);
		grid.add(costL, 0, 1);

		cost = new TextField();
		cost.setEditable(true);
		grid.add(cost, 1, 1);
		
		Text barcodeL = new Text(barcodePrefixLabel);
		barcodeL.setFont(myFont);
		barcodeL.setWrappingWidth(150);
		barcodeL.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barcodeL, 0, 2);

		barcodePrefix = new TextField();
		barcodePrefix.setEditable(true);
		grid.add(barcodePrefix, 1, 2);

		MessageView mv = new MessageView("");
		mv.setFont(myFont);
		mv.setWrappingWidth(350);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				validateEntries();
			}
		});

		doneCont.getChildren().add(submitButton);

		
		doneButton = new Button(doneButtonLabel);
		doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelTrans", null);

			}
		});
		
		doneCont.getChildren().add(doneButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(mv);
		vbox.getChildren().add(doneCont);
		
		return vbox;
	}

	// -------------------------------------------------------------
	public void validateEntries() {
		/**
		1.) Ensure nothing is empty
		2.) Ensure Cost is a double
		3.) Ensure Barcode Prefix is 2 integers
		4.) Ensure a TreeType with this barcodePrefix doesn't already exist
		*/ 
		
		// 1.)
		if (description.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			description.requestFocus();
		}
		
		else if (cost.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			cost.requestFocus();
		}
		
		else if (barcodePrefix.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			barcodePrefix.requestFocus();
		}
		
		
		// 2.)
		else if (!(cost.getText().matches("(\\d)+(\\.\\d{2})?"))) {
			statusLog.displayErrorMessage(invalidCostMessage);
			cost.requestFocus();
		}
		
		// 3.)
		else if (!(barcodePrefix.getText().matches("\\d{2}"))) {
			statusLog.displayErrorMessage(invalidBarcodePrefixMessage);
			barcodePrefix.requestFocus();
		}
		
		// 4.)
		else {
			myModel.stateChangeRequest("searchTreeTypes", barcodePrefix.getText());
			boolean result = (boolean) myModel.getState("TreeTypesEmpty");
			
			if (result == false) {
				statusLog.displayErrorMessage(duplicateBarcodePrefixMessage);
				barcodePrefix.requestFocus();
			}
			else {
				Properties p = new Properties();
				p = setPropertiesObject();
				myModel.stateChangeRequest("insertNewTreeType", p);
				statusLog.displayMessage(addSuccessMessage);
			}
		}
	}
	
	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		props.setProperty("typeDescription", description.getText());
		props.setProperty("barcodePrefix", barcodePrefix.getText());
		
		String theCost = cost.getText();
		
		if (theCost.matches("(\\d)+")) {
			theCost = theCost.concat(".00");
			props.setProperty("cost", theCost);
		}
		props.setProperty("cost", cost.getText());
		
		return props;
	}
	// -------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	// ----------------------------------------------------------
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}

	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	// ----------------------------------------------------------
	public void updateState(String key, Object value) {

	}

}