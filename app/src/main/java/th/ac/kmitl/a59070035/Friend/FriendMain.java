package th.ac.kmitl.a59070035.Friend;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import th.ac.kmitl.a59070035.Home;
import th.ac.kmitl.a59070035.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendMain extends Fragment {



    public FriendMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        backBtn();
    }

    public void backBtn(){
        Button btn = getView().findViewById(R.id.friend_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new Home())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
