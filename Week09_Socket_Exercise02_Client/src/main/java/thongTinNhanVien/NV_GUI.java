package thongTinNhanVien;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class NV_GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel pnContent, pnNorth, pnCenter;
	private JLabel lblMaNV, lblHo, lblTen, lblEmail, lblSDT, lblStatus, lblTimKiem;
	private JTextField txtMaNV, txtHo, txtTen, txtEmail, txtSDT, txtStatus, txtTimKiem;
	private JButton btnFind, btnAdd, btnUpdate, btnDelete, btnSave;
	private DefaultTableModel model;
	private JTable table;
	private DanhSachNhanVien ds;
	private NV_Database database;
	
	
	public NV_GUI() {
		super("Staff Information");
		database = new NV_Database();
		ds = new DanhSachNhanVien();
		buildGUI();
		try {
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadData() throws Exception {
		ds = database.readNV("Nhanvien.txt");
		if (ds == null) {
			ds = new DanhSachNhanVien();
		} else {
			for (NhanVien nv : ds.getList()) {
				String[] row = {nv.getMaNV(), nv.getHoNV(), nv.getTenNV(), nv.getPhai(), nv.getTuoi() + "", nv.getTienLuong() + ""};
				model.addRow(row);
			}
		}
	}
	
	
	public void buildGUI() {
		setTitle("Staff Information");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		createAndDisplayGUI();
	}
	
	
	public void createAndDisplayGUI() {
		// ** panel content **
		pnContent = new JPanel();
		pnContent.setLayout(new BorderLayout());
				
		// ** panel north **
		// tạo heading và thêm vào north panel
		pnNorth = new JPanel();
		pnNorth.add(Box.createRigidArea(new Dimension(0, 30)));
		JLabel heading = new JLabel("Staff Information");
		pnNorth.add(heading);
		pnContent.add(pnNorth, BorderLayout.NORTH);
		heading.setFont(new Font("Arial", Font.BOLD, 25));
		heading.setForeground(Color.BLACK);
		
		// ** panel center **
		pnCenter = new JPanel();
		pnContent.add(pnCenter, BorderLayout.CENTER);
		
		// panel center chia làm 2 phần: pnInfo và pnTable
		// pnInfo chứa các thông tin nhập của nhân viên
		JPanel pnInfo = new JPanel();
		pnCenter.add(pnInfo);
		
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		Box b2 = Box.createHorizontalBox();
		Box b3 = Box.createHorizontalBox();
		Box b4 = Box.createHorizontalBox();
		Box b5 = Box.createHorizontalBox();
		
		//row 1
		lblMaNV = new JLabel("Staff ID: ");
		txtMaNV = new JTextField(60);
		b1.add(lblMaNV);
		b1.add(txtMaNV);
		
		//row 2
		lblTen = new JLabel("First Name: ");
		txtTen = new JTextField(20);
		lblHo = new JLabel("Last Name: ");
		txtHo = new JTextField(20);
		
		lblMaNV.setPreferredSize(lblTen.getPreferredSize());
		
		b2.add(lblTen);
		b2.add(txtTen);
		b2.add(Box.createRigidArea(new DimensionUIResource(10, 0)));
		b2.add(lblHo);
		b2.add(txtHo);
		
		//row 3
		lblEmail = new JLabel("Email: ");
		txtEmail = new JTextField(20);
		lblEmail.setPreferredSize(lblMaNV.getPreferredSize());
		b3.add(lblEmail);
		b3.add(txtEmail);
		
		
		//row 4
		lblSDT = new JLabel("Phone: ");
		txtSDT = new JTextField(60);
		lblSDT.setPreferredSize(lblMaNV.getPreferredSize());
		b4.add(lblSDT);
		b4.add(txtSDT);
		
		// row 5
		lblStatus = new JLabel("Active: ");
		txtStatus = new JTextField(60);
		lblStatus.setPreferredSize(lblMaNV.getPreferredSize());
		b5.add(lblStatus);
		b5.add(txtStatus);
		
		// thêm các box vào panel info
		pnInfo.add(b);
		b.add(b1);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b2);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b3);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b4);
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b5);
		
		createTable();

		// ** panel south **
		JPanel pnSouth = new JPanel();
		
		// tạo split pane
		JSplitPane splitPane;
		pnContent.add(splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT), BorderLayout.SOUTH);
		splitPane.setResizeWeight(0.5);
		
		// tạo pnTimKiem và thêm các components vào
		JPanel pnTimKiem = new JPanel();
		lblTimKiem = new JLabel("Find by staff id: ");
		txtTimKiem = new JTextField(10);
		btnFind = new JButton("Find");
		
		pnTimKiem.add(lblTimKiem);
		pnTimKiem.add(txtTimKiem);
		pnTimKiem.add(btnFind);
		splitPane.add(pnTimKiem);
		
		// tạo pnChucNang
		JPanel pnChucNang = new JPanel();
		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnSave = new JButton("Save");
		
		pnChucNang.add(btnAdd);
		pnChucNang.add(btnDelete);
		pnChucNang.add(btnUpdate);
		pnChucNang.add(btnSave);
		splitPane.add(pnChucNang);
		
		// set border cho pnChucNang và pnTimKiem
		Border subBorder = BorderFactory.createLineBorder(Color.GRAY);
		pnChucNang.setBorder(subBorder);
		pnTimKiem.setBorder(subBorder);
		
		// ** create container **
		Container container = getContentPane();
		container.add(pnContent);
		
		// ** subscribe ActionListener **
		btnFind.addActionListener(this);
		btnAdd.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnSave.addActionListener(this);
	}
	
	
	public void createTable() {
		JPanel pnTable = new JPanel();
		model = new DefaultTableModel();
		table = new JTable(model);
		model.addColumn("Staff ID");
		model.addColumn("First Name");
		model.addColumn("Last Name");
		model.addColumn("Email");
		model.addColumn("Phone");
		model.addColumn("Active");

		TableColumn column = new TableColumn();
		column.setPreferredWidth(100);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
												JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(650, 250));
		pnCenter.add(scrollPane);
		pnCenter.add(pnTable);
		
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked (MouseEvent e) {
				int row = table.getSelectedRow();
				txtMaNV.setText(model.getValueAt(row, 0).toString());
				txtHo.setText(model.getValueAt(row, 1).toString());
				txtTen.setText(model.getValueAt(row, 2).toString());
				txtEmail.setText(model.getValueAt(row, 4).toString());
				txtSDT.setText(model.getValueAt(row, 5).toString());
			}
			
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
		
	@Override
	public void actionPerformed (ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnAdd)) {
			if (btnAdd.getText().equals("Add")) {
				btnDelete.setEnabled(false);
				btnAdd.setText("Cancel");
			} else {
				btnDelete.setEnabled(true);
				btnAdd.setText("Add");
			}
		} else if (o.equals(btnSave)) {
			if (txtMaNV.getText().equals("") || txtHo.getText().equals("") || txtTen.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "You have not entered enough information !");
			} else {
				try {
					
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			xoaTrang();
			txtMaNV.requestFocus();
			this.btnAdd.setText("Add");
			this.btnDelete.setEnabled(true);
		} else if (o.equals(btnDelete)) {
			try {
				xoa();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} else if (o.equals(btnUpdate)) {
			xoaTrang();
		} else if (o.equals(btnFind)) {
			int pos = ds.timKiemNV(this.txtTimKiem.getText());
			if (pos == -1) {
				JOptionPane.showMessageDialog(this, "Could not find employee with code " + this.txtTimKiem.getText());
			} else {
				table.setRowSelectionInterval(pos, pos);
			}
		}
	}
	
	
	
	
	public void xoa() throws Exception {
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee ?", 
					"Delete", JOptionPane.YES_NO_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				ds.xoaNVViTri(r);
				model.removeRow(r);
				xoaTrang();
//				database.saveFile("NhanVien.dat", ds);
				database.writeNV("Nhanvien.txt", ds);
			}
		} else {
			JOptionPane.showMessageDialog(null, "You have not selected any employee !");
		}
	}
	
	
	public void save() throws Exception {
		database.writeNV("NhanVien.txt", ds);
	}
	
	
	public void xoaTrang() {
		txtMaNV.setText("");
		txtHo.setText("");
		txtTen.setText("");
		txtEmail.setText("");
		txtSDT.setText("");
		txtMaNV.requestFocus();
	}
	
	
	public static void main(String[] args) {
		FlatLightLaf.setup();
		new NV_GUI().setVisible(true);
	}
}
