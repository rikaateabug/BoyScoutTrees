package transactions;

import java.util.Locale;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.scene.Scene;
import models.Scout;
import models.ScoutCollection;
import models.Tree;
import views.AddScoutView;
import views.AddTreeView;
import views.ScoutCollectionView;
import views.View;
import views.ViewFactory;

public class TreeTransaction extends Transaction {

	private String addScoutErrorMessage = "";
	private String addScoutStatusMessage = "";

	private String transType;

	private Tree myTree;

	// ----------------------------------------------------------
	public TreeTransaction(String trans, Locale locale) throws Exception {
		super(locale);
		transType = trans;
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

		if (transType.equals("AddTree")) {
			Scene currentScene = myViews.get("AddTreeView");

			if (currentScene == null) {

				View newView = ViewFactory.createView("AddTreeView", this);
				currentScene = new Scene(newView);
				myViews.put("AddTreeView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		}

		else if (transType.equals("UpdateTree")
				|| (transType.equals("RemoveTree"))) {
			Scene currentScene = myViews.get("SearchTreeBarcodeView");

			if (currentScene == null) {
				View newView = ViewFactory.createView("SearchTreeBarcodeView",
						this);
				currentScene = new Scene(newView);
				myViews.put("SearchTreeBarcodeView", currentScene);
				return currentScene;
			} else {
				return currentScene;
			}
		} else {
			return null;
		}
	}

	// ----------------------------------------------------------
	protected Scene createUpdateTreeView() {
		Scene currentScene = myViews.get("UpdateTreeView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("UpdateTreeView", this);
			currentScene = new Scene(newView);
			myViews.put("UpdateTreeView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}

	// ----------------------------------------------------------
	protected Scene createDeleteTreeView() {
		Scene currentScene = myViews.get("DeleteTreeView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("DeleteTreeView", this);
			currentScene = new Scene(newView);
			myViews.put("DeleteTreeView", currentScene);
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
		if (key.equals("Tree")) {
			return myTree;
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

		else if (key.equals("insertNewTree")) {
			myTree = new Tree((Properties) value);
			myTree.insertIntoInDatabase();
		}

		else if (key.equals("UpdateATree")) {
			myTree = new Tree((Properties) value);
			myTree.updateStateInDatabase();
		}

		else if (key.equals("DeleteATree")) {
			myTree = (Tree) value;
			myTree.deleteTree();
		}

		else if (key.equals("showView")) {
			
			if (transType.equals("RemoveTree")) {
				try {
					myTree = new Tree((String) value);
					swapToView(createDeleteTreeView());
				} catch (InvalidPrimaryKeyException e1) {
					e1.printStackTrace();
				}
			}

			if (transType.equals("UpdateTree")) {
				try {
					myTree = new Tree((String) value);
					swapToView(createUpdateTreeView());
				} catch (InvalidPrimaryKeyException e1) {
					e1.printStackTrace();
				}
			}
		}
		myRegistry.updateSubscribers(key, this);
	}

}
