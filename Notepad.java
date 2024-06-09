import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Notepad extends JFrame implements ActionListener{
	JTextArea area;
	String text;

	Notepad(){
		setTitle("Notepad");
		ImageIcon notepadIcon = new ImageIcon("icons/notepadImage.png");
		Image icon = notepadIcon.getImage();
		setIconImage(icon);

		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.WHITE);

		JMenu file = new JMenu("File");
		file.setFont(new Font("Aerial", Font.PLAIN, 14));
		JMenuItem newOp = new JMenuItem("New");
		newOp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newOp.addActionListener(this);
		file.add(newOp);

		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(this);
		file.add(open);

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		file.add(save);

		JMenuItem print = new JMenuItem("Print");
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		print.addActionListener(this);
		file.add(print);

		JMenuItem exitt = new JMenuItem("Exit");
		exitt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, ActionEvent.CTRL_MASK));
		exitt.addActionListener(this);
		file.add(exitt);

		JMenu edit = new JMenu("Edit");
		edit.setFont(new Font("Aerial", Font.PLAIN, 14));
		JMenuItem cut = new JMenuItem("Cut");
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cut.addActionListener(this);
		edit.add(cut);

		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copy.addActionListener(this);
		edit.add(copy);

		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		paste.addActionListener(this);
		edit.add(paste);

		JMenuItem selectAll = new JMenuItem("Select All");
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		selectAll.addActionListener(this);
		edit.add(selectAll);

		JMenu helpmenu = new JMenu("Help");
		helpmenu.setFont(new Font("Aerial", Font.PLAIN, 14));
		JMenuItem help = new JMenuItem("About");
		help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		help.addActionListener(this);
		helpmenu.add(help);

		area = new JTextArea();
		area.setFont(new Font("Sans_serif", Font.PLAIN, 18));
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		add(area);

		JScrollPane scroll = new JScrollPane(area);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		add(scroll);

		menubar.add(file);
		menubar.add(edit);
		menubar.add(helpmenu);
		setJMenuBar(menubar);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae){

        String command = ae.getActionCommand();
        switch(command){
            case "New": 
                    area.setText("");
                break;
            case "Open": 
                    JFileChooser chooser = new JFileChooser();
			        int action = chooser.showOpenDialog(this);	//opens dialog
			        if(action != JFileChooser.APPROVE_OPTION)
				        return;
		
			        File Inputfile = chooser.getSelectedFile();
			        try{
				        BufferedReader br = new BufferedReader(new FileReader(Inputfile));
				        area.read(br, null);
			        } catch(IOException e){
				        System.out.println(e);
			        }
                break;

            case "Save": 
                    JFileChooser saveas = new JFileChooser();
                    saveas.setApproveButtonText("Save");
                    int action1 = saveas.showOpenDialog(this);	//opens dialog
                    if(action1 != JFileChooser.APPROVE_OPTION) {
                        return;
                    }

                    File Outputfile = new File(saveas.getSelectedFile()+"");
                    BufferedWriter bw = null;
                    try{
                        bw = new BufferedWriter(new FileWriter(Outputfile));
                    area.write(bw);
                    }catch(IOException e){
                        System.out.println(e);
			        }
                break;

            case "Print":
                    try{
                        area.print();
                    }catch(Exception e){
                        System.out.println(e);
                    }
                break;
            
            case "Exit": 
                    System.exit(0);
                break;
            
            case "Cut":
                    text = area.getSelectedText();
                    area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
                break;
		
		    case "Copy": 
                    text = area.getSelectedText();
                break;
              
            case "Paste": 
                    area.insert(text, area.getCaretPosition());
                break;
            
            case "Select All": 
                    area.selectAll();
                break;
            
            case "About": 
                    new AboutNotepad();
                break;
        }
	}
	public static void main(String[] args) {
		new Notepad();
	}
}

class AboutNotepad {
	AboutNotepad(){
		JFrame frame = new JFrame("About Notepad");
		frame.setBounds(400,100,500,500);

		ImageIcon i1 = new ImageIcon("icons/windows.png");
		Image i2 = i1.getImage().getScaledInstance(300, 60, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel icon1 = new JLabel(i3);
		icon1.setBounds(50,40,400,80);
		frame.add(icon1);

		JLabel text = new JLabel("<html>Windows Notepad 11.2402.22.0<br>© 2023 Microsoft. All rights reserved.</html>");
		text.setBounds(100, 80, 500, 300);
		text.setFont(new Font("Arial", Font.PLAIN, 18));
		frame.add(text);

		JButton ok = new JButton("OK");
		ok.setBounds(180, 400,120, 30);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				frame.setVisible(false);
			}
		});
		frame.add(ok);
		
		frame.setLayout(null);
		frame.setVisible(true);
	}
}