package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.util.Random;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

/**
 * The Class DtCaptcha, which holds a datatype of the captcha.
 */
public class DtCaptcha extends DtString implements JIntIs {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**
	 * Instantiates a new datatype captcha.
	 *
	 * @param s The primitive tpye of the string to create the datatype 
	 */
	public DtCaptcha(PtString s) {
		super(s);
	}
	
	//New constructor used for the DtCaptcha, a random String will be generated.
	public DtCaptcha() {
		this.generateNewCaptcha();
	}
	
	/** The maximum length a captcha can be. */
	private int _maxLength = 8;
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#is()
	 */
	public PtBoolean is(){
		return new PtBoolean(this.value.getValue().length() <= _maxLength);
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.DtIs#getExpectedDataStructure()
	 */
	public PtString getExpectedDataStructure(){
		return new PtString("Expected strucutre of the captcha is to have a maximum length of " + _maxLength); 
	}
	
	// Creates a new ptString, the captcha to solve
	public void generateNewCaptcha() {
		Random r = new Random();
		String string = "";
		for(int i = 0; i < 8; i++)
			string += Character.toString(((char) (r.nextInt(26) + 97)));
		
		value = new PtString(string);
	}
}
