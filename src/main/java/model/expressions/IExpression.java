package model.expressions;

import exceptions.MyException;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.types.IType;
import model.values.IValue;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap<IValue> heap) throws MyException;
    IExpression deepCopy();
    IType typecheck(MyIDictionary<String, IType> typeTable) throws MyException;
}
