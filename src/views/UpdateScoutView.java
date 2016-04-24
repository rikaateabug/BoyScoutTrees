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
import java.util.Properties;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;





// project imports
import impresario.IModel;


// ==============================================================
public class UpdateScoutView extends View {

	// GUI components
	
	protected final String scoutIDLabel = new String(myResourceBundle.getString("scoutIDLabel"));
	protected final String dateLastUpdatedLabel = new String(myResourceBundle.getString("dateLastUpdatedLabel"));
	protected final String titleLabel = new String(myResourceBundle.getString("title"));
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
	protected final String updateSuccessMessage = new String(myResourceBundle.getString("updateSuccessMessage"));
	protected final String invalidDateFormatMessage = new String(myResourceBundle.getString("invalidDateFormatMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	
	
	protected TextField scoutID;
	protected TextField lastName;
	protected TextField firstName;
	protected TextField middleName;
	protected TextField dateOfBirth;
	protected TextField phoneNumber;
	protected TextField email;
	protected TextField troopID;
	protected TextField dateStatusUpdated;
	protected ComboBox<String> status;

	protected Button submitButton;
	protected Button doneButton;

	// For showing error message
	protected MessageView statusLog;
	protected Scout myScout;
	protected ScoutTransaction myTrans;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public UpdateScoutView(IModel scoutTrans) {
		super(scoutTrans, "UpdateScoutView");

		myTrans = (ScoutTransaction) scoutTrans;
		myScout = (Scout) scoutTrans.getState("Scout");
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		// populateFields();

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

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(30);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		// -------------------------------------------------------------
		Text scoutLabel = new Text(scoutIDLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		scoutLabel.setFont(myFont);
		scoutLabel.setWrappingWidth(150);
		scoutLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(scoutLabel, 0, 0);
		
		scoutID = new TextField((String)myScout.getState("scoutID"));
		scoutID.setEditable(false);
		scoutID.setMouseTransparent(true);
		scoutID.setFocusTraversable(false);
		scoutID.setStyle("-fx-control-inner-background: #d3d3d3;");
		
		grid.add(scoutID, 1, 0);
		
		// -------------------------------------------------------------
		Text fnLabel = new Text(firstNameLabel);
		fnLabel.setFont(myFont);
		fnLabel.setWrappingWidth(150);
		fnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(fnLabel, 0, 1);

		
		firstName = new TextField((String)myScout.getState("firstName"));
		firstName.setEditable(true);
		grid.add(firstName, 1, 1);
		// -------------------------------------------------------------
		Text mnLabel = new Text(middleNameLabel);
		mnLabel.setFont(myFont);
		mnLabel.setWrappingWidth(150);
		mnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(mnLabel, 0, 2);

		middleName = new TextField((String)myScout.getState("middleName"));
		middleName.setEditable(true);
		grid.add(middleName, 1, 2);
		// -------------------------------------------------------------
		Text lnLabel = new Text(lastNameLabel);
		lnLabel.setFont(myFont);
		lnLabel.setWrappingWidth(150);
		lnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(lnLabel, 0, 3);

		lastName = new TextField((String)myScout.getState("lastName"));
		lastName.setEditable(true);
		grid.add(lastName, 1, 3);
		// -------------------------------------------------------------
		Text dobLabel = new Text(dateOfBirthLabel);
		dobLabel.setFont(myFont);
		dobLabel.setWrappingWidth(150);
		dobLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dobLabel, 0, 4);

		dateOfBirth = new TextField((String)myScout.getState("birthDate"));
		dateOfBirth.setEditable(true);
		grid.add(dateOfBirth, 1, 4);
		// -------------------------------------------------------------
		Text pnLabel = new Text(phoneNumberLabel);
		pnLabel.setFont(myFont);
		pnLabel.setWrappingWidth(150);
		pnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(pnLabel, 0, 5);

		phoneNumber = new TextField((String)myScout.getState("phoneNumber"));
		phoneNumber.setEditable(true);
		grid.add(phoneNumber, 1, 5);
		// -------------------------------------------------------------
		Text eLabel = new Text(emailLabel);
		eLabel.setFont(myFont);
		eLabel.setWrappingWidth(150);
		eLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(eLabel, 0, 6);

		email = new TextField((String)myScout.getState("email"));
		email.setEditable(true);
		grid.add(email, 1, 6);
		// -------------------------------------------------------------
		Text tidLabel = new Text(troopIDLabel);
		tidLabel.setFont(myFont);
		tidLabel.setWrappingWidth(150);
		tidLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(tidLabel, 0, 7);

		troopID = new TextField((String)myScout.getState("troopID"));
		troopID.setEditable(true);
		grid.add(troopID, 1, 7);
		// -------------------------------------------------------------
		Text sLabel = new Text(statusLabel);
		sLabel.setFont(myFont);
		sLabel.setWrappingWidth(150);
		sLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(sLabel, 0, 8);

		status = new ComboBox<String>();
		status.getItems().addAll(activeStatus, inactiveStatus);
		String currentStatus = (String)myScout.getState("status");
		
		if (currentStatus.equals(activeStatus)) {
			status.setValue(activeStatus);
		}
		
		if (currentStatus.equals(inactiveStatus)) {
			status.setValue(inactiveStatus);
		}
		
		grid.add(status, 1, 8);
		// -------------------------------------------------------------
		Text dLabel = new Text(dateLastUpdatedLabel);
		dLabel.setFont(myFont);
		dLabel.setWrappingWidth(150);
		dLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dLabel, 0, 9);
		dateStatusUpdated = new TextField((String)myScout.getState("dateStatusUpdated"));
		dateStatusUpdated.setEditable(false);
		dateStatusUpdated.setMouseTransparent(true);
		dateStatusUpdated.setFocusTraversable(false);
		dateStatusUpdated.setStyle("-fx-control-inner-background: #d3d3d3;");
		
		grid.add(dateStatusUpdated, 1, 9);
		// -------------------------------------------------------------
		MessageView mv = new MessageView("");
		mv.setFont(myFont);
		mv.setWrappingWidth(350);
		vbox.getChildren().add(mv);
		
		HBox doneCont = new HBox(20);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));


		doneCont.getChildren().add(submitButton);

		doneButton = new Button(doneButtonLabel);
		doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
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

		if (firstName.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			firstName.requestFocus();
		}
		
		else if (middleName.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			middleName.requestFocus();
		}
		
		else if (lastName.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			lastName.requestFocus();
		}

		else if (dateOfBirth.getText().isEmpty()) {
			System.out.println("Date of birth is null");
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			dateOfBirth.requestFocus();
		}

		else if (!(dateOfBirth.getText().matches("\\d{4}-\\d{2}-\\d{2}"))) {
			displayErrorMessage(invalidDateFormatMessage);
			dateOfBirth.requestFocus();
		}

		else if (phoneNumber.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			phoneNumber.requestFocus();
		}

		else if (email.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			email.requestFocus();
		}

		else if (troopID.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			troopID.requestFocus();
		}
		
		else {
			Properties p = setPropertiesObject();
			myTrans.stateChangeRequest("UpdateAScout", p);
			statusLog.displayMessage(updateSuccessMessage);
		}
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		props.setProperty("scoutID", scoutID.getText());
		props.setProperty("firstName", firstName.getText());
		props.setProperty("middleName", middleName.getText());
		props.setProperty("lastName", lastName.getText());
		props.setProperty("birthDate", dateOfBirth.getText());
		props.setProperty("phoneNumber", phoneNumber.getText());
		props.setProperty("troopID", troopID.getText());
		props.setProperty("email", email.getText());
		props.setProperty("status", status.getValue());
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
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
