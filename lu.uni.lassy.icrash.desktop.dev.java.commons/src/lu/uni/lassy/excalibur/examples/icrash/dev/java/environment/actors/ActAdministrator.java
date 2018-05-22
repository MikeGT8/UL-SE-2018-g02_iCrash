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
package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtDescription;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtGPSLocation;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPIID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtRequestID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

/**
 * The Interface ActAdministrator that allows RMI access to administrator functions.
 */
public interface ActAdministrator extends ActAuthenticated {

	/**
	 * Add a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when creating the coordinator
	 * @param aDtLogin The username to use when creating the coordinator
	 * @param aDtPassword The password to use when creating the coordinator
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	public PtBoolean oeAddCoordinator(DtCoordinatorID aDtCoordinatorID,
			DtLogin aDtLogin, DtPassword aDtPassword, EtCrisisType aAccessRights) throws RemoteException,
			NotBoundException;

	/**
	 * Delete a coordinator to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator to delete
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeDeleteCoordinator(DtCoordinatorID aDtCoordinatorID)
			throws RemoteException, NotBoundException;
	
	/**
	 * Update a coordinators access rights to the system, using the parameters passed.
	 *
	 * @param aDtCoordinatorID The ID to use when looking for the coordinator to update
	 * @param accessRights The new access rights
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */

	public PtBoolean oeUpdateCoordinatorAccessRights(DtCoordinatorID aDtCoordinatorID, EtCrisisType accessRights)
			throws RemoteException, NotBoundException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was created .
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorAdded() throws RemoteException;

	/**
	 * A message sent to the listening actor saying the coordinator was deleted.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorDeleted() throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorUpdated() throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the coordinator was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	public PtBoolean ieCoordinatorAccessRightsUpdated() throws RemoteException;
	
	/* PI variant */
	
	/**
	 * Add a PI to the system, using the parameters passed.
	 *
	 * @param aPIID The ID to use when creating the PI
	 * @param aName The PI name to use when creating the PI
	 * @param aCity The city to use when creating the PI
	 * @param aGPSLocation The gps location to use when creating the PI
	 * @param aDescription The description to use when creating the PI
	 * @param aCategory The category to use when creating the PI
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeAddPI(DtID aPIID, DtName aPIName, 
			DtCity aPICity, DtGPSLocation aGPSLocation, 
			DtDescription aPIDescription, EtCategory aPICategory) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Update a PI to the system, using the parameters passed.
	 *
	 * @param aPIID The ID to use when updating the PI
	 * @param aName The PI name to use when updating the PI
	 * @param aCity The city to use when updating the PI
	 * @param aGPSLocation The gps location to use when updating the PI
	 * @param aDescription The description to use when updating the PI
	 * @param aCategory The category to use when updating the PI
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeUpdatePI(DtID aPIID, DtName aPIName, 
			DtCity aPICity, DtGPSLocation aGPSLocation, 
			DtDescription aPIDescription, EtCategory aPICategory) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Delete a PI to the system, using the parameters passed.
	 *
	 * @param aPIID The ID to use when looking for the PI to delete
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeDeletePI(DtID aPIID) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Get all pending requests from the system.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeGetAllRequestsFromCoordinator() 
			throws RemoteException, NotBoundException;
	
	/**
	 * Treat a specific request from the system.
	 *
	 * @param aRequestID The ID to use when looking for the Request to treat
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeTreatRequest(DtID aRequestID) 
			throws RemoteException, NotBoundException;
	
	/**
	 * Solve a specific request from the system.
	 *
	 * @param aRequestID The ID to use when looking for the Request to solve
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound correctly in RMI settings
	 */
	
	public PtBoolean oeSolveRequest(DtID aRequestID) 
			throws RemoteException, NotBoundException;
	
	/**
	 * A list of requests sent to the listening actor saying the requests need to be treated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean ieRequestList() 
			throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the request was treated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean ieRequestBeingTreated() 
			throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the request was solved.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean ieRequestSolved() 
			throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the PI was added.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean iePIAdded() 
			throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the PI was updated.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean iePIUpToDate() 
			throws RemoteException;
	
	/**
	 * A message sent to the listening actor saying the PI was deleted.
	 *
	 * @return The success of the method
	 * @throws RemoteException Thrown if the server is offline
	 */
	
	public PtBoolean iePIDeleted() 
			throws RemoteException;

}
