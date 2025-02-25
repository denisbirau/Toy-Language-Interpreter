package model.types;

import model.values.IValue;
import model.values.IntValue;

public class IntType implements IType {
    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
