package views;

import java.util.ResourceBundle;

import impresario.IModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.MainStageContainer;
import models.Scout;
import views.View;

public class TreeLotCoordinatorView extends View {


	//GUI Components
	protected final String title = new String(myResourceBundle.getString("title"));
	
	protected final String scoutPaneTitle = new String(myResourceBundle.getString("scoutPaneTitle"));
	protected final String treePaneTitle = new String(myResourceBundle.getString("treePaneTitle"));
	protected final String treeTypePaneTitle = new String(myResourceBundle.getString("treeTypePaneTitle"));
	protected final Label commonActions = new Label(myResourceBundle.getString("commonActions"));
	protected final Label otherActions = new Label(myResourceBundle.getString("otherActions"));
	protected final Button sellTree = new Button(myResourceBundle.getString("sellTree"));
	protected final Button openShift = new Button(myResourceBundle.getString("openShift"));
	protected final Button closeShift = new Button(myResourceBundle.getString("closeShift"));
	protected final Label scout = new Label(myResourceBundle.getString("scout"));
	protected final Label tree = new Label(myResourceBundle.getString("tree"));
	protected final Label treeType = new Label(myResourceBundle.getString("treeType"));
	protected final Button addScout = new Button(myResourceBundle.getString("addScout"));
	protected final Button updateScout = new Button(myResourceBundle.getString("updateScout"));
	protected final Button removeScout = new Button(myResourceBundle.getString("removeScout"));
	protected final Button addTree = new Button(myResourceBundle.getString("addTree"));
	protected final Button updateTree = new Button(myResourceBundle.getString("updateTree"));
	protected final Button removeTree = new Button(myResourceBundle.getString("removeTree"));
	protected final Button addTreeType = new Button(myResourceBundle.getString("addTreeType"));
	protected final Button updateTreeType = new Button(myResourceBundle.getString("updateTreeType"));

	protected final String noOpenShifts = new String(myResourceBundle.getString("noOpenShifts"));
	protected final String openShifts = new String(myResourceBundle.getString("openShifts"));
	
	protected final String openShiftError = new String(myResourceBundle.getString("openShiftError"));
	protected final String sellTreeError = new String(myResourceBundle.getString("sellTreeError"));
	
	protected Text myShiftLabel;
	
	//Constructor
	public TreeLotCoordinatorView(IModel treeLotCoordinator) {
		super(treeLotCoordinator, "TreeLotCoordinatorView");
		
		VBox container = new VBox(10);
		//container.setPadding(new Insets(15, 10, 10, 5));
		container.setPadding(new Insets(20, 20, 20, 20));
		
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());
		
		getChildren().add(container);
	}

	//Creates the title of our GUI
	//----------------------------------------------------------	
	private Node createTitle() {
			
		HBox container = new HBox();
		container.setPadding(new Insets(30, 20, 30, 20));
		container.setAlignment(Pos.CENTER);
				
		//Change below if desired
		Text titleText = new Text(title);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
		//titleText.setWrappingWidth(300);	//Change this to 500 when you change the previous line to 50
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);
		//container.setStyle("-fx-border-color: black; -fx-border-style: hidden hidden solid hidden;");
		return container;
	}
	
	
	//----------------------------------------------------------
	private VBox createFormContent() {
		//All non-title GUI stuff goes inside here
		
		VBox formContainer = new VBox(15);
		
		VBox topSection = new VBox(20);
		topSection.setAlignment(Pos.CENTER_LEFT);
		topSection.setPadding(new Insets(20, 10, 10, 10));
		
		HBox topRow = new HBox(15);
		topRow.setAlignment(Pos.CENTER);
		topRow.setPadding(new Insets(20, 20, 20, 20));
		
		String style = "-fx-border-color: -fx-box-border; -fx-border-insets: -1 0 0 0; -fx-background-color: linear-gradient(from 0px 0px to 0px 5px, derive(-fx-background, -6%), -fx-background)";
		
		
		topRow.setStyle(style);
		
		//ActionEvents, note they are lambda expressions
		addScout.setStyle("-fx-font: 20 arial;");
		addScout.setOnAction(e -> {
			myModel.stateChangeRequest("AddScout", null);
		});
		
		updateScout.setStyle("-fx-font: 20 arial;");
		updateScout.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateScout", null);
		});
		
		removeScout.setStyle("-fx-font: 20 arial;");
		removeScout.setOnAction(e -> {
			myModel.stateChangeRequest("RemoveScout", null);
		});
		
		addTree.setStyle("-fx-font: 20 arial;");
		addTree.setOnAction(e -> {
			myModel.stateChangeRequest("AddTree", null);
		});
		
		updateTree.setStyle("-fx-font: 20 arial;");
		updateTree.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateTree", null);
		});
		
		removeTree.setStyle("-fx-font: 20 arial;");
		removeTree.setOnAction(e -> {
			myModel.stateChangeRequest("RemoveTree", null);
		});
		
		addTreeType.setStyle("-fx-font: 20 arial;");
		addTreeType.setOnAction(e -> {
			myModel.stateChangeRequest("AddTreeType", null);
		});
		
		updateTreeType.setStyle("-fx-font: 20 arial;");
		updateTreeType.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateTreeType", null);
		});
		
		
		////////////////////////////////////////////////////
		openShift.setStyle("-fx-font: 20 arial;");
		openShift.setOnAction(e -> {
			boolean openSession = (boolean) myModel.getState("SessionStatus");

			if (openSession == false) {
				myModel.stateChangeRequest("OpenSession", null);
			} else {
				// Error
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(openShiftError);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
			}
		});

		////////////////////////////////////////////////////
		sellTree.setStyle("-fx-font: 20 arial;");
		sellTree.setOnAction(e -> {
			boolean openSession = (boolean) myModel.getState("SessionStatus");

			if (openSession == true) {
				myModel.stateChangeRequest("SellTree", null);
			} else {
				// Error
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(sellTreeError);
				alert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						alert.close();
					}
				});
			}

		});
		
		////////////////////////////////////////////////////
		closeShift.setStyle("-fx-font: 20 arial;");
		closeShift.setOnAction(e -> {
			boolean openSession = (boolean)myModel.getState("SessionStatus");
			
			if (openSession == true) {
				myModel.stateChangeRequest("CloseSession", null);
			}
			else {
				//Error
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setContentText(noOpenShifts);
				alert.showAndWait().ifPresent(response -> {
				     if (response == ButtonType.OK) {
				    	 alert.close();
				     }
				 });
			}
			
		});
		
		////////////////////////////////////////////////////
		TitledPane scoutPane = new TitledPane();
		scoutPane.setAnimated(false);
		scoutPane.setText(scoutPaneTitle);
		scoutPane.setStyle("-fx-font: 20 arial;");
		HBox scoutHbox = new HBox(15);
		scoutHbox.setAlignment(Pos.CENTER);
		scoutHbox.setPadding(new Insets(20, 10, 20, 10));
		scoutHbox.getChildren().addAll(addScout, updateScout, removeScout);
		scoutPane.setContent(scoutHbox);
		////////////////////////////////////////////////////
		TitledPane treePane = new TitledPane();
		treePane.setAnimated(false);
		treePane.setText(treePaneTitle);
		treePane.setStyle("-fx-font: 20 arial;");
		HBox treeHbox = new HBox(15);
		treeHbox.setAlignment(Pos.CENTER);
		treeHbox.setPadding(new Insets(20, 10, 20, 10));
		treeHbox.getChildren().addAll(addTree, updateTree, removeTree);
		treePane.setContent(treeHbox);
		////////////////////////////////////////////////////
		TitledPane treeTypePane = new TitledPane();
		treeTypePane.setAnimated(false);
		treeTypePane.setText(treeTypePaneTitle);
		treeTypePane.setStyle("-fx-font: 20 arial;");
		HBox treeTypeHbox = new HBox(15);
		treeTypeHbox.setAlignment(Pos.CENTER);
		treeTypeHbox.setPadding(new Insets(20, 10, 20, 10));
		treeTypeHbox.getChildren().addAll(addTreeType, updateTreeType);
		treeTypePane.setContent(treeTypeHbox);
		
		
		Accordion accordion = new Accordion();
		accordion.getPanes().addAll(scoutPane, treePane, treeTypePane);
		
		//This event ensures the Accordion will resize when a panel is opened
		accordion.heightProperty().addListener(new ChangeListener() {
			@Override 
			public void changed(ObservableValue o, Object oldVal, Object newVal) {
				MainStageContainer.resizeStage();
			}
		});
		
		otherActions.setStyle("-fx-font-weight: bold; -fx-font: 20 arial; -fx-underline: true;");
		commonActions.setStyle("-fx-font-weight: bold; -fx-font: 20 arial; -fx-underline: true;");
		
		VBox other = new VBox();
		other.setAlignment(Pos.CENTER_LEFT);
		other.setPadding(new Insets(0, 10, 10, 10));
		other.getChildren().add(otherActions);
		
		myShiftLabel = new Text();
		myShiftLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
		getSessionStatus();
		VBox centering = new VBox();
		centering.setAlignment(Pos.CENTER);
		centering.setPadding(new Insets(15, 0, 15, 0));
		centering.setStyle("-fx-border-color: black; -fx-border-style: solid hidden solid hidden;");
		centering.getChildren().add(myShiftLabel);
		
		
		//Add all layouts to the form container
		topRow.getChildren().addAll(sellTree, openShift, closeShift);
		topSection.getChildren().addAll(centering, new VBox(), commonActions, topRow);
		//topSection.getChildren().addAll(commonActions, topRow, centering);
		//topSection.getChildren().addAll(commonActions, topRow);
		formContainer.getChildren().addAll(topSection, other);
		
		formContainer.getChildren().add(accordion);
		
		return formContainer;
	}
	
	//----------------------------------------------------------
	public void updateState(String arg0, Object arg1) {
		// TODO Auto-generated method stub
	}
	
	//----------------------------------------------------------
	public void getSessionStatus() {
		boolean status;
		status = (boolean) myModel.getState("SessionStatus");
		
		if (status == true) {
			myShiftLabel.setText(openShifts);
			myShiftLabel.setFill(Color.BLUE);
		}
		else {
			myShiftLabel.setText(noOpenShifts);
			myShiftLabel.setFill(Color.RED);
		}
	}
	
	
}
