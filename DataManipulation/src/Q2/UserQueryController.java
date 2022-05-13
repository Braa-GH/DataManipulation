package Q2;

import Q1.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UserQueryController implements Initializable {


    @FXML
    private VBox root;
    @FXML
    private Button btnQ1;
    @FXML
    private Button btnQ2;
    @FXML
    private Button btnQ3;
    @FXML
    private Button btnQ4;
    @FXML
    private Button btnExecute;
    @FXML
    private TableView<Student> table;
    @FXML
    private TableColumn<Student, String> majorColumn;
    @FXML
    private TableColumn<Student, Double> gradeColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TextArea textArea;
    Statement statement;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection  = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/student?serverTimezone=UTC","root", "");
            this.statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        majorColumn.setCellValueFactory(new PropertyValueFactory("major"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory("grade"));

        table.getSelectionModel().selectedItemProperty().addListener(e -> {

        });
    }


    @FXML
    void actionQ1(ActionEvent event) {
        String sql = "SELECT * FROM Student WHERE major = 'Software Engineering'";
        textArea.setText(sql);
    }

    @FXML
    void actionQ2(ActionEvent event) {
        String sql = "SELECT * FROM Student WHERE grade >= 90";
        textArea.setText(sql);
    }

    @FXML
    void actionQ3(ActionEvent event) {
        String sql = "SELECT * FROM Student WHERE grade > 60 ORDER BY name";
        textArea.setText(sql);
    }

    @FXML
    void actionQ4(ActionEvent event) {
        String sql = "UPDATE Student SET grade = grade + 3 WHERE major = 'Computer Science' AND grade < 70";
        textArea.setText(sql);
    }

    @FXML
    void actionExecute(ActionEvent event) throws SQLException {
        this.table.getItems().clear();
        ResultSet resultSet = this.statement.executeQuery(textArea.getText());
        while (resultSet.next()){
            Student student = new Student();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setGrade(resultSet.getDouble("grade"));
            student.setMajor(resultSet.getString("major"));
            this.table.getItems().add(student);
        }
    }


}
