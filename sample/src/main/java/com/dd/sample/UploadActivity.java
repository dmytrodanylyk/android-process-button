package com.dd.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dd.processbutton.iml.GenerateProcessButton;
import com.dd.sample.utils.ProgressGenerator;

public class UploadActivity extends Activity implements
        ProgressGenerator.OnCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_upload);

        final ProgressGenerator progressGenerator = new ProgressGenerator(this,
                null);
        final GenerateProcessButton btnUpload = (GenerateProcessButton) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressGenerator.start(btnUpload, false);
                btnUpload.setEnabled(false);
            }
        });
    }

    @Override
    public void onComplete() {
        Toast.makeText(this, R.string.Loading_Complete, Toast.LENGTH_LONG).show();
    }
}
