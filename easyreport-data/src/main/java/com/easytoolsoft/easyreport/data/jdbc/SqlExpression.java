package com.easytoolsoft.easyreport.data.jdbc;

import java.util.List;

import com.easytoolsoft.easyreport.data.mapping.ColumnProperty;

public class SqlExpression {

	private String commandText;
	private Object[] parameters;
	private int[] paramTypes;
	private List<List<ColumnProperty>> batchParameters;

	/**
	 *
	 * @param commandText sql语句
	 * @param batchParameters sql语句对应的参数集合
	 */
	public SqlExpression(String commandText, List<List<ColumnProperty>> batchParameters) {
		this.commandText = commandText;
		this.batchParameters = batchParameters;
	}

	/**
	 *
	 * @param commandText sql语句
	 * @param parameters sql语句对应的参数
	 * @param paramTypes sql语句参数对应的数据类型
	 */
	public SqlExpression(String commandText, Object[] parameters, int[] paramTypes) {
		this.commandText = commandText;
		this.parameters = parameters;
		this.paramTypes = paramTypes;
	}

	/**
	 * @return
	 */
	public String getCommandText() {
		return this.commandText;
	}

	/**
	 * @param
	 */
	public void setCommandText(final String CommandText) {
		this.commandText = CommandText;
	}

	/**
	 * @return
	 */
	public Object[] getParameters() {
		return this.parameters;
	}

	/**
	 * @param
	 */
	public void setParameters(final Object[] Parameters) {
		this.parameters = Parameters;
	}

	/**
	 * @return
	 */
	public int[] getParamTypes() {
		return this.paramTypes;
	}

	/**
	 * @param
	 */
	public void setParamTypes(final int[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	/**
	 *
	 * @return
	 */
	public List<List<ColumnProperty>> getBatchParameters() {
		return this.batchParameters;
	}

	/**
	 *
	 * @param batchParameters
	 */
	public void setBatchParameters(List<List<ColumnProperty>> batchParameters) {
		this.batchParameters = batchParameters;
	}
}
