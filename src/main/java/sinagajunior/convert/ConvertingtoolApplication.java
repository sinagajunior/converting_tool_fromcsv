package sinagajunior.convert;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class ConvertingtoolApplication extends Application {


	private ConfigurableApplicationContext springContext;

	private Parent rootNode;


	public static void main(String[] args) {
		Application.launch(args);
	}


	@Override
	public void init() throws Exception  {
		springContext = SpringApplication.run(ConvertingtoolApplication.class);
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainForm.fxml"));
		fxmlLoader.setControllerFactory(springContext::getBean);
		rootNode  = fxmlLoader.load();



	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(rootNode));
		primaryStage.show();

	}


	@Override
	public void stop() throws Exception {
		springContext.close();
	}

}
