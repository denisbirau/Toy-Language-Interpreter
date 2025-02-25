package model.expressions;

import exceptions.MyException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.values.IValue;

public class VariableExpression implements IExpression {
    private final String varName;

    public VariableExpression(String id) {
        this.varName = id;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException {
        return symbolTable.get(this.varName);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(this.varName);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable.get(this.varName);
    }

    @Override
    public String toString() {
        return this.varName;
    }
}
