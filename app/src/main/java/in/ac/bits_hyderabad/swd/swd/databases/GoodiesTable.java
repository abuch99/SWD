package in.ac.bits_hyderabad.swd.swd.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.KeyboardShortcutInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class GoodiesTable {

    public static String KEY_ID ="id";
    public static String KEY_NAME ="name";
    public static String KEY_HOST = "host";
    public static String KEY_IMAGE = "image";
    public static String KEY_SIZE_CHART ="size_chart_img";
    public static String KEY_Q_XS ="xs";
    public static String KEY_Q_S = "s";
    public static String KEY_Q_M = "m";
    public static String KEY_Q_L = "l";
    public static String KEY_Q_XL = "xl";
    public static String KEY_Q_XXL = "xxl";
    public static String KEY_Q_XXXL = "xxxl";
    public static String KEY_MIN_AMT_FUNDRAISER = "min_amt_fundraiser";
    public static String KEY_MAX_AMT_FUNDRAISER = "max_amt_fundraiser";
    public static String KEY_MAX_QNTY_LIMITEDGOODIES = "max_quantity";
    public static String KEY_PRICE = "price";
    public static String KEY_CLOSING_TIME = "closing_time";
    public static String KEY_CUSTOM = "custom";
    public static String KEY_HOSTER_NAME = "hoster_name";
    public static String KEY_HOSTER_MOBILE = "hoster_mobile";
    public static String KEY_HOSTER_ID = "hoster_id";
    public static String KEY_UPLOAD_DATE = "upload_date";


    private static final String DATABASE_NAME = "GOODIES";
    private static final String DATABASE_TABLE = "GOODIES_TABLE";
    private static final int DATABASE_VERSION =1;

    private dbhelperGoodies ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public GoodiesTable(Context c){
        ourContext=c;
        open();
    }
    public GoodiesTable open() {                                       //open Database
        ourHelper = new dbhelperGoodies(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close() {                                           //close Database
        ourHelper.close();
    }
    public void dropTable() {
        ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }
    public long createEntry(JSONObject object) throws JSONException {       //create entru=y in database using json object
        ContentValues cv = new ContentValues();
        cv.put(KEY_ID,object.getString("id"));
        cv.put(KEY_NAME,object.getString("name"));
        cv.put(KEY_HOST,object.getString("host"));
        cv.put(KEY_SIZE_CHART,object.getString("size_chart_img"));
        cv.put(KEY_Q_XS,object.getString("xs"));
        cv.put(KEY_Q_S,object.getString("s"));
        cv.put(KEY_Q_M,object.getString("m"));
        cv.put(KEY_Q_L,object.getString("l"));
        cv.put(KEY_Q_XL,object.getString("xl"));
        cv.put(KEY_Q_XXL,object.getString("xxl"));
        cv.put(KEY_Q_XXXL,object.getString("xxxl"));
        cv.put(KEY_MIN_AMT_FUNDRAISER,object.getString("min_amt_fundraiser"));
        cv.put(KEY_MAX_AMT_FUNDRAISER,object.getString("max_amt_fundraiser"));
        cv.put(KEY_MAX_QNTY_LIMITEDGOODIES,object.getString("max_quantity"));
        cv.put(KEY_PRICE,object.getString("price"));
        cv.put(KEY_CLOSING_TIME,object.getString("closing_time"));
        cv.put(KEY_CUSTOM,object.getString("custom"));
        cv.put(KEY_HOSTER_NAME,object.getString("hoster_name"));
        cv.put(KEY_HOSTER_ID,object.getString("hoster_id"));
        cv.put(KEY_HOSTER_MOBILE,object.getString("hoster_mobile"));
        cv.put(KEY_UPLOAD_DATE,object.getString("upload_date"));

        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public JSONObject getJsonObject() throws JSONException {                //getting Json object from database
        JSONObject object = new JSONObject();
        Cursor c=ourDatabase.rawQuery("select * from "+ DATABASE_TABLE,null);
        if(c.moveToFirst()) {
            object.put("id",c.getString(c.getColumnIndex(KEY_ID)));
            object.put("name",c.getString(c.getColumnIndex(KEY_NAME)));
            object.put("host",c.getString(c.getColumnIndex(KEY_HOST)));
            object.put("size_chart_img",c.getString(c.getColumnIndex(KEY_SIZE_CHART)));
            object.put("xs",c.getString(c.getColumnIndex(KEY_Q_XS)));
            object.put("s",c.getString(c.getColumnIndex(KEY_Q_S)));
            object.put("m",c.getString(c.getColumnIndex(KEY_Q_M)));
            object.put("l",c.getString(c.getColumnIndex(KEY_Q_L)));
            object.put("xl",c.getString(c.getColumnIndex(KEY_Q_XL)));
            object.put("xxl",c.getString(c.getColumnIndex(KEY_Q_XXL)));
            object.put("xxxl",c.getString(c.getColumnIndex(KEY_Q_XXXL)));
            object.put("min_amt_fundraiser",c.getString(c.getColumnIndex(KEY_MIN_AMT_FUNDRAISER)));
            object.put("max_amt_fundraiser",c.getString(c.getColumnIndex(KEY_MAX_AMT_FUNDRAISER)));
            object.put("max_quantity",c.getString(c.getColumnIndex(KEY_MAX_QNTY_LIMITEDGOODIES)));
            object.put("price",c.getString(c.getColumnIndex(KEY_PRICE)));
            object.put("closing_time",c.getString(c.getColumnIndex(KEY_CLOSING_TIME)));
            object.put("custom",c.getString(c.getColumnIndex(KEY_CUSTOM)));
            object.put("hoster_name",c.getString(c.getColumnIndex(KEY_HOSTER_NAME)));
            object.put("hoster_id",c.getString(c.getColumnIndex(KEY_HOSTER_ID)));
            object.put("hoster_mobile",c.getString(c.getColumnIndex(KEY_HOSTER_MOBILE)));
            object.put("upload_date",c.getString(c.getColumnIndex(KEY_UPLOAD_DATE)));


        }
    return object;

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

    public void deleteAll() {                                                               //deleting whoLe database table

        ourDatabase.delete(DATABASE_TABLE,null,null);

    }
    private static class dbhelperGoodies extends SQLiteOpenHelper{

        public dbhelperGoodies(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ID +" TEXT PRIMARY KEY NOT NULL ," +
                    KEY_NAME +" TEXT NOT NULL ," +
                    KEY_HOST +  "TEXT NOT NULL," +
                    KEY_IMAGE + "TEXT," +
                    KEY_SIZE_CHART + "TEXT," +
                    KEY_Q_XS + "TEXT," +
                    KEY_Q_S + "TEXT," +
                    KEY_Q_M + "TEXT," +
                    KEY_Q_L + "TEXT," +
                    KEY_Q_XL + "TEXT," +
                    KEY_Q_XXL + "TEXT," +
                    KEY_Q_XXXL + "TEXT," +
                    KEY_MIN_AMT_FUNDRAISER + "TEXT," +
                    KEY_MAX_AMT_FUNDRAISER + "TEXT," +
                    KEY_MAX_QNTY_LIMITEDGOODIES + "TEXT," +
                    KEY_PRICE + "TEXT," +
                    KEY_CLOSING_TIME + "TEXT," +
                    KEY_CUSTOM + "TEXT," +
                    KEY_HOSTER_NAME + "TEXT," +
                    KEY_HOSTER_MOBILE + "TEXT," +
                    KEY_HOSTER_ID + "TEXT," +
                    KEY_UPLOAD_DATE + "TEXT);"
                    );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
