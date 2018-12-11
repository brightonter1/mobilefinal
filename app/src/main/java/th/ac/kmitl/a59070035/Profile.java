package th.ac.kmitl.a59070035;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    EditText userId,name,age,pwd;
    String SuserId,Sname,Sage,Spwd;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchShow();
        saveBtn();
    }

    public void fetchShow(){
        Bundle bundle = this.getArguments();

        Log.d("System", (String) getArguments().get("userid") + getArguments().get("name") + getArguments().get("age") + getArguments().get("pwd"));
        userId = getView().findViewById(R.id.profile_userid);
        name = getView().findViewById(R.id.profile_name);
        age = getView().findViewById(R.id.profile_age);
        pwd = getView().findViewById(R.id.profile_pwd);

        userId.setText(String.valueOf(getArguments().get("userid")));
        name.setText(String.valueOf(getArguments().get("name")));
        age.setText(String.valueOf(getArguments().get("age")));
        pwd.setText(String.valueOf(getArguments().get("pwd")));
    }

    public void saveBtn(){
        Button btn = getView().findViewById(R.id.profile_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                userId = getView().findViewById(R.id.profile_userid);
//                name = getView().findViewById(R.id.profile_name);
//                age = getView().findViewById(R.id.profile_age);
//                pwd = getView().findViewById(R.id.profile_pwd);

                SuserId = userId.getText().toString();
                Sname = name.getText().toString();
                Sage = age.getText().toString();
                Spwd = pwd.getText().toString();


                int iAge = Integer.valueOf(Spwd);

                Log.d("System", SuserId.length()+ " " + Sname +" " + Sage+" " + Spwd.length());



                if ((SuserId.length() >= 6 && SuserId.length() <= 12) && (Sname.contains(" ")) && (iAge >= 10 && iAge <= 80 && (Spwd.length() > 6 ))){
                    UpdateToSql();
                    writeFile();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new Home())
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getActivity(), "Please check your info", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void writeFile(){
        String file = "text";
        EditText eText = getView().findViewById(R.id.profile_text);
        String  text = eText.getText().toString();
        try{
            FileOutputStream outFile = getActivity().openFileOutput(file, Context.MODE_PRIVATE);
            outFile.write(text.getBytes());
            outFile.close();
            Log.d("System", "Message saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIdCount(){
        String id = null;
        SQLiteDatabase myDB1 = getActivity().openOrCreateDatabase("My.DB", Context.MODE_PRIVATE, null);

        myDB1.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(200), name VARCHAR(200), age VARCHAR(200), pwd VARCHAR(200))");

        final Cursor myCursor = myDB1.rawQuery("select * from Account", null);

        while (myCursor.moveToNext()){
            id = myCursor.getString(0);
        }
        myDB1.close();
        return id;
    }

    public void UpdateToSql(){

        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("My.DB", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(200), name VARCHAR(200), age VARCHAR(200), pwd VARCHAR(200))");
        ContentValues row = new ContentValues();
        row.put("userid", SuserId);
        row.put("name", Sname);
        row.put("age", Sage);
        row.put("pwd", Spwd);

        myDB.update("Account",row, "id=" +Integer.parseInt(getIdCount()) ,null);
        myDB.close();

        Log.d("System", "update complete");
    }
}
