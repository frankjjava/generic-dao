package otc.framework.generic.dao;

import java.util.Map;

public class NamedParameterArgumentsBuilder extends ParametersMapBuilder {

    protected NamedParameterArgumentsBuilder() {}

    public static NamedParameterArgumentsBuilder newBuilder() {
        return new NamedParameterArgumentsBuilder();
    }

    /**
     *
     * @param name
     * @param value
     * @return
     * @param <T>
     */
    public <T> NamedParameterArgumentsBuilder addNameAndValue(String name, T value) {
        super.addNameAndValue(name, value);
        return this;
    }

    /**
     *
     * @return
     */
    public Map<String, Object> build() {
        return super.build();
    }
}
