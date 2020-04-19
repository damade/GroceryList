package com.example.android.grocerylist.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.grocerylist.R;

public class DataRequestFormClass extends Dialog implements View.OnClickListener {

    public Activity mActivity;
    public Dialog d;
    public Button yes, no;
    public EditText name;
    public EditText email;
    public  EditText phonenumber;

    public DataRequestFormClass(Activity activity) {
        super(activity);
        mActivity = activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.data_request_form);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        name = findViewById(R.id.question_text_name);
        email = findViewById(R.id.question_text_email);
        phonenumber = findViewById(R.id.question_phone_number);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        // TODO Auto-generated constructor stub
        this.mActivity = activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                Intent intent = new Intent(mActivity,GroceryListActivity.class);
                String myName = name.getText().toString();
                String myEmail = email.getText().toString();
                String myPhoneNumber = phonenumber.getText().toString();

                intent.putExtra(GroceryListActivity.ORIGINAL_CONTACT_NAME, myName);
                intent.putExtra(GroceryListActivity.ORIGINAL_CONTACT_EMAIL,myEmail);
                intent.putExtra(GroceryListActivity.ORIGINAL_CONTACT_PHONENUMBER, myPhoneNumber);
                mActivity.startActivity(intent);
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}