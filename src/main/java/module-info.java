module com.oopryhmatooii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.oopryhmatooii to javafx.fxml;
    exports com.oopryhmatooii;
}