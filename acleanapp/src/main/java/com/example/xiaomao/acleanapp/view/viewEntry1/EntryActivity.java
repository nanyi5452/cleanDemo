package com.example.xiaomao.acleanapp.view.viewEntry1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.xiaomao.acleanapp.R;
import com.example.xiaomao.acleanapp.view.EntryView;
import com.example.xiaomao.executors.JobExecutor;
import com.example.xiaomao.executors.UIThread;
import com.example.xiaomao.interactor.GetEntryUseCase;
import com.example.xiaomao.interactor.UseCase;
import com.example.xiaomao.repository.DataStore;
import com.example.xiaomao.repository.RemoteDataStore;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

public class EntryActivity extends AppCompatActivity {

    public static final String INTENT_KEY_ENTRYKEY="ID key";
    public static final int ENTRYKEY_default=-9;

    private int entryId=ENTRYKEY_default;

    public static void jump(Context context,int entryId){
        Intent i=new Intent(context,EntryActivity.class);
        i.putExtra(INTENT_KEY_ENTRYKEY,entryId);
        context.startActivity(i);
    }



    Context me;

    UseCase getEntry;

    EntryView ev;
    ImageView iv;
    CircleProgressBar progressBar;


    ViewEntryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=getIntent();
        entryId=i.getIntExtra(INTENT_KEY_ENTRYKEY,ENTRYKEY_default);
        setTitle("图片");

        me=this;

        setContentView(R.layout.activity_entry);
        progressBar= (CircleProgressBar) findViewById(R.id.progressBar_entry);
        iv= (ImageView) findViewById(R.id.image_entry);
        ev=new EntryView() {

            @Override
            public void renderEntry(GetEntryUseCase.EntryReturn result) {
                iv.setImageDrawable(result.getRetImage());
            }

            @Override
            public void showLoading() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideLoading() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void showRetry() {
            }

            @Override
            public void hideRetry() {
            }

            @Override
            public void showError(String message) {
            }

            @Override
            public Context context() {
                return me;
            }
        };

        DataStore remote=new RemoteDataStore(getApplicationContext());
        getEntry=new GetEntryUseCase(new JobExecutor(),new UIThread(),remote,entryId);


        presenter=new ViewEntryPresenter(getEntry);
        presenter.setView(ev);

        presenter.initialize();
    }




    @Override
    protected void onResume() {
        super.onResume();
    }


}
