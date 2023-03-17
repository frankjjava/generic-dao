package otc.framework.generic.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otc.framework.generic.dao.exception.GenericDaoBuilderException;

public class UpdateStatementBuilder extends WhereClauseBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDaoImpl.class);

    private StringBuilder updateStatement;
    private enum LEVEL {TABLENAME_ADDED, SET_ADDED, WHERE_ADDED, CONDITION_ADDED}
    private UpdateStatementBuilder.LEVEL level;

    private UpdateStatementBuilder() {}

    public static UpdateStatementBuilder newBuilder() {
        return new UpdateStatementBuilder();
    }

    public UpdateStatementBuilder table(String tableName) {
        if (null != level) {
            throw new GenericDaoBuilderException(String.format("Repeat call to 'updateTable(%s)' is not allowed. ", tableName));
        }
        Utility.validateTableName(tableName);
        initUpdateClause();
        updateStatement.append(tableName);
        level = LEVEL.TABLENAME_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder set(String columnName, T columnValue) {
        if (null == level) {
            throw new GenericDaoBuilderException(String.format("Call to 'set(%s, %s)' not in the right sequence. " +
                    "Call updateTable(...) first", columnName, columnValue));
        }
        if (LEVEL.WHERE_ADDED == level || LEVEL.CONDITION_ADDED == level) {
            throw new GenericDaoBuilderException(String.format("Update statement not in required state for call set(%s, %s)",
                    columnName, columnValue));
        }
        Utility.validate(columnName, columnValue);
        if (LEVEL.TABLENAME_ADDED == level) {
            updateStatement.append(BaseDao.SET);
        } else if (LEVEL.SET_ADDED == level) {
            updateStatement.append(BaseDao.COMMA);
        }
        updateStatement.append(columnName)
                .append(TOKENS.EQUALS)
                .append(BaseDao.APOSTROPHE)
                .append(columnValue)
                .append(BaseDao.APOSTROPHE);
        level = LEVEL.SET_ADDED;
        return this;
    }

    public UpdateStatementBuilder setForNamedParameter(String columnName, String paramName) {
        if (null == level) {
            throw new GenericDaoBuilderException(String.format("Call to 'setForNamedParameter(%s, %s)' not in the right sequence. " +
                    "Call updateTable(...) first", columnName, paramName));
        }
        if (LEVEL.WHERE_ADDED == level || LEVEL.CONDITION_ADDED == level) {
            throw new GenericDaoBuilderException(String.format("Update statement not in required state for call setForNamedParameter(%s, %s)",
                    columnName, paramName));
        }
        Utility.validateColumnAndParamName(columnName, paramName);
        if (LEVEL.TABLENAME_ADDED == level) {
            updateStatement.append(BaseDao.SET);
        } else if (LEVEL.SET_ADDED == level) {
            updateStatement.append(BaseDao.COMMA);
        }
        updateStatement.append(columnName)
                .append(TOKENS.EQUALS)
                .append(BaseDao.COLON)
                .append(paramName);
        level = LEVEL.SET_ADDED;
        return this;
    }

    /**
     *
     * @return
     */
    public String build() {
        if (updateStatement == null) {
            throw new GenericDaoBuilderException(String.format("Insert statement nothing to build()"));
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
        isWhereCalled("equals(...)");
        super.equals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder equalsNamedCriteria(String columnName) {
        isWhereCalled("equalsNamedCriteria(...)");
        super.equalsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder notEquals(String columnName, T columnValue) {
        isWhereCalled("notEquals(...)");
        super.notEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder notEqualsNamedCriteria(String columnName) {
        isWhereCalled("notEqualsNamedCriteria(...)");
        super.notEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder greaterThan(String columnName, T columnValue) {
        isWhereCalled("greaterThan(...)");
        super.greaterThan(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanNamedCriteria(String columnName) {
        isWhereCalled("greaterThanNamedCriteria(...)");
        super.greaterThanNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanEquals(String columnName, T columnValue) {
        isWhereCalled("greaterThanEquals(...)");
        super.greaterThanEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder greaterThanEqualsNamedCriteria(String columnName) {
        isWhereCalled("greaterThanEqualsNamedCriteria(...)");
        super.greaterThanEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder lessThan(String columnName, T columnValue) {
        isWhereCalled("lessThan(...)");
        super.lessThan(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder lessThanNamedCriteria(String columnName) {
        isWhereCalled("lessThanNamedCriteria(...)");
        super.lessThanNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder lessThanEquals(String columnName, T columnValue) {
        isWhereCalled("lessThanEquals(...)");
        super.lessThanEquals(columnName, columnValue);
        this.level = LEVEL.CONDITION_ADDED;
        return this;
    }

    public <T> UpdateStatementBuilder lessThanEqualsNamedCriteria(String columnName) {
        isWhereCalled("lessThanEqualsNamedCriteria(...)");
        super.lessThanEqualsNamedCriteria(columnName);
        this.level = LEVEL.CONDITION_ADDED;
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

    private boolean isWhereCalled(String methodName) {
        if (LEVEL.WHERE_ADDED != level && LEVEL.CONDITION_ADDED != level) {
            throw new GenericDaoBuilderException(String.format("Query created not in required state for call '%s' ",
                    methodName));
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
