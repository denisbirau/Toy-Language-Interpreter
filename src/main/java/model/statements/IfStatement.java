package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.adts.MyIStack;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.BoolType;
import model.types.IType;
import model.values.BoolValue;
import model.values.IValue;

public class IfStatement implements IStatement {
    private final IExpression condition;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.condition = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IValue value = this.condition.evaluate(symbolTable, state.getHeap());
        if (!value.getType().equals(new BoolType())) {
            throw new StatementException(this.condition + " does not result a boolean value!");
        }
        if (value.equals(new BoolValue(true))) {
            executionStack.push(this.thenStatement);
        } else {
            executionStack.push(this.elseStatement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.condition.deepCopy(), this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType conditionType = this.condition.typecheck(typeTable);
        if (!conditionType.equals(new BoolType()))
            throw new TypeException(this.condition + " does not result in a value of bool type!");
        this.thenStatement.typeCheck(typeTable.deepCopy());
        this.elseStatement.typeCheck(typeTable.deepCopy());
        return typeTable;
    }

    @Override
    public String toString() {
        return "if (" + this.condition +
                ") then (" + this.thenStatement +
                ") else (" + this.elseStatement + ")";
    }
}
