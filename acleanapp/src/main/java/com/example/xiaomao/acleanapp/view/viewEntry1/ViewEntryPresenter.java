package com.example.xiaomao.acleanapp.view.viewEntry1;

import android.support.annotation.NonNull;

import com.example.xiaomao.acleanapp.view.EntryView;
import com.example.xiaomao.acleanapp.view.presenter.Presenter;
import com.example.xiaomao.interactor.GetEntryUseCase;
import com.example.xiaomao.interactor.ReturnResult;
import com.example.xiaomao.interactor.UseCase;
import com.example.xiaomao.utils.subscriber.DefaultSubscriber;

/**
 * Created by Administrator on 16-3-22.
 */
public class ViewEntryPresenter implements Presenter {


    private EntryView entryView;

    private final UseCase getEntryUseCase;


    public ViewEntryPresenter(UseCase getEntryUseCase){
        this.getEntryUseCase=getEntryUseCase;
    }

    public void setView(@NonNull EntryView view) {
        this.entryView = view;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getEntryUseCase.unsubscribe();
        this.entryView=null;
    }


    /**
     * Initializes the presenter by start retrieving user details.
     */
    public void initialize() {
        this.loadUserDetails();
    }

    /**
     * Loads user details.
     */
    private void loadUserDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getUserDetails();
    }


    private void showViewLoading() {
        this.entryView.showLoading();
    }

    private void hideViewLoading() {
        this.entryView.hideLoading();
    }

    private void showViewRetry() {
        this.entryView.showRetry();
    }

    private void hideViewRetry() {
        this.entryView.hideRetry();
    }

    private void showEntryInView(GetEntryUseCase.EntryReturn result) {
        entryView.renderEntry(result);
    }


    private void getUserDetails() {
        this.getEntryUseCase.execute(new ViewEntrySubscriber());
    }

    private final class ViewEntrySubscriber extends DefaultSubscriber<ReturnResult> {

        @Override public void onCompleted() {
            hideViewLoading();
        }

        @Override public void onError(Throwable e) {
            hideViewLoading();
//            showErrorMessage(new DefaultErrorBundle((Exception) e)); // not implemented...
            showViewRetry();
        }

        @Override public void onNext(ReturnResult result) {
            showEntryInView((GetEntryUseCase.EntryReturn)result);
        }
    }


}
