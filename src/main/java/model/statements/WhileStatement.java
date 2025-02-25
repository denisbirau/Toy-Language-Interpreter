package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.expressions.IExpression;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class WhileStatement implements IStatement{
    private final IExpression condition;
    private final IStatement statement;

    public WhileStatement(IExpression condition, IStatement statement) {
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        IValue value = this.condition.evaluate(state.getSymbolTable(), state.getHeap());
        if (!value.getType().equals(new BoolType())) {
            throw new StatementException(this.condition + " does not result in a bool value!");
        }
        if (value.equals(new BoolValue(true))) {
            executionStack.push(this.deepCopy());
            executionStack.push(this.statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.condition.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType conditionType = this.condition.typecheck(typeTable);
        if (!conditionType.equals(new BoolType()))
            throw new TypeException(this.condition + " does not result in a value of bool type!");
        this.statement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }

    @Override
    public String toString() {
        return "while(" + this.condition + ") {" + this.statement + "}";
    }
}
