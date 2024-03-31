package com.rays.pro4.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OrderListCtl", urlPatterns = { "/ctl/OrderListCtl" })
public class OrderListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(OrderListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	protected void preload(HttpServletRequest request) {

		OrderModel umodel = new OrderModel();

		try {
			List olist = umodel.list(0, 0);

			request.setAttribute("OrderList", olist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		OrderBean bean = new OrderBean();
		bean.setOrderName(DataUtility.getString(request.getParameter("orderName")));
		bean.setOrderType(DataUtility.getString(request.getParameter("orderType")));
		bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		bean.setId(DataUtility.getInt(request.getParameter("searchId")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = request.getParameter("orderDate");
		if (date1 != null) {
			try {
				bean.setOrderDate(sdf.parse(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		String date = request.getParameter("orderDate1");
		if (date != null) {
			try {
				bean.setOrderDate(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		/*
		 * bean.setRoleId(DataUtility.getLong(request.getParameter("roleid")));
		 * bean.setLogin(DataUtility.getString(request.getParameter("loginid")));
		 * bean.setDob(DataUtility.getDate(request.getParameter("dateofb")));
		 */
		return bean;
	}

	/**
	 * Contains Display logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("OrderListCtl doGet Start");
		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		OrderBean bean = (OrderBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		OrderModel model = new OrderModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo + 1, pageSize);
			request.setAttribute("nextlist", nextList.size());
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("OrderListCtl doGet End");
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("OrderListCtl doPost Start");
		List list;
		List nextList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("opretion ....." + op);
		OrderBean bean = (OrderBean) populateBean(request);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		OrderModel model = new OrderModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
			System.out.println("search chali...!!!!!");
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				OrderBean deletebean = new OrderBean();
				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("Order is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {

			list = model.search(bean, pageNo, pageSize);
			System.out.println("list chali.......");

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		System.out.println("list in.....");
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println("Empty list...!!!!");
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		log.debug("OrderListCtl doGet End");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.ORDER_LIST_VIEW;
	}

}
