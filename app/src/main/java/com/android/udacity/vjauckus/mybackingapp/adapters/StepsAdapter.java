package com.android.udacity.vjauckus.mybackingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for Backing steps used in StepDetailsFragment
 * Created on 09.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

   // private final static String TAG = StepsAdapter.class.getSimpleName();
    private ArrayList<StepsModel> mStepsDataList;
    /**
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final StepAdapterOnClickHandler mClickHandler;

    //Empty Constructor
    public StepsAdapter(StepAdapterOnClickHandler clickHandler){

        mClickHandler = clickHandler;

    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_list_item_layout, parent, false);

        return new StepsViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the step data at the specified position
     * @param holder StepsViewHolder
     * @param position specified position in step list
     */
    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {

       // Context context = holder.itemView.getContext();
        String stepDescription = mStepsDataList.get(position).getShortDescription();
        holder.mStepDescription.setText(stepDescription);

    }


    /**
     * Cache of the children views for a steps list item.
     */
    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_description) TextView mStepDescription;

        /**
         * Constructor
         */

        public  StepsViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mClickHandler.onClickStep(mStepsDataList, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mStepsDataList == null) return 0;
        return mStepsDataList.size();
    }

    public void setStepsDataList(ArrayList<StepsModel> stepsModels){

        mStepsDataList = stepsModels;
        notifyDataSetChanged();

    }

    public interface StepAdapterOnClickHandler{

       void onClickStep(ArrayList<StepsModel> stepsModelArrayList, int stepPosition);

    }
}
