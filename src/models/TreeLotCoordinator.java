package models;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import transactions.ScoutTransaction;
import transactions.Transaction;
import transactions.TransactionFactory;
import userinterface.WindowPosition;
import views.SearchScoutNameView;
import views.TreeLotCoordinatorView;
import views.View;
import views.ViewFactory;
import views.WelcomeView;

/**
 * TreeLotCoordinator - Main interface agent to the tree sales system
 * 
 * @author Amanda
 *
 */

public class TreeLotCoordinator implements IView, IModel {

	//For internationalization:
	private Locale myLocale;
	
	// Impresario Required:
	private Properties dependencies;
	private ModelRegistry myRegistry;

	// GUI Related:
	private Stage myStage;
	private Hashtable<String, Scene> myViews;

	private String transactionErrorMessage = "";

	// ----------------------------------------------------------
	// Constructor
	public TreeLotCoordinator() {
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// Create the Registry Object, not required if inheriting from
		// EntityBase
		myRegistry = new ModelRegistry("TreeLotCoordinator");
		if (myRegistry == null) {
			new Event(Event.getLeafLevelClassName(this), "TreeLotCoordinator", "Could not instantiate Registry",
					Event.ERROR);
		}

		setDependencies();
		createAndShowWelcomeView();
	}

	// -----------------------------------------------------------------------------------
	private void setLocale(String locale) {
		if (locale.equals("English")) {
			myLocale = new Locale("en", "US");
		}
		else if (locale.equals("French")) {
			myLocale = new Locale("fr", "FR");
		}
	}

	// -----------------------------------------------------------------------------------
	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	// -----------------------------------------------------------------------------------
	public Object getState(String key) {
		
		if (key.equals("Locale")) {
			
			if (myLocale != null)
				return myLocale;
			else
				return "Error: Locale is undefined";
		}
		return "";
	}

	// -----------------------------------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		if (key.equals("TreeLotCoordinatorView")) {
			createAndShowTreeLotCoordinatorView();
		}
		
		else if (key.equals("CancelTransaction") == true)
		{
			createAndShowTreeLotCoordinatorView();
		}
		
		else if (key.equals("SetLocale")) {
			setLocale((String)value);
			createAndShowTreeLotCoordinatorView();
		}
		
		else if ((key.equals("AddScout")) || (key.equals("UpdateScout")) || (key.equals("RemoveScout"))) {
			String transType = key;
			doTransaction(transType);
		}
		
		else if ((key.equals("AddTree")) || (key.equals("UpdateTree")) || (key.equals("RemoveTree"))) {
			String transType = key;
			doTransaction(transType);
		}
		
		else if ((key.equals("AddTreeType")) || (key.equals("UpdateTreeType"))) {
			String transType = key;
			doTransaction(transType);
		}
		
		myRegistry.updateSubscribers(key, this);
	}

	// -----------------------------------------------------------------------------------

	public void doTransaction(String transType) {
		try {
			Transaction trans = TransactionFactory.createTransaction(transType, myLocale);
			trans.subscribe("CancelTransaction", this);
			trans.stateChangeRequest("DoYourJob", "");
			 
		} catch (Exception ex) {
			transactionErrorMessage = "FATAL ERROR: TRANSACTION FAILURE: Unrecognized transaction!!";
			new Event(Event.getLeafLevelClassName(this), "createTransaction",
					"Transaction Creation Failure: Unrecognized transaction " + ex.toString(), Event.ERROR);
		}
	}

	// -----------------------------------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);

	}

	// -----------------------------------------------------------------------------------
	public void subscribe(String key, IView subscriber) {
		myRegistry.subscribe(key, subscriber);
	}

	// -----------------------------------------------------------------------------------
	public void unSubscribe(String key, IView subscriber) {
		myRegistry.unSubscribe(key, subscriber);
	}

	// -----------------------------------------------------------------------------
	private void createAndShowTreeLotCoordinatorView() {
		Scene currentScene = (Scene) myViews.get("TreeLotCoordinatorView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("TreeLotCoordinatorView", this);
			currentScene = new Scene(newView);
			myViews.put("TreeLotCoordinatorView", currentScene);
		}
		swapToView(currentScene);
	}

	// -----------------------------------------------------------------------------
	private void createAndShowWelcomeView() {
		Scene currentScene = (Scene) myViews.get("WelcomeView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("WelcomeView", this);
			currentScene = new Scene(newView);
			currentScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			myViews.put("WelcomeView", currentScene);
		}
		swapToView(currentScene);
	}

	// -----------------------------------------------------------------------------
	private void createAndShowSearchView() {
		Scene currentScene = (Scene) myViews.get("SearchScoutNameView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("SearchScoutNameView", this);
			currentScene = new Scene(newView);
			myViews.put("WelcomeView", currentScene);
		}
		swapToView(currentScene);
	}
	
	// -----------------------------------------------------------------------------
	public void swapToView(Scene newScene) {
		if (newScene == null) {
			System.out.println("TreeLotCoordinator.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView", "Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();

		// Place in center
		WindowPosition.placeCenter(myStage);
	}

}
