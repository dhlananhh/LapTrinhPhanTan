package thongTinNhanVien;

import java.io.Serializable;
import java.util.Objects;

public class NhanVien implements Serializable {
	private String maNV;
	private String hoNV;
	private String tenNV;
	private String phai;
	private int tuoi;
	private double tienLuong;
	

	public NhanVien (String maNV) {
		this.maNV = maNV;
	}
	
	
	public NhanVien (String maNV, String hoNV, String tenNV, String phai, int tuoi, double tienLuong) {
		super();
		this.maNV = maNV;
		this.hoNV = hoNV;
		this.tenNV = tenNV;
		this.phai = phai;
		this.tuoi = tuoi;
		this.tienLuong = tienLuong;
	}


	public String getMaNV() {
		return maNV;
	}


	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}


	public String getHoNV() {
		return hoNV;
	}


	public void setHoNV(String hoNV) {
		this.hoNV = hoNV;
	}


	public String getTenNV() {
		return tenNV;
	}


	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}


	public String getPhai() {
		return phai;
	}


	public void setPhai(String phai) {
		this.phai = phai;
	}


	public int getTuoi() {
		return tuoi;
	}


	public void setTuoi(int tuoi) {
		this.tuoi = tuoi;
	}


	public double getTienLuong() {
		return tienLuong;
	}


	public void setTienLuong(double tienLuong) {
		this.tienLuong = tienLuong;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(maNV);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNV, other.maNV);
	}


	@Override
	public String toString() {
		return String.format("Nhan vien [MaNV = %s, HoNV = %s, TenNV = %s, Phai = %s, Tuoi = %d, Tien luong = %.2f]", maNV, hoNV, tenNV, phai, tuoi, tienLuong);
	}
}
