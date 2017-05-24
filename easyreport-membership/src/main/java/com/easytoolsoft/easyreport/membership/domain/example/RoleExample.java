package com.easytoolsoft.easyreport.membership.domain.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tom Deng
 * @date 2017-03-25
 */
public class RoleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RoleExample() {
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

        public Criteria andModulesIsNull() {
            addCriterion("modules is null");
            return (Criteria)this;
        }

        public Criteria andModulesIsNotNull() {
            addCriterion("modules is not null");
            return (Criteria)this;
        }

        public Criteria andModulesEqualTo(final String value) {
            addCriterion("modules =", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesNotEqualTo(final String value) {
            addCriterion("modules <>", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesGreaterThan(final String value) {
            addCriterion("modules >", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesGreaterThanOrEqualTo(final String value) {
            addCriterion("modules >=", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesLessThan(final String value) {
            addCriterion("modules <", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesLessThanOrEqualTo(final String value) {
            addCriterion("modules <=", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesLike(final String value) {
            addCriterion("modules like", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesNotLike(final String value) {
            addCriterion("modules not like", value, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesIn(final List<String> values) {
            addCriterion("modules in", values, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesNotIn(final List<String> values) {
            addCriterion("modules not in", values, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesBetween(final String value1, final String value2) {
            addCriterion("modules between", value1, value2, "modules");
            return (Criteria)this;
        }

        public Criteria andModulesNotBetween(final String value1, final String value2) {
            addCriterion("modules not between", value1, value2, "modules");
            return (Criteria)this;
        }

        public Criteria andPermissionsIsNull() {
            addCriterion("permissions is null");
            return (Criteria)this;
        }

        public Criteria andPermissionsIsNotNull() {
            addCriterion("permissions is not null");
            return (Criteria)this;
        }

        public Criteria andPermissionsEqualTo(final String value) {
            addCriterion("permissions =", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsNotEqualTo(final String value) {
            addCriterion("permissions <>", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsGreaterThan(final String value) {
            addCriterion("permissions >", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsGreaterThanOrEqualTo(final String value) {
            addCriterion("permissions >=", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsLessThan(final String value) {
            addCriterion("permissions <", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsLessThanOrEqualTo(final String value) {
            addCriterion("permissions <=", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsLike(final String value) {
            addCriterion("permissions like", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsNotLike(final String value) {
            addCriterion("permissions not like", value, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsIn(final List<String> values) {
            addCriterion("permissions in", values, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsNotIn(final List<String> values) {
            addCriterion("permissions not in", values, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsBetween(final String value1, final String value2) {
            addCriterion("permissions between", value1, value2, "permissions");
            return (Criteria)this;
        }

        public Criteria andPermissionsNotBetween(final String value1, final String value2) {
            addCriterion("permissions not between", value1, value2, "permissions");
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

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria)this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria)this;
        }

        public Criteria andCodeEqualTo(final String value) {
            addCriterion("code =", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotEqualTo(final String value) {
            addCriterion("code <>", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeGreaterThan(final String value) {
            addCriterion("code >", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(final String value) {
            addCriterion("code >=", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLessThan(final String value) {
            addCriterion("code <", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLessThanOrEqualTo(final String value) {
            addCriterion("code <=", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeLike(final String value) {
            addCriterion("code like", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotLike(final String value) {
            addCriterion("code not like", value, "code");
            return (Criteria)this;
        }

        public Criteria andCodeIn(final List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotIn(final List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria)this;
        }

        public Criteria andCodeBetween(final String value1, final String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria)this;
        }

        public Criteria andCodeNotBetween(final String value1, final String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria)this;
        }

        public Criteria andIsSystemIsNull() {
            addCriterion("is_system is null");
            return (Criteria)this;
        }

        public Criteria andIsSystemIsNotNull() {
            addCriterion("is_system is not null");
            return (Criteria)this;
        }

        public Criteria andIsSystemEqualTo(final Byte value) {
            addCriterion("is_system =", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemNotEqualTo(final Byte value) {
            addCriterion("is_system <>", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemGreaterThan(final Byte value) {
            addCriterion("is_system >", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemGreaterThanOrEqualTo(final Byte value) {
            addCriterion("is_system >=", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemLessThan(final Byte value) {
            addCriterion("is_system <", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemLessThanOrEqualTo(final Byte value) {
            addCriterion("is_system <=", value, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemIn(final List<Byte> values) {
            addCriterion("is_system in", values, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemNotIn(final List<Byte> values) {
            addCriterion("is_system not in", values, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemBetween(final Byte value1, final Byte value2) {
            addCriterion("is_system between", value1, value2, "isSystem");
            return (Criteria)this;
        }

        public Criteria andIsSystemNotBetween(final Byte value1, final Byte value2) {
            addCriterion("is_system not between", value1, value2, "isSystem");
            return (Criteria)this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria)this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria)this;
        }

        public Criteria andStatusEqualTo(final Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotEqualTo(final Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusGreaterThan(final Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(final Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusLessThan(final Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusLessThanOrEqualTo(final Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusIn(final List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotIn(final List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria)this;
        }

        public Criteria andStatusBetween(final Byte value1, final Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotBetween(final Byte value1, final Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria)this;
        }

        public Criteria andSequenceIsNull() {
            addCriterion("sequence is null");
            return (Criteria)this;
        }

        public Criteria andSequenceIsNotNull() {
            addCriterion("sequence is not null");
            return (Criteria)this;
        }

        public Criteria andSequenceEqualTo(final Integer value) {
            addCriterion("sequence =", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceNotEqualTo(final Integer value) {
            addCriterion("sequence <>", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceGreaterThan(final Integer value) {
            addCriterion("sequence >", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceGreaterThanOrEqualTo(final Integer value) {
            addCriterion("sequence >=", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceLessThan(final Integer value) {
            addCriterion("sequence <", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceLessThanOrEqualTo(final Integer value) {
            addCriterion("sequence <=", value, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceIn(final List<Integer> values) {
            addCriterion("sequence in", values, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceNotIn(final List<Integer> values) {
            addCriterion("sequence not in", values, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceBetween(final Integer value1, final Integer value2) {
            addCriterion("sequence between", value1, value2, "sequence");
            return (Criteria)this;
        }

        public Criteria andSequenceNotBetween(final Integer value1, final Integer value2) {
            addCriterion("sequence not between", value1, value2, "sequence");
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

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria)this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria)this;
        }

        public Criteria andCreateUserEqualTo(final String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserNotEqualTo(final String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserGreaterThan(final String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(final String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserLessThan(final String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(final String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserLike(final String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserNotLike(final String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserIn(final List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserNotIn(final List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserBetween(final String value1, final String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria)this;
        }

        public Criteria andCreateUserNotBetween(final String value1, final String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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