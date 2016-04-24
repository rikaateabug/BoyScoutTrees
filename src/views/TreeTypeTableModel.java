package views;

import java.util.Vector;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class TreeTypeTableModel {

	private final SimpleStringProperty treeTypeID;
	private final SimpleStringProperty typeDescription;
	private final SimpleStringProperty cost;
	private final SimpleStringProperty barcodePrefix;
	
	// ----------------------------------------------------------------------------
	public TreeTypeTableModel(Vector<String> treeTypeData) {
		treeTypeID = new SimpleStringProperty(treeTypeData.elementAt(0));
		typeDescription = new SimpleStringProperty(treeTypeData.elementAt(1));
		cost = new SimpleStringProperty(treeTypeData.elementAt(2));
		barcodePrefix = new SimpleStringProperty(treeTypeData.elementAt(3));
	}

	// ----------------------------------------------------------------------------
	public String getTreeTypeID() {
		return treeTypeID.get();
	}

	// ----------------------------------------------------------------------------
	public void setTreeTypeID(String s) {
		treeTypeID.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getTypeDescription() {
		return typeDescription.get();
	}

	// ----------------------------------------------------------------------------
	public void setTypeDescription(String s) {
		typeDescription.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getCost() {
		return cost.get();
	}

	// ----------------------------------------------------------------------------
	public void setCost(String s) {
		cost.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getBarcodePrefix() {
		return barcodePrefix.get();
	}

	// ----------------------------------------------------------------------------
	public void setBarcodePrefix(String s) {
		barcodePrefix.set(s);
	}
}
