package th.ac.kmitl.a59070035;


import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    private EditText userId,pwd;
    private String SuserId,Spwd;

    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        loginBtn();
        regisBtn();
        if (!userExist().equals("Not Found") ){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new Home())
                    .addToBackStack(null)
                    .commit();
        }else{
            Log.d("System", "No Account Exist");
        }
    }

    public void loginBtn(){
        Button loginBtn = getView().findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = getView().findViewById(R.id.login_userId);
                pwd = getView().findViewById(R.id.login_pwd);
                SuserId = userId.getText().toString();
                Spwd = pwd.getText().toString();

                if (SuserId.isEmpty() || Spwd.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill out this form", Toast.LENGTH_SHORT).show();
                }else{
                    checkUserOnSql();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new Home())
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
    }


    public void checkUserOnSql(){

        String userId = null,pwd = null;
        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("My.DB", Context.MODE_PRIVATE, null);

        myDB.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(200), name VARCHAR(200), age VARCHAR(200), pwd VARCHAR(200))");

        final Cursor myCursor = myDB.rawQuery("select * from Account", null);

        while (myCursor.moveToNext()){
            userId = myCursor.getString(1);
            pwd = myCursor.getString(4);
            Log.d("System", "Check compare : " +  userId +" " + pwd);
        }


        if (userId.equals(SuserId) && pwd.equals(Spwd)){
                SharedPreferences sp = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                sp.edit().putString("userId", SuserId).apply();
                Log.d("System", "Condition isEmpty : " + sp.getString("userId", "0"));
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new Home())
                        .addToBackStack(null)
                        .commit();

        }else{
            Toast.makeText(getActivity(), "Invalid user or password", Toast.LENGTH_SHORT).show();
        }

    }

    public String userExist(){
        SharedPreferences sp = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
        String userExist = sp.getString("userId", "Not Found");
        Log.d("System", "have userId : " + userExist);
        return userExist;
    }

    public void regisBtn(){
        TextView regisBtn = getView().findViewById(R.id.login_regis);
        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new Register())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


}
