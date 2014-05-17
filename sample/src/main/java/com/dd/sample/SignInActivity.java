package com.dd.sample;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.dd.sample.utils.ProgressGenerator;
import com.dd.sample.utils.ProgressGenerator.OnFailureListener;

public class SignInActivity extends Activity implements
        ProgressGenerator.OnCompleteListener, OnFailureListener {

    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";
    private ActionProcessButton btnSignIn;
    private Random random = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_sign_in);

        final EditText editEmail = (EditText) findViewById(R.id.editEmail);
        final EditText editPassword = (EditText) findViewById(R.id.editPassword);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this,
                this);
        btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnSignIn, random.nextBoolean());
                btnSignIn.setEnabled(false);
                editEmail.setEnabled(false);
                editPassword.setEnabled(false);
            }
        });
    }

    @Override
    public void onComplete() {
        Toast.makeText(this, R.string.Loading_Complete, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure() {
        btnSignIn.setEnabled(true);
    }

}
