package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.TreeSet;

import OwnException.ConflictRelationshipException;
import OwnException.DuplicatedNameException;
import OwnException.NoAvailableException;
import OwnException.NoParentException;
import OwnException.NoSuchAgeException;
import OwnException.NotToBeClassmatesException;
import OwnException.NotToBeColleaguesException;
import OwnException.NotToBeCoupledException;
import OwnException.NotToBeFriendsException;
import OwnException.NotToBeParentException;
import OwnException.PersonTypeChangeException;
import OwnException.TooYoungException;
import OwnInterface.WriteAndReadTextFile;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.hsqldb.Server;

public class Mininet extends Application implements WriteAndReadTextFile {

    private File file1 = new File("src/TextFile/people.txt");
    private File file2 = new File("src/TextFile/relations.txt");
    private static Person source_record; // used for recording source person has
                                         // been select in find out connect
                                         // scene
    private static Person target_record; // used for recording target person has
                                         // been select in find out connect
                                         // scene
    private static Person connect1_record; // used for recording first Person
                                           // has been select in connect person
                                           // scene
    private static Person connect2_record; // used for recording second Person
                                           // has been select in connect person
                                           // scene
    private static Person bl_select_record; // used for recording the Person has
                                            // been select in blood relationship
                                            // scene
    private static Person select_record; // used for recording the Person has
                                         // been select in select scene
    private static Person O_record; // used for recording the Person object
                                    // created by judge_Object method.
    private static Person p_record; // used for recording the Person object
                                    // created by judge_Person method.
    private String n; // when user enter the person information, it is used for
                      // recording the input name.
    private String i;
    private String s;
    private String sta;
    private String g;
    private int a;
    protected static Server hsqlServer = null;
    protected static Connection con = null;
    protected static ResultSet rs = null;
    protected static PreparedStatement prsm = null;
    /**
     * used for recording the relationships which has been already traversed.
     * Serve FindOutChain() method.
     */
    private ArrayList<Relationships> EndList = new ArrayList<>();
    /**
     * used for recording the relationships which will be traversed. Serve
     * FindOutChain() method Serve FindOutChain method.
     */
    private ArrayList<Relationships> StartList = new ArrayList<>();
    /**
     * used for recording every layer of elements in the shortest connected
     * chain in FindOutChain() method and SetChainText(Person src) method. e.g.
     * A is B friend, B is C friend, C is D friend, when user search the chain
     * between A and D, ResultList will store the information like this. "B,",
     * "C," (exclude A and D). e.g. A is B friend, A is C friend, B is D friend,
     * C is D friend, D is F friend, when user search the chain between A and F,
     * ResultList will store the information like this. "B,C", "D". (exclude A
     * and F). Serve FindOutChain method
     */
    private ArrayList<String> ResultList = new ArrayList<>();
    /**
     * used for controlling the while loop in FindOutChain() method and judging
     * in the SetChainText(Person src) method which serve the FindOutChain
     * method. the default value is false.
     */
    private boolean judge = false;
    /**
     * used for serving the FindOutChain() method and SetStartAndEndList()
     * method count the total number of friends relationships.
     */
    private int total = 0;
    private static Scene sc_menu = null;
    private static Scene sc_add = null;
    private static Scene sc_guide = null;
    private static Scene sc_select = null;
    private static Scene sc_list = null;
    private static Scene sc_add_parent = null;
    private static Scene sc_Connect_person = null;
    private static Scene sc_find_con = null;
    private static Scene sc_blood_re = null;
    private static Scene sc_file = null;
    private static Scene sc_DB = null;
    private static Stage st = null;
    private static Stage sub_st = null;

    @FXML // fx:id="image_txt"
    private TextField image_txt; // Value injected by FXMLLoader

    @FXML // fx:id="state_txt"
    private TextField state_txt; // Value injected by FXMLLoader

    @FXML // fx:id="name_txt"
    private TextField name_txt; // Value injected by FXMLLoader

    @FXML // fx:id="gender_txt"
    private TextField gender_txt; // Value injected by FXMLLoader

    @FXML // fx:id="age_txt"
    private TextField age_txt; // Value injected by FXMLLoader

    @FXML // fx:id="status_txt"
    private TextField status_txt; // Value injected by FXMLLoader

    @FXML // fx:id="livi"
    private ListView<String> livi; // Value injected by FXMLLoader
    @FXML // fx:id="onePName"
    private TextField onePName; // Value injected by FXMLLoader

    @FXML // fx:id="anoPAge"
    private TextField anoPAge; // Value injected by FXMLLoader

    @FXML // fx:id="anoPImage"
    private TextField anoPImage; // Value injected by FXMLLoader

    @FXML // fx:id="anoPStatus"
    private TextField anoPStatus; // Value injected by FXMLLoader

    @FXML // fx:id="anoPGender"
    private TextField anoPGender; // Value injected by FXMLLoader

    @FXML // fx:id="onePStatus"
    private TextField onePStatus; // Value injected by FXMLLoader

    @FXML // fx:id="onePGender"
    private TextField onePGender; // Value injected by FXMLLoader

    @FXML // fx:id="onePAge"
    private TextField onePAge; // Value injected by FXMLLoader

    @FXML // fx:id="onePState"
    private TextField onePState; // Value injected by FXMLLoader

    @FXML // fx:id="anoPName"
    private TextField anoPName; // Value injected by FXMLLoader

    @FXML // fx:id="anoPState"
    private TextField anoPState; // Value injected by FXMLLoader

    @FXML // fx:id="onePImage"
    private TextField onePImage; // Value injected by FXMLLoader

    @FXML // fx:id="displayButton"
    private Button displayButton; // Value injected by FXMLLoader

    @FXML // fx:id="extraAddBT"
    private Button extraAddBT; // Value injected by FXMLLoader

    @FXML // fx:id="t_a"
    private TextArea t_a; // Value injected by FXMLLoader

    @FXML // fx:id="t_n"
    private TextArea t_n; // Value injected by FXMLLoader

    @FXML // fx:id="EMessage"
    private TextArea EMessage; // Value injected by FXMLLoader

    @FXML // fx:id="showProButton"
    private MenuItem showProButton; // Value injected by FXMLLoader

    @FXML // fx:id="DelateProButton"
    private MenuItem DelateProButton; // Value injected by FXMLLoader

    @FXML // fx:id="menuButton"
    private MenuButton menuButton; // Value injected by FXMLLoader

    @FXML // fx:id="UpdateProButton"
    private MenuItem UpdateProButton; // Value injected by FXMLLoader

    @FXML // fx:id="selectList"
    private ListView<String> selectList; // Value injected by FXMLLoader

    @FXML // fx:id="selectS"
    private TextField selectS; // Value injected by FXMLLoader

    @FXML // fx:id="selectN"
    private TextField selectN; // Value injected by FXMLLoader

    @FXML // fx:id="selectI"
    private TextField selectI; // Value injected by FXMLLoader

    @FXML // fx:id="selectG"
    private TextField selectG; // Value injected by FXMLLoader

    @FXML // fx:id="selectA"
    private TextField selectA; // Value injected by FXMLLoader

    @FXML // fx:id="selectSta"
    private TextField selectSta; // Value injected by FXMLLoader

    @FXML // fx:id="updatepane"
    private AnchorPane updatepane; // Value injected by FXMLLoader

    @FXML // fx:id="selectEMessage"
    private TextArea selectEMessage; // Value injected by FXMLLoader

    @FXML // fx:id="Inforpane"
    private AnchorPane Inforpane; // Value injected by FXMLLoader

    @FXML // fx:id="PerAge"
    private TextField PerAge; // Value injected by FXMLLoader

    @FXML // fx:id="PerImage"
    private TextField PerImage; // Value injected by FXMLLoader

    @FXML // fx:id="PerGender"
    private TextField PerGender; // Value injected by FXMLLoader

    @FXML // fx:id="PerState"
    private TextField PerState; // Value injected by FXMLLoader

    @FXML // fx:id="PerStatus"
    private TextField PerStatus; // Value injected by FXMLLoader

    @FXML // fx:id="PerName"
    private TextField PerName; // Value injected by FXMLLoader

    @FXML // fx:id="PerAge1"
    private AnchorPane Deletepane; // Value injected by FXMLLoader

    @FXML // fx:id="PerAge1"
    private TextField PerAge1; // Value injected by FXMLLoader

    @FXML // fx:id="PerStatus1"
    private TextField PerStatus1; // Value injected by FXMLLoader

    @FXML // fx:id="PerImage1"
    private TextField PerImage1; // Value injected by FXMLLoader

    @FXML // fx:id="PerState1"
    private TextField PerState1; // Value injected by FXMLLoader

    @FXML // fx:id="PerGender1"
    private TextField PerGender1; // Value injected by FXMLLoader

    @FXML // fx:id="PerName1"
    private TextField PerName1; // Value injected by FXMLLoader

    @FXML // fx:id="LabelDelete"
    private Label LabelDelete; // Value injected by FXMLLoader

    @FXML // fx:id="DeleteRLV"
    private ListView<String> DeleteRLV; // Value injected by FXMLLoader

    @FXML // fx:id="PerAge1"
    private AnchorPane DeleteMsgpane; // Value injected by FXMLLoader

    @FXML // fx:id="FBRlv"
    private ListView<String> FBRlv; // Value injected by FXMLLoader

    @FXML // fx:id="BLTextArea"
    private ListView<String> BRListView; // Value injected by FXMLLoader

    @FXML // fx:id="connect_one_lv"
    private ListView<String> connect_one_lv; // Value injected by FXMLLoader

    @FXML // fx:id="connect_two_lv"
    private ListView<String> connect_two_lv; // Value injected by FXMLLoader

    @FXML // fx:id="ConnectMessage"
    private TextField ConnectMessage; // Value injected by FXMLLoader

    @FXML // fx:id="Find_Con_Message"
    private TextField Find_Con_Message; // Value injected by FXMLLoader

    @FXML // fx:id="Dir_Relat_TF"
    private TextField Dir_Relat_TF; // Value injected by FXMLLoader

    @FXML // fx:id="Find_Con_Target"
    private ListView<String> Find_Con_Target; // Value injected by FXMLLoader

    @FXML // fx:id="Con_Chain_TF"
    private ListView<String> Con_Chain_TF; // Value injected by FXMLLoader

    @FXML // fx:id="Find_Con_Source"
    private ListView<String> Find_Con_Source; // Value injected by FXMLLoader
    @FXML // fx:id="ConChainButton"
    private Button ConChainButton; // Value injected by FXMLLoader
    @FXML // fx:id="ConChainVBox"
    private VBox ConChainVBox; // Value injected by FXMLLoader

    @FXML // fx:id="FileView"
    private ListView<String> FileView; // Value injected by FXMLLoader

    @FXML // fx:id="FileMessage"
    private TextField FileMessage; // Value injected by FXMLLoader

    @FXML // fx:id="FileReView"
    private ListView<String> FileReView; // Value injected by FXMLLoader

    @FXML // fx:id="DBMessage"
    private TextArea DBMessage; // Value injected by FXMLLoader
    @FXML // fx:id="CreateTableText"
    private TextField CreateTableText; // Value injected by FXMLLoader

    @FXML // fx:id="ReadRelationFromDB"
    private ListView<String> ReadRelationFromDB; // Value injected by FXMLLoader
    @FXML // fx:id="ReadPersonFromDB"
    private ListView<String> ReadPersonFromDB; // Value injected by FXMLLoader
    @FXML // fx:id="UploadToDBText"
    private TextField UploadToDBText; // Value injected by FXMLLoader
    @FXML // fx:id="GuideListView"
    private ListView<String> GuideListView; // Value injected by FXMLLoader

    protected static TreeSet<Person> persons = new TreeSet<Person>(new Comparator<Person>() {
        public int compare(Person o1, Person o2) {
            if (o1.getName().compareTo(o2.getName()) > 0)
                return 1;
            if (o1.getName().compareTo(o2.getName()) < 0)
                return -1;
            return 0;
        };
    });

    /**
     * Using TreeSet, and override an Anonymous Comparator's compare method to
     * sort the name in alphabetic order.
     */
    protected static TreeSet<Relationships> relat = new TreeSet<Relationships>(new Comparator<Relationships>() {
        public int compare(Relationships o1, Relationships o2) {
            if (o1.getP1().getName().compareTo(o2.getP1().getName()) > 0)
                return 1;
            if (o1.getP1().equals(o2.getP2()) && o1.getP2().equals(o2.getP1()) && o1.getRe().equals(o2.getRe()))
                return 0;
            if (o1.getP1().equals(o2.getP1()) && o1.getP2().equals(o2.getP2()) && o1.getRe().equals(o2.getRe()))
                return 0;
            return -1;
        };
    });

    // DataBase function in DataBase scene start.
    /**
     * when the user click the connect to database button, this method will be
     * called, the error message will show under the button if connect fail.
     */
    public void ConnectToDB() {
        hsqlServer = new Server();
        hsqlServer.setLogWriter(null);
        hsqlServer.setSilent(true);
        hsqlServer.setDatabaseName(0, "JavaAssignmentDB");
        hsqlServer.setDatabasePath(0, "file:MYDB");
        hsqlServer.start();
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            con = DriverManager.getConnection("jdbc:hsqldb:JavaAssignmentDB", "SA", "123");
            if (con != null) {
                DBMessage.setVisible(true);
                DBMessage.setText("Connect successfully!");
            } else {
                DBMessage.setVisible(true);
                DBMessage.setStyle("-fx-text-inner-color: red;");
                DBMessage.setText("Connect failed!");
            }
        } catch (Exception e) {
            DBMessage.setVisible(true);
            DBMessage.setStyle("-fx-text-inner-color: red;");
            DBMessage.setText("Connect failed!");
        }
    }

    /**
     * create 'MiniNet_Relation' table and 'MiniNet_Person' table, if these two
     * table is already exist, this method will drop first then recreate it,
     * avoiding the sql syntax error when these two table are already exist, so
     * the method check it first.
     */
    public void createTable() {
        int recording = 0;
        try {
            DatabaseMetaData meta1 = con.getMetaData();
            rs = meta1.getTables(null, null, "MiniNet_Relation", null);
            if (rs.next()) {
                recording++;
            }
            rs.close();
            DatabaseMetaData meta2 = con.getMetaData();
            rs = meta2.getTables(null, null, "MiniNet_Person", null);
            if (rs.next()) {
                recording++;
            }
            rs.close();
            if (recording == 2) {
                createRelationTable();
                createPersonTable();
                CreateTableText.setText("Two table has been created!");
            } else {
                DropTable();
                createRelationTable();
                createPersonTable();
                CreateTableText.setText("Two table has been created!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * a private method which serve the createTable() method, this method is
     * used for creating the Relation Table called "MiniNet_Relation"
     */
    private void createRelationTable() {
        try {
            prsm = con.prepareStatement("CREATE TABLE " + "MiniNet_Relation " + "(person1_name VARCHAR(50) NOT NULL, "
                    + "person2_name VARCHAR(50) NOT NULL, relationships VARCHAR(10));");
            prsm.executeUpdate();
            con.commit();
            prsm.close();
        } catch (Exception e) {
            e.printStackTrace();
            CreateTableText.setText("Create table failed!");
        }
    }

    /**
     * a private method which serve the creatTable() method, this method is used
     * for creating the Person Table called "MiniNet_Person"
     */
    private void createPersonTable() {
        try {

            prsm = con.prepareStatement("CREATE TABLE " + "MiniNet_Person"
                    + "(pname VARCHAR(50) NOT NULL, profile_image VARCHAR(60), status VARCHAR(60), "
                    + "gender CHAR(5), age INTEGER check(age between 0 and 150),state VARCHAR(5), "
                    + "PRIMARY KEY (pname));");
            prsm.executeUpdate();
            con.commit();
            prsm.close();

        } catch (Exception e) {
            e.printStackTrace();
            CreateTableText.setText("Create table failed!");
        }
    }
    
    /**
     * avoiding the insert error and keep the newest data, this method will delete the data from 
     * two table,then insert them again. The error message will show if upload failed.
     */
    public void UploadToDB() {
        try {
            DeleteDataFromDB();
            addPersonToDB();
            addRelationToDB();
            UploadToDBText.setText("Upload successful!");
        } catch (Exception e) {
            e.printStackTrace();
            UploadToDBText.setText("upload failed");
        }

    }

    /**
     * private method serve the UploadToDB() method, the function is deleting the data in two tables
     * from database
     */
    private void DeleteDataFromDB() {
        try {
            prsm = con.prepareStatement("Delete from MiniNet_Person;");
            prsm.executeUpdate();
            con.commit();
            prsm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            prsm = con.prepareStatement("Delete from MiniNet_Relation;");
            prsm.executeUpdate();
            con.commit();
            prsm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * this private method serve create table method, it is used for drop the table from MiniNet_Person
     * and MiniNet_Relation
     */
    private void DropTable() {
        try {
            prsm = con.prepareStatement("DROP Table MiniNet_Person;");
            prsm.executeUpdate();
            con.commit();
            prsm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            prsm = con.prepareStatement("DROP Table MiniNet_Relation;");
            prsm.executeUpdate();
            con.commit();
            prsm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method will display the person information from database.
     */
    public void ViewPersonFromDB() {
        ArrayList<String> al = new ArrayList<>();
        String str = null;
        try {
            prsm = con.prepareStatement("select * from MiniNet_Person;");
            rs = prsm.executeQuery();
            while (rs.next()) {
                str = (rs.getString("pname") + "\t" + rs.getString("profile_image") + "\t" + rs.getString("status")
                        + "\t" + rs.getString("gender") + "\t" + rs.getInt("age") + "\t" + rs.getString("state"));
                al.add(str);
            }
            ObservableList<String> data = FXCollections.observableArrayList(al);
            ReadPersonFromDB.setItems(data);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method will display the relation information from database.
     */
    public void ViewRelationFromDB() {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> al = new ArrayList<>();
        try {
            prsm = con.prepareStatement("select * from MiniNet_Relation;");
            rs = prsm.executeQuery();
            while (rs.next()) {
                sb.append(rs.getString("person1_name") + "\t" + rs.getString("person2_name") + "\t"
                        + rs.getString("relationships"));
                al.add(sb.toString());
            }
            ObservableList<String> data = FXCollections.observableArrayList(al);
            ReadRelationFromDB.setItems(data);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * upload the information of persons to the database, if failed, the error message will
     * be printed out.
     */
    public void addPersonToDB() {
        try {
            for (Person p : persons) {
                prsm = con.prepareStatement("Insert into MiniNet_Person VALUES(?,?,?,?,?,?);");
                prsm.setString(1, p.getName());
                prsm.setString(2, p.getProfile_image());
                prsm.setString(3, p.getStatus());
                prsm.setString(4, p.getGender());
                prsm.setInt(5, p.getAge());
                prsm.setString(6, p.getState());
                prsm.executeUpdate();
                con.commit();
                prsm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Add person failed");
        }
    }

    /**
     * upload the information of relations to the database, if failed, the error message will
     * be printed out.
     */
    public void addRelationToDB() {
        try {
            for (Relationships re : relat) {
                prsm = con.prepareStatement("Insert into MiniNet_Relation VALUES(?,?,?);");
                prsm.setString(1, re.getP1().getName());
                prsm.setString(2, re.getP2().getName());
                prsm.setString(3, re.getRe());
                prsm.executeUpdate();
                con.commit();
                prsm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Add relation failed");
        }
    }

    /**
     * when click the back button in Database scene, then it will show the menu
     * scene, and clear the content in Database scene.
     */
    public void DBBackButton() {
        DBMessage.clear();
        CreateTableText.clear();
        UploadToDBText.clear();
        ReadPersonFromDB.setItems(null);
        ReadRelationFromDB.setItems(null);
        st.setScene(sc_menu);
        st.show();
    }
    // DataBase function in DataBase scene end.

    // File function in File scene start
    /**
     * upload the information of person and relations to the text file, if failed,
     * the error message will display
     */
    public void UploadToFile() {
        try {
            write_to_file();
            FileMessage.setVisible(true);
            FileMessage.setStyle("-fx-text-inner-color: blue;");
            FileMessage.setText("sucessful");
        } catch (IOException e) {
            FileMessage.setVisible(true);
            FileMessage.setStyle("-fx-text-inner-color: red;");
            FileMessage.setText("Write into file fail");
        }
    }

    /**
     * display the information of person in person.txt, the error message will show
     * if failed.
     */
    public void DisplayPersonButton() {
        try {
            ObservableList<String> ovb = FXCollections.observableArrayList(ReadFromFile(file1));
            if (ovb.size() == 0) {
                ovb.add("there is no content in person.txt");
            }
            FileView.setItems(ovb);
        } catch (IOException e) {
            FileMessage.setVisible(true);
            FileMessage.setStyle("-fx-text-inner-color: red;");
            FileMessage.setText("Read from people.txt fail");
        }
    }

    /**
     * display the information of relationship in relation.txt, the error message will 
     * show if failed.
     */
    public void DisplayReButton() {
        try {
            ObservableList<String> ovb = FXCollections.observableArrayList(ReadFromFile(file2));
            if (ovb.size() == 0) {
                ovb.add("there is no content in person.txt");
            }
            FileReView.setItems(ovb);
        } catch (IOException e) {
            FileMessage.setVisible(true);
            FileMessage.setStyle("-fx-text-inner-color: red;");
            FileMessage.setText("Read from relations.txt fail");
        }
    }
    
    /**
     * when click the back button in File scene, then it will show the menu
     * scene, and clear the content in File scene. 
     */
    public void FileBackButton() {
        FileView.setItems(null);
        FileReView.setItems(null);
        FileMessage.clear();
        FileMessage.setVisible(false);
        st.setScene(sc_menu);
        st.show();
    }// File function in File scene end

    // add person function in add person scene start
    /**
     * when the user click the confirm button in add scene, this method will be
     * called, this method is used for collecting the data from the user input.
     * All of the Exception will be handled in GUI, when the exception appear,
     * the error message will show on TextArea t_a and t_n.
     * 
     * @exception DuplicatedNameException
     *                when the user input the name which is already exist, it
     *                will throw this exception, because we assume the user name
     *                is a primary key, it can not allow duplicated.
     * @exception NoSuchAgeException
     *                If the user enter an age which is less than 0 or more than
     *                150, it will throw this exception.
     * @exception NoParentException
     *                If the user want to add a child or a young child, he will
     *                be forced to add this child's parent, and at this time,
     *                the add parent button can be used.
     */
    public void add_person() {
        boolean judge = false;
        t_n.setStyle("-fx-text-inner-color: red;");
        t_a.setStyle("-fx-text-inner-color: red;");
        t_n.clear();
        t_a.clear();
        do {
            try {
                addSinglePerson(name_txt, image_txt, status_txt, gender_txt, age_txt, state_txt);
                if (persons.size() != 0)
                    check_name(n);
                judge_Person(n, i, s, g, a, sta);
                st.setScene(sc_menu);
                st.show();
                judge = true;
            } catch (DuplicatedNameException e) {
                t_n.setVisible(true);
                t_n.setText("This name is already exist!");
            } catch (NoSuchAgeException e) {
                t_a.setVisible(true);
                t_a.setText("A person's age can not be negative or over 150!");
            } catch (NoParentException e) {
                t_a.setVisible(true);
                t_a.setText("This person is a child, you need add his parent first!");
            } finally {
                clear(name_txt, image_txt, status_txt, gender_txt, state_txt, age_txt);
            }
        } while (!judge);
        t_n.setVisible(false);
        t_a.setVisible(false);
        t_n.clear();
        t_a.clear();
    }
    
    /**
     * this clear method is used for clear batch content in TextFied
     * 
     * @param t1
     *            used for clearing name
     * @param t2
     *            used for clearing profile image
     * @param t3
     *            used for clearing status
     * @param t4
     *            used for clearing gender
     * @param t5
     *            used for clearing state
     * @param t6
     *            used for clearing age the order of parameters here can be
     *            randomly.
     */
    private void clear(TextField t1, TextField t2, TextField t3, TextField t4, TextField t5, TextField t6) {
        t1.clear();
        t2.clear();
        t3.clear();
        t4.clear();
        t5.clear();
        t6.clear();
    }
    
    /**
     * private method, aiming to check the name ensure that it is not
     * duplicated.
     * it serve add_person() method and updatePerson() method
     * 
     * @param the
     *            name that the user want to add
     * @throws DuplicatedNameException
     *             assume the name alone can be used as the identifier, the name
     *             that user want to add can not be duplicated.
     * @author KAI ZHANG
     * @date 2018/5/13
     */
    private void check_name(String n) throws DuplicatedNameException {
        for (Person p : persons) {
            if (p.getName().equals(n)) {
                throw new DuplicatedNameException("adding a person who has the same name", n);
            }
        }
    }
    
    /**
     * this private method is used for judge the person through his age, if it
     * is an adult, create object directly, if it is a child or young child, the
     * user need to add its parent first.
     * 
     * @param n
     *            person's name
     * @param i
     *            person's profile image
     * @param s
     *            person's status
     * @param g
     *            person's gender
     * @param a
     *            person's age
     * @param st
     *            person's state
     * @throws NoParentException
     *             if the child is added, the user need to add his parent first
     */
    private void judge_Person(String n, String i, String s, String g, int a, String st) throws NoParentException {
        if (a > 16) {
            persons.add(new Adult(n, i, s, g, a, st));
        } else if (a < 3) {
            p_record = new YoungChild(n, i, s, g, a, st);
            extraAddBT.setVisible(true);
            extraAddBT.setDisable(false);
            throw new NoParentException("need to add parent first");
        } else {
            p_record = new Child(n, i, s, g, a, st);
            extraAddBT.setVisible(true);
            extraAddBT.setDisable(false);
            throw new NoParentException("need to add parent first");
        }
    }
    
    /**
     * a private method which is used for assigning the value of content which
     * the user input to the corresponding attributes. * It serve add_person()
     * method
     * 
     * @param t1
     *            the textField which is used for enter the name
     * @param t2
     *            the textField which is used for enter the profile image
     * @param t3
     *            the textField which is used for enter the status
     * @param t4
     *            the textField which is used for enter the gender
     * @param t5
     *            the textField which is used for enter the age
     * @param t6
     *            the textField which is used for enter the state
     * @throws NoSuchAgeException
     *             Customize Exception if the age is less than 0 or more than
     *             150, it will throw this Exception
     */
    private void addSinglePerson(TextField t1, TextField t2, TextField t3, TextField t4, TextField t5, TextField t6)
            throws NoSuchAgeException {
        n = t1.getText();
        i = t2.getText();
        s = t3.getText();
        g = t4.getText();
        sta = t6.getText();
        a = Integer.parseInt(t5.getText());
        if (a < 0 || a > 150)
            throw new NoSuchAgeException("a person's age can not be negative or over 150", a);
    }
    
    /**
     * when click the back button in add scene, then it will show the menu
     * scene, and clear the content and set the text filed and button invisible
     * or disable in add scene.
     */
    public void AddBackButton() {
        extraAddBT.setVisible(false);
        extraAddBT.setDisable(true);
        clear(name_txt, image_txt, status_txt, gender_txt, state_txt, age_txt);
        t_n.setVisible(false);
        t_a.setVisible(false);
        t_n.clear();
        t_a.clear();
        st.setScene(sc_menu);
        st.show();
    }
    
    /**
     * the add parent button will show when the user add a child, when click
     * this button the add parent scene will show.
     */
    public void addParentButton() {
        extraAddBT.setVisible(false);
        extraAddBT.setDisable(true);
        t_n.setVisible(false);
        t_a.setVisible(false);
        t_n.clear();
        t_a.clear();
        st.setScene(sc_add_parent);
        st.show();
    }// add person function in add person scene end

    // add child parent function in add parent scene start
    /**
     * when click the back button in adding child's parent scene, then it will
     * show the menu scene, and clear the content and set the the text field
     * invisible in adding child's parent scene.
     */
    public void AddParentBackButton() {
        clear(onePName, onePImage, onePStatus, onePGender, onePState, onePAge);
        clear(anoPName, anoPImage, anoPStatus, anoPGender, anoPState, anoPAge);
        EMessage.setVisible(false);
        EMessage.clear();
        st.setScene(sc_add);
        st.show();
    }
    
    /**
     * when the user click the confirm button in add parent scene, this method
     * will be called, this method is used for collecting the data from the user
     * input. All of the Exception will be handled in GUI, when the exception
     * appear,the Text filed EMessage will show the error message.
     * 
     * @throws NoParentException
     */

    public void addChildParent() throws NoParentException {
        EMessage.setStyle("-fx-text-inner-color: red;");
        boolean judge = false;
        do {
            try {
                addSinglePerson(onePName, onePImage, onePStatus, onePGender, onePAge, onePState);
                judge_Object(n, i, s, g, a, sta);
                Person p1 = O_record;
                addSinglePerson(anoPName, anoPImage, anoPStatus, anoPGender, anoPAge, anoPState);
                judge_Object(n, i, s, g, a, sta);
                Person p2 = O_record;
                p1.ToBeCouple(p2);
                p_record.ToBeParent(p1);
                p2.ToBeParent(p_record);
                persons.add(p1);
                persons.add(p2);
                persons.add(p_record);
                st.setScene(sc_menu);
                st.show();
                judge = true;
            } catch (NoSuchAgeException e) {
                EMessage.setVisible(true);
                EMessage.setText("A person's age can not be negative or over 150!");
            } catch (InputMismatchException e) {
                EMessage.setVisible(true);
                EMessage.setText("Wrong age!");
            } catch (NotToBeCoupledException e) {
                EMessage.setVisible(true);
                EMessage.setText("Can not add a person who are not a adult into couple relationships!");
            } catch (NoAvailableException e) {
                EMessage.setVisible(true);
                EMessage.setText("At least one person is already connected with another adult as a couple!");
            } catch (NotToBeParentException e) {
                EMessage.setVisible(true);
                EMessage.setText("A child can not be one of parent!");
            } catch (ConflictRelationshipException e) {
                EMessage.setVisible(true);
                EMessage.setText("Can not connect two couple or itself to parent relationships!");
            } finally {
                clear(onePName, onePImage, onePStatus, onePGender, onePState, onePAge);
                clear(anoPName, anoPImage, anoPStatus, anoPGender, anoPState, anoPAge);
            }
        } while (!judge);
        EMessage.setVisible(false);
        EMessage.clear();
    }
    
    /**
     * this private method serve the addChildParent() method, base on the different age create the 
     * different object.
     * @param n person name
     * @param i person image
     * @param s person status
     * @param g person gender
     * @param a person age
     * @param st person state
     */
    private void judge_Object(String n, String i, String s, String g, int a, String st) {
        if (a > 16) {
            O_record = new Adult(n, i, s, g, a, s);
        } else if (a < 3) {
            O_record = new YoungChild(n, i, s, g, a, st);
        } else {
            O_record = new Child(n, i, s, g, a, st);
        }
    }// add child parent function in add parent scene end
    
    // select one person function in select scene start
    /**
     * show the information of person under show person's name in select one person
     * scene
     */
    public void selectPerson() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        selectList.setItems(data);
    }
    
    /**
     * click the item in ListView in select scene, the attribute select_record
     * will get the corresponding person object.
     */
    public void selectedListView() {
        select_record = select_person(selectList.getSelectionModel().getSelectedItem());
    }
    
    /**
     * enter a name and return the corresponding person object.
     * @param n the person's name
     * @return the corresponding person object
     */
    public Person select_person(String n) {
        Person ps = null;
        for (Person p : persons) {
            if (p.getName().equals(n)) {
                ps = p;
            }
        }
        return ps;
    }
    
    /**
     * when the user click the Show profile Button in the menu button
     * "Please select operation", the pane will show personal information. The
     * personal information will show on the Inforpane. when the user click the
     * Show Profile Button, the program also set other panes to invisible and
     * disable.
     */
    public void showViewButton() {
        selectEMessage.clear();
        selectEMessage.setVisible(false);
        Deletepane.setVisible(false);
        Deletepane.setDisable(true);
        updatepane.setVisible(false);
        updatepane.setDisable(true);
        DeleteMsgpane.setVisible(false);
        DeleteMsgpane.setDisable(true);
        Inforpane.setDisable(false);
        Inforpane.setVisible(true);
        PerName.setText(select_record.getName());
        PerImage.setText(select_record.getProfile_image());
        PerStatus.setText(select_record.getStatus());
        PerGender.setText(select_record.getGender());
        PerAge.setText(String.valueOf(select_record.getAge()));
        PerState.setText(select_record.getState());
    }
    
    /**
     * when the user click the Update profile Button in the menu button
     * "Please select operation", the pane will show updating personal
     * information. when the user click the Update Profile Button, the program
     * also set other panes to invisible and disable.
     */
    public void updateViewButton() {
        selectEMessage.clear();
        selectEMessage.setVisible(false);
        Deletepane.setVisible(false);
        Deletepane.setDisable(true);
        Inforpane.setVisible(false);
        Inforpane.setDisable(true);
        DeleteMsgpane.setVisible(false);
        DeleteMsgpane.setDisable(true);
        updatepane.setDisable(false);
        updatepane.setVisible(true);
    }
    
    /**
     * after updating the person's profile, when the user click Confirm button,
     * it will execute this method this method used for get the content from the
     * text field and update the data
     * 
     * @exception DuplicatedNameException
     *                if the person enter the name which is conflict with other
     *                person's name will throw this exception, and this
     *                exception will handle in GUI, the selectEMessage is used
     *                for display the error message.
     * @exception PersonTypeChangeException
     *                this update function do not allow user to change the
     *                person type through changing the age, if the user do this,
     *                it will throw this exception, because it will cause a
     *                series of error, maybe this person's relationship will
     *                change(e.g. a person who is a adult, if the user want to
     *                change his age, then change him to a child, the
     *                relationships like colleague,couple is invalid for child.)
     *                if the user want to do that, he need to delete this person
     *                first, then add again, including this person's information
     *                and relationship.
     * @author KAI ZHANG
     */
    public void updatePerson() {
        selectEMessage.setStyle("-fx-text-inner-color: red;");
        boolean judge = false;
        do {
            try {
                int a = Integer.parseInt(selectA.getText());
                String na = select_record.getName();
                if (!(selectN.getText().equals(na)))
                    check_name(selectN.getText());
                if (select_record instanceof Adult && a <= 16 || select_record instanceof Child && a > 16
                        || select_record instanceof Child && a < 3 || select_record instanceof YoungChild && a >= 3) {
                    throw new PersonTypeChangeException(
                            "can not update this person to other type of person, you need delete first, then readd it!");
                }
                select_record.setName(selectN.getText());
                select_record.setProfile_image(selectI.getText());
                select_record.setStatus(selectS.getText());
                select_record.setAge(a);
                select_record.setGender(selectG.getText());
                select_record.setState(selectSta.getText());
                updatepane.setVisible(false);
                updatepane.setDisable(true);
                judge = true;
            } catch (DuplicatedNameException e) {
                selectEMessage.setVisible(true);
                selectEMessage.setText("This name is already exist!");
            } catch (PersonTypeChangeException e) {
                selectEMessage.setVisible(true);
                selectEMessage.setText(
                        "Can not update this person to other type of person, you need delate first, then readd it!");
            } finally {
                clear(selectN, selectI, selectS, selectG, selectSta, selectA);
            }
        } while (!judge);
        selectEMessage.clear();
        selectEMessage.setVisible(false);
    }
    
    /**
     * when the user click the Delete person Button in the menu button
     * "Please select operation", the pane will show personal information and
     * his relationship. under the menu button will appear the information to
     * ensure that whether the user really want to delete it. when the user
     * click the delete person Button, the program also set other panes to
     * invisible and disable.
     */
    public void DeleteViewButton() {
        selectEMessage.clear();
        selectEMessage.setVisible(false);
        Inforpane.setVisible(false);
        Inforpane.setDisable(true);
        updatepane.setVisible(false);
        updatepane.setDisable(true);
        Deletepane.setDisable(false);
        Deletepane.setVisible(true);
        PerName1.setText(select_record.getName());
        PerImage1.setText(select_record.getProfile_image());
        PerStatus1.setText(select_record.getStatus());
        PerGender1.setText(select_record.getGender());
        PerAge1.setText(String.valueOf(select_record.getAge()));
        PerState1.setText(select_record.getState());
        ArrayList <String> al=new ArrayList<>();
        for(Relationships re:relat){
            if(re.getP1().equals(select_record)){
                al.add(re.getP2().getName()+"\t\t"+re.getRe());
            }
            if(re.getP2().equals(select_record)){
                al.add(re.getP1().getName()+"\t\t"+re.getRe());
            }
        }
        if(al.size()==0) al.add("this person do not have any relations with other persons");
        ObservableList<String> obv=FXCollections.observableArrayList(al);
        DeleteRLV.setItems(obv);
        DeleteMsgpane.setDisable(false);
        DeleteMsgpane.setVisible(true);
    }
    
    /**
     * when the user click the yes button in delete person session, this method will check whether this
     * person has the child, if he has, the delete failed and the error message will display.
     */
    public void DeleteYButton() {
        boolean judge = false;
        selectEMessage.setStyle("-fx-text-inner-color: red;");
        clear(PerName1, PerImage1, PerStatus1, PerGender1, PerAge1, PerState1);
        do {
            try {
                delete_person(select_record);
                judge = true;
            } catch (NoParentException e) {
                selectEMessage.setVisible(true);
                selectEMessage.setText("can not delete a person with child");
            }
        } while (!judge);
        clear(PerName, PerImage, PerStatus, PerGender, PerAge, PerState);
        selectPerson();
        Deletepane.setVisible(false);
        Deletepane.setDisable(true);
        DeleteMsgpane.setVisible(false);
        DeleteMsgpane.setDisable(true);
        selectEMessage.clear();
        selectEMessage.setVisible(false);
        select_record = null;
    }
    
    /**
     * this private method serve the method deleteYButton(), this method will throw NoParentException
     * @param p the selected person object
     * @throws NotToBeParentException the person who has just one parent will thorw this exception
     */
    private void delete_person(Person p) throws NoParentException {
        Iterator <Relationships> ita=relat.iterator();
        while (ita.hasNext()) {
            Relationships elem = ita.next();
            if(elem.getP1().equals(p) && elem.getRe().equals("parent")
                    || elem.getP2().equals(p) && elem.getRe().equals("parent")) {
                throw new NoParentException("delete parent failed, the child can not have one parent");
            } else if(elem.getP1().equals(p)|| elem.getP2().equals(p)){
                ita.remove();
            }
            }
        Iterator <Person> it=persons.iterator();
        while (it.hasNext()) {
            Person ele=it.next();
            if(ele.equals(p)){
                it.remove();
            }
        }
    }
    
    /**
     * when user click the no button in delete person session, the content will be cleared.
     */
    public void DeleteNButton() {
        Deletepane.setVisible(false);
        Deletepane.setDisable(true);
        DeleteMsgpane.setVisible(false);
        DeleteMsgpane.setDisable(true);
        selectEMessage.clear();
        selectEMessage.setVisible(false);
    }
    
    /**
     * when click the back button in select scene, then it will show the menu
     * scene, and clear the content and set the panes invisible and disable in
     * select scene.
     */
    public void SelectBackButton() {
        selectList.setItems(null);
        Deletepane.setVisible(false);
        Deletepane.setDisable(true);
        Inforpane.setVisible(false);
        Inforpane.setDisable(true);
        updatepane.setVisible(false);
        updatepane.setDisable(true);
        DeleteMsgpane.setVisible(false);
        DeleteMsgpane.setDisable(true);
        selectEMessage.clear();
        selectEMessage.setVisible(false);
        clear(selectN, selectI, selectS, selectG, selectSta, selectA);
        st.setScene(sc_menu);
        st.show();
    }// select one person function in select scene end

    // list all persons function in list scene start
    /**
     * when the user click the display button, the ListView in list scene will show all the person information.
     */
    public void showPerson() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(String.format("\t\t\t\t%-32s%-44s%-34s%-40s%-30d%-15s", p.getName(), p.getProfile_image(),
                    p.getStatus(), p.getGender(), p.getAge(), p.getState()));
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        livi.setItems(data);
        displayButton.setVisible(false);
        displayButton.setDisable(true);
    }
    
    /**
     * when click the back button in list Scene, then will show the menu scene,
     * and clear the content and set the button disable and invisible in list
     * scene
     */
    public void ListBackButton() {
        livi.setItems(null);
        displayButton.setVisible(true);
        displayButton.setDisable(false);
        st.setScene(sc_menu);
        st.show();
    }// list all persons function in list scene end
    
    // connect two persons function in connect scene start
    /**
     * display all the person when click select one person in connectPerson scene.
     */
    public void connectOnePerson() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        connect_one_lv.setItems(data);
    }
    
    /**
     * display all the person when click select another person in connectPerson scene.
     */
    public void connectAnoPerson() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        connect_two_lv.setItems(data);
    }
    
    /**
     * used for recording first Person has been selected in connect person
     */
    public void selectedConnect1LV() {
        connect1_record = select_person(connect_one_lv.getSelectionModel().getSelectedItem());
    }

    /**
     * used for recording second Person has been selected in connect person
     */
    public void selectedConnect2LV() {
        connect2_record = select_person(connect_two_lv.getSelectionModel().getSelectedItem());
    }

    /**
     * when click the friends button in MenuButton "connect", the function is connecting
     * two persons as friends relationships
     * every exception will show as error message on GUI.
     */
    public void ConnectFriendsButton() {
        try {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: black;");
            connect1_record.ToBeFriends(connect2_record);
            ConnectMessage.setText("Add friends relation sucessful");
        } catch (TooYoungException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("Can not make friend with the child whose age under 3 years old");
        } catch (NotToBeFriendsException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText(
                    "An adullt can not make friend with a child or two children can not make friends if their age gap larger than 3");
        } catch (ConflictRelationshipException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("A person can not add himself");
        }
    }
    
    /**
     * when click the couple button in MenuButton "connect", the function is connecting
     * two persons as couple relationships
     * every exception will show as error message on GUI.
     */
    public void ConnectCoupleButton() {
        try {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: black;");
            connect1_record.ToBeCouple(connect2_record);
            ConnectMessage.setText("Add couple relation sucessful");
        } catch (NotToBeCoupledException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("Can not make a couple that at least one member is not an adult");
        } catch (NoAvailableException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("At least one of adults is already connected with other adult as a couple!");
        } catch (ConflictRelationshipException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText(
                    "A person can not add himself or can not connect a parent and a his child as couple relationships");
        }
    }

    /**
     * when click the parent button in MenuButton "connect", the function is connecting
     * two persons as parent relationships
     * every exception will show as error message on GUI.
     */
    public void ConnectParentButton() {
        try {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: black;");
            connect1_record.ToBeParent(connect2_record);
            ConnectMessage.setText("Add parent relation sucessful");
        } catch (NotToBeParentException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("Can not add a child as a parent");
        } catch (ConflictRelationshipException e) {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setText("A person can not add himself or can not connect couple to parent relationships");
        }
    }

    /**
     * when click the colleague button in MenuButton "connect", the function is connecting
     * two persons as colleague relationships
     * every exception will show as error message on GUI.
     */
    public void ConnectColleagueButton() {
        try {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: black;");
            connect1_record.ToBeColleague(connect2_record);
            ConnectMessage.setText("Add colleague relation sucessful");
        } catch (NotToBeColleaguesException e) {
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setVisible(true);
            ConnectMessage.setText("Can not connect a child in a colleague relation");
        } catch (ConflictRelationshipException e) {
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setVisible(true);
            ConnectMessage.setText("A person can not add himself");
        }
    }

    /**
     * when click the classmate button in MenuButton "connect", the function is connecting
     * two persons as classmate relationships
     * every exception will show as error message on GUI.
     */
    public void ConnectClassmateButton() {
        try {
            ConnectMessage.setVisible(true);
            ConnectMessage.setStyle("-fx-text-inner-color: black;");
            connect1_record.ToBeClassmate(connect2_record);
            ConnectMessage.setText("Add classmate relation sucessful");
        } catch (ConflictRelationshipException e) {
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setVisible(true);
            ConnectMessage.setText("A person can not add himself");
        } catch (NotToBeClassmatesException e) {
            ConnectMessage.setStyle("-fx-text-inner-color: red;");
            ConnectMessage.setVisible(true);
            ConnectMessage.setText("Can not make a child whose age under 3 years old in a classmate relation");
        }
    }
    
    /**
     * when click the back button in connect person scene, then it will show the
     * menu scene, and clear the content and set the text field invisible in
     * connect person scene.
     */
    public void ConnectBackButton() {
        connect_one_lv.setItems(null);
        connect_two_lv.setItems(null);
        ConnectMessage.clear();
        ConnectMessage.setVisible(false);
        st.setScene(sc_menu);
        st.show();
    }// connect two persons function in connect scene end
    
    // find blood relationships in BloodRe scene start
    /**
     * display all the person when click please select a person in BloodRe scene.
     */
    public void selectBLPButton() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        FBRlv.setItems(data);
    }
    
    /**
     * display the corresponding blood relation for the selected item in find out blood relation scene.
     */
    public void selectedBRlv() {
        int n = 0;
        TreeSet<String> te = new TreeSet<String>();
        BRListView.setItems(null);
        bl_select_record = select_person(FBRlv.getSelectionModel().getSelectedItem());
        for (Relationships r : relat) {
            if (r.getP1().equals(bl_select_record) && r.getRe().equals("parent")) {
                te.add(r.getP2().getName());
                n++;
            }
            if (r.getP2().equals(bl_select_record) && r.getRe().equals("parent")) {
                te.add(r.getP1().getName());
                n++;
            }
        }
        if (n == 0) {
            te.add("No parent or child!");
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        BRListView.setItems(data);
    }
    
    /**
     * when click the back button in find blood relationships scene, then it
     * will show the menu scene, and clear the content in find blood
     * relationships scene.
     */
    public void BloodBackButton() {
        FBRlv.setItems(null);
        BRListView.setItems(null);
        st.setScene(sc_menu);
        st.show();
    }// find blood relationships in BloodRe scene end
    
    //find directly or indirectly relationship in FindCon scene start
    /**
     * display all the person information when click source person in FindCon scene.
     */
    public void FindConSource() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        Find_Con_Source.setItems(data);
    }

    /**
     * recording the object when the user click the item in source person list view.
     */
    public void FindConSList() {
        source_record = select_person(Find_Con_Source.getSelectionModel().getSelectedItem());
    }

    /**
     * display all the person information when click target person in FindCon scene.
     */
    public void FindConTarget() {
        TreeSet<String> te = new TreeSet<String>();
        for (Person p : persons) {
            te.add(p.getName());
        }
        ObservableList<String> data = FXCollections.observableArrayList(te);
        Find_Con_Target.setItems(data);
    }

    /**
     * recording the object when the user click the item in target person list view.
     */
    public void FindConTList() {
        target_record = select_person(Find_Con_Target.getSelectionModel().getSelectedItem());
    }

    /**
     * when the user click the find out relation button, the directly relationship will show, 
     * if they are not directly connecting, the message "this two persons do not directly connect"
     * will show and the find out connection chain will light up.
     */
    public void FindOutDir() {
        int n = 0;
        for (Relationships re : relat) {
            if (source_record.equals(re.getP1()) && target_record.equals(re.getP2())
                    || source_record.equals(re.getP2()) && target_record.equals(re.getP1())) {
                Dir_Relat_TF.setText(re.getRe());
                n++;
            }
        }
        if (n == 0) {
            Dir_Relat_TF.setStyle("-fx-text-inner-color: blue;");
            Dir_Relat_TF.setText("This two persons do not directly connect");
            ConChainButton.setDisable(false);
            ConChainVBox.setDisable(false);
        }
    }
    
    /**
     * when click the back button in find connection scene, then it will show the menu scene, 
     * and clear the content and set some nodes to disable or invisible in find connection scene.
     */
    public void FindConBackButton() {
        total = 0;
        EndList.clear();
        StartList.clear();
        ResultList.clear();
        judge = false;
        Find_Con_Source.setItems(null);
        Find_Con_Target.setItems(null);
        Dir_Relat_TF.clear();
        Con_Chain_TF.setItems(null);
        Find_Con_Message.clear();
        Find_Con_Message.setVisible(false);
        ConChainButton.setDisable(true);
        ConChainVBox.setDisable(true);
        st.setScene(sc_menu);
        st.show();
    }

    /**
     * this method is used for finding out the shortest chain
     * using variable "total" count all of the relationship which is friends.
     * and divide it into two ArrayList.
     * use these two ArrayList, the EndList record the relationship has been traversal,
     * and the StartList record the relationship will be traversal.
     * HashSet Temp is used for storing the relationships has been traversal in every
     * iterate. Every iterate, the EndList will add all the elements in Temp, and the
     * StartList remove add all the elements in Temp until match the target or the 
     * EndList size equals the total size
     */
    public void FindOutChain() {
        int n = 1;
        total = 0;
        EndList.clear();
        StartList.clear();
        ResultList.clear();
        judge = false;
        HashSet<Relationships> Temp = new HashSet<>();
        SetStartAndEndList();
        do {
            for (int i = 0; i < EndList.size(); i++) {
                for (int j = 0; j < StartList.size(); j++) {
                    if ((EndList.get(i).getP2()).equals(StartList.get(j).getP1())
                            || (EndList.get(i).getP2()).equals(StartList.get(j).getP2())
                            || (EndList.get(i).getP1()).equals(StartList.get(j).getP1())
                            || (EndList.get(i).getP1()).equals(StartList.get(j).getP2())) {
                        Temp.add(StartList.get(j));
                    }
                }
            }
            for (Relationships elem : Temp) {
                if (elem.getP1().equals(target_record) || elem.getP2().equals(target_record))
                    judge = true;
            }
            Iterator<Relationships> it = Temp.iterator();
            while (it.hasNext()) {
                Relationships elem = it.next();
                if (!(elem.getP1().equals(target_record)) && !(elem.getP2().equals(target_record)) && judge == true)
                    it.remove();
            }
            EndList.addAll(Temp);
            StartList.removeAll(Temp);
            Temp.clear();
            ++n;
            if (judge == true)
                break;
        } while (EndList.size() < total);
        SetChainText(target_record);
        ResultList.remove(target_record.getName());
        Collections.reverse(ResultList);
        Find_Con_Message.setVisible(true);
        Find_Con_Message.setStyle("-fx-text-inner-color: blue;");
        if (ResultList.contains("Not connected")) {
            Find_Con_Message.setText("There is no connect with these two persons");
        } else {
            Find_Con_Message.setText("The shortest friends chain length is " + n);
        }
        ObservableList<String> data = FXCollections.observableArrayList(ResultList);
        Con_Chain_TF.setItems(data);
    }

    /**
     * this method serve FindOutChain() method, this method will mark the shortest friends chain which
     * is used in FindOutChain() method and recording the result in ArrayList ResultList.
     */
    private void SetStartAndEndList() {
        for (Relationships re : relat) {
            if (re.getRe().equals("friends")) {
                if (re.getP1().equals(source_record) || re.getP2().equals(source_record)) {
                    EndList.add(re);
                    total++;
                } else {
                    StartList.add(re);
                    total++;
                }
            }
        }
    }

    private void SetChainText(Person src) {
        boolean flag = false;
        ArrayList<Relationships> sub_EndList = new ArrayList<>();
        ArrayList<Relationships> sub_StartList = new ArrayList<>();
        ArrayList<Relationships> temp = new ArrayList<>();
        ArrayList<String> temp_resulet = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        if (judge == true) {
            for (Relationships re : EndList) {
                if (re.getP1().equals(src)) {
                    sub_EndList.add(re);
                } else if (re.getP2().equals(src)) {
                    sub_EndList.add(re);
                } else {
                    sub_StartList.add(re);
                }
            }
            do {
                for (Relationships end_re : sub_EndList) {
                    for (Relationships start_re : sub_StartList) {
                        if (end_re.getP1().equals(start_re.getP1()) || end_re.getP2().equals(start_re.getP1())) {
                            temp.add(start_re);
                            temp_resulet.add(start_re.getP1().getName());
                        }
                        if (end_re.getP1().equals(start_re.getP2()) || end_re.getP2().equals(start_re.getP2())) {
                            temp.add(start_re);
                            temp_resulet.add(start_re.getP2().getName());
                        }
                    }
                }
                for (String name : temp_resulet) {
                    sb.append(name + ",");
                }
                ResultList.add(sb.toString());
                sb.delete(0, sb.length());
                temp_resulet.clear();
                sub_EndList.addAll(temp);
                sub_StartList.removeAll(temp);
                temp.clear();
                for (Relationships relation : sub_EndList) {
                    if (relation.getP1().equals(source_record) || relation.getP2().equals(source_record)) {
                        flag = true;
                    }
                }
                if (flag == true)
                    break;
            } while (sub_EndList.size() <= EndList.size());
        } else {
            ResultList.add("Not connected");
        }
    }//find directly or indirectly relationship in FindCon scene end

    // exit function start
    /**
     * this method used for exist the application and close the resources.
     * @throws IOException
     */
    public void Exit() throws IOException {
        try {
            if (rs != null)
                rs.close();
            if (prsm != null)
                prsm.close();
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        write_to_file();
        System.exit(0);

    }// exit function end
  
    // main menu start
    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for adding a person. when click
     * the add a person in the menu, the add person scene will show.
     * 
     * @param e
     *            the trigger of the actionEvent
     */
    public void AddPersonButton(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_add);
        st.show();
    }
    
    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for selecting one person. when
     * click the select one person in the menu, the select person scene will
     * show.
     * 
     * @param e
     */
    public void SelectPersonButton(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_select);
        st.show();
    }

    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for listing all the persons
     * information. when click the list all persons in the menu, the list
     * persons scene will show.
     * 
     * @param e
     *            the trigger of the actionEvent
     */
    public void ListPersonInfo(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_list);
        st.show();
    }

    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for finding out blood relations.
     * when click the find out blood relationship in the menu, the find out
     * blood relationship scene will show.
     * 
     * @param e
     *            the trigger of the actionEvent
     */
    public void FindBlRe(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_blood_re);
        st.show();
    }

    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for finding out connection. when
     * click the find out connection in the menu, the find connection scene will
     * show.
     * 
     * @param e
     *            the trigger of the actionEvent
     */
    public void FindCon(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_find_con);
        st.show();
    }

    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for connecting two persons. when
     * click the connect two button in the menu, the connect person scene will
     * show.
     * 
     * @param e
     *            the trigger of the actionEvent
     */
    public void ConectTwoPerson(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_Connect_person);
        st.show();
    }
    
    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for opening the file scene. when
     * click the file button in the menu, the file scene will show.
     * @param e the trigger of the actionEvent
     */
    public void FileButton(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_file);
        st.show();
    }

    /**
     * the first statement is aiming to find out the event trigger Node, and
     * then find out the event trigger stage, recording the stage, use this
     * stage to generate a new scene which used for opening the file scene. when
     * click the file button in the menu, the file scene will show.
     * @param e the trigger of the actionEvent
     */
    public void DBButton(ActionEvent e) {
        st = (Stage) ((Node) e.getSource()).getScene().getWindow();
        st.setScene(sc_DB);
        st.show();
    }
    
    /**
     * when user click the guide button, a popup widow will show the guide
     * information. setResizable method set whether the size of window can be
     * change. initModality method set the application modal, when the guide
     * window appear, the user can not operate the original widow, which more
     * like an application.
     */
    public void GuideButton() {
        sub_st = new Stage();
        sub_st.setWidth(800);
        sub_st.setHeight(600);
        sub_st.setResizable(false);
        sub_st.setScene(sc_guide);
        sub_st.initModality(Modality.APPLICATION_MODAL);
        sub_st.show();
    }
    // main menu end

    /**
     * override the method from interface WriteAndReadTextFile interface
     */
    @Override
    public void write_to_file() throws IOException {
        File file1 = new File("src/TextFile/people.txt");
        File file2 = new File("src/TextFile/relations.txt");
        PrintWriter pt1 = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
        PrintWriter pt2 = new PrintWriter(new BufferedWriter(new FileWriter(file2)));
        for (Person ps : persons) {
            pt1.println(ps.getName() + "\t" + ps.getProfile_image() + "\t" + ps.getStatus() + "\t" + ps.getGender()
                    + "\t" + ps.getAge() + "\t" + ps.getState());
        }
        for (Relationships relation : relat) {
            if (relation.getP1().getName().compareTo(relation.getP2().getName()) < 0) {
                pt2.println(relation.getP1().getName() + "\t" + relation.getP2().getName() + "\t" + relation.getRe());
            } else {
                pt2.println(relation.getP2().getName() + "\t" + relation.getP1().getName() + "\t" + relation.getRe());
            }
        }
        pt1.close();
        pt2.close();
    }

    /**
     * use this method to set the different scene.
     * 
     * @throws IOException
     */
    private void setScene() throws IOException {
        Parent pt1 = FXMLLoader.load(getClass().getResource("MININET.fxml"));
        sc_menu = new Scene(pt1, 1000, 800);
        Parent pt2 = FXMLLoader.load(getClass().getResource("Add.fxml"));
        sc_add = new Scene(pt2, 1000, 800);
        Parent pt3 = FXMLLoader.load(getClass().getResource("Guide.fxml"));
        sc_guide = new Scene(pt3, 800, 600);
        Parent pt4 = FXMLLoader.load(getClass().getResource("Select.fxml"));
        sc_select = new Scene(pt4, 1000, 800);
        Parent pt5 = FXMLLoader.load(getClass().getResource("List.fxml"));
        sc_list = new Scene(pt5, 1000, 800);
        Parent pt6 = FXMLLoader.load(getClass().getResource("add_parent.fxml"));
        sc_add_parent = new Scene(pt6, 1000, 800);
        Parent pt7 = FXMLLoader.load(getClass().getResource("BloodRe.fxml"));
        sc_blood_re = new Scene(pt7, 1000, 800);
        Parent pt8 = FXMLLoader.load(getClass().getResource("FindCon.fxml"));
        sc_find_con = new Scene(pt8, 1000, 800);
        Parent pt9 = FXMLLoader.load(getClass().getResource("ConnectPerson.fxml"));
        sc_Connect_person = new Scene(pt9, 1000, 800);
        Parent pt10 = FXMLLoader.load(getClass().getResource("File.fxml"));
        sc_file = new Scene(pt10, 1000, 800);
        Parent pt11 = FXMLLoader.load(getClass().getResource("DB.fxml"));
        sc_DB = new Scene(pt11, 1000, 800);

    }

    /**
     * set the guide information
     */
    public void ShowGuide() {
        ArrayList<String> al = new ArrayList<>();
        al.add("File button: click then go into file scene, when user can upload the information to the text file whenever "
                + "they want, and after click this button, the newest version will upload to the file. View the personal "
                + "information from file will show the person information read from the text file, as well as the view relation"
                + "ships from file button.");
        al.add("");
        al.add("DataBase button: click then go into database scene, when the user click connect to database button, the appicatino "
                + "will attempt to connect database, if fail, the error message will display, the create table button used for create"
                + " two table, if they are already exist, the user do not need to click this button, if click, this operation will drop"
                + " them, then recreate it. Upload to database button is used for upload the newest data to the database. Read personal "
                + "information from database, the data store in the database will display, as well as the read relationships from database.");
        al.add("");
        al.add("In add a person scene(when type the add a person button): input the information of person, "
                + "if the person is not an adult the add parent button will show, then click it, enter the"
                + " add parent scene");
        al.add("");
        al.add("In parent scene(when type the add parent button): input the information of two parent, if "
                + "the information is invalid, the correspoding error message will show");
        al.add("");
        al.add("In Select one person scene(when type the select one person button): type the show person's name"
                + " button, the information of person will show and choose a person, then click please select "
                + "operation, then will show three menuItem button which is show profile, update profile and "
                + "delete person, choose one the correspodng information will show on the right side, when the user"
                + " type the delete button, the confirmation information will show to ensure the user really want to"
                + " delete this person.");
        al.add("");
        al.add("In List all persons scene(when type the list all persons button): click the display button to display "
                + "the information of every person has been created.");
        al.add("");
        al.add("In connect two persons scene(when type the connect two persons button): click select one person button "
                + "and click select another person button will show the list of the person name which is already exist, "
                + "after selecting two person, click the connect button, it will show a list of five relationships, choose"
                + " one then the operation message will show below, if failed, it will show the error message below.");
        al.add("");
        al.add("In find out blood relationship scene(when type the find out blood relationship button): click the please "
                + "select a person button, it will show every person's name, then when the user choose one of them, it "
                + "will show his blood relation");
        al.add("");
        al.add("In find out connection scene(when type the find out connection button): click the source person and target "
                + "person button, it will show the list of persons names, select two persons then click Find out relation "
                + "button, if they are directly connect, the information will show under direct relationship, if not, the "
                + "find out connection chain button will light up, then click it, it will show the shortest connection chain."
                + " e.g. A is B friend, B is C friend, C is D friend, when user search the chain between A and D, the result"
                + " will show like this: \"B,\", \"C,\", and the shortest friends chain is 3");
        ObservableList<String> ovb = FXCollections.observableArrayList(al);
        GuideListView.setItems(ovb);
    }
    
    /**
     * implement the abstract method from WriteAndReadTextFile interface the
     * function is storing the data from the file to the ArrayList.
     * 
     * @param file
     *            the file the user want to read.
     * @return return the ArrayList which store the content read from text file
     */
    @Override
    public ArrayList<String> ReadFromFile(File file) throws IOException {
        ArrayList<String> ar = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bf.readLine()) != null) {
            ar.add(line);
        }
        bf.close();
        return ar;
        
    }
    
    /**
     * the method need to be override when the class extends Application
     */
    @Override
    public void start(Stage primaryStage) {

        try {
            setScene();
            primaryStage.setTitle("MiniNet 2.0");
            primaryStage.setWidth(1000);
            primaryStage.setHeight(800);
            primaryStage.setScene(sc_menu);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // main method
    public static void main(String[] args) {

        launch(args);

    }
}
