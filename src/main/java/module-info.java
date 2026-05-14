module com.oopryhmatooii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.oopryhmatooii to javafx.fxml;
    exports com.oopryhmatooii;
}