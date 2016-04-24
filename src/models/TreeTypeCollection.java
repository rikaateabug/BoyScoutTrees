package models;

import java.awt.Event;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;

import database.*;
import exception.InvalidPrimaryKeyException;
import impresario.IModel;
import impresario.IView;
import transactions.ScoutTransaction;
import transactions.TreeTypeTransaction;
import userinterface.MainStageContainer;
import views.ScoutCollectionView;
import views.View;
import views.ViewFactory;
import javafx.*;
import javafx.scene.Scene;
import model.EntityBase;

import java.sql.SQLException;
import java.util.Properties;

import model.EntityBase;

public class TreeTypeCollection extends EntityBase implements IView {

	private static final String myTableName = "TreeType";

	private Vector<TreeType> treeTypes;
	protected TreeTypeTransaction myTrans;

	// ------------------------------------------------------------------
	public TreeTypeCollection() {
		super(myTableName);

		treeTypes = new Vector<TreeType>();
	}

	// ------------------------------------------------------------------
	public TreeTypeCollection(IModel trans) {
		super(myTableName);
		//myStage = MainStageContainer.getInstance();
		treeTypes = new Vector<TreeType>();
		myTrans = (TreeTypeTransaction) trans;
		persistentState = new Properties();
	}

	// ------------------------------------------------------------------
	public void findAllTreeTypes() {

		String query = "SELECT * FROM " + myTableName;

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null) {

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved
						.elementAt(cnt);
				TreeType treeType = new TreeType(nextAccountData);
				if (treeType != null) {
					addTreeType(treeType);
				}
			}

		}
	}
	
	// ------------------------------------------------------------------
	public void findTreeTypesWithBarcodePrefix(String barcodePrefix) {

		String query = "SELECT * FROM " + myTableName + " WHERE (barcodePrefix=" + barcodePrefix + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null) {

			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved
						.elementAt(cnt);

				TreeType treeType = new TreeType(nextAccountData);

				if (treeType != null) {
					addTreeType(treeType);
				}
			}

		}
	}
	
	// ------------------------------------------------------------------
	public Object getState(String key) {
		if (key.equals("TreeTypes"))
			return treeTypes;
		else if (key.equals("TreeTypeList"))
			return this;
		
		if (key.equals("Locale")) {
			return myTrans.getState("Locale");
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
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("showView")) {
			if (myTrans.getState("transType").equals("RemoveScout")) {
				myTrans.stateChangeRequest("showDeleteView", value);
			}
			if (myTrans.getState("transType").equals("UpdateScout")) {
				myTrans.stateChangeRequest("showUpdateView", value);
			}
		}
		if (key.equals("CancelTrans")) {
			myTrans.stateChangeRequest("CancelTrans", null);
		}
		
		myRegistry.updateSubscribers(key, this);

	}

	// ------------------------------------------------------------------
	private void addTreeType(TreeType t) {
		treeTypes.add(t);
	}
	
	// ------------------------------------------------------------------
	public boolean isEmpty() {
		
		if (treeTypes.size() != 0) {
			return false;
		}
		return true;
	}

	// ------------------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}

	// ------------------------------------------------------------------
	public void display() {
		for (int i = 0; i < treeTypes.size(); i++) {
			System.out.println(treeTypes.get(i).toString());
		}
	}
}
