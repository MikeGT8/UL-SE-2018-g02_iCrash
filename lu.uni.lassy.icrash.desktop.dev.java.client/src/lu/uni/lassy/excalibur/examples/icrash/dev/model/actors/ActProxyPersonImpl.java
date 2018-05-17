package lu.uni.lassy.excalibur.examples.icrash.dev.model.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCity;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtName;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message.MessageType;

public class ActProxyPersonImpl extends ActProxyAuthenticatedImpl implements ActProxyPerson {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new client side actor for persons.
	 *
	 * @param user The server side actor to be associated with
	 * @throws RemoteException Thrown if the server is offline
	 * @throws NotBoundException Thrown if the server has not been bound in the RMI settings
	 */
	public ActProxyPersonImpl(ActPerson user) throws RemoteException, NotBoundException {
		
		super(user);
	}
	
	synchronized public PtBoolean oeSearchPI(DtName aPIName, EtCategory aPICategory, DtCity aPICity) throws RemoteException, NotBoundException {
		
		if(getServerSideActor() !=null)
			
			return ((ActPerson) getServerSideActor()).oeSearchPI(aPIName, aPICategory, aPICity);
		else
			
			return new PtBoolean(false);
	}

	synchronized public PtBoolean oeSendNewRequest(DtName aPIName, EtCategory aPICategory, DtCity aPICity) throws RemoteException, NotBoundException {
	
	if(getServerSideActor() !=null)
		
		return ((ActPerson) getServerSideActor()).oeSendNewRequest(aPIName, aPICategory, aPICity);
	else
		
		return new PtBoolean(false);
	}
	
	synchronized public PtBoolean oeGetGPSLocation(DtID aPIID) throws RemoteException, NotBoundException {
		
		if(getServerSideActor() !=null)
			
			return ((ActPerson) getServerSideActor()).oeGetGPSLocation(aPIID);
		else
			
			return new PtBoolean(false);
	}
	
	synchronized public PtBoolean oeGetPIDescription(DtID aPIID) throws RemoteException, NotBoundException {
		
		if(getServerSideActor() !=null)
			
			return ((ActPerson) getServerSideActor()).oeGetPIDescription(aPIID);
		else
			
			return new PtBoolean(false);
	}
	
	public PtBoolean iePIAdded() {
		
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("message ActPerson.iePIAdded received from system");
		listOfMessages.add(new Message(MessageType.iePIAdded));
		
		return new PtBoolean(true);
	}
}
