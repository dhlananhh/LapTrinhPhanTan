package exercise04.demo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.reactivestreams.client.MongoDatabase;

import exercise04.dao.StaffDAO;
import exercise04.entities.Phone;
import exercise04.entities.Staff;
import exercise04.utils.DBConnection;

public class StaffDemo {
	public static void main (String[] args) throws InterruptedException {
		DBConnection conn = new DBConnection();
		MongoDatabase db = conn.getInstance().getDatabase();
		
		StaffDAO staffDAO = new StaffDAO(db);

		/*
		
		// Insert thêm 1 NV mới
		Staff newStaff = new Staff();
		newStaff.setStaffId(11L);
		newStaff.setFirstName("Kandace");
		newStaff.setLastName("Kingsley");
		
		Phone newPhone = new Phone();
		newPhone.setNumber("(516) 379-5556");
		newPhone.setType("personal");
		
		newStaff.setPhone(newPhone);
		newStaff.setEmail("kandace.kingsley@bikes.shop");
		
		// In KQ insert thêm 1 NV mới
		System.out.println(staffDAO.insertOneNewStaff(newStaff));
		
		
		// Insert thêm nhiều NV mới
		List<Staff> lstNewStaffs = new ArrayList<Staff>();
		
		Staff newStaff1 = new Staff();
		newStaff1.setStaffId(12L);
		newStaff1.setFirstName("Victoria");
		newStaff1.setLastName("Westwood");
		
		Phone newPhone1 = new Phone();
		newPhone1.setNumber("(972) 530-3334");
		newPhone1.setType("personal");
		
		newStaff1.setPhone(newPhone1);
		newStaff1.setEmail("victoria.westwood@bikes.shop");
		
		Staff newStaff2 = new Staff();
		newStaff2.setStaffId(13L);
		newStaff2.setFirstName("Benjamin");
		newStaff2.setLastName("Harrison");
		
		Phone newPhone2 = new Phone();
		newPhone2.setNumber("(972) 530-4445");
		newPhone2.setType("personal");
		
		newStaff1.setPhone(newPhone2);
		newStaff2.setEmail("benjamin.harrison@bikes.shop");
		
		lstNewStaffs.add(newStaff1);
		lstNewStaffs.add(newStaff2);
		
		// In KQ insert thêm nhiều NV mới
		System.out.println(staffDAO.insertManyNewStaffs(lstNewStaffs));
		
		*/
		
		// Lấy thông tin NV khi biết mã
		System.out.println(staffDAO.getStaff(12L));
	}
}
