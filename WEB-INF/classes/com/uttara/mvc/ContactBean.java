package com.uttara.mvc;

public class ContactBean {

	int sl_no;
	String name,email,phNum;
	public int getSl_no() {
		return sl_no;
	}
	public void setSl_no(int sl_no) {
		this.sl_no = sl_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhNum() {
		return phNum;
	}
	public void setPhNum(String phNum) {
		this.phNum = phNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phNum == null) ? 0 : phNum.hashCode());
		result = prime * result + sl_no;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactBean other = (ContactBean) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phNum == null) {
			if (other.phNum != null)
				return false;
		} else if (!phNum.equals(other.phNum))
			return false;
		if (sl_no != other.sl_no)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ContactBean [sl_no=" + sl_no + ", name=" + name + ", email="
				+ email + ", phNum=" + phNum + "]";
	}
	public String validate()
	{
		StringBuilder sb = new StringBuilder();
		if(name==null || name.trim().equals(""))
			sb.append("Name is mandatory!Enter it<br/>");
		if(email==null || email.trim().equals(""))
			sb.append("Email is mandatory!Enter it<br/>");
		if(phNum==null || phNum.trim().equals(""))
			sb.append("Phone num is mandatory!Enter it<br/>");

		String str = sb.toString();
		if(str.equals(""))
			return Constants.SUCCESS;
		else
			return str;
	}
}
