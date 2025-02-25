package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.types.IType;
import model.values.IValue;

public class VariableDeclarationStatement implements IStatement {
    private final String name;
    private final IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if (symbolTable.containsKey(this.name)) {
            throw new StatementException(this.name + " is already declared!");
        }
        symbolTable.put(this.name, this.type.getDefaultValue());

        return null;
    }

    @Override
    public IStatement deepCopy() { return new VariableDeclarationStatement(this.name, this.type.deepCopy());}

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        typeTable.put(this.name, this.type);
        return typeTable;
    }

    @Override
    public String toString() { return this.type + " " + this.name; }
}
