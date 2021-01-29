package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.data.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    EditText edit_username;
    EditText edit_email;
    EditText edit_phone;
    EditText edit_pass;
    Button btn_sign;
    UserLoginTask mAuthTask;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //String MobilePattern = "[0-9]{10}";

    private static final String REGISTER_URL = "https://atharvaayurdhama.in/UserRegistration/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edit_username = (EditText)findViewById(R.id.fullname_reg);
        edit_email = (EditText)findViewById(R.id.email_reg);
        edit_phone = (EditText)findViewById(R.id.phone_reg);
        edit_pass = (EditText)findViewById(R.id.password_reg);
        btn_sign = (Button) findViewById(R.id.btn_signup);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username=edit_username.getText().toString().trim().toLowerCase();
        String email=edit_email.getText().toString().trim().toLowerCase();
        String phone_number = edit_phone.getText().toString().trim().toLowerCase();
        String password=edit_pass.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            edit_username.setError("Name Required.");
            return;
        }

        if(TextUtils.isEmpty(email)){
            edit_email.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(phone_number)) {
            edit_phone.setError("Phone Required");
            return;
        }

        if(TextUtils.isEmpty(password)){
            edit_pass.setError("Password Required");
            return;
        }

        if(!email.matches(emailPattern) && email.length() > 0)
        {
            edit_email.setError("Email Format Should be <abc@gmail.com>.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            edit_pass.setError("Password is required.");
            return;
        }

        if(phone_number.length() < 10 && phone_number.length() > 10) {
            edit_phone.setError("Please enter valid 10 digit phone number.");
            return;
        }

        if(password.length() < 8){
            edit_pass.setError("Password Must be >= 8 Characters.");
            return;
        }

        if(!password.matches("^(?=.*[_.()$&@]).*$")){
            edit_pass.setError("Password Must contain atleast 1 Number, 1 Special Character and 1 Uppercase Letter.");
            return;
        }

        if(!password.matches("(.*[0-9].*)")){
            edit_pass.setError("Password Must contain atleast 1 Number, 1 Special Character and 1 Uppercase Letter.");
            return;
        }

        if(!password.matches("(.*[A-Z].*)")){
            //hasUpper=true;
            edit_pass.setError("Password Must contain atleast 1 Number, 1 Special Character and 1 Uppercase Letter.");
            return;
        }


       // register(username,email,password);
        mAuthTask = new UserLoginTask(username,email,password);
        mAuthTask.execute((Void) null);

    }
    /**
     * Represents an asynchronous login/registration task used to Register
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final String mEmail;
        private final String mPwd;

        String jsonStr;

        UserLoginTask(String username,String email,String password) {
            mName = username;
            mEmail= email;
            mPwd=password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            HttpHandler sh = new HttpHandler();
            String url = API.REG_FULL_URL;

            //Creating a Hashmap for storing parameters for POST Method.
            HashMap<String, String> postparam = new HashMap<>();

            // adding each child node to HashMap key => value\
            postparam.put("username", mName);
            postparam.put("email", mEmail);
            postparam.put("password", mPwd);

            /*if(TextUtils.isEmpty(edit_email)){
                mEmail.setError("Email is Required.");
                return;
            }*/


            for (Map.Entry<String,String> entry : postparam.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Log.e(key,value);
            }

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCallPost(url,postparam);
            Log.e("Debugg(LoginAct)", "Response from url: " + jsonStr); //The Output of First Page

            //JSON Parsing
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                String resp="";
                try {
                    jsonObj = new JSONObject(jsonStr);
                    //Log.e("doInBackgroundl", jsonObj.getString("status"));
                    resp=jsonObj.getString("success");

                } catch (final JSONException e) {
                    Log.e("Error", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
                if(resp.equals("1"))
                {
                    //If Status Returned true then registration is successful
                    return true;
                }

            } else {
                Log.e("Error", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();

                    }
                });
            }

            //If Status returned false then registration error
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Toast.makeText(getApplicationContext(),
                        "Registration Successful",
                        Toast.LENGTH_LONG)
                        .show();
                startActivity(new Intent(getApplicationContext(),Login.class));
                //if sucessful login then go to main activity
                /*Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                intent.putExtra("JSONStr", jsonStr);
                startActivity(intent);*/
            } else {
                Toast.makeText(getApplicationContext(),
                        "Registration Unsuccessful",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}
