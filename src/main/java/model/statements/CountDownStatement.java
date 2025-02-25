package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyICountDownLatchTable;
import model.adts.MyIDictionary;
import model.adts.MyIList;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class CountDownStatement implements IStatement{
    private final String varName;

    public CountDownStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyICountDownLatchTable<Integer> latchTable = state.getLatchTable();
        MyIList<IValue> output = state.getOutput();

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " was not declared!");

        IValue varValue = symbolTable.get(this.varName);
        if (!varValue.getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        IntValue intValue = (IntValue) varValue;
        if (!latchTable.containsKey(intValue.getValue()))
            throw new StatementException(this.varName + " is not in latch table!");

        int value = latchTable.get(intValue.getValue());
        if (value > 0) {
            latchTable.put(intValue.getValue(), value - 1);
        }
        output.add(new IntValue(state.getId()));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CountDownStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "";
    }
}
