package com.example.xiaomao.acleanapp.view.viewlist1;

import android.support.annotation.NonNull;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.acleanapp.view.EntryListView;
import com.example.xiaomao.acleanapp.view.presenter.Presenter;
import com.example.xiaomao.interactor.GetEntriesUseCase;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.interactor.UseCase;
import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

/**
 * Created by Administrator on 16-3-20.
 */
public class ViewListPresenter implements Presenter {


    private EntryListView entryListView;

    private final UseCase getEntriesUseCase;

    public ViewListPresenter(UseCase getEntriesUseCase){
        this.getEntriesUseCase=getEntriesUseCase;
//        getEntriesUseCase;
    }


    public void setView(@NonNull EntryListView view) {
        this.entryListView = view;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getEntriesUseCase.unsubscribe();
        this.entryListView = null;
    }


    /**
     * Initializes the presenter by start retrieving the user list.
     */
    public void initialize() {
        this.loadUserList();
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getEntries();
    }


    public void onUserClicked(DisplayEntry entry) {
        this.entryListView.viewEntry(entry);
    }

    private void showViewLoading() {
        this.entryListView.showLoading();
    }

    private void hideViewLoading() {
        this.entryListView.hideLoading();
    }

    private void showViewRetry() {
        this.entryListView.showRetry();
    }

    private void hideViewRetry() {
        this.entryListView.hideRetry();
    }


    private void showErrorMessage() {   //  needs to be imlemented
    }

    private void showEntriesInView(GetEntriesUseCase.EntriesResult ret) {
        this.entryListView.renderList(ret);
    }



    private void getEntries() {
        this.getEntriesUseCase.execute(new EntriesSubscriber());
    }

    private final class EntriesSubscriber extends DefaultSubscriber<ReturnResult> {

        @Override public void onCompleted() {hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            hideViewLoading();
//            showErrorMessage(new DefaultErrorBundle((Exception) e)); //....
            showViewRetry();
        }

        @Override public void onNext(ReturnResult ret) {
            showEntriesInView((GetEntriesUseCase.EntriesResult)ret);
        }
    }





}
