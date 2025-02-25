package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.types.IType;

public class NoStatement implements IStatement {
    public NoStatement() {}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NoStatement();
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "\n";
    }
}
