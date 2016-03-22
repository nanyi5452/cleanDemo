package com.example.xiaomao.acleanapp.view.viewlist1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.acleanapp.R;
import com.example.xiaomao.acleanapp.view.EntryListView;
import com.example.xiaomao.acleanapp.view.viewEntry1.EntryActivity;
import com.example.xiaomao.executors.JobExecutor;
import com.example.xiaomao.executors.UIThread;
import com.example.xiaomao.interactor.GetEntriesUseCase;
import com.example.xiaomao.interactor.UseCase;
import com.example.xiaomao.repository.DataStore;
import com.example.xiaomao.repository.RemoteDataStore;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

public class ViewListActivity extends AppCompatActivity {

    public static void jump(Context context){
        Intent i=new Intent(context,ViewListActivity.class);
        context.startActivity(i);
    }


    CircleProgressBar progressBar;
    RecyclerView entriesRv;
    EntriesRVadapter adapter;

    private EntryListView entriesView;

    Context me;

    ViewListPresenter presenter;
    UseCase usecase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me=this;
        setContentView(R.layout.activity_view_list);

        initViews();

        DataStore remote=new RemoteDataStore(getApplicationContext());
        usecase=new GetEntriesUseCase(new JobExecutor(),new UIThread(),remote);

        presenter=new ViewListPresenter(usecase);
        presenter.setView(entriesView);
        presenter.initialize();
    }

    private void initViews() {
        progressBar= (CircleProgressBar) findViewById(R.id.progressBar);
        entriesRv= (RecyclerView) findViewById(R.id.recycler_entries);
        entriesRv.setHasFixedSize(true);
        entriesRv.setLayoutManager(new LinearLayoutManager(me));
        entriesView=new EntryListView(){
            @Override
            public void showLoading() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideLoading() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void showRetry() {}

            @Override
            public void hideRetry() {}

            @Override
            public void showError(String message) {}

            @Override
            public Context context() {
                return me.getApplicationContext();
            }

            @Override
            public void renderList(GetEntriesUseCase.EntriesResult result) {
                if (adapter==null){
                    adapter=new EntriesRVadapter(result);

                    adapter.setOnItemClickListener(new EntriesRVadapter.OnItemClickListener() {
                        @Override
                        public void onEntryClicked(DisplayEntry entry) {
                            EntryActivity.jump(me,entry.getEntryId());
                        }
                    });

                    entriesRv.setAdapter(adapter);
                }
                else {
                    adapter.updateDate(result);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void viewEntry(DisplayEntry entry) {
            }
        };
    }
}
