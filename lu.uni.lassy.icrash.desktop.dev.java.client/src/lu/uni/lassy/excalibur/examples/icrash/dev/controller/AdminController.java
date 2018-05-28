/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Hashtable;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtAlertID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtDescription;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLatitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLongitude;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtReal;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyAdministratorImpl;

/**
 * The Class AdminController, used to do functions that an admin can only do.
 */
public class AdminController extends AbstractUserController {
	
	/**
	 * Instantiates a new admin controller.
	 *
	 * @param aActAdmin the a act admin
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI properties
	 */
	public AdminController(ActAdministrator aActAdmin) throws RemoteException, NotBoundException{	
		super(new ActProxyAdministratorImpl(aActAdmin));
	}
	/**
	 * If an administrator is logged in, will send an addCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to create, the user specifies the ID, not the system in the creation process
	 * @param login The logon of the user to create. This is the username they will use at point of logon at the client front end
	 * @param password The password of the user to create. This is the password they will use at point of logon at the client front end
	 * @return Returns a PtBoolean true if the user was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeAddCoordinator(String coordinatorID, String login, String password, EtCrisisType accessRights) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			DtLogin aDtLogin = new DtLogin(new PtString(login));
			DtPassword aDtPassword = new DtPassword(new PtString(password));
			EtCrisisType aAccessRights = accessRights;
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			ht.put(aDtLogin, aDtLogin.value.getValue());
			ht.put(aDtPassword, aDtPassword.value.getValue());
			ht.put(aAccessRights, aAccessRights.toString());
			try {
				return actorAdmin.oeAddCoordinator(aDtCoordinatorID, aDtLogin, aDtPassword, aAccessRights);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	/**
	 * If an administrator is logged in, will send a deleteCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to delete
	 * @return Returns a PtBoolean true if the user was deleted, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeDeleteCoordinator(String coordinatorID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			if (!aDtCoordinatorID.is().getValue())
				return new PtBoolean(false);
			try {
				return actorAdmin.oeDeleteCoordinator(aDtCoordinatorID);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a updateCoordinatorAccessRights request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to update
	 * @return Returns a PtBoolean true if the user was deleted, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeUpdateCoordinatorAccessRights(String coordinatorID, EtCrisisType accessRights) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		if (getUserType() == UserType.Admin){
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			DtCoordinatorID aDtCoordinatorID = new DtCoordinatorID(new PtString(coordinatorID));
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aDtCoordinatorID, aDtCoordinatorID.value.getValue());
			if (!aDtCoordinatorID.is().getValue())
				return new PtBoolean(false);
			try {
				return actorAdmin.oeUpdateCoordinatorAccessRights(aDtCoordinatorID, accessRights);
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
			} catch (NotBoundException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		return new PtBoolean(false);
	}
	
	/* PI variant */
	
	/**
	 * If an administrator is logged in, will send an AddPI request to the server. If successful, it will return a PtBoolean of true
	 * @param PIID The ID of the PI to create, the user specifies the ID, not the system in the creation process
	 * @param name The name of the PI to create.
	 * @param city The city of the PI to create.
	 * @param latitude The latitude of the PI to create.
	 * @param longitude The longitude of the PI to create.
	 * @param description The description of the PI to create.
	 * @param category The category of the PI to create.
	 * @return Returns a PtBoolean true if the PI was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeAddPI(String PIID, String name, String city, String latitude, String longitude, String description, EtCategory category) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			
			DtID aPIID = new DtID(new PtString(PIID));
			DtName aPIName = new DtName(new PtString(name));
			DtCity aPICity = new DtCity(new PtString(city));
			
			double aPILatitude = Double.parseDouble(latitude);
			double aPILongitude = Double.parseDouble(longitude);
			DtGPSLocation aPIGPSLocation = new DtGPSLocation(new DtLatitude(new PtReal(aPILatitude)), new DtLongitude(new PtReal(aPILongitude)));
			
			DtDescription aPIDescription = new DtDescription(new PtString(description));
			EtCategory aPICategory = category;
			
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aPIID, aPIID.value.getValue());
			ht.put(aPIName, aPIName.value.getValue());
			ht.put(aPICity, aPICity.value.getValue());
			ht.put(aPIGPSLocation.latitude, Double.toString(aPIGPSLocation.latitude.value.getValue()));
			ht.put(aPIGPSLocation.longitude, Double.toString(aPIGPSLocation.longitude.value.getValue()));
			ht.put(aPIDescription, aPIDescription.value.getValue());
			ht.put(aPICategory, aPICategory.toString());
			
			try {
				return actorAdmin.oeAddPI(aPIID, aPIName, aPICity, aPIGPSLocation, aPIDescription, aPICategory);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send an UpdatePI request to the server. If successful, it will return a PtBoolean of true
	 * @param PIID The ID of the PI to update, the user specifies the ID, not the system in the update process
	 * @param name The name of the PI to update.
	 * @param city The city of the PI to update.
	 * @param latitude The latitude of the PI to update.
	 * @param longitude The longitude of the PI to update.
	 * @param description The description of the PI to update.
	 * @param category The category of the PI to update.
	 * @return Returns a PtBoolean true if the PI was created, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeUpdatePI(String PIID, String name, String city, String latitude, String longitude, String description, EtCategory category) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			
			DtID aPIID = new DtID(new PtString(PIID));
			DtName aPIName = new DtName(new PtString(name));
			DtCity aPICity = new DtCity(new PtString(city));
			
			double aPILatitude = Double.parseDouble(latitude);
			double aPILongitude = Double.parseDouble(longitude);
			DtGPSLocation aPIGPSLocation = new DtGPSLocation(new DtLatitude(new PtReal(aPILatitude)), new DtLongitude(new PtReal(aPILongitude)));
			
			DtDescription aPIDescription = new DtDescription(new PtString(description));
			EtCategory aPICategory = category;
			
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aPIID, aPIID.value.getValue());
			ht.put(aPIName, aPIName.value.getValue());
			ht.put(aPICity, aPICity.value.getValue());
			ht.put(aPIGPSLocation.latitude, Double.toString(aPIGPSLocation.latitude.value.getValue()));
			ht.put(aPIGPSLocation.longitude, Double.toString(aPIGPSLocation.longitude.value.getValue()));
			ht.put(aPIDescription, aPIDescription.value.getValue());
			ht.put(aPICategory, aPICategory.toString());
			
			if (!aPIID.is().getValue())
				
				return new PtBoolean(false);
			
			try {
				return actorAdmin.oeUpdatePI(aPIID, aPIName, aPICity, aPIGPSLocation, aPIDescription, aPICategory);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a DeleteCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @param coordinatorID The ID of the coordinator to delete
	 * @return Returns a PtBoolean true if the user was deleted, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	public PtBoolean oeDeletePI(String PIID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			
			DtID aPIID = new DtID(new PtString(PIID));
			
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aPIID, aPIID.value.getValue());
			
			if (!aPIID.is().getValue())
				return new PtBoolean(false);
			
			try {
				return actorAdmin.oeDeletePI(aPIID);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a GetAllRequestsFromCoordinator request to the server. If successful, it will return a PtBoolean of true
	 * @return Returns a PtBoolean true if the administrator got a list of requests, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeGetAllRequestsFromCoordinator() throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
			
			try {
				return actorAdmin.oeGetAllRequestsFromCoordinator();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a TreatRequest request to the server. If successful, it will return a PtBoolean of true
	 * @param requestID The ID of the request to treat, the user specifies the ID, not the system in the treat process
	 * @return Returns a PtBoolean true if the request was treated, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeTreatRequest(String requestID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (this.getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
		
			DtID aRequestID = new DtID(new PtString(requestID));
			
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aRequestID, aRequestID.value.getValue());
			
			try {
				return actorAdmin.oeTreatRequest(aRequestID);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
	
	/**
	 * If an administrator is logged in, will send a SolveRequest request to the server. If successful, it will return a PtBoolean of true
	 * @param requestID The ID of the request to solve, the user specifies the ID, not the system in the treat process
	 * @return Returns a PtBoolean true if the request was solved, otherwise will return false
	 * @throws ServerOfflineException is an error that is thrown when the server is offline or not reachable
	 * @throws ServerNotBoundException is only thrown when attempting to access a server which has no current binding. This shouldn't happen, but you never know!
	 * @throws IncorrectFormatException is thrown when a Dt/Et information type does not match the is() method specified in the specification
	 */
	
	public PtBoolean oeSolveRequest(String requestID) throws ServerOfflineException, ServerNotBoundException, IncorrectFormatException{
		
		if (this.getUserType() == UserType.Admin) {
			
			ActProxyAdministratorImpl actorAdmin = (ActProxyAdministratorImpl)getAuth();
		
			DtID aRequestID = new DtID(new PtString(requestID));
			
			Hashtable<JIntIs, String> ht = new Hashtable<JIntIs, String>();
			ht.put(aRequestID, aRequestID.value.getValue());
			
			try {
				return actorAdmin.oeSolveRequest(aRequestID);
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerOfflineException();
				
			} catch (NotBoundException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				throw new ServerNotBoundException();
			}
		}
		
		return new PtBoolean(false);
	}
}
