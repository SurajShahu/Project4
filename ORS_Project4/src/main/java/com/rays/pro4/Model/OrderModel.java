package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

public class OrderModel {

	private static Logger log = Logger.getLogger(UserModel.class);

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = "SELECT MAX(ID) FROM ST_ORDER";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK Started");
		return pk + 1;

	}

	public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		
		String sql = "INSERT INTO ST_ORDER VALUES(?,?,?,?,?,?)";
		Connection conn = null;
		int pk = 0;

		/*
		 * OrderBean existbean = findByLogin(bean.getLogin()); if (existbean != null) {
		 * throw new DuplicateRecordException("login Id already exists");
		 * 
		 * }
		 */

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getOrderName());
			pstmt.setString(3, bean.getOrderType());
			pstmt.setInt(4, bean.getAmount());
			pstmt.setDate(5,new java.sql.Date(bean.getOrderDate().getTime()) );
			pstmt.setString(6, bean.getAddress());

			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	public void delete(OrderBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM ST_ORDER WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/*
	 * public OrderBean findByLogin(String login) throws ApplicationException {
	 * log.debug("Model findByLohin Started"); String sql =
	 * "SELECT * FROM ST_ORDER WHERE login=?"; OrderBean bean = null; Connection
	 * conn = null; try { conn = JDBCDataSource.getConnection(); PreparedStatement
	 * pstmt = conn.prepareStatement(sql); pstmt.setString(1, login); ResultSet rs =
	 * pstmt.executeQuery(); while (rs.next()) { bean = new OrderBean();
	 * bean.setId(rs.getLong(1)); bean.setFirstName(rs.getString(2));
	 * bean.setLastName(rs.getString(3)); bean.setLogin(rs.getString(4));
	 * bean.setPassword(rs.getString(5)); bean.setDob(rs.getDate(6));
	 * bean.setMobileNo(rs.getString(7)); bean.setRoleId(rs.getLong(8));
	 * bean.setUnSuccessfulLogin(rs.getInt(9)); bean.setGender(rs.getString(10));
	 * bean.setLastLogin(rs.getTimestamp(11)); bean.setLock(rs.getString(12));
	 * bean.setRegisterdIP(rs.getString(13)); bean.setLastLoginIP(rs.getString(14));
	 * bean.setCreatedBy(rs.getString(15)); bean.setModifiedBy(rs.getString(16));
	 * bean.setCreatedDatetime(rs.getTimestamp(17));
	 * bean.setModifiedDatetime(rs.getTimestamp(18)); } rs.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * log.error("DataBase Exception .", e); throw new
	 * ApplicationException("Exception: Exception in getting user by Login"); }
	 * finally { JDBCDataSource.closeConnection(conn); }
	 * log.debug("Model findby login end"); return bean; }
	 */
	public OrderBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "SELECT * FROM ST_ORDER WHERE ID=?";
		OrderBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setOrderDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE ST_ORDER SET ORDER_NAME=?,ORDER_TYPE=?,AMOUNT=?,ORDER_DATE=?,ADDRESS=? WHERE ID=?";
		Connection conn = null;
		/*
		 * OrderBean existBean = findByLogin(bean.getLogin()); if (existBean != null &&
		 * !(existBean.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("LoginId is Already Exist"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getOrderName());
			pstmt.setString(2, bean.getOrderType());
			pstmt.setInt(3, bean.getAmount());
			pstmt.setDate(4,new java.sql.Date(bean.getOrderDate().getTime()) );
			pstmt.setString(5, bean.getAddress());
			pstmt.setLong(6, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	public List search(OrderBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ORDER WHERE 1=1");
		if (bean != null) {

			if (bean.getOrderName() != null && bean.getOrderName().length() > 0) {
				sql.append(" AND ORDER_NAME like '" + bean.getOrderName() + "%'");
			}
			if (bean.getOrderType() != null && bean.getOrderType().length() > 0) {
				sql.append(" AND ORDER_TYPE like '" + bean.getOrderType() + "%'");
			}
			if (bean.getOrderDate() != null && bean.getOrderDate().getTime() > 0) {
				sql.append(" AND ORDER_DATE like '" + new java.sql.Date(bean.getOrderDate().getTime()) + "%'");
			}
			if (bean.getAmount() != null && bean.getAmount() > 0) {
				sql.append(" AND AMOUNT like '" + bean.getAmount() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			if (bean.getId()>0) {
				sql.append(" AND ID = '" + bean.getId() + "'");
			}

			/*
			 * if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
			 * sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'"); } if
			 * (bean.getLogin() != null && bean.getLogin().length() > 0) {
			 * sql.append(" AND LOGIN like '" + bean.getLogin() + "%'"); } if
			 * (bean.getRoleId() > 0) { sql.append(" AND ROLE_ID = " + bean.getRoleId()); }
			 * if (bean.getLastName() != null && bean.getLastName().length() > 0) {
			 * sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'"); } if
			 * (bean.getId() > 0) { sql.append(" AND id = " + bean.getId()); }
			 * 
			 * if (bean.getPassword() != null && bean.getPassword().length() > 0) {
			 * sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'"); } if
			 * (bean.getDob() != null && bean.getDob().getDate() > 0) {
			 * sql.append(" AND DOB = " + bean.getGender()); } if (bean.getMobileNo() !=
			 * null && bean.getMobileNo().length() > 0) { sql.append(" AND MOBILE_NO = " +
			 * bean.getMobileNo()); } if (bean.getUnSuccessfulLogin() > 0) {
			 * sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin()); } if
			 * (bean.getGender() != null && bean.getGender().length() > 0) {
			 * sql.append(" AND GENDER like '" + bean.getGender() + "%'"); }
			 * 
			 * if (bean.getDob() != null && bean.getDob().getTime() > 0) { Date d = new
			 * Date(bean.getDob().getTime()); sql.append("AND DOB = '" +
			 * DataUtility.getDateString(d) + "'"); }
			 */

		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setOrderDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_ORDER");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderBean bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setAmount(rs.getInt(4));
				bean.setOrderDate(rs.getDate(5));
				bean.setAddress(rs.getString(6));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
