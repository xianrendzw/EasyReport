package com.easytoolsoft.easyreport.membership.domain.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public class EventExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EventExample() {
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

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria)this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria)this;
        }

        public Criteria andSourceEqualTo(final String value) {
            addCriterion("source =", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceNotEqualTo(final String value) {
            addCriterion("source <>", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceGreaterThan(final String value) {
            addCriterion("source >", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(final String value) {
            addCriterion("source >=", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceLessThan(final String value) {
            addCriterion("source <", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceLessThanOrEqualTo(final String value) {
            addCriterion("source <=", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceLike(final String value) {
            addCriterion("source like", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceNotLike(final String value) {
            addCriterion("source not like", value, "source");
            return (Criteria)this;
        }

        public Criteria andSourceIn(final List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria)this;
        }

        public Criteria andSourceNotIn(final List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria)this;
        }

        public Criteria andSourceBetween(final String value1, final String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria)this;
        }

        public Criteria andSourceNotBetween(final String value1, final String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria)this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria)this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria)this;
        }

        public Criteria andUserIdEqualTo(final Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotEqualTo(final Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdGreaterThan(final Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(final Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThan(final Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThanOrEqualTo(final Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdIn(final List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotIn(final List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdBetween(final Integer value1, final Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotBetween(final Integer value1, final Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria)this;
        }

        public Criteria andAccountIsNull() {
            addCriterion("account is null");
            return (Criteria)this;
        }

        public Criteria andAccountIsNotNull() {
            addCriterion("account is not null");
            return (Criteria)this;
        }

        public Criteria andAccountEqualTo(final String value) {
            addCriterion("account =", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountNotEqualTo(final String value) {
            addCriterion("account <>", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountGreaterThan(final String value) {
            addCriterion("account >", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountGreaterThanOrEqualTo(final String value) {
            addCriterion("account >=", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountLessThan(final String value) {
            addCriterion("account <", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountLessThanOrEqualTo(final String value) {
            addCriterion("account <=", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountLike(final String value) {
            addCriterion("account like", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountNotLike(final String value) {
            addCriterion("account not like", value, "account");
            return (Criteria)this;
        }

        public Criteria andAccountIn(final List<String> values) {
            addCriterion("account in", values, "account");
            return (Criteria)this;
        }

        public Criteria andAccountNotIn(final List<String> values) {
            addCriterion("account not in", values, "account");
            return (Criteria)this;
        }

        public Criteria andAccountBetween(final String value1, final String value2) {
            addCriterion("account between", value1, value2, "account");
            return (Criteria)this;
        }

        public Criteria andAccountNotBetween(final String value1, final String value2) {
            addCriterion("account not between", value1, value2, "account");
            return (Criteria)this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria)this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria)this;
        }

        public Criteria andLevelEqualTo(final String value) {
            addCriterion("level =", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelNotEqualTo(final String value) {
            addCriterion("level <>", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelGreaterThan(final String value) {
            addCriterion("level >", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(final String value) {
            addCriterion("level >=", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelLessThan(final String value) {
            addCriterion("level <", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelLessThanOrEqualTo(final String value) {
            addCriterion("level <=", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelLike(final String value) {
            addCriterion("level like", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelNotLike(final String value) {
            addCriterion("level not like", value, "level");
            return (Criteria)this;
        }

        public Criteria andLevelIn(final List<String> values) {
            addCriterion("level in", values, "level");
            return (Criteria)this;
        }

        public Criteria andLevelNotIn(final List<String> values) {
            addCriterion("level not in", values, "level");
            return (Criteria)this;
        }

        public Criteria andLevelBetween(final String value1, final String value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria)this;
        }

        public Criteria andLevelNotBetween(final String value1, final String value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria)this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria)this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria)this;
        }

        public Criteria andUrlEqualTo(final String value) {
            addCriterion("url =", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlNotEqualTo(final String value) {
            addCriterion("url <>", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlGreaterThan(final String value) {
            addCriterion("url >", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(final String value) {
            addCriterion("url >=", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlLessThan(final String value) {
            addCriterion("url <", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlLessThanOrEqualTo(final String value) {
            addCriterion("url <=", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlLike(final String value) {
            addCriterion("url like", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlNotLike(final String value) {
            addCriterion("url not like", value, "url");
            return (Criteria)this;
        }

        public Criteria andUrlIn(final List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria)this;
        }

        public Criteria andUrlNotIn(final List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria)this;
        }

        public Criteria andUrlBetween(final String value1, final String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria)this;
        }

        public Criteria andUrlNotBetween(final String value1, final String value2) {
            addCriterion("url not between", value1, value2, "url");
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