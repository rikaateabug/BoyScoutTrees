package views;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
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

/** The class containing the Account View for the ATM application */
// ==============================================================
public class AddScoutView extends View {

	// GUI components
	
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
	protected final String invalidDateErrorMessage = new String(myResourceBundle.getString("invalidDateErrorMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	protected final String addSuccessMessage = new String(myResourceBundle.getString("addSuccessMessage"));
	 
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
	//protected MessageView statusLog;
	protected Scout myScout;
	protected ScoutTransaction myTrans;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public AddScoutView(IModel scoutTrans) {
		super(scoutTrans, "AddScoutView");

		myTrans = (ScoutTransaction) scoutTrans;
		myScout = (Scout) scoutTrans.getState("Scout");
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		//container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		// populateFields();

	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(20, 5, 5, 5));
		
		
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
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 25, 25, 25));
		
		
		// -------------------------------------------------------------
		Text accNumLabel = new Text(firstNameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		accNumLabel.setFont(myFont);
		accNumLabel.setWrappingWidth(100);
		accNumLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(accNumLabel, 0, 1);
		firstName = new TextField();
		firstName.setEditable(true);
		grid.add(firstName, 1, 1);
		// -------------------------------------------------------------
		Text acctTypeLabel = new Text(middleNameLabel);
		acctTypeLabel.setFont(myFont);
		acctTypeLabel.setWrappingWidth(100);
		acctTypeLabel.setTextAlignment(TextAlignment.RIGHT);		
		grid.add(acctTypeLabel, 0, 2);

		middleName = new TextField();
		middleName.setEditable(true);
		grid.add(middleName, 1, 2);
		// -------------------------------------------------------------
		Text balLabel = new Text(lastNameLabel);
		balLabel.setFont(myFont);
		balLabel.setWrappingWidth(100);
		balLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel, 0, 3);

		lastName = new TextField();
		lastName.setEditable(true);
		grid.add(lastName, 1, 3);
		// -------------------------------------------------------------
		Text balLabel1 = new Text(dateOfBirthLabel);
		balLabel1.setFont(myFont);
		balLabel1.setWrappingWidth(100);
		balLabel1.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel1, 0, 4);

		dateOfBirth = new TextField();
		dateOfBirth.setEditable(true);
		grid.add(dateOfBirth, 1, 4);
		// -------------------------------------------------------------
		Text balLabel2 = new Text(phoneNumberLabel);
		balLabel2.setFont(myFont);
		balLabel2.setWrappingWidth(100);
		balLabel2.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel2, 0, 5);

		phoneNumber = new TextField();
		phoneNumber.setEditable(true);
		grid.add(phoneNumber, 1, 5);
		// -------------------------------------------------------------
		Text balLabel3 = new Text(emailLabel);
		balLabel3.setFont(myFont);
		balLabel3.setWrappingWidth(100);
		balLabel3.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel3, 0, 6);

		email = new TextField();
		email.setEditable(true);
		grid.add(email, 1, 6);
		// -------------------------------------------------------------
		Text balLabel5 = new Text(troopIDLabel);
		balLabel5.setFont(myFont);
		balLabel5.setWrappingWidth(100);
		balLabel5.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel5, 0, 7);

		troopID = new TextField();
		troopID.setEditable(true);
		grid.add(troopID, 1, 7);
		// -------------------------------------------------------------
		Text balLabel6 = new Text(statusLabel);
		balLabel6.setFont(myFont);
		balLabel6.setWrappingWidth(100);
		balLabel6.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel6, 0, 8);

		status = new ComboBox<String>();
		status.getItems().addAll(activeStatus, inactiveStatus);
		status.setValue(activeStatus);
		grid.add(status, 1, 8);
		// -------------------------------------------------------------
		//MessageView mv = new MessageView("");
		//mv.setFont(myFont);
		//mv.setWrappingWidth(350);
		//vbox.getChildren().add(mv);
		
		HBox doneCont = new HBox(10);
		doneCont.setPadding(new Insets(0, 0, 5, 0));
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		//submitButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

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
	/**
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
*/
	// ---------------------------------------------------------
	public void processAction(Event e) {

		if (firstName.getText().isEmpty()) {
	//		statusLog.displayErrorMessage(nullFieldErrorMessage);
			firstName.requestFocus();
		}
		
		else if (middleName.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			middleName.requestFocus();
		}
		
		else if (lastName.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			lastName.requestFocus();
		}

		else if (dateOfBirth.getText().isEmpty()) {
			System.out.println("Date of birth is null");
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			dateOfBirth.requestFocus();
		}

		else if (!(dateOfBirth.getText().matches("\\d{4}-\\d{2}-\\d{2}"))) {
		//	displayErrorMessage(invalidDateErrorMessage);
			dateOfBirth.requestFocus();
		}

		else if (phoneNumber.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			phoneNumber.requestFocus();
		}

		else if (email.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			email.requestFocus();
		}

		else if (troopID.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			troopID.requestFocus();
		}
		
		else {
			Properties p = setPropertiesObject();
			myTrans.stateChangeRequest("insertNewScout", p);
	//		statusLog.displayMessage(addSuccessMessage);
			clearTextFields();
		}
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
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
	public void clearTextFields() {
		lastName.clear();
		firstName.clear();
		middleName.clear();
		dateOfBirth.clear();
		phoneNumber.clear();
		email.clear();
		troopID.clear();
		status.setValue(activeStatus);
	}
	
	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		//statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	// ----------------------------------------------------------
	public void displayMessage(String message) {
		//statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		//statusLog.clearErrorMessage();
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
