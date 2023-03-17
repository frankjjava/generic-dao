package otc.framework.generic.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamedParameterArgumentsArrayBuilder {

    private List<Map<String, Object>> paramsList;

    private NamedParameterArgumentsBuilder namedParameterArgumentsBuilder;

    private NamedParameterArgumentsArrayBuilder() {

    }

    /**
     *
     * @return
     */
    public static NamedParameterArgumentsArrayBuilder newBuilder() {
        return new NamedParameterArgumentsArrayBuilder();
    }

    /**
     *
     * @param name
     * @param value
     * @return
     * @param <T>
     */
    public <T> NamedParameterArgumentsArrayBuilder addNameAndValue(String name, T value) {
        if (namedParameterArgumentsBuilder == null) {
            namedParameterArgumentsBuilder = NamedParameterArgumentsBuilder.newBuilder();
        }
        namedParameterArgumentsBuilder.addNameAndValue(name, value);
        return this;
    }

    /**
     *
     * @return
     */
    public NamedParameterArgumentsArrayBuilder newSet() {
        if (namedParameterArgumentsBuilder == null) {
            namedParameterArgumentsBuilder = NamedParameterArgumentsBuilder.newBuilder();
            return this;
        }
        if (paramsList == null) {
            paramsList =  new ArrayList<>();
        }
        paramsList.add(namedParameterArgumentsBuilder.build());
        namedParameterArgumentsBuilder = NamedParameterArgumentsBuilder.newBuilder();
        return this;
    }

    /**
     *
     * @return
     */
    public Map<String, Object>[] build() {
        this.paramsList.add(this.namedParameterArgumentsBuilder.build());
        return paramsList.toArray(new HashMap[paramsList.size()]);
    }

}
