package com.easytoolsoft.easyreport.meta.domain.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public class DataSourceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DataSourceExample() {
        this.oredCriteria = new ArrayList<>();
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }

    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(final Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Criteria or() {
        final Criteria criteria = createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        final Criteria criteria = createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        return new Criteria();
    }

    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            this.criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return this.criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(final String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }

        protected void addCriterion(final String condition, final Object value, final String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(final String condition, final Object value1, final Object value2,
                                    final String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria)this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria)this;
        }

        public Criteria andIdEqualTo(final Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotEqualTo(final Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThan(final Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThan(final Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdIn(final List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotIn(final List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria)this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria)this;
        }

        public Criteria andUidEqualTo(final String value) {
            addCriterion("uid =", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidNotEqualTo(final String value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidGreaterThan(final String value) {
            addCriterion("uid >", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidGreaterThanOrEqualTo(final String value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidLessThan(final String value) {
            addCriterion("uid <", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidLessThanOrEqualTo(final String value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidLike(final String value) {
            addCriterion("uid like", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidNotLike(final String value) {
            addCriterion("uid not like", value, "uid");
            return (Criteria)this;
        }

        public Criteria andUidIn(final List<String> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria)this;
        }

        public Criteria andUidNotIn(final List<String> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria)this;
        }

        public Criteria andUidBetween(final String value1, final String value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria)this;
        }

        public Criteria andUidNotBetween(final String value1, final String value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria)this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria)this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria)this;
        }

        public Criteria andNameEqualTo(final String value) {
            addCriterion("name =", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotEqualTo(final String value) {
            addCriterion("name <>", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThan(final String value) {
            addCriterion("name >", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThanOrEqualTo(final String value) {
            addCriterion("name >=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThan(final String value) {
            addCriterion("name <", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThanOrEqualTo(final String value) {
            addCriterion("name <=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLike(final String value) {
            addCriterion("name like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotLike(final String value) {
            addCriterion("name not like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameIn(final List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotIn(final List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameBetween(final String value1, final String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotBetween(final String value1, final String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria)this;
        }

        public Criteria andDriverClassIsNull() {
            addCriterion("driver_class is null");
            return (Criteria)this;
        }

        public Criteria andDriverClassIsNotNull() {
            addCriterion("driver_class is not null");
            return (Criteria)this;
        }

        public Criteria andDriverClassEqualTo(final String value) {
            addCriterion("driver_class =", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassNotEqualTo(final String value) {
            addCriterion("driver_class <>", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassGreaterThan(final String value) {
            addCriterion("driver_class >", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassGreaterThanOrEqualTo(final String value) {
            addCriterion("driver_class >=", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassLessThan(final String value) {
            addCriterion("driver_class <", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassLessThanOrEqualTo(final String value) {
            addCriterion("driver_class <=", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassLike(final String value) {
            addCriterion("driver_class like", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassNotLike(final String value) {
            addCriterion("driver_class not like", value, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassIn(final List<String> values) {
            addCriterion("driver_class in", values, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassNotIn(final List<String> values) {
            addCriterion("driver_class not in", values, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassBetween(final String value1, final String value2) {
            addCriterion("driver_class between", value1, value2, "driverClass");
            return (Criteria)this;
        }

        public Criteria andDriverClassNotBetween(final String value1, final String value2) {
            addCriterion("driver_class not between", value1, value2, "driverClass");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlIsNull() {
            addCriterion("jdbc_url is null");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlIsNotNull() {
            addCriterion("jdbc_url is not null");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlEqualTo(final String value) {
            addCriterion("jdbc_url =", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlNotEqualTo(final String value) {
            addCriterion("jdbc_url <>", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlGreaterThan(final String value) {
            addCriterion("jdbc_url >", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlGreaterThanOrEqualTo(final String value) {
            addCriterion("jdbc_url >=", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlLessThan(final String value) {
            addCriterion("jdbc_url <", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlLessThanOrEqualTo(final String value) {
            addCriterion("jdbc_url <=", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlLike(final String value) {
            addCriterion("jdbc_url like", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlNotLike(final String value) {
            addCriterion("jdbc_url not like", value, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlIn(final List<String> values) {
            addCriterion("jdbc_url in", values, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlNotIn(final List<String> values) {
            addCriterion("jdbc_url not in", values, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlBetween(final String value1, final String value2) {
            addCriterion("jdbc_url between", value1, value2, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andJdbcUrlNotBetween(final String value1, final String value2) {
            addCriterion("jdbc_url not between", value1, value2, "jdbcUrl");
            return (Criteria)this;
        }

        public Criteria andUserIsNull() {
            addCriterion("user is null");
            return (Criteria)this;
        }

        public Criteria andUserIsNotNull() {
            addCriterion("user is not null");
            return (Criteria)this;
        }

        public Criteria andUserEqualTo(final String value) {
            addCriterion("user =", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserNotEqualTo(final String value) {
            addCriterion("user <>", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserGreaterThan(final String value) {
            addCriterion("user >", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserGreaterThanOrEqualTo(final String value) {
            addCriterion("user >=", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserLessThan(final String value) {
            addCriterion("user <", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserLessThanOrEqualTo(final String value) {
            addCriterion("user <=", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserLike(final String value) {
            addCriterion("user like", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserNotLike(final String value) {
            addCriterion("user not like", value, "user");
            return (Criteria)this;
        }

        public Criteria andUserIn(final List<String> values) {
            addCriterion("user in", values, "user");
            return (Criteria)this;
        }

        public Criteria andUserNotIn(final List<String> values) {
            addCriterion("user not in", values, "user");
            return (Criteria)this;
        }

        public Criteria andUserBetween(final String value1, final String value2) {
            addCriterion("user between", value1, value2, "user");
            return (Criteria)this;
        }

        public Criteria andUserNotBetween(final String value1, final String value2) {
            addCriterion("user not between", value1, value2, "user");
            return (Criteria)this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria)this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria)this;
        }

        public Criteria andPasswordEqualTo(final String value) {
            addCriterion("password =", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordNotEqualTo(final String value) {
            addCriterion("password <>", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordGreaterThan(final String value) {
            addCriterion("password >", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(final String value) {
            addCriterion("password >=", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordLessThan(final String value) {
            addCriterion("password <", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordLessThanOrEqualTo(final String value) {
            addCriterion("password <=", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordLike(final String value) {
            addCriterion("password like", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordNotLike(final String value) {
            addCriterion("password not like", value, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordIn(final List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordNotIn(final List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordBetween(final String value1, final String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria)this;
        }

        public Criteria andPasswordNotBetween(final String value1, final String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria)this;
        }

        public Criteria andOptionsIsNull() {
            addCriterion("options is null");
            return (Criteria)this;
        }

        public Criteria andOptionsIsNotNull() {
            addCriterion("options is not null");
            return (Criteria)this;
        }

        public Criteria andOptionsEqualTo(final String value) {
            addCriterion("options =", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsNotEqualTo(final String value) {
            addCriterion("options <>", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsGreaterThan(final String value) {
            addCriterion("options >", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsGreaterThanOrEqualTo(final String value) {
            addCriterion("options >=", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsLessThan(final String value) {
            addCriterion("options <", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsLessThanOrEqualTo(final String value) {
            addCriterion("options <=", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsLike(final String value) {
            addCriterion("options like", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsNotLike(final String value) {
            addCriterion("options not like", value, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsIn(final List<String> values) {
            addCriterion("options in", values, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsNotIn(final List<String> values) {
            addCriterion("options not in", values, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsBetween(final String value1, final String value2) {
            addCriterion("options between", value1, value2, "options");
            return (Criteria)this;
        }

        public Criteria andOptionsNotBetween(final String value1, final String value2) {
            addCriterion("options not between", value1, value2, "options");
            return (Criteria)this;
        }

        public Criteria andCommentIsNull() {
            addCriterion("comment is null");
            return (Criteria)this;
        }

        public Criteria andCommentIsNotNull() {
            addCriterion("comment is not null");
            return (Criteria)this;
        }

        public Criteria andCommentEqualTo(final String value) {
            addCriterion("comment =", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentNotEqualTo(final String value) {
            addCriterion("comment <>", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentGreaterThan(final String value) {
            addCriterion("comment >", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentGreaterThanOrEqualTo(final String value) {
            addCriterion("comment >=", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentLessThan(final String value) {
            addCriterion("comment <", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentLessThanOrEqualTo(final String value) {
            addCriterion("comment <=", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentLike(final String value) {
            addCriterion("comment like", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentNotLike(final String value) {
            addCriterion("comment not like", value, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentIn(final List<String> values) {
            addCriterion("comment in", values, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentNotIn(final List<String> values) {
            addCriterion("comment not in", values, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentBetween(final String value1, final String value2) {
            addCriterion("comment between", value1, value2, "comment");
            return (Criteria)this;
        }

        public Criteria andCommentNotBetween(final String value1, final String value2) {
            addCriterion("comment not between", value1, value2, "comment");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedIsNull() {
            addCriterion("gmt_created is null");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedIsNotNull() {
            addCriterion("gmt_created is not null");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedEqualTo(final Date value) {
            addCriterion("gmt_created =", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedNotEqualTo(final Date value) {
            addCriterion("gmt_created <>", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedGreaterThan(final Date value) {
            addCriterion("gmt_created >", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedGreaterThanOrEqualTo(final Date value) {
            addCriterion("gmt_created >=", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedLessThan(final Date value) {
            addCriterion("gmt_created <", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedLessThanOrEqualTo(final Date value) {
            addCriterion("gmt_created <=", value, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedIn(final List<Date> values) {
            addCriterion("gmt_created in", values, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedNotIn(final List<Date> values) {
            addCriterion("gmt_created not in", values, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedBetween(final Date value1, final Date value2) {
            addCriterion("gmt_created between", value1, value2, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtCreatedNotBetween(final Date value1, final Date value2) {
            addCriterion("gmt_created not between", value1, value2, "gmtCreated");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedEqualTo(final Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedNotEqualTo(final Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedGreaterThan(final Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(final Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedLessThan(final Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(final Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedIn(final List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedNotIn(final List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedBetween(final Date value1, final Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria)this;
        }

        public Criteria andGmtModifiedNotBetween(final Date value1, final Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria)this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andFieldLike(final String fieldName, final String keyword) {
            addCriterion(fieldName + " like ", keyword, fieldName);
            return this;
        }
    }

    public static class Criterion {
        private final String condition;
        private final String typeHandler;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;

        protected Criterion(final String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(final String condition, final Object value, final String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(final String condition, final Object value) {
            this(condition, value, null);
        }

        protected Criterion(final String condition, final Object value, final Object secondValue,
                            final String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(final String condition, final Object value, final Object secondValue) {
            this(condition, value, secondValue, null);
        }

        public String getCondition() {
            return this.condition;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getSecondValue() {
            return this.secondValue;
        }

        public boolean isNoValue() {
            return this.noValue;
        }

        public boolean isSingleValue() {
            return this.singleValue;
        }

        public boolean isBetweenValue() {
            return this.betweenValue;
        }

        public boolean isListValue() {
            return this.listValue;
        }

        public String getTypeHandler() {
            return this.typeHandler;
        }
    }
}