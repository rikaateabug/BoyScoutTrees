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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Session;
import models.TreeLotCoordinator;
import transactions.SessionTransaction;
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
public class OpenSessionView extends View {

	// GUI components

	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String dateLabel = new String(myResourceBundle.getString("dateLabel"));
	protected final String startTimeLabel = new String(myResourceBundle.getString("startTimeLabel"));
	protected final String currentDateLabel = new String(myResourceBundle.getString("currentDateLabel"));
	protected final String currentTimeLabel = new String(myResourceBundle.getString("currentTimeLabel"));
	protected final String startingCashLabel = new String(myResourceBundle.getString("startingCashLabel"));
	protected final String notesLabel = new String(myResourceBundle.getString("notesLabel"));
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String invalidDateErrorMessage = new String(myResourceBundle.getString("invalidDateErrorMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));

	protected TextField startCash;
	protected TextField custDate;
	protected TextField custTime;
	protected TextArea notes;

	protected RadioButton defaultDate;
	protected RadioButton customDate;

	protected RadioButton defaultTime;
	protected RadioButton customTime;

	protected Button submitButton;
	protected Button cancelButton;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public OpenSessionView(IModel sessTrans) {
		super(sessTrans, "OpenSessionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

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
		container.setPadding(new Insets(20, 5, 5, 5));

		Text titleText = new Text(titleLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
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
		// vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setPadding(new Insets(20, 25, 25, 25));

		Text dLabel = new Text(dateLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 15);
		dLabel.setFont(myFont);
		dLabel.setWrappingWidth(100);
		// dLabel.setTextAlignment(TextAlignment.RIGHT);
		custDate = new TextField();
		custDate.setEditable(false);
		custDate.setMouseTransparent(true);
		custDate.setFocusTraversable(false);
		custDate.setStyle("-fx-control-inner-background: #d3d3d3;");

		final ToggleGroup group = new ToggleGroup();
		defaultDate = new RadioButton(currentDateLabel);
		defaultDate.setToggleGroup(group);
		defaultDate.setSelected(true);

		customDate = new RadioButton("");
		customDate.setToggleGroup(group);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					if (group.getSelectedToggle().equals(defaultDate)) {
						custDate.setEditable(false);
						custDate.setMouseTransparent(true);
						custDate.setFocusTraversable(false);
						custDate.setStyle("-fx-control-inner-background: #d3d3d3;");
					}

					else if (group.getSelectedToggle().equals(customDate)) {
						custDate.setEditable(true);
						custDate.setMouseTransparent(false);
						custDate.setFocusTraversable(true);
						custDate.requestFocus();
						custDate.setStyle("-fx-control-inner-background: #ffffff;");
					}
				}
			}
		});

		HBox custDateRow = new HBox(5);
		// custDateRow.setAlignment(Pos.CENTER_LEFT);
		//custDateRow.getChildren().addAll(customDate, custDate);
		// -------------------------------------------------------------
		Text sLabel = new Text(startTimeLabel);
		sLabel.setFont(myFont);
		sLabel.setWrappingWidth(100);
		custTime = new TextField();
		custTime.setEditable(false);
		custTime.setMouseTransparent(true);
		custTime.setFocusTraversable(false);
		custTime.setStyle("-fx-control-inner-background: #d3d3d3;");

		final ToggleGroup group2 = new ToggleGroup();
		defaultTime = new RadioButton(currentTimeLabel);
		defaultTime.setToggleGroup(group2);
		defaultTime.setSelected(true);

		customTime = new RadioButton("");
		customTime.setToggleGroup(group2);

		group2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group2.getSelectedToggle() != null) {
					if (group2.getSelectedToggle().equals(defaultTime)) {
						custTime.setEditable(false);
						custTime.setMouseTransparent(true);
						custTime.setFocusTraversable(false);
						custTime.setStyle("-fx-control-inner-background: #d3d3d3;");
					}

					else if (group2.getSelectedToggle().equals(customTime)) {
						custTime.setEditable(true);
						custTime.setMouseTransparent(false);
						custTime.setFocusTraversable(true);
						custTime.requestFocus();
						custTime.setStyle("-fx-control-inner-background: #ffffff;");
					}
				}
			}
		});

		HBox custDateRow2 = new HBox(5);
		// custDateRow2.setAlignment(Pos.CENTER_LEFT);
		//custDateRow2.getChildren().addAll(customTime, custTime);
		// -------------------------------------------------------------

		Text scLabel = new Text(startingCashLabel);
		scLabel.setFont(myFont);
		scLabel.setWrappingWidth(100);
		startCash = new TextField();
		startCash.setPrefWidth(100);
		startCash.setMinWidth(100);
		startCash.setMaxWidth(100);

		Text nLabel = new Text(notesLabel);
		nLabel.setFont(myFont);
		nLabel.setWrappingWidth(100);
		notes = new TextArea();

		// -------------------------------------------------------------
		VBox section1 = new VBox(10);
		section1.setPadding(new Insets(0, 0, 0, 20));
		//section1.getChildren().addAll(dLabel, defaultDate, custDateRow);

		VBox section2 = new VBox(10);
		section2.setPadding(new Insets(0, 0, 0, 20));
		//section2.getChildren().addAll(sLabel, defaultTime, custDateRow2);

		VBox section3 = new VBox(10);
		section3.setPadding(new Insets(0, 0, 0, 20));
		section3.getChildren().addAll(scLabel, startCash);

		VBox section4 = new VBox(10);
		section4.setPadding(new Insets(0, 0, 0, 20));
		section4.getChildren().addAll(nLabel, notes);

		VBox spacing = new VBox(50);
		spacing.setPadding(new Insets(0, 0, 20, 0));
		VBox spacing2 = new VBox(50);
		spacing2.setPadding(new Insets(0, 0, 20, 0));
		VBox spacing3 = new VBox(50);
		spacing3.setPadding(new Insets(0, 0, 20, 0));

		vbox.getChildren().addAll(section1, spacing, section2, spacing2, section3, spacing3, section4);
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
				clearErrorMessage();
				//myModel.stateChangeRequest("CancelAddScout", null);
				myModel.stateChangeRequest("UpdateTLC", null);	//This needs to be moved to the part where you confim, testing now
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
		
		if (startCash.getText().isEmpty()) {
			System.out.println("Starting cash can't be empty");
			startCash.requestFocus();
			return;
		}
		else if (!(startCash.getText().matches("(\\d)+(\\.\\d{2})?"))) {
			System.out.println("Starting cash invalid");
			startCash.requestFocus();
			return;
		}
		
		if (customDate.isSelected() == true) {
			//Check the format (isn't null)
			date = "custom";
		}
		else if (defaultDate.isSelected() == true) {
			date = "default";
		}
		
		if (customTime.isSelected() == true) {
			//Check the format (isn't null)
			time = "custom";
		}
		else if (defaultTime.isSelected() == true) {
			time = "default";
		}
		Properties p = setPropertiesObject(date, time);
		myModel.stateChangeRequest("selectScouts", p);
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject(String date, String time) {

		Properties props = new Properties();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		
		if (date.equals("default")) {
			props.setProperty("startDate", df.format(d));
		}
		else if (date.equals("custom")) {
			//Figure this out
			
		}
		
		if (time.equals("default")) {
			props.setProperty("startTime", tf.format(d));
		}
		else if (time.equals("custom")) {
			//Figure this out
		}
		
		if (notes.getText().isEmpty()) {
			props.setProperty("notes", "");
		}
		else {
			props.setProperty("notes", notes.getText());
		}
		
		
		props.setProperty("startingCash", startCash.getText());
		
		
		return props;
	}

	// ----------------------------------------------------------
	public void clearTextFields() {
		/**
		 * lastName.clear(); firstName.clear(); middleName.clear();
		 * dateOfBirth.clear(); phoneNumber.clear(); email.clear();
		 * troopID.clear(); status.setValue(activeStatus);
		 */
	}

	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		// statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	// ----------------------------------------------------------
	public void displayMessage(String message) {
		// statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		// statusLog.clearErrorMessage();
	}

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
