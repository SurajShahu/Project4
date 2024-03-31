package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import org.apache.log4j.Logger;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

/**
 * JDBC Implementation of UserModel.
 * 
 * @author Suraj Sahu
 *
 */

public class ProductModel {
	private static Logger log = Logger.getLogger(ProductModel.class);

	public int nextPK() throws DatabaseException {

		log.debug("Model nextPK Started");

		String sql = "SELECT MAX(ID) FROM ST_PRODUCT";
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

	public long add(ProductBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = "INSERT INTO ST_PRODUCT VALUES(?,?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		/*
		 * ProductBean existbean = findByLogin(bean.getAddress()); if (existbean != null)
		 * { throw new DuplicateRecordException("login Id already exists");
		 * 
		 * }
		 */

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getProductName());
			pstmt.setString(3, bean.getCompanyName());
			pstmt.setString(4, bean.getAddress());
			pstmt.setInt(5, bean.getAmount());
			

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

	public void delete(ProductBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM ST_PRODUCT WHERE ID=?";
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
	 * public ProductBean findByLogin(String login) throws ApplicationException {
	 * log.debug("Model findByLohin Started"); String sql =
	 * "SELECT * FROM ST_PRODUCT WHERE login=?"; ProductBean bean = null; Connection
	 * conn = null; try { conn = JDBCDataSource.getConnection(); PreparedStatement
	 * pstmt = conn.prepareStatement(sql); pstmt.setString(1, login); ResultSet rs =
	 * pstmt.executeQuery(); while (rs.next()) { bean = new ProductBean();
	 * bean.setId(rs.getLong(1)); bean.setProductName(rs.getString(2));
	 * bean.setCompanyName(rs.getString(3)); bean.setAddress(rs.getString(4));
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
	
	public ProductBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "SELECT * FROM ST_PRODUCT WHERE ID=?";
		ProductBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setProductName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setAddress(rs.getString(4));
				bean.setAmount(rs.getInt(5));
			
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

	public void update(ProductBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE ST_PRODUCT SET PRODUCT_NAME=?,COMPANY_NAME=?,ADDRESS=?,AMOUNT=?  WHERE ID=?";
		Connection conn = null;
		/*
		 * ProductBean existBean = findByLogin(bean.getAddress()); if (existBean != null
		 * && !(existBean.getId() == bean.getId())) { throw new
		 * DuplicateRecordException("LoginId is Already Exist"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getProductName());
			pstmt.setString(2, bean.getCompanyName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setInt(4, bean.getAmount());
			
			pstmt.setLong(5, bean.getId());
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

	public List search(ProductBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(ProductBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PRODUCT WHERE 1=1");
		if (bean != null) {
			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" AND PRODUCT_NAME like '" + bean.getProductName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			
			if (bean.getCompanyName() != null && bean.getCompanyName().length() > 0) {
				sql.append(" AND COMPANY_NAME like '" + bean.getCompanyName() + "%'");
			}
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}


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
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setProductName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setAddress(rs.getString(4));
				bean.setAmount(rs.getInt(5));
			
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
		StringBuffer sql = new StringBuffer("select * from ST_PRODUCT");

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
				ProductBean bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setProductName(rs.getString(2));
				bean.setCompanyName(rs.getString(3));
				bean.setAddress(rs.getString(4));
				bean.setAmount(rs.getInt(5));
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
