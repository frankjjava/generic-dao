package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoException;

import java.util.HashMap;
import java.util.Map;

public class CreateRecordDataBuilder {

    private Map<String, Object> params;

    private CreateRecordDataBuilder() {}

    public static CreateRecordDataBuilder createInstance() {
        return new CreateRecordDataBuilder();
    }

    public <T> CreateRecordDataBuilder addColumData(String columnName, T columnData) {
        if (columnName == null || columnName.trim().equals("")) {
            throw new GenericDaoException("Column-name cannot be null or empty !");
        }
        if (columnData == null) {
            throw new GenericDaoException("Column-data cannot be null !");
        }
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(columnName, columnData);
        return this;
    }

    public Map<String, Object> build() {
        return params;
    }
}
