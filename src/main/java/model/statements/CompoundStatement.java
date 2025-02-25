package model.statements;

import exceptions.MyException;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.ProgramState;
import model.types.IType;

public class CompoundStatement implements IStatement {
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(this.firstStatement.deepCopy(), this.secondStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeTable));
    }

    @Override
    public String toString() {
        return "(" + firstStatement + "; " + secondStatement + ")";
    }
}
