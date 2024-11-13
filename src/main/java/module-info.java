module com.example.java_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires mysql.connector.java;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.java_project to javafx.fxml;
    exports com.example.java_project;

    opens com.example.java_project.enrollment to javafx.fxml;
    exports com.example.java_project.enrollment;

    opens com.example.java_project.measurements to javafx.fxml;
    exports com.example.java_project.measurements;

    opens com.example.java_project.workersconsole to javafx.fxml;
    exports com.example.java_project.workersconsole;

    opens com.example.java_project.allworker to javafx.fxml;
    exports com.example.java_project.allworker;

    opens com.example.java_project.measurementsexplorer to javafx.fxml;
    exports com.example.java_project.measurementsexplorer;

    opens com.example.java_project.getreadyproducts to javafx.fxml;
    exports com.example.java_project.getreadyproducts;

    opens com.example.java_project.adminpanel to javafx.fxml;
    exports com.example.java_project.adminpanel;

    opens com.example.java_project.orderdeliverypanel to javafx.fxml;
    exports com.example.java_project.orderdeliverypanel;


}