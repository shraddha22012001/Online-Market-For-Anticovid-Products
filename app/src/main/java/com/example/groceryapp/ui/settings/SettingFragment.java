package com.example.groceryapp.ui.settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.groceryapp.ForgetPasswordActivity;
import com.example.groceryapp.Loginactivity;
import com.example.groceryapp.Prevalent.Prevalent;
import com.example.groceryapp.R;
import com.example.groceryapp.ResetPasswordActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends Fragment {

    private EditText fullNameChangeTextBtn,userPhoneEditText,addressEditText;
    private TextView saveTextBtn;
    private Button SecurityQuestionBtn;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        fullNameChangeTextBtn=(EditText)view.findViewById(R.id.setting_full_name);
        userPhoneEditText=(EditText)view.findViewById(R.id.setting_phone_no);
        addressEditText=(EditText)view.findViewById(R.id.setting_address);
        saveTextBtn=(TextView) view.findViewById(R.id.update_setting_btn);
        SecurityQuestionBtn=(Button) view.findViewById(R.id.security_questions_btn);
        SecurityQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ResetPasswordActivity.class);
                intent.putExtra("check","settings");
                startActivity(intent);
            }
        });


saveTextBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            userInfosaved();


    }

});
/*
profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        checker="clicked";
              CropImage.activity(imageuri)
                .setAspectRatio(1,1).start(getActivity());

    }
});*/
        return view;

    }
/*
   public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageuri=result.getUri();
            profileImageView.setImageURI(imageuri);
        }
        else{
            Toast.makeText(getActivity(),"TRY again",Toast.LENGTH_SHORT).show();
        }

    }*/
   public void userInfosaved(){
  if(TextUtils.isEmpty(fullNameChangeTextBtn.getText().toString())){
    Toast.makeText(getActivity(),"name is mendatory",Toast.LENGTH_SHORT).show();
}
   else if (TextUtils.isEmpty(userPhoneEditText.getText().toString())) {
     Toast.makeText(getActivity(),"phone is mendatory",Toast.LENGTH_SHORT).show();
}
    else if (TextUtils.isEmpty(addressEditText.getText().toString())) {
    Toast.makeText(getActivity(),"address is mendatory",Toast.LENGTH_SHORT).show();
}
   else {
      updateOnlyuserinfo();
      }
    }
/*
    public void uploadImage() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("update profile");
        progressDialog.setMessage("Please wait while we are updating ur account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if(imageuri!=null)
        {
            final StorageReference fileRef=storageProfilePictureRef.child(Prevalent.CurrentOnlineUser.getPhone()+".jpg");

            uploadTask=fileRef.putFile(imageuri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                       Uri downloadUrl=  task.getResult();
                        myurl=downloadUrl.toString();
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");

                        HashMap<String,Object> userMap=new HashMap<>();
                        userMap.put("name",fullNameChangeTextBtn.getText().toString());
                        userMap.put("address",addressEditText.getText().toString());
                        userMap.put("phoneorder",userPhoneEditText.getText().toString());
                        userMap.put("image",myurl);
                        progressDialog.dismiss();

                        ref.child(Prevalent.CurrentOnlineUser.getPhone()).updateChildren(userMap);



                        Toast.makeText(getActivity(),"Profilr information updated sucessfully",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
            {
            Toast.makeText(getActivity(),"image is not selected",Toast.LENGTH_SHORT).show();
           }

    }*/

    public void updateOnlyuserinfo(){

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String,Object> userMap=new HashMap<>();
        userMap.put("name",fullNameChangeTextBtn.getText().toString());
        userMap.put("address",addressEditText.getText().toString());
        userMap.put("phoneorder",userPhoneEditText.getText().toString());


        ref.child(Prevalent.CurrentOnlineUser.getPhone()).updateChildren(userMap);

        Toast.makeText(getActivity(),"Profile information updated sucessfully",Toast.LENGTH_SHORT).show();

}

}
