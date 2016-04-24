package views;

import java.awt.Graphics;
import java.awt.Shape;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.text.BadLocationException;
import javax.swing.text.Position.Bias;

import exception.InvalidPrimaryKeyException;
// project imports
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Scout;
import models.ScoutCollection;
import transactions.ScoutTransaction;
import userinterface.MessageView;
import views.View;

public class ScoutCollectionView extends View {

	protected final String scoutsFound = new String(myResourceBundle.getString("scoutsFound"));
	protected final String scoutIDLabel = new String(myResourceBundle.getString("scoutIDLabel"));
	protected final String dateLastUpdatedLabel = new String(myResourceBundle.getString("dateLastUpdatedLabel"));
	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String lastNameLabel = new String(myResourceBundle.getString("lastNameLabel"));
	protected final String firstNameLabel = new String(myResourceBundle.getString("firstNameLabel")); 
	protected final String middleNameLabel = new String(myResourceBundle.getString("middleNameLabel"));
	protected final String dateOfBirthLabel = new String(myResourceBundle.getString("dateOfBirthLabel"));
	protected final String phoneNumberLabel = new String(myResourceBundle.getString("phoneNumberLabel"));
	protected final String troopIDLabel = new String(myResourceBundle.getString("troopIDLabel"));
	protected final String emailLabel = new String(myResourceBundle.getString("emailLabel"));
	protected final String statusLabel = new String(myResourceBundle.getString("statusLabel"));
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String selectionErrorMessage = new String(myResourceBundle.getString("selectionErrorMessage"));
	
	protected TableView<ScoutTableModel> tableOfScouts;
	protected Button cancelButton;
	protected Button submitButton;
	
	private ScoutTransaction myScoutTransaction;
	
	protected MessageView statusLog;

	// --------------------------------------------------------------------------
	public ScoutCollectionView(ScoutCollection scoutTrans) {
		super(scoutTrans, "ScoutCollectionView");
		myModel = scoutTrans;
		
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                                            "));

		getChildren().add(container);

		populateFields();
	}

	// --------------------------------------------------------------------------
	protected void populateFields() {

		getEntryTableModelValues();
	}

	// --------------------------------------------------------------------------
	protected void getEntryTableModelValues() {

		ObservableList<ScoutTableModel> tableData = FXCollections.observableArrayList();
		try {
			ScoutCollection b1 = (ScoutCollection) myModel.getState("ScoutList");
			
			Vector entryList = (Vector) b1.getState("Scouts");
			
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true) {

				Scout nextScout = (Scout) entries.nextElement();
				Vector<String> view = nextScout.getEntryListView();

				// add this list entry to the list
				ScoutTableModel nextTableRowData = new ScoutTableModel(view);
				tableData.add(nextTableRowData);

			}

			tableOfScouts.setItems(tableData);

		} catch (Exception e) {// SQLException e) {
			// Need to handle this exception
		}
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
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContent() {
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text prompt = new Text(scoutsFound);
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		grid.add(prompt, 0, 0, 2, 1);

		tableOfScouts = new TableView<ScoutTableModel>();
		tableOfScouts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		
		TableColumn scoutIDCol = new TableColumn(scoutIDLabel);
		scoutIDCol.setMinWidth(100);
		scoutIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("scoutID"));

		TableColumn lastNameCol = new TableColumn(lastNameLabel);
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("lastName"));

		TableColumn firstNameCol = new TableColumn(firstNameLabel);
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("firstName"));

		TableColumn middleNameCol = new TableColumn(middleNameLabel);
		middleNameCol.setMinWidth(100);
		middleNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("middleName"));

		TableColumn dobCol = new TableColumn(dateOfBirthLabel);
		dobCol.setMinWidth(100);
		dobCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("birthDate"));

		TableColumn phoneCol = new TableColumn(phoneNumberLabel);
		phoneCol.setMinWidth(100);
		phoneCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("phoneNumber"));

		TableColumn emailCol = new TableColumn(emailLabel);
		emailCol.setMinWidth(100);
		emailCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("email"));

		TableColumn troopIDCol = new TableColumn(troopIDLabel);
		troopIDCol.setMinWidth(100);
		troopIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("troopID"));

		TableColumn statusCol = new TableColumn(statusLabel);
		statusCol.setMinWidth(100);
		statusCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("status"));

		TableColumn dateCol = new TableColumn(dateLastUpdatedLabel);
		dateCol.setMinWidth(100);
		dateCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("dateStatusUpdated"));

		tableOfScouts.getColumns().addAll(scoutIDCol, firstNameCol, middleNameCol, lastNameCol, dobCol, phoneCol, emailCol, troopIDCol, statusCol, dateCol);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(tableOfScouts);

		cancelButton = new Button(cancelButtonLabel);
		submitButton = new Button(submitButtonLabel);
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent e) {

				//if there isn't a selection.....
				if (tableOfScouts.getSelectionModel().getSelectedItem() == null) {
					statusLog.displayErrorMessage(selectionErrorMessage);
				}
				else {
				clearErrorMessage();
				ScoutTableModel scm = tableOfScouts.getSelectionModel().getSelectedItem();
				myModel.stateChangeRequest("showView", scm.getScoutID());
				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent e) {

				myModel.stateChangeRequest("CancelAddScout", null);
			}
		});
		
		HBox btnContainer = new HBox(30);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}

	protected MessageView createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);

		return statusLog;
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
	/*
	 * //-----------------------------------------------------------------------
	 * --- public void mouseClicked(MouseEvent click) { if(click.getClickCount()
	 * >= 2) { processAccountSelected(); } }
	 */

	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
