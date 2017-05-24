package com.easytoolsoft.easyreport.meta.domain.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public class TaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskExample() {
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

        public Criteria andReportIdsIsNull() {
            addCriterion("report_ids is null");
            return (Criteria)this;
        }

        public Criteria andReportIdsIsNotNull() {
            addCriterion("report_ids is not null");
            return (Criteria)this;
        }

        public Criteria andReportIdsEqualTo(final String value) {
            addCriterion("report_ids =", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsNotEqualTo(final String value) {
            addCriterion("report_ids <>", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsGreaterThan(final String value) {
            addCriterion("report_ids >", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsGreaterThanOrEqualTo(final String value) {
            addCriterion("report_ids >=", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsLessThan(final String value) {
            addCriterion("report_ids <", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsLessThanOrEqualTo(final String value) {
            addCriterion("report_ids <=", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsLike(final String value) {
            addCriterion("report_ids like", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsNotLike(final String value) {
            addCriterion("report_ids not like", value, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsIn(final List<String> values) {
            addCriterion("report_ids in", values, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsNotIn(final List<String> values) {
            addCriterion("report_ids not in", values, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsBetween(final String value1, final String value2) {
            addCriterion("report_ids between", value1, value2, "reportIds");
            return (Criteria)this;
        }

        public Criteria andReportIdsNotBetween(final String value1, final String value2) {
            addCriterion("report_ids not between", value1, value2, "reportIds");
            return (Criteria)this;
        }

        public Criteria andCronExprIsNull() {
            addCriterion("cron_expr is null");
            return (Criteria)this;
        }

        public Criteria andCronExprIsNotNull() {
            addCriterion("cron_expr is not null");
            return (Criteria)this;
        }

        public Criteria andCronExprEqualTo(final String value) {
            addCriterion("cron_expr =", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprNotEqualTo(final String value) {
            addCriterion("cron_expr <>", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprGreaterThan(final String value) {
            addCriterion("cron_expr >", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprGreaterThanOrEqualTo(final String value) {
            addCriterion("cron_expr >=", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprLessThan(final String value) {
            addCriterion("cron_expr <", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprLessThanOrEqualTo(final String value) {
            addCriterion("cron_expr <=", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprLike(final String value) {
            addCriterion("cron_expr like", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprNotLike(final String value) {
            addCriterion("cron_expr not like", value, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprIn(final List<String> values) {
            addCriterion("cron_expr in", values, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprNotIn(final List<String> values) {
            addCriterion("cron_expr not in", values, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprBetween(final String value1, final String value2) {
            addCriterion("cron_expr between", value1, value2, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andCronExprNotBetween(final String value1, final String value2) {
            addCriterion("cron_expr not between", value1, value2, "cronExpr");
            return (Criteria)this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria)this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria)this;
        }

        public Criteria andTypeEqualTo(final Byte value) {
            addCriterion("type =", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotEqualTo(final Byte value) {
            addCriterion("type <>", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeGreaterThan(final Byte value) {
            addCriterion("type >", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(final Byte value) {
            addCriterion("type >=", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeLessThan(final Byte value) {
            addCriterion("type <", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeLessThanOrEqualTo(final Byte value) {
            addCriterion("type <=", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeIn(final List<Byte> values) {
            addCriterion("type in", values, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotIn(final List<Byte> values) {
            addCriterion("type not in", values, "type");
            return (Criteria)this;
        }

        public Criteria andTypeBetween(final Byte value1, final Byte value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotBetween(final Byte value1, final Byte value2) {
            addCriterion("type not between", value1, value2, "type");
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