package com.dd.sample;

import com.dd.processbutton.iml.ActionProcessButton;
import com.dd.processbutton.iml.GenerateProcessButton;
import com.dd.processbutton.iml.SubmitProcessButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class StateSampleActivity extends Activity implements View.OnClickListener {

    private ActionProcessButton mBtnAction;
    private GenerateProcessButton mBtnGenerate;
    private SubmitProcessButton mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_states);

        mBtnAction = (ActionProcessButton) findViewById(R.id.btnAction);
        mBtnSubmit = (SubmitProcessButton) findViewById(R.id.btnSubmit);
        mBtnGenerate = (GenerateProcessButton) findViewById(R.id.btnGenerate);

        findViewById(R.id.btnProgressLoading).setOnClickListener(this);
        findViewById(R.id.btnProgressError).setOnClickListener(this);
        findViewById(R.id.btnProgressComplete).setOnClickListener(this);
        findViewById(R.id.btnProgressNormal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProgressLoading:
                mBtnAction.setProgress(50);
                mBtnSubmit.setProgress(50);
                mBtnGenerate.setProgress(50);
                break;
            case R.id.btnProgressError:
                mBtnAction.setProgress(-1);
                mBtnSubmit.setProgress(-1);
                mBtnGenerate.setProgress(-1);
                break;
            case R.id.btnProgressComplete:
                mBtnAction.setProgress(100);
                mBtnSubmit.setProgress(100);
                mBtnGenerate.setProgress(100);
                break;
            case R.id.btnProgressNormal:
                mBtnAction.setProgress(0);
                mBtnSubmit.setProgress(0);
                mBtnGenerate.setProgress(0);
                break;
        }
    }
}
