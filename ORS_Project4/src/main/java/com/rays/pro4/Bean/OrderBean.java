package com.rays.pro4.Bean;

import java.util.Date;

public class OrderBean extends BaseBean {

	private String orderName;
	private String orderType;

	private Date orderDate;
	private Integer amount;

	private String address;

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return address;
		/* return orderDate + ""; */
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return address;
		//return orderDate + "";
	}

}
