package transactions;

import java.util.Locale;

import impresario.IModel;

public class TransactionFactory {

	public static Transaction createTransaction(String transType, Locale myLocale) throws Exception {

		Transaction trans = null;

		if ((transType.equals("AddScout")) || (transType.equals("UpdateScout")) || (transType.equals("RemoveScout"))) {
			trans = new ScoutTransaction(transType, myLocale);
		}

		else if ((transType.equals("AddTree")) || (transType.equals("UpdateTree"))
				|| (transType.equals("RemoveTree"))) {
			 trans = new TreeTransaction(transType, myLocale);
		}

		else if ((transType.equals("AddTreeType")) || (transType.equals("UpdateTreeType"))) {
			trans = new TreeTypeTransaction(transType, myLocale);
		}
		
		return trans;
	}
}
