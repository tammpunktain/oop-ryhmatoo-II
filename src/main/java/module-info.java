module com.example.oopryhmatooii {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.oopryhmatooii to javafx.fxml;
    exports com.example.oopryhmatooii;
}