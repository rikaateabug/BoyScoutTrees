package views;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
import models.TreeLotCoordinator;
import transactions.ScoutTransaction;
import userinterface.MessageView;
import views.View;
import javafx.*;

import java.awt.Graphics;
import java.awt.Shape;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;





// project imports
import impresario.IModel;


// ==============================================================
public class DeleteScoutView extends View {

	// GUI components
	
	protected final String scoutIDLabel = new String(myResourceBundle.getString("scoutIDLabel"));
	protected final String dateLastUpdatedLabel = new String(myResourceBundle.getString("dateLastUpdatedLabel"));
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String lastNameLabel = new String(myResourceBundle.getString("lastNameLabel"));
	protected final String firstNameLabel = new String(myResourceBundle.getString("firstNameLabel")); 
	protected final String middleNameLabel = new String(myResourceBundle.getString("middleNameLabel"));
	protected final String dateOfBirthLabel = new String(myResourceBundle.getString("dateOfBirthLabel"));
	protected final String phoneNumberLabel = new String(myResourceBundle.getString("phoneNumberLabel"));
	protected final String troopIDLabel = new String(myResourceBundle.getString("troopIDLabel"));
	protected final String emailLabel = new String(myResourceBundle.getString("emailLabel"));
	protected final String statusLabel = new String(myResourceBundle.getString("statusLabel"));
	protected final String activeStatus = new String(myResourceBundle.getString("activeStatus"));
	protected final String inactiveStatus = new String(myResourceBundle.getString("inactiveStatus"));
	protected final String doneButtonLabel = new String(myResourceBundle.getString("doneButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String removeSuccessMessage = new String(myResourceBundle.getString("removeSuccessMessage"));
	protected final String yesLabel = new String(myResourceBundle.getString("yesLabel"));
	protected final String noLabel = new String(myResourceBundle.getString("noLabel"));
	protected final String removeConfirmMessage = new String(myResourceBundle.getString("removeConfirmMessage"));
	
	protected Text scoutID;
	protected Text lastName;
	protected Text firstName;
	protected Text middleName;
	protected Text dateOfBirth;
	protected Text phoneNumber;
	protected Text email;
	protected Text troopID;
	protected Text dateStatusUpdated;
	protected Text status;

	protected Button submitButton;
	protected Button doneButton;

	// For showing error message
	protected MessageView statusLog;
	protected Scout myScout;
	protected ScoutTransaction myTrans;
	protected Locale myLocale;
	
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public DeleteScoutView(IModel scoutTrans) {
		super(scoutTrans, "DeleteScoutView");

		myTrans = (ScoutTransaction) scoutTrans;
		myScout = (Scout) scoutTrans.getState("Scout");
		myLocale = (Locale)myModel.getState("Locale");
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		container.getChildren().add(createTitle());
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
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(30);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		// -------------------------------------------------------------
		Text scoutLabel = new Text(scoutIDLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 14);
		scoutLabel.setFont(myFont);
		scoutLabel.setWrappingWidth(150);
		scoutLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(scoutLabel, 0, 0);
		
		scoutID = new Text((String)myScout.getState("scoutID"));
		
		grid.add(scoutID, 1, 0);
		
		// -------------------------------------------------------------
		Text fnLabel = new Text(firstNameLabel);
		fnLabel.setFont(myFont);
		fnLabel.setWrappingWidth(150);
		fnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(fnLabel, 0, 1);

		
		firstName = new Text((String)myScout.getState("firstName"));
		grid.add(firstName, 1, 1);
		// -------------------------------------------------------------
		Text mnLabel = new Text(middleNameLabel);
		mnLabel.setFont(myFont);
		mnLabel.setWrappingWidth(150);
		mnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(mnLabel, 0, 2);

		middleName = new Text((String)myScout.getState("middleName"));
		grid.add(middleName, 1, 2);
		// -------------------------------------------------------------
		Text lnLabel = new Text(lastNameLabel);
		lnLabel.setFont(myFont);
		lnLabel.setWrappingWidth(150);
		lnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(lnLabel, 0, 3);

		lastName = new Text((String)myScout.getState("lastName"));
		grid.add(lastName, 1, 3);
		// -------------------------------------------------------------
		Text dobLabel = new Text(dateOfBirthLabel);
		dobLabel.setFont(myFont);
		dobLabel.setWrappingWidth(150);
		dobLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dobLabel, 0, 4);

		dateOfBirth = new Text((String)myScout.getState("birthDate"));
		grid.add(dateOfBirth, 1, 4);
		// -------------------------------------------------------------
		Text pnLabel = new Text(phoneNumberLabel);
		pnLabel.setFont(myFont);
		pnLabel.setWrappingWidth(150);
		pnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(pnLabel, 0, 5);

		phoneNumber = new Text((String)myScout.getState("phoneNumber"));
		
		grid.add(phoneNumber, 1, 5);
		// -------------------------------------------------------------
		Text eLabel = new Text(emailLabel);
		eLabel.setFont(myFont);
		eLabel.setWrappingWidth(150);
		eLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(eLabel, 0, 6);

		email = new Text((String)myScout.getState("email"));
		grid.add(email, 1, 6);
		// -------------------------------------------------------------
		Text tidLabel = new Text(troopIDLabel);
		tidLabel.setFont(myFont);
		tidLabel.setWrappingWidth(150);
		tidLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(tidLabel, 0, 7);

		troopID = new Text((String)myScout.getState("troopID"));
		grid.add(troopID, 1, 7);
		// -------------------------------------------------------------
		Text sLabel = new Text(statusLabel);
		sLabel.setFont(myFont);
		sLabel.setWrappingWidth(150);
		sLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(sLabel, 0, 8);

		status = new Text((String)myScout.getState("status"));
		grid.add(status, 1, 8);
		// -------------------------------------------------------------
		Text dLabel = new Text(dateLastUpdatedLabel);
		dLabel.setFont(myFont);
		dLabel.setWrappingWidth(150);
		dLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dLabel, 0, 9);
		dateStatusUpdated = new Text((String)myScout.getState("dateStatusUpdated"));
		grid.add(dateStatusUpdated, 1, 9);
		// -------------------------------------------------------------
		MessageView mv = new MessageView("");
		mv.setFont(myFont);
		mv.setWrappingWidth(350);
		vbox.getChildren().add(mv);
		
		HBox doneCont = new HBox(20);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");

		doneCont.getChildren().add(submitButton);

		doneButton = new Button(doneButtonLabel);
		doneButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");
		
		// -------------------------------------------------------------
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelAddScout", null);   
			}
		});
		// -------------------------------------------------------------
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				processAction(e);
			}
		});

		doneCont.getChildren().add(doneButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// Create the status log field
	// -------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// ---------------------------------------------------------
	public void processAction(Event e) {
			Properties p = setPropertiesObject();
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText(removeConfirmMessage);
			
			ButtonType buttonTypeOne = new ButtonType(yesLabel);
			ButtonType buttonTypeTwo = new ButtonType(noLabel);
			
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne){
				alert.close();
				myTrans.stateChangeRequest("DeleteAScout", p);
				showConfirmationMessage();
			} else {
			    alert.close();
			}
	}

	// ----------------------------------------------------------
	public void showConfirmationMessage() {
	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(removeSuccessMessage);
		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				alert.close();
				myModel.stateChangeRequest("CancelAddScout", null);   
			}
		});
	
	}
	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		props.setProperty("scoutID", (String)myScout.getState("scoutID"));
		props.setProperty("firstName", (String)myScout.getState("firstName"));
		props.setProperty("middleName", (String)myScout.getState("middleName"));
		props.setProperty("lastName", (String)myScout.getState("lastName"));
		props.setProperty("birthDate", (String)myScout.getState("birthDate"));
		props.setProperty("phoneNumber", (String)myScout.getState("phoneNumber"));
		props.setProperty("troopID", (String)myScout.getState("troopID"));
		props.setProperty("email", (String)myScout.getState("email"));
		props.setProperty("status", "inactive");
		
		DateFormat df;
		if (myLocale.toString().equals("en_US")) {
			df = new SimpleDateFormat("MM-dd-yyyy");
		}
		else {
			df = new SimpleDateFormat("dd-MM-yyyy");
		}
		Date date = new Date();
		props.setProperty("dateStatusUpdated", df.format(date));
		return props;
	}

	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	// ----------------------------------------------------------
	public void displayMessage(String message) {
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
