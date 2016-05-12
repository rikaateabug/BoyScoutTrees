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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.MainStageContainer;
import models.Scout;
import models.ScoutCollection;
import transactions.ScoutTransaction;
import transactions.Transaction;
import userinterface.MessageView;
import views.View;

public class ScoutCollectionView extends View {

	protected final String scoutsFound = new String(myResourceBundle.getString("scoutsFound"));
	protected final String scoutsFoundSession = new String(myResourceBundle.getString("scoutsFoundSession"));
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

	// private ScoutTransaction myScoutTransaction;

	protected MessageView statusLog;
	private String transType;
	
	// --------------------------------------------------------------------------
	public ScoutCollectionView(ScoutCollection scoutCol) {
		super(scoutCol, "ScoutCollectionView");
		myModel = scoutCol;
		transType = (String) myModel.getState("TransactionType");
		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		
		if (transType.equals("ScoutTransaction")) {
			container.getChildren().add(createFormContent());
		} else if (transType.equals("SessionTransaction")) {
			container.getChildren().add(createFormContentSession());
		}
		// Error message area
		// container.getChildren().add(createStatusLog(" "));

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
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
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
		Text placeholder = new Text("\t\tNO SCOUTS FOUND");
		VBox test = new VBox();
		test.setAlignment(Pos.TOP_LEFT);
		test.getChildren().add(placeholder);
		placeholder.setTextAlignment(TextAlignment.CENTER);
		tableOfScouts.setPlaceholder(test);

		TableColumn scoutIDCol = new TableColumn(scoutIDLabel);
		scoutIDCol.setMinWidth(50);
		scoutIDCol.setStyle("-fx-alignment: CENTER;");
		scoutIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("scoutID"));

		TableColumn lastNameCol = new TableColumn(lastNameLabel);
		lastNameCol.setMinWidth(100);
		lastNameCol.setStyle("-fx-alignment: CENTER;");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("lastName"));

		TableColumn firstNameCol = new TableColumn(firstNameLabel);
		firstNameCol.setMinWidth(100);
		firstNameCol.setStyle("-fx-alignment: CENTER;");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("firstName"));

		TableColumn middleNameCol = new TableColumn(middleNameLabel);
		middleNameCol.setMinWidth(100);
		middleNameCol.setStyle("-fx-alignment: CENTER;");
		middleNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("middleName"));

		TableColumn dobCol = new TableColumn(dateOfBirthLabel);
		dobCol.setMinWidth(100);
		dobCol.setStyle("-fx-alignment: CENTER;");
		dobCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("birthDate"));

		TableColumn phoneCol = new TableColumn(phoneNumberLabel);
		phoneCol.setMinWidth(100);
		phoneCol.setStyle("-fx-alignment: CENTER;");
		phoneCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("phoneNumber"));

		TableColumn emailCol = new TableColumn(emailLabel);
		emailCol.setMinWidth(100);
		emailCol.setStyle("-fx-alignment: CENTER;");
		emailCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("email"));

		TableColumn troopIDCol = new TableColumn(troopIDLabel);
		troopIDCol.setMinWidth(100);
		troopIDCol.setStyle("-fx-alignment: CENTER;");
		troopIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("troopID"));

		TableColumn statusCol = new TableColumn(statusLabel);
		statusCol.setMinWidth(100);
		statusCol.setStyle("-fx-alignment: CENTER;");
		statusCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("status"));

		TableColumn dateCol = new TableColumn(dateLastUpdatedLabel);
		dateCol.setMinWidth(100);
		dateCol.setStyle("-fx-alignment: CENTER;");
		dateCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("dateStatusUpdated"));

		cancelButton = new Button(cancelButtonLabel);
		cancelButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");
		submitButton = new Button(submitButtonLabel);
		submitButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");

		tableOfScouts.getColumns().addAll(scoutIDCol, firstNameCol, middleNameCol, lastNameCol, dobCol, phoneCol,
				emailCol, troopIDCol, statusCol, dateCol);
		createActionEventsScout();

		tableOfScouts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(800, 300);
		scrollPane.setContent(tableOfScouts);

		HBox btnContainer = new HBox(30);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);
		
		return vbox;
	}

	
	// Create the main form content
	// -------------------------------------------------------------
	private VBox createFormContentSession() {
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text prompt = new Text(scoutsFoundSession);
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		prompt.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
		grid.add(prompt, 0, 0, 2, 1);

		tableOfScouts = new TableView<ScoutTableModel>();
		tableOfScouts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		Text placeholder = new Text("\t\tNO SCOUTS FOUND");
		VBox test = new VBox();
		test.setAlignment(Pos.TOP_LEFT);
		test.getChildren().add(placeholder);
		placeholder.setTextAlignment(TextAlignment.CENTER);
		tableOfScouts.setPlaceholder(test);

		TableColumn scoutIDCol = new TableColumn(scoutIDLabel);
		scoutIDCol.setMinWidth(50);
		scoutIDCol.setStyle("-fx-alignment: CENTER;");
		scoutIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("scoutID"));

		TableColumn lastNameCol = new TableColumn(lastNameLabel);
		lastNameCol.setMinWidth(100);
		lastNameCol.setStyle("-fx-alignment: CENTER;");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("lastName"));

		TableColumn firstNameCol = new TableColumn(firstNameLabel);
		firstNameCol.setMinWidth(100);
		firstNameCol.setStyle("-fx-alignment: CENTER;");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("firstName"));

		TableColumn middleNameCol = new TableColumn(middleNameLabel);
		middleNameCol.setMinWidth(100);
		middleNameCol.setStyle("-fx-alignment: CENTER;");
		middleNameCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("middleName"));

		TableColumn dobCol = new TableColumn(dateOfBirthLabel);
		dobCol.setMinWidth(100);
		dobCol.setStyle("-fx-alignment: CENTER;");
		dobCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("birthDate"));

		TableColumn phoneCol = new TableColumn(phoneNumberLabel);
		phoneCol.setMinWidth(100);
		phoneCol.setStyle("-fx-alignment: CENTER;");
		phoneCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("phoneNumber"));

		TableColumn emailCol = new TableColumn(emailLabel);
		emailCol.setMinWidth(100);
		emailCol.setStyle("-fx-alignment: CENTER;");
		emailCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("email"));

		TableColumn troopIDCol = new TableColumn(troopIDLabel);
		troopIDCol.setMinWidth(100);
		troopIDCol.setStyle("-fx-alignment: CENTER;");
		troopIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("troopID"));

		TableColumn statusCol = new TableColumn(statusLabel);
		statusCol.setMinWidth(100);
		statusCol.setStyle("-fx-alignment: CENTER;");
		statusCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("status"));

		TableColumn dateCol = new TableColumn(dateLastUpdatedLabel);
		dateCol.setMinWidth(100);
		dateCol.setStyle("-fx-alignment: CENTER;");
		dateCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("dateStatusUpdated"));

		cancelButton = new Button(cancelButtonLabel);
		cancelButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");
		submitButton = new Button(submitButtonLabel);
		submitButton.setStyle("-fx-font: 15 arial; -fx-font-weight: bold;");

		tableOfScouts.getColumns().addAll(firstNameCol, middleNameCol, lastNameCol, scoutIDCol, dobCol);
		createActionEventsSession();

		tableOfScouts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(500, 300);
		scrollPane.setContent(tableOfScouts);

		HBox btnContainer = new HBox(30);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(cancelButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(scrollPane);
		vbox.getChildren().add(btnContainer);

		return vbox;
	}
	
	
	
	
	
	// ----------------------------------------------------------
	public void createActionEventsScout() {
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// if there isn't a selection.....
				if (tableOfScouts.getSelectionModel().getSelectedItem() == null) {
					System.out.println("select something");
					Alert alert = new Alert(AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText(selectionErrorMessage);
					
					alert.showAndWait().ifPresent(response -> {
							if (response == ButtonType.OK) {
								alert.close();
							}
						});
				} 
				else {
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
	}

	
	// ----------------------------------------------------------
	public void createActionEventsSession() {
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				// if there isn't a selection.....
				if (tableOfScouts.getSelectionModel().getSelectedItems() == null) {
					System.out.println("select something");
				
				} 
				
				else {
//					CHANGE THIS CHANGE THIS CHANGE THIS
					ObservableList<ScoutTableModel> scm = tableOfScouts.getSelectionModel().getSelectedItems();
					System.out.println("scm size is: " + scm.size());
					if (scm.size() <= 4) {
						myModel.stateChangeRequest("ShowScoutShiftView", scm);
					}
					//System.out.println("too many scouts selected");
				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {

				myModel.stateChangeRequest("CancelSession", null);
			}
		});
	}

	// ----------------------------------------------------------
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
