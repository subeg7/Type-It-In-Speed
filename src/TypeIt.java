
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.scene.Cursor.cursor;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

class TypeGarage {

    JPanel playPanel, mainPanel, thePanel;
    JPanel createPanel = new JPanel();
    JFrame frame;
    JTextArea textArea, playTextArea;
    JMenuBar menuBar;
    JButton prevTextBut = new JButton("<<< Prev Text");

//        For playPanel components
    JButton playButton = new JButton("Start");
    JButton nextTextBut = new JButton("Next Text >>>");
    JTextField typeArea = new JTextField(10);
    JTextField setWPM;
    JLabel setWpmLabel;
    JLabel targetLabel;
    JLabel titleName;
    JLabel timeNo;
    int targetWPM = 35;
    int wordsLength;
    JSlider slider;
    JLabel currWpmLabel;
    JLabel wpmValueLabel;
    JFrame wpmFrame;

    String fileText;
    int time = 0;
    int wordPosition = 0;
    ArrayList<JLabel> listLabel;

    Font font = new Font("serif", Font.BOLD, 28);
    JProgressBar timeBar;

    String[] Words;
    int totalTextWords;
    String[] resumeList;

    ArrayList<Integer> randomValues = new ArrayList<Integer>();
    int prevClicked = 0;

    boolean textAreaPresence;

//    edit Menu
    ArrayList<File> fileList = new ArrayList<File>();

    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    File editedFile;
    JFrame editFrame;
    Thread thread;
    int currValue;
    int wordsTyped = 0;
    int actualTime;

    int currentCaretPos;

    JButton pauseBut = new JButton("Pause");
    int currWpm;
    int nextText= 0;

    JFrame resumeFrame;

    public static void main(String[] args) {

        TypeGarage mainObj = new TypeGarage();
        mainObj.mainGui();

    }

    public void mainGui() {

        frame = new JFrame("TypeIt v1.0");
        frame.setVisible(true);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);

        thePanel = new JPanel();
        mainPanel = new JPanel();

        frame.getContentPane().add(mainPanel);

//        JMenuBar mBar = new JMenuBar();
//        frame.setJMenuBar(null);
//        frame.setJMenuBar(mBar);
//        JMenu file = new JMenu("File");
//        mBar.add(file);
//        mBar.add(new JMenu("Edit"));
//        mainPanel.setBackground(Color.black);
//        thePanel.add(mainPanel);
        JButton createBut = new JButton("Manage Text");
        mainPanel.add(createBut);
        createBut.setBackground(Color.red);
        createBut.addActionListener(new createButListener());

        JButton playBut = new JButton("Type Text");
        mainPanel.add(playBut);
        playBut.setBackground(Color.blue);
        playBut.setForeground(Color.white);
        playBut.addActionListener(new playButListener());

    }

    class playButListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time = 00;
            playerGui();
        }

    }

    class createButListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
//            frame.repaint();
//            textArea=null;
//            textAreaPresence=false;
            createrGui();
//            frame.repaint();
        }

    }

    public void createrGui() {

        frame.getContentPane().remove(mainPanel);
        frame.repaint();
        frame.getContentPane().add(createPanel);
        createPanel.add(new JLabel("THi is the test"));
        frame.repaint();
        createPanel.setBackground(Color.red);
        createPanel.repaint();

        frame.setJMenuBar(null);
        JMenuBar manMenuBar = new JMenuBar();
        frame.setJMenuBar(manMenuBar);
//        manMenuBar.setVisible(true);
        frame.repaint();

//        manMenu/Bar.setVisible(true);
        manMenuBar.setForeground(Color.blue);

        JMenu fileMenu = new JMenu("FILE");
        manMenuBar.add(fileMenu);
//        fileMenu.

        JMenu editMenu = new JMenu("TOOLS");
        manMenuBar.add(editMenu);

        JMenuItem newMI = new JMenuItem("new");
        fileMenu.add(newMI);
        newMI.addActionListener(new newMIListener());

        JMenuItem openItem = new JMenuItem("open");
        fileMenu.add(openItem);
        openItem.addActionListener(new openMIListener());

        JMenuItem saveMI = new JMenuItem("save");
        fileMenu.add(saveMI);
        saveMI.addActionListener(new saveMIListener());

        JMenuItem saveAsMI = new JMenuItem("save as");
        fileMenu.add(saveAsMI);
        saveAsMI.addActionListener(new saveAsMIListener());

        JMenuItem deleteMI = new JMenuItem("delete");
        fileMenu.add(deleteMI);
        deleteMI.addActionListener(new deleteMIListener());

        JMenuItem recentMI = new JMenuItem("recent");
        fileMenu.add(recentMI);

        JMenuItem SEMI = new JMenuItem("save and exit");
        fileMenu.add(SEMI);

        JMenuItem exitMI = new JMenuItem("exit");
        fileMenu.add(exitMI);
        exitMI.addActionListener(new exitMIListener());

        JMenuItem fontMI = new JMenuItem("manage font");
        editMenu.add(fontMI);

        JMenuItem cutMI = new JMenuItem("cut");
        editMenu.add(cutMI);

        JMenuItem copyMI = new JMenuItem("copy");
        editMenu.add(copyMI);
//        frame.getContentPane().add(new JLabel("Is this added  before the the MenuBar"));

        frame.repaint();
    }

    class deleteMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editFile("delete");
        }
    }

    class newMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            createTextArea();

//            frame.repaint();
        }

    }

    public void createTextArea() {

        if (!textAreaPresence) {

            JLabel atTopLabel = new JLabel("Enter the Text down below");
            createPanel.add(atTopLabel);

            textArea = new JTextArea(10, 26);
            createPanel.add(textArea);
            textAreaPresence = true;

//            textArea.add(new Popup());
//            textArea.setOpaque(false);
            textArea.setFont(font);

            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            JScrollPane aScroller = new JScrollPane(textArea);
            aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            createPanel.add(aScroller);
        } else {
//            create New window to new Type Area;
        }
        textArea.setText("");
        textArea.requestFocus();

    }

    class openMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            editFile("edit");
        }

    }

    public void editFile(String calledFrom) {
//        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        try {

            File fileCollection = new File("FileCollection.txt");

            BufferedReader fileReader = new BufferedReader(new FileReader(fileCollection));
            String files = fileReader.readLine();

//            if (files != null) {
            if (files == null) {
                System.out.println("No lines in FileCollection");
            }
            String[] fileNames = files.split("/");
//            }

            labelList = null;
            labelList = new ArrayList<JLabel>();

            for (int i = 0; i <= fileNames.length - 1; i++) {
                File tempFile = new File(fileNames[i]);
                fileList.add(tempFile);
                labelList.add(new JLabel("" + tempFile.getName()));

            }
            editFrame = new JFrame("Edit Frame");
            editFrame.setSize(200, 200);
            editFrame.setVisible(true);

            JPanel editMainPanel = new JPanel();
            editFrame.getContentPane().add(editMainPanel);
            fileReader.close();

            for (JLabel label : labelList) {

                editMainPanel.add(label);

                int index = labelList.indexOf(label);
                if (calledFrom == "delete") {
                    label.addMouseListener(new deleteFileLabelListener(index));

                } else {
                    label.addMouseListener(new editFileLabelListener(index));
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class deleteFileLabelListener implements MouseListener {

        int index;

        public deleteFileLabelListener(int ix) {
            index = ix;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            try {

                File tempDeleteFile = fileList.get(index);
                tempDeleteFile.delete();

                File fileCollection = new File("FileCollection.txt");

                BufferedReader reader = new BufferedReader(new FileReader(fileCollection));

                String singleFileLine = reader.readLine();
                reader.close();

                if (singleFileLine == null) {

                }

                String[] fileNames = singleFileLine.split("/");
                String afterDeleteColl = "";

                String toDelete = fileList.get(index).getPath();
                System.out.println("File name to delete is" + toDelete);

                File temp = fileList.get(index);
                temp.delete();
                for (String currName : fileNames) {
                    if (!(toDelete.equals(currName))) {

                        afterDeleteColl = afterDeleteColl + currName + "/";
                    }

                }

                System.out.println("After Deleting:" + afterDeleteColl);

                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileCollection));

                fileWriter.write(afterDeleteColl);
                fileWriter.close();
                editFrame.setVisible(false);
                editFile("delete");

//                editFrame.repaint();
            } catch (IOException ex) {

                System.out.println("Couldn't close the file");
//            }catch(FileNotFoundException ex){
//                System.out.println("Couldn't open the file");
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            labelList.get(index).setForeground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            labelList.get(index).setForeground(Color.black);
        }

    }

    class editFileLabelListener implements MouseListener {

        int index;

        public editFileLabelListener(int in) {
            index = in;

        }

        @Override
        public void mouseClicked(MouseEvent e) {

            System.out.println("EditFileLabelListener clicked");
            editFrame.setVisible(false);
//            editFrame.repaint();
            editedFile = fileList.get(index);
//            fileList.get(index).delete();
//            fileList.get(index).

            createTextArea();
            displayText("open", index);

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            Font labelFont = new Font("serif", Font.ROMAN_BASELINE, 20);
//            labelList.get(index).setFont(labelFont);

            labelList.get(index).setForeground(Color.blue);

        }

        @Override
        public void mouseExited(MouseEvent e) {
//            Font labelFont =  new Font ("serif", Font.BOLD, 28);
//            labelList.get(index).setFont(null);
            labelList.get(index).setForeground(Color.black);
        }

    }

    class saveAsMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);

            saveAsFile(fileSave.getSelectedFile());
        }

    }

    class saveMIListener implements ActionListener {

        boolean isEdited;

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(editedFile));
                String text = textArea.getText();

                writer.write(text);
                writer.close();
                textArea.setText("");
            } catch (IOException ex) {
                Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    class exitMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent er) {
            frame.getContentPane().remove(createPanel);

//           frame.removeAll();
            frame.setJMenuBar(null);
            frame.repaint();

            frame.getContentPane().add(mainPanel);

        }

    }

    public void saveAsFile(File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            File fileCollection = new File("FileCollection.txt");
            System.out.println(fileCollection.getPath());
            BufferedWriter collectionWriter = new BufferedWriter(new FileWriter(fileCollection, true));

            String text = textArea.getText();

            String filePath = file.getPath();
            System.out.println(file.getPath());

            collectionWriter.write(filePath + "/");

            writer.write(text);
            writer.close();
            collectionWriter.close();

        } catch (IOException ex) {
            Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playerGui() {

        frame.getContentPane().remove(mainPanel);

        playPanel = new JPanel();

        frame.getContentPane().add(playPanel);

        targetLabel = new JLabel("Set WPM");
        playPanel.add(targetLabel);
        targetLabel.setFont(font);
        targetLabel.addMouseListener(new setTargetLabelListener());

        JLabel wpmLabel = new JLabel("Current WPM:");
        playPanel.add(wpmLabel);
//        wpmLabel.setOpaque(false);
        wpmLabel.setForeground(Color.lightGray);
        currWpmLabel = new JLabel("10");
        playPanel.add(currWpmLabel);
//        wpmLabel.setFont(font);

        JLabel wpmNo = new JLabel("12");
        playPanel.add(wpmNo);
        wpmNo.setFont(font);

        JLabel gapLabel = new JLabel("                               ");
        playPanel.add(gapLabel);
//        gapLabel.setForeground(Color.DARK_GRAY);

        gapLabel.setFont(font);
        gapLabel.setOpaque(false);

        JLabel timeLabel = new JLabel("Time:");
        playPanel.add(timeLabel);
        timeLabel.setFont(font);

        timeNo = new JLabel("0");
        playPanel.add(timeNo);
        timeNo.setFont(font);

        JLabel gapLabel2 = new JLabel("                     ");
        playPanel.add(gapLabel2);

        JLabel title = new JLabel("Title:");
        playPanel.add(title);
//        title.setForeground(Color.MAGENTA);

        titleName = new JLabel("kjhkjhkjh");
        playPanel.add(titleName);
        titleName.setForeground(Color.blue);

        playPanel.remove(wpmNo);

        playTextArea = new JTextArea(10, 26);
        playPanel.add(playTextArea);
        playTextArea.setForeground(Color.orange);
        playTextArea.setFont(font);
        playTextArea.setBackground(Color.darkGray);

//        Graphics2D graphics = new Graphics2D();
        displayText("", 0);

        playTextArea.setLineWrap(true);
        playTextArea.setWrapStyleWord(true);

        JScrollPane aScroller = new JScrollPane(playTextArea);
        aScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        playPanel.add(aScroller);

        JLabel typeLabel = new JLabel("Type below");
        typeLabel.setFont(font);

        prevTextBut.setFont(font);
        prevTextBut.setBackground(Color.pink);
        playPanel.add(prevTextBut);
        prevTextBut.addActionListener(new prevTextButListener());

        playPanel.add(playButton);
        playButton.setBackground(Color.GREEN);
        playButton.addActionListener(new startButtonListener());

        nextTextBut.setFont(font);
        nextTextBut.setBackground(Color.pink);
        nextTextBut.addActionListener(new nextTextBut());

        playPanel.add(nextTextBut);
//        nextTextBut.setBackground(Color.blue);

        playButton.setFont(font);

//        JLabel gapL = new JLabel("                  GAP        ");
//        gapL.setFont(font);
//        gapL.setOpaque(false);
//        gapL.setForeground(Color.gray);
//        playPanel.add(gapL);
//        setWpmLabel = new JLabel("Set WPM");
//        playPanel.add(setWpmLabel);
//        setWPM = new JTextField(10);
//        playPanel.add(setWPM);
//        setWPM.requestFocus();
//        setWPM.addActionListener(new setWPMListener());
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        frame.repaint();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenu toolMenu = new JMenu("Tools");
        menuBar.add(toolMenu);

        JMenuItem pauseMI = new JMenuItem("resume saved");
        toolMenu.add(pauseMI);
        pauseMI.addActionListener(new resumeSavedListener());

        JMenuItem savePausedMI = new JMenuItem("save paused");
        toolMenu.add(savePausedMI);
        savePausedMI.addActionListener(new savePausedListener());

        JMenuItem startNewMI = new JMenuItem("start new");
        fileMenu.add(startNewMI);

        JMenuItem exitMI = new JMenuItem("exit");
        fileMenu.add(exitMI);
        exitMI.addActionListener(new playExitMIListener());

    }

    class savePausedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                String textName = titleName.getText();
                File currentStatus = new File("Resume" + textName);
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentStatus));

                File pausedList = new File("PausedList.txt");
                BufferedWriter listWriter = new BufferedWriter(new FileWriter(pausedList, true));
//                int remainingTime = time;
//                int currentWpm = currWpm;
//                int worPos = wordPosition;
//                int cPos = currentCaretPos;

//                writer.write(textName + "/");
                writer.write(currWpm + "/");
                writer.write(wordPosition + "/");
                writer.write(currentCaretPos + "/");
                writer.write(time + "/");
                writer.close();

                listWriter.write(currentStatus.getPath() + "/");
                listWriter.close();

            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }

    }

    class resumeSavedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            resumeFrame = new JFrame("Resume It");
            resumeFrame.setSize(200, 200);
            resumeFrame.setVisible(true);

            JPanel resumePanel = new JPanel();
            resumeFrame.getContentPane().add(resumePanel);

            File file = new File("PausedList.txt");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String read = reader.readLine();
                resumeList = read.split("/");

                int i = 0;
                listLabel = new ArrayList<>();
                for (String st : resumeList) {
                    JLabel listLabel1 = new JLabel(st);
                    listLabel.add(listLabel1);
                    listLabel.get(i).addMouseListener(new resumeListListener(i));
                    resumePanel.add(listLabel1);
                    i++;

                }
                reader.close();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    class resumeListListener implements MouseListener {

        int resumeListIndex;

        public resumeListListener(int i) {
            resumeListIndex = i;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            BufferedReader reader = null;
            resumeFrame.setVisible(false);
            try {
                displayText("resume", resumeListIndex);
                wordProcessing();
                String fileName = resumeList[resumeListIndex];
                File file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                String read = reader.readLine();
                String[] status = read.split("/");

//                 writer.write(currWpm + "/");
//                writer.write(wordPosition + "/");
//                writer.write(currentCaretPos + "/");
//                writer.write(time);
                currWpm = Integer.parseInt(status[0]);
                wordPosition = Integer.parseInt(status[1]);
                currentCaretPos = Integer.parseInt(status[2]);
                time = Integer.parseInt(status[3]);
//                System.out.println("The time read from resumeMenu is :" + time);
                String st = "" + currentCaretPos;
                highlight(st);
//                time = Integer.parseInt(status[3]);
                reader.close();
//                String tempWpm =  status[0];
//                int rest 

            } catch (FileNotFoundException ex) {
                Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            listLabel.get(resumeListIndex).setForeground(Color.yellow);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            listLabel.get(resumeListIndex).setForeground(null);

        }

    }

    class startNewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        }

    }

    class setTargetLabelListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            wpmFrame = new JFrame("SetWpm");
            wpmFrame.setSize(450, 175);
            wpmFrame.setVisible(true);

            JPanel wpmPanel = new JPanel();
            wpmFrame.add(wpmPanel);
            JLabel label = new JLabel("WORDS PER MINUTE Targeted:");
            wpmValueLabel = new JLabel("36");
            wpmValueLabel.setFont(font);
            wpmValueLabel.setForeground(Color.green);
            wpmPanel.add(label);
            wpmPanel.add(wpmValueLabel);

            slider = new JSlider(10, 100, 20);
            slider.addMouseListener(new sliderListener());
            slider.setSnapToTicks(true);
            wpmPanel.add(slider);
            slider.setMajorTickSpacing(10);
            slider.setMinorTickSpacing(2);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setValue(35);

            slider.setPreferredSize(new Dimension(400, 50));
            int value = 0;
//            slider.addChangeListener(new ChangeListener(){
//                public void stateChanged(ChangeEvent event ){
//                    value = slider.getValue();
//                    System.out.println(value);
//                }
//           });

            JButton okBut = new JButton("Ok");
            okBut.addActionListener(new okayWpmListener());
            okBut.setBackground(Color.ORANGE);
            wpmPanel.add(okBut);
            System.out.println("In mainPanel<<<");
            System.out.println("Current Value in Slider is" + currValue);
            System.out.println("\t\t\t>>>>>");
//            slider.set
//            slider.add(wpmPanel);

//            slider.addMouseListener(new sliderListener());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            targetLabel.setForeground(Color.cyan);

        }

        @Override
        public void mouseExited(MouseEvent e) {
            targetLabel.setForeground(null);
        }

    }

    class sliderListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int temp = slider.getValue();
//            System.out.println("Slider Clicked, current WPM is :" + temp);
            wpmValueLabel.setText("" + temp);
//              targetWPM = temp;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int temp = slider.getValue();
            wpmValueLabel.setText("" + temp);

        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            int temp = slider.getValue();
//            wpmValueLabel.setText(""+temp);
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    class playExitMIListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (thread != null) {
                thread.stop();
            }

            frame.getContentPane().remove(playPanel);
            frame.repaint();
            frame.getContentPane().add(mainPanel);
            frame.repaint();
            frame.setJMenuBar(null);

        }

    }

    class okayWpmListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            targetWPM = slider.getValue();
//            wpmFrame.setVisible(false);
            wpmFrame.dispose();

//            int temp = slider.getValue();
//            System.out.println(" Slider last Value :" + temp);
        }

    }

    class prevTextButListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            prevClicked++;
            displayText("prev", 0);
        }

    }

    class nextTextBut implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            prevClicked = 0;
            displayText("", 0);

        }

    }

    class startButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            wordProcessing();

            playPanel.remove(prevTextBut);
            playPanel.remove(playButton);
            playPanel.remove(nextTextBut);
            playPanel.remove(targetLabel);

            playPanel.add(typeArea);
            typeArea.setFont(font);
            typeArea.addKeyListener(new typeAreaListener());

//            checking wether the Panel consists the timeBar or not!
            Component[] components = playPanel.getComponents();

            boolean isOkay = true;
            for (Component c : components) {
                if (c == timeBar) {
                    isOkay = false;
                }

            }

            if (isOkay) {

                JLabel gapL = new JLabel("                                                                            ........    ");
                playPanel.add(gapL);

                timeBar = new JProgressBar(0, wordsLength - 1);
//            System.out.println("The Total Words int the Text are:"+wordsLength);
                playPanel.add(timeBar);
                timeBar.setBorderPainted(true);

            }

            frame.repaint();

            TimeThread theJob = new TimeThread();
            thread = new Thread(theJob);
            thread.start();

            typeArea.requestFocus();
            typeArea.setText("Type here");
            typeArea.selectAll();
            typeArea.setForeground(Color.blue);

            if (isIn(playPanel, pauseBut)) {
                pauseBut.setText("pause It");
                playPanel.add(pauseBut);
                pauseBut.setBackground(Color.blue);
                pauseBut.setForeground(Color.white);

                pauseBut.addActionListener(new pauseButListener());
            }
        }

    }

    class pauseButListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String buttonState = pauseBut.getText();
            if (buttonState == "pause It") {
                pauseBut.setText("resume It");
                thread.suspend();
                typeArea.setEditable(false);
                pauseBut.setBackground(Color.red);

            } else {
                pauseBut.setText("pause It");
                thread.resume();
                typeArea.setEditable(true);
                typeArea.requestFocus();
                pauseBut.setBackground(Color.blue);
                pauseBut.setForeground(Color.white);

            }

        }

    }

    public boolean isIn(JPanel panel, Component compo) {
//        boolean isOkay = true;
        Component[] components = panel.getComponents();

        boolean isOkay = true;
        for (Component c : components) {
            if (c == compo) {
                isOkay = false;
            }

        }
        return isOkay;

    }

    public void highlight(String s) {
        try {
            playTextArea.setAutoscrolls(true);
            Highlighter h = playTextArea.getHighlighter();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);

            if (!(s.equals("" + currentCaretPos))) {

//                System.out.println("The currentCaretPos didn't matched");
                int len = s.length();
                currentCaretPos = currentCaretPos + len + 1;

            }
            playTextArea.setCaretPosition(currentCaretPos);
            playTextArea.setCaretPosition(currentCaretPos);
            h.addHighlight(0, currentCaretPos, painter);
            playTextArea.repaint();

        } catch (BadLocationException ex) {
            Logger.getLogger(TypeGarage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class typeAreaListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            Character key = e.getKeyChar();
            if (key == ' ') {
                String userWord = typeArea.getText();
                String mainWord = Words[wordPosition];
                String spaceAddedMainWord = "";
                if (wordPosition > 0) {
                    spaceAddedMainWord = " " + mainWord;
                }

                if (userWord.equals(mainWord) || userWord.equals(spaceAddedMainWord)) {
                    timeBar.setValue(wordsTyped);

                    if (wordPosition == Words.length - 1) {
                        thread.stop();
                        System.out.println("Text ENded");
//                        showEndText();
                        playTextArea.setText("COngratulations!!!!!!....You Completed it");
                        typeArea.setText("Completed");
                        typeArea.setForeground(Color.green);
                        typeArea.setEditable(false);

                    } else {
                        wordPosition++;
                        typeArea.requestFocus();
                        typeArea.selectAll();
                        wordsTyped++;
//                    System.out.println("total words typed: "+wordsTyped);

                        typeArea.setForeground(Color.blue);
                        highlight(mainWord);

                    }

                } else {
                    typeArea.setForeground(Color.red);
//                    typeArea.setText("INCorrect"); 
                }

            }

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }

    class TimeThread implements Runnable {

//        int time = 0;
        @Override
        public void run() {
            while (true) {
                try {
                    int sec, minute;
                    minute = time / 60;
                    sec = time % 60;
                    if (sec < 10) {
                        timeNo.setText(" " + minute + ":0" + sec);
                    } else {
                        timeNo.setText(" " + minute + ":" + sec);
                    }

                    time--;
                    int elapsedTime = actualTime - time;
                    if ((elapsedTime / 30) != 0 && elapsedTime % 30 == 0) {

//                            int timeElapsed = actuae
                        currWpm = wordsTyped / (elapsedTime / 30);
                        currWpm *= 2;
                        currWpmLabel.setText("" + currWpm);
                        currWpmLabel.setFont(font);
                        currWpmLabel.setForeground(Color.MAGENTA);

                    }
                    Thread.sleep(1000);

//                    curWpm = wordsTyped/
                } catch (InterruptedException ex) {
                    System.out.println("Time error");
                }

            }
        }
    }

    public void displayText(String callFrom, int index) {
        try {

            File fileCollection = new File("FileCollection.txt");
            BufferedReader fileReader = new BufferedReader(new FileReader(fileCollection));
            String files = fileReader.readLine();
            fileReader.close();

            String[] fileNames;
            fileNames = files.split("/");

            int nameListLength = fileNames.length;
//            int arrayLength = randomValues.size();

//            System.out.println("The size of the RandomValues Array is" + arrayLength);
//            boolean isOld = true;
//            int random = 0;
//            random = (int) (Math.random() * length);
//            if (arrayLength == 0) {
//                randomValues.add(random);
//            }
//
//            while (isOld) {
////                boolean isNewest = false;
//                random = (int) (Math.random() * length);
////                System.out.println("First Random value was" + random);
//
//                for (int temp : randomValues) {
//
//                    if (random == temp) {
////                        System.out.println("Vlaue Matched : The temp value was" + temp);
//
////                        System.out.println("The rad");
//                        break;
//                    } else {
//
//                        isOld = false;
//                    }
//
//                }
//            }
//            System.out.println("Final Random Value is " + random + "\n\n");
//            System.out.println(" \t \tThe size of array is" + arrayLength);
            File theFile;
            theFile = new File(fileNames[nextText]);

            if (callFrom == "") {
//                theFile = new File(fileNames[nextText]);

                if (nextText < nameListLength - 1) {
                    nextText++;
                }

//                System.out.println("The randomm value is" + random);
//                randomValues.add(random);
                titleName.setText(theFile.getName());
            }

            if (callFrom == "prev") {

                theFile = null;
                if (nextText > 0) {
                    nextText--;

                }
                theFile = new File(fileNames[nextText]);

                System.out.println("\t" + theFile.getName());
                titleName.setText(theFile.getName());

            }

            if (callFrom == "open") {
                theFile = null;
                theFile = fileList.get(index);

            }

            if (callFrom == "resume") {
//                System.out.println("Called From Resume");
                theFile = null;
                String currTextTitle = listLabel.get(index).getText();
//                System.out.println("The current file to display as resume is :" + currTextTitle);
                theFile = new File(currTextTitle);
                File mainTextFile;

                for (String st : fileNames) {
                    mainTextFile = new File(st);
//                    String theFileName  = 
                    String mainTextTitle = ("Resume" + mainTextFile.getName());
                    System.out.println("main Title is:" + mainTextTitle);

                    if (mainTextTitle.equals(currTextTitle)) {
//                      
                        theFile = mainTextFile;
                        char[] c = new char[30];
                        mainTextTitle.getChars(6, mainTextTitle.length(), c, nameListLength);
//                        System.out.println("The character array is:");

                        String titleText = "";
                        for (char ch : c) {
//                            System.out.print(ch);
//                            System.out.println(titleText);
                            titleText = titleText + ch;

                        }
                        System.out.println("The title text to show is:" + titleText);
                        titleName.setText(titleText);
                        break;

                    } else {
//                        System.out.println("didn't matched with "+mainTextFile.getName());
                        mainTextFile = null;
                    }
                }
            }

            BufferedReader FileReader = new BufferedReader(new FileReader(theFile));
            String line;
            fileText = "";

            while ((line = FileReader.readLine()) != null) {
                fileText = fileText + line;

            }

            Font bigFont = new Font("serif", Font.BOLD, 28);

            if (callFrom == "open") {
                textArea.setText(fileText);
            } else {
                playTextArea.setText(fileText);
//                Cursor cursor = new Cursor();
//                playTextArea.setCaretPosition(wordPosition);
//                playTextArea.setCaretColor(Color.red);
                playTextArea.select(0, 10);
                playTextArea.setSelectedTextColor(Color.green);
                playTextArea.setEditable(false);

//playTextArea.setCursor(cursor);
            }

//            playTextArea.setFont(bigFont);
            fileReader.close();
//        wordProcessing(fileText);

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public void wordProcessing() {
        Words = fileText.split(" ");
//        System.out.println(Words[10]);
        wordsLength = Words.length;

        if (time == 0) {
            System.out.println("Time initialized");
            time = wordsLength * 60 / targetWPM;
        }

        actualTime = time;

    }
}