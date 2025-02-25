package model.expressions;

import exceptions.ExpressionException;
import exceptions.MyException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.values.IValue;
import model.values.BoolValue;
import model.types.BoolType;

public class LogicExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final char operator;

    public LogicExpression(char operator, IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        IValue first_value = this.firstExpression.evaluate(symbolTable, heap);
        if (!first_value.getType().equals(new BoolType()))
            throw new ExpressionException("The first operand is not a boolean expression!");

        IValue second_value = this.secondExpression.evaluate(symbolTable, heap);
        if (!second_value.getType().equals(new BoolType()))
            throw new ExpressionException("The second operand is not a boolean expression!");

        boolean first_boolean = ((BoolValue) first_value).getValue();
        boolean second_boolean = ((BoolValue) second_value).getValue();
        return switch (this.operator) {
            case '&' -> new BoolValue(first_boolean && second_boolean);
            case '|' -> new BoolValue(first_boolean || second_boolean);
            default -> throw new ExpressionException("Invalid operator!");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new LogicExpression(this.operator, this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType firstType = this.firstExpression.typecheck(typeTable);
        if (!firstType.equals(new BoolType()))
            throw new TypeException(this.firstExpression + " does not result in a value of bool type!");

        IType secondType = this.secondExpression.typecheck(typeTable);
        if (!secondType.equals(new BoolType()))
            throw new TypeException(this.secondExpression + " does not result in a value of bool type!");

        return new BoolType();
    }

    @Override
    public String toString() {
        return this.firstExpression + Character.toString(this.operator) + this.secondExpression;
    }
}
