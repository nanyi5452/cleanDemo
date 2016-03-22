package com.example.xiaomao.acleanapp.view;

import com.example.xiaomao.interactor.GetEntryUseCase;

/**
 * Created by Administrator on 16-3-22.
 */
public interface EntryView extends LoadDataView{


    void renderEntry(GetEntryUseCase.EntryReturn result);

}
