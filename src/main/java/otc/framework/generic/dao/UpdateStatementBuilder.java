package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class UpdateStatementBuilder extends WhereClauseBuilder {

    private StringBuilder updateStatement;
    private enum LEVEL {TABLENAME_ADDED, SET_ADDED, WHERE_ADDED, CONDITION_ADDED}
    private UpdateStatementBuilder.LEVEL level;

    private UpdateStatementBuilder() {}

    public static UpdateStatementBuilder createInstance() {
        return new UpdateStatementBuilder();
    }

    public UpdateStatementBuilder updateTable(String tableName) {
        if (null != level) {
            throw new GenericDaoBuilderException(String.format("Invalid repeat-call to 'updateTable(%s)' not allowed. " +
                    "Call updateTable(...) first", tableName));
        }
        Utility.validateTableName(tableName);
        initUpdateClause();
        updateStatement.append(tableName);
        level = LEVEL.TABLENAME_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder setColumn(String columnName, T columnValue) {
        if (LEVEL.TABLENAME_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Call to 'setColumn(%s)' not in the right sequence. " +
                    "Call updateTable(...) first", columnName));
        }
        Utility.validate(columnName);
        if (!initUpdateClause()) {
            updateStatement.append(GenericDaoConstants.COMMA);
        }
        updateStatement.append(BaseDao.SET)
                .append(columnName)
                .append(otc.framework.generic.dao.WhereClauseBuilder.TOKENS.EQUALS)
                .append(GenericDaoConstants.APOSTROPHE)
                .append(columnValue)
                .append(GenericDaoConstants.APOSTROPHE);
        level = LEVEL.SET_ADDED;
        return this;
    }

    public String build() {
        if (updateStatement == null) {
            return null;
        }
        if (LEVEL.SET_ADDED != level && LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Update statement created not in a required state for call to build() "));
        }
        if (LEVEL.SET_ADDED == level) {
            return updateStatement.toString();
        }
        return updateStatement.append(super.build()).toString();
    }

    private boolean initUpdateClause() {
        if (updateStatement == null) {
            updateStatement = new StringBuilder(BaseDao.UPDATE);
            return true;
        }
        return false;
    }

    public UpdateStatementBuilder where() {
        if (LEVEL.SET_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Query not created in required state for call to where() "));
        }
        level = LEVEL.WHERE_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder equals(String columnName, T columnValue) {
        isWhereCalled();
        super.equals(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder equalsNamedCriteria(String columnName) {
        isWhereCalled();
        super.equalsNamedCriteria(columnName);
        return this;
    }

    public <T> UpdateStatementBuilder notEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.notEquals(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder notEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.notEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> UpdateStatementBuilder greaterThan(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThan(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanNamedCriteria(columnName);
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThanEquals(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> UpdateStatementBuilder lessThan(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThan(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder lessThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanNamedCriteria(columnName);
        return this;
    }

    public <T> UpdateStatementBuilder lessThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThanEquals(columnName, columnValue);
        return this;
    }

    public <T> UpdateStatementBuilder lessThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanEqualsNamedCriteria(columnName);
        return this;
    }

    public UpdateStatementBuilder and() {
        isConditionAdded();
        super.and();
        return this;
    }

    public UpdateStatementBuilder or() {
        isConditionAdded();
        super.or();
        return this;
    }

    public UpdateStatementBuilder not() {
        isConditionAdded();
        super.not();
        return this;
    }

    public <T> UpdateStatementBuilder between(String columnName, T beginValue, T endValue) {
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
