package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.secondary.DtSMS;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class CtPerson extends CtAuthenticated {

	private static final long serialVersionUID = 227L;

	public DtPhoneNumber id;
	public EtHumanKind type;

	/* Initialises the class and it's data. */
	public PtBoolean init(DtPhoneNumber aId,EtHumanKind aType){

		id = aId;
		type = aType;

		return new PtBoolean(true);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof CtPerson))
			return false;
		
		CtPerson ctPerson = (CtPerson)obj;
		if (!ctPerson.id.value.getValue().equals(this.id.value.getValue()))
			return false;
		if (!ctPerson.type.equals(this.type))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		
		return this.id.value.getValue().length();
	}
}
