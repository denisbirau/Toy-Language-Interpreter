package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyICountDownLatchTable;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

public class NewLatchStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public NewLatchStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();
        MyICountDownLatchTable<Integer> latchTable = state.getLatchTable();

        IValue expressionValue = this.expression.evaluate(symbolTable, heap);
        if (!expressionValue.getType().equals(new IntType()))
            throw new StatementException(this.expression + " does not result in a value of int type!");

        IntValue intValue = (IntValue)expressionValue;

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " was not declared!");
        if (!symbolTable.get(this.varName).getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        int newAddress = latchTable.allocate(intValue.getValue());
        symbolTable.put(this.varName, new IntValue(newAddress));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(this.varName, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.varName + ", " + this.expression + ")";
    }
}
