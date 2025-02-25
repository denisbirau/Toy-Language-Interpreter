package model.expressions;

import exceptions.MyException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        return this.value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(this.value.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return this.value.getType();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
