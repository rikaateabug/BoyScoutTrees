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
public class AddTreeView extends View {

	// GUI components
	
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String statusLabel = new String(myResourceBundle.getString("statusLabel"));
	protected final String availableStatus = new String(myResourceBundle.getString("availableStatus"));
	protected final String soldStatus = new String(myResourceBundle.getString("soldStatus"));
	protected final String damagedStatus = new String(myResourceBundle.getString("damagedStatus"));
	protected final String dateLastUpdatedLabel = new String(myResourceBundle.getString("dateLastUpdatedLabel"));
	protected final String doneButtonLabel = new String(myResourceBundle.getString("doneButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String notesLabel = new String(myResourceBundle.getString("notesLabel"));
	protected final String barcodeLabel = new String(myResourceBundle.getString("barcodeLabel"));
	protected final String invalidBarcodePrefix = new String(myResourceBundle.getString("invalidBarcodePrefix"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	protected final String addSuccessMessage = new String(myResourceBundle.getString("addSuccessMessage"));		
			
	protected TextField barcode;
	protected TextField treeType;
	protected TextArea notes;
	protected TextField dateStatusUpdated;
	protected ComboBox<String> status;
	protected Tree myTree;
	protected Button submitButton;
	protected Button doneButton;
	protected ScoutTableModel myTable;
	
	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public AddTreeView(IModel trans) {
		super(trans, "AddTreeView");

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
		grid.setPadding(new Insets(25, 25, 25, 0));

		Text accNumLabel = new Text(barcodeLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		accNumLabel.setFont(myFont);
		accNumLabel.setWrappingWidth(150);
		accNumLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(accNumLabel, 0, 0);

		barcode = new TextField();
		barcode.setEditable(true);
		grid.add(barcode, 1, 0);

		Text balLabel = new Text(notesLabel);
		balLabel.setFont(myFont);
		balLabel.setWrappingWidth(150);
		balLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel, 0, 1);

		notes = new TextArea();
		notes.setEditable(true);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(200, 100);
		scrollPane.setContent(notes);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		grid.add(scrollPane, 1, 1);
		
		
		Text balLabel5 = new Text(statusLabel);
		balLabel5.setFont(myFont);
		balLabel5.setWrappingWidth(150);
		balLabel5.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel5, 0, 2);

		status = new ComboBox<String>();
		status.getItems().addAll(availableStatus, soldStatus, damagedStatus);
		status.setValue(availableStatus);
		
		grid.add(status, 1, 2);

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
				validateBarcode();
			}
		});

		doneCont.getChildren().add(submitButton);

		
		doneButton = new Button(doneButtonLabel);
		doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		
		doneButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest("CancelAddScout", null);

			}
		});
		
		doneCont.getChildren().add(doneButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(mv);
		vbox.getChildren().add(doneCont);
		
		return vbox;
	}

	// -------------------------------------------------------------
	public void validateBarcode() {
		
		if (barcode.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			barcode.requestFocus();
		}
		else {
			try {
				Tree myTree = new Tree(barcode.getText());
				statusLog.displayErrorMessage(invalidBarcodePrefix);
				System.out.println("The error is here?");
				
				barcode.requestFocus();
				
			} catch (InvalidPrimaryKeyException e) {
				processAction();
				
			}
		}
		
	}
	
	// -------------------------------------------------------------
	public void processAction() {
		
		if (barcode.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			barcode.requestFocus();
		}
		
		else {
			String treeTypeID = barcode.getText();
			treeTypeID = treeTypeID.substring(0, 2);
			try {
				TreeType treeType = new TreeType();
				String retrieved = treeType.searchBarcodePrefix(treeTypeID);
				Properties p = setPropertiesObject(retrieved);
				myModel.stateChangeRequest("insertNewTree", p);
				statusLog.displayMessage(addSuccessMessage);
			
			} catch (InvalidPrimaryKeyException e1) {
				statusLog.displayErrorMessage(invalidBarcodePrefix);
			}
		}
	}
	
	// ----------------------------------------------------------
	public Properties setPropertiesObject(String typeID) {
		Properties props = new Properties();
		props.setProperty("barcode", barcode.getText());
		props.setProperty("treeTypeID", typeID);
		props.setProperty("status", status.getValue());
		
		if (notes.getText().isEmpty()) {
			props.setProperty("notes", "");
		}
		else {
			props.setProperty("notes", notes.getText());
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		props.setProperty("dateStatusUpdated", df.format(date));
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