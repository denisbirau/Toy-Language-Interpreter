package model.statements;

import exceptions.MyException;
import exceptions.StatementException;
import javafx.util.Pair;
import model.ProgramState;
import model.adts.*;
import model.expressions.IExpression;
import model.types.IType;
import model.values.IValue;

import java.util.List;

public class CallStatement implements IStatement {
    private final String procedureName;
    private final List<IExpression> parameters;

    public CallStatement(String procedureName, List<IExpression> parameters) {
        this.procedureName = procedureName;
        this.parameters = parameters;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws MyException {
        MyIDictionary<String, IValue> symbolTable = currentState.getSymbolTable();
        MyIHeap<IValue> heap = currentState.getHeap();
        MyIProcedureTable<String, Pair<List<String>, IStatement>> procTable = currentState.getProcedureTable();

        if(!procTable.containsKey(procedureName)) {
            throw new StatementException("Call: Procedure not defined!");
        }

        List<String> procedureParameters = procTable.get(procedureName).getKey();
        IStatement procedureBody = procTable.get(procedureName).getValue();
        MyIDictionary<String, IValue> newSymbolTable = new MyDictionary<>();
        for (String parameter : procedureParameters) {
            int index = procedureParameters.indexOf(parameter);
            newSymbolTable.put(parameter, parameters.get(index).evaluate(symbolTable, heap));
        }
        currentState.getSymbolTables().push(newSymbolTable);
        currentState.getExecutionStack().push(new ReturnStatement());
        currentState.getExecutionStack().push(procedureBody);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeTable) throws MyException {
        return typeTable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("call ").append(this.procedureName).append("(");
        for (IExpression exp: this.parameters)
            sb.append(exp).append(", ");
        sb.replace(sb.toString().length() - 2, sb.toString().length(), ")");
        return sb.toString();
    }
}
