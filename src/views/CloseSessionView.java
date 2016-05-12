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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Session;
import models.TransactionCollection;
import models.TreeLotCoordinator;
import transactions.SessionTransaction;
import userinterface.MessageView;
import views.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Properties;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Position.Bias;

// project imports
import impresario.IModel;

/** The class containing the Account View for the ATM application */
// ==============================================================
public class CloseSessionView extends View {

	// GUI components

	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String dateLabel = new String(myResourceBundle.getString("dateLabel"));
	protected final String startTimeLabel = new String(myResourceBundle.getString("startTimeLabel"));
	protected final String currentDateLabel = new String(myResourceBundle.getString("currentDateLabel"));
	protected final String currentTimeLabel = new String(myResourceBundle.getString("currentTimeLabel"));
	protected final String startingCashLabel = new String(myResourceBundle.getString("startingCashLabel"));
	protected final String endingCashLabel = new String(myResourceBundle.getString("endingCashLabel"));
	protected final String totalCheckTransactionsLabel = new String(myResourceBundle.getString("totalCheckTransactionsLabel"));
	protected final String notesLabel = new String(myResourceBundle.getString("notesLabel"));
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String invalidDateErrorMessage = new String(myResourceBundle.getString("invalidDateErrorMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	protected final String checkLabel = new String(myResourceBundle.getString("checkLabel"));
	protected final String cashLabel = new String(myResourceBundle.getString("cashLabel"));
	protected final String successMessageLabel = new String(myResourceBundle.getString("successMessageLabel"));
	
	
	protected TextField startCash;
	protected TextField endCash;
	protected TextField custDate;
	protected TextField checks;
	
	protected TextField custTime;
	protected TextArea notes;

	protected Session mySession;
	protected TransactionCollection mySales;
	protected Button submitButton;
	protected Button cancelButton;
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public CloseSessionView(IModel sessTrans) {
		super(sessTrans, "CloseSessionView");
		
		mySession = (Session)myModel.getState("Session");
		String strID = (String)mySession.getState("sessionID");
		//System.out.println("The session id is: " + strID);
		mySales = new TransactionCollection();
		mySales.findAllTransactionsFromSession(strID);
		System.out.println("The size of mySales is " + mySales.size());
		
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		
		container.setPadding(new Insets(25, 25, 25, 25));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());
		//container.getChildren().add(createStatusLog(" "));
		getChildren().add(container);
	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(20, 5, 5, 5));

		Text titleText = new Text(titleLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		//titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);
		//vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setPadding(new Insets(5, 25, 5, 25));

		Text dLabel = new Text(dateLabel);
		
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 14);
		dLabel.setFont(myFont);
		//dLabel.setWrappingWidth(100);
		// dLabel.setTextAlignment(TextAlignment.RIGHT);
		custDate = new TextField();
		custDate.setText((String)mySession.getState("startDate"));
		custDate.setEditable(false);
		custDate.setMouseTransparent(true);
		custDate.setFocusTraversable(false);
		custDate.setPrefWidth(100);
		custDate.setMinWidth(100);
		custDate.setMaxWidth(100);
		custDate.setStyle("-fx-control-inner-background: #d3d3d3;");

		Text stLabel = new Text(startTimeLabel);
		stLabel.setFont(myFont);
		//stLabel.setWrappingWidth(100);
		custTime = new TextField();
		custTime.setText((String)mySession.getState("startTime"));
		custTime.setEditable(false);
		custTime.setMouseTransparent(true);
		custTime.setFocusTraversable(false);
		custTime.setPrefWidth(100);
		custTime.setMinWidth(100);
		custTime.setMaxWidth(100);
		custTime.setStyle("-fx-control-inner-background: #d3d3d3;");

		Text scLabel = new Text(startingCashLabel);
		scLabel.setFont(myFont);
		//scLabel.setWrappingWidth(100);
		startCash = new TextField();
		startCash.setPrefWidth(100);
		startCash.setMinWidth(100);
		startCash.setMaxWidth(100);
		startCash.setText((String)mySession.getState("startingCash"));
		startCash.setEditable(false);
		startCash.setMouseTransparent(true);
		startCash.setFocusTraversable(false);
		startCash.setStyle("-fx-control-inner-background: #d3d3d3;");

		Text ecLabel = new Text(endingCashLabel);
		ecLabel.setFont(myFont);
		//ecLabel.setWrappingWidth(100);
		endCash = new TextField();
		endCash.setPrefWidth(100);
		endCash.setMinWidth(100);
		endCash.setMaxWidth(100);
		
		double end = mySales.getEndingCash(cashLabel); 
		System.out.println("The ending cash is: " + end);
		endCash.setText(Double.toString(end) + "0");
		
		Text tcLabel = new Text(totalCheckTransactionsLabel);
		tcLabel.setFont(myFont);
		//tcLabel.setWrappingWidth(100);
		checks = new TextField();
		checks.setPrefWidth(100);
		checks.setMinWidth(100);
		checks.setMaxWidth(100);
		
		int checksAmt = mySales.getTotalCheckTransactions(checkLabel); 
		checks.setText(Integer.toString(checksAmt));
		
		Text noLabel = new Text(notesLabel);
		noLabel.setFont(myFont);
		notes = new TextArea();
		notes.setText((String)mySession.getState("notes"));

		// -------------------------------------------------------------
		VBox section1 = new VBox(10);
		
		section1.setPadding(new Insets(0, 0, 0, 20));
		section1.getChildren().addAll(dLabel, custDate);

		VBox section2 = new VBox(10);
		section2.setPadding(new Insets(0, 0, 0, 20));
		section2.getChildren().addAll(stLabel, custTime);

		VBox section3 = new VBox(10);
		section3.setPadding(new Insets(0, 0, 0, 20));
		section3.getChildren().addAll(scLabel, startCash);

		VBox section4 = new VBox(10);
		section4.setPadding(new Insets(0, 0, 0, 20));
		section4.getChildren().addAll(ecLabel, endCash);

		VBox section5 = new VBox(10);
		section5.setPadding(new Insets(0, 0, 0, 20));
		section5.getChildren().addAll(tcLabel, checks);

		VBox section6 = new VBox(10);
		section6.setPadding(new Insets(0, 0, 0, 20));
		section6.getChildren().addAll(noLabel, notes);		
		
		VBox spacing = new VBox(50);
		spacing.setPadding(new Insets(0, 0, 20, 0));
		VBox spacing2 = new VBox(50);
		spacing2.setPadding(new Insets(0, 0, 20, 0));
		VBox spacing3 = new VBox(50);
		spacing3.setPadding(new Insets(0, 0, 20, 0));

		vbox.getChildren().addAll(section1, spacing, section2, spacing2, section3, spacing3, section4, section5, section6);
		// -------------------------------------------------------------

		HBox doneCont = new HBox(10);
		doneCont.setPadding(new Insets(30, 0, 5, 0));
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button(submitButtonLabel);
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		// submitButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");

		doneCont.getChildren().add(submitButton);

		cancelButton = new Button(cancelButtonLabel);
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		// -------------------------------------------------------------
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

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

		doneCont.getChildren().add(cancelButton);

		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// Create the status log field
	// -------------------------------------------------------------
	/**
	 * protected MessageView createStatusLog(String initialMessage) { statusLog
	 * = new MessageView(initialMessage);
	 * 
	 * return statusLog; }
	 */
	// ---------------------------------------------------------
	public void processAction(Event e) {

		String time = new String();
		String date = new String();
		
		if (endCash.getText().isEmpty()) {
			System.out.println(nullFieldErrorMessage);
			endCash.requestFocus();
			return;
		}
		else if (!(endCash.getText().matches("(\\d)+(\\.\\d{2})?"))) {
			System.out.println(nullFieldErrorMessage);
			endCash.requestFocus();
			return;
		}
		
		else if (checks.getText().isEmpty()) {
			System.out.println(nullFieldErrorMessage);
			checks.requestFocus();
			return;
		}
		else {
			Properties p = setPropertiesObject();
			myModel.stateChangeRequest("closeSession", p);
			showConfirmationMessage();
			myModel.stateChangeRequest("UpdateTreeLotCoordinator", null);
		}
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {

		Properties props = new Properties();
		DateFormat tf = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		
		if (!(notes.getText().isEmpty())) {
			props.setProperty("notes", notes.getText());
		}
		
		props.setProperty("sessionID", (String)mySession.getState("sessionID"));
		props.setProperty("totalCheckTrans", checks.getText());
		props.setProperty("endingCash", endCash.getText());
		props.setProperty("endTime", tf.format(d));

		
		return props;
	}

	// -------------------------------------------------------------
	public void showConfirmationMessage() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(successMessageLabel);

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				alert.close();
				myModel.stateChangeRequest("CancelAddScout", null);
			}
		});
	}

	// ----------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
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

	

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
