package dao;

import java.util.List;

import entity.Staff;

public interface StaffDAO {
	public boolean addStaff (Staff staff);
	public boolean updateStaff (Staff staff);
	public boolean deleteStaff (Long id);
	public Staff getStaffById (Long id);
	public int findStaffById (Long id);
	public List<Staff> getAllStaffs();
}
