package org.iptime.seonghunjo.lab_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Database 관련 객체들
    SQLiteDatabase db;
    String dbName = "idList.db"; // name of Database;
    String tableName = "idListTable"; // name of Table;
    int dbMode = Context.MODE_PRIVATE;

    // layout object
    EditText mEtName;
    Button mBtInsert;
    Button mBtRead;

    EditText mEtModify;
    Button mBtDelete;
    Button mBtUpdate;
    Button mBtSort;

    String modify;

    String order;
    boolean isASC = true;

    int selectedIndex;

    ListView mList;
    ArrayAdapter<String> baseAdapter;
    ArrayList<String> nameList;
    ArrayList<Integer> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // // Database 생성 및 열기
        db = openOrCreateDatabase(dbName,dbMode,null);
        // 테이블 생성
        createTable();

        mEtName = (EditText) findViewById(R.id.et_text);
        mBtInsert = (Button) findViewById(R.id.bt_insert);
        mBtRead = (Button) findViewById(R.id.bt_read);

        mEtModify = (EditText) findViewById(R.id.et_modify);
        mBtDelete = (Button) findViewById(R.id.bt_delete);
        mBtUpdate = (Button) findViewById(R.id.bt_update);
        mBtSort = (Button) findViewById(R.id.bt_sort);

        ListView mList = (ListView) findViewById(R.id.list_view);

        mBtInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                insertData(name);
            }
        });

        mBtRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameList.clear();
                idList.clear();

                selectAll();
                baseAdapter.notifyDataSetChanged();
            }
        });

        mBtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dbid = idList.get(selectedIndex);

                nameList.remove(selectedIndex);
                idList.remove(selectedIndex);

                removeData(dbid);
                baseAdapter.notifyDataSetChanged();
            }
        });

        mBtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dbid = idList.get(selectedIndex);

                modify = mEtModify.getText().toString();
                nameList.set(selectedIndex, modify);

                updateData(dbid, modify);
                baseAdapter.notifyDataSetChanged();
            }
        });

        mBtSort.setText("Click to order by DESC");
        mBtSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameList.clear();
                idList.clear();

                isASC = !isASC;
                if(isASC)
                    mBtSort.setText("Click to order by DESC");
                else
                    mBtSort.setText("Click to order by ASC");

                selectAll();

                baseAdapter.notifyDataSetChanged();
            }
        });

        // Create listview
        nameList = new ArrayList<String>();
        idList = new ArrayList<Integer>();

        baseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        mList.setAdapter(baseAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
            }
        });
    }

    // Table 생성
    public void createTable() {
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite","error: "+ e);
        }
    }

    // Table 삭제
    public void removeTable() {
        String sql = "drop table " + tableName;
        db.execSQL(sql);
    }

    // Data 추가
    public void insertData(String name) {
        String sql = "insert into " + tableName + " values(NULL, '" + name + "');";
        db.execSQL(sql);
    }

    // Data 업데이트
    public void updateData(int index, String name) {
        String sql = "update " + tableName + " set name = '" + name + "' where id = " + index + ";";
        db.execSQL(sql);
    }

    // Data 삭제
    public void removeData(int index) {
        String sql = "delete from " + tableName + " where id = " + index + ";";
        db.execSQL(sql);
    }

    // Data 읽기(꺼내오기)
    public void selectData(int index) {
        String sql = "select * from " + tableName + " where id = " + index + ";";
        Cursor result = db.rawQuery(sql, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            int id = result.getInt(0);
            String name = result.getString(1);
            //Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_SHORT).show();
            Log.d("lab_sqlite", "\"index= \" + id + \" name=\" + name ");
        }
        result.close();
    }

    // 모든 Data 읽기
    public void selectAll() {
        if(isASC)
            order = "ASC";
        else
            order = "DESC";

        String sql = "select * from " + tableName + " order by id " + order + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            int id = results.getInt(0);
            String name = results.getString(1);
            //Toast.makeText(this, "index= " + id + " name=" + name, Toast.LENGTH_SHORT).show();
            Log.d("lab_sqlite", "index= " + id + " name=" + name);

            nameList.add(name);
            idList.add(id);
            results.moveToNext();
        }
        results.close();
    }

}
