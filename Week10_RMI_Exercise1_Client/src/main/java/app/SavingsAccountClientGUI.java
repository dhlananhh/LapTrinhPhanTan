package app;


import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.*;
import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import dao.SavingsAccountDAO;
import entities.SavingsAccount;

import java.util.List;

import services.SavingsAccountService;

public class SavingsAccountClientGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private SavingsAccountService service;
//	private SavingsAccount account;

    private JTextField cardNumberTextField;
    private JTextField accountHolderNameTextField;
    private JTextField balanceTextField;
    private JButton createButton;
    private JButton getAllAccountsButton;
    private JButton getAccByCardNumberButton;
    private JButton depositButton;
    private JButton withdrawButton;

    private JTextArea resultTextArea;
    
    private String URL = "rmi://192.168.1.227:9999/";

    public SavingsAccountClientGUI() throws MalformedURLException, RemoteException, NotBoundException {
        super("Tài khoản tiết kiệm ABC");

        SavingsAccountDAO service = (SavingsAccountDAO) Naming.lookup(URL + "SavingsAccountService");
        

        JPanel createAccountPanel = createAccountPanel();
        JPanel getAllAccountsPanel = getAllAccountsPanel();
        JPanel getAccByCardNumberPanel = getAccByCardNumberPanel();
        JPanel depositPanel = depositPanel();
        JPanel withdrawPanel = withdrawPanel();
        JPanel resultPanel = resultPanel();

        Box mainBox = Box.createVerticalBox();
        mainBox.add(createAccountPanel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getAllAccountsPanel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(getAccByCardNumberPanel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(depositPanel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(withdrawPanel);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(resultPanel);

        getContentPane().add(mainBox, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Số tài khoản:"));
        cardNumberTextField = new JTextField(20);
        panel.add(cardNumberTextField);

        panel.add(new JLabel("Họ tên chủ sở hữu:"));
        accountHolderNameTextField = new JTextField(20);
        panel.add(accountHolderNameTextField);

        panel.add(new JLabel("Số tiền:"));
        balanceTextField = new JTextField(10);
        panel.add(balanceTextField);

        createButton = new JButton("Tạo");
        createButton.addActionListener(this);
        panel.add(createButton);

        return panel;
    }

    private JPanel getAllAccountsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        getAllAccountsButton = new JButton("Lấy tất cả tài khoản");
        getAllAccountsButton.addActionListener(this);
        panel.add(getAllAccountsButton);

        return panel;
    }

    private JPanel getAccByCardNumberPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Số tài khoản:"));
        cardNumberTextField = new JTextField(20);
        panel.add(cardNumberTextField);

        getAccByCardNumberButton = new JButton("Lấy theo số tài khoản");
        getAccByCardNumberButton.addActionListener(this);
        panel.add(getAccByCardNumberButton);

        return panel;
    }

    private JPanel depositPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Số tài khoản:"));
        cardNumberTextField = new JTextField(20);
        panel.add(cardNumberTextField);

        panel.add(new JLabel("Số tiền nạp:"));
        balanceTextField = new JTextField(10);
        panel.add(balanceTextField);

        depositButton = new JButton("Nạp tiền");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        return panel;
    }
    
    private JPanel withdrawPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(new JLabel("Số tài khoản:"));
        cardNumberTextField = new JTextField(20);
        panel.add(cardNumberTextField);

        panel.add(new JLabel("Số tiền rút:"));
        balanceTextField = new JTextField(10);
        panel.add(balanceTextField);

        withdrawButton = new JButton("Rút tiền");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        return panel;
    }
    
    private JPanel resultPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        resultTextArea = new JTextArea(5, 30);
        resultTextArea.setEditable(false);
        panel.add(new JScrollPane(resultTextArea));

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        resultTextArea.setText("");
        if (e.getSource() == createButton) {
            String cardNumber = cardNumberTextField.getText().trim();
            String accountHolderName = accountHolderNameTextField.getText().trim();
            double balance = Double.parseDouble(balanceTextField.getText().trim());

            try {
                if (!service.validateCardNumber(cardNumber)) {
                    throw new IllegalArgumentException("Số tài khoản không hợp lệ");
                }
                service.createSavingsAccount(cardNumber, accountHolderName, balance);
                resultTextArea.append("Tạo tài khoản thành công!");
            } catch (Exception ex) {
                resultTextArea.append("Lỗi: " + ex.getMessage());
            } finally {
                cardNumberTextField.setText("");
                accountHolderNameTextField.setText("");
                balanceTextField.setText("");
            }
        } else if (e.getSource() == getAllAccountsButton) {
            try {
                List<SavingsAccount> accounts = service.getAllSavingsAccounts();
                StringBuilder sb = new StringBuilder();
                for (SavingsAccount account : accounts) {
                    sb.append(account.toString()).append("\n");
                }
                resultTextArea.setText(sb.toString());
            } catch (Exception ex) {
                resultTextArea.append("Lỗi: " + ex.getMessage());
            }
        } else if (e.getSource() == getAccByCardNumberButton) {
            String cardNumber = cardNumberTextField.getText().trim();

            try {
                SavingsAccount account = service.getSavingsAccountByCardNumber(cardNumber);
                if (account != null) {
                    resultTextArea.setText(account.toString());
                } else {
                    resultTextArea.setText("Không tìm thấy tài khoản.");
                }
            } catch (Exception ex) {
                resultTextArea.append("Lỗi: " + ex.getMessage());
            } finally {
                cardNumberTextField.setText("");
            }
        } else if (e.getSource() == depositButton) {
            String cardNumber = cardNumberTextField.getText().trim();
            double amount = Double.parseDouble(balanceTextField.getText().trim());

            try {
                service.deposit(cardNumber, amount);
                resultTextArea.append("Nạp tiền thành công!");
            } catch (Exception ex) {
                resultTextArea.append("Lỗi: " + ex.getMessage());
            } finally {
                cardNumberTextField.setText("");
                balanceTextField.setText("");
            }
        } else if (e.getSource() == withdrawButton) {
            String cardNumber = cardNumberTextField.getText().trim();
            double amount = Double.parseDouble(balanceTextField.getText().trim());

            try {
                service.withdraw(cardNumber, amount);
                resultTextArea.append("Rút tiền thành công!");
            } catch (Exception ex) {
                resultTextArea.append("Lỗi: " + (ex instanceof RemoteException ? "Kết nối server bị lỗi" : ex.getMessage()));
            } finally {
                cardNumberTextField.setText("");
                balanceTextField.setText("");
            }
        }
    }


    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
    	FlatLightLaf.setup();
        new SavingsAccountClientGUI();
	}
}