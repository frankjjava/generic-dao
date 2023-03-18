package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class UpsertStatementBuilder extends InsertStatementBuilder {

    private StringBuilder upsertStatement;
    private enum LEVEL {TABLENAME_ADDED, COLUMN_ADDED, VALUES_ADDED, VALUE_ADDED, WHERE_ADDED, CONDITION_ADDED,
                ON_CONFLICT_COLUMNS_ADDED, DO_UPDATE_SET_ADDED, SET_ADDED}
    private LEVEL level;
    private boolean hasWhereClause;

    private UpsertStatementBuilder() {}

    public static UpsertStatementBuilder newBuilder() {
        return new UpsertStatementBuilder();
    }

    public UpsertStatementBuilder into(String tableName) {
        super.into(tableName);
//        if (null != level) {
//            throw new GenericDaoBuilderException(String.format("Repeat call to 'deleteTable(%s)' is not allowed. ", tableName));
//        }
//        Utility.validateTableName(tableName);
        initUpsertClause();
//        upsertStatement.append(tableName);
        level = LEVEL.TABLENAME_ADDED;
        return this;
    }

    public String build() {
        if (upsertStatement == null) {
            throw new GenericDaoBuilderException(String.format("Upsert statement nothing to build(). level = %s", level));
        }
        if (LEVEL.VALUE_ADDED != level && LEVEL.CONDITION_ADDED != level && LEVEL.SET_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Upsert statement created not in a required state for call to build(). level = %s", level));
        }
        if (LEVEL.VALUE_ADDED == level) {
            upsertStatement.append(BaseDao.CLOSE_PARANTHESIS);
        }
        return upsertStatement.toString();
    }

    private boolean initUpsertClause() {
        if (upsertStatement == null) {
            upsertStatement = new StringBuilder();
            return true;
        }
        return false;
    }

    public UpsertStatementBuilder onConflict(String columnName) {
        if (LEVEL.VALUE_ADDED != level && LEVEL.ON_CONFLICT_COLUMNS_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Upsert statement not in required state to call onConflict(%s). level = %s", columnName, level));
        }
        Utility.validate(columnName);
        if (LEVEL.VALUE_ADDED == level) {
            upsertStatement.append(super.build());
            if (hasWhereClause) {
                upsertStatement.append(super.build());
            }
            upsertStatement.append(BaseDao.ON_CONFLICT);
        } else if (LEVEL.ON_CONFLICT_COLUMNS_ADDED == level) {
            upsertStatement.append(BaseDao.COMMA);
        }
        upsertStatement.append(columnName);
        level = LEVEL.ON_CONFLICT_COLUMNS_ADDED;
        return this;
    }

    public UpsertStatementBuilder doUpdateSet() {
        if (LEVEL.ON_CONFLICT_COLUMNS_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Upsert statement not created in required state for call to doUpdateSet(). level = %s", level));
        }
        upsertStatement.append(BaseDao.CLOSE_PARANTHESIS)
                .append(BaseDao.DO_UPDATE_SET);
        level = LEVEL.DO_UPDATE_SET_ADDED;
        return this;
    }

    public <T> UpsertStatementBuilder set(String columnName, T columnValue) {
        if (LEVEL.DO_UPDATE_SET_ADDED != level && LEVEL.SET_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Upsert statement not in required state for call set(%s, %s). level = %s",
                    columnName, columnValue, level));
        }
        Utility.validate(columnName, columnValue);
        if (LEVEL.SET_ADDED == level) {
            upsertStatement.append(BaseDao.COMMA);
        }
        upsertStatement.append(columnName)
                .append(TOKENS.EQUALS)
                .append(BaseDao.APOSTROPHE)
                .append(columnValue)
                .append(BaseDao.APOSTROPHE);
        level = LEVEL.SET_ADDED;
        return this;
    }

    public <T> UpsertStatementBuilder setForNamedParameter(String columnName, String paramName) {
        if (LEVEL.DO_UPDATE_SET_ADDED != level && LEVEL.SET_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Upsert statement not in required state for call setForNamedParameter(%s, %s). level = %s",
                    columnName, paramName, level));
        }
        Utility.validateColumnAndParamName(columnName, paramName);
        if (LEVEL.SET_ADDED == level) {
            upsertStatement.append(BaseDao.COMMA);
        }
        upsertStatement.append(columnName)
                .append(TOKENS.EQUALS)
                .append(BaseDao.COLON)
                .append(paramName);
        level = LEVEL.SET_ADDED;
        return this;
    }


    public UpsertStatementBuilder column(String columnName) {
        super.column(columnName);
//        if (InsertStatementBuilder.LEVEL.TABLENAME_ADDED != level && InsertStatementBuilder.LEVEL.COLUMN_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format(
//                    "Insert statement not in required state to call column(%s). level = %s", columnName, level));
//        }
//        Utility.validate(columnName);
//        if (InsertStatementBuilder.LEVEL.TABLENAME_ADDED == level) {
//            insertStatement.append(BaseDao.OPEN_PARANTHESIS);
//        } else {
//            insertStatement.append(BaseDao.COMMA);
//        }
//        insertStatement.append(columnName);
        level = LEVEL.COLUMN_ADDED;
        return this;
    }

    public UpsertStatementBuilder values() {
        super.values();
//        if (InsertStatementBuilder.LEVEL.COLUMN_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format(
//                    "Insert statement not in required state to call values(). level = %s", level));
//        }
//        insertStatement.append(BaseDao.CLOSE_PARANTHESIS)
//                .append(BaseDao.VALUES);
        level = LEVEL.VALUES_ADDED;
        return this;
    }

    public UpsertStatementBuilder value() {
        super.value();
//        if (true) {
//            throw new GenericDaoBuilderException("Not implemented.");
//        }
//        if (InsertStatementBuilder.LEVEL.VALUES_ADDED != level && InsertStatementBuilder.LEVEL.VALUE_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format(
//                    "Insert statement not in required state to call value(%s). level = %s",
//                    level));
//        }
//        if (InsertStatementBuilder.LEVEL.VALUE_ADDED == level) {
//            insertStatement.append(BaseDao.COMMA);
//        }
//        insertStatement.append(BaseDao.QUESTION);
        level = LEVEL.VALUE_ADDED;
        return this;
    }

    public UpsertStatementBuilder namedValue(String columnName) {
        super.namedValue(columnName);
//        if (InsertStatementBuilder.LEVEL.VALUES_ADDED != level && InsertStatementBuilder.LEVEL.VALUE_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format(
//                    "Insert statement not in required state to call namedValue(%s). level = %s",
//                    columnName, level));
//        }
//        Utility.validate(columnName);
//        if (InsertStatementBuilder.LEVEL.VALUE_ADDED == level) {
//            insertStatement.append(BaseDao.COMMA);
//        }
//        insertStatement.append(BaseDao.COLON)
//                .append(columnName);
        level = LEVEL.VALUE_ADDED;
        return this;
    }

    public UpsertStatementBuilder where() {
        super.where();
//        if (InsertStatementBuilder.LEVEL.COLUMN_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format(
//                    "Insert statement not created in required state for call to where(). level = %s", level));
//        }
        level = LEVEL.WHERE_ADDED;
        hasWhereClause = true;
        return this;
    }

    public <T> UpsertStatementBuilder equals(String columnName, T columnValue) {
//        isWhereCalled();
        super.equals(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder equalsNamedCriteria(String columnName) {
//        isWhereCalled();
        super.equalsNamedCriteria(columnName);
        return this;
    }

    public <T> UpsertStatementBuilder notEquals(String columnName, T columnValue) {
//        isWhereCalled();
        super.notEquals(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder notEqualsNamedCriteria(String columnName) {
//        isWhereCalled();
        super.notEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> UpsertStatementBuilder greaterThan(String columnName, T columnValue) {
//        isWhereCalled();
        super.greaterThan(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder greaterThanNamedCriteria(String columnName) {
//        isWhereCalled();
        super.greaterThanNamedCriteria(columnName);
        return this;
    }

    public <T> UpsertStatementBuilder greaterThanEquals(String columnName, T columnValue) {
//        isWhereCalled();
        super.greaterThanEquals(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder greaterThanEqualsNamedCriteria(String columnName) {
//        isWhereCalled();
        super.greaterThanEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> UpsertStatementBuilder lessThan(String columnName, T columnValue) {
//        isWhereCalled();
        super.lessThan(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder lessThanNamedCriteria(String columnName) {
//        isWhereCalled();
        super.lessThanNamedCriteria(columnName);
        return this;
    }

    public <T> UpsertStatementBuilder lessThanEquals(String columnName, T columnValue) {
//        isWhereCalled();
        super.lessThanEquals(columnName, columnValue);
        return this;
    }

    public <T> UpsertStatementBuilder lessThanEqualsNamedCriteria(String columnName) {
//        isWhereCalled();
        super.lessThanEqualsNamedCriteria(columnName);
        return this;
    }

    public UpsertStatementBuilder and() {
//        isConditionAdded();
        super.and();
        return this;
    }

    public UpsertStatementBuilder or() {
//        isConditionAdded();
        super.or();
        return this;
    }

    public UpsertStatementBuilder not() {
//        isConditionAdded();
        super.not();
        return this;
    }

    public <T> UpsertStatementBuilder between(String columnName, T beginValue, T endValue) {
        super.between(columnName, beginValue, endValue);
        return this;
    }

//    private boolean isWhereCalled() {
//        if (LEVEL.WHERE_ADDED != level && LEVEL.CONDITION_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
//        }
//        return true;
//    }

//    private boolean isConditionAdded() {
//        if (LEVEL.CONDITION_ADDED != level) {
//            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
//        }
//        return true;
//    }

}
