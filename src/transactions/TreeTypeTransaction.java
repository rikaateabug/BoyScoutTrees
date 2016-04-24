package transactions;

import java.util.Locale;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import models.Scout;
import models.ScoutCollection;
import models.Tree;
import models.TreeType;
import models.TreeTypeCollection;
import views.AddScoutView;
import views.AddTreeView;
import views.ScoutCollectionView;
import views.TreeTypeCollectionView;
import views.View;
import views.ViewFactory;

public class TreeTypeTransaction extends Transaction {

	private String transType;

	private TreeType myTreeType;
	private TreeTypeCollection myTreeTypes;

	// ----------------------------------------------------------
	public TreeTypeTransaction(String trans, Locale locale) throws Exception {
		super(locale);
		transType = trans;
	}

	// ----------------------------------------------------------
	protected void setDependencies() {

		dependencies = new Properties();
		dependencies.setProperty("CancelTrans", "CancelTransaction");
		dependencies.setProperty("OK", "CancelTransaction");
		myRegistry.setDependencies(dependencies);
	}

	// ----------------------------------------------------------
	protected Scene createView() {

		if (transType.equals("AddTreeType")) {
			Scene currentScene = myViews.get("AddTreeTypeView");

			if (currentScene == null) {

				View newView = ViewFactory.createView("AddTreeTypeView", this);
				currentScene = new Scene(newView);
				myViews.put("AddTreeTypeView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		}

		else if (transType.equals("UpdateTreeType")) {
			myTreeTypes = new TreeTypeCollection();
			myTreeTypes.findAllTreeTypes();
			
			Scene currentScene = myViews.get("TreeTypeCollectionView");
			
			if (currentScene == null) {
				
				View newView = ViewFactory.createView("TreeTypeCollectionView", this);
				
				currentScene = new Scene(newView);
				myViews.put("TreeTypeCollectionView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		} else {
			return null;
		}
	}

	// ----------------------------------------------------------
	protected Scene createUpdateTreeTypeView() {
		Scene currentScene = myViews.get("UpdateTreeTypeView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("UpdateTreeTypeView", this);
			currentScene = new Scene(newView);
			myViews.put("UpdateTreeTypeView", currentScene);
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
		if (key.equals("TreeType")) {
			return myTreeType;
		}

		if (key.equals("transType")) {
			return transType;
		}
		
		if (key.equals("TreeTypes")) {
			return myTreeTypes;
		}

		if (key.equals("TreeTypesEmpty")) {
			return myTreeTypes.isEmpty();
		}
		
		return null;
	}

	// ----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("DoYourJob")) {
			doYourJob();
		}

		else if (key.equals("searchTreeTypes")) {
			myTreeTypes = new TreeTypeCollection();
			myTreeTypes.findTreeTypesWithBarcodePrefix((String)value);
		}
		
		else if (key.equals("insertNewTreeType")) {
			myTreeType = new TreeType((Properties) value);
			myTreeType.updateStateInDatabase();
		}

		else if (key.equals("UpdateATreeType")) {
			
			myTreeType = new TreeType((Properties) value);
			System.out.println("The tree type has been created");
			myTreeType.updateStateInDatabase();
			
		}

		else if (key.equals("showView")) {
			
			try {
					myTreeType = new TreeType((String) value);
					swapToView(createUpdateTreeTypeView());
				} catch (InvalidPrimaryKeyException e1) {
					e1.printStackTrace();
				}
			}
		myRegistry.updateSubscribers(key, this);
	}

}
