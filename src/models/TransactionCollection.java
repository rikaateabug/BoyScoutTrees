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

public class TransactionCollection extends EntityBase implements IView {

	private static final String myTableName = "Transaction";

	private Vector<Sale> sales;
	protected SessionTransaction mySessionTrans;
	private Locale myLocale;
	
	// ------------------------------------------------------------------
	public TransactionCollection(IModel trans) {
		super(myTableName);
		myStage = MainStageContainer.getInstance();
		sales = new Vector<Sale>();
		mySessionTrans = (SessionTransaction) trans;
		myLocale = (Locale) mySessionTrans.getState("Locale");

		persistentState = new Properties();
	}

	// ------------------------------------------------------------------
	public TransactionCollection() {
		super(myTableName);
		sales = new Vector<Sale>();
		persistentState = new Properties();
	}
	
	// ------------------------------------------------------------------
	public void findAllTransactionsFromSession(String sessionID) {


		String query = "SELECT * FROM " + myTableName + " WHERE (sessionID="+ sessionID + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if (allDataRetrieved != null) {
			for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++) {
				Properties nextAccountData = (Properties) allDataRetrieved.elementAt(cnt);

				Sale sale = new Sale(nextAccountData);

				if (sale != null) {
					addSale(sale);
				}
			}

		}
	}
	
	// ------------------------------------------------------------------
	public int getTotalCheckTransactions() {
		int checks = 0;
		for (Sale s : sales) {
			if (s.getState("paymentMethod").equals("Check")) {
				checks++;
			}
		}
		return checks;
	}
	
	// ------------------------------------------------------------------
	public double getEndingCash() {
		double total = 0;
		String str;
		for (Sale s : sales) {
			str = (String)s.getState("transactionAmount");
			System.out.println("str is: " + str);
			total += Double.valueOf(str);
		}
		
		return total;
	}

	// ------------------------------------------------------------------
	public Object getState(String key) {
		if (key.equals("Scouts"))
			return sales;
		else if (key.equals("ScoutList"))
			return this;

		if (key.equals("Locale")) {
			return myLocale;
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
		return sales.size();
	}
	
	// ------------------------------------------------------------------
	public Sale get(int pos) {
		return sales.get(pos);
	}

	// ------------------------------------------------------------------
	public void stateChangeRequest(String key, Object value) {

		if (key.equals("CancelSession")) {
			mySessionTrans.stateChangeRequest("CancelAddScout", null);
		}
		
		if (key.equals("ShowScoutShiftView")) {
			mySessionTrans.stateChangeRequest("ShowScoutShiftView", value);
		}
		
		myRegistry.updateSubscribers(key, this);

	}

	// ------------------------------------------------------------------
	private void addSale(Sale s) {
		sales.add(s);
	}

	// ------------------------------------------------------------------
	public void updateState(String key, Object value) {
		stateChangeRequest(key, value);
	}

	// ------------------------------------------------------------------
	public void display() {
		for (int i = 0; i < sales.size(); i++) {
			System.out.println(sales.get(i).toString());
		}
	}
}
