package com.example.interpretor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.adts.MyDictionary;
import model.statements.*;

import java.io.IOException;
import examples.Examples;

public class ProgramsViewController {
    @FXML
    private ListView<IStatement> programsListView;

    @FXML
    private MainViewController mainViewController;

    @FXML
    public void initialize() {
        IStatement[] examples = new Examples().getExamples();
        ObservableList<IStatement> exampleList = FXCollections.observableArrayList();
        for (IStatement example: examples) {
            try {
                example.typeCheck(new MyDictionary<>());
                exampleList.add(example);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Exception found in program: " + example + "\nTraceback:\n" + e.getMessage());
                alert.showAndWait();
            }
        }
        this.programsListView.setItems(exampleList);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load());
            this.mainViewController = fxmlLoader.getController();
            stage.setTitle("Main Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.programsListView.getSelectionModel().selectedItemProperty().addListener((_, _, newStatement) -> {
            String filename = "log" + (programsListView.getSelectionModel().getSelectedIndex() + 1) + ".txt";
            this.mainViewController.setStatement(newStatement, filename);
        });
    }
}