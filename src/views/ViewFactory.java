package views;

import impresario.IModel;
import views.View;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if (viewName.equals("WelcomeView") == true)
		{
			return new WelcomeView(model);
		}
		
		else if (viewName.equals("TreeLotCoordinatorView") == true)
		{
			return new TreeLotCoordinatorView(model);
		}
		
		else if (viewName.equals("AddScoutView") == true)
		{
			return new AddScoutView(model);
		}
		
		else if (viewName.equals("UpdateScoutView") == true)
		{
			return new UpdateScoutView(model);
		}
		
		else if (viewName.equals("SearchScoutNameView") == true)
		{
			return new SearchScoutNameView(model);
		}
		
		else if (viewName.equals("DeleteScoutView") == true)
		{
			return new DeleteScoutView(model);
		}
		else if (viewName.equals("SearchTreeBarcodeView") == true)
		{
			return new SearchTreeBarcodeView(model);
		}
		
		else if (viewName.equals("AddTreeView") == true)
		{
			return new AddTreeView(model);
		}
		
		else if (viewName.equals("UpdateTreeView") == true)
		{
			return new UpdateTreeView(model);
		}
		
		else if (viewName.equals("DeleteTreeView") == true)
		{
			return new DeleteTreeView(model);
		}
		/////////////////////////////////////////////////////////////////////////
		else if (viewName.equals("AddTreeTypeView") == true)
		{
			return new AddTreeTypeView(model);
		}
		
		else if (viewName.equals("UpdateTreeTypeView") == true)
		{
			return new UpdateTreeTypeView(model);
		}
		
		else if (viewName.equals("TreeTypeCollectionView") == true)
		{
			return new TreeTypeCollectionView(model);
		}
		
		else
			return null;
	}
}
