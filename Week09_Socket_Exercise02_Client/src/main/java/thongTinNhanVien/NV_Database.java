package thongTinNhanVien;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.File;


public class NV_Database {
	public NV_Database() {
		super();
	}
	
	
	//Cach 1: luu thanh file text
	//Doc file
	public DanhSachNhanVien readNV (String part) throws Exception {
		DanhSachNhanVien ds = new DanhSachNhanVien();
		File f = new File(part);
		if (f.exists()) {
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] data = line.split(",");
				NhanVien nv = new NhanVien(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Double.parseDouble(data[5]));
				ds.themNV(nv);
			}
			scanner.close();
		} else {
			f.createNewFile();
		}
		return ds;
	}
	
	
	//Cach 2: Ghi file
	public void writeNV (String part, DanhSachNhanVien ds) throws Exception {
		try (PrintWriter out = new PrintWriter(new FileOutputStream(part), true)) {
				for (NhanVien nv : ds.getList()) {
					String data = nv.getMaNV() + "," + nv.getHoNV() + "," + nv.getTenNV() + "," + nv.getPhai() + "," + nv.getTuoi() + "," + nv.getTienLuong();
					out.println(data);
				}
		}
	}
	
	
	//Ghi file
	public void saveFile (String fileName, Object o) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "IO Error !");
			return;
		}
	}
	
	
	//Đọc file
	public Object readFile (String fileName) {
		Object o = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "IO Error !");
		}
		return 0;
	}  
}
