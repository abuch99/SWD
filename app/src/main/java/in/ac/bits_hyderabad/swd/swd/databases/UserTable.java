package in.ac.bits_hyderabad.swd.swd.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTable {

    public static String KEY_UID             = "uid";
    public static String KEY_TAG             = "tag";
    public static String KEY_ERROR           = "error";
    public static String KEY_NAME            = "name";
    public static String KEY_ID              = "id";
    public static String KEY_BRANCH          = "branch";
    public static String KEY_ROOM            = "room";
    public static String KEY_GENDER          = "gender";
    public static String KEY_PHONE           = "phone";
    public static String KEY_EMAIL           = "email";
    public static String KEY_DOB             = "dob";
    public static String KEY_FATHER          = "father";
    public static String KEY_MOTHER          = "mother";
    public static String KEY_FMAIL           = "fmail";
    public static String KEY_FPHONE          = "fphone";
    public static String KEY_FOCCUP          = "foccup";
    public static String KEY_MMAIL           = "mmail";
    public static String KEY_MOCCUP          = "moccup";
    public static String KEY_HPHONE          = "hphone";
    public static String KEY_HOMEADD         = "homeadd";
    public static String KEY_CITY            = "city";
    public static String KEY_STATE           = "state";
    public static String KEY_LOCALADD        = "localadd";
    public static String KEY_GUARDIAN        = "guardian";
    public static String KEY_GPHONE          = "gphone";
    public static String KEY_NATION          = "nation";
    public static String KEY_BLOOD           = "blood";
    public static String KEY_BANK            = "bank";
    public static String KEY_ACNO            = "acno";
    public static String KEY_IFSC            = "ifsc";
    public static String KEY_PIMAGE          = "pimage";
    public static String KEY_TIME            = "time";
    public static String KEY_PASSWORD        = "password";

    private static final String DATABASE_NAME = "USERDATA";
    private static final String DATABASE_TABLE = "USERDATA_TABLE";
    private static final int DATABASE_VERSION = 1;



    private dbhelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;
    public UserTable(Context c) {                                   //Constructor
        ourContext = c;
        open();
    }
    public UserTable open() {                                       //open Database
        ourHelper = new dbhelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {                                           //close Database
        ourHelper.close();
    }

    public long createEntry(JSONObject object) throws JSONException {       //create entru=y in database using json object
        ContentValues cv = new ContentValues();
        cv.put(KEY_UID,object.getString("uid"));
        cv.put(KEY_TAG,object.getString("tag"));
        cv.put(KEY_ERROR,object.getString("error"));
        cv.put(KEY_NAME,object.getString("name"));
        cv.put(KEY_ID,object.getString("id"));
        cv.put(KEY_BRANCH,object.getString("branch"));
        cv.put(KEY_ROOM,object.getString("room"));
        cv.put(KEY_GENDER,object.getString("gender"));
        cv.put(KEY_PHONE,object.getString("phone"));
        cv.put(KEY_EMAIL,object.getString("email"));
        cv.put(KEY_DOB,object.getString("dob"));
        cv.put(KEY_FATHER,object.getString("father"));
        cv.put(KEY_MOTHER,object.getString("mother"));
        cv.put(KEY_FMAIL,object.getString("fmail"));
        cv.put(KEY_FPHONE,object.getString("fphone"));
        cv.put(KEY_FOCCUP,object.getString("foccup"));
        cv.put(KEY_MMAIL,object.getString("mmail"));
        cv.put(KEY_MOCCUP,object.getString("moccup"));
        cv.put(KEY_HPHONE,object.getString("hphone"));
        cv.put(KEY_HOMEADD,object.getString("homeadd"));
        cv.put(KEY_CITY,object.getString("city"));
        cv.put(KEY_STATE,object.getString("state"));
        cv.put(KEY_LOCALADD,object.getString("localadd"));
        cv.put(KEY_GUARDIAN,object.getString("guardian"));
        cv.put(KEY_GPHONE,object.getString("gphone"));
        cv.put(KEY_NATION,object.getString("nation"));
        cv.put(KEY_BLOOD,object.getString("blood"));
        cv.put(KEY_BANK,object.getString("bank"));
        cv.put(KEY_ACNO,object.getString("acno"));
        cv.put(KEY_IFSC,object.getString("ifsc"));
        cv.put(KEY_PIMAGE,object.getString("pimage"));
        cv.put(KEY_TIME,object.getString("time"));
        cv.put(KEY_PASSWORD,object.getString("password"));

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }
    public JSONObject getJsonObject() throws JSONException {                //getting Json object from database
        JSONObject object=new JSONObject();
//      String[] columns = new String[]{KEY_UID,KEY_UNAME,KEY_CATEGORY,KEY_SUBCATEGORY,KEY_TITLE,KEY_SUBTITLE,KEY_LINK,KEY_BODY,KEY_IMAGE};
        Cursor c=ourDatabase.rawQuery("select * from "+ DATABASE_TABLE,null);
        if(c.moveToFirst()) {
            object.put("uid",c.getString(c.getColumnIndex(KEY_UID)) );
            object.put("tag",c.getString(c.getColumnIndex(KEY_TAG)) );
            object.put("error",c.getString(c.getColumnIndex(KEY_ERROR)) );
            object.put("name",c.getString(c.getColumnIndex(KEY_NAME)) );
            object.put("id",c.getString(c.getColumnIndex(KEY_ID)) );
            object.put("branch",c.getString(c.getColumnIndex(KEY_BRANCH)) );
            object.put("room",c.getString(c.getColumnIndex(KEY_ROOM)) );
            object.put("gender",c.getString(c.getColumnIndex(KEY_GENDER)) );
            object.put("phone",c.getString(c.getColumnIndex(KEY_PHONE)) );
            object.put("email",c.getString(c.getColumnIndex(KEY_EMAIL)) );
            object.put("dob",c.getString(c.getColumnIndex(KEY_DOB)) );
            object.put("father",c.getString(c.getColumnIndex(KEY_FATHER)) );
            object.put("mother",c.getString(c.getColumnIndex(KEY_MOTHER)) );
            object.put("fmail",c.getString(c.getColumnIndex(KEY_FMAIL)) );
            object.put("fphone",c.getString(c.getColumnIndex(KEY_FPHONE)) );
            object.put("foccup",c.getString(c.getColumnIndex(KEY_FOCCUP)) );
            object.put("mmail",c.getString(c.getColumnIndex(KEY_MMAIL)) );
            object.put("moccup",c.getString(c.getColumnIndex(KEY_MOCCUP)) );
            object.put("hphone",c.getString(c.getColumnIndex(KEY_HPHONE)) );
            object.put("homeadd",c.getString(c.getColumnIndex(KEY_HOMEADD)) );
            object.put("city",c.getString(c.getColumnIndex(KEY_CITY)) );
            object.put("state",c.getString(c.getColumnIndex(KEY_STATE)) );
            object.put("localadd",c.getString(c.getColumnIndex(KEY_LOCALADD)) );
            object.put("guardian",c.getString(c.getColumnIndex(KEY_GUARDIAN)) );
            object.put("gphone",c.getString(c.getColumnIndex(KEY_GPHONE)) );
            object.put("nation",c.getString(c.getColumnIndex(KEY_NATION)) );
            object.put("blood",c.getString(c.getColumnIndex(KEY_BLOOD)) );
            object.put("bank",c.getString(c.getColumnIndex(KEY_BANK)) );
            object.put("acno",c.getString(c.getColumnIndex(KEY_ACNO)) );
            object.put("ifsc",c.getString(c.getColumnIndex(KEY_IFSC)) );
            object.put("pimage",c.getString(c.getColumnIndex(KEY_PIMAGE)) );
            object.put("time",c.getString(c.getColumnIndex(KEY_TIME)) );

        }

        return object;

    }
    public void deleteAll() {                                                               //deleting whote database table

        ourDatabase.delete(DATABASE_TABLE,null,null);

    }



    public boolean hasData(){                                                               //checking for any current logins
        boolean has;
        Cursor c=ourDatabase.rawQuery("select * from "+ DATABASE_TABLE,null);
        if(c.getCount()>=1){
            has=true;
        }
        else
            has=false;
        return has;
    }

    public String getUID() {                                                                //login id of current user
        String uid="";
        Cursor c=ourDatabase.rawQuery("select * from "+DATABASE_TABLE,null);
        if(c.moveToFirst()){
            uid=c.getString(c.getColumnIndex(KEY_UID));
        }
        return  uid;
    }
    public String getPassword() {                                                              //password of current user
        String uid="";
        Cursor c=ourDatabase.rawQuery("select * from "+DATABASE_TABLE,null);
        if(c.moveToFirst()){
            uid=c.getString(c.getColumnIndex(KEY_PASSWORD));
        }
        return  uid;
    }
    public String getID() {                                                              //password of current user
        String id="";
        Cursor c=ourDatabase.rawQuery("select * from "+DATABASE_TABLE,null);
        if(c.moveToFirst()){
            id=c.getString(c.getColumnIndex(KEY_ID));
        }
        return  id;
    }
    public String getName(){
        String name="";
        Cursor c=ourDatabase.rawQuery("select * from "+DATABASE_TABLE,null);
        if(c.moveToFirst()){
            name=c.getString(c.getColumnIndex(KEY_NAME));
        }
        return name;
    }

    public void dropTable() {
        ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }



    private static class dbhelper extends SQLiteOpenHelper {        //database class

        public dbhelper(Context context) {

            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_UID + " TEXT PRIMARY KEY NOT NULL ," +
                    KEY_TAG + " TEXT NOT NULL," +
                    KEY_ERROR + " TEXT NOT NULL," +
                    KEY_NAME + " TEXT NOT NULL," +
                    KEY_ID + " TEXT NOT NULL," +
                    KEY_BRANCH + " TEXT NOT NULL," +
                    KEY_ROOM + " TEXT NOT NULL," +
                    KEY_GENDER + " TEXT NOT NULL," +
                    KEY_PHONE + " TEXT NOT NULL," +
                    KEY_EMAIL + " TEXT NOT NULL," +
                    KEY_DOB + " TEXT NOT NULL," +
                    KEY_FATHER + " TEXT," +
                    KEY_MOTHER + " TEXT," +
                    KEY_FMAIL + " TEXT," +
                    KEY_FPHONE + " TEXT," +
                    KEY_FOCCUP + " TEXT," +
                    KEY_MMAIL + " TEXT," +
                    KEY_MOCCUP + " TEXT," +
                    KEY_HPHONE + " TEXT," +
                    KEY_HOMEADD + " TEXT," +
                    KEY_CITY + " TEXT," +
                    KEY_STATE + " TEXT," +
                    KEY_LOCALADD+ " TEXT," +
                    KEY_GUARDIAN+ " TEXT," +
                    KEY_GPHONE+ " TEXT," +
                    KEY_NATION + " TEXT," +
                    KEY_BLOOD + " TEXT," +
                    KEY_BANK + " TEXT," +
                    KEY_ACNO + " TEXT," +
                    KEY_IFSC + " TEXT," +
                    KEY_PIMAGE + " TEXT," +
                    KEY_TIME + " TEXT," +
                    KEY_PASSWORD+" TEXT );"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

}
