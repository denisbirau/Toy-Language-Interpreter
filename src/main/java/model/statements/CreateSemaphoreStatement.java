package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.adts.MyISemaphoreTable;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.values.IValue;
import model.values.IntValue;

import java.util.ArrayList;
import java.util.List;

public class CreateSemaphoreStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public CreateSemaphoreStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();
        MyISemaphoreTable<Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if (!symbolTable.containsKey(this.varName))
            throw new StatementException(this.varName + " is not declared!");
        if (!symbolTable.get(this.varName).getType().equals(new IntType()))
            throw new StatementException(this.varName + " is not of int type!");

        IValue value = this.expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(new IntType()))
            throw new StatementException(this.expression + " does not result in an int type!");

        int intValue = ((IntValue) value).getValue();
        semaphoreTable.allocate(new Pair<>(intValue, new ArrayList<>()));
        symbolTable.put(this.varName, new IntValue(intValue));

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStatement(this.varName, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!typeTable.get(this.varName).equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        if (!this.expression.typecheck(typeTable).equals(new IntType()))
            throw new TypeException(this.expression + " does not result in a value of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + this.varName + ", " + this.expression + ")";
    }
}
