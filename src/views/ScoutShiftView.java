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
import models.ScoutCollection;
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
public class ScoutShiftView extends View {

	// GUI components
	
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String indexLabel1 = new String(myResourceBundle.getString("indexLabel1"));
	protected final String indexLabel2 = new String(myResourceBundle.getString("indexLabel2"));
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String nextScoutButtonLabel = new String(myResourceBundle.getString("nextScoutButtonLabel"));
	protected final String previousScoutButtonLabel = new String(myResourceBundle.getString("previousScoutButtonLabel"));
	
	protected final String nameLabel = new String(myResourceBundle.getString("nameLabel"));
	protected final String companionNameLabel = new String(myResourceBundle.getString("companionNameLabel"));
	protected final String startLabel = new String(myResourceBundle.getString("startLabel"));
	protected final String endLabel = new String(myResourceBundle.getString("endLabel"));
	protected final String companionHoursLabel = new String(myResourceBundle.getString("companionHoursLabel"));
	
	
	protected final String invalidTimeErrorMessage = new String(myResourceBundle.getString("invalidTimeErrorMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	
	
	
	// ----------------------------------------------------------
	protected TextField scoutName;
	protected TextField startTime;
	protected TextField endTime;
	protected TextField companionName;
	protected TextField companionHours;
	
	protected Text prompt;
	
	protected Button prevousScoutButton;
	protected Button nextScoutButton;
	protected Button submitButton;
	protected Button cancelButton;

	protected ScoutCollection myScoutCollection;
	private int pos;
	private final String collectionSize;
	
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public ScoutShiftView(IModel scoutTrans) {
		super(scoutTrans, "ScoutShiftView");
		myScoutCollection = (ScoutCollection)myModel.getState("ScoutCollection");
		int size = myScoutCollection.size();
		collectionSize = Integer.toString(size);
		pos = 1;
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

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
		vbox.setPadding(new Insets(20, 25, 25, 25));

		//Scout 1 of 3
		
		prompt = new Text(indexLabel1 + " " + pos + " " + indexLabel2 + " " + collectionSize);
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		
		vbox.getChildren().add(prompt);
		
		VBox scoutSection = new VBox(10);
		scoutSection.setPadding(new Insets(20, 25, 25, 25));
		scoutSection.setStyle("-fx-border-color: black; -fx-border-style: solid solid solid solid;");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 25, 25, 25));
		
		
		// -------------------------------------------------------------
		Text snLabel = new Text(nameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		snLabel.setFont(myFont);
		snLabel.setWrappingWidth(100);
		snLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(snLabel, 0, 0);
		scoutName = new TextField();
		scoutName.setEditable(false);
		scoutName.setMouseTransparent(true);
		scoutName.setFocusTraversable(false);
		scoutName.setStyle("-fx-control-inner-background: #d3d3d3;");
		scoutName.setPrefWidth(100);
		scoutName.setMinWidth(100);
		scoutName.setMaxWidth(100);
		grid.add(scoutName, 1, 0);
		
		// -------------------------------------------------------------
		Text cnLabel = new Text(companionNameLabel);
		cnLabel.setFont(myFont);
		cnLabel.setWrappingWidth(100);
		cnLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(cnLabel, 0, 1);
		companionName = new TextField();
		companionName.setEditable(true);
		companionName.setPrefWidth(100);
		companionName.setMinWidth(100);
		companionName.setMaxWidth(100);
		grid.add(companionName, 1, 1);

		// -------------------------------------------------------------
		Text stLabel = new Text(startLabel);
		stLabel.setFont(myFont);
		stLabel.setWrappingWidth(100);
		stLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(stLabel, 0, 2);
		startTime = new TextField();
		startTime.setEditable(true);
		startTime.setPrefWidth(100);
		startTime.setMinWidth(100);
		startTime.setMaxWidth(100);
		grid.add(startTime, 1, 2);
		
		// -------------------------------------------------------------
		Text etLabel = new Text(endLabel);
		etLabel.setFont(myFont);
		etLabel.setWrappingWidth(100);
		etLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(etLabel, 0, 3);
		endTime = new TextField();
		endTime.setEditable(true);
		endTime.setPrefWidth(100);
		endTime.setMinWidth(100);
		endTime.setMaxWidth(100);
		grid.add(endTime, 1, 3);
		
		// -------------------------------------------------------------
		Text chLabel = new Text(companionHoursLabel);
		chLabel.setFont(myFont);
		chLabel.setWrappingWidth(100);
		chLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(chLabel, 0, 4);
		companionHours = new TextField();
		companionHours.setEditable(true);
		companionHours.setPrefWidth(100);
		companionHours.setMinWidth(100);
		companionHours.setMaxWidth(100);
		grid.add(companionHours, 1, 4);

		// -------------------------------------------------------------
		HBox doneCont = new HBox(10);
		doneCont.setPadding(new Insets(0, 0, 5, 0));
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		//submitButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

		doneCont.getChildren().add(submitButton);

		cancelButton = new Button(cancelButtonLabel);
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		// -------------------------------------------------------------
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

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
				prompt.setText(indexLabel1 + " " + pos + " " + indexLabel2 + " " + collectionSize);
				//processAction(e);
			}
		});

		doneCont.getChildren().add(cancelButton);

		scoutSection.getChildren().add(grid);
		vbox.getChildren().add(scoutSection);
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
/**
		if (firstName.getText().isEmpty()) {
	//		statusLog.displayErrorMessage(nullFieldErrorMessage);
			firstName.requestFocus();
		}
		
		else if (middleName.getText().isEmpty()) {
		//	statusLog.displayErrorMessage(nullFieldErrorMessage);
			middleName.requestFocus();
		}
				
		else {
			Properties p = setPropertiesObject();
			myModel.stateChangeRequest("insertNewScout", p);
	//		statusLog.displayMessage(addSuccessMessage);
			clearTextFields();
		}
		*/
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		/*
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
		*/
		return props;
	}

	// ----------------------------------------------------------
	public void clearTextFields() {
		/**
		lastName.clear();
		firstName.clear();
		middleName.clear();
		dateOfBirth.clear();
		phoneNumber.clear();
		email.clear();
		troopID.clear();
		status.setValue(activeStatus);
	*/
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
