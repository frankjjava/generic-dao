package otc.framework.generic.dao;

import otc.framework.generic.dao.exception.GenericDaoBuilderException;
import otc.framework.generic.dao.exception.GenericDaoException;
import otc.framework.generic.dao.exception.GenericDaoValidationException;

public class SelectQueryBuilder {

    private StringBuilder selectClause;
    private WhereClauseBuilder whereClauseBuilder;

    private boolean mature;

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
        mature = true;
        return this;
    }

    public SelectQueryBuilder from(String tableName) {
        if (!mature) {
            throw new GenericDaoBuilderException(String.format("Call to 'from(%s)' not in the right sequence. " +
                    "Call addColumn(...) first", tableName));
        }
        Utility.validateTableName(tableName);
        selectClause.append(BaseDao.FROM)
                .append(tableName);
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

    private static class WhereClauseBuilder {

        private StringBuilder whereClause;

        public enum LOGICAL_OPERATORS {
            AND(" AND "),
            /** The or. */
            OR(" OR "),
            NOT(" NOT ");

            private final String operator;

            private LOGICAL_OPERATORS(String operator) {
                this.operator = operator;
            }

            String getOperator() {
                return operator;
            }

        };

        public enum TOKENS {

            EQUALS(" = "),
            NOT_EQUALS(" != "),
            GREATER_THAN(" > "),
            LESS_THAN(" < "),
            GREATER_THAN_EQUALS(" >= "),
            LESS_THAN_EQUALS(" <= "),
            IS(" IS "),
            IN(" IN "),
            IS_NULL(" IS NULL "),
            IS_NOT_NULL(" IS NOT NULL "),

//        LIKE(" LIKE "),
//        IS_NOT(" IS NOT "),

            /** The where. */
            WHERE(" where "),

            /** The Constant BETWEEN. */
            BETWEEN(" between '"),

            /** The Constant SET. */
            SET(" set ");

            private final String label;

            private TOKENS(String token) {
                this.label = token;
            }

            @Override
            public String toString() {
                return this.label;
            }
        }

        private WhereClauseBuilder() {}

        public static WhereClauseBuilder createInstance() {
            return new WhereClauseBuilder();
        }

        public <T> WhereClauseBuilder equals(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder equalsNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder notEquals(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.NOT_EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder notEqualsNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.NOT_EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder greaterThan(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.GREATER_THAN);
            return this;
        }

        public <T> WhereClauseBuilder greaterThanNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.GREATER_THAN);
            return this;
        }

        public <T> WhereClauseBuilder greaterThanEquals(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.GREATER_THAN_EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder greaterThanEqualsNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.GREATER_THAN_EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder lessThan(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.LESS_THAN);
            return this;
        }

        public <T> WhereClauseBuilder lessThanNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.LESS_THAN);
            return this;
        }

        public <T> WhereClauseBuilder lessThanEquals(String columnName, T columnValue) {
            addWhereClause(columnName, columnValue, TOKENS.LESS_THAN_EQUALS);
            return this;
        }

        public <T> WhereClauseBuilder lessThanEqualsNamedCriteria(String columnName) {
            addNamedCriteria(columnName, TOKENS.LESS_THAN_EQUALS);
            return this;
        }

        public WhereClauseBuilder and() {
            addLogicalOperator(LOGICAL_OPERATORS.AND);
            return this;
        }

        public WhereClauseBuilder or() {
            addLogicalOperator(LOGICAL_OPERATORS.OR);
            return this;
        }

        public WhereClauseBuilder not() {
            addLogicalOperator(LOGICAL_OPERATORS.NOT);
            return this;
        }

        public <T> WhereClauseBuilder between(String columnName, T beginValue, T endValue) {
            validate(columnName, beginValue, endValue);
            if (whereClause == null) {
                whereClause = new StringBuilder(TOKENS.WHERE.label);
            }
            whereClause.append(columnName)
                    .append(TOKENS.BETWEEN)
                    .append(beginValue)
                    .append(GenericDaoConstants.APOSTROPHE + GenericDaoConstants.SPACE)
                    .append(LOGICAL_OPERATORS.AND)
                    .append(GenericDaoConstants.SPACE + GenericDaoConstants.APOSTROPHE)
                    .append(endValue).append(GenericDaoConstants.APOSTROPHE);

            return this;
        }

        public String build() {
            if (whereClause == null) {
                return null;
            }
            return whereClause.toString();
        }

        private void initWhereClause() {
            if (whereClause == null) {
                whereClause = new StringBuilder(TOKENS.WHERE.label);
            }
        }

        private <T> void validate(String columnName, T beginValue, T endValue) {
            Utility.validate(columnName);
            if (null == beginValue || (beginValue instanceof String && ((String)beginValue).trim().equals(""))) {
                throw new GenericDaoValidationException("Invalid Begin-value");
            }
            if (null == endValue || (endValue instanceof String && ((String)endValue).trim().equals(""))) {
                throw new GenericDaoValidationException("Invalid End-value");
            }
            return;
        }

        private void addNamedCriteria(String columnName, TOKENS relationalOperator) {
            Utility.validate(columnName);
            initWhereClause();
            whereClause.append(columnName)
                    .append(relationalOperator)
                    .append(GenericDaoConstants.COLON)
                    .append(columnName);
        }

        private <T> void addWhereClause(String columnName, T columnValue, TOKENS relationalOperator) {
            Utility.validate(columnName, columnValue);
            initWhereClause();
            boolean isString = columnValue instanceof String;
            if (isString) {
                String value = ((String) columnValue).trim();
                if (value.equalsIgnoreCase(TOKENS.IS_NULL.label.trim())) {
                    whereClause.append(columnName).append(TOKENS.IS_NULL);
                } else if (value.equalsIgnoreCase(GenericDaoConstants.ASTERISK) ||
                        TOKENS.IS_NOT_NULL.label.trim().equals(value)) {
                    whereClause.append(columnName).append(TOKENS.IS_NOT_NULL);
                } else {
                    whereClause.append(columnName)
                            .append(relationalOperator)
                            .append(GenericDaoConstants.APOSTROPHE)
                            .append(columnValue)
                            .append(GenericDaoConstants.APOSTROPHE);
                }
            } else {
                whereClause.append(columnName)
                        .append(relationalOperator)
                        .append(GenericDaoConstants.APOSTROPHE)
                        .append(columnValue)
                        .append(GenericDaoConstants.APOSTROPHE);
            }
        }

        private void addLogicalOperator(LOGICAL_OPERATORS logicalOperator) {
            if (whereClause == null || whereClause.toString().trim().equals("")) {
                throw new GenericDaoValidationException("Invalid Where clause constructed");
            }
            whereClause.append(logicalOperator.getOperator());
        }

        /**
         * Builds the where clause.
         *
         * @param whereClause the where clause
         * @param colName the col name
         * @param value the value
         * @param logicalOperator the logical operator
         * @param relationalOperator the relational operator
         * @param isUseNamedCriteria the is use named criteria
         * @param ignoreCriteriaFields the ignore criteria fields
         * @return the string builder
         */
/*
    protected StringBuilder buildWhereClause(StringBuilder whereClause, String colName, String value,
                                             LOGICAL_OPERATORS logicalOperator, RELATIONAL_OPERATORS relationalOperator, boolean isUseNamedCriteria,
                                             List<String> ignoreCriteriaFields) {
        boolean isIgnoreFieldsContainsIgnoreCase = containsIgnoreCase(ignoreCriteriaFields, colName);
        if (whereClause == null) {
            whereClause = new StringBuilder(GenericDaoConstants.WHERE);
        } else if (!whereClause.toString().equals(GenericDaoConstants.WHERE)) {
            if (logicalOperator != null && (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase)) {
                whereClause.append(GenericDaoConstants.SPACE)
                        .append(logicalOperator.name())
                        .append(GenericDaoConstants.SPACE);
            }
        }
        if (value != null && !isIgnoreFieldsContainsIgnoreCase) {
            value = value.trim();
            if (value.equalsIgnoreCase(GenericDaoConstants.IS_NULL.trim())) {
                whereClause.append(colName).append(GenericDaoConstants.IS_NULL);
            } else if (value.equalsIgnoreCase(GenericDaoConstants.ASTERISK) ||
                    GenericDaoConstants.IS_NOT_NULL.trim().equals(value)) {
                whereClause.append(colName).append(GenericDaoConstants.IS_NOT_NULL);
            } else {
                whereClause.append(colName)
                        .append(relationalOperator)
                        .append(GenericDaoConstants.APOSTROPHE)
                        .append(value)
                        .append(GenericDaoConstants.APOSTROPHE);
            }
        } else if (isUseNamedCriteria) {
            whereClause.append(colName)
                    .append(relationalOperator)
                    .append(GenericDaoConstants.COLON)
                    .append(colName);
        } else if (ignoreCriteriaFields == null || !isIgnoreFieldsContainsIgnoreCase) {
            whereClause.append(colName).append(GenericDaoConstants.IS_NULL);
        }
        return whereClause;
    }
*/

        /**
         * Builds the where between clause.
         *
         * @param whereClause the where clause
         * @param logicalOperator the logical operator
         * @param columnName the column name
         * @param beginValue the begin value
         * @param endValue the end value
         * @return the string builder
         */
        protected StringBuilder buildWhereBetweenClause(StringBuilder whereClause, TOKENS logicalOperator,
                                                        String columnName, String beginValue, String endValue) {
            if (beginValue == null || endValue == null) {
                throw new GenericDaoException("Invalid Begin-value and / or End-value!");
            }
            if (whereClause == null) {
                whereClause = new StringBuilder(TOKENS.WHERE.label);
            } else if (!whereClause.toString().equals(TOKENS.WHERE.label)) {
                if (logicalOperator != null) {
                    whereClause.append(GenericDaoConstants.SPACE)
                            .append(logicalOperator.name())
                            .append(GenericDaoConstants.SPACE);
                }
            }
            whereClause.append(columnName)
                    .append(TOKENS.BETWEEN.label)
                    .append(beginValue)
                    .append(GenericDaoConstants.APOSTROPHE + GenericDaoConstants.SPACE)
                    .append(LOGICAL_OPERATORS.AND)
                    .append(GenericDaoConstants.SPACE + GenericDaoConstants.APOSTROPHE)
                    .append(endValue).append(GenericDaoConstants.APOSTROPHE);
            return whereClause;
        }

        /**
         * Contains ignore case.
         *
         * @param values the values
         * @param searchStr the search str
         * @return true, if successful
         */
/*    private boolean containsIgnoreCase(List<String> values, String searchStr) {
        if (values == null && searchStr == null) {
            return true;
        }
        if (values == null || values.isEmpty() || searchStr == null) {
            return false;
        }
        for (String value : values) {
            if (value.equalsIgnoreCase(searchStr)) {
                return true;
            }
        }
        return false;
    }*/
    }

}
