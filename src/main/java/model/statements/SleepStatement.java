package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.types.IType;
import model.values.IValue;
import model.values.IntValue;

public class SleepStatement implements IStatement{
    private final IntValue seconds;

    public SleepStatement(IntValue seconds) {
        this.seconds = seconds;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        if (this.seconds.getValue() < 0)
            throw new StatementException(this.seconds + " can not be a negative number!");
        if (this.seconds.getValue() > 0)
            executionStack.push(new SleepStatement(new IntValue(this.seconds.getValue() - 1)));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(this.seconds);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "sleep(" + this.seconds + ")";
    }
}
