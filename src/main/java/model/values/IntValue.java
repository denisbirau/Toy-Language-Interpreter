package model.values;

import model.types.IntType;
import model.types.IType;

public class IntValue implements IValue {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(this.value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof IntValue)) {
            return false;
        }
        return ((IntValue)object).value == this.value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
