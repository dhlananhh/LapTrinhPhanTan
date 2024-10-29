package services;


import java.util.List;

import dao.StaffDAO;
import entity.Staff;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class StaffService implements StaffDAO {
	private EntityManager entityManager;
	
	
	public StaffService (EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	public boolean addStaff (Staff staff) {
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			entityManager.persist(staff);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	public boolean updateStaff(Staff staff) {
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			entityManager.merge(staff);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		return false;
	}
	
	
	public boolean deleteStaff(Staff staff) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			entityManager.remove(staff);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

		return false;
	}
	
	
	public int findStaffById (Long id) {
		return entityManager.find(Staff.class, id) != null ? 1 : -1;
	}


	@Override
	public boolean deleteStaff(Long id) {
		EntityTransaction transaction = entityManager.getTransaction();
		
		try {
			transaction.begin();
			Staff staff = entityManager.find(Staff.class, id);
			entityManager.remove(staff);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
		
		return false;
	}


	@Override
	public Staff getStaffById(Long id) {
		return entityManager.find(Staff.class, id);
	}


	@Override
	public List<Staff> getAllStaffs() {
		return entityManager.createQuery("SELECT s FROM Staff s", Staff.class).getResultList();
	}
}
