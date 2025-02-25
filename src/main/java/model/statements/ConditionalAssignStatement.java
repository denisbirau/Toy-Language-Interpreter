package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.types.IType;

public class ConditionalAssignStatement implements IStatement{
    private final String varName;
    private final IExpression condition, firstExpression, secondExpression;

    public ConditionalAssignStatement(String varName, IExpression condition, IExpression firstExpression, IExpression secondExpression) {
        this.varName = varName;
        this.condition = condition;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        state.getExecutionStack().push(new IfStatement(this.condition, new AssignStatement(this.varName, this.firstExpression), new AssignStatement(this.varName, this.secondExpression)));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignStatement(this.varName, this.condition.deepCopy(), this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        return this.varName + "=" + this.condition + "?" + this.firstExpression + ":" + this.secondExpression;
    }
}
