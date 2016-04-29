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

import views.View;
import views.ViewFactory;
import javafx.*;
import model.EntityBase;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Sale extends EntityBase implements IView {

	private static final String myTableName = "Transaction";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	// ----------------------------------------------------------
	// Constructor for trans from database
	// ------------------------------------------------------
	public Sale(String transID) throws InvalidPrimaryKeyException {
		super(myTableName);

		String query = "SELECT * FROM " + myTableName + " WHERE (transID = "
				+ transID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one trans at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one trans. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple transs matching id : " + transID + " found.");
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
		// If no trans found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No trans matching id : "
					+ transID + " found.");
		}
	}

	// ------------------------------------------------------
	// Constructor taking a new trans
	// ------------------------------------------------------
	public Sale(Properties props) {
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
	public void insertNewSale() {
		try {
			Integer i = insertAutoIncrementalPersistentState(mySchema, persistentState);
			System.out.println(i + " is the primary key of the new sale");
		} catch (SQLException e) {
			System.out.println("Error inserting new sale into the database" + e.toString());
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
			if (persistentState.getProperty("transID") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("transID",
						persistentState.getProperty("transID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "trans data for transID : "
						+ persistentState.getProperty("transID")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {

				Integer transID = insertAutoIncrementalPersistentState(mySchema, persistentState);
				
				persistentState.setProperty("barcode", "" + transID.intValue());
				updateStatusMessage = "trans data for new trans : "
						+ persistentState.getProperty("transID")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing transaction data in database!";
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

		v.addElement(persistentState.getProperty("transID"));
		v.addElement(persistentState.getProperty("sessionID"));
		v.addElement(persistentState.getProperty("transactionType"));
		v.addElement(persistentState.getProperty("barcode"));
		v.addElement(persistentState.getProperty("transactionAmount"));
		v.addElement(persistentState.getProperty("paymentMethod"));
		v.addElement(persistentState.getProperty("customerName"));
		v.addElement(persistentState.getProperty("customerPhone"));
		v.addElement(persistentState.getProperty("customerEmail"));
		v.addElement(persistentState.getProperty("transactionDate"));
		v.addElement(persistentState.getProperty("transactionTime"));
		v.addElement(persistentState.getProperty("dateStatusUpdated"));
		return v;
	}

	// ----------------------------------------------------------
	// Method used in the tester class
	// ----------------------------------------------------------
	public String toString() {

		String s = persistentState.getProperty("transID") + " \n"
				+ persistentState.getProperty("sessionID") + " \n"
				+ persistentState.getProperty("transactionType") + " \n"
				+ persistentState.getProperty("barcode") + " \n"
				+ persistentState.getProperty("transactionAmount") + " \n"
				+ persistentState.getProperty("paymentMethod") + " \n"
				+ persistentState.getProperty("customerName") + " \n"
				+ persistentState.getProperty("customerPhone") + " \n"
				+ persistentState.getProperty("customerEmail") + " \n"
				+ persistentState.getProperty("transactionDate") + " \n"
				+ persistentState.getProperty("transactionTime") + " \n"
				+ persistentState.getProperty("dateStatusUpdated");
		return s;
	}
}
