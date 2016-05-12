package views;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
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
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
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
	protected final String invalidPhoneErrorMessage = new String(myResourceBundle.getString("invalidPhoneErrorMessage"));
	protected final String addAnotherMessage = new String(myResourceBundle.getString("addAnotherMessage"));
	protected final String yesLabel = new String(myResourceBundle.getString("yesLabel"));
	protected final String noLabel = new String(myResourceBundle.getString("noLabel")); 
	protected final String monthTextLabel = new String(myResourceBundle.getString("monthTextLabel"));
	protected final String dayTextLabel = new String(myResourceBundle.getString("dayTextLabel"));
	protected final String yearTextLabel = new String(myResourceBundle.getString("yearTextLabel"));
	protected final String fullNameLabel = new String(myResourceBundle.getString("fullNameLabel"));
	
	
	protected TextField areaCode;
	protected TextField lastName;
	protected TextField firstName;
	protected TextField middleName;
	//protected TextField dateOfBirth;
	protected TextField yearField;
	protected TextField monthField;
	protected TextField dayField;
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
	protected Locale myLocale;
	
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public AddScoutView(IModel scoutTrans) {
		super(scoutTrans, "AddScoutView");

		myTrans = (ScoutTransaction) scoutTrans;
		myScout = (Scout) scoutTrans.getState("Scout");
		myLocale = (Locale)myModel.getState("Locale");
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(25, 50, 25, 50));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		getChildren().add(container);
	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(20, 5, 30, 5));
		
		
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
		VBox vbox = new VBox(20);

		
		VBox nameBox = new VBox(10);
		// -------------------------------------------------------------
		VBox fnBox = new VBox(5);
		Text accNumLabel = new Text(firstNameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 15);
		
		Font myFont2 = Font.font("Helvetica", FontWeight.LIGHT, 12);
		accNumLabel.setFont(myFont2);
		accNumLabel.setTextAlignment(TextAlignment.RIGHT);
		firstName = new TextField();
		firstName.setEditable(true);
		firstName.setPrefWidth(100);
		firstName.setMinWidth(100);
		firstName.setMaxWidth(100);
		//fnBox.getChildren().addAll(accNumLabel, firstName);
		fnBox.getChildren().addAll(firstName, accNumLabel);
		
		VBox mnBox = new VBox(5);
		Text acctTypeLabel = new Text(middleNameLabel);
		acctTypeLabel.setFont(myFont2);
		acctTypeLabel.setTextAlignment(TextAlignment.RIGHT);
		middleName = new TextField();
		middleName.setEditable(true);
		middleName.setPrefWidth(100);
		middleName.setMinWidth(100);
		middleName.setMaxWidth(100);
		//mnBox.getChildren().addAll(acctTypeLabel, middleName);
		mnBox.getChildren().addAll(middleName, acctTypeLabel);
		
		VBox lnBox = new VBox(5);
		Text balLabel = new Text(lastNameLabel);
		balLabel.setFont(myFont2);
		balLabel.setTextAlignment(TextAlignment.RIGHT);
		lastName = new TextField();
		lastName.setEditable(true);
		lastName.setPrefWidth(100);
		lastName.setMinWidth(100);
		lastName.setMaxWidth(100);
		//lnBox.getChildren().addAll(balLabel, lastName);
		lnBox.getChildren().addAll(lastName, balLabel);
		
		Text nameLabel = new Text(fullNameLabel);
		nameLabel.setFont(myFont);
		
		HBox rows = new HBox(10);
		rows.getChildren().addAll(fnBox, mnBox, lnBox);
		nameBox.getChildren().addAll(nameLabel, rows);
		
		
		
		// -------------------------------------------------------------
		VBox dobBox = new VBox(10);
		Text balLabel1 = new Text(dateOfBirthLabel);
		Text dateSpace1 = new Text("-");
		Text dateSpace2 = new Text("-");
		dateSpace1.setFont(myFont);
		dateSpace2.setFont(myFont);
		balLabel1.setFont(myFont);
		balLabel1.setTextAlignment(TextAlignment.RIGHT);

		//dateOfBirth = new TextField();
		//dateOfBirth.setEditable(true);	//If this works then change it for locale
		
		monthField = new TextField();
		monthField.setPrefWidth(40);
		monthField.setMinWidth(40);
		monthField.setMaxWidth(40);
		monthField.setPromptText("00");
		
		
		dayField = new TextField();
		dayField.setPrefWidth(40);
		dayField.setMinWidth(40);
		dayField.setMaxWidth(40);
		dayField.setPromptText("00");
		
		
		yearField = new TextField();
		yearField.setPrefWidth(60);
		yearField.setMinWidth(60);
		yearField.setMaxWidth(60);
		yearField.setPromptText("0000");
		
		VBox monthBox = new VBox(5);
		Text monthLabel = new Text(monthTextLabel);
		monthLabel.setFont(myFont2);
		monthBox.getChildren().addAll(monthField, monthLabel);
		
		VBox dayBox = new VBox(5);
		Text dayLabel = new Text(dayTextLabel);
		dayLabel.setFont(myFont2);
		dayBox.getChildren().addAll(dayField, dayLabel);
		
		VBox yearBox = new VBox(5);
		Text yearLabel = new Text(yearTextLabel);
		yearLabel.setFont(myFont2);
		yearBox.getChildren().addAll(yearField, yearLabel);
		
		HBox dateHbox = new HBox(5);
		myLocale = (Locale)myModel.getState("Locale");
		if (myLocale.toString().equals("en_US")) {
			dateHbox.getChildren().addAll(monthBox, dateSpace1, dayBox, dateSpace2, yearBox);
		}
		else if (myLocale.toString().equals("fr_FR")) {
			dateHbox.getChildren().addAll(dayBox, dateSpace1, monthBox, dateSpace2, yearBox);
		}
		
		dobBox.getChildren().addAll(balLabel1, dateHbox);
		
		// -------------------------------------------------------------
		VBox pnBox = new VBox(10);
		Text balLabel2 = new Text(phoneNumberLabel);
		balLabel2.setFont(myFont);
		balLabel2.setTextAlignment(TextAlignment.RIGHT);

		
		
		phoneNumber = new TextField();
		phoneNumber.setEditable(true);
		phoneNumber.setPrefWidth(80);
		phoneNumber.setMinWidth(80);
		phoneNumber.setMaxWidth(80);
		
		Text dash = new Text("-");
		dash.setFont(myFont);
		areaCode = new TextField();
		areaCode.setPrefWidth(60);
		areaCode.setMinWidth(60);
		areaCode.setMaxWidth(60);
		
		HBox phoneFields;
		
		if (myLocale.toString().equals("en_US")) {
			phoneFields = new HBox(5);
			phoneFields.getChildren().addAll(areaCode, dash, phoneNumber);	
		}
		else {
			phoneFields = new HBox(0);
			phoneNumber.setPrefWidth(100);
			phoneNumber.setMinWidth(100);
			phoneNumber.setMaxWidth(100);
			Text pre = new Text("+");
			pre.setFont(myFont);
			phoneFields.getChildren().addAll(pre, phoneNumber);
		}
		
		pnBox.getChildren().addAll(balLabel2, phoneFields);
		
		
		// -------------------------------------------------------------
		VBox eBox = new VBox(10);
		Text balLabel3 = new Text(emailLabel);
		balLabel3.setFont(myFont);
		// balLabel3.setWrappingWidth(100);
		balLabel3.setTextAlignment(TextAlignment.RIGHT);
		//grid.add(balLabel3, 0, 6);

		email = new TextField();
		email.setEditable(true);
		email.setPrefWidth(150);
		email.setMinWidth(150);
		email.setMaxWidth(150);
		eBox.getChildren().addAll(balLabel3, email);
		// -------------------------------------------------------------
		
		VBox tIDBox = new VBox(10);
		Text balLabel5 = new Text(troopIDLabel);
		balLabel5.setFont(myFont);
		balLabel5.setTextAlignment(TextAlignment.RIGHT);
		troopID = new TextField();
		troopID.setEditable(true);
		troopID.setPrefWidth(100);
		troopID.setMinWidth(100);
		troopID.setMaxWidth(100);
		
		tIDBox.getChildren().addAll(balLabel5, troopID);
		
		// -------------------------------------------------------------
		VBox statusBox = new VBox(10);
		Text balLabel6 = new Text(statusLabel);
		balLabel6.setFont(myFont);
		balLabel6.setTextAlignment(TextAlignment.RIGHT);

		status = new ComboBox<String>();
		status.getItems().addAll(activeStatus, inactiveStatus);
		status.setValue(activeStatus);
		statusBox.getChildren().addAll(balLabel6, status);
		
		// -------------------------------------------------------------

		HBox doneCont = new HBox(10);
		doneCont.setPadding(new Insets(20, 0, 10, 0));
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		//submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setStyle("-fx-font: 17 arial; -fx-font-weight: bold;");

		doneCont.getChildren().add(submitButton);

		doneButton = new Button(doneButtonLabel);
		//doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		doneButton.setStyle("-fx-font: 17 arial; -fx-font-weight: bold;");
		
		// -------------------------------------------------------------
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				myModel.stateChangeRequest("CancelAddScout", null);
			}
		});
		// -------------------------------------------------------------

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		doneCont.getChildren().add(doneButton);

		vbox.getChildren().addAll(nameBox, dobBox, pnBox, eBox, tIDBox, statusBox);
		vbox.getChildren().add(doneCont);

		return vbox;
	}	
	

	// ---------------------------------------------------------
	public void processAction(Event e) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		
		if (firstName.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			firstName.requestFocus();
			return;
		}

		if (lastName.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			lastName.requestFocus();
			return;
		}

		if (monthField.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			monthField.requestFocus();
			return;
		}

		if (dayField.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			dayField.requestFocus();
			return;
		}
		
		if (yearField.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			yearField.requestFocus();
			return;
		}
		
		
		if (myLocale.toString().equals("en_US")) {
			String dob = new String(monthField.getText() + "-" + dayField.getText() + "-" + yearField.getText());
			if (!(dob.matches("\\d{2}-\\d{2}-\\d{4}"))) {
				alert.setContentText(invalidDateErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				return;
			}
		}
		
		if (myLocale.toString().equals("fr_FR")) {
			String dob = new String(dayField.getText() + "-" + monthField.getText() + "-" + yearField.getText());
			if (!(dob.matches("\\d{2}-\\d{2}-\\d{4}"))) {
				alert.setContentText(invalidDateErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				return;
			}
		}

		if (myLocale.toString().equals("en_US")) {
			if (areaCode.getText().isEmpty()) {
				alert.setContentText(nullFieldErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				areaCode.requestFocus();
				return;
			} else if (!(areaCode.getText().matches("\\d{3}"))) {
				alert.setContentText(invalidPhoneErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				return;
			} else if (phoneNumber.getText().isEmpty()) {
				alert.setContentText(nullFieldErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				phoneNumber.requestFocus();
				return;
			}
			if (!(phoneNumber.getText().matches("\\d{7}"))) {
				alert.setContentText(invalidPhoneErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				return;
			}
		}		

		if (myLocale.toString().equals("fr_FR")) {
			if (phoneNumber.getText().isEmpty()) {
				alert.setContentText(nullFieldErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				phoneNumber.requestFocus();
				return;
			}
			if (!(phoneNumber.getText().matches("\\d{11}"))) {
				alert.setContentText(invalidPhoneErrorMessage);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
				return;
			}
		}


		if (email.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			email.requestFocus();
			return;
		}

		if (troopID.getText().isEmpty()) {
			alert.setContentText(nullFieldErrorMessage);
			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					alert.close();
				}
			});
			troopID.requestFocus();
			return;
		}
		
		else {
			Properties p = setPropertiesObject();
			myTrans.stateChangeRequest("insertNewScout", p);
			displaySuccessMessage();
		}
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		props.setProperty("firstName", firstName.getText());
		if (!monthField.getText().isEmpty()) {
			props.setProperty("middleName", middleName.getText());
		}
		props.setProperty("lastName", lastName.getText());
		props.setProperty("troopID", troopID.getText());
		props.setProperty("email", email.getText());
		props.setProperty("status", status.getValue());
		
		
		String dob;
		String tele;
		DateFormat df;
		
		if (myLocale.toString().equals("fr_FR")) {
			dob = new String(dayField.getText() + "-" + monthField.getText() + "-" + yearField.getText());
			tele = phoneNumber.getText();
			df = new SimpleDateFormat("dd-MM-yyyy");
		}
		else {
			dob = new String(monthField.getText() + "-" + dayField.getText() + "-" + yearField.getText());
			tele = new String(areaCode.getText() + "-" + phoneNumber.getText());
			df = new SimpleDateFormat("MM-dd-yyyy");
		}
		
		props.setProperty("birthDate", dob);
		props.setProperty("phoneNumber", tele);
		Date date = new Date();
		props.setProperty("dateStatusUpdated", df.format(date));
		return props;
	}

	public void displaySuccessMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);	//Change this to Confirmation?
		alert.setHeaderText(addSuccessMessage);
		alert.setContentText(addAnotherMessage);
		
		ButtonType yesButtonType = new ButtonType(yesLabel);
		ButtonType noButtonType = new ButtonType(noLabel);
		
		alert.getButtonTypes().setAll(yesButtonType, noButtonType);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == yesButtonType) {
			clearTextFields();
			alert.close();
		}
		else {
			alert.close();
			myModel.stateChangeRequest("CancelAddScout", null);
		}
	}
	
	// ----------------------------------------------------------
	public void clearTextFields() {
		lastName.clear();
		firstName.clear();
		middleName.clear();
		if (myLocale.toString().equals("en_US"))
			areaCode.clear();
		phoneNumber.clear();
		email.clear();
		monthField.clear();
		dayField.clear();
		yearField.clear();
		troopID.clear();
		status.setValue(activeStatus);
	}
	
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
