package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyILockTable;
import model.adts.MyIStack;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class LockStatement implements IStatement{
    private final String varName;

    public LockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyILockTable<Integer> lockTable = state.getLockTable();
        MyIStack<IStatement> executionStack = state.getExecutionStack();

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " was not declared!");

        IValue varValue = symbolTable.get(this.varName);
        if (!varValue.getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        int intValue = ((IntValue) varValue).getValue();
        if (!lockTable.containsKey(intValue))
            throw new StatementException(this.varName + " is not in lock table!");

        if (lockTable.get(intValue) == -1)
            lockTable.put(intValue, state.getId());
        else
            executionStack.push(this.deepCopy());

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new LockStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "lock(" + this.varName + ")";
    }
}
