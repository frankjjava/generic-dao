package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class DeleteStatementBuilder extends WhereClauseBuilder {

    private StringBuilder deleteStatement;
    private enum LEVEL {TABLENAME_ADDED, WHERE_ADDED, CONDITION_ADDED}
    private LEVEL level;

    private DeleteStatementBuilder() {}

    public static DeleteStatementBuilder createInstance() {
        return new DeleteStatementBuilder();
    }

    public DeleteStatementBuilder deleteTable(String tableName) {
        if (null != level) {
            throw new GenericDaoBuilderException(String.format("Invalid repeat-call to 'deleteTable(%s)' not allowed. " +
                    "Call updateTable(...) first", tableName));
        }
        Utility.validateTableName(tableName);
        initDeleteClause();
        deleteStatement.append(tableName);
        level = LEVEL.TABLENAME_ADDED;
        return this;
    }

    public String build() {
        if (deleteStatement == null) {
            return null;
        }
        if (LEVEL.TABLENAME_ADDED != level && LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Delete statement created not in a required state for call to build() "));
        }
        if (LEVEL.TABLENAME_ADDED == level) {
            return deleteStatement.toString();
        }
        return deleteStatement.append(super.build()).toString();
    }

    private boolean initDeleteClause() {
        if (deleteStatement == null) {
            deleteStatement = new StringBuilder(BaseDao.DELETE)
                    .append(BaseDao.FROM);
            return true;
        }
        return false;
    }

    public DeleteStatementBuilder where() {
        if (LEVEL.TABLENAME_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Delete statement not created in required state for call to where() "));
        }
        level = LEVEL.WHERE_ADDED;
        return this;
    }

    public <T> DeleteStatementBuilder equals(String columnName, T columnValue) {
        isWhereCalled();
        super.equals(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder equalsNamedCriteria(String columnName) {
        isWhereCalled();
        super.equalsNamedCriteria(columnName);
        return this;
    }

    public <T> DeleteStatementBuilder notEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.notEquals(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder notEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.notEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> DeleteStatementBuilder greaterThan(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThan(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder greaterThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanNamedCriteria(columnName);
        return this;
    }

    public <T> DeleteStatementBuilder greaterThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThanEquals(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder greaterThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> DeleteStatementBuilder lessThan(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThan(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder lessThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanNamedCriteria(columnName);
        return this;
    }

    public <T> DeleteStatementBuilder lessThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThanEquals(columnName, columnValue);
        return this;
    }

    public <T> DeleteStatementBuilder lessThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanEqualsNamedCriteria(columnName);
        return this;
    }

    public DeleteStatementBuilder and() {
        isConditionAdded();
        super.and();
        return this;
    }

    public DeleteStatementBuilder or() {
        isConditionAdded();
        super.or();
        return this;
    }

    public DeleteStatementBuilder not() {
        isConditionAdded();
        super.not();
        return this;
    }

    public <T> DeleteStatementBuilder between(String columnName, T beginValue, T endValue) {
        super.between(columnName, beginValue, endValue);
        return this;
    }

    private boolean isWhereCalled() {
        if (level != LEVEL.WHERE_ADDED) {
            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
        }
        return true;
    }

    private boolean isConditionAdded() {
        if (level != LEVEL.CONDITION_ADDED) {
            throw new GenericDaoBuilderException(String.format("Query created not in required state for call to this method "));
        }
        return true;
    }

}
