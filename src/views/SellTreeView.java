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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
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
import models.Tree;
import models.TreeLotCoordinator;
import models.TreeType;
import transactions.ScoutTransaction;
import userinterface.MessageView;
import views.View;
import javafx.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
// project imports
import impresario.IModel;

/** The class containing the Account View for the ATM application */
// ==============================================================
public class SellTreeView extends View {

	// GUI components
	
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String treeTypeLabel = new String(myResourceBundle.getString("treeTypeLabel"));
	protected final String treeBarcodeLabel = new String(myResourceBundle.getString("treeBarcodeLabel"));
	protected final String salePriceLabel = new String(myResourceBundle.getString("salePriceLabel"));
	protected final String optionalLabel = new String(myResourceBundle.getString("optionalLabel"));
	protected final String requiredLabel = new String(myResourceBundle.getString("requiredLabel"));
	protected final String paymentMethodLabel = new String(myResourceBundle.getString("paymentMethodLabel"));
	protected final String cashLabel = new String(myResourceBundle.getString("cashLabel"));
	protected final String checkLabel = new String(myResourceBundle.getString("checkLabel"));
	protected final String customerInformationLabel = new String(myResourceBundle.getString("customerInformationLabel"));
	protected final String customerNameLabel = new String(myResourceBundle.getString("customerNameLabel"));
	protected final String phoneNumberLabel = new String(myResourceBundle.getString("phoneNumberLabel"));
	protected final String emailLabel = new String(myResourceBundle.getString("emailLabel"));
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String invalidCostErrorMessage = new String(myResourceBundle.getString("invalidCostErrorMessage"));
	protected final String nullFieldErrorMessage = new String(myResourceBundle.getString("nullFieldErrorMessage"));
	protected final String successLabel = new String(myResourceBundle.getString("successLabel"));
	
	//protected final String requiredFieldsLabel = new String(myResourceBundle.getString("requiredFieldsLabel"));	 
	
	protected TextField treeBarcode;
	protected TextField custName;
	protected TextField custPhone;
	protected TextField custEmail;
	protected TextField treeType;
	protected TextField salePrice;
	protected RadioButton cash;
	protected RadioButton check;
	protected TextArea notes;
	final ToggleGroup group = new ToggleGroup();
	Text cLabel2 = new Text(customerNameLabel);
	Text balLabel3 = new Text(phoneNumberLabel);
	Text requiredField;
	
	protected Button submitButton;
	protected Button cancelButton;

	// For showing error message
	protected MessageView statusLog;
	private Tree myTree;
	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public SellTreeView(IModel scoutTrans) {
		super(scoutTrans, "SellTreeView");
		myTree = (Tree) myModel.getState("Tree");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 15, 15, 15));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog(""));

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
		vbox.setPadding(new Insets(20, 25, 25, 25));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(50);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 25, 25, 25));
		
		
		// -------------------------------------------------------------
		Text accNumLabel = new Text(treeBarcodeLabel);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 14);
		accNumLabel.setFont(myFont);
		//accNumLabel.setWrappingWidth(100);
		accNumLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(accNumLabel, 0, 1);
		treeBarcode = new TextField();
		treeBarcode.setText((String)myTree.getState("barcode"));
		treeBarcode.setEditable(false);
		treeBarcode.setMouseTransparent(true);
		treeBarcode.setFocusTraversable(false);
		treeBarcode.setStyle("-fx-control-inner-background: #d3d3d3;");
		grid.add(treeBarcode, 1, 1);
		// -------------------------------------------------------------
		Text acctTypeLabel = new Text(treeTypeLabel);
		acctTypeLabel.setFont(myFont);
		//acctTypeLabel.setWrappingWidth(100);
		acctTypeLabel.setTextAlignment(TextAlignment.RIGHT);		
		grid.add(acctTypeLabel, 0, 2);
		treeType = new TextField();
		try {
			TreeType theType = new TreeType();
			String retrieved = theType.getTreeType((String)myTree.getState("treeTypeID"));
			treeType.setText(retrieved);
		} catch (InvalidPrimaryKeyException e1) {
			
		}
		treeType.setEditable(false);
		treeType.setMouseTransparent(true);
		treeType.setFocusTraversable(false);
		treeType.setStyle("-fx-control-inner-background: #d3d3d3;");
		grid.add(treeType, 1, 2);
		// -------------------------------------------------------------
		Text balLabel = new Text(salePriceLabel);
		balLabel.setFont(myFont);
		//balLabel.setWrappingWidth(100);
		balLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel, 0, 3);

		salePrice = new TextField();
		try {
			TreeType theType = new TreeType((String)myTree.getState("treeTypeID"));
			String theCost = (String)theType.getState("cost");
			System.out.println("the cost is: " + theCost);
			salePrice.setText(theCost);
		} catch (InvalidPrimaryKeyException e1) {
		}
		
		salePrice.setEditable(true);
		grid.add(salePrice, 1, 3);
		// -------------------------------------------------------------
		Text balLabel1 = new Text(paymentMethodLabel);
		balLabel1.setFont(myFont);
		//balLabel1.setWrappingWidth(150);
		balLabel1.setTextAlignment(TextAlignment.RIGHT);
		grid.add(balLabel1, 0, 4);
		
		
		cash = new RadioButton(cashLabel);
		cash.setToggleGroup(group);
		//cash.setSelected(true);

		check = new RadioButton(checkLabel);
		check.setToggleGroup(group);
		
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if (group.getSelectedToggle() != null) {
					if (group.getSelectedToggle().equals(cash)) {
						cLabel2.setText(customerNameLabel);
						balLabel3.setText(phoneNumberLabel);
						requiredField.setVisible(false);
					}

					else if (group.getSelectedToggle().equals(check)) {
						cLabel2.setText(customerNameLabel + "*");
						balLabel3.setText(phoneNumberLabel + "*");
						requiredField.setVisible(true);
					}
				}
			}
		});
		
		HBox cashCheckRow = new HBox(10);
		//custDateRow.setAlignment(Pos.CENTER_LEFT);
		cashCheckRow.getChildren().addAll(cash, check);
		grid.add(cashCheckRow, 1, 4);

		// -------------------------------------------------------------
		
		VBox vb = new VBox(15);
		vb.setPadding(new Insets(20, 25, 0, 25));
		vb.setAlignment(Pos.CENTER);
		Text balLabel2 = new Text(customerInformationLabel);
		balLabel2.setFont(Font.font("Arial", FontWeight.BOLD, 16));
		//balLabel2.setWrappingWidth(100);
		balLabel2.setTextAlignment(TextAlignment.CENTER);
		vb.getChildren().add(balLabel2);
		
		
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(50);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(20, 25, 25, 25));
		
		
		
		cLabel2 = new Text(customerNameLabel);
		cLabel2.setFont(myFont);
		//cLabel2.setWrappingWidth(100);
		cLabel2.setTextAlignment(TextAlignment.RIGHT);
		grid2.add(cLabel2, 0, 1);

		custName = new TextField();
		custName.setEditable(true);
		grid2.add(custName, 1, 1);
		// -------------------------------------------------------------
		balLabel3 = new Text(phoneNumberLabel);
		balLabel3.setFont(myFont);
		//balLabel3.setWrappingWidth(100);
		balLabel3.setTextAlignment(TextAlignment.RIGHT);
		grid2.add(balLabel3, 0, 2);

		custPhone = new TextField();
		custPhone.setEditable(true);
		grid2.add(custPhone, 1, 2);
		// -------------------------------------------------------------
		Text balLabel5 = new Text(emailLabel);
		balLabel5.setFont(myFont);
		//balLabel5.setWrappingWidth(100);
		balLabel5.setTextAlignment(TextAlignment.RIGHT);
		grid2.add(balLabel5, 0, 3);

		custEmail = new TextField();
		custEmail.setEditable(true);
		grid2.add(custEmail, 1, 3);
		// -------------------------------------------------------------
		/**
		Text balLabel6 = new Text(emailLabel);
		balLabel6.setFont(myFont);
		//balLabel5.setWrappingWidth(100);
		balLabel6.setTextAlignment(TextAlignment.RIGHT);
		grid2.add(balLabel6, 0, 3);

		notes = new TextArea();
		notes.setEditable(true);
		grid2.add(custEmail, 1, 3);
		*/		
		
		requiredField = new Text(requiredLabel);
		requiredField.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		//requiredField.setFill(Color.RED);
		requiredField.setVisible(false);
		grid2.add(requiredField, 0, 4);
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
				processAction(e);
			}
		});

		doneCont.getChildren().add(cancelButton);

		vbox.getChildren().addAll(grid, vb, grid2);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// -------------------------------------------------------------
	public void toggleRequired(boolean required) {
		if (required == true) {
			balLabel3 = new Text(phoneNumberLabel);
		}
		else {
			
		}
	}
	
	// Create the status log field
	// -------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// ---------------------------------------------------------
	public void processAction(Event e) {

		if (salePrice.getText().isEmpty()) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			salePrice.requestFocus();
			return;
		}
		else if ((check.isSelected() == false) && (cash.isSelected() == false)) {
			statusLog.displayErrorMessage(nullFieldErrorMessage);
			cash.requestFocus();
			return;
		}
		
		if (check.isSelected() == true) {
			if (custName.getText().isEmpty()) {
				statusLog.displayErrorMessage(nullFieldErrorMessage);
				custName.requestFocus();
				return;
			}
			else if (custPhone.getText().isEmpty()) {
				statusLog.displayErrorMessage(nullFieldErrorMessage);
				custPhone.requestFocus();
				return;
			}
		}
		
		
			Properties p = setPropertiesObject();
			myModel.stateChangeRequest("soldTree", p);
	//		statusLog.displayMessage(addSuccessMessage);
			showConfirmationMessage();
	}

	// ----------------------------------------------------------
	public Properties setPropertiesObject() {
		Properties props = new Properties();
		
		props.setProperty("transactionType", "Tree Sale");
		props.setProperty("barcode", (String)myTree.getState("barcode"));
		props.setProperty("transactionAmount", salePrice.getText());
		
		if (group.getSelectedToggle().equals(cash)) {
			//System.out.println("Cash was selected");
			props.setProperty("paymentMethod", cashLabel);
		}
		else {
			//System.out.println("Check was selected");
			props.setProperty("paymentMethod", checkLabel);
		}
		
		if (!(custName.getText().isEmpty())) {
			props.setProperty("customerName", custName.getText());
		}
		
		if (!(custPhone.getText().isEmpty())) {
			props.setProperty("customerPhone", custPhone.getText());
			
		}
		
		if (!(custEmail.getText().isEmpty())) {
			props.setProperty("customerEmail", custEmail.getText());
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat tf = new SimpleDateFormat("HH:mm");
		Date d = new Date();
		
		props.setProperty("transactionDate", df.format(d));
		props.setProperty("transactionTime", tf.format(d));
		props.setProperty("dateStatusUpdated", df.format(d));
		
		
		
		return props;
	}

	// -------------------------------------------------------------
	public void showConfirmationMessage() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(successLabel);

		alert.showAndWait().ifPresent(response -> {
			if (response == ButtonType.OK) {
				alert.close();
				myModel.stateChangeRequest("CancelAddScout", null);
			}
		});
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
