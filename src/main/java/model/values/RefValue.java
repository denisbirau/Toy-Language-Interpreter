package model.values;

import model.types.IType;
import model.types.RefType;

public class RefValue implements IValue{
    private final int address;
    private final IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public IType getType() {
        return new RefType(this.locationType);
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType);
    }

    @Override
    public String toString() {
        return "(" + this.address + ": " + this.locationType + ")";
    }
}
