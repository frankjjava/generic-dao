package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoException;

import java.util.HashMap;
import java.util.Map;

public class ParametersMapBuilder {

    private Map<String, Object> params;

    public static ParametersMapBuilder newBuilder() {
        return new ParametersMapBuilder();
    }

    /**
     *
     * @param columnName
     * @param columnData
     * @return
     * @param <T>
     */
    protected <T> ParametersMapBuilder addNameAndValue(String columnName, T columnData) {
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

    /**
     *
     * @return
     */
    public Map<String, Object> build() {
        return params;
    }
}
