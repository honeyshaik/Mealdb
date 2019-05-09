package com.example.dell.mealdb.Database;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public final class StateFullRecyclerView extends RecyclerView {
    private static final String SAVED_SUPER_STATE="super_state";
    private static final String  SAVED_LAYOUT_MANAGER="layout-manager-state";
    private Parcelable mLayoutManagerSavedState;
    public StateFullRecyclerView(@NonNull Context context) {
        super(context);
    }

    public StateFullRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateFullRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            Bundle bundle=(Bundle) state;
            mLayoutManagerSavedState = bundle.getParcelable(SAVED_LAYOUT_MANAGER);
            state=bundle.getParcelable(SAVED_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }
    private void restorePosition(){
        if(mLayoutManagerSavedState!=null){
            this.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
            mLayoutManagerSavedState=null;
        }
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        restorePosition();
    }
}
