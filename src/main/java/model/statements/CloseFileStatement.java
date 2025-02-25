package model.statements;

import exceptions.FileException;
import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.types.IType;
import model.types.StringType;
import model.values.IValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement{
    private final IExpression expression;

    public CloseFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = this.expression.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new StringType())) {
            throw new StatementException(this.expression + " does not result in a value of string type!");
        }

        StringValue stringValue = (StringValue) value;
        if (!fileTable.containsKey(stringValue)) {
            throw new StatementException(stringValue + " is not open!");
        }

        try {
            BufferedReader bufferedReader = fileTable.get(stringValue);
            bufferedReader.close();
            fileTable.remove(stringValue, bufferedReader);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseFileStatement(this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType expressionType = this.expression.typecheck(typeTable);
        if (!expressionType.equals(new StringType()))
            throw new TypeException(this.expression + " does not result in a value of string type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "close(" + this.expression + ")";
    }
}
