package model;

import exceptions.ADTException;
import exceptions.MyException;
import javafx.util.Pair;
import model.adts.*;
import model.values.IValue;
import model.statements.IStatement;
import model.values.StringValue;

import java.io.BufferedReader;
import java.util.List;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIStack<MyIDictionary<String, IValue>> symbolTables;
    private MyIList<IValue> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<IValue> heap;
    private MyICountDownLatchTable<Integer> latchTable;
    private MyILockTable<Integer> lockTable;
    private MyIProcedureTable<String, Pair<List<String>, IStatement>> procedureTable;
    private MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable;
    private IStatement originalProgram;
    private int id;
    private static int currentId = 1;

    public ProgramState(MyIStack<IStatement> executionStack,
                 MyIStack<MyIDictionary<String, IValue>> symbolTables,
                 MyIList<IValue> output,
                 MyIDictionary<StringValue, BufferedReader> fileTable,
                 MyIHeap<IValue> heap,
                 MyICountDownLatchTable<Integer> latchTable,
                 MyILockTable<Integer> lockTable,
                 MyIProcedureTable<String, Pair<List<String>, IStatement>> procedureTable,
                 MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable,
                 IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTables = symbolTables;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.latchTable = latchTable;
        this.lockTable = lockTable;
        this.procedureTable = procedureTable;
        this.semaphoreTable = semaphoreTable;
        this.id = 1;
        this.originalProgram = originalProgram;

        this.executionStack.push(this.originalProgram);
    }

    public ProgramState(MyIStack<IStatement> executionStack,
                        MyIStack<MyIDictionary<String, IValue>> symbolTables,
                        MyIList<IValue> output,
                        MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeap<IValue> heap,
                        MyICountDownLatchTable<Integer> latchTable,
                        MyILockTable<Integer> lockTable,
                        MyIProcedureTable<String, Pair<List<String>, IStatement>> procedureTable,
                        MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable) {
        this.executionStack = executionStack;
        this.symbolTables = symbolTables;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.latchTable = latchTable;
        this.lockTable = lockTable;
        this.procedureTable = procedureTable;
        this.semaphoreTable = semaphoreTable;
    }

    public Boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

    public ProgramState executeOneStep() throws MyException {
        if (this.executionStack.isEmpty()) {
            throw new ADTException("The execution stack of the current program state is empty!");
        }
        IStatement currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    public void typeCheck() throws MyException{
        originalProgram.typeCheck(new MyDictionary<>());
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public MyIStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public MyIStack<MyIDictionary<String, IValue>> getSymbolTables() {
        return this.symbolTables;
    }

    public MyIDictionary<String, IValue> getSymbolTable() {
        if(this.symbolTables.isEmpty()) {
            this.symbolTables.push(new MyDictionary<>());
        }
        return symbolTables.peek();
    }

    public MyIList<IValue> getOutput() {
        return this.output;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public MyIHeap<IValue> getHeap() {
        return this.heap;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public MyICountDownLatchTable<Integer> getLatchTable() { return this.latchTable; }

    public MyILockTable<Integer> getLockTable() { return this.lockTable; }

    public MyIProcedureTable<String, Pair<List<String>, IStatement>> getProcedureTable() { return this.procedureTable; }

    public MyISemaphoreTable<Pair<Integer, List<Integer>>> getSemaphoreTable() { return this.semaphoreTable; }

    // Setters
    public synchronized void setId() {
        currentId++;
        this.id = currentId;
    }

    public void setExecutionStack(MyIStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTables(MyIStack<MyIDictionary<String, IValue>> symbolTables) {
        this.symbolTables = symbolTables;
    }

    public void setOutput(MyIList<IValue> output) {
        this.output = output;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeap(MyIHeap<IValue> heap) {
        this.heap = heap;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public void setLatchTable(MyICountDownLatchTable<Integer> latchTable) { this.latchTable = latchTable; }

    public void setLockTable(MyILockTable<Integer> lockTable) { this.lockTable = lockTable; }

    public void setProcedureTable(MyIProcedureTable<String, Pair<List<String>, IStatement>> procedureTable) { this.procedureTable = procedureTable; }

    public void setSemaphoreTable(MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable) { this.semaphoreTable = semaphoreTable; }

    @Override
    public String toString() {
        return ">>Program State(Id:" + this.id + "):\n" +
                "Execution Stack:\n" + this.executionStack +
                "Symbol Tables:\n" + this.symbolTables +
                "Output:\n" + this.output +
                "File Table:\n" + this.fileTable +
                "Heap:\n" + this.heap +
                "Latch Table:\n" + this.latchTable +
                "Lock Table:\n" + this.lockTable +
                "Procedure Table:\n" + this.procedureTable +
                "Semaphore Table:\n" + this.semaphoreTable;
    }
}
