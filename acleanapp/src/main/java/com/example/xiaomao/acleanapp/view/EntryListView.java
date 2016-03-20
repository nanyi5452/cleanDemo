/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.xiaomao.acleanapp.view;

import com.example.coreDomain.DisplayEntry;
import com.example.xiaomao.interactor.GetEntriesUseCase;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link com.example.coreDomain.DisplayEntry}.
 */
public interface EntryListView extends LoadDataView {
  /**
   * Render a user list in the UI.  (provide data to the adapter)
   *
   * @param result return result from {@link GetEntriesUseCase}
   */
  void renderList(GetEntriesUseCase.EntriesResult result);

  /**
   * View a {@link com.example.coreDomain.DisplayEntry} profile/details.
   *
   * @param entry The user that will be shown.
   */
  void viewEntry(DisplayEntry entry);
}
