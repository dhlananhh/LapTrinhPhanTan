package thongTinNhanVien;

import java.io.Serializable;
import java.util.ArrayList;

public class DanhSachNhanVien implements Serializable {
	private ArrayList<NhanVien> list;
	
	
	public DanhSachNhanVien() {
		list = new ArrayList<>();
	}
	
	
	//thêm nhân viên
	public boolean themNV (NhanVien nv) {
		for (int i=0; i<list.size(); i++)
			if (list.get(i).getMaNV().equalsIgnoreCase(nv.getMaNV()))
				return false;
		list.add(nv);
		return true;
	}
	
	
	//lấy danh sách nhân viên
	public ArrayList<NhanVien> getList() {
		return list;
	}
	
	
	//lấy số lượng nhân viên
	public int getSize() {
		return list.size();
	}
	
	
	//xóa vị trí nhân viên
	public boolean xoaNVViTri (int index) {
		if (index < 0 || index >= list.size())
			return false;
		list.remove(index);
		return true;
	}
	
	
	//sửa nhân viên
	public boolean suaNV (String maNV, String hoNV, String tenNV, String phai, int tuoi, double tienLuong) {
		NhanVien nv = new NhanVien(maNV, hoNV, tenNV, phai, tuoi, tienLuong);
		if (list.contains(nv)) {
			nv.setMaNV(maNV);
			nv.setHoNV(hoNV);
			nv.setTenNV(tenNV);
			nv.setPhai(phai);
			nv.setTuoi(tuoi);
			nv.setTienLuong(tienLuong);
			return true;
		} else {
			return false;
		}
	}
	
	
	//kiểm tra có tồn tại mã nhân viên đó không
	public boolean kiemTra (String maNV) {
		NhanVien nv = new NhanVien(maNV);
		if (list.contains(nv)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	//tìm kiếm nhân viên
	public int timKiemNV (String maNV) {
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getMaNV().equalsIgnoreCase(maNV))
				return i;
		}
		return -1;
	}
	
}
