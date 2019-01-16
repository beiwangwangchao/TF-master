/*
 *
 */
package com.lkkhpg.dsis.common.system.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户角色分配组织查询Example.
 * 
 * @author chenjingxiong
 */
public class SysUserRoleAssignExample {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysUserRoleAssignExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUserRoleAssignIdIsNull() {
            addCriterion("USER_ROLE_ASSIGN_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdIsNotNull() {
            addCriterion("USER_ROLE_ASSIGN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdEqualTo(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID =", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdNotEqualTo(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID <>", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdGreaterThan(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID >", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdGreaterThanOrEqualTo(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID >=", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdLessThan(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID <", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdLessThanOrEqualTo(Long value) {
            addCriterion("USER_ROLE_ASSIGN_ID <=", value, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdIn(List<Long> values) {
            addCriterion("USER_ROLE_ASSIGN_ID in", values, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdNotIn(List<Long> values) {
            addCriterion("USER_ROLE_ASSIGN_ID not in", values, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdBetween(Long value1, Long value2) {
            addCriterion("USER_ROLE_ASSIGN_ID between", value1, value2, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserRoleAssignIdNotBetween(Long value1, Long value2) {
            addCriterion("USER_ROLE_ASSIGN_ID not between", value1, value2, "userRoleAssignId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("ROLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("ROLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Long value) {
            addCriterion("ROLE_ID =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Long value) {
            addCriterion("ROLE_ID <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Long value) {
            addCriterion("ROLE_ID >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ROLE_ID >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Long value) {
            addCriterion("ROLE_ID <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Long value) {
            addCriterion("ROLE_ID <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Long> values) {
            addCriterion("ROLE_ID in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Long> values) {
            addCriterion("ROLE_ID not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Long value1, Long value2) {
            addCriterion("ROLE_ID between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Long value1, Long value2) {
            addCriterion("ROLE_ID not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andAssignTypeIsNull() {
            addCriterion("ASSIGN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAssignTypeIsNotNull() {
            addCriterion("ASSIGN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAssignTypeEqualTo(String value) {
            addCriterion("ASSIGN_TYPE =", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotEqualTo(String value) {
            addCriterion("ASSIGN_TYPE <>", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeGreaterThan(String value) {
            addCriterion("ASSIGN_TYPE >", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ASSIGN_TYPE >=", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeLessThan(String value) {
            addCriterion("ASSIGN_TYPE <", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeLessThanOrEqualTo(String value) {
            addCriterion("ASSIGN_TYPE <=", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeLike(String value) {
            addCriterion("ASSIGN_TYPE like", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotLike(String value) {
            addCriterion("ASSIGN_TYPE not like", value, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeIn(List<String> values) {
            addCriterion("ASSIGN_TYPE in", values, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotIn(List<String> values) {
            addCriterion("ASSIGN_TYPE not in", values, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeBetween(String value1, String value2) {
            addCriterion("ASSIGN_TYPE between", value1, value2, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignTypeNotBetween(String value1, String value2) {
            addCriterion("ASSIGN_TYPE not between", value1, value2, "assignType");
            return (Criteria) this;
        }

        public Criteria andAssignValueIsNull() {
            addCriterion("ASSIGN_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andAssignValueIsNotNull() {
            addCriterion("ASSIGN_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andAssignValueEqualTo(Long value) {
            addCriterion("ASSIGN_VALUE =", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueNotEqualTo(Long value) {
            addCriterion("ASSIGN_VALUE <>", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueGreaterThan(Long value) {
            addCriterion("ASSIGN_VALUE >", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueGreaterThanOrEqualTo(Long value) {
            addCriterion("ASSIGN_VALUE >=", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueLessThan(Long value) {
            addCriterion("ASSIGN_VALUE <", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueLessThanOrEqualTo(Long value) {
            addCriterion("ASSIGN_VALUE <=", value, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueIn(List<Long> values) {
            addCriterion("ASSIGN_VALUE in", values, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueNotIn(List<Long> values) {
            addCriterion("ASSIGN_VALUE not in", values, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueBetween(Long value1, Long value2) {
            addCriterion("ASSIGN_VALUE between", value1, value2, "assignValue");
            return (Criteria) this;
        }

        public Criteria andAssignValueNotBetween(Long value1, Long value2) {
            addCriterion("ASSIGN_VALUE not between", value1, value2, "assignValue");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIsNull() {
            addCriterion("DEFAULT_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIsNotNull() {
            addCriterion("DEFAULT_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagEqualTo(String value) {
            addCriterion("DEFAULT_FLAG =", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotEqualTo(String value) {
            addCriterion("DEFAULT_FLAG <>", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagGreaterThan(String value) {
            addCriterion("DEFAULT_FLAG >", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DEFAULT_FLAG >=", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLessThan(String value) {
            addCriterion("DEFAULT_FLAG <", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLessThanOrEqualTo(String value) {
            addCriterion("DEFAULT_FLAG <=", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagLike(String value) {
            addCriterion("DEFAULT_FLAG like", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotLike(String value) {
            addCriterion("DEFAULT_FLAG not like", value, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagIn(List<String> values) {
            addCriterion("DEFAULT_FLAG in", values, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotIn(List<String> values) {
            addCriterion("DEFAULT_FLAG not in", values, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagBetween(String value1, String value2) {
            addCriterion("DEFAULT_FLAG between", value1, value2, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andDefaultFlagNotBetween(String value1, String value2) {
            addCriterion("DEFAULT_FLAG not between", value1, value2, "defaultFlag");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberIsNull() {
            addCriterion("OBJECT_VERSION_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberIsNotNull() {
            addCriterion("OBJECT_VERSION_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberEqualTo(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER =", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberNotEqualTo(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER <>", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberGreaterThan(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER >", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberGreaterThanOrEqualTo(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER >=", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberLessThan(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER <", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberLessThanOrEqualTo(Long value) {
            addCriterion("OBJECT_VERSION_NUMBER <=", value, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberIn(List<Long> values) {
            addCriterion("OBJECT_VERSION_NUMBER in", values, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberNotIn(List<Long> values) {
            addCriterion("OBJECT_VERSION_NUMBER not in", values, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberBetween(Long value1, Long value2) {
            addCriterion("OBJECT_VERSION_NUMBER between", value1, value2, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andObjectVersionNumberNotBetween(Long value1, Long value2) {
            addCriterion("OBJECT_VERSION_NUMBER not between", value1, value2, "objectVersionNumber");
            return (Criteria) this;
        }

        public Criteria andRequestIdIsNull() {
            addCriterion("REQUEST_ID is null");
            return (Criteria) this;
        }

        public Criteria andRequestIdIsNotNull() {
            addCriterion("REQUEST_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRequestIdEqualTo(Long value) {
            addCriterion("REQUEST_ID =", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotEqualTo(Long value) {
            addCriterion("REQUEST_ID <>", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdGreaterThan(Long value) {
            addCriterion("REQUEST_ID >", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("REQUEST_ID >=", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdLessThan(Long value) {
            addCriterion("REQUEST_ID <", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdLessThanOrEqualTo(Long value) {
            addCriterion("REQUEST_ID <=", value, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdIn(List<Long> values) {
            addCriterion("REQUEST_ID in", values, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotIn(List<Long> values) {
            addCriterion("REQUEST_ID not in", values, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdBetween(Long value1, Long value2) {
            addCriterion("REQUEST_ID between", value1, value2, "requestId");
            return (Criteria) this;
        }

        public Criteria andRequestIdNotBetween(Long value1, Long value2) {
            addCriterion("REQUEST_ID not between", value1, value2, "requestId");
            return (Criteria) this;
        }

        public Criteria andProgramIdIsNull() {
            addCriterion("PROGRAM_ID is null");
            return (Criteria) this;
        }

        public Criteria andProgramIdIsNotNull() {
            addCriterion("PROGRAM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProgramIdEqualTo(Long value) {
            addCriterion("PROGRAM_ID =", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdNotEqualTo(Long value) {
            addCriterion("PROGRAM_ID <>", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdGreaterThan(Long value) {
            addCriterion("PROGRAM_ID >", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PROGRAM_ID >=", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdLessThan(Long value) {
            addCriterion("PROGRAM_ID <", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdLessThanOrEqualTo(Long value) {
            addCriterion("PROGRAM_ID <=", value, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdIn(List<Long> values) {
            addCriterion("PROGRAM_ID in", values, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdNotIn(List<Long> values) {
            addCriterion("PROGRAM_ID not in", values, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdBetween(Long value1, Long value2) {
            addCriterion("PROGRAM_ID between", value1, value2, "programId");
            return (Criteria) this;
        }

        public Criteria andProgramIdNotBetween(Long value1, Long value2) {
            addCriterion("PROGRAM_ID not between", value1, value2, "programId");
            return (Criteria) this;
        }

        public Criteria andCreationDateIsNull() {
            addCriterion("CREATION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreationDateIsNotNull() {
            addCriterion("CREATION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreationDateEqualTo(Date value) {
            addCriterion("CREATION_DATE =", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotEqualTo(Date value) {
            addCriterion("CREATION_DATE <>", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateGreaterThan(Date value) {
            addCriterion("CREATION_DATE >", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATION_DATE >=", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateLessThan(Date value) {
            addCriterion("CREATION_DATE <", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATION_DATE <=", value, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateIn(List<Date> values) {
            addCriterion("CREATION_DATE in", values, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotIn(List<Date> values) {
            addCriterion("CREATION_DATE not in", values, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateBetween(Date value1, Date value2) {
            addCriterion("CREATION_DATE between", value1, value2, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreationDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATION_DATE not between", value1, value2, "creationDate");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("CREATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("CREATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("CREATED_BY =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("CREATED_BY <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("CREATED_BY >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("CREATED_BY >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("CREATED_BY <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("CREATED_BY <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("CREATED_BY in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("CREATED_BY not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("CREATED_BY between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("CREATED_BY not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIsNull() {
            addCriterion("LAST_UPDATED_BY is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIsNotNull() {
            addCriterion("LAST_UPDATED_BY is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY =", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY <>", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByGreaterThan(Long value) {
            addCriterion("LAST_UPDATED_BY >", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY >=", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByLessThan(Long value) {
            addCriterion("LAST_UPDATED_BY <", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByLessThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATED_BY <=", value, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByIn(List<Long> values) {
            addCriterion("LAST_UPDATED_BY in", values, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotIn(List<Long> values) {
            addCriterion("LAST_UPDATED_BY not in", values, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATED_BY between", value1, value2, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdatedByNotBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATED_BY not between", value1, value2, "lastUpdatedBy");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("LAST_UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("LAST_UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(Date value) {
            addCriterion("LAST_UPDATE_DATE >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(Date value) {
            addCriterion("LAST_UPDATE_DATE <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("LAST_UPDATE_DATE <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<Date> values) {
            addCriterion("LAST_UPDATE_DATE not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("LAST_UPDATE_DATE not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginIsNull() {
            addCriterion("LAST_UPDATE_LOGIN is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginIsNotNull() {
            addCriterion("LAST_UPDATE_LOGIN is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginEqualTo(Long value) {
            addCriterion("LAST_UPDATE_LOGIN =", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginNotEqualTo(Long value) {
            addCriterion("LAST_UPDATE_LOGIN <>", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginGreaterThan(Long value) {
            addCriterion("LAST_UPDATE_LOGIN >", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginGreaterThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATE_LOGIN >=", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginLessThan(Long value) {
            addCriterion("LAST_UPDATE_LOGIN <", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginLessThanOrEqualTo(Long value) {
            addCriterion("LAST_UPDATE_LOGIN <=", value, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginIn(List<Long> values) {
            addCriterion("LAST_UPDATE_LOGIN in", values, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginNotIn(List<Long> values) {
            addCriterion("LAST_UPDATE_LOGIN not in", values, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATE_LOGIN between", value1, value2, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLoginNotBetween(Long value1, Long value2) {
            addCriterion("LAST_UPDATE_LOGIN not between", value1, value2, "lastUpdateLogin");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryIsNull() {
            addCriterion("ATTRIBUTE_CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryIsNotNull() {
            addCriterion("ATTRIBUTE_CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryEqualTo(String value) {
            addCriterion("ATTRIBUTE_CATEGORY =", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryNotEqualTo(String value) {
            addCriterion("ATTRIBUTE_CATEGORY <>", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryGreaterThan(String value) {
            addCriterion("ATTRIBUTE_CATEGORY >", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_CATEGORY >=", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryLessThan(String value) {
            addCriterion("ATTRIBUTE_CATEGORY <", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryLessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE_CATEGORY <=", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryLike(String value) {
            addCriterion("ATTRIBUTE_CATEGORY like", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryNotLike(String value) {
            addCriterion("ATTRIBUTE_CATEGORY not like", value, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryIn(List<String> values) {
            addCriterion("ATTRIBUTE_CATEGORY in", values, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryNotIn(List<String> values) {
            addCriterion("ATTRIBUTE_CATEGORY not in", values, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_CATEGORY between", value1, value2, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttributeCategoryNotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE_CATEGORY not between", value1, value2, "attributeCategory");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNull() {
            addCriterion("ATTRIBUTE1 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNotNull() {
            addCriterion("ATTRIBUTE1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute1EqualTo(String value) {
            addCriterion("ATTRIBUTE1 =", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotEqualTo(String value) {
            addCriterion("ATTRIBUTE1 <>", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThan(String value) {
            addCriterion("ATTRIBUTE1 >", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE1 >=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThan(String value) {
            addCriterion("ATTRIBUTE1 <", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE1 <=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Like(String value) {
            addCriterion("ATTRIBUTE1 like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotLike(String value) {
            addCriterion("ATTRIBUTE1 not like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1In(List<String> values) {
            addCriterion("ATTRIBUTE1 in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotIn(List<String> values) {
            addCriterion("ATTRIBUTE1 not in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Between(String value1, String value2) {
            addCriterion("ATTRIBUTE1 between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE1 not between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNull() {
            addCriterion("ATTRIBUTE2 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNotNull() {
            addCriterion("ATTRIBUTE2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute2EqualTo(String value) {
            addCriterion("ATTRIBUTE2 =", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotEqualTo(String value) {
            addCriterion("ATTRIBUTE2 <>", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThan(String value) {
            addCriterion("ATTRIBUTE2 >", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE2 >=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThan(String value) {
            addCriterion("ATTRIBUTE2 <", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE2 <=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Like(String value) {
            addCriterion("ATTRIBUTE2 like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotLike(String value) {
            addCriterion("ATTRIBUTE2 not like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2In(List<String> values) {
            addCriterion("ATTRIBUTE2 in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotIn(List<String> values) {
            addCriterion("ATTRIBUTE2 not in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Between(String value1, String value2) {
            addCriterion("ATTRIBUTE2 between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE2 not between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNull() {
            addCriterion("ATTRIBUTE3 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNotNull() {
            addCriterion("ATTRIBUTE3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute3EqualTo(String value) {
            addCriterion("ATTRIBUTE3 =", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotEqualTo(String value) {
            addCriterion("ATTRIBUTE3 <>", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThan(String value) {
            addCriterion("ATTRIBUTE3 >", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE3 >=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThan(String value) {
            addCriterion("ATTRIBUTE3 <", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE3 <=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Like(String value) {
            addCriterion("ATTRIBUTE3 like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotLike(String value) {
            addCriterion("ATTRIBUTE3 not like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3In(List<String> values) {
            addCriterion("ATTRIBUTE3 in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotIn(List<String> values) {
            addCriterion("ATTRIBUTE3 not in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Between(String value1, String value2) {
            addCriterion("ATTRIBUTE3 between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE3 not between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute4IsNull() {
            addCriterion("ATTRIBUTE4 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute4IsNotNull() {
            addCriterion("ATTRIBUTE4 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute4EqualTo(String value) {
            addCriterion("ATTRIBUTE4 =", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotEqualTo(String value) {
            addCriterion("ATTRIBUTE4 <>", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4GreaterThan(String value) {
            addCriterion("ATTRIBUTE4 >", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE4 >=", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4LessThan(String value) {
            addCriterion("ATTRIBUTE4 <", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE4 <=", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4Like(String value) {
            addCriterion("ATTRIBUTE4 like", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotLike(String value) {
            addCriterion("ATTRIBUTE4 not like", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4In(List<String> values) {
            addCriterion("ATTRIBUTE4 in", values, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotIn(List<String> values) {
            addCriterion("ATTRIBUTE4 not in", values, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4Between(String value1, String value2) {
            addCriterion("ATTRIBUTE4 between", value1, value2, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE4 not between", value1, value2, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute5IsNull() {
            addCriterion("ATTRIBUTE5 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute5IsNotNull() {
            addCriterion("ATTRIBUTE5 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute5EqualTo(String value) {
            addCriterion("ATTRIBUTE5 =", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotEqualTo(String value) {
            addCriterion("ATTRIBUTE5 <>", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5GreaterThan(String value) {
            addCriterion("ATTRIBUTE5 >", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE5 >=", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5LessThan(String value) {
            addCriterion("ATTRIBUTE5 <", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE5 <=", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5Like(String value) {
            addCriterion("ATTRIBUTE5 like", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotLike(String value) {
            addCriterion("ATTRIBUTE5 not like", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5In(List<String> values) {
            addCriterion("ATTRIBUTE5 in", values, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotIn(List<String> values) {
            addCriterion("ATTRIBUTE5 not in", values, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5Between(String value1, String value2) {
            addCriterion("ATTRIBUTE5 between", value1, value2, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE5 not between", value1, value2, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute6IsNull() {
            addCriterion("ATTRIBUTE6 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute6IsNotNull() {
            addCriterion("ATTRIBUTE6 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute6EqualTo(String value) {
            addCriterion("ATTRIBUTE6 =", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6NotEqualTo(String value) {
            addCriterion("ATTRIBUTE6 <>", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6GreaterThan(String value) {
            addCriterion("ATTRIBUTE6 >", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE6 >=", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6LessThan(String value) {
            addCriterion("ATTRIBUTE6 <", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE6 <=", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6Like(String value) {
            addCriterion("ATTRIBUTE6 like", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6NotLike(String value) {
            addCriterion("ATTRIBUTE6 not like", value, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6In(List<String> values) {
            addCriterion("ATTRIBUTE6 in", values, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6NotIn(List<String> values) {
            addCriterion("ATTRIBUTE6 not in", values, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6Between(String value1, String value2) {
            addCriterion("ATTRIBUTE6 between", value1, value2, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute6NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE6 not between", value1, value2, "attribute6");
            return (Criteria) this;
        }

        public Criteria andAttribute7IsNull() {
            addCriterion("ATTRIBUTE7 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute7IsNotNull() {
            addCriterion("ATTRIBUTE7 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute7EqualTo(String value) {
            addCriterion("ATTRIBUTE7 =", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7NotEqualTo(String value) {
            addCriterion("ATTRIBUTE7 <>", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7GreaterThan(String value) {
            addCriterion("ATTRIBUTE7 >", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE7 >=", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7LessThan(String value) {
            addCriterion("ATTRIBUTE7 <", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE7 <=", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7Like(String value) {
            addCriterion("ATTRIBUTE7 like", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7NotLike(String value) {
            addCriterion("ATTRIBUTE7 not like", value, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7In(List<String> values) {
            addCriterion("ATTRIBUTE7 in", values, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7NotIn(List<String> values) {
            addCriterion("ATTRIBUTE7 not in", values, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7Between(String value1, String value2) {
            addCriterion("ATTRIBUTE7 between", value1, value2, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute7NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE7 not between", value1, value2, "attribute7");
            return (Criteria) this;
        }

        public Criteria andAttribute8IsNull() {
            addCriterion("ATTRIBUTE8 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute8IsNotNull() {
            addCriterion("ATTRIBUTE8 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute8EqualTo(String value) {
            addCriterion("ATTRIBUTE8 =", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8NotEqualTo(String value) {
            addCriterion("ATTRIBUTE8 <>", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8GreaterThan(String value) {
            addCriterion("ATTRIBUTE8 >", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE8 >=", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8LessThan(String value) {
            addCriterion("ATTRIBUTE8 <", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE8 <=", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8Like(String value) {
            addCriterion("ATTRIBUTE8 like", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8NotLike(String value) {
            addCriterion("ATTRIBUTE8 not like", value, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8In(List<String> values) {
            addCriterion("ATTRIBUTE8 in", values, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8NotIn(List<String> values) {
            addCriterion("ATTRIBUTE8 not in", values, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8Between(String value1, String value2) {
            addCriterion("ATTRIBUTE8 between", value1, value2, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute8NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE8 not between", value1, value2, "attribute8");
            return (Criteria) this;
        }

        public Criteria andAttribute9IsNull() {
            addCriterion("ATTRIBUTE9 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute9IsNotNull() {
            addCriterion("ATTRIBUTE9 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute9EqualTo(String value) {
            addCriterion("ATTRIBUTE9 =", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9NotEqualTo(String value) {
            addCriterion("ATTRIBUTE9 <>", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9GreaterThan(String value) {
            addCriterion("ATTRIBUTE9 >", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE9 >=", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9LessThan(String value) {
            addCriterion("ATTRIBUTE9 <", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE9 <=", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9Like(String value) {
            addCriterion("ATTRIBUTE9 like", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9NotLike(String value) {
            addCriterion("ATTRIBUTE9 not like", value, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9In(List<String> values) {
            addCriterion("ATTRIBUTE9 in", values, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9NotIn(List<String> values) {
            addCriterion("ATTRIBUTE9 not in", values, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9Between(String value1, String value2) {
            addCriterion("ATTRIBUTE9 between", value1, value2, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute9NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE9 not between", value1, value2, "attribute9");
            return (Criteria) this;
        }

        public Criteria andAttribute10IsNull() {
            addCriterion("ATTRIBUTE10 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute10IsNotNull() {
            addCriterion("ATTRIBUTE10 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute10EqualTo(String value) {
            addCriterion("ATTRIBUTE10 =", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10NotEqualTo(String value) {
            addCriterion("ATTRIBUTE10 <>", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10GreaterThan(String value) {
            addCriterion("ATTRIBUTE10 >", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE10 >=", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10LessThan(String value) {
            addCriterion("ATTRIBUTE10 <", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE10 <=", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10Like(String value) {
            addCriterion("ATTRIBUTE10 like", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10NotLike(String value) {
            addCriterion("ATTRIBUTE10 not like", value, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10In(List<String> values) {
            addCriterion("ATTRIBUTE10 in", values, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10NotIn(List<String> values) {
            addCriterion("ATTRIBUTE10 not in", values, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10Between(String value1, String value2) {
            addCriterion("ATTRIBUTE10 between", value1, value2, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute10NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE10 not between", value1, value2, "attribute10");
            return (Criteria) this;
        }

        public Criteria andAttribute11IsNull() {
            addCriterion("ATTRIBUTE11 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute11IsNotNull() {
            addCriterion("ATTRIBUTE11 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute11EqualTo(String value) {
            addCriterion("ATTRIBUTE11 =", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11NotEqualTo(String value) {
            addCriterion("ATTRIBUTE11 <>", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11GreaterThan(String value) {
            addCriterion("ATTRIBUTE11 >", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE11 >=", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11LessThan(String value) {
            addCriterion("ATTRIBUTE11 <", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE11 <=", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11Like(String value) {
            addCriterion("ATTRIBUTE11 like", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11NotLike(String value) {
            addCriterion("ATTRIBUTE11 not like", value, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11In(List<String> values) {
            addCriterion("ATTRIBUTE11 in", values, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11NotIn(List<String> values) {
            addCriterion("ATTRIBUTE11 not in", values, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11Between(String value1, String value2) {
            addCriterion("ATTRIBUTE11 between", value1, value2, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute11NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE11 not between", value1, value2, "attribute11");
            return (Criteria) this;
        }

        public Criteria andAttribute12IsNull() {
            addCriterion("ATTRIBUTE12 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute12IsNotNull() {
            addCriterion("ATTRIBUTE12 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute12EqualTo(String value) {
            addCriterion("ATTRIBUTE12 =", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12NotEqualTo(String value) {
            addCriterion("ATTRIBUTE12 <>", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12GreaterThan(String value) {
            addCriterion("ATTRIBUTE12 >", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE12 >=", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12LessThan(String value) {
            addCriterion("ATTRIBUTE12 <", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE12 <=", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12Like(String value) {
            addCriterion("ATTRIBUTE12 like", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12NotLike(String value) {
            addCriterion("ATTRIBUTE12 not like", value, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12In(List<String> values) {
            addCriterion("ATTRIBUTE12 in", values, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12NotIn(List<String> values) {
            addCriterion("ATTRIBUTE12 not in", values, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12Between(String value1, String value2) {
            addCriterion("ATTRIBUTE12 between", value1, value2, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute12NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE12 not between", value1, value2, "attribute12");
            return (Criteria) this;
        }

        public Criteria andAttribute13IsNull() {
            addCriterion("ATTRIBUTE13 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute13IsNotNull() {
            addCriterion("ATTRIBUTE13 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute13EqualTo(String value) {
            addCriterion("ATTRIBUTE13 =", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13NotEqualTo(String value) {
            addCriterion("ATTRIBUTE13 <>", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13GreaterThan(String value) {
            addCriterion("ATTRIBUTE13 >", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE13 >=", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13LessThan(String value) {
            addCriterion("ATTRIBUTE13 <", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE13 <=", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13Like(String value) {
            addCriterion("ATTRIBUTE13 like", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13NotLike(String value) {
            addCriterion("ATTRIBUTE13 not like", value, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13In(List<String> values) {
            addCriterion("ATTRIBUTE13 in", values, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13NotIn(List<String> values) {
            addCriterion("ATTRIBUTE13 not in", values, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13Between(String value1, String value2) {
            addCriterion("ATTRIBUTE13 between", value1, value2, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute13NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE13 not between", value1, value2, "attribute13");
            return (Criteria) this;
        }

        public Criteria andAttribute14IsNull() {
            addCriterion("ATTRIBUTE14 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute14IsNotNull() {
            addCriterion("ATTRIBUTE14 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute14EqualTo(String value) {
            addCriterion("ATTRIBUTE14 =", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14NotEqualTo(String value) {
            addCriterion("ATTRIBUTE14 <>", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14GreaterThan(String value) {
            addCriterion("ATTRIBUTE14 >", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE14 >=", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14LessThan(String value) {
            addCriterion("ATTRIBUTE14 <", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE14 <=", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14Like(String value) {
            addCriterion("ATTRIBUTE14 like", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14NotLike(String value) {
            addCriterion("ATTRIBUTE14 not like", value, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14In(List<String> values) {
            addCriterion("ATTRIBUTE14 in", values, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14NotIn(List<String> values) {
            addCriterion("ATTRIBUTE14 not in", values, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14Between(String value1, String value2) {
            addCriterion("ATTRIBUTE14 between", value1, value2, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute14NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE14 not between", value1, value2, "attribute14");
            return (Criteria) this;
        }

        public Criteria andAttribute15IsNull() {
            addCriterion("ATTRIBUTE15 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute15IsNotNull() {
            addCriterion("ATTRIBUTE15 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute15EqualTo(String value) {
            addCriterion("ATTRIBUTE15 =", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15NotEqualTo(String value) {
            addCriterion("ATTRIBUTE15 <>", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15GreaterThan(String value) {
            addCriterion("ATTRIBUTE15 >", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15GreaterThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE15 >=", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15LessThan(String value) {
            addCriterion("ATTRIBUTE15 <", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15LessThanOrEqualTo(String value) {
            addCriterion("ATTRIBUTE15 <=", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15Like(String value) {
            addCriterion("ATTRIBUTE15 like", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15NotLike(String value) {
            addCriterion("ATTRIBUTE15 not like", value, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15In(List<String> values) {
            addCriterion("ATTRIBUTE15 in", values, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15NotIn(List<String> values) {
            addCriterion("ATTRIBUTE15 not in", values, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15Between(String value1, String value2) {
            addCriterion("ATTRIBUTE15 between", value1, value2, "attribute15");
            return (Criteria) this;
        }

        public Criteria andAttribute15NotBetween(String value1, String value2) {
            addCriterion("ATTRIBUTE15 not between", value1, value2, "attribute15");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     *
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
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

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}