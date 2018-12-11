package th.ac.kmitl.a59070035;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    TextView welcome;
    String name = null;
    String userid = null;
    String age = null;
    String pwd = null;
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        SQLiteDatabase myDB = getActivity().openOrCreateDatabase("My.DB", Context.MODE_PRIVATE, null);

        myDB.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, userid VARCHAR(200), name VARCHAR(200), age VARCHAR(200), pwd VARCHAR(200))");

        final Cursor myCursor = myDB.rawQuery("select * from Account", null);

        while (myCursor.moveToNext()){
            name = myCursor.getString(2);
            userid = myCursor.getString(1);
            age = myCursor.getString(3);
            pwd = myCursor.getString(4);

        }
        myDB.close();


        profileBtn();
        signOutBtn();
        readFile();

        welcome = getView().findViewById(R.id.home_welcome);
        welcome.setText("Hello " + name);

    }

    public void readFile(){
        TextView show = getView().findViewById(R.id.home_txt);
        String message;
        try {
            FileInputStream inFile = getActivity().openFileInput("text");
            InputStreamReader inputStreamReader = new InputStreamReader(inFile);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message=bufferedReader.readLine()) != null){
                stringBuffer.append(message + "\n");
            }
            show.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profileBtn(){

        Button btn = getView().findViewById(R.id.home_profile);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("userid", userid);
                bundle.putString("name", name);
                bundle.putString("age", age);
                bundle.putString("pwd", pwd);

                Fragment fragment = new Profile();
                fragment.setArguments(bundle);


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_view, fragment).addToBackStack(null).commit();


            }
        });
    }

    public void signOutBtn(){
        Button btn = getView().findViewById(R.id.home_signout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                sp.edit().putString("userId", "Not Found").apply();
                Log.d("System", "Condition isEmpty : " + sp.getString("userId", "0"));
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new Login())
                        .commit();
            }
        });
    }
    
    


}
