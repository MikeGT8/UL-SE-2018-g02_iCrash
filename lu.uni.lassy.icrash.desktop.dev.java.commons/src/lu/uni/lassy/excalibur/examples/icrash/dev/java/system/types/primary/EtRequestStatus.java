package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public enum EtRequestStatus implements JIntIs {
	
	pending,
	treated,
	solved;
	
	public PtBoolean is() {
		
		return new PtBoolean(this.name() == EtRequestStatus.pending.name() || 
				this.name() == EtRequestStatus.treated.name()|| 
				this.name() == EtRequestStatus.solved.name());
	}
}
