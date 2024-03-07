import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.StringBuilder;
import java.net.Socket;

public class Editor {
    JFrame frame=new JFrame("Editor");
    JMenuBar menuBar=new JMenuBar();
    JMenu fileMenu=new JMenu("File");
    JMenu editMenu=new JMenu("Edit");
    JMenu themeMenu=new JMenu("Theme");
    JMenuItem metal=new JMenuItem("Metal");
    JMenuItem nimbus=new JMenuItem("Nimbus");
    JMenuItem motif=new JMenuItem("Motif");
    JMenuItem light=new JMenuItem("Light");
    JMenuItem dark=new JMenuItem("Dark");
    JMenuItem macDark=new JMenuItem("MacDark");
    JMenuItem macLight=new JMenuItem("MacLight");
    JMenuItem intelliJ=new JMenuItem("IntelliJ");
    JMenuItem darcula=new JMenuItem("Darcula");
    JMenuItem copy=new JMenuItem("Copy");
    JMenuItem paste=new JMenuItem("Paste");
    JMenuItem insertPicture=new JMenuItem("Insert Picture");
    JMenuItem find=new JMenuItem("Find");
    JMenuItem substitute=new JMenuItem("Substitute");
    JMenuItem _new=new JMenuItem("New");
    JMenuItem open=new JMenuItem("Open");
    JMenuItem save=new JMenuItem("Save");
    JMenuItem saveAs=new JMenuItem("Save As");
    JMenuItem send=new JMenuItem("Send");
    JMenuItem close=new JMenuItem("Close");
    JMenuItem undo=new JMenuItem("Undo");
    JMenuItem redo=new JMenuItem("Redo");
    String[] fontItem;
    String[] fontSizeItem=new String[21];
    JComboBox<String> font;
    JComboBox<String> fontSize;
    JRadioButton bold=new JRadioButton("Bold");
    JRadioButton italic=new JRadioButton("Italic");
    JRadioButton underline=new JRadioButton("Underline");
    JTabbedPane tabbedPane=new JTabbedPane(SwingConstants.BOTTOM,JTabbedPane.SCROLL_TAB_LAYOUT);
    SimpleAttributeSet attrSet=new SimpleAttributeSet();
    JDialog findDialog=new JDialog(frame,false);
    JPanel findPanel=new JPanel();
    JTextField findField=new JTextField();
    JButton findButton=new JButton("Next");
    JDialog substituteDialog=new JDialog(frame,false);
    JPanel substitutePanel=new JPanel();
    JPanel substituteSubPanel= new JPanel();
    JTextField substituteField1=new JTextField();
    JTextField substituteField2=new JTextField();
    JButton nextButton=new JButton("Next");
    JButton substituteButton=new JButton("Substitute");
    JButton substituteAllButton=new JButton("Substitute All");
    BoxLayout findLayout=new BoxLayout(findPanel,BoxLayout.X_AXIS);
    BoxLayout substituteLayout=new BoxLayout(substitutePanel,BoxLayout.Y_AXIS);
    JDialog sendDialog= new JDialog(frame,false);
    JPanel sendPanel=new JPanel();
    JTextField sendField1=new JTextField();
    JTextField sendField2=new JTextField();
    JButton sendButton=new JButton("Send");
    BoxLayout sendLayout=new BoxLayout(sendPanel,BoxLayout.X_AXIS);
    JButton colorChooseButton =new JButton("Choose");
    JLabel colorLabel=new JLabel("R:0 G:0 B:0 Alpha:255");
    JButton backgroundChooseButton =new JButton("Choose");
    JLabel backgroundLabel=new JLabel("R:0 G:0 B:0 Alpha:255");
    public void init()
    {
        GraphicsEnvironment environment=GraphicsEnvironment.getLocalGraphicsEnvironment();
        fontItem=environment.getAvailableFontFamilyNames();
        font=new JComboBox<String>(fontItem);
        for(int i=10;i<=50;i+=2)fontSizeItem[(i-10)/2]=Integer.toString(i);
        fontSize=new JComboBox<String>(fontSizeItem);

        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
        _new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK|InputEvent.SHIFT_DOWN_MASK));
        save.setEnabled(false);
        saveAs.setEnabled(false);
        close.setEnabled(false);
        send.setEnabled(false);
        editMenu.setEnabled(false);
        fileMenu.add(_new);
        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.add(send);
        fileMenu.add(close);
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.add(insertPicture);
        editMenu.add(find);
        editMenu.add(substitute);
        editMenu.add(undo);
        editMenu.add(redo);
        themeMenu.add(metal);
        themeMenu.add(nimbus);
        themeMenu.add(motif);
        themeMenu.add(dark);
        themeMenu.add(light);
        themeMenu.add(intelliJ);
        themeMenu.add(darcula);
        themeMenu.add(macDark);
        themeMenu.add(macLight);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(themeMenu);
        frame.setJMenuBar(menuBar);

        JPanel head=new JPanel();
        JPanel headPanel=new JPanel();
        BoxLayout headPanelLayout=new BoxLayout(headPanel,BoxLayout.Y_AXIS);
        headPanel.setLayout(headPanelLayout);
        head.add(new JLabel("Font:"));
        head.add(font);
        head.add(new JLabel("Font Size:"));
        head.add(fontSize);
        head.add(bold);
        head.add(italic);
        head.add(underline);
        JPanel head2=new JPanel();
        head2.add(new JLabel("Font Color:"));
        head2.add(colorLabel);
        head2.add(colorChooseButton);
        head2.add(new JLabel("Background Color:"));
        head2.add(backgroundLabel);
        head2.add(backgroundChooseButton);
        headPanel.add(head);
        headPanel.add(head2);
        frame.add(headPanel, BorderLayout.NORTH);

        frame.add(tabbedPane,BorderLayout.CENTER);

        frame.setSize(new Dimension(1000,800));
        frame.setMinimumSize(new Dimension(800,640));
        frame.setLocation(environment.getCenterPoint().x-500,environment.getCenterPoint().y-320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        StyleConstants.setFontFamily(attrSet,fontItem[0]);

        findDialog.setSize(new Dimension(400,60));
        findDialog.setLocation(frame.getX()+frame.getWidth()/2-200,frame.getY()+frame.getHeight()/2-30);
        findDialog.setResizable(false);
        findPanel.setLayout(findLayout);
        findPanel.add(Box.createHorizontalGlue());
        findPanel.add(findField);
        findPanel.add(findButton);
        findPanel.add(Box.createHorizontalGlue());
        findDialog.add(findPanel);
        findDialog.setTitle("Find");

        substituteDialog.setSize(new Dimension(400,170));
        substituteDialog.setLocation(frame.getX()+frame.getWidth()/2-200,frame.getY()+frame.getHeight()/2-30);
        substituteDialog.setResizable(false);
        substitutePanel.setLayout(substituteLayout);
        substitutePanel.add(Box.createVerticalGlue());
        substitutePanel.add(new JLabel("Find"));
        substitutePanel.add(substituteField1);
        substitutePanel.add(new JLabel("Substitute"));
        substitutePanel.add(substituteField2);
        substituteSubPanel.add(nextButton);
        substituteSubPanel.add(substituteButton);
        substituteSubPanel.add(substituteAllButton);
        substitutePanel.add(substituteSubPanel);
        substitutePanel.add(Box.createVerticalGlue());
        substituteDialog.add(substitutePanel);
        substituteDialog.setTitle("Substitute");

        sendDialog.setSize(new Dimension(400,60));
        sendDialog.setLocation(frame.getX()+frame.getWidth()/2-200,frame.getY()+frame.getHeight()/2-30);
        sendDialog.setResizable(false);
        sendPanel.setLayout(sendLayout);
        sendPanel.add(Box.createHorizontalGlue());
        sendPanel.add(new JLabel("Host:"));
        sendPanel.add(sendField1);
        sendPanel.add(new JLabel("Port:"));
        sendPanel.add(sendField2);
        sendPanel.add(sendButton);
        sendPanel.add(Box.createHorizontalGlue());
        sendDialog.add(sendPanel);
        sendDialog.setTitle("Send");

        _new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.insertTab("New File",null,new file(),null,tabbedPane.getTabCount());
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
                ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
                saveAs.setEnabled(true);
                close.setEnabled(true);
                send.setEnabled(true);
                editMenu.setEnabled(true);
                undo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canUndo());
                redo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canRedo());
            }
        });
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                fileChooser.showOpenDialog(frame);
                File selectedFile=fileChooser.getSelectedFile();
                if(selectedFile==null)return;
                if(selectedFile.length()>1048576)
                {
                    JOptionPane.showMessageDialog(frame,"The file is too large!","",JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                tabbedPane.insertTab(selectedFile.getName(),null,new file(),null,tabbedPane.getTabCount());
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
                ((file)tabbedPane.getSelectedComponent()).file=selectedFile;
                ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
                try
                {
                    File file=((file)tabbedPane.getSelectedComponent()).file;
                    FileReader fileReader=new FileReader(file);
                    BufferedReader bufferedReader=new BufferedReader(fileReader);
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while((line=bufferedReader.readLine())!=null) {
                        stringBuilder.append(line);
                        stringBuilder.append('\n');
                    }
                    String result=stringBuilder.toString();
                    StyledDocument doc=((file)tabbedPane.getSelectedComponent()).textPane.getStyledDocument();
                    doc.remove(0, doc.getLength());
                    doc.insertString(0,result,attrSet);
                    bufferedReader.close();
                    fileReader.close();
                }
                catch(Exception exception){
                    exception.printStackTrace();
                }
                saveAs.setEnabled(true);
                save.setEnabled(true);
                close.setEnabled(true);
                send.setEnabled(true);
                editMenu.setEnabled(true);
                undo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canUndo());
                redo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canRedo());
        }});
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String content=((file)tabbedPane.getSelectedComponent()).textPane.getText();
                    FileWriter fileWriter=new FileWriter(((file)tabbedPane.getSelectedComponent()).file);
                    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                    bufferedWriter.write(content);
                    bufferedWriter.close();
                    fileWriter.close();
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                fileChooser.showSaveDialog(frame);
                File Savefile=fileChooser.getSelectedFile();
                if(Savefile==null)return;
                File file=((file)tabbedPane.getSelectedComponent()).file;
                if(file==null)file=Savefile;
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),Savefile.getName());
                try {
                    String content=((file)tabbedPane.getSelectedComponent()).textPane.getText();
                    FileWriter fileWriter=new FileWriter(Savefile);
                    BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                    bufferedWriter.write(content);
                    bufferedWriter.close();
                    fileWriter.close();
                }catch(Exception exception){
                    exception.printStackTrace();
                }
                save.setEnabled(true);
            }
        });
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((file)tabbedPane.getSelectedComponent()).textPane.copy();
            }
        });
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((file)tabbedPane.getSelectedComponent()).textPane.paste();
            }
        });
        insertPicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(frame);
                File image = fileChooser.getSelectedFile();
                if (image == null) return;
                try {
                    BufferedImage bufferedImage=ImageIO.read(image);
                    Canvas canvas = new Canvas(bufferedImage);
                    canvas.repaint();
                    ((file)tabbedPane.getSelectedComponent()).textPane.insertComponent(canvas);
                }catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem=(String)font.getSelectedItem();
                StyleConstants.setFontFamily(attrSet,selectedItem);
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        fontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedItem=Integer.parseInt((String)fontSize.getSelectedItem());
                StyleConstants.setFontSize(attrSet,selectedItem);
                if(editMenu.isEnabled())
                    ((file) tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet, false);
            }
        });
        colorChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color=JColorChooser.showDialog(frame,"Color Chooser",Color.black);
                if(color==null)return;
                colorLabel.setText("R:"+color.getRed()+" G:"+color.getGreen()+" B:"+color.getBlue()+" Alpha:"+color.getAlpha());
                StyleConstants.setForeground(attrSet,color);
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        backgroundChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color=JColorChooser.showDialog(frame,"Color Chooser",Color.black);
                if(color==null)return;
                backgroundLabel.setText("R:"+color.getRed()+" G:"+color.getGreen()+" B:"+color.getBlue()+" Alpha:"+color.getAlpha());
                StyleConstants.setBackground(attrSet,color);
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        bold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyleConstants.setBold(attrSet,bold.isSelected());
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        italic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyleConstants.setItalic(attrSet,italic.isSelected());
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        underline.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StyleConstants.setUnderline(attrSet,underline.isSelected());
                if(editMenu.isEnabled())
                    ((file)tabbedPane.getSelectedComponent()).textPane.setCharacterAttributes(attrSet,false);
            }
        });
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findDialog.setVisible(true);
            }
        });
        substitute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                substituteDialog.setVisible(true);
            }
        });
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String content = ((file)tabbedPane.getSelectedComponent()).textPane.getText();
                    String substr=findField.getText();
                    int index=content.indexOf(substr,((file)tabbedPane.getSelectedComponent()).textPane.getCaretPosition());
                    if(index==-1)index=content.indexOf(substr,0);
                    if(index==-1)return;
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionStart(index);
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionEnd(index+substr.length());
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String content = ((file)tabbedPane.getSelectedComponent()).textPane.getText();
                    String substr=substituteField1.getText();
                    int index=content.indexOf(substr,((file)tabbedPane.getSelectedComponent()).textPane.getCaretPosition());
                    if(index==-1)index=content.indexOf(substr,0);
                    if(index==-1)return;
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionStart(index);
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionEnd(index+substr.length());
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        substituteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StyledDocument doc = ((file)tabbedPane.getSelectedComponent()).textPane.getStyledDocument();
                    int start=((file)tabbedPane.getSelectedComponent()).textPane.getSelectionStart();
                    int len=((file)tabbedPane.getSelectedComponent()).textPane.getSelectionEnd()-((file)tabbedPane.getSelectedComponent()).textPane.getSelectionStart();
                    if(len==0)return;
                    doc.remove(start,len);
                    doc.insertString(start, substituteField2.getText(), attrSet);
                    String content = doc.getText(0, doc.getLength());
                    String substr=substituteField1.getText();
                    int index=content.indexOf(substr,((file)tabbedPane.getSelectedComponent()).textPane.getCaretPosition());
                    if(index==-1)index=content.indexOf(substr,0);
                    if(index==-1)return;
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionStart(index);
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionEnd(index+substr.length());
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        substituteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StyledDocument doc = ((file)tabbedPane.getSelectedComponent()).textPane.getStyledDocument();
                    String content = doc.getText(0, doc.getLength());
                    String substr = substituteField1.getText();
                    int index = content.indexOf(substr, ((file)tabbedPane.getSelectedComponent()).textPane.getCaretPosition());
                    if (index == -1) index = content.indexOf(substr, 0);
                    if (index == -1) return;
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionStart(index);
                    ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionEnd(index + substr.length());
                    while(true) {
                        int start = ((file)tabbedPane.getSelectedComponent()).textPane.getSelectionStart();
                        int len = ((file)tabbedPane.getSelectedComponent()).textPane.getSelectionEnd() - ((file)tabbedPane.getSelectedComponent()).textPane.getSelectionStart();
                        if (len == 0) return;
                        doc.remove(start, len);
                        doc.insertString(start, substituteField2.getText(), attrSet);
                        content = doc.getText(0, doc.getLength());
                        substr = substituteField1.getText();
                        index = content.indexOf(substr, ((file)tabbedPane.getSelectedComponent()).textPane.getCaretPosition());
                        if (index == -1) index = content.indexOf(substr, 0);
                        if (index == -1) break;
                        ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionStart(index);
                        ((file)tabbedPane.getSelectedComponent()).textPane.setSelectionEnd(index + substr.length());
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendDialog.setVisible(true);
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new sendThread(sendField1.getText(),Integer.parseInt(sendField2.getText())).start();
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
                if(tabbedPane.getTabCount()==0) {
                    close.setEnabled(false);
                    send.setEnabled(false);
                    editMenu.setEnabled(false);
                    saveAs.setEnabled(false);
                    save.setEnabled(false);
                    return;
                }
                if(((file)tabbedPane.getSelectedComponent()).file==null)
                    save.setEnabled(false);
                else
                    save.setEnabled(true);
            }
        });
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane.getTabCount()==0) {
                    close.setEnabled(false);
                    send.setEnabled(false);
                    editMenu.setEnabled(false);
                    saveAs.setEnabled(false);
                    save.setEnabled(false);
                    return;
                }
                if(((file)tabbedPane.getSelectedComponent()).file==null)
                    save.setEnabled(false);
                else
                    save.setEnabled(true);
                undo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canUndo());
                redo.setEnabled(((file)tabbedPane.getSelectedComponent()).undoManager.canRedo());
            }
        });
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((file)tabbedPane.getSelectedComponent()).undo();
            }
        });
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((file)tabbedPane.getSelectedComponent()).redo();
            }
        });
        metal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        nimbus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        motif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        dark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        light.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        intelliJ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatIntelliJLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        darcula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatDarculaLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        macDark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatMacDarkLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        macLight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    UIManager.setLookAndFeel(new FlatMacLightLaf());
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(frame);
                    SwingUtilities.updateComponentTreeUI(findDialog);
                    SwingUtilities.updateComponentTreeUI(substituteDialog);
                    SwingUtilities.updateComponentTreeUI(sendDialog);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }
    private class Canvas extends JPanel{
        BufferedImage image;
        JDialog dialog;
        JButton large=new JButton("Large");
        JButton small=new JButton("Small");
        JPanel panel=new JPanel();
        Canvas(BufferedImage bufferedImage){
            super();
            this.image=bufferedImage;
            dialog=new JDialog(frame,false);
            dialog.setSize(new Dimension(200,120));
            dialog.setResizable(false);
            BoxLayout layout=new BoxLayout(panel,BoxLayout.X_AXIS);
            panel.setLayout(layout);
            panel.add(Box.createHorizontalGlue());
            panel.add(large);
            panel.add(Box.createHorizontalGlue());
            panel.add(small);
            panel.add(Box.createHorizontalGlue());
            dialog.add(panel);
            large.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x=(int)(getWidth()*1.1);
                    int y=(int)(getHeight()*1.1);
                    if(x>frame.getWidth())return;
                    setSize(new Dimension(x,y));
                    setPreferredSize(new Dimension(x,y));
                    setMaximumSize(new Dimension(x,y));
                    repaint();
                }
            });
            small.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x=(int)(getWidth()*0.9);
                    int y=(int)(getHeight()*0.9);
                    if(x<30||y<30)return;
                    setSize(new Dimension(x,y));
                    setPreferredSize(new Dimension(x,y));
                    setMaximumSize(new Dimension(x,y));
                    repaint();
                }
            });
            int x=image.getWidth();
            int y=image.getHeight();
            while(x>frame.getWidth()){
                x*=0.9;
                y*=0.9;
            }
            this.setSize(new Dimension(x,y));
            this.setPreferredSize(new Dimension(x,y));
            this.setMaximumSize(new Dimension(x,y));
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dialog.setLocation(frame.getX()+frame.getWidth()/2-100,frame.getY()+frame.getHeight()/2-60);
                    dialog.setVisible(true);
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    return;
                }
                @Override
                public void mouseReleased(MouseEvent e) {
                    return;
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    return;
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    return;
                }
            });
        }
        @Override
        public void paintComponent(Graphics g){
            g.drawImage(this.image,0,0,getWidth(),getHeight(),null);
        }
    }
    private class sendThread extends Thread
    {
        String host;
        int port;
        sendThread(String host,int port){
            this.host=host;
            this.port=port;
        }
        @Override
        public void run() {
            super.run();
            try {
                Socket s = new Socket(host, port);
                OutputStream out=s.getOutputStream();
                OutputStreamWriter outputStreamWriter=new OutputStreamWriter(out);
                BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(((file)tabbedPane.getSelectedComponent()).textPane.getText());
                bufferedWriter.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class file extends JScrollPane
    {
        File file;
        JTextPane textPane=new JTextPane();
        UndoManager undoManager=new UndoManager();
        file()
        {
            super();
            textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
                @Override
                public void undoableEditHappened(UndoableEditEvent e) {
                    undoManager.addEdit(e.getEdit());
                    undo.setEnabled(undoManager.canUndo());
                    redo.setEnabled(undoManager.canRedo());

                }
            });
            textPane.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    textPane.setCharacterAttributes(attrSet, false);
                }
                @Override
                public void focusLost(FocusEvent e) {
                    return;
                }
            });
            this.setViewportView(textPane);
        }
        void undo()
        {
            undoManager.undo();
            undo.setEnabled(undoManager.canUndo());
            redo.setEnabled(undoManager.canRedo());
        }
        void redo()
        {
            undoManager.redo();
            undo.setEnabled(undoManager.canUndo());
            redo.setEnabled(undoManager.canRedo());
        }
    }
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        new Editor().init();
    }
}