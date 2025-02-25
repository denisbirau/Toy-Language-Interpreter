package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyILockTable;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewLockStatement implements IStatement{
    private final String varName;

    public NewLockStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyILockTable<Integer> lockTable = state.getLockTable();
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();

        int newValue = lockTable.allocate(-1);

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " was not declared!");

        IValue varValue = symbolTable.get(this.varName);
        if (!varValue.getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not an integer!");

        symbolTable.put(this.varName, new IntValue(newValue));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "newLock(" + this.varName + ")";
    }
}
