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


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {

    private EditText userId,name,age,pwd;
    private String SuserId,Sname,Sage,Spwd;

    public Register() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        regisBtn();
    }

    public void regisBtn(){
        Button regisBtn = getView().findViewById(R.id.regis_btn);
        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = getView().findViewById(R.id.regis_userId);
                name = getView().findViewById(R.id.regis_name);
                age = getView().findViewById(R.id.regis_age);
                pwd = getView().findViewById(R.id.regis_pwd);

                SuserId = userId.getText().toString();
                Sname = name.getText().toString();
                Spwd = pwd.getText().toString();
                Sage = age.getText().toString();

                Log.d("System", "This is name : " + Sname);
                int iAge = Integer.valueOf(Sage);

                if ( (SuserId.length() >= 6 && SuserId.length() <= 12) && (Sname.contains(" ")) && ( iAge >= 10 && iAge <= 60) && (Spwd.length() > 6)){
                    Toast.makeText(getActivity(), "Register Complete", Toast.LENGTH_SHORT).show();
                    writeToSql();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new Login())
                            .addToBackStack(null)
                            .commit();
                }else{
                    Toast.makeText(getActivity(), "Please check your info again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeToSql(){

        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("My.DB", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(200), name VARCHAR(200), age VARCHAR(200), pwd VARCHAR(200))");
        ContentValues row = new ContentValues();
        row.put("userid", SuserId);
        row.put("name", Sname);
        row.put("age", Sage);
        row.put("pwd", Spwd);

        myDB.insert("Account", null, row);
        myDB.close();

    }



}
