package model.statements;

import exceptions.MyException;
import model.adts.MyIDictionary;
import model.adts.MyIList;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.IType;
import model.values.IValue;

public class PrintStatement implements IStatement {
    private final IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIList<IValue> output = state.getOutput();
        IValue outputValue = this.expression.evaluate(state.getSymbolTable(), state.getHeap());
        output.add(outputValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        this.expression.typecheck(typeTable);
        return typeTable;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }
}
