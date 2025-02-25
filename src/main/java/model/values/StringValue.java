package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof StringValue)) {
            return false;
        }
        StringValue value = (StringValue) object;
        return this.value.equals(value.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
