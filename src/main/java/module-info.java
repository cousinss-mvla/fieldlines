module me.cousinss.fieldlines {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.cousinss.fieldlines to javafx.fxml;
    exports me.cousinss.fieldlines;
}