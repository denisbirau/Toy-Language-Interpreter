package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(this.value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BoolValue)) {
            return false;
        }
        return ((BoolValue) object).getValue() == this.value;
    }

    @Override
    public String toString() {
        return Boolean.toString(this.value);
    }
}
