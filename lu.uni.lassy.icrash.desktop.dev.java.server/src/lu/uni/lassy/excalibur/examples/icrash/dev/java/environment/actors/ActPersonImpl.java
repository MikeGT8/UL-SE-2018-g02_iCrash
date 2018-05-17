package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class ActPersonImpl extends ActAuthenticatedImpl implements ActPerson {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new server side actor of type person.
	 *
	 * @param The username of the actor, this is of type DtLogin. It is used in identifying the correct actor when working with their Class type (CtPerson)
	 * @throws RemoteException Thrown if the server is offline
	 */
	public ActPersonImpl(DtLogin n) throws RemoteException {
		
		super(n);
	}
	
	@Override
	public PtBoolean oeSearchPI(DtName aPIName, EtCategory aPICategory, DtCity aPICity)
			throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSearchPI sent to system");
		PtBoolean res = iCrashSys_Server.oeSearchPI(aPIName, aPICategory, aPICity);
			
		if(res.getValue() == true)
			log.info("operation oeSearchPI successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeSendNewRequest(DtName aPIName, EtCategory aPICategory, DtCity aPICity)
			throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeSendNewRequest sent to system");
		PtBoolean res = iCrashSys_Server.oeSendNewRequest(aPIName, aPICategory, aPICity);
			
		if(res.getValue() == true)
			log.info("operation oeSendNewRequest successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeGetGPSLocation(DtID aPIID) throws RemoteException, NotBoundException {
		
		Logger log = Log4JUtils.getInstance().getLogger();
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeGetGPSLocation sent to system");
		PtBoolean res = iCrashSys_Server.oeGetGPSLocation(aPIID);
			
		if(res.getValue() == true)
			log.info("operation oeGetGPSLocation successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean oeGetPIDescription(DtID aPIID) throws RemoteException, NotBoundException {

		Logger log = Log4JUtils.getInstance().getLogger();
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
			 	
		//Gathering the remote object as it was published into the registry
	    IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		
		//set up ActAuthenticated instance that performs the request
		iCrashSys_Server.setCurrentRequestingAuthenticatedActor(this);

		log.info("message ActCoordinator.oeGetPIDescription sent to system");
		PtBoolean res = iCrashSys_Server.oeGetPIDescription(aPIID);
			
		if(res.getValue() == true)
			log.info("operation oeGetPIDescription successfully executed by the system");

		return res;
	}

	@Override
	public PtBoolean iePIAdded() throws RemoteException {
		
		for (Iterator<ActProxyAuthenticated> iterator = listeners.iterator(); iterator.hasNext();) {
			
			ActProxyAuthenticated aProxy = iterator.next();
			
			try {
				if (aProxy instanceof ActProxyPerson)
					((ActProxyPerson) aProxy).iePIAdded();
				
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
