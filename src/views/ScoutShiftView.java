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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import models.Session;
import models.Shift;
import models.TreeLotCoordinator;
import transactions.ScoutTransaction;
import userinterface.MessageView;
import views.View;
import javafx.*;

import java.awt.Graphics;
import java.awt.Shape;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	protected TextField scoutName1;
	protected TextField startTime1;
	protected TextField endTime1;
	protected TextField companionName1;
	protected TextField companionHours1;
	
	protected TextField scoutName2;
	protected TextField startTime2;
	protected TextField endTime2;
	protected TextField companionName2;
	protected TextField companionHours2;
	
	protected TextField scoutName3;
	protected TextField startTime3;
	protected TextField endTime3;
	protected TextField companionName3;
	protected TextField companionHours3;
	
	protected TextField scoutName4;
	protected TextField startTime4;
	protected TextField endTime4;
	protected TextField companionName4;
	protected TextField companionHours4;
	
	
	protected Text prompt;
	
	protected Button submitButton;
	protected Button cancelButton;

	protected ScoutCollection myScoutCollection;
	private int pos;
	private final int collectionSize;
	private ArrayList<Properties> shiftList = new ArrayList<Properties>();
	private Session mySession;
	protected MessageView statusLog;
	
	private boolean section2 = false;
	private boolean section3 = false;
	private boolean section4 = false;
	
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public ScoutShiftView(IModel scoutTrans) {
		super(scoutTrans, "ScoutShiftView");
		mySession = (Session) myModel.getState("Session");
		myScoutCollection = (ScoutCollection)myModel.getState("ScoutCollection");
		collectionSize = myScoutCollection.size();
		
		if (collectionSize >= 2)
			section2 = true;
		if (collectionSize >= 3)
			section3 = true;
		if (collectionSize == 4)
			section4 = true;
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		VBox scrollC = new VBox(10);
		//scrollC.setAlignment(Pos.CENTER_LEFT);
		scrollC.getChildren().add(createFormContent1());
		
		if (section2 == true)
			scrollC.getChildren().add(createFormContent2());
		
		if (section3 == true)
			scrollC.getChildren().add(createFormContent3());
		
		if (section4 == true)
			scrollC.getChildren().add(createFormContent4());
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(500, 500);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setContent(scrollC);
		
		
		container.getChildren().add(scrollPane);
		container.getChildren().add(createButtons());
		container.getChildren().add(createStatusLog(" "));
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
	private VBox createFormContent1() {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 25, 0, 25));
		vbox.setAlignment(Pos.CENTER);

		VBox scoutSection = new VBox(10);
		scoutSection.setPadding(new Insets(20, 120, 25, 150));
		scoutSection.setStyle("-fx-border-color: black; -fx-border-style: hidden hidden hidden hidden;");
		scoutSection.setAlignment(Pos.CENTER_LEFT);

		// -------------------------------------------------------------
		Text snLabel = new Text(nameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		snLabel.setFont(myFont);
		snLabel.setWrappingWidth(200);
		snLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(snLabel, 0, 0);
		scoutName1 = new TextField();
		Scout s = myScoutCollection.get(0);
		String fn = (String) s.getState("firstName");
		String ln = (String) s.getState("lastName");
		scoutName1.setText(fn + " " + ln);
		scoutName1.setEditable(false);
		scoutName1.setMouseTransparent(true);
		scoutName1.setFocusTraversable(false);
		scoutName1.setStyle("-fx-control-inner-background: #d3d3d3;");
		scoutName1.setPrefWidth(150);
		scoutName1.setMinWidth(150);
		scoutName1.setMaxWidth(150);
		// grid.add(scoutName1, 1, 0);
		scoutSection.getChildren().addAll(snLabel, scoutName1);

		// -------------------------------------------------------------
		Text cnLabel = new Text(companionNameLabel);
		cnLabel.setFont(myFont);
		cnLabel.setWrappingWidth(200);
		cnLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(cnLabel, 0, 1);
		companionName1 = new TextField();
		companionName1.setEditable(true);
		companionName1.setPrefWidth(150);
		companionName1.setMinWidth(150);
		companionName1.setMaxWidth(150);
		// grid.add(companionName1, 1, 1);
		scoutSection.getChildren().addAll(cnLabel, companionName1);

		// -------------------------------------------------------------
		Text stLabel = new Text(startLabel);
		stLabel.setFont(myFont);
		stLabel.setWrappingWidth(200);
		stLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(stLabel, 0, 2);
		startTime1 = new TextField();
		startTime1.setEditable(true);
		startTime1.setPrefWidth(100);
		startTime1.setMinWidth(100);
		startTime1.setMaxWidth(100);
		// grid.add(startTime1, 1, 2);
		scoutSection.getChildren().addAll(stLabel, startTime1);

		// -------------------------------------------------------------
		Text etLabel = new Text(endLabel);
		etLabel.setFont(myFont);
		etLabel.setWrappingWidth(200);
		etLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(etLabel, 0, 3);
		endTime1 = new TextField();
		endTime1.setEditable(true);
		endTime1.setPrefWidth(100);
		endTime1.setMinWidth(100);
		endTime1.setMaxWidth(100);
		// grid.add(endTime1, 1, 3);
		scoutSection.getChildren().addAll(etLabel, endTime1);

		// -------------------------------------------------------------
		Text chLabel = new Text(companionHoursLabel);
		chLabel.setFont(myFont);
		chLabel.setWrappingWidth(200);
		chLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(chLabel, 0, 4);
		companionHours1 = new TextField();
		companionHours1.setEditable(true);
		companionHours1.setPrefWidth(100);
		companionHours1.setMinWidth(100);
		companionHours1.setMaxWidth(100);
		// grid.add(companionHours1, 1, 4);
		scoutSection.getChildren().addAll(chLabel, companionHours1);

		// scoutSection.getChildren().addAll(grid);
		vbox.getChildren().add(scoutSection);

		return vbox;
	}

	// -------------------------------------------------------------
	private VBox createFormContent2() {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 25, 0, 25));
		vbox.setAlignment(Pos.CENTER);

		VBox scoutSection = new VBox(10);
		scoutSection.setPadding(new Insets(20, 120, 25, 150));
		scoutSection.setStyle("-fx-border-color: black; -fx-border-style: solid hidden hidden hidden;");
		scoutSection.setAlignment(Pos.CENTER_LEFT);

		// -------------------------------------------------------------
		Text snLabel = new Text(nameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		snLabel.setFont(myFont);
		snLabel.setWrappingWidth(200);
		snLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(snLabel, 0, 0);
		scoutName2 = new TextField();
		Scout s = myScoutCollection.get(1);
		String fn = (String) s.getState("firstName");
		String ln = (String) s.getState("lastName");
		scoutName2.setText(fn + " " + ln);
		scoutName2.setEditable(false);
		scoutName2.setMouseTransparent(true);
		scoutName2.setFocusTraversable(false);
		scoutName2.setStyle("-fx-control-inner-background: #d3d3d3;");
		scoutName2.setPrefWidth(150);
		scoutName2.setMinWidth(150);
		scoutName2.setMaxWidth(150);
		// grid.add(scoutName1, 1, 0);
		scoutSection.getChildren().addAll(snLabel, scoutName2);

		// -------------------------------------------------------------
		Text cnLabel = new Text(companionNameLabel);
		cnLabel.setFont(myFont);
		cnLabel.setWrappingWidth(200);
		cnLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(cnLabel, 0, 1);
		companionName2 = new TextField();
		companionName2.setEditable(true);
		companionName2.setPrefWidth(150);
		companionName2.setMinWidth(150);
		companionName2.setMaxWidth(150);
		// grid.add(companionName1, 1, 1);
		scoutSection.getChildren().addAll(cnLabel, companionName2);

		// -------------------------------------------------------------
		Text stLabel = new Text(startLabel);
		stLabel.setFont(myFont);
		stLabel.setWrappingWidth(200);
		stLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(stLabel, 0, 2);
		startTime2 = new TextField();
		startTime2.setEditable(true);
		startTime2.setPrefWidth(100);
		startTime2.setMinWidth(100);
		startTime2.setMaxWidth(100);
		// grid.add(startTime1, 1, 2);
		scoutSection.getChildren().addAll(stLabel, startTime2);

		// -------------------------------------------------------------
		Text etLabel = new Text(endLabel);
		etLabel.setFont(myFont);
		etLabel.setWrappingWidth(200);
		etLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(etLabel, 0, 3);
		endTime2 = new TextField();
		endTime2.setEditable(true);
		endTime2.setPrefWidth(100);
		endTime2.setMinWidth(100);
		endTime2.setMaxWidth(100);
		// grid.add(endTime1, 1, 3);
		scoutSection.getChildren().addAll(etLabel, endTime2);

		// -------------------------------------------------------------
		Text chLabel = new Text(companionHoursLabel);
		chLabel.setFont(myFont);
		chLabel.setWrappingWidth(200);
		chLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(chLabel, 0, 4);
		companionHours2 = new TextField();
		companionHours2.setEditable(true);
		companionHours2.setPrefWidth(100);
		companionHours2.setMinWidth(100);
		companionHours2.setMaxWidth(100);
		// grid.add(companionHours1, 1, 4);
		scoutSection.getChildren().addAll(chLabel, companionHours2);

		// scoutSection.getChildren().addAll(grid);
		vbox.getChildren().add(scoutSection);

		return vbox;
	}// -------------------------------------------------------------

	private VBox createFormContent3() {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 25, 0, 25));
		vbox.setAlignment(Pos.CENTER);

		VBox scoutSection = new VBox(10);
		scoutSection.setPadding(new Insets(20, 120, 25, 150));
		scoutSection.setStyle("-fx-border-color: black; -fx-border-style: solid hidden hidden hidden;");
		scoutSection.setAlignment(Pos.CENTER_LEFT);

		// -------------------------------------------------------------
		Text snLabel = new Text(nameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		snLabel.setFont(myFont);
		snLabel.setWrappingWidth(200);
		snLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(snLabel, 0, 0);
		scoutName3 = new TextField();
		Scout s = myScoutCollection.get(2);
		String fn = (String) s.getState("firstName");
		String ln = (String) s.getState("lastName");
		scoutName3.setText(fn + " " + ln);
		scoutName3.setEditable(false);
		scoutName3.setMouseTransparent(true);
		scoutName3.setFocusTraversable(false);
		scoutName3.setStyle("-fx-control-inner-background: #d3d3d3;");
		scoutName3.setPrefWidth(150);
		scoutName3.setMinWidth(150);
		scoutName3.setMaxWidth(150);
		// grid.add(scoutName1, 1, 0);
		scoutSection.getChildren().addAll(snLabel, scoutName3);

		// -------------------------------------------------------------
		Text cnLabel = new Text(companionNameLabel);
		cnLabel.setFont(myFont);
		cnLabel.setWrappingWidth(200);
		cnLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(cnLabel, 0, 1);
		companionName3 = new TextField();
		companionName3.setEditable(true);
		companionName3.setPrefWidth(150);
		companionName3.setMinWidth(150);
		companionName3.setMaxWidth(150);
		// grid.add(companionName1, 1, 1);
		scoutSection.getChildren().addAll(cnLabel, companionName3);

		// -------------------------------------------------------------
		Text stLabel = new Text(startLabel);
		stLabel.setFont(myFont);
		stLabel.setWrappingWidth(200);
		stLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(stLabel, 0, 2);
		startTime3 = new TextField();
		startTime3.setEditable(true);
		startTime3.setPrefWidth(100);
		startTime3.setMinWidth(100);
		startTime3.setMaxWidth(100);
		// grid.add(startTime1, 1, 2);
		scoutSection.getChildren().addAll(stLabel, startTime3);

		// -------------------------------------------------------------
		Text etLabel = new Text(endLabel);
		etLabel.setFont(myFont);
		etLabel.setWrappingWidth(200);
		etLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(etLabel, 0, 3);
		endTime3 = new TextField();
		endTime3.setEditable(true);
		endTime3.setPrefWidth(100);
		endTime3.setMinWidth(100);
		endTime3.setMaxWidth(100);
		// grid.add(endTime1, 1, 3);
		scoutSection.getChildren().addAll(etLabel, endTime3);

		// -------------------------------------------------------------
		Text chLabel = new Text(companionHoursLabel);
		chLabel.setFont(myFont);
		chLabel.setWrappingWidth(200);
		chLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(chLabel, 0, 4);
		companionHours3 = new TextField();
		companionHours3.setEditable(true);
		companionHours3.setPrefWidth(100);
		companionHours3.setMinWidth(100);
		companionHours3.setMaxWidth(100);
		// grid.add(companionHours1, 1, 4);
		scoutSection.getChildren().addAll(chLabel, companionHours3);

		// scoutSection.getChildren().addAll(grid);
		vbox.getChildren().add(scoutSection);

		return vbox;
	}// -------------------------------------------------------------

	private VBox createFormContent4() {
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(0, 25, 0, 25));
		vbox.setAlignment(Pos.CENTER);

		VBox scoutSection = new VBox(10);
		scoutSection.setPadding(new Insets(20, 120, 25, 150));
		scoutSection.setStyle("-fx-border-color: black; -fx-border-style: solid hidden hidden hidden;");
		scoutSection.setAlignment(Pos.CENTER_LEFT);

		// -------------------------------------------------------------
		Text snLabel = new Text(nameLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		snLabel.setFont(myFont);
		snLabel.setWrappingWidth(200);
		snLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(snLabel, 0, 0);
		scoutName4 = new TextField();
		Scout s = myScoutCollection.get(3);
		String fn = (String) s.getState("firstName");
		String ln = (String) s.getState("lastName");
		scoutName4.setText(fn + " " + ln);
		scoutName4.setEditable(false);
		scoutName4.setMouseTransparent(true);
		scoutName4.setFocusTraversable(false);
		scoutName4.setStyle("-fx-control-inner-background: #d3d3d3;");
		scoutName4.setPrefWidth(150);
		scoutName4.setMinWidth(150);
		scoutName4.setMaxWidth(150);
		// grid.add(scoutName1, 1, 0);
		scoutSection.getChildren().addAll(snLabel, scoutName4);

		// -------------------------------------------------------------
		Text cnLabel = new Text(companionNameLabel);
		cnLabel.setFont(myFont);
		cnLabel.setWrappingWidth(200);
		cnLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(cnLabel, 0, 1);
		companionName4 = new TextField();
		companionName4.setEditable(true);
		companionName4.setPrefWidth(150);
		companionName4.setMinWidth(150);
		companionName4.setMaxWidth(150);
		// grid.add(companionName1, 1, 1);
		scoutSection.getChildren().addAll(cnLabel, companionName4);

		// -------------------------------------------------------------
		Text stLabel = new Text(startLabel);
		stLabel.setFont(myFont);
		stLabel.setWrappingWidth(200);
		stLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(stLabel, 0, 2);
		startTime4 = new TextField();
		startTime4.setEditable(true);
		startTime4.setPrefWidth(100);
		startTime4.setMinWidth(100);
		startTime4.setMaxWidth(100);
		// grid.add(startTime1, 1, 2);
		scoutSection.getChildren().addAll(stLabel, startTime4);

		// -------------------------------------------------------------
		Text etLabel = new Text(endLabel);
		etLabel.setFont(myFont);
		etLabel.setWrappingWidth(200);
		etLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(etLabel, 0, 3);
		endTime4 = new TextField();
		endTime4.setEditable(true);
		endTime4.setPrefWidth(100);
		endTime4.setMinWidth(100);
		endTime4.setMaxWidth(100);
		// grid.add(endTime1, 1, 3);
		scoutSection.getChildren().addAll(etLabel, endTime4);

		// -------------------------------------------------------------
		Text chLabel = new Text(companionHoursLabel);
		chLabel.setFont(myFont);
		chLabel.setWrappingWidth(200);
		chLabel.setTextAlignment(TextAlignment.LEFT);
		// grid.add(chLabel, 0, 4);
		companionHours4 = new TextField();
		companionHours4.setEditable(true);
		companionHours4.setPrefWidth(100);
		companionHours4.setMinWidth(100);
		companionHours4.setMaxWidth(100);
		// grid.add(companionHours1, 1, 4);
		scoutSection.getChildren().addAll(chLabel, companionHours4);

		// scoutSection.getChildren().addAll(grid);
		vbox.getChildren().add(scoutSection);

		return vbox;
	}	
	// -------------------------------------------------------------
	public HBox createButtons() {
		
		HBox doneCont = new HBox(10);
		doneCont.setPadding(new Insets(20, 0, 5, 0));
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
				myModel.stateChangeRequest("CancelAddScout", null);
			}
		});
		// -------------------------------------------------------------

		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				processAction(e);
			}
		});

		doneCont.getChildren().add(cancelButton);
		return doneCont;
	}
	
	

	// Create the status log field
	// -------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	// ---------------------------------------------------------
	public void processAction(Event e) {
		
		if (processAction1() == false) {
			return;
		}
		setPropertiesObject1();
		
		if (section2 == true) {
			if (processAction2() == false) {
				return;
			}
			setPropertiesObject2();
		}
		
		if (section3 == true) {
			if (processAction3() == false) {
				return;
			}
			setPropertiesObject3();
		}
		
		if (section4 == true) {
			if (processAction4() == false) {
				return;
			}
			setPropertiesObject4();
		}
		
		myModel.stateChangeRequest("insertShifts", shiftList);
		//Show Message
		myModel.stateChangeRequest("UpdateTLC", null);
	}
	
	
	// ---------------------------------------------------------
	public boolean processAction1() {

		//Check if the fields are empty 
		if (companionName1.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionName1.requestFocus();
			return false;
		}
		
		else if (startTime1.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			startTime1.requestFocus();
			return false;
		}
		else if (endTime1.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			endTime1.requestFocus();
			return false;
		}
		
		else if (companionHours1.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionHours1.requestFocus();
			return false;
		}
		
		return true;
	}

	// ---------------------------------------------------------
	public boolean processAction2() {

		// Check if the fields are empty
		if (companionName2.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionName2.requestFocus();
			return false;
		}

		else if (startTime2.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			startTime2.requestFocus();
			return false;
		} else if (endTime2.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			endTime2.requestFocus();
			return false;
		}

		else if (companionHours2.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionHours2.requestFocus();
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------
	public boolean processAction3() {

		// Check if the fields are empty
		if (companionName3.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionName3.requestFocus();
			return false;
		}

		else if (startTime3.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			startTime3.requestFocus();
			return false;
		} else if (endTime3.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			endTime3.requestFocus();
			return false;
		}

		else if (companionHours3.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionHours3.requestFocus();
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------
	public boolean processAction4() {

		// Check if the fields are empty
		if (companionName4.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionName4.requestFocus();
			return false;
		}

		else if (startTime4.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			startTime4.requestFocus();
			return false;
		} else if (endTime4.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			endTime4.requestFocus();
			return false;
		}

		else if (companionHours4.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			companionHours4.requestFocus();
			return false;
		}
		return true;
	}
	
	
	// ----------------------------------------------------------
	public void setPropertiesObject1() {
		Properties props = new Properties();
		Scout s = myScoutCollection.get(0);
		props.setProperty("sessionID", (String) myModel.getState("SessionID"));
		props.setProperty("scoutID", (String) s.getState("scoutID"));
		props.setProperty("companionName", companionName1.getText());
		props.setProperty("startTime", startTime1.getText());
		props.setProperty("endTime", endTime1.getText());
		props.setProperty("companionHours", companionHours1.getText());
		shiftList.add(props);
	}
	
	// ----------------------------------------------------------
	public void setPropertiesObject2() {
		Properties props = new Properties();
		Scout s = myScoutCollection.get(1);
		props.setProperty("sessionID", (String) myModel.getState("SessionID"));
		props.setProperty("scoutID", (String) s.getState("scoutID"));
		props.setProperty("companionName", companionName2.getText());
		props.setProperty("startTime", startTime2.getText());
		props.setProperty("endTime", endTime2.getText());
		props.setProperty("companionHours", companionHours2.getText());
		shiftList.add(props);
	}

	// ----------------------------------------------------------
	public void setPropertiesObject3() {
		Properties props = new Properties();
		Scout s = myScoutCollection.get(2);
		props.setProperty("sessionID", (String) myModel.getState("SessionID"));
		props.setProperty("scoutID", (String) s.getState("scoutID"));
		props.setProperty("companionName", companionName3.getText());
		props.setProperty("startTime", startTime3.getText());
		props.setProperty("endTime", endTime3.getText());
		props.setProperty("companionHours", companionHours3.getText());
		shiftList.add(props);
	}

	// ----------------------------------------------------------
	public void setPropertiesObject4() {
		Properties props = new Properties();
		Scout s = myScoutCollection.get(3);
		props.setProperty("sessionID", (String) myModel.getState("SessionID"));
		props.setProperty("scoutID", (String) s.getState("scoutID"));
		props.setProperty("companionName", companionName4.getText());
		props.setProperty("startTime", startTime4.getText());
		props.setProperty("endTime", endTime4.getText());
		props.setProperty("companionHours", companionHours4.getText());
		shiftList.add(props);
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
