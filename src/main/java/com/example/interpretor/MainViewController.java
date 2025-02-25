package com.example.interpretor;

import controller.Controller;
import exceptions.MyException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.*;
import model.expressions.ArithmeticExpression;
import model.expressions.VariableExpression;
import model.statements.*;
import model.types.IntType;
import model.values.IValue;
import model.values.StringValue;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainViewController {
    private Controller controller;

    @FXML
    public TextField nrOfProgramStatesTextField;

    @FXML
    public TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML
    public TableColumn<Map.Entry<Integer, IValue>, String> addressHeapTableColumn;
    @FXML
    public TableColumn<Map.Entry<Integer, IValue>, String> valueHeapTableColumn;

    @FXML
    public ListView<IValue> outputListView;

    @FXML
    public ListView<StringValue> fileTableListView;

    @FXML
    public ListView<ProgramState> programStatesListView;

    @FXML
    public TableView<Map.Entry<String, IValue>> symbolTableView;
    @FXML
    public TableColumn<Map.Entry<String, IValue>, String> nameSymbolTableColumn;
    @FXML
    public TableColumn<Map.Entry<String, IValue>, String> valueSymbolTableColumn;

    @FXML
    public ListView<IStatement> executionStackListView;

    @FXML
    public Button runOneStepButton;

    @FXML
    public TableView<Map.Entry<Integer, Integer>> latchTableView;
    @FXML
    public TableColumn<Map.Entry<Integer, Integer>, String> locationLatchTableColumn;
    @FXML
    public TableColumn<Map.Entry<Integer, Integer>, String> valueLatchTableColumn;

    @FXML
    public TableView<Map.Entry<Integer, Integer>> lockTableView;
    @FXML
    public TableColumn<Map.Entry<Integer, Integer>, String> locationLockTableColumn;
    @FXML
    public TableColumn<Map.Entry<Integer, Integer>, String> valueLockTableColumn;

    @FXML
    public TableView<Map.Entry<String, Pair<List<String>, IStatement>>> procedureTableView;
    @FXML
    public TableColumn<Map.Entry<String, Pair<List<String>, IStatement>>, String> procedureNameColumn;
    @FXML
    public TableColumn<Map.Entry<String, Pair<List<String>, IStatement>>, String> procedureBodyColumn;

    @FXML
    public TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;
    @FXML
    public TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> indexSemaphoreTableColumn;
    @FXML
    public TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> valueSemaphoreTableColumn;
    @FXML
    public TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, String> listOfValuesSemaphoreTableColumn;

    public void initialize() {
        this.runOneStepButton.setOnAction(_ -> this.runOneStepButtonHandler());

        this.programStatesListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ProgramState> call(ListView<ProgramState> programStateListView) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(ProgramState item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(Integer.toString(item.getId()));
                        } else
                            setText("");
                    }
                };
            }
        });

        this.nameSymbolTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey()));
        this.valueSymbolTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        this.addressHeapTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));
        this.valueHeapTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        this.locationLatchTableColumn.setCellValueFactory(entryIntegerCellDataFeatures -> new SimpleStringProperty(entryIntegerCellDataFeatures.getValue().getKey().toString()));
        this.valueLatchTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        this.locationLockTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));
        this.valueLockTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().toString()));

        this.procedureNameColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey() + "(" + entryStringCellDataFeatures.getValue().getValue().getKey() + ")"));
        this.procedureBodyColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty("{ " + entryStringCellDataFeatures.getValue().getValue().getValue().toString() + " }"));

        this.indexSemaphoreTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getKey().toString()));
        this.valueSemaphoreTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().getKey().toString()));
        this.listOfValuesSemaphoreTableColumn.setCellValueFactory(entryStringCellDataFeatures -> new SimpleStringProperty(entryStringCellDataFeatures.getValue().getValue().getValue().toString()));

        this.programStatesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.programStatesListView.getSelectionModel().selectedItemProperty().addListener(
                (_, _, newState) -> {this.populateExecutionStackListView(newState); this.populateSymbolTableView(newState);}
        );
    }

    private void runOneStepButtonHandler() {
        try {
            this.controller.executeOneStepForAllProgramsGUI();
        } catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        } finally {
            this.updateFields();
        }
    }

    private void updateFields() {
        ProgramState state = this.programStatesListView.getSelectionModel().getSelectedItem();
        this.setNrOfProgramsTextField();
        this.populateProgramStatesListView();
        this.populateSymbolTableView(state);
        this.populateExecutionStackListView(state);
        this.populateOutputListView();
        this.populateFileTableListView();
        this.populateHeapTableView();
        this.populateLatchTableView();
        this.populateLockTableView();
        this.populateProcedureTableView();
        this.populateSemaphoreTableView();
    }

    private void populateSemaphoreTableView() {
        if (!this.controller.getAllProgramStates().isEmpty()) {
            this.semaphoreTableView.getItems().setAll(FXCollections.observableArrayList(this.controller.getSemaphoreTable().getMap().entrySet()));
        }
    }

    private void populateProcedureTableView() {
        if (!this.controller.getAllProgramStates().isEmpty()) {
            this.procedureTableView.getItems().setAll(FXCollections.observableArrayList(this.controller.getProcedureTable().getMap().entrySet()));
        }
    }

    private void populateLockTableView() {
        if (!this.controller.getAllProgramStates().isEmpty()) {
            this.lockTableView.getItems().setAll(FXCollections.observableArrayList(this.controller.getLockTable().getMap().entrySet()));
        }
    }

    private void populateLatchTableView() {
        if (!this.controller.getAllProgramStates().isEmpty()) {
            this.latchTableView.getItems().setAll(FXCollections.observableArrayList(this.controller.getLatchTable().getMap().entrySet()));
        }
    }

    private void populateHeapTableView() {
        if (!this.controller.getAllProgramStates().isEmpty())
            this.heapTableView.getItems().setAll(FXCollections.observableArrayList(this.controller.getHeapTable().getMap().entrySet()));
    }

    private void populateFileTableListView() {
        if (!this.controller.getAllProgramStates().isEmpty())
            this.fileTableListView.getItems().setAll(FXCollections.observableArrayList(this.controller.getFileTable().getMap().keySet()));
    }

    private void populateOutputListView() {
        if (!this.controller.getAllProgramStates().isEmpty())
            this.outputListView.getItems().setAll(FXCollections.observableArrayList(this.controller.getOutput().getList()));
    }

    private void populateExecutionStackListView(ProgramState state) {
        if (state != null) {
            ObservableList<IStatement> statements = FXCollections.observableArrayList(state.getExecutionStack().getElements());
            this.executionStackListView.getItems().setAll(statements);
        }
    }

    private void populateSymbolTableView(ProgramState state) {
        if (state != null)
            this.symbolTableView.getItems().setAll(FXCollections.observableArrayList(state.getSymbolTable().getMap().entrySet()));
    }

    private void populateProgramStatesListView() {
        if (!this.controller.getAllProgramStates().isEmpty())
            this.programStatesListView.setItems(FXCollections.observableArrayList(this.controller.getAllProgramStates()));
    }

    private void setNrOfProgramsTextField() {
        this.nrOfProgramStatesTextField.setText(String.valueOf(this.controller.getAllProgramStates().size()));
    }

    public void setStatement(IStatement statement, String path) {
        MyIProcedureTable<String, Pair<List<String>, IStatement>> procedureTable = new MyProcedureTable<>();
        List<String> parameters = new ArrayList<>();
        parameters.add("a");
        parameters.add("b");
        procedureTable.put("sum", new Pair<>(parameters, new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                            new AssignStatement("v", new ArithmeticExpression('+', new VariableExpression(parameters.getFirst()), new VariableExpression(parameters.getLast()))),
                            new PrintStatement(new VariableExpression("v"))
                    )
                )));
        procedureTable.put("product", new Pair<>(parameters, new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ArithmeticExpression('*', new VariableExpression(parameters.getFirst()), new VariableExpression(parameters.getLast()))),
                        new PrintStatement(new VariableExpression("v"))
                )
        )));
        ProgramState state = new ProgramState(
                new MyStack<>(),
                new MyStack<>(),
                new MyList<>(),
                new MyDictionary<>(),
                new MyHeap<>(),
                new MyCountDownLatchTable<>(),
                new MyLockTable<>(),
                procedureTable,
                new MySemaphoreTable<>(),
                statement
        );

        Repository repository = new Repository(path);
        repository.addProgram(state);
        this.controller = new Controller(repository);

        this.updateFields();
        this.programStatesListView.getSelectionModel().selectFirst();

        this.runOneStepButton.setDisable(false);
    }
}
