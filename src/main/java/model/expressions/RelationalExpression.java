package model.expressions;

import exceptions.ExpressionException;
import exceptions.MyException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.BoolType;
import model.types.IType;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IValue;
import model.values.IntValue;

public class RelationalExpression implements IExpression {
    private final String operator;
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    public RelationalExpression(String operator, IExpression firstExpression, IExpression secondExpression) {
        this.operator = operator;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        IValue firstValue = this.firstExpression.evaluate(symbolTable, heap);
        if (!firstValue.getType().equals(new IntType())) {
            throw new ExpressionException("First operand is not an integer!");
        }

        IValue secondValue = this.secondExpression.evaluate(symbolTable, heap);
        if (!secondValue.getType().equals(new IntType())) {
            throw new ExpressionException("Second operand is not an integer!");
        }

        int firstInt = ((IntValue) firstValue).getValue();
        int secondInt = ((IntValue) secondValue).getValue();
        return switch (this.operator) {
            case "<" -> new BoolValue(firstInt < secondInt);
            case "<=" -> new BoolValue(firstInt <= secondInt);
            case "==" -> new BoolValue(firstInt == secondInt);
            case "!=" -> new BoolValue(firstInt != secondInt);
            case ">" -> new BoolValue(firstInt > secondInt);
            case ">=" -> new BoolValue(firstInt >= secondInt);
            default -> throw new ExpressionException("Invalid operator!");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(this.operator, this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType firstType = this.firstExpression.typecheck(typeTable);
        if (!firstType.equals(new IntType()))
            throw new TypeException(this.firstExpression + " does not result in a value of bool type!");

        IType secondType = this.secondExpression.typecheck(typeTable);
        if (!secondType.equals(new IntType()))
            throw new TypeException(this.secondExpression + " does not result in a value of bool type!");

        return new BoolType();
    }

    @Override
    public String toString() {
        return this.firstExpression + this.operator + this.secondExpression;
    }
}
