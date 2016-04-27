package views;

import java.util.ResourceBundle;

import impresario.IModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.MainStageContainer;
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
	

	
	//Constructor
	public TreeLotCoordinatorView(IModel treeLotCoordinator) {
		super(treeLotCoordinator, "TreeLotCoordinatorView");
		
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 10, 10, 5));
		
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());
		
		getChildren().add(container);
	}

	//Creates the title of our GUI
	//----------------------------------------------------------	
	private Node createTitle() {
			
		HBox container = new HBox();
		container.setPadding(new Insets(10, 0, 10, 0));
		container.setAlignment(Pos.CENTER);
				
		//Change below if desired
		Text titleText = new Text(title);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
		titleText.setWrappingWidth(300);	//Change this to 500 when you change the previous line to 50
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKOLIVEGREEN);
		container.getChildren().add(titleText);
					
		return container;
	}
	
	
	//----------------------------------------------------------
	private VBox createFormContent() {
		//All non-title GUI stuff goes inside here
		
		VBox formContainer = new VBox(15);
		
		VBox topSection = new VBox(15);
		topSection.setAlignment(Pos.CENTER_LEFT);
		topSection.setPadding(new Insets(10, 10, 10, 10));
		topSection.setStyle("-fx-border-color: black; -fx-border-style: hidden hidden solid hidden;");
		
		HBox topRow = new HBox(15);
		topRow.setAlignment(Pos.CENTER);
		topRow.setPadding(new Insets(10, 10, 10, 10));
		
		//ActionEvents, note they are lambda expressions
		addScout.setOnAction(e -> {
			myModel.stateChangeRequest("AddScout", null);
		});
		
		updateScout.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateScout", null);
		});
		
		removeScout.setOnAction(e -> {
			myModel.stateChangeRequest("RemoveScout", null);
		});
		
		addTree.setOnAction(e -> {
			myModel.stateChangeRequest("AddTree", null);
		});
		
		updateTree.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateTree", null);
		});
		
		removeTree.setOnAction(e -> {
			myModel.stateChangeRequest("RemoveTree", null);
		});
		
		addTreeType.setOnAction(e -> {
			myModel.stateChangeRequest("AddTreeType", null);
		});
		
		updateTreeType.setOnAction(e -> {
			myModel.stateChangeRequest("UpdateTreeType", null);
		});
		
		openShift.setOnAction(e -> {
			myModel.stateChangeRequest("OpenSession", null);
		});
		
		/**
		 * Action Events for Sell Tree, Open Shift & Close Shift
		 * We don't need these yet
		sellTree.setOnAction(e -> {
			myModel.stateChangeRequest("SellTree", null);
		});
		
		
		
		closeShift.setOnAction(e -> {
			myModel.stateChangeRequest("CloseShift", null);
		});
		*/
		
		////////////////////////////////////////////////////
		TitledPane scoutPane = new TitledPane();
		scoutPane.setAnimated(false);
		scoutPane.setText(scoutPaneTitle);
		HBox scoutHbox = new HBox(15);
		scoutHbox.setAlignment(Pos.CENTER);
		scoutHbox.setPadding(new Insets(20, 10, 20, 10));
		scoutHbox.getChildren().addAll(addScout, updateScout, removeScout);
		scoutPane.setContent(scoutHbox);
		////////////////////////////////////////////////////
		TitledPane treePane = new TitledPane();
		treePane.setAnimated(false);
		treePane.setText(treePaneTitle);
		HBox treeHbox = new HBox(15);
		treeHbox.setAlignment(Pos.CENTER);
		treeHbox.setPadding(new Insets(20, 10, 20, 10));
		treeHbox.getChildren().addAll(addTree, updateTree, removeTree);
		treePane.setContent(treeHbox);
		////////////////////////////////////////////////////
		TitledPane treeTypePane = new TitledPane();
		treeTypePane.setAnimated(false);
		treeTypePane.setText(treeTypePaneTitle);
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
		
		otherActions.setStyle("-fx-font-weight: bold;");
		commonActions.setStyle("-fx-font-weight: bold;");
		
		VBox other = new VBox();
		other.setAlignment(Pos.CENTER_LEFT);
		other.setPadding(new Insets(0, 10, 10, 10));
		other.getChildren().add(otherActions);
		
		Text sessionText = new Text(getSessionStatus());
		sessionText.setFont(Font.font("Arial", FontWeight.MEDIUM, 15));
		
		
		//Add all layouts to the form container
		topRow.getChildren().addAll(sellTree, openShift, closeShift);
		topSection.getChildren().addAll(commonActions, topRow, sessionText);
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
	public String getSessionStatus() {
		boolean status;
		status = (boolean) myModel.getState("SessionStatus");
		
		if (status == true) {
			return "Shift open";
		}
		else {
			return "There are currently no open shifts";
		}
	}
	
	
}
