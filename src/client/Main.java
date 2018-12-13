package client;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	static ScrumUIController controller;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ScrumUI.fxml"));
	    Parent root = loader.load();
		
		controller = (ScrumUIController)loader.getController();
	    
        Scene scene = new Scene(root, 600, 400);
    
        stage.setTitle("SCRUM Tool");
        stage.setScene(scene);
        stage.setOnCloseRequest(event->System.exit(0));
        stage.show();
        
     
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
