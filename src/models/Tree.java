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
//import userinterface.LibrarianView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Tree extends EntityBase implements IView {

	private static final String myTableName = "Tree";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	// ----------------------------------------------------------
	public Tree(String barcode) throws InvalidPrimaryKeyException {
		super(myTableName);

		//String query = "SELECT * FROM " + myTableName
		//		+ " WHERE barcode LIKE '%" + barcode + "%'";

		String query = "SELECT * FROM " + myTableName + " WHERE (barcode=" + barcode + ")";
		
		Vector allDataRetrieved = getSelectQueryResult(query);

		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one account. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException(
						"Multiple trees matching id : " + barcode + " found.");
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
		// If no account found for this user name, throw an exception
		else {
			throw new InvalidPrimaryKeyException(
					"No account matching barcode : " + barcode + " found.");
		}
	}

	// ----------------------------------------------------------
	private void setDependencies() {
		dependencies = new Properties();

		myRegistry.setDependencies(dependencies);
	}

	// ----------------------------------------------------------
	public Tree(Properties props) {
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
			if (persistentState.getProperty("barcode") != null) {

				Properties whereClause = new Properties();
				whereClause.setProperty("barcode",
						persistentState.getProperty("barcode"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Tree data for barcode : "
						+ persistentState.getProperty("barcode")
						+ " updated successfully in database!";
				System.out.println(updateStatusMessage);
			} else {

				Integer barcode = insertAutoIncrementalPersistentState(
						mySchema, persistentState);
				persistentState.setProperty("barcode", "" + barcode.intValue());
				updateStatusMessage = "Tree data for new tree : "
						+ persistentState.getProperty("barcode")
						+ " installed successfully in database!";
				System.out.println(updateStatusMessage);

			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing tree data in database!";
		}
	}

	// ----------------------------------------------------------
	public void insertIntoInDatabase() {

		try {
			Integer id = insertPersistentState(mySchema, persistentState);
			// persistentState.setProperty("id", "" + id.intValue());
			updateStatusMessage = "TreeType data for new tree type : "
					+ persistentState.getProperty("treeTypeID")
					+ " installed successfully in database!";
			System.out.println(updateStatusMessage);

		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing tree type data in database!";
		}
	}

	// ----------------------------------------------------------
	public void deleteTree() {

		try {
			Properties whereClause = new Properties();
			whereClause.setProperty("barcode",
					persistentState.getProperty("barcode"));
			deletePersistentState(mySchema, whereClause);
			updateStatusMessage = "Tree with barcode : "
					+ persistentState.getProperty("barcode")
					+ " deleted successfully from database!";
			System.out.println(updateStatusMessage);

		} catch (SQLException ex) {
			updateStatusMessage = "Error in deleting tree from database!";
		}
	}

	// ------------------------------------------------------
	public String toString() {
		return persistentState.toString();
	}

	// ----------------------------------------------------------
	public Vector<String> getEntryListView() {
		Vector<String> v = new Vector<String>();

		v.addElement(persistentState.getProperty("barcode"));
		v.addElement(persistentState.getProperty("treeType"));
		v.addElement(persistentState.getProperty("notes"));
		v.addElement(persistentState.getProperty("status"));
		v.addElement(persistentState.getProperty("dateStatusUpdated"));

		return v;
	}

}
