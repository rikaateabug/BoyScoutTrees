package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import transactions.TreeTypeTransaction;
import views.TreeTypeCollectionView;

public class Tester {

	public static void main(String [] args) {
		

		/**
		Properties props = new Properties();
		props.setProperty("firstName", "Billy");
		props.setProperty("middleName", "James");
		props.setProperty("lastName", "Henderson");
		props.setProperty("birthDate", "2000-22-22");
		props.setProperty("phoneNumber", "1-585-654-7584");
		props.setProperty("troopID", "0056");
		props.setProperty("email", "email@email");
		props.setProperty("status", "Active");
		

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		props.setProperty("dateStatusUpdated", df.format(date));
		
		
		Scout s = new Scout(props);
		s.updateStateInDatabase();
		
		
		ScoutCollection sc = new ScoutCollection();
		sc.findScoutsWithNameLike("billy", "henderson");
		sc.display();
		*/
		/**
		String s = "11.";
		String regex = "(\\d+)(\\.\\d{2})?";
		if ((s.matches("(\\d)+"))) {
			System.out.println(s + " matches");
		}
		else
			System.out.println(s + " does not match our regex");
	}
	*/
		Locale myLocale = new Locale("en", "US");
		TreeTypeTransaction trans;
		try {
			trans = new TreeTypeTransaction("UpdateTreeType", myLocale);
			TreeTypeCollectionView newView = new TreeTypeCollectionView(trans);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
