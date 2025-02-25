package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType{
    private final IType innerType;

    public RefType(IType innerType) {
        this.innerType = innerType;
    }

    public IType getInnerType() {
        return this.innerType;
    }

    @Override
    public IValue getDefaultValue() {
        return new RefValue(0, this.innerType);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.innerType);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof RefType) {
            return this.innerType.equals(((RefType) object).innerType);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ref " + this.innerType;
    }
}
