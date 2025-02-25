package model.expressions;

import exceptions.ExpressionException;
import exceptions.MyException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class ReadHeapExpression implements IExpression{
    private final IExpression expression;

    public ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        IValue value = this.expression.evaluate(symbolTable, heap);
        if (!(value instanceof RefValue)) {
            throw new ExpressionException("Value is not of reference type!");
        }
        int address = ((RefValue) value).getAddress();
        if (!heap.containsKey(address)) {
            throw new ExpressionException("Value was not allocated!");
        }
        return heap.get(address);
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(this.expression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType expressionType = this.expression.typecheck(typeTable);
        if (!(expressionType instanceof RefType))
            throw new TypeException(this.expression + " does not result in a value of reference type!");

        return ((RefType) expressionType).getInnerType();
    }

    @Override
    public String toString() {
        return "readHeap(" + this.expression + ")";
    }
}
