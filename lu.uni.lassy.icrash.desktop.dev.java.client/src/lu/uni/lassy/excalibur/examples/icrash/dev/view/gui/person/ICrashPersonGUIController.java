package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.person;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.util.Callback;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CoordinatorController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.PersonController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtPI;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyCoordinatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyPersonImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Modality;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/**
 * The Class ICrashGUIController, used for dealing with the GUI for the person.
 */
public class ICrashPersonGUIController extends AbstractAuthGUIController {
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/

	/** The logon pane that holds the controls for logging on. */
	@FXML
    private Pane pnLogon;

    /** The textfield for entering in the username for logging on. */
    @FXML
    private TextField txtfldPersonLogonUserName;

    /** The passwordfield for entering in the password for logging on. */
    @FXML
    private PasswordField psswrdfldPersonLogonPassword;
    
    /** The textfield for entering in the captcha for logging on. */
    @FXML
    private TextField txtfldPersonCaptcha;

    /** The button that allows a user to initiate the logon function. */
    @FXML
    private Button bttnPersonLogon;

    /** The main tabpane that holds the normal user controls. */
    @FXML
    private TabPane tbpnMain;

    /** The tab containing the controls for PIs. */
    @FXML
    private Tab tbPersonPis;

    /** The button that allows searching for a PI. */
    @FXML
    private Button bttnSearchPI;

    /** The button that allows a user to send a new request. */
    @FXML
    private Button bttnSendNewRequest;

    /** The button that allows a user to get a GPS location of a PI. */
    @FXML
    private Button bttnGetGPSLocation;

    /** The button that allows a user to a description of a PI. */
    @FXML
    private Button bttnGetDescription;

    /** The tablview that shows the user the PIs they have selected. */
    @FXML
    private TableView<CtPI> tblvwPis;

    /** The tableview of the messages the user has recieved. */
    @FXML
    private TableView<Message> tblvwPersonMessages;

    /** The button that allows a user to logoff. */
    @FXML
    private Button bttnPersonLogoff;

    /**
     * Button event that deals with searching for a specific PI
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnSearchPI_OnClick(ActionEvent event) {
    	
    	searchPI();
    }

    /**
     * Button event that deals with sending a new request
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnSendNewRequest_OnClick(ActionEvent event) {
    	
    	sendNewRequest();
    }

    /**
     * Button event that deals with logging off the user
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnPersonLogoff_OnClick(ActionEvent event) {
    	logoff();
    }

    /**
     * Button event that deals with logging on the user
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnPersonLogon_OnClick(ActionEvent event) {
    	logon();
    }

    /**
     * Button event that deals with getting the GPS location of a PI
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnGetGPSLocation_OnClick(ActionEvent event) {
    	
    	getGPSLocation();
    }

    /**
     * Button event that deals with getting the description of a PI
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnGetDescription_OnClick(ActionEvent event) {
    	
    	getDescription();
    }
    
    /*
     * These are other classes accessed by this controller
     */
	
	/** The user controller, for this GUI it's the person controller and allows access to person functions. */
	private PersonController userController;
	
	/*
	 * Other things created for this controller
	 */
	//NOTHING HERE
	
	/*
	 * Methods used within the GUI
	 */
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
	 */
	public void setUpTables() {
		
		setUpMessageTables(tblvwPersonMessages);
		setUpPITables(tblvwPis);
	}
	
	/**
	 * Runs the function that will allow the current user to search for the specific PI.
	 */
	
	private void searchPI(){
		
		// Further implementation
	}
	
	/**
	 * Runs the function that will allow the current user to send a new request to add the PI.
	 */
	
	private void sendNewRequest() {
		
		// Further implementation
	}
	
	/**
	 * Runs the function that will allow the current user to get the selected PI's GPS location.
	 */
	
	private void getGPSLocation() {
		
		// Further implementation
	}
	
	/**
	 * Runs the function that will allow the current user to get the selected PI's description.
	 */
	
	private void getDescription() {
		
		// Further implementation
	}
	

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logon()
	 */
	@Override
	public void logon() {
		
		if(txtfldPersonLogonUserName.getText().length() > 0 && psswrdfldPersonLogonPassword.getText().length() > 0) {
			
			try {
				if (userController.oeLogin(txtfldPersonLogonUserName.getText(), psswrdfldPersonLogonPassword.getText()).getValue()) {
					
					if (userController.getUserType() == UserType.Person) {
						logonShowPanes(true);
					}
				}
			}
			catch (ServerOfflineException | ServerNotBoundException e) {
				
				showExceptionErrorMessage(e);
			}
    	}
    	else
    		showWarningNoDataEntered();
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonWithCaptcha()
	 */
	@Override
	public void logonWithCaptcha() {
		
		if(txtfldPersonLogonUserName.getText().length() > 0 && psswrdfldPersonLogonPassword.getText().length() > 0 && txtfldPersonCaptcha.getText().length() > 0) {
			
			try {
				if (userController.oeLoginWithCaptcha(txtfldPersonLogonUserName.getText(), psswrdfldPersonLogonPassword.getText(), txtfldPersonCaptcha.getText()).getValue()) {
					
					if (userController.getUserType() == UserType.Person) {
						logonWithCaptchaShowPanes(true);
					}
				}
			}
			catch (ServerOfflineException | ServerNotBoundException e) {
				
				showExceptionErrorMessage(e);
			}
    	}
    	else
    		showWarningNoDataEntered();
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logoff()
	 */
	@Override
	public void logoff() {
		
		try {
			
			if (userController.oeLogout().getValue()) {
				
				logonShowPanes(false);
			}
		} catch (ServerOfflineException | ServerNotBoundException e) {
			
			showExceptionErrorMessage(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
	 */
	protected void logonShowPanes(boolean loggedOn) {
		
		tbpnMain.setVisible(loggedOn);
		bttnPersonLogoff.setDisable(!loggedOn);
		pnLogon.setVisible(!loggedOn);
		bttnPersonLogon.setDefaultButton(!loggedOn);
		
		if (loggedOn) {
			
			tbpnMain.getSelectionModel().selectFirst();
		}
		else{
			txtfldPersonLogonUserName.setText("");
			psswrdfldPersonLogonPassword.setText("");
			txtfldPersonLogonUserName.requestFocus();
			
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
	 */
	protected void logonWithCaptchaShowPanes(boolean loggedOn) {
		
		tbpnMain.setVisible(loggedOn);
		bttnPersonLogoff.setDisable(!loggedOn);
		pnLogon.setVisible(!loggedOn);
		bttnPersonLogon.setDefaultButton(!loggedOn);
		
		if (loggedOn) {
			
			tbpnMain.getSelectionModel().selectFirst();
		}
		else{
			txtfldPersonLogonUserName.setText("");
			psswrdfldPersonLogonPassword.setText("");
			txtfldPersonLogonUserName.requestFocus();
			
		}
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		
		try {
			userController.removeAllListeners();
			
		} catch (ServerOfflineException | ServerNotBoundException e) {
			
			showExceptionErrorMessage(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setUpTables();
		
		logonShowPanes(false);
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try { 
			if (actor instanceof ActPerson)
			{
				try{
					userController = new PersonController((ActPerson)actor);
					
					try{
						userController.getAuthImpl().listOfMessages.addListener(new ListChangeListener<Message>() {
							
							@Override
							public void onChanged(ListChangeListener.Change<? extends Message> c) {
								
								addMessageToTableView(tblvwPersonMessages, c.getList());
							}
						});
						
						((ActProxyPersonImpl)userController.getAuthImpl()).MapOfCtPis.addListener(new MapChangeListener<String, CtPI>(){
				
							@Override
							public void onChanged(
									javafx.collections.MapChangeListener.Change<? extends String, ? extends CtPI> change) {
								
								addPisToTableView(tblvwPis, change.getMap().values());
							}
						});
						
					} catch (Exception e) {
						
						showExceptionErrorMessage(e);
					}
				} catch (RemoteException e) {
					
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerOfflineException();
					
				} catch (NotBoundException e) {
					
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerNotBoundException();
				}
			}
			else
				throw new IncorrectActorException(actor, ActPerson.class);
			
		} catch (IncorrectActorException | ServerOfflineException | ServerNotBoundException e) {
			
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		
		return new PtBoolean(true);
	}
}
