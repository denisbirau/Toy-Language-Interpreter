package model.expressions;

import exceptions.ExpressionException;
import exceptions.MyException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.values.IValue;
import model.values.IntValue;
import model.types.IntType;

public class ArithmeticExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final char operator;

    public ArithmeticExpression(char operator, IExpression firstExpression, IExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        IValue first_value = this.firstExpression.evaluate(symbolTable, heap);
        if (!first_value.getType().equals(new IntType())) {
            throw new ExpressionException(this.secondExpression + " is not an integer expression!");
        }

        IValue second_value = this.secondExpression.evaluate(symbolTable, heap);
        if (!second_value.getType().equals(new IntType())) {
            throw new ExpressionException(this.firstExpression + " is not an integer expression!");
        }

        int firstInt = ((IntValue) first_value).getValue();
        int secondInt = ((IntValue) second_value).getValue();
        return switch (this.operator) {
            case '+' -> new IntValue(firstInt + secondInt);
            case '-' -> new IntValue(firstInt - secondInt);
            case '*' -> new IntValue(firstInt * secondInt);
            case '/' -> {
                if (secondInt == 0) {
                    throw new ExpressionException("Division by zero error!");
                }
                yield new IntValue(firstInt / secondInt);
            }
            default -> throw new ExpressionException("Invalid operator!");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(this.operator, this.firstExpression.deepCopy(), this.secondExpression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType firstType = this.firstExpression.typecheck(typeTable);
        if (!firstType.equals(new IntType()))
            throw new TypeException(this.firstExpression + " does not result in a value of int type!");

        IType secondType = this.secondExpression.typecheck(typeTable);
        if (!secondType.equals(new IntType()))
            throw new TypeException(this.secondExpression + " does not result in a value of int type!");

        return new IntType();
    }

    @Override
    public String toString() {
        return this.firstExpression + Character.toString(this.operator) + this.secondExpression;
    }
}
