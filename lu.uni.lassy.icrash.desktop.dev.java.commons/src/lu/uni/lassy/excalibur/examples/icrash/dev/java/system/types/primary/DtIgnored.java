package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public class DtIgnored extends PtBoolean implements JIntIs {

	private static final long serialVersionUID = 227L;
	
	public DtIgnored(boolean bool) {
		
		super(bool);
	}
	
	public PtBoolean is() {
		
		return new PtBoolean((this.getValue() == true || this.getValue() == false));
	}
}
