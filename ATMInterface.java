import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class ATMInterface{
    double balance;
    JFrame jf; 
    JTextField tf1;
    JTextField tf2;
    ArrayList<String> transactionHistory;

    ATMInterface(){
        balance = 1000; // Initial balance
        jf = new JFrame("ATM");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transactionHistory = new ArrayList<String>();

        JLabel id = new JLabel("Enter User ID: ");  //For User ID
        id.setBounds(50, 50, 100, 30);
        jf.add(id);
        tf1 = new JTextField(10);
        tf1.setBounds(180, 50, 100, 30);
        jf.add(tf1);

        JLabel pin = new JLabel("Enter 4-digit Pin:");  //For Pin
        pin.setBounds(50, 90, 100, 30);
        jf.add(pin);
        tf2 = new JPasswordField(4);
        tf2.setBounds(180, 90, 100, 30);
        jf.add(tf2);

        JButton submit = new JButton("Submit");       //Submit button
        submit.setBounds(150, 140, 80, 30);
        jf.add(submit);

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                authenticateUser();
            }
        });

        jf.setLayout(null);
        jf.setSize(450, 400);
        jf.setVisible(true);
    }

    private void authenticateUser() {
        String userId = tf1.getText();
        String pin = tf2.getText();

        if(userId.equals("user123") && pin.equals("1234")) {
            jf.getContentPane().removeAll(); // Clear the current content
            jf.repaint();       // Repaint the frame
            atmOperations();    //authentication successful
        } else {
            JOptionPane.showMessageDialog(null,"Enter Valid id and pin");
        }
    }

    public void atmOperations(){
        
        JButton trnsHisBtn = new JButton("Transaction History");
        trnsHisBtn.setBounds(122, 50, 150, 30);
        jf.add(trnsHisBtn);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(150, 90, 100, 30);
        jf.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(150, 130, 100, 30);
        jf.add(withdrawBtn);

        JButton transferBtn = new JButton("Transfer");
        transferBtn.setBounds(150, 170, 100, 30);
        jf.add(transferBtn);

        JButton close = new JButton("Close");
        close.setBounds(160, 230, 70, 20);
        jf.add(close);
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                System.exit(0);
            }
        });

        trnsHisBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                showTransactionHistory();
            }
        });

        depositBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                performDeposit();
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                performWithdraw();
            }
        });

        transferBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                performTransfer();
            }
        });
    }

    public void showTransactionHistory() {
        StringBuilder history = new StringBuilder("   Date         Time       Type      Amount\n");
        for (String transaction : transactionHistory) {
            history.append(transaction).append("\n");
        }
        JOptionPane.showMessageDialog(jf, history.toString(), "Transaction History", JOptionPane.PLAIN_MESSAGE);
    }    
    
    public void performWithdraw(){  
        String amountWith = JOptionPane.showInputDialog("Enter amount: ");
        if(amountWith==null)    return;
        double amount = Double.parseDouble(amountWith);

        if(amount > balance){
            JOptionPane.showMessageDialog(jf, "Insufficient Balance "+amount+". Your balance is "+balance);
        } else{
            balance -= amount;
            recordTransaction("Withdrawal", amount);
            JOptionPane.showMessageDialog(jf, "Amount Withdrawn "+amount+". Your balance is "+balance);
        }
    }

    public void performDeposit(){
        String amountdepo = JOptionPane.showInputDialog("Enter amount: ");
        if(amountdepo==null)    return;
        double amount = Double.parseDouble(amountdepo);
        balance += amount;
        recordTransaction("Deposit ", amount);
        JOptionPane.showMessageDialog(jf, "Amount deposited "+amount+". Your balance is "+balance);
    }

    public void performTransfer(){
        String recipientAccount = JOptionPane.showInputDialog("Enter recipient's ID:");
        if(!recipientAccount.equals("userabc")){
            recipientAccount = JOptionPane.showInputDialog(jf, "Enter Valid ID: ");
        }
        String amountStr = JOptionPane.showInputDialog("Enter transfer amount:");
        if(amountStr==null)    return;

        double amount = Double.parseDouble(amountStr);
        if (amount <= 0) {
            JOptionPane.showMessageDialog(jf, "Invalid transfer amount");
            return;
        } else{
            balance -= amount;
            recordTransaction("Transfer to " + recipientAccount, amount);
            JOptionPane.showMessageDialog(jf, "Amount "+amountStr+" transferred to "+recipientAccount+". Your balance is "+balance);
        }
    }

    private void recordTransaction(String type, double amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        String timestamp = dateFormat.format(new Date());
        String formattedAmount = String.format("%.2f", amount); // Format amount with two decimal places
        String transaction = String.format("%s  %s  Rs. %s", timestamp, type, formattedAmount);
        transactionHistory.add(transaction);
    }
    
    public static void main(String[] args) {
        new ATMInterface();
    }
}
