package transactions;

import java.util.Locale;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import models.Scout;
import models.ScoutCollection;
import views.AddScoutView;
import views.ScoutCollectionView;
import views.View;
import views.ViewFactory;

public class ScoutTransaction extends Transaction {

	private String addScoutErrorMessage = "";
	private String addScoutStatusMessage = "";

	private String transType;
	private Scout myScout;
	private ScoutCollection myScoutCollection;
	
	// ----------------------------------------------------------
	public ScoutTransaction(String trans, Locale locale) throws Exception {
		super(locale);
		transType = trans;
		myScoutCollection = new ScoutCollection();
	}

// ----------------------------------------------------------
	protected void setDependencies() {
		
		dependencies = new Properties();
		dependencies.setProperty("DoTransfer", "TransactionError");
		dependencies.setProperty("CancelAddScout", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		myRegistry.setDependencies(dependencies);

	}

	// ----------------------------------------------------------
	protected Scene createView() {
		
		if (transType.equals("AddScout")) {
			Scene currentScene = myViews.get("AddScoutView");

			if (currentScene == null) {
				View newView = ViewFactory.createView("AddScoutView", this);
				currentScene = new Scene(newView);
				myViews.put("AddScoutView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		}
		
		else if (transType.equals("UpdateScout") || (transType.equals("RemoveScout"))) {
			Scene currentScene = myViews.get("SearchScoutNameView");

			if (currentScene == null) {
				View newView = ViewFactory.createView("SearchScoutNameView", this);
				currentScene = new Scene(newView);
				myViews.put("SearchScoutNameView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		}
		else {
			return null;
		}
	}

	// ----------------------------------------------------------
	protected Scene createScoutCollectionView() {
		
		Scene currentScene = myViews.get("ScoutCollectionView");
		
		if (currentScene == null) {
			//View newView = ViewFactory.createView("ScoutCollectionView", this);
			ScoutCollectionView newView = new ScoutCollectionView(myScoutCollection);
			currentScene = new Scene(newView);
			myViews.put("ScoutCollectionView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}
	
	// ----------------------------------------------------------
	protected Scene createUpdateScoutView() {
		Scene currentScene = myViews.get("UpdateScoutView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("UpdateScoutView", this);
			currentScene = new Scene(newView);
			myViews.put("UpdateScoutView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}
	
	// ----------------------------------------------------------
	protected Scene createDeleteScoutView() {
		Scene currentScene = myViews.get("DeleteScoutView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("DeleteScoutView", this);
			currentScene = new Scene(newView);
			myViews.put("DeleteScoutView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}
	// ----------------------------------------------------------
	public Object getState(String key) {
		if (key.equals("Locale")) {
			return myLocale;
		}
		if (key.equals("Scout")) {
			return myScout;
		}
		if (key.equals("ScoutList")) {
			return myScoutCollection;
		}
		if (key.equals("transType")) {
			return transType;
		}
		
		return null;
	}

	// ----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("DoYourJob")) {
			doYourJob();
		}

		else if (key.equals("insertNewScout")) {
			Scout s = new Scout((Properties)value);
			s.updateStateInDatabase();
		}
		
		else if (key.equals("UpdateAScout")) {
			myScout = new Scout((Properties)value);
			myScout.updateStateInDatabase();
		}
		
		else if (key.equals("DeleteAScout")) {
			myScout = new Scout((Properties)value);
			myScout.updateStateInDatabase();
		}
		
		else if (key.equals("showCollectionView")) {
			Properties props = (Properties) value;
			String firstName = props.getProperty("firstName");
			String lastName = props.getProperty("lastName");
			myScoutCollection = new ScoutCollection(this);
			myScoutCollection.findScoutsWithNameLike(firstName, lastName);
			swapToView(createScoutCollectionView());
		}

		else if (key.equals("showDeleteView")) {
			try {
				myScout = new Scout((String)value);
				swapToView(createDeleteScoutView());
			} catch (InvalidPrimaryKeyException e1) {
				e1.printStackTrace();
			}
		}
		
		else if (key.equals("showUpdateView")) {
			try {
				myScout = new Scout((String)value);
				swapToView(createUpdateScoutView());
			} catch (InvalidPrimaryKeyException e1) {
				e1.printStackTrace();
			}
		}
		
		myRegistry.updateSubscribers(key, this);
	}

}
