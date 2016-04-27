package models;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;

import exception.InvalidPrimaryKeyException;
import database.*;
import impresario.IView;
import userinterface.MainStageContainer;
import views.AddScoutView;
import views.View;
import views.ViewFactory;
import javafx.*;
import model.EntityBase;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Scout extends EntityBase implements IView {

	private static final String myTableName = "Scout";
	protected Properties dependencies;
	private String updateStatusMessage = "";
	// protected Stage myStage; Don't need stage

	protected TreeLotCoordinator myTLC; // Pretty Sure I don't need this

	// ----------------------------------------------------------
	// Constructor for scout from database
	// ------------------------------------------------------
	public Scout(String scoutID) throws InvalidPrimaryKeyException {
		super(myTableName);

		String query = "SELECT * FROM " + myTableName + " WHERE (scoutID = "
				+ scoutID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one scout at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one scout. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple scouts matching id : " + scoutID + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved
						.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedAccountData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String) allKeys.nextElement();
					String nextValue = retrievedAccountData
							.getProperty(nextKey);

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no scout found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No scout matching id : "
					+ scoutID + " found.");
		}
	}

	// ------------------------------------------------------
	// Constructor taking a new Scout
	// ------------------------------------------------------
	public Scout(Properties props) {
		super(myTableName);
		setDependencies();
		setData(props);
	}

	// ------------------------------------------------------
	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	// ------------------------------------------------------
	public void setData(Properties props) {
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true) {
			String nextKey = (String) allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			
			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	
	
	
	
	// ------------------------------------------------------
	public Object getState(String key) {
		{
			if (key.equals("UpdateStatusMessage") == true)
				return updateStatusMessage;

			return persistentState.getProperty(key);
		}
	}

	// ------------------------------------------------------
	protected void initializeSchema(String tableName) {
		if (mySchema == null) {
			mySchema = getSchemaInfo(myTableName);
		}
	}

	// ------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		
		myRegistry.updateSubscribers(key, this);
	}

	// ------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}
	

	// ------------------------------------------------------
	public void updateStateInDatabase() {

		try {
			if (persistentState.getProperty("scoutID") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("scoutID",
						persistentState.getProperty("scoutID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Scout data for ScoutID : "
						+ persistentState.getProperty("scoutID")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {

				Integer scoutID = insertAutoIncrementalPersistentState(mySchema, persistentState);
				
				persistentState.setProperty("barcode", "" + scoutID.intValue());
				updateStatusMessage = "Scout data for new scout : "
						+ persistentState.getProperty("scoutID")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing scout data in database!";
		}
	}

	// ------------------------------------------------------
	public void deleteStateInDatabase() {
		try {
			Integer status = deletePersistentState(mySchema, persistentState);
			System.out.println(status.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// ------------------------------------------------------
	public Vector<String> getEntryListView() {
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("scoutID"));
		v.addElement(persistentState.getProperty("lastName"));
		v.addElement(persistentState.getProperty("firstName"));
		v.addElement(persistentState.getProperty("middleName"));
		v.addElement(persistentState.getProperty("birthDate"));
		v.addElement(persistentState.getProperty("phoneNumber"));
		v.addElement(persistentState.getProperty("email"));
		v.addElement(persistentState.getProperty("troopID"));
		v.addElement(persistentState.getProperty("status"));
		v.addElement(persistentState.getProperty("dateStatusUpdated"));

		return v;
	}

	// ----------------------------------------------------------
	// Method used in the tester class
	// ----------------------------------------------------------
	public String toString() {

		String s = persistentState.getProperty("scoutID") + " \n"
				+ persistentState.getProperty("lastName") + " \n"
				+ persistentState.getProperty("firstName") + " \n"
				+ persistentState.getProperty("middleName") + " \n"
				+ persistentState.getProperty("dateOfBirth") + " \n"
				+ persistentState.getProperty("phoneNumber") + " \n"
				+ persistentState.getProperty("email") + " \n"
				+ persistentState.getProperty("troopID") + " \n"
				+ persistentState.getProperty("status") + " \n"
				+ persistentState.getProperty("dateStatusUpdated");
		return s;
	}
}
