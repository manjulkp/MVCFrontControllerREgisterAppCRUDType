package com.uttara.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {

	public String register(RegBean bean)
	{
		System.out.println("in Models->register() bean = "+bean);
		/*
		 * 1) input validate the bean
		 * 2) business validations
		 * 3) apply business logic
		 * 4) return msg
		 * 
		 */
		String msg = bean.validate();
		if(msg.equals(Constants.SUCCESS))
		{
			//input validation has succeeded...
			//apply business validation...
			
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps_sql=null,ps_ins=null;
//check if connection is null ,then display error 
			try
			{
				con = JDBCHelper.getConnection();
				
				ps_sql = con.prepareStatement("select * from reg_users where email = ?");
				ps_sql.setString(1, bean.getEmail());
				ps_sql.execute();
				
				rs = ps_sql.getResultSet();
				if(rs.next())
				{
					//there is at least 1 row...user email is duplicate!!!
					return "Email id is a duplicate!Give another!!";
				}
				else
				{
					//no email exists in db
					//insert the data
					ps_ins = con.prepareStatement("insert into reg_users(name,email,pass) values(?,?,?)");
					ps_ins.setString(1, bean.getUname());
					ps_ins.setString(2, bean.getEmail());
					ps_ins.setString(3, bean.getPass());
					ps_ins.execute();
					
					return Constants.SUCCESS;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return "Oops something bad happened "+e.getMessage();
			}
	
		}
		else
			return msg;
		
	}

	public String authenticate(LoginBean bean) {
		
		/*
		 * 1) perform input validations
		 * 2) perform business validations/logic
		 * 
		 */
		String msg = bean.validate();
		if(msg.equals(Constants.SUCCESS))
		{
			Connection con = null;
			PreparedStatement ps_sql = null;
			ResultSet rs = null;
			try
			{
				con = JDBCHelper.getConnection();
				ps_sql = con.prepareStatement("select * from reg_users where email = ? and pass = ?");
				ps_sql.setString(1, bean.getEmail());
				ps_sql.setString(2, bean.getPass());
				ps_sql.execute();
				
				rs = ps_sql.getResultSet();
				if(rs.next())
					return Constants.SUCCESS;
				else
					return "Email/Pass combination is invalid!Try again!!";
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return "Oops something bad happened! "+e.getMessage();
			}
			finally
			{
				JDBCHelper.close(rs);
				JDBCHelper.close(ps_sql);
				JDBCHelper.close(con);
			}
		}
		else
			return msg;
	}

	public List<ContactBean> getContacts(String email) {
		
		List<ContactBean> beans = new ArrayList<ContactBean>();
		
		Connection con = null;
		PreparedStatement ps_sql = null;
		ResultSet rs = null;
		try
		{
			con = JDBCHelper.getConnection();
			ps_sql = con.prepareStatement("select * from contact where reg_sl = (select sl_no from reg_users where email = ?)");
			ps_sql.setString(1,email);
			ps_sql.execute();
			
			rs = ps_sql.getResultSet();
			while(rs.next())
			{
				ContactBean bean = new ContactBean();
				bean.setSl_no(rs.getInt("sl_no"));
				bean.setName(rs.getString("name"));
				bean.setEmail(rs.getString("email"));
				bean.setPhNum(rs.getString("phnums"));
				beans.add(bean);
			}
			return beans;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
			throw new RuntimeException("oops theres been a problem "+e.getMessage());
		}
		finally
		{
			JDBCHelper.close(rs);
			JDBCHelper.close(ps_sql);
			JDBCHelper.close(con);
		}

	}

	public String addContact(ContactBean bean,String email) {
		
		String msg = bean.validate();
		if(msg.equals(Constants.SUCCESS))
		{
			Connection con = null;
			ResultSet rs = null;
			PreparedStatement ps_sql=null,ps_ins=null;
			try
			{
				con = JDBCHelper.getConnection();
				ps_ins = con.prepareStatement("insert into contact(reg_sl,name,email,phnums) values((select sl_no from reg_users where email = ?),?,?,?)");
					
				ps_ins.setString(1, email);
				ps_ins.setString(2, bean.getName());
				ps_ins.setString(3, bean.getEmail());
				ps_ins.setString(4, bean.getPhNum());
				ps_ins.execute();
					
				return Constants.SUCCESS;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return "Oops something bad happened "+e.getMessage();
			}
			finally
			{
				JDBCHelper.close(rs);
				JDBCHelper.close(ps_sql);
				JDBCHelper.close(con);
			}
		}
		else
			return msg;
		
	}
}





