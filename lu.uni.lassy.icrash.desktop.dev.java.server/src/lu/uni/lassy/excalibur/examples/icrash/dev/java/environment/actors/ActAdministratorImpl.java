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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
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
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

import org.apache.log4j.Logger;
/**
 * The Class ActAdministratorImpl, which is a server side actor for the user Administrator.
 */
public class ActAdministratorImpl extends ActAuthenticatedImpl implements ActAdministrator {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor of type administrator.
	 *
	 * @param n The username of the Administrator associated with this actor. This helps linking class types and actors together
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActAdministratorImpl(DtLogin n) throws RemoteException {
		super(n);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#getName()
	 */
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#oeAddCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword)
	 */
	synchronized public PtBoolean oeAddCoordinator(
			DtCoordinatorID aDtCoordinatorID, DtLogin aDtLogin,
			DtPassword aDtPassword, EtCrisisType aAccessRights) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeAddCoordinator sent to system");
		PtBoolean res = iCrashSys_Server.oeAddCoordinator(aDtCoordinatorID,
				aDtLogin, aDtPassword, aAccessRights);

		if (res.getValue() == true)
			log.info("operation oeAddCoordinator successfully executed by the system");

		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#oeDeleteCoordinator(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID)
	 */
	synchronized public PtBoolean oeDeleteCoordinator(
			DtCoordinatorID aDtCoordinatorID) throws RemoteException,
			NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeDeleteCoordinator sent to system");
		PtBoolean res = iCrashSys_Server.oeDeleteCoordinator(aDtCoordinatorID);

		if (res.getValue() == true)
			log.info("operation oeDeleteCoordinator successfully executed by the system");

		return res;
		
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#oeUpdateCoordinatorAccessRights(lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID, lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisType)
	 */
	@Override
	public PtBoolean oeUpdateCoordinatorAccessRights(DtCoordinatorID aDtCoordinatorID, EtCrisisType accessRights)
			throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeUpdateCoordinatorAccessRights sent to system");
		PtBoolean res = iCrashSys_Server.oeUpdateCoordinatorAccessRights(aDtCoordinatorID, accessRights);

		if (res.getValue() == true)
			log.info("operation oeUpdateCoordinatorAccessRights successfully executed by the system");

		return res;
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorAdded()
	 */
	public PtBoolean ieCoordinatorAdded() {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorAdded();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorDeleted()
	 */
	public PtBoolean ieCoordinatorDeleted() {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorDeleted();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorUpdated()
	 */
	@Override
	public PtBoolean ieCoordinatorUpdated() throws RemoteException {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorUpdated();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator#ieCoordinatorAccessRightsUpdated()
	 */
	@Override
	public PtBoolean ieCoordinatorAccessRightsUpdated() throws RemoteException {
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator
				.hasNext();) {
			ActProxyAuthenticated aProxy = iterator.next();
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieCoordinatorAccessRightsUpdated();
			} catch (RemoteException e) {
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		return new PtBoolean(true);
	}
	
	/* PI variant */

	@Override
	public PtBoolean oeAddPI(DtID aPIID, DtName aPIName, DtCity aPICity, DtGPSLocation aGPSLocation,
			DtDescription aPIDescription, EtCategory aPICategory) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeAddPI sent to system");
		PtBoolean res = iCrashSys_Server.oeAddPI(aPIID, aPIName, aPICity, aGPSLocation, aPIDescription, aPICategory);

		if (res.getValue() == true)
			
			log.info("operation oeAddPI successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeUpdatePI(DtID aPIID, DtName aPIName, DtCity aPICity, DtGPSLocation aGPSLocation,
			DtDescription aPIDescription, EtCategory aPICategory) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeUpdatePI sent to system");
		PtBoolean res = iCrashSys_Server.oeUpdatePI(aPIID, aPIName, aPICity, aGPSLocation, aPIDescription, aPICategory);

		if (res.getValue() == true)
			
			log.info("operation oeUpdatePI successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeDeletePI(DtID aPIID) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeDeletePI sent to system");
		PtBoolean res = iCrashSys_Server.oeDeletePI(aPIID);

		if (res.getValue() == true)
			
			log.info("operation oeDeletePI successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeGetAllRequestsFromCoordinator() throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeGetAllRequestsFromCoordinator sent to system");
		PtBoolean res = iCrashSys_Server.oeGetAllRequestsFromCoordinator();

		if (res.getValue() == true)
			
			log.info("operation oeGetAllRequestsFromCoordinator successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeTreatRequest(DtID aRequestID) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeTreatRequest sent to system");
		PtBoolean res = iCrashSys_Server.oeTreatRequest(aRequestID);

		if (res.getValue() == true)
			
			log.info("operation oeTreatRequest successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeSolveRequest(DtID aRequestID) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();

		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry.lookup("iCrashServer");

		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActAdministrator.oeSolveRequest sent to system");
		PtBoolean res = iCrashSys_Server.oeSolveRequest(aRequestID);

		if (res.getValue() == true)
			
			log.info("operation oeSolveRequest successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean ieRequestList() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieRequestList();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean ieRequestBeingTreated() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieRequestBeingTreated();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean ieRequestSolved() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).ieRequestSolved();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean iePIAdded() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).iePIAdded();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean iePIUpToDate() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).iePIUpToDate();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean iePIDeleted() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyAdministrator)
					((ActProxyAdministrator) aProxy).iePIDeleted();
				
			} catch (RemoteException e) {
				
				Log4JUtils.getInstance().getLogger().error(e);
				iterator.remove();
			}
		}
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean oeResetPassword(DtLogin aDtLogin) throws RemoteException, NotBoundException {
		// Reset PW of Administrator wont work
		System.out.println("Cannot reset password for the administrator. Please access database and change it yourself");
		return new PtBoolean(false);
	}
}
