package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyISemaphoreTable;
import model.adts.MyIStack;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.List;

public class AcquireStatement implements IStatement{
    private final String varName;

    public AcquireStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " is not declared!");
        if (!symbolTable.get(this.varName).getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        int varValue = ((IntValue)symbolTable.get(this.varName)).getValue();
        if (!semaphoreTable.containsKey(varValue))
            throw new StatementException(this.varName + " is not in semaphore table!");

        Pair<Integer, List<Integer>> pair = semaphoreTable.get(varValue);
        int key = pair.getKey();
        List<Integer> list = pair.getValue();
        if (key > list.size()) {
            if (!list.contains(state.getId()))
                list.add(state.getId());
        } else {
            executionStack.push(this.deepCopy());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AcquireStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "acquire(" + this.varName + ")";
    }
}
