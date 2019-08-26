package sinagajunior.convert.controllers;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSourceAware;

import java.io.IOException;

public interface BootInitializable extends Initializable, ApplicationContextAware, MessageSourceAware {


    public Node initView() throws IOException;
    public void setStage(Stage stage);

    public void initConstuct();

    public void initIcons();

}
