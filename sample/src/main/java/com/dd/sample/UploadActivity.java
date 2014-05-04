package com.dd.sample;

import com.dd.processbutton.iml.GenerateProcessButton;
import com.dd.sample.utils.ProgressGenerator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class UploadActivity extends Activity implements ProgressGenerator.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_upload);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this);
        final GenerateProcessButton btnUpload =
                (GenerateProcessButton) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnUpload);
                btnUpload.setEnabled(false);
            }
        });
    }

    @Override
    public void onComplete() {

    }
}
