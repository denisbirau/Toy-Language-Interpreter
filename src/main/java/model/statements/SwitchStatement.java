package model.statements;

import exceptions.MyException;
import exceptions.TypeException;
import model.ProgramState;
import model.adts.MyIDictionary;
import model.expressions.IExpression;
import model.expressions.RelationalExpression;
import model.types.IType;

public class SwitchStatement implements IStatement {
    private final IExpression expression1, expression2, expression3;
    private final IStatement statement1, statement2, statement3;

    public SwitchStatement(IExpression expression1, IExpression expression2, IStatement statement1, IExpression expression3, IStatement statement2, IStatement statement3) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement1 = statement1;
        this.statement2 = statement2;
        this.statement3 = statement3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStatement newStatement = new IfStatement(
                new RelationalExpression("==", this.expression1, this.expression2),
                this.statement1,
                new IfStatement(
                        new RelationalExpression("==", this.expression1, this.expression3),
                        this.statement2,
                        this.statement3
                )
        );
        state.getExecutionStack().push(newStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(this.expression1.deepCopy(), this.expression2.deepCopy(), this.statement1.deepCopy(), this.expression3.deepCopy(), this.statement2.deepCopy(), this.statement3.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        if (!(this.expression1.typecheck(typeTable).equals(this.expression2.typecheck(typeTable)) && this.expression1.typecheck(typeTable).equals(this.expression3.typecheck(typeTable))))
            throw new TypeException("Switch expressions are not the same type!");
        this.statement1.typeCheck(typeTable);
        this.statement2.typeCheck(typeTable);
        this.statement3.typeCheck(typeTable);
        return typeTable;
    }

    @Override
    public String toString() {
        return "switch(" + this.expression1 + ") (case " + this.expression2 + ": " + this.statement1 + ") (case " + this.expression3 + ": " + this.statement2 + ") (default: " + this.statement3 + ")";
    }
}
