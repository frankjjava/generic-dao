package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoValidationException;

public class Utility {


    public static void validate(String columnName) {
        if (null == columnName || columnName.trim().equals("")) {
            throw new GenericDaoValidationException("Column name cannot be empty.");
        }
    }

    public static <T> void validate(String columnName, T columnValue) {
        validate(columnName);
        if (null == columnValue || (columnValue instanceof String && ((String)columnValue).trim().equals(""))) {
            throw new GenericDaoValidationException("Column value cannot be empty.");
        }
        return;
    }

    public static void validateColumnAndParamName(String columnName, String paramName) {
        validate(columnName);
        if (null == paramName || paramName.trim().equals("")) {
            throw new GenericDaoValidationException("Param-name  cannot be empty.");
        }
        return;
    }

    public static void validateTableName(String tableName) {
        if (null == tableName || tableName.trim().equals("")) {
            throw new GenericDaoValidationException("Table name cannot be empty.");
        }
    }

}
