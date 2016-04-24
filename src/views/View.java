//tabs=4
//************************************************************
//	COPYRIGHT 2009/2015 Sandeep Mitra and Michael Steves, The
// College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
//This file is the product of The College at Brockport and cannot 
//be reproduced, copied, or used in any shape or form without 
//the express written consent of The College at Brockport.
//************************************************************
//
//specify the package
package views;

//system imports
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.EventObject;
import java.util.Locale;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
//project imports
import common.StringList;
import impresario.IView;
import impresario.IModel;
import impresario.IControl;
import impresario.ControlRegistry;

//==============================================================
public abstract class View extends Group
	implements IView, IControl
{
	// private data
	protected IModel myModel;
	protected ControlRegistry myRegistry;
	protected ResourceBundle myResourceBundle;
	
	// GUI components
	
		
	// Class constructor
	//----------------------------------------------------------
	public View(IModel model, String classname)
	{
		
		myModel = model;
		myRegistry = new ControlRegistry(classname);

		if (!(classname.equals("WelcomeView")))	//WelcomeView determines the locale, Can't call before
			setResourceBundle(classname);
	}
	
	
	//----------------------------------------------------------
	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}
	
	//----------------------------------------------------------
	public void setResourceBundle(String classname)
	{
		Locale myLocale = (Locale) myModel.getState("Locale");
		try {
			myResourceBundle = ResourceBundle.getBundle("locale/" + classname ,myLocale);
		}
		catch (Exception e) {
			System.out.println("Error: ResourceBundle " + classname + " not found");
		}
	}
	
	// Allow models to register for state updates
	//----------------------------------------------------------
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}
		
		
	// Allow models to unregister for state updates
	//----------------------------------------------------------
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}
	
	
}
