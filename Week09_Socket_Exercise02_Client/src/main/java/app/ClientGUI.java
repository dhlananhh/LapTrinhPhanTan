package app;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import dao.StaffDAO;
import entity.Staff;
import jakarta.persistence.EntityManager;
import services.StaffService;
import utils.ConnectDB;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;


public class ClientGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String HOST = "192.168.1.227";
	private static final int PORT = 3002;
	
	
	private JPanel pnContent, pnNorth, pnCenter;
	private JLabel lblMaNV, lblHo, lblTen, lblEmail, lblSDT, lblStatus, lblTimKiem;
	private JTextField txtMaNV, txtHo, txtTen, txtEmail, txtSDT, txtStatus, txtTimKiem;
	private JTextArea responseArea;
	private JButton btnFind, btnAdd, btnUpdate, btnDelete, btnSave;
	private DefaultTableModel model;
	private JTable table;
	
	private StaffDAO staffDAO;
	private StaffService staffService;
	private Staff staff;
	private EntityManager entityManager;
	
	
	public ClientGUI() {
		super("Staff Information");
		buildGUI();	
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
		Box b6 = Box.createHorizontalBox();
		
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
		
		// row 6
		responseArea = new JTextArea(10, 40);
		b6.add(responseArea);
		
		
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
		b.add(Box.createRigidArea(new DimensionUIResource(0, 20)));
		b.add(b6);

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
	
	
	
	
		
	@Override
	public void actionPerformed (ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnAdd)) {
			staff = new Staff();
			staff.setId(Long.parseLong(txtMaNV.getText()));
			staff.setLastName(txtHo.getText());
			staff.setFirstName(txtTen.getText());
			staff.setEmail(txtEmail.getText());
			staff.setPhone(txtSDT.getText());
			staff.setActive(Integer.parseInt(txtStatus.getText()));

			if (staffService.addStaff(staff)) {
				Object[] row = new Object[] { staff.getId(), staff.getLastName(), staff.getFirstName(),
						staff.getEmail(), staff.getPhone(), staff.getActive() };
				model.addRow(row);
				JOptionPane.showMessageDialog(null, "Employee has been added !");
			} else {
				JOptionPane.showMessageDialog(null, "Employee has not been added !");
			}
		} 
		else if (o.equals(btnDelete)) {
			try {
				xoa();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		} 
		else if (o.equals(btnUpdate)) {
			if (staffService.updateStaff(staff)) {
				int r = table.getSelectedRow();
				model.setValueAt(staff.getFirstName(), r, 1);
				model.setValueAt(staff.getLastName(), r, 2);
				model.setValueAt(staff.getEmail(), r, 3);
				model.setValueAt(staff.getPhone(), r, 4);
				model.setValueAt(staff.getActive(), r, 5);
				JOptionPane.showMessageDialog(null, "Employee has been updated !");
			} else {
				JOptionPane.showMessageDialog(null, "Employee has not been updated !");
			}
		} 
		else if (o.equals(btnFind)) {
			int pos = staffService.findStaffById(Long.parseLong(this.txtTimKiem.getText()));
			
			if (pos != -1) {
                table.setRowSelectionInterval(pos, pos);
                JOptionPane.showMessageDialog(null, "Employee has been found !");
            } else {
                JOptionPane.showMessageDialog(null, "Employee has not been found !");
            }
		}
	}
	
	
	public void xoa() throws Exception {
		int r = table.getSelectedRow();
		if (r != -1) {
			int tb = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee ?", 
					"Delete", JOptionPane.YES_NO_OPTION);
			if (tb == JOptionPane.YES_OPTION) {
				if (staffService.deleteStaff(Long.parseLong(txtMaNV.getText()))) {
					model.removeRow(r);
					xoaTrang();
					JOptionPane.showMessageDialog(null, "Employee has been deleted !");
				} else {
					JOptionPane.showMessageDialog(null, "Employee has not been deleted !");
				}
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "You have not selected any employee !");
		}
	}	
	
	public void xoaTrang() {
		txtMaNV.setText("");
		txtHo.setText("");
		txtTen.setText("");
		txtEmail.setText("");
		txtSDT.setText("");
		txtMaNV.requestFocus();
	}
	
	
	private void connectToServer() {
        try (Socket socket = new Socket(HOST, PORT)) {
            // Gửi request tới server
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            // Xử lý kết quả trả về từ server
            String response = inputStream.readUTF();
            responseArea.append(response + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	public static void main(String[] args) {
		ClientGUI client = new ClientGUI();
		FlatLightLaf.setup();
		new ClientGUI().setVisible(true);
		client.connectToServer();
	}
}
