package innt.ffhs.ch.funday;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 * Registration Form for creating new Users
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText mMail;
    private EditText mPassword;
    private EditText mPhone;
    private EditText mName;
    private Button regButton;
    private FirebaseAuth mAuth;
    private AccountValidator validate = new AccountValidator();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //UI Elements
        mMail = (EditText) findViewById(R.id.regMail);
        mPassword = (EditText) findViewById(R.id.regPw);
        mPhone = (EditText) findViewById(R.id.regPhone);
        mName = (EditText) findViewById(R.id.regName);
        regButton = (Button) findViewById(R.id.regConfirm);

        mAuth = FirebaseAuth.getInstance();


        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attempRegistration();
            }
        });

    }

    public void attempRegistration() {
        String temail = mMail.getText().toString();
        String tpassword = mPassword.getText().toString();
        String tphone = mPhone.getText().toString();
        String tname = mName.getText().toString();

        boolean cancel = false;
        View focusView = null;
        //Do some basic validation tests before invoke Registration
        if (!TextUtils.isEmpty(tpassword) && !validate.isEmailValid(temail)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(temail)) {
            mMail.setError(getString(R.string.error_field_required));
            focusView = mMail;
            cancel = true;
        } else if (!validate.isEmailValid(temail)) {
            mMail.setError(getString(R.string.error_invalid_email));
            focusView = mMail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            doRegistration(temail, tpassword, tphone, tname);

        }
    }

    private void doRegistration(final String mail, String pw, final String phone, final String name) {

        mAuth.createUserWithEmailAndPassword(mail, pw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    //debuging
                    System.out.println(user);
                    //Send Toast message and invoke Mailverification
                    Toast toast = Toast.makeText(getApplicationContext(), "Registration mail pending..", Toast.LENGTH_LONG);
                    toast.show();
                    user.sendEmailVerification();
                    String uID = user.getUid();
                    //Store User ID and Mail to the RT Database
                    DatabaseReference fdRef = database.getReference("Users").child(uID);
                    fdRef.child("Mail").setValue(mail);
                    fdRef.child("Phone").setValue(phone);
                    fdRef.child("Name").setValue(name);

                    //Go back to login Screen
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                } else {
                    System.out.println(task.getException().getMessage());
                    Toast toast = Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }


        });

    }
}