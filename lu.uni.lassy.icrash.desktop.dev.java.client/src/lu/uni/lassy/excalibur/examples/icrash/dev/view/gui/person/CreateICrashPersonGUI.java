package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.person;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActPerson;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPhoneNumber;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

import java.net.URL;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Class MainICrashGUI that allows creation of a person iCrash window.
 */
public class CreateICrashPersonGUI implements CreatedWindows {
	
	/**
	 * Instantiates a new window for the person to use iCrash.
	 *
	 * @param aPersonID the ID of the person associated with this window
	 * @param aPerson the actor associated with this window
	 */
	public CreateICrashPersonGUI() {
		
		start();
	}
	
	/** The Person ID that was used to create this window, it's used to work out which windows to close when a person has been deleted. */
	private DtPhoneNumber _aPersonID;
	
	/**
	 * Gets the data type of the person's ID.
	 *
	 * @return the datatype of the person's ID
	 */
	public DtPhoneNumber getDtPersonID(){
		return this._aPersonID;
	}
	
	/** The stage that the form will be held in. */
	private Stage stage;
	
	/**
	 * Starts the system and opens the window up (If no exceptions have been thrown).
	 *
	 * @param aActPerson the actor associated with this window
	 */
	private void start() {
		
		try {
			URL url = this.getClass().getResource("ICrashPersonGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
			
            stage = new Stage();
            stage.setTitle("iCrash Person");
            stage.setScene(new Scene(root));
            stage.show();
            ((ICrashPersonGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            	
				@Override
				public void handle(WindowEvent event) {
					((ICrashPersonGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}
	
	/**
	 * Allows the other screens to force this window to close. Will be used if the person has been deleted
	 */
	@Override
	public void closeWindow() {
		
		if (stage != null)
			stage.close();
	}
}
