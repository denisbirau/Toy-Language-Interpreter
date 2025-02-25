package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.ProgramState;
import model.types.IType;
import model.values.IValue;

public class AssignStatement implements IStatement {
    private final String varName;
    private final IExpression expression;

    public AssignStatement(String id, IExpression expression) {
        this.varName = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (!symbolTable.containsKey(this.varName)) {
            throw new StatementException(this.varName + " is not declared!");
        }

        IValue value = this.expression.evaluate(symbolTable, state.getHeap());
        IType varType = symbolTable.get(this.varName).getType();
        if (!value.getType().equals(varType)) {
            throw new StatementException(value + " can't be of type " + varType + "!");
        }

        symbolTable.put(this.varName, value);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(this.varName, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType varType = typeTable.get(this.varName);
        IType expressionType = this.expression.typecheck(typeTable);
        if (!varType.equals(expressionType))
            throw new TypeException(this.expression + "'s type and " + this.varName + "'s type don't match!");
        return typeTable;
    }

    @Override
    public String toString() {
        return varName + "=" + expression;
    }
}
