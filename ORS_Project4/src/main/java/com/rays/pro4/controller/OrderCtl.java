package com.rays.pro4.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl{

	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(OrderCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	/*
	 * @Override protected void preload(HttpServletRequest request) {
	 * System.out.println("uctl preload"); RoleModel model = new RoleModel(); try {
	 * List l = model.list(); request.setAttribute("roleList", l); } catch
	 * (ApplicationException e) { log.error(e); } }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		System.out.println("uctl Validate");
		log.debug("OrderCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("orderName"))) {
			request.setAttribute("orderName", PropertyReader.getValue("error.require", "Order Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("orderName"))) {
			request.setAttribute("orderName","Order name must contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("orderType"))) {
			request.setAttribute("orderType", PropertyReader.getValue("error.require", "Order Type"));
			pass = false;
		} 
		
		
		if (DataValidator.isNull(request.getParameter("amount"))) {
			request.setAttribute("amount", PropertyReader.getValue("error.require", "Amount"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("orderDate"))) {
			request.setAttribute("orderDate", PropertyReader.getValue("error.require", "Order date"));
			pass = false;
		} 

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("orderType", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		} 
		
		log.debug("OrderCtl Method validate Ended");

		return pass;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */

	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println(" uctl Base bean P bean");
		log.debug("OrderCtl Method populatebean Started");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		OrderBean bean = new OrderBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setOrderName(DataUtility.getString(request.getParameter("orderName")));

		bean.setOrderType(DataUtility.getString(request.getParameter("orderType")));

		bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		
		String date=request.getParameter("orderDate");
		if(date!=null)
		{
			try {
				bean.setOrderDate(sdf.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		bean.setAddress(DataUtility.getString(request.getParameter("address")));

		populateDTO(bean, request);

		log.debug("OrderCtl Method populatebean Ended");

		return bean;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("OrderCtl Method doGet Started");
		System.out.println("u ctl do get 1111111");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		OrderModel model = new OrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0 condition");
			OrderBean bean;
			try {
				bean = model.findByPK(id);
				System.out.println("Ankit11111111111");
				System.out.println(bean);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("OrderCtl Method doGet Ended");
		ServletUtility.forward(getView(), request, response);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("uctl Do Post");

		log.debug("OrderCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		System.out.println(">>>><<<<>><<><<><<><>**********" + id + op);

		OrderModel model = new OrderModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OrderBean bean = (OrderBean) populateBean(request);
			System.out.println(" ctl DoPost 11111111");

			try {
				if (id > 0) {
					model.update(bean);
					long id2=(long) bean.getId();
					bean=model.findByPK(id2);
					ServletUtility.setBean(bean, request);
					System.out.println("  ctl DoPost 222222");
					ServletUtility.setSuccessMessage("Order is successfully Updated", request);

				} else {
					System.out.println("  ctl DoPost 33333");
					long pk = model.add(bean);
					//bean.setId(pk);
					//ServletUtility.setBean(bean, request);

					ServletUtility.setSuccessMessage("Order is successfully Added", request);
					//ServletUtility.setSuccessMessage("Order "+bean.getOrderName() +" is successfully Added", request);
					//ServletUtility.forward(getView(), request, response);
					//bean.setId(pk);
				}
				/*
				 * ServletUtility.setBean(bean, request);
				 * ServletUtility.setSuccessMessage("Order is successfully saved", request);
				 */

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				System.out.println(" U ctl D post 4444444");
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println(" ctl D p 5555555");

			OrderBean bean = (OrderBean) populateBean(request);
			try {
				model.delete(bean);
				System.out.println("ctl D Post  6666666");
				ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println("ctl Do post 77777");
			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		}
		log.debug("OrderCtl Method doPostEnded");
		ServletUtility.forward(getView(), request, response);


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.ORDER_VIEW;
	}

}
