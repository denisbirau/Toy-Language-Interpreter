package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyICountDownLatchTable;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class AwaitStatement implements IStatement{
    private final String varName;

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyICountDownLatchTable<Integer> latchTable = state.getLatchTable();
        MyIStack<IStatement> executionStack = state.getExecutionStack();

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " was not declared!");

        IValue varValue = symbolTable.get(this.varName);
        if (!varValue.getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        IntValue intValue = (IntValue) varValue;
        if (!latchTable.containsKey(intValue.getValue()))
            throw new StatementException(this.varName + " is not in latch table!");

        int status = latchTable.get(intValue.getValue());
        if (status != 0)
            executionStack.push(this.deepCopy());

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "await(" + this.varName + ")";
    }
}
