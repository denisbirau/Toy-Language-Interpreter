package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    @Override
    public IValue getDefaultValue() {
        return new StringValue("empty");
    }

    @Override
    public StringType deepCopy() {
        return new StringType();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }
}
