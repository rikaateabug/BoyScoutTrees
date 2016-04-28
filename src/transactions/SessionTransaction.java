package transactions;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import impresario.IView;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import models.Scout;
import models.ScoutCollection;
import models.Session;
import models.Shift;
import views.AddScoutView;
import views.ScoutCollectionView;
import views.View;
import views.ViewFactory;

public class SessionTransaction extends Transaction {

	private Session mySession;
	private ScoutCollection myScoutCollection;
	private ArrayList<Shift> myShifts;
	private String mySessionID;
	// ----------------------------------------------------------
	public SessionTransaction(Locale locale) throws Exception {
		super(locale);
	}

	// ----------------------------------------------------------
	protected void setDependencies() {

		dependencies = new Properties();
		dependencies.setProperty("DoTransfer", "TransactionError");
		dependencies.setProperty("CancelAddScout", "CancelTransaction");
		dependencies.setProperty("UpdateTLC", "UpdateTreeLotCoordinator");
		dependencies.setProperty("OK", "CancelTransaction");
		myRegistry.setDependencies(dependencies);

	}

	// ----------------------------------------------------------
	protected Scene createView() {

		Scene currentScene = myViews.get("OpenSessionView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("OpenSessionView", this);
			currentScene = new Scene(newView);
			myViews.put("OpenSessionView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}

	
	// ----------------------------------------------------------
	protected Scene createScoutCollectionView() {

		myViews.remove("ScoutCollectionView");
		ScoutCollectionView newView = new ScoutCollectionView(myScoutCollection);
		Scene currentScene = new Scene(newView);
		myViews.put("ScoutCollectionView", currentScene);
		return currentScene;
	}

	// ----------------------------------------------------------
	protected Scene createScoutShiftView() {

		Scene currentScene = myViews.get("ScoutShiftView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("ScoutShiftView", this);
			currentScene = new Scene(newView);
			myViews.put("ScoutShiftView", currentScene);
			return currentScene;
		} else {
			return currentScene;
		}
	}

	// ----------------------------------------------------------
	protected Scene createConfirmSessionView() {

		Scene currentScene = myViews.get("ConfirmSessionView");

		if (currentScene == null) {
			View newView = ViewFactory.createView("ConfirmSessionView", this);
			currentScene = new Scene(newView);
			myViews.put("ConfirmSessionView", currentScene);
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
		if (key.equals("Session")) {
			return mySession;
		}
		if (key.equals("SessionID")) {
			return mySessionID;
		}
		if (key.equals("ScoutCollection")) {
			return myScoutCollection;
		}
		if (key.equals("myShifts")) {
			return myShifts;
		}
		return null;
	}

	// ----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("DoYourJob")) {
			doYourJob();
		}
		
		else if (key.equals("insertShifts")) {
			ArrayList<Properties> myProps = (ArrayList<Properties>) value;

			Shift theShift;
			for (Properties props : myProps) {
				theShift = new Shift(props);
				theShift.insertNewShift();
			}
		}
		
		else if (key.equals("selectScouts")) {
			mySession = new Session((Properties) value);
			mySessionID = mySession.insertNewSession().toString();
			System.out.println("The Session has been saved by session transaction");
			myScoutCollection = new ScoutCollection(this);
			myScoutCollection.findAllActiveScoutsAlphabetically();
			swapToView(createScoutCollectionView());
		}
		
		else if (key.equals("ShowScoutShiftView")) {
			try {
				myScoutCollection.setScoutsFromSelection((ObservableList<views.ScoutTableModel>)value);
				//System.out.println("The size of the scout collection is: " + myScoutCollection.size());
				swapToView(createScoutShiftView());
			} catch (InvalidPrimaryKeyException e) {
				e.printStackTrace();
			}
		}
		
		else if (key.equals("ShowConfirmSessionView")) {
			myShifts = (ArrayList<Shift>) value;
			swapToView(createConfirmSessionView());
		}
		
		
		myRegistry.updateSubscribers(key, this);
	}

}
