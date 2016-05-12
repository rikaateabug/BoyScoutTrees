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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.util.Locale;
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
	protected final String successLabel = new String(myResourceBundle.getString("successLabel"));

	
	
	// ----------------------------------------------------------
	protected Text prompt;
	protected Button submitButton;
	protected Button cancelButton;
	protected ScoutCollection myScoutCollection;
	private final int collectionSize;
	private ArrayList<Properties> shiftList = new ArrayList<Properties>();
	private Session mySession;
	protected MessageView statusLog;
	private Locale myLocale;
	
	private Section section1;
	private Section section2;
	private Section section3;
	private Section section4;
	
	
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public ScoutShiftView(IModel scoutTrans) {
		super(scoutTrans, "ScoutShiftView");
		mySession = (Session) myModel.getState("Session");
		myScoutCollection = (ScoutCollection)myModel.getState("ScoutCollection");
		collectionSize = myScoutCollection.size();
		
		//Check if the locale is US to see whether am/pm is needed
		myLocale = (Locale) myModel.getState("Locale");
		boolean amPm;
		if (myLocale.toString().equals("en_US")) {
			amPm = true;
		}
		else {
			amPm = false;
		}
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		VBox mySections = new VBox(10);
		
		mySections.setPadding(new Insets(20, 5, 20, 5));
		
		section1 = new Section(true, 0, amPm);
		
		if (collectionSize >= 2)
			section2 = new Section(true, 1, amPm);
		else
			section2 = new Section(false, 1, amPm);
		
		if (collectionSize >= 3)
			section3 = new Section(true, 2, amPm);
		else
			section3 = new Section(false, 2, amPm);
		
		if (collectionSize == 4)
			section4 = new Section(true, 3, amPm);
		else
			section4 = new Section(false, 3, amPm);
		
		
		mySections.getChildren().add(section1.getVBox());
		mySections.getChildren().add(section2.getVBox());
		mySections.getChildren().add(section3.getVBox());
		mySections.getChildren().add(section4.getVBox());
		
		container.getChildren().add(mySections);
		
		container.getChildren().add(createButtons());
		
		getChildren().add(container);

	}

	// Create the title container
	// -------------------------------------------------------------
	private Node createTitle() {
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		container.setPadding(new Insets(20, 10, 30, 10));
		
		
		Text titleText = new Text(titleLabel);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		//titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// -------------------------------------------------------------
	// Inner Section Class, used for this view
	public class Section {
		//instance variables
		private TextField scoutName;
		private TextField startTime;
		private TextField endTime;
		private TextField companionName;
		private TextField companionHours;
		private ComboBox<String> time1;
		private ComboBox<String> time2;
		private VBox myVBox;
		private boolean created;
		private int secPosition;
		private boolean includeAMPM;
		
		// -------------------------------------------------------------
		public Section(boolean c, int p, boolean inc) {
			created = c;
			secPosition = p;
			includeAMPM = inc;
			myVBox = createSection();
		}
		
		// -------------------------------------------------------------
		public VBox createSection() {
			VBox vbox = new VBox(10);
			vbox.setPadding(new Insets(0, 25, 0, 25));
			vbox.setAlignment(Pos.CENTER);

			GridPane grid = new GridPane(); 
			grid.setPadding(new Insets(5, 25, 5, 25));
			grid.setVgap(10);
			//grid.setStyle("-fx-border-color: black; -fx-border-style: solid solid solid solid;");
			
			String style;
			if (created == true) {
				style = "-fx-background-color: " + "-fx-shadow-highlight-color, " + "-fx-outer-border, "
						+ "-fx-inner-border, " + "-fx-body-color; " + "-fx-background-insets: 0 0 -1 0, 0, 1, 2; "
						+ "-fx-background-radius: 3px, 3px, 2px, 1px;";
			} else {
				style = "-fx-background-color: " + "-fx-shadow-highlight-color, " + "-fx-outer-border, "
						+ "-fx-inner-border, " + " #C7C7C7; " + "-fx-background-insets: 0 0 -1 0, 0, 1, 2; "
						+ "-fx-background-radius: 3px, 3px, 2px, 1px;";
			}
			
			grid.setStyle(style);
			
			// -------------------------------------------------------------
			Text snLabel = new Text(nameLabel);
			Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
			snLabel.setFont(myFont);
			snLabel.setWrappingWidth(200);
			snLabel.setTextAlignment(TextAlignment.LEFT);
			scoutName = new TextField();
			
			if (created == true) {
				Scout s = myScoutCollection.get(secPosition);
				String fn = (String) s.getState("firstName");
				String ln = (String) s.getState("lastName");
				scoutName.setText(fn + " " + ln);
			}
			
			scoutName.setEditable(false);
			scoutName.setMouseTransparent(true);
			scoutName.setFocusTraversable(false);
			scoutName.setStyle("-fx-control-inner-background: #d3d3d3;");
			scoutName.setPrefWidth(150);
			scoutName.setMinWidth(150);
			scoutName.setMaxWidth(150);
			
			VBox nameAndLabel = new VBox(5);
			nameAndLabel.getChildren().addAll(snLabel, scoutName);
			grid.add(nameAndLabel, 0, 0);
			
			// -------------------------------------------------------------
			VBox spacer1 = new VBox(5);

			VBox spacer2 = new VBox(5);
			spacer2.setAlignment(Pos.CENTER_LEFT);

			Text stLabel = new Text(startLabel);
			stLabel.setFont(myFont);
			stLabel.setWrappingWidth(200);
			stLabel.setTextAlignment(TextAlignment.LEFT);
			startTime = new TextField();
			startTime.setEditable(true);
			startTime.setPrefWidth(100);
			startTime.setMinWidth(100);
			startTime.setMaxWidth(100);

			if (includeAMPM == true) {
				String am = "a.m.";
				String pm = "p.m.";
				time1 = new ComboBox();
				time1.getItems().addAll(am, pm);
				time1.setValue(am);
				HBox complicated = new HBox(5);
				complicated.getChildren().addAll(startTime, time1);
				spacer1.getChildren().addAll(stLabel, complicated);
			}
			else {
				spacer1.getChildren().addAll(stLabel, startTime);
			}
			
			grid.add(spacer1, 1, 0);
			
			Text etLabel = new Text(endLabel);
			etLabel.setFont(myFont);
			etLabel.setWrappingWidth(200);
			etLabel.setTextAlignment(TextAlignment.LEFT);
			endTime = new TextField();
			endTime.setEditable(true);
			endTime.setPrefWidth(100);
			endTime.setMinWidth(100);
			endTime.setMaxWidth(100);
			
			if (includeAMPM == true) {
				HBox complicated2 = new HBox(5);
				time2 = new ComboBox();
				String am = "a.m.";
				String pm = "p.m.";
				time2.getItems().addAll(am, pm);
				time2.setValue(am);
				complicated2.getChildren().addAll(endTime, time2);
				spacer2.getChildren().addAll(etLabel, complicated2);
			}
			else {
				spacer2.getChildren().addAll(etLabel, endTime);
			}

			grid.add(spacer2, 2, 0);
			
			// -------------------------------------------------------------
			VBox sectionSpacer = new VBox();
			sectionSpacer.setPadding(new Insets(10, 0, 5, 0));

			Text cnLabel = new Text(companionNameLabel);
			cnLabel.setFont(myFont);
			cnLabel.setWrappingWidth(200);
			cnLabel.setTextAlignment(TextAlignment.LEFT);
			companionName = new TextField();
			companionName.setEditable(true);
			companionName.setPrefWidth(150);
			companionName.setMinWidth(150);
			companionName.setMaxWidth(150);
			HBox companionBox = new HBox(10);
			VBox gosh1 = new VBox(5);
			VBox gosh2 = new VBox(5);

			gosh1.getChildren().addAll(cnLabel, companionName);
			grid.add(gosh1, 0, 1);

			// -------------------------------------------------------------
			Text chLabel = new Text(companionHoursLabel);
			chLabel.setFont(myFont);
			chLabel.setWrappingWidth(200);
			chLabel.setTextAlignment(TextAlignment.LEFT);
			companionHours = new TextField();
			companionHours.setEditable(true);
			companionHours.setPrefWidth(100);
			companionHours.setMinWidth(100);
			companionHours.setMaxWidth(100);
			gosh2.getChildren().addAll(chLabel, companionHours);

			grid.add(gosh2, 1, 1);
			
			if (created == false) {
				setFieldsNotEditable(companionName);
				setFieldsNotEditable(companionHours);
				setFieldsNotEditable(endTime);
				setFieldsNotEditable(startTime);
				
				if (includeAMPM == true) {
					time1.setEditable(false);
					time1.setMouseTransparent(true);
					time1.setFocusTraversable(false);
					//time1.setStyle("-fx-control-inner-background: #d3d3d3;");
					time2.setEditable(false);
					time2.setMouseTransparent(true);
					time2.setFocusTraversable(false);
					//time2.setStyle("-fx-control-inner-background: #d3d3d3;");
				}
			}
			
			vbox.getChildren().add(grid);
			return vbox;
			
		}
		
		// -------------------------------------------------------------
		public void setFieldsNotEditable(TextField tf) {
			tf.setEditable(false);
			tf.setMouseTransparent(true);
			tf.setFocusTraversable(false);
			tf.setStyle("-fx-control-inner-background: #d3d3d3;");
		}
		
		// -------------------------------------------------------------
		public boolean validateSection() {

			// Check if the fields are empty
			if (companionName.getText().isEmpty()) {
				//statusLog.displayErrorMessage(nullFieldErrorMessage);
				System.out.println("No");
				companionName.requestFocus();
				return false;
			}
			else if (startTime.getText().isEmpty()) {
				//statusLog.displayErrorMessage(nullFieldErrorMessage);
				startTime.requestFocus();
				return false;
			} 
			else if (endTime.getText().isEmpty()) {
				//statusLog.displayErrorMessage(nullFieldErrorMessage);
				endTime.requestFocus();
				return false;
			}

			else if (companionHours.getText().isEmpty()) {
				//statusLog.displayErrorMessage(nullFieldErrorMessage);
				companionHours.requestFocus();
				return false;
			}

			return true;
		}
		
		// -------------------------------------------------------------
		public void setProperties() {
			Properties props = new Properties();
			Scout s = myScoutCollection.get(secPosition);
			props.setProperty("sessionID", (String) myModel.getState("SessionID"));
			props.setProperty("scoutID", (String) s.getState("scoutID"));
			props.setProperty("companionName", companionName.getText());
			props.setProperty("startTime", startTime.getText());
			props.setProperty("endTime", endTime.getText());
			props.setProperty("companionHours", companionHours.getText());
			shiftList.add(props);
		}

		// -------------------------------------------------------------
		public int getPos() {
			return secPosition;
		}

		// -------------------------------------------------------------
		public boolean isCreated() {
			return created;
		}

		// -------------------------------------------------------------
		public boolean returnAmPm() {
			return includeAMPM;
		}

		// -------------------------------------------------------------
		public VBox getVBox() {
			return myVBox;
		}
		
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
	
	// ---------------------------------------------------------
	public void processAction(Event e) {
		shiftList.clear();
		
		if (section1.validateSection() == false) {
			return;
		} 
		else {
			section1.setProperties();
		}
		
		if (section2.isCreated() == true) {
			if (section2.validateSection() == false)
				return;
			else
				section2.setProperties();
		}
		
		if (section3.isCreated() == true) {
			if (section3.validateSection() == false)
				return;
			else
				section3.setProperties();
		}
		
		if (section4.isCreated() == true) {
			if (section4.validateSection() == false)
				return;
			else
				section4.setProperties();
		}
		
		
		//setProperties objects & Add them to shiftList
		myModel.stateChangeRequest("insertShifts", shiftList);
		//myModel.stateChangeRequest("UpdateTLC", null);
		//Show Message
		
		Alert alert = new Alert(AlertType.INFORMATION);	//Change this to Confirmation?
		//alert.setTitle("Information Dialog");
		alert.setHeaderText(successLabel);
		//alert.setContentText("I have a great message for you!");

		Scout s;
		StringBuilder sb = new StringBuilder();
		
		sb.append("Shift created with the Scouts:");
		for (int i = 0; i < myScoutCollection.size(); i++) {
			s = myScoutCollection.get(i);
			String fn = (String) s.getState("firstName");
			String ln = (String) s.getState("lastName");
			sb.append("\n\t" + fn + " " + ln + "\n");
			
		}
		
		alert.setContentText(sb.toString());
		
		alert.showAndWait().ifPresent(response -> {
		     if (response == ButtonType.OK) {
		    	 myModel.stateChangeRequest("UpdateTLC", null);
		     }
		 });

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

	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
