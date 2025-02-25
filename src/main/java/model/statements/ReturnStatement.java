package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.types.IType;

public class ReturnStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getSymbolTables().pop();
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "return";
    }
}
