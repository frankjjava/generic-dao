package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class InsertStatementBuilder extends WhereClauseBuilder {

    private StringBuilder insertStatement;
    private enum LEVEL {TABLENAME_ADDED, COLUMN_ADDED, VALUES_ADDED, VALUE_ADDED, WHERE_ADDED, CONDITION_ADDED,
                SET_ADDED}
    private LEVEL level;
    private boolean hasWhereClause;

    InsertStatementBuilder() {}

    public static InsertStatementBuilder newBuilder() {
        return new InsertStatementBuilder();
    }

    public InsertStatementBuilder into(String tableName) {
        if (null != level) {
            throw new GenericDaoBuilderException(String.format("Repeat call to 'deleteTable(%s)' is not allowed. ", tableName));
        }
        Utility.validateTableName(tableName);
        initInsertClause();
        insertStatement.append(tableName);
        level = LEVEL.TABLENAME_ADDED;
        return this;
    }

    public String build() {
        if (insertStatement == null) {
            throw new GenericDaoBuilderException(String.format("Insert statement nothing to build(). level = %s", level));
        }
        if (LEVEL.VALUE_ADDED != level && LEVEL.CONDITION_ADDED != level && LEVEL.SET_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement created not in a required state for call to build(). level = %s", level));
        }
        if (LEVEL.VALUE_ADDED == level) {
            insertStatement.append(BaseDao.CLOSE_PARANTHESIS);
        }
        return insertStatement.toString();
    }

    private boolean initInsertClause() {
        if (insertStatement == null) {
            insertStatement = new StringBuilder(BaseDao.INSERT_INTO);
            return true;
        }
        return false;
    }

    public InsertStatementBuilder column(String columnName) {
        if (LEVEL.TABLENAME_ADDED != level && LEVEL.COLUMN_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement not in required state to call column(%s). level = %s", columnName, level));
        }
        Utility.validate(columnName);
        if (LEVEL.TABLENAME_ADDED == level) {
            insertStatement.append(BaseDao.OPEN_PARANTHESIS);
        } else {
            insertStatement.append(BaseDao.COMMA);
        }
        insertStatement.append(columnName);
        level = LEVEL.COLUMN_ADDED;
        return this;
    }

    public InsertStatementBuilder values() {
        if (LEVEL.COLUMN_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement not in required state to call values(). level = %s", level));
        }
        insertStatement.append(BaseDao.CLOSE_PARANTHESIS)
                .append(BaseDao.VALUES);
        level = LEVEL.VALUES_ADDED;
        return this;
    }

    public InsertStatementBuilder value() {
        if (true) {
            throw new GenericDaoBuilderException("Not implemented.");
        }
        if (LEVEL.VALUES_ADDED != level && LEVEL.VALUE_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement not in required state to call value(%s). level = %s",
                    level));
        }
        if (LEVEL.VALUE_ADDED == level) {
            insertStatement.append(BaseDao.COMMA);
        }
        insertStatement.append(BaseDao.QUESTION);
        level = LEVEL.VALUE_ADDED;
        return this;
    }

    public InsertStatementBuilder namedValue(String columnName) {
        if (LEVEL.VALUES_ADDED != level && LEVEL.VALUE_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement not in required state to call namedValue(%s). level = %s",
                    columnName, level));
        }
        Utility.validate(columnName);
        if (LEVEL.VALUE_ADDED == level) {
            insertStatement.append(BaseDao.COMMA);
        }
        insertStatement.append(BaseDao.COLON)
                .append(columnName);
        level = LEVEL.VALUE_ADDED;
        return this;
    }

    public InsertStatementBuilder where() {
        if (LEVEL.COLUMN_ADDED != level) {
            throw new GenericDaoBuilderException(String.format(
                    "Insert statement not created in required state for call to where(). level = %s", level));
        }
        level = LEVEL.WHERE_ADDED;
        hasWhereClause = true;
        return this;
    }

    public <T> InsertStatementBuilder equals(String columnName, T columnValue) {
        isWhereCalled();
        super.equals(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder equalsNamedCriteria(String columnName) {
        isWhereCalled();
        super.equalsNamedCriteria(columnName);
        return this;
    }

    public <T> InsertStatementBuilder notEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.notEquals(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder notEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.notEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> InsertStatementBuilder greaterThan(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThan(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder greaterThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanNamedCriteria(columnName);
        return this;
    }

    public <T> InsertStatementBuilder greaterThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThanEquals(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder greaterThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> InsertStatementBuilder lessThan(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThan(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder lessThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanNamedCriteria(columnName);
        return this;
    }

    public <T> InsertStatementBuilder lessThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThanEquals(columnName, columnValue);
        return this;
    }

    public <T> InsertStatementBuilder lessThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanEqualsNamedCriteria(columnName);
        return this;
    }

    public InsertStatementBuilder and() {
        isConditionAdded();
        super.and();
        return this;
    }

    public InsertStatementBuilder or() {
        isConditionAdded();
        super.or();
        return this;
    }

    public InsertStatementBuilder not() {
        isConditionAdded();
        super.not();
        return this;
    }

    public <T> InsertStatementBuilder between(String columnName, T beginValue, T endValue) {
        super.between(columnName, beginValue, endValue);
        return this;
    }

    private boolean isWhereCalled() {
        if (LEVEL.WHERE_ADDED != level && LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
        }
        return true;
    }

    private boolean isConditionAdded() {
        if (LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
        }
        return true;
    }

}
