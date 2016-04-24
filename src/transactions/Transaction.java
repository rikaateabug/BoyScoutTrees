package transactions;

import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.MainStageContainer;
import userinterface.WindowPosition;

abstract public class Transaction implements IView, IModel {

	protected Properties dependencies;
	protected ModelRegistry myRegistry;
	protected Stage myStage;
	protected Hashtable<String, Scene> myViews;
	protected Locale myLocale;
	

	// -----------------------------------------------------------------------------
	// Constructor that takes in the locale
	protected Transaction(Locale locale) throws Exception {
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();
		myLocale = locale;
		
		myRegistry = new ModelRegistry("Transaction");
		if (myRegistry == null) {
			new Event(Event.getLeafLevelClassName(this), "Transaction", "Could not instantiate Registry", Event.ERROR);
		}
		setDependencies();
	}

	// -----------------------------------------------------------------------------
	protected void doYourJob() {
		try {
			Scene newScene = createView();
			swapToView(newScene);
		} catch (Exception e) {
			new Event(Event.getLeafLevelClassName(this), "Transaction", "Could not find any class", Event.ERROR);
		}
	}

	// -----------------------------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}

	// -----------------------------------------------------------------------------
	public void subscribe(String key, IView subscriber) {
		myRegistry.subscribe(key, subscriber);
	}

	// -----------------------------------------------------------------------------
	public void unSubscribe(String key, IView subscriber) {
		myRegistry.unSubscribe(key, subscriber);
	}

	// -----------------------------------------------------------------------------
	public void swapToView(Scene newScene) {
		if (newScene == null) {
			new Event(Event.getLeafLevelClassName(this), "swapToView", "Missing view for display ", Event.ERROR);
			return;
		}
		
		myStage.setScene(newScene); //
		myStage.sizeToScene();

		WindowPosition.placeCenter(myStage);
	}

	// Abstract Classes:
	protected abstract void setDependencies();

	protected abstract Scene createView();

	public abstract Object getState(String key);

	public abstract void stateChangeRequest(String key, Object value);

}
