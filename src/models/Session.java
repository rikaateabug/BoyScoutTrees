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

public class Session extends EntityBase implements IView {

	private static final String myTableName = "Session";
	protected Properties dependencies;
	private String updateStatusMessage = "";
	// protected Stage myStage; Don't need stage

	protected TreeLotCoordinator myTLC; // Pretty Sure I don't need this

	// ----------------------------------------------------------
	// Constructor for Session from database
	// ------------------------------------------------------
	public Session(String sessionID) throws InvalidPrimaryKeyException {
		super(myTableName);

		String query = "SELECT * FROM " + myTableName + " WHERE (sessionID = " + sessionID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one session at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one session. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple sessions matching id : " + sessionID + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedAccountData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String) allKeys.nextElement();
					String nextValue = retrievedAccountData.getProperty(nextKey);

					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}

			}
		}
		// If no session found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No session matching id : " + sessionID + " found.");
		}
	}

	// ------------------------------------------------------
	// Constructor taking a new session
	// ------------------------------------------------------
	public Session(Properties props) {
		super(myTableName);
		setDependencies();
		setData(props);
	}

	// ------------------------------------------------------
	// Constructor used to see if there's an open Session 
	// ------------------------------------------------------
	public Session() {
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
	public Integer insertNewSession()
	{		
		try 
		{
			Integer i = insertAutoIncrementalPersistentState(mySchema, persistentState);	
			System.out.println(i + " is the primary key of the new Book");
			return i;
		}
		catch (SQLException e) {
			System.out.println("Error inserting new Book into the database" + e.toString());
		}
		
		return null;
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
			if (persistentState.getProperty("sessionID") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("sessionID", persistentState.getProperty("sessionID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "session data for sessionID : " + persistentState.getProperty("sessionID")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {
				
				Integer sessionID = insertAutoIncrementalPersistentState(mySchema, persistentState);
				System.out.println("I got here");
				persistentState.setProperty("sessionID", "" + sessionID.intValue());
				updateStatusMessage = "session data for new session : " + persistentState.getProperty("sessionID")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing session data in database!";
		}
	}

	// ------------------------------------------------------
	public boolean isOpenSession() throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (endTime IS NULL)";

		Vector allDataRetrieved = getSelectQueryResult(query);
		int size = allDataRetrieved.size();
		
		if (size > 0) {
			return true;
		}	
		else {
			return false;
		}
	}

	// ------------------------------------------------------
	public String getOpenSessionID() throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (endTime IS NULL)";
		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one session at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one session. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple sessions found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved.elementAt(0);
				return retrievedAccountData.getProperty("sessionID");

			}
		}
		// If no session found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No session  found.");
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

		v.addElement(persistentState.getProperty("sessionID"));
		v.addElement(persistentState.getProperty("startDate"));
		v.addElement(persistentState.getProperty("startTime"));
		v.addElement(persistentState.getProperty("endTime"));
		v.addElement(persistentState.getProperty("startingCash"));
		v.addElement(persistentState.getProperty("endingCash"));
		v.addElement(persistentState.getProperty("totalCheckTrans"));
		v.addElement(persistentState.getProperty("notes"));

		return v;
	}

	// ----------------------------------------------------------
	// Method used in the tester class
	// ----------------------------------------------------------
	public String toString() {

		String s = "sessionID: " + persistentState.getProperty("sessionID") + " \nstartDate: " + persistentState.getProperty("startDate") + " \nstartTime:"
				+ persistentState.getProperty("startTime") + " \nendTime: " + persistentState.getProperty("endTime") + " \nstartingCash: "
				+ persistentState.getProperty("startingCash") + " \nendingCash: " + persistentState.getProperty("endingCash")
				+ " \ntotalChecks: " + persistentState.getProperty("totalCheckTrans") + " \nnotes: " + persistentState.getProperty("notes");
		return s;
	}
}
