package otc.framework.generic.dao;

public class SelectQueryBuilder {

    private StringBuilder selectClause;
    private WhereClauseBuilder whereClauseBuilder;

    private SelectQueryBuilder() {}

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
        return this;
    }

    public String build() {
        if (selectClause == null) {
            return null;
        }
        if (whereClauseBuilder == null) {
            return selectClause.toString();
        }
        return selectClause.append(whereClauseBuilder.build()).toString();
    }

    private boolean initSelectClause() {
        if (selectClause == null) {
            selectClause = new StringBuilder(BaseDao.SELECT);
            return true;
        }
        return false;
    }

    public SelectQueryBuilder where() {
        if (whereClauseBuilder == null) {
            whereClauseBuilder =  WhereClauseBuilder.createInstance();
        }
        return this;
    }

    public <T> SelectQueryBuilder equals(String columnName, T columnValue) {
        whereClauseBuilder.equals(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder equalsNamedCriteria(String columnName) {
        whereClauseBuilder.equalsNamedCriteria(columnName);
        return this;
    }

    public <T> SelectQueryBuilder notEquals(String columnName, T columnValue) {
        whereClauseBuilder.notEquals(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder notEqualsNamedCriteria(String columnName) {
        whereClauseBuilder.notEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> SelectQueryBuilder greaterThan(String columnName, T columnValue) {
        whereClauseBuilder.greaterThan(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder greaterThanNamedCriteria(String columnName) {
        whereClauseBuilder.greaterThanNamedCriteria(columnName);
        return this;
    }

    public <T> SelectQueryBuilder greaterThanEquals(String columnName, T columnValue) {
        whereClauseBuilder.greaterThanEquals(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder greaterThanEqualsNamedCriteria(String columnName) {
        whereClauseBuilder.greaterThanEqualsNamedCriteria(columnName);
        return this;
    }

    public <T> SelectQueryBuilder lessThan(String columnName, T columnValue) {
        whereClauseBuilder.lessThan(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder lessThanNamedCriteria(String columnName) {
        whereClauseBuilder.lessThanNamedCriteria(columnName);
        return this;
    }

    public <T> SelectQueryBuilder lessThanEquals(String columnName, T columnValue) {
        whereClauseBuilder.lessThanEquals(columnName, columnValue);
        return this;
    }

    public <T> SelectQueryBuilder lessThanEqualsNamedCriteria(String columnName) {
        whereClauseBuilder.lessThanEqualsNamedCriteria(columnName);
        return this;
    }

    public SelectQueryBuilder and() {
        whereClauseBuilder.and();
        return this;
    }

    public SelectQueryBuilder or() {
        whereClauseBuilder.or();
        return this;
    }

    public SelectQueryBuilder not() {
        whereClauseBuilder.not();
        return this;
    }

    public <T> SelectQueryBuilder between(String columnName, T beginValue, T endValue) {
        whereClauseBuilder.between(columnName, beginValue, endValue);
        return this;
    }

}
