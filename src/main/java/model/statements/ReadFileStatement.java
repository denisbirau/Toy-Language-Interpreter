package model.statements;

import exceptions.MyException;
import exceptions.FileException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.types.IType;
import model.types.IntType;
import model.types.StringType;
import model.values.IValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private final IExpression expression;
    private final String varName;

    public ReadFileStatement(IExpression expression, String name) {
        this.expression = expression;
        this.varName = name;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symbolTable.containsKey(this.varName)) {
            throw new StatementException(this.varName + " is not defined in the symbol table!");
        }

        IValue varValue = symbolTable.get(this.varName);
        if (!varValue.getType().equals(new IntType())) {
            throw new StatementException(this.varName + " is not of int type!");
        }

        IValue expressionValue = this.expression.evaluate(symbolTable, state.getHeap());
        if (!expressionValue.getType().equals(new StringType())) {
            throw new StatementException(this.expression + " does not result in a value of string type!");
        }

        StringValue stringValue = (StringValue) expressionValue;
        if (!fileTable.containsKey(stringValue)) {
            throw new StatementException(stringValue + " is not open!");
        }

        try {
            BufferedReader bufferedReader = fileTable.get(stringValue);
            String line = bufferedReader.readLine();
            IntValue intValue;
            if (line == null) {
                intValue = new IntValue(0);
            } else {
                intValue = new IntValue(Integer.parseInt(line));
            }
            symbolTable.put(this.varName, intValue);
        } catch (IOException exception) {
            throw new FileException(exception.getMessage());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(this.expression.deepCopy(), this.varName);
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType varType = typeTable.get(this.varName);
        if (!varType.equals(new IntType()))
            throw new TypeException(this.varName + " is not of int type!");
        IType expressionType = this.expression.typecheck(typeTable);
        if (!expressionType.equals(new StringType()))
            throw new TypeException(this.expression + " does not result in a value of string type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "read(" + this.expression + ", " + this.varName + ")";
    }
}
