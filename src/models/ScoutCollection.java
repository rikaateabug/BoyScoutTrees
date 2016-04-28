package models;

import java.awt.Event;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;

import database.*;
import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import impresario.IView;
import transactions.ScoutTransaction;
import transactions.SessionTransaction;
import userinterface.MainStageContainer;
import views.ScoutCollectionView;
import views.ScoutTableModel;
import views.View;
import views.ViewFactory;
import javafx.*;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import model.EntityBase;

import java.sql.SQLException;
import java.util.Properties;

import model.EntityBase;

public class ScoutCollection extends EntityBase implements IView {

	private static final String myTableName = "Scout";

	private Vector<Scout> scouts;
	protected ScoutTransaction myScoutTrans;
	protected SessionTransaction mySessionTrans;
	private Locale myLocale;
	private String transType;
	
	// ------------------------------------------------------------------
	public ScoutCollection() {
		super(myTableName);

		scouts = new Vector<Scout>();
	}

	// ------------------------------------------------------------------
	public ScoutCollection(IModel trans) {
		super(myTableName);
		myStage = MainStageContainer.getInstance();
		scouts = new Vector<Scout>();

		if (trans instanceof ScoutTransaction) {
			//System.out.println("This is a scout transaction");
			myScoutTrans = (ScoutTransaction) trans;
			myLocale = (Locale) myScoutTrans.getState("Locale");
			transType = "ScoutTransaction";
		} 
		
		else if (trans instanceof SessionTransaction) {
			//System.out.println("This is a session transaction");
			mySessionTrans = (SessionTransaction) trans;
			myLocale = (Locale) mySessionTrans.getState("Locale");
			transType = "SessionTransaction";
		}

		persistentState = new Properties();
	}

	// ------------------------------------------------------------------
	public void findScoutsWithFirstNameLike(String name) {

		String query = "SELECT * FROM " + myTableName + " WHERE (firstName LIKE '%" + name + "%')";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null) {

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved.elementAt(cnt);

				Scout scout = new Scout(nextAccountData);

				if (scout != null) {
					addScout(scout);
				}
			}

		}
	}

	// ------------------------------------------------------------------
	public void findScoutsWithNameLike(String firstName, String lastName) {

		if (firstName == null && lastName == null) {
			System.out.println("Error, first and last cannot be null");
			return;
		}

		String query = "";

		if (firstName == null) {
			query = "SELECT * FROM " + myTableName + " WHERE (lastName LIKE '%" + lastName + "%')";
		}

		if (lastName == null) {
			query = "SELECT * FROM " + myTableName + " WHERE (firstName LIKE '%" + firstName + "%')";
		}

		if (firstName != null && lastName != null) {
			query = "SELECT * FROM " + myTableName + " WHERE (firstName LIKE '%" + firstName
					+ "%') AND (lastName LIKE '%" + lastName + "%')";
		}

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null) {

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved.elementAt(cnt);

				Scout scout = new Scout(nextAccountData);

				if (scout != null) {
					addScout(scout);
				}
			}

		}
	}

	// ------------------------------------------------------------------
	public void findAllActiveScoutsAlphabetically() {

		String query = "SELECT * FROM " + myTableName + " WHERE (status = 'Active') GROUP BY firstName";

		Vector allDataRetrieved = getSelectQueryResult(query);
	
		if (allDataRetrieved != null) {

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved.elementAt(cnt);

				Scout scout = new Scout(nextAccountData);

				if (scout != null) {
					addScout(scout);
				}
			}

		}
	}

	// ------------------------------------------------------------------
	public Object getState(String key) {
		if (key.equals("Scouts"))
			return scouts;
		else if (key.equals("ScoutList"))
			return this;

		if (key.equals("Locale")) {
			return myLocale;
		}
		if (key.equals("TransactionType")) {
			return transType;
		}

		if (key.equals("ScoutTransaction")) {
			return myScoutTrans;
		}
		if (key.equals("SessionTransaction")) {
			return mySessionTrans;
		}
		
		return null;
	}

	// ------------------------------------------------------------------
	protected void initializeSchema(String tableName) {
		if (mySchema == null) {
			mySchema = getSchemaInfo(tableName);
		}
	}
	
	// ------------------------------------------------------------------
	public int size() {
		return scouts.size();
	}
	
	// ------------------------------------------------------------------
	public Scout get(int pos) {
		return scouts.get(pos);
	}

	// ------------------------------------------------------------------
	public void setScoutsFromSelection(ObservableList<ScoutTableModel> selectedScouts) throws InvalidPrimaryKeyException {
		//getScoutID()
		scouts.clear();
		for (ScoutTableModel stm : selectedScouts) {
			Scout s = new Scout(stm.getScoutID());
			scouts.add(s);
		}
	}

	

	// ------------------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("showView")) {
			if (myScoutTrans.getState("transType").equals("RemoveScout")) {
				myScoutTrans.stateChangeRequest("showDeleteView", value);
			}
			if (myScoutTrans.getState("transType").equals("UpdateScout")) {
				myScoutTrans.stateChangeRequest("showUpdateView", value);
			}
		}
		if (key.equals("CancelAddScout")) {
			myScoutTrans.stateChangeRequest("CancelAddScout", null);
		}

		if (key.equals("CancelSession")) {
			mySessionTrans.stateChangeRequest("CancelAddScout", null);
		}
		
		if (key.equals("ShowScoutShiftView")) {
			mySessionTrans.stateChangeRequest("ShowScoutShiftView", value);
		}
		
		myRegistry.updateSubscribers(key, this);

	}

	// ------------------------------------------------------------------
	private void addScout(Scout s) {
		scouts.add(s);
	}

	// ------------------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}

	// ------------------------------------------------------------------
	public void display() {
		for (int i = 0; i < scouts.size(); i++) {
			System.out.println(scouts.get(i).toString());
		}
	}
}
