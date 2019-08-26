package sinagajunior.convert.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import sinagajunior.convert.utility.AesUtil;
import sinagajunior.convert.utility.Partition;
import sinagajunior.convert.utility.PusdafilConverter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @project convertingtool
 * @Author royantonius on 07/08/19 .
 */
@Component
//@Slf4j
public class MainController implements BootInitializable {

    @FXML
    private TextField txtSource;


    @FXML
    private TextField txtResult;


    //@FXML
    // private TextArea txtLog;


   @FXML
   private  TextField txtLimitMaxRow;

    @FXML
    private TextField txtEncryptedKey;


    @FXML
    private TextField txtEncryptedIV;


    @FXML
    private Button btnSource;

    @FXML
    private Button btnExport;


    @FXML
    private CheckBox isRandomIV;


    @Override
    public Node initView() throws IOException {
        return null;
    }

    @Override
    public void setStage(Stage stage) {

    }

    @Override
    public void initConstuct() {

    }

    @Override
    public void initIcons() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       txtLimitMaxRow.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtLimitMaxRow.setText(oldValue);

            }
        });


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }


    @FXML
    public void convertandencrypt() {
        File folder = new File(this.txtSource.getText());
        if (checkFieldNotEmpty()) {

            for (File fileCSV : folder.listFiles()) {
                if (fileCSV.getName().endsWith(".csv")) {


                    List<Object> objectList = null; //PusdafilConverter.ConvertCSVToJson(fileCSV);

                    try {
                        objectList = PusdafilConverter.ConvertCSVToJson(fileCSV);
                    } catch (Exception e) {
                       // e.printStackTrace();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }


                    createFile(fileCSV,convertToPrettierJson(objectList),"");
                     int hitung=0;
                    for(List<Object> data : Partition.ofSize(objectList,Integer.parseInt(txtLimitMaxRow.getText()))){


                        String ojkResult = AesUtil.encryptToOJKFormat(convertToPrettierJson(data),
                        txtEncryptedKey.getText(), txtEncryptedIV.getText(), isRandomIV.isSelected());
                        hitung++;
                        createFile(fileCSV,ojkResult, "encript-"+hitung+"-");

                    }
                }

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Finish");
            alert.showAndWait();


        } else {
            ShowErrorAllert("Field cant be Empty");
        }


    }

    private void ShowErrorAllert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static  String convertToPrettierJson(List<Object> list){

        ObjectMapper mapper = new ObjectMapper();
        try {
            return  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

       return  null;
    }


    private void createFile(File fileCSV, String ojkResult, String encriptedSign)  {


        FileWriter fw;

        try {
            fw = new FileWriter(new File(txtResult.getText().concat(
                    "/").concat(encriptedSign).concat(fileCSV.getName().replace(".csv", "").concat(".json"))));

            fw.write(String.format("%s", ojkResult));

            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }


    @FXML
    public void closeapp() {
        System.exit(0);
    }


    @FXML
    public void opensourcefolder() {
        this.txtSource.setText(openDirectoryChooser());
    }

    @FXML
    public void openexportfolder() {
        this.txtResult.setText(openDirectoryChooser());
    }

    private String openDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);
        return selectedDirectory == null ? "" : selectedDirectory.getAbsolutePath();

    }

    private boolean checkFieldNotEmpty() {
        boolean valid = true;
        if (txtSource.getText().equals("") || txtResult.equals("") ||
                txtEncryptedKey.getText().equals("") || txtLimitMaxRow.getText().equals("")) {
            valid = false;
        }
        if (!(isRandomIV.isSelected()) && txtEncryptedIV.getText().equals("")) {
            valid = false;
        }

        return valid;
    }


}
