package com.example.xiaomao.acleanapp.view.viewlist1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiaomao.acleanapp.R;
import com.example.xiaomao.interactor.GetEntriesUseCase;

/**
 * Created by Administrator on 16-3-20.
 */
public class EntriesRVadapter extends RecyclerView.Adapter<EntriesRVadapter.RVHolder> {

    private GetEntriesUseCase.EntriesResult ret;

    public EntriesRVadapter(GetEntriesUseCase.EntriesResult ret){
        this.ret=ret;
    }

    public void updateDate(GetEntriesUseCase.EntriesResult ret){
        this.ret=ret;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rvitem1, parent, false);

        LinearLayout container = (LinearLayout)layout.findViewById(R.id.container);
        RVHolder holder = new RVHolder(container);
        holder.logo = (ImageView)layout.findViewById(R.id.logo);
        holder.tv = (TextView)layout.findViewById(R.id.tv_entryId);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        holder.logo.setImageDrawable(ret.getImageList().get(position));
        StringBuilder sb=new StringBuilder();
        sb.append("显示id:").append(""+ret.getEntries().get(position).getEntryId()).append("  图片介绍:").append(ret.getEntries().get(position).getDescription());
        holder.tv.setText(sb.toString());
    }

    @Override
    public int getItemCount() {
        return ret.getEntries().size();
    }

    public static class RVHolder extends RecyclerView.ViewHolder{
        LinearLayout container;
        ImageView logo;
        TextView tv;

        public RVHolder(LinearLayout v) {
            super(v);
            container = v;
        }
    }

}
