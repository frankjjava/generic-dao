package otc.framework.generic.dao;

import java.util.Map;

public class CreateRecordDataBuilder extends ParametersMapBuilder {

    private CreateRecordDataBuilder() {}

    public static CreateRecordDataBuilder newBuilder() {
        return new CreateRecordDataBuilder();
    }

    /**
     *
     * @param columnName
     * @param columnData
     * @return
     * @param <T>
     */
    public <T> CreateRecordDataBuilder addColumData(String columnName, T columnData) {
        super.addNameAndValue(columnName, columnData);
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
