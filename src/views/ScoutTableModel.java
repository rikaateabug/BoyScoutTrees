package views;

import java.util.Vector;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ScoutTableModel {

	// ToggleGroup group = new ToggleGroup();
	// RadioButton myButton = new RadioButton("");

	// private final SimpleObjectProperty button1;
	private final SimpleStringProperty scoutID;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty middleName;
	private final SimpleStringProperty birthDate;
	private final SimpleStringProperty phoneNumber;
	private final SimpleStringProperty email;
	private final SimpleStringProperty troopID;
	private final SimpleStringProperty status;
	private final SimpleStringProperty dateStatusUpdated;

	// ----------------------------------------------------------------------------
	public ScoutTableModel(Vector<String> scoutData) {
		scoutID = new SimpleStringProperty(scoutData.elementAt(0));
		lastName = new SimpleStringProperty(scoutData.elementAt(1));
		firstName = new SimpleStringProperty(scoutData.elementAt(2));
		middleName = new SimpleStringProperty(scoutData.elementAt(3));
		birthDate = new SimpleStringProperty(scoutData.elementAt(4));
		phoneNumber = new SimpleStringProperty(scoutData.elementAt(5));
		email = new SimpleStringProperty(scoutData.elementAt(6));
		troopID = new SimpleStringProperty(scoutData.elementAt(7));
		status = new SimpleStringProperty(scoutData.elementAt(8));
		dateStatusUpdated = new SimpleStringProperty(scoutData.elementAt(9));
	}
	
	// ----------------------------------------------------------------------------
	public String getScoutID() {
		return scoutID.get();
	}

	// ----------------------------------------------------------------------------
	public void setScoutID(String s) {
		scoutID.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getLastName() {
		return lastName.get();
	}

	// ----------------------------------------------------------------------------
	public void setLastName(String s) {
		lastName.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getFirstName() {
		return firstName.get();
	}

	// ----------------------------------------------------------------------------
	public void setFirstName(String s) {
		firstName.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getMiddleName() {
		return middleName.get();
	}

	// ----------------------------------------------------------------------------
	public void setMiddleName(String s) {
		middleName.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getBirthDate() {
		return birthDate.get();
	}

	// ----------------------------------------------------------------------------
	public void setBirthDate(String s) {
		birthDate.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	// ----------------------------------------------------------------------------
	public void setPhoneNumber(String s) {
		phoneNumber.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getEmail() {
		return email.get();
	}

	// ----------------------------------------------------------------------------
	public void setEmail(String s) {
		email.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getTroopID() {
		return troopID.get();
	}

	// ----------------------------------------------------------------------------
	public void setTroopID(String s) {
		troopID.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getStatus() {
		return status.get();
	}

	// ----------------------------------------------------------------------------
	public void setStatus(String s) {
		status.set(s);
	}

	// ----------------------------------------------------------------------------
	public String getDateStatusUpdated() {
		return dateStatusUpdated.get();
	}

	// ----------------------------------------------------------------------------
	public void setDateStatusUpdated(String s) {
		dateStatusUpdated.set(s);
	}
}
