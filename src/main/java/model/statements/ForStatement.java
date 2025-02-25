package model.statements;

import exceptions.MyException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.types.IType;
import model.types.IntType;

public class ForStatement implements IStatement{
    private final String counter;
    private final IExpression expression1, expression2, expression3;
    private final IStatement statement;

    public ForStatement(String counter, IExpression expression1, IExpression expression2, IExpression expression3, IStatement statement) {
        this.counter = counter;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new CompoundStatement(
                new VariableDeclarationStatement(this.counter, new IntType()),
                new CompoundStatement(
                        new AssignStatement(this.counter, this.expression1),
                        new WhileStatement(new RelationalExpression("<", new VariableExpression(this.counter), this.expression2), new CompoundStatement(
                                this.statement,
                                new AssignStatement(this.counter, this.expression3)
                        ))
                )
        );
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(this.counter, this.expression1.deepCopy(), this.expression2.deepCopy(), this.expression3.deepCopy(), this.statement.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        typeTable.put(this.counter, new IntType());
        if (!typeTable.get(this.counter).equals(new IntType()))
            throw new TypeException(this.counter + " is not of int type!");
        if (!this.expression1.typecheck(typeTable).equals(new IntType()))
            throw new TypeException(this.expression1 + " does not result in a value of int type!");
        if (!this.expression2.typecheck(typeTable).equals(new IntType()))
            throw new TypeException(this.expression2 + " does not result in a value of int type!");
        if (!this.expression3.typecheck(typeTable).equals(new IntType()))
            throw new TypeException(this.expression3 + " does not result in a value of int type!");
        return typeTable;
    }

    @Override
    public String toString() {
        return "for(" + this.counter + "=" + this.expression1 + ";" + this.counter + "<" + this.expression2 + ";" + this.counter + "=" + this.expression3 + ") { " + this.statement + " }";
    }
}
