package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.types.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws MyException;
    IStatement deepCopy();
    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException;
}
