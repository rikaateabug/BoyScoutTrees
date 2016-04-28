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

public class Shift extends EntityBase implements IView {

	private static final String myTableName = "Shift";
	protected Properties dependencies;
	private String updateStatusMessage = "";
	// protected Stage myStage; Don't need stage

	protected TreeLotCoordinator myTLC; // Pretty Sure I don't need this

	// ----------------------------------------------------------
	// Constructor for Shift from database
	// ------------------------------------------------------
	public Shift(String shiftID) throws InvalidPrimaryKeyException {
		super(myTableName);

		String query = "SELECT * FROM " + myTableName + " WHERE (shiftID = "
				+ shiftID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one shift at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one shift. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple shifts matching id : " + shiftID + " found.");
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
		// If no shift found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No shift matching id : "
					+ shiftID + " found.");
		}
	}

	// ------------------------------------------------------
	// Constructor taking a new shift
	// ------------------------------------------------------
	public Shift(Properties props) {
		super(myTableName);
		setDependencies();
		setData(props);
	}

	// ------------------------------------------------------
	public Shift() {
		super(myTableName);
		setDependencies();
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
	public void insertNewShift() {
		try {
			Integer i = insertAutoIncrementalPersistentState(mySchema, persistentState);
			System.out.println(i + " is the primary key of the new shift");
		} catch (SQLException e) {
			System.out.println("Error inserting new shift into the database" + e.toString());
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
			if (persistentState.getProperty("shiftID") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("shiftID",
						persistentState.getProperty("shiftID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "shift data for shiftID : "
						+ persistentState.getProperty("shiftID")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {

				Integer shiftID = insertAutoIncrementalPersistentState(mySchema, persistentState);
				
				persistentState.setProperty("barcode", "" + shiftID.intValue());
				updateStatusMessage = "shift data for new shift : "
						+ persistentState.getProperty("shiftID")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing shift data in database!";
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

		v.addElement(persistentState.getProperty("shiftID"));
		v.addElement(persistentState.getProperty("sessionID"));
		v.addElement(persistentState.getProperty("scoutID"));
		v.addElement(persistentState.getProperty("companionName"));
		v.addElement(persistentState.getProperty("startTime"));
		v.addElement(persistentState.getProperty("endTime"));
		v.addElement(persistentState.getProperty("companionHours"));


		return v;
	}

	// ----------------------------------------------------------
	// Method used in the tester class
	// ----------------------------------------------------------
	public String toString() {

		String s = persistentState.getProperty("shiftID") + " \n"
				+ persistentState.getProperty("sessionID") + " \n"
				+ persistentState.getProperty("scoutID") + " \n"
				+ persistentState.getProperty("companionName") + " \n"
				+ persistentState.getProperty("startTime") + " \n"
				+ persistentState.getProperty("endTime") + " \n"
				+ persistentState.getProperty("companionHours");
		return s;
	}
}
