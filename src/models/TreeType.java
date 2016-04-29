package models;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;

import exception.InvalidPrimaryKeyException;
import database.*;
import impresario.IView;
import models.MainStageContainer;
import views.View;
import views.ViewFactory;
import javafx.*;
import model.EntityBase;
import views.AddScoutView;
//import .LibrarianView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TreeType extends EntityBase implements IView {

	private static final String myTableName = "TreeType";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	// ----------------------------------------------------------
	public TreeType(String id) throws InvalidPrimaryKeyException {
		super(myTableName);

		String query = "SELECT * FROM " + myTableName + " WHERE (treeTypeID=" + id + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one account. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple Tree Types matching treeTypeID : " + id
								+ " found.");
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
		// If no tree found for this treeTypeID, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No Tree Type matching ID : "
					+ id + " found.");
		}
	}

	// ----------------------------------------------------------
	private void setDependencies() {
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}

	// ----------------------------------------------------------
	public TreeType(Properties props) {
		super(myTableName);
		setDependencies();
		setData(props);

		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true) {
			String nextKey = (String) allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);

			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}

	// ----------------------------------------------------------
	public TreeType() {
		super(myTableName);
		//myStage = MainStageContainer.getInstance();
		persistentState = new Properties();
	}

	// ----------------------------------------------------------
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

	// ----------------------------------------------------------
	public Object getState(String key) {
		{
			if (key.equals("UpdateStatusMessage") == true)
				return updateStatusMessage;

			return persistentState.getProperty(key);
		}
	}

	// ----------------------------------------------------------
	protected void initializeSchema(String tableName) {
		if (mySchema == null) {
			mySchema = getSchemaInfo(myTableName);
		}
	}

	// ----------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {
		{

			myRegistry.updateSubscribers(key, this);
		}
	}

	// ----------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}

	// ----------------------------------------------------------
	public void updateStateInDatabase() {

		try {
			if (persistentState.getProperty("treeTypeID") != null) {

				Properties whereClause = new Properties();
				whereClause.setProperty("treeTypeID",
						persistentState.getProperty("treeTypeID"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "TreeType data for treeTypeID : "
						+ persistentState.getProperty("treeTypeID")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {

				Integer id = insertAutoIncrementalPersistentState(mySchema,
						persistentState);
				persistentState.setProperty("id", "" + id.intValue());
				updateStatusMessage = "TreeType data for new tree type : "
						+ persistentState.getProperty("id")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing tree type data in database!";
		}
		// DEBUG System.out.println("updateStateInDatabase " +
		// updateStatusMessage);
	}

	// ----------------------------------------------------------
	public String searchBarcodePrefix(String barcodePrefix)
			throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix=" + barcodePrefix + ")";
		
		Vector allDataRetrieved = getSelectQueryResult(query);
		
		// You must get one treeType at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();
			// There should be EXACTLY one TreeType. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple accounts matching the barcode prefix : "
								+ barcodePrefix + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved
						.elementAt(0);
				return retrievedAccountData.getProperty("treeTypeID");
			}
		}
		// If no TreeType found for this treeTypeID, throw an exception
		else {
			throw new InvalidPrimaryKeyException(
					"No tree Type matching barcode prefix : " + barcodePrefix
							+ " found.");
		}
	}

	// ----------------------------------------------------------
	public String getTreeType(String barcodePrefix) throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix=" + barcodePrefix + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one treeType at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();
			// There should be EXACTLY one TreeType. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple accounts matching the barcode prefix : " + barcodePrefix + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved.elementAt(0);
				return retrievedAccountData.getProperty("typeDescription");
			}
		}
		// If no TreeType found for this treeTypeID, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No tree Type matching barcode prefix : " + barcodePrefix + " found.");
		}
	}

	// ----------------------------------------------------------
	public String getTreeTypeCost(String barcodePrefix) throws InvalidPrimaryKeyException {

		String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix=" + barcodePrefix + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one treeType at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();
			// There should be EXACTLY one TreeType. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple accounts matching the barcode prefix : " + barcodePrefix + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedAccountData = (Properties) allDataRetrieved.elementAt(0);
				return retrievedAccountData.getProperty("cost");
			}
		}
		// If no TreeType found for this treeTypeID, throw an exception
		else {
			throw new InvalidPrimaryKeyException("No tree Type matching barcode prefix : " + barcodePrefix + " found.");
		}
	}
	
	// ------------------------------------------------------
	public String toString() {
		String s = persistentState.getProperty("typeDescription") + " \t" + 
				   persistentState.getProperty("cost") + " \t" +
				   persistentState.getProperty("barcodePrefix");
		return s;
	}

	public Vector<String> getEntryListView() {
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("treeTypeID"));
		v.addElement(persistentState.getProperty("typeDescription"));
		v.addElement(persistentState.getProperty("cost"));
		v.addElement(persistentState.getProperty("barcodePrefix"));

		return v;
	}

}
