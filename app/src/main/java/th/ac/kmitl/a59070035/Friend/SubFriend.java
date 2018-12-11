package th.ac.kmitl.a59070035.Friend;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import th.ac.kmitl.a59070035.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubFriend extends Fragment {


    public SubFriend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_friend, container, false);
    }

}
