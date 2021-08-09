package com.example.groceryapp.ui.Logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.groceryapp.Loginactivity;
import com.example.groceryapp.MainActivity;
import com.example.groceryapp.R;

import io.paperdb.Paper;

public class LogoutFragment extends Fragment {
    private Button LogoutButton;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        /*LogoutButton=(Button) view.findViewById(R.id.logout_btn);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {*/
                Paper.book().destroy();
                Intent intent=new Intent(getActivity(), Loginactivity.class);
                startActivity(intent);
      /*      }
        });*/
        return view;
    }
}
