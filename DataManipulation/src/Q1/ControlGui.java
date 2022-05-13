package Q1;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

    public class ControlGui implements Initializable {
    Statement statement;
    @FXML
    private TextField idField;
    @FXML
    private Button btnEdit;
    @FXML
    private TableColumn<Student, Integer> columnId;
    @FXML
    private TextField nameField;
    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Double> columnGrade;
    @FXML
    private Button btnCourseRegist;
    @FXML
    private TextField gradeField;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField majorField;
    @FXML
    private TableColumn<Student, String> columnMajor;
    @FXML
    private TextField courseField;
    @FXML
    private TextField semesterField;
    @FXML
    private TableColumn<Student, String> columnName;

    @FXML
    void actionAddStudent(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String major = majorField.getText();
        double grade = Double.parseDouble(gradeField.getText());
        String sql = "INSERT INTO Student (id,name,major,grade) VALUES (" + id +  ",'" + name + "','" + major + "'," + grade + ")";
        this.statement.executeUpdate(sql);
        tableView.getItems().add(new Student(id,name , major, grade));
    }

    @FXML
    void actionEditStudent(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        String major = majorField.getText();
        double grade = Double.parseDouble(gradeField.getText());
        String sql = "UPDATE Student SET name = '" + name + "', major = '" + major + "', grade = " + grade + "WHERE id = " + id;
        this.statement.executeUpdate(sql);
        tableView.getItems().set(tableView.getSelectionModel().getSelectedIndex() , new Student(id,name,major,grade));
    }

    @FXML
    void actionDeleteStudent(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idField.getText());
        String sql = "DELETE  FROM Student WHERE id = " + id;
        this.statement.executeUpdate(sql);
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    void actionRegistCourse(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(idField.getText());
        int courseId = Integer.parseInt(courseField.getText());
        String semester = semesterField.getText();
        String sql = "INSERT INTO Registration (studentId,courseId,semester) VALUES (" + id + "," + courseId + ",'" + semester + "')";
        this.statement.executeUpdate(sql);
        System.out.println("Course registered successfully..");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnId.setCellValueFactory(new PropertyValueFactory("id"));
        columnName.setCellValueFactory(new PropertyValueFactory("name"));
        columnMajor.setCellValueFactory(new PropertyValueFactory("major"));
        columnGrade.setCellValueFactory(new PropertyValueFactory("grade"));

        ResultSet rs;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection  = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/student?serverTimezone=UTC","root", "");
            this.statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM Student");
            while (rs.next()){
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setGrade(rs.getDouble("grade"));
                student.setMajor(rs.getString("major"));
                this.tableView.getItems().add(student);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tableView.getSelectionModel().selectedItemProperty().addListener(e -> {
            Student student = tableView.getSelectionModel().getSelectedItem();
            if (student != null) {
                idField.setText(String.valueOf(student.getId()));
                nameField.setText(student.getName());
                majorField.setText(student.getMajor());
                gradeField.setText(String.valueOf(student.getGrade()));
            }
        });
    }

}

