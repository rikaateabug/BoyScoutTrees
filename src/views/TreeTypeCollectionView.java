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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import models.TreeType;
import models.TreeTypeCollection;
import transactions.ScoutTransaction;
import transactions.TreeTypeTransaction;
import userinterface.MessageView;
import views.View;

public class TreeTypeCollectionView extends View {

	protected final String titleLabel = new String(myResourceBundle.getString("titleLabel"));
	protected final String treeTypesFound = new String(myResourceBundle.getString("treeTypesFound"));
	protected final String treeTypeIDLabel = new String(myResourceBundle.getString("treeTypeIDLabel"));
	
	protected final String descriptionLabel = new String(myResourceBundle.getString("descriptionLabel"));
	protected final String barcodePrefixLabel = new String(myResourceBundle.getString("barcodePrefixLabel"));
	protected final String costLabel = new String(myResourceBundle.getString("costLabel"));
	
	protected final String cancelButtonLabel = new String(myResourceBundle.getString("cancelButtonLabel"));
	protected final String submitButtonLabel = new String(myResourceBundle.getString("submitButtonLabel"));
	protected final String selectionErrorMessage = new String(myResourceBundle.getString("selectionErrorMessage"));
	
	
	protected TableView<TreeTypeTableModel> tableOfTreeTypes;
	protected Button cancelButton;
	protected Button submitButton;
	
	private TreeTypeTransaction myTrans;
	
	protected MessageView statusLog;

	// --------------------------------------------------------------------------
	public TreeTypeCollectionView(IModel t) {
		super(t, "TreeTypeCollectionView");
		
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

		ObservableList<TreeTypeTableModel> tableData = FXCollections.observableArrayList();
		try {
			TreeTypeCollection b1 = (TreeTypeCollection) myModel.getState("TreeTypes");
			
			Vector entryList = (Vector) b1.getState("TreeTypes");
			
			Enumeration entries = entryList.elements();

			while (entries.hasMoreElements() == true) {

				TreeType nextTreeType = (TreeType) entries.nextElement();
				Vector<String> view = nextTreeType.getEntryListView();

				// add this list entry to the list
				TreeTypeTableModel nextTableRowData = new TreeTypeTableModel(view);
				tableData.add(nextTableRowData);

			}

			tableOfTreeTypes.setItems(tableData);

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

		Text prompt = new Text(treeTypesFound);
		prompt.setWrappingWidth(350);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		grid.add(prompt, 0, 0, 2, 1);

		tableOfTreeTypes = new TableView<TreeTypeTableModel>();
		tableOfTreeTypes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		TableColumn treeTypeIDCol = new TableColumn(treeTypeIDLabel);
		treeTypeIDCol.setMinWidth(50);
		treeTypeIDCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("treeTypeID"));
		treeTypeIDCol.setStyle("-fx-alignment: CENTER;");
		
		TableColumn descCol = new TableColumn(descriptionLabel);
		descCol.setMinWidth(150);
		descCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("typeDescription"));
		descCol.setStyle("-fx-alignment: CENTER;");
		
		
		TableColumn costCol = new TableColumn(costLabel);
		costCol.setMinWidth(100);
		costCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("cost"));
		costCol.setStyle("-fx-alignment: CENTER;");
		
		
		TableColumn barcodePrefixCol = new TableColumn(barcodePrefixLabel);
		barcodePrefixCol.setMinWidth(100);
		barcodePrefixCol.setCellValueFactory(new PropertyValueFactory<ScoutTableModel, String>("barcodePrefix"));
		barcodePrefixCol.setStyle("-fx-alignment: CENTER;");
		
		
		tableOfTreeTypes.getColumns().addAll(treeTypeIDCol, descCol, costCol, barcodePrefixCol);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setPrefSize(115, 150);
		scrollPane.setContent(tableOfTreeTypes);

		cancelButton = new Button(cancelButtonLabel);
		submitButton = new Button(submitButtonLabel);
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent e) {

				//if there isn't a selection.....
				if (tableOfTreeTypes.getSelectionModel().getSelectedItem() == null) {
					statusLog.displayErrorMessage(selectionErrorMessage);
				}
				else {
				clearErrorMessage();
				TreeTypeTableModel scm = tableOfTreeTypes.getSelectionModel().getSelectedItem();
				myModel.stateChangeRequest("showView", scm.getTreeTypeID());
				}
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			
			@Override
			public void handle(ActionEvent e) {

				myModel.stateChangeRequest("CancelTrans", null);
			}
		});
		
		HBox btnContainer = new HBox(30);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(cancelButton);
		btnContainer.getChildren().add(submitButton);

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

	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

	}

}
