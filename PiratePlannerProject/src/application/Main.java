package application;
	
import application.model.Expense;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;



/**
 * @author leon
 *
 */
public class Main extends Application {
	private static Stage stage;
	private static Stage newStage;
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/Month.fxml"));
			Scene scene = new Scene(root,780,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	/**
	 * @param path
	 */
	public static void changeScene(String path) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource(path));
			stage.setScene(new Scene(root, 780, 600));
			stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path
	 */
	public static void createNewWindow(String path) {
	     try{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
            Parent root1 = (Parent) fxmlLoader.load();
                       
            newStage = new Stage();
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setTitle("ABC");
            newStage.setScene(new Scene(root1));  
            newStage.show();
        }catch(Exception e) {
	    	  e.printStackTrace();
	    }
	}
	/**
	 * @param path
	 */
	public static void changeNewWindowScene(String path) {
		try {
			Parent root = FXMLLoader.load(Main.class.getResource(path));
			newStage.setScene(new Scene(root));
			newStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	public static void closeNewWindow() {
		newStage.close();
	}
}
