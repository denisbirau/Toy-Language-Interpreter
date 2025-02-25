package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.adts.MyStack;
import model.types.IType;
import model.values.IValue;

public class ForkStatement implements IStatement{
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> newExecutionStack = new MyStack<>();
        newExecutionStack.push(this.statement);
        MyIStack<MyIDictionary<String, IValue>> symbolTables = state.getSymbolTables();
        MyIStack<MyIDictionary<String, IValue>> newSymbolTables = new MyStack<>();

        for (MyIDictionary<String, IValue> dict: symbolTables.getElements().reversed())
            newSymbolTables.push(dict.deepCopy());

        ProgramState newProgramState = new ProgramState(
                newExecutionStack,
                newSymbolTables,
                state.getOutput(),
                state.getFileTable(),
                state.getHeap(),
                state.getLatchTable(),
                state.getLockTable(),
                state.getProcedureTable(),
                state.getSemaphoreTable()
        );
        newProgramState.setId();

        return newProgramState;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        this.statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement + ")";
    }
}
