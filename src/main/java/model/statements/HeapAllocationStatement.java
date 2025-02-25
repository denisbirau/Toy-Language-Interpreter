package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.adts.MyIHeap;
import model.expressions.IExpression;
import model.types.IType;
import model.types.RefType;
import model.values.IValue;
import model.values.RefValue;

public class HeapAllocationStatement implements IStatement{
    private final String varName;
    private final IExpression expression;

    public HeapAllocationStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();

        if (!symbolTable.containsKey(this.varName)) {
            throw new StatementException(this.varName + " is not declared!");
        }
        IValue oldValue = symbolTable.get(this.varName);
        if (!(oldValue.getType() instanceof RefType)) {
            throw new StatementException(this.varName + " is not of reference type!");
        }

        IValue newValue = this.expression.evaluate(symbolTable, state.getHeap());
        if (!(newValue.getType().equals(((RefType) oldValue.getType()).getInnerType()))) {
            throw new StatementException(this.expression + " can't result a " + oldValue.getType() + " type!");
        }

        int address = heap.allocate(newValue);
        symbolTable.put(this.varName, new RefValue(address, newValue.getType()));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(this.varName, this.expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        IType varType = typeTable.get(this.varName);
        IType expressionType = this.expression.typecheck(typeTable);
        if (!varType.equals(new RefType(expressionType)))
            throw new TypeException(this.varName + "'s inner type and " + this.expression + "'s type don't match!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "new(" + this.varName + ", " + this.expression + ")";
    }
}
