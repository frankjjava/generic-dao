package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class SelectQueryBuilder extends WhereClauseBuilder {

    private enum LEVEL {COLUMN_ADDED, FROM_ADDED, WHERE_ADDED, CONDITION_ADDED}

    private LEVEL level;
    private StringBuilder selectClause;

    private SelectQueryBuilder() {
        super();
    }

    public static SelectQueryBuilder createInstance() {
        return new SelectQueryBuilder();
    }

    public SelectQueryBuilder addColumn(String columnName) {
        Utility.validate(columnName);
        if (initSelectClause()) {
            selectClause.append(columnName);
        } else {
            selectClause.append(GenericDaoConstants.COMMA).append(columnName);
        }
        level = LEVEL.COLUMN_ADDED;
        return this;
    }

    public SelectQueryBuilder from(String tableName) {
        if (LEVEL.COLUMN_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Call to 'from(%s)' not in the right sequence. " +
                    "Call addColumn(...) first", tableName));
        }
        if (level == LEVEL.FROM_ADDED) {
            throw new GenericDaoBuilderException(String.format("Repeat call to 'from(%s)' is not allowed ", tableName));
        }
        Utility.validateTableName(tableName);
        selectClause.append(BaseDao.FROM)
                .append(tableName);
        level = LEVEL.FROM_ADDED;
        return this;
    }

    public String build() {
        if (selectClause == null) {
            return null;
        }
        if (LEVEL.COLUMN_ADDED == level || LEVEL.WHERE_ADDED == level) {
            throw new GenericDaoBuilderException("Query not created in required state for call to build() ");
        }
        if (LEVEL.FROM_ADDED == level) {
            return selectClause.toString();
        }
        return selectClause.append(super.build())
                .append(GenericDaoConstants.SEMI_COLON)
                .toString();
    }

    private boolean initSelectClause() {
        if (selectClause == null) {
            selectClause = new StringBuilder(BaseDao.SELECT);
            return true;
        }
        return false;
    }

    public SelectQueryBuilder where() {
        if (LEVEL.FROM_ADDED != level) {
            throw new GenericDaoBuilderException("Query created not in a required state for call to where() ");
        }
        if (level == LEVEL.WHERE_ADDED) {
            throw new GenericDaoBuilderException("Repeat call to 'where()' is not allowed ");
        }
        level = LEVEL.WHERE_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder equals(String columnName, T columnValue) {
        isWhereCalled();
        super.equals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder equalsNamedCriteria(String columnName) {
        isWhereCalled();
        super.equalsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder notEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.notEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder notEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.notEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder greaterThan(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThan(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder greaterThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder greaterThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.greaterThanEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder greaterThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.greaterThanEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder lessThan(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThan(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder lessThanNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder lessThanEquals(String columnName, T columnValue) {
        isWhereCalled();
        super.lessThanEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> SelectQueryBuilder lessThanEqualsNamedCriteria(String columnName) {
        isWhereCalled();
        super.lessThanEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public SelectQueryBuilder and() {
        isConditionAdded();
        super.and();
        return this;
    }

    public SelectQueryBuilder or() {
        isConditionAdded();
        super.or();
        return this;
    }

    public SelectQueryBuilder not() {
        isConditionAdded();
        super.not();
        return this;
    }

    public <T> SelectQueryBuilder between(String columnName, T beginValue, T endValue) {
        super.between(columnName, beginValue, endValue);
        return this;
    }

    private boolean isWhereCalled() {
        if (LEVEL.WHERE_ADDED != level && LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException("Query created not in required state for call to this method ");
        }
        return true;
    }

    private boolean isConditionAdded() {
        if (LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException("Query created not in required state for call to this method ");
        }
        return true;
    }

}
