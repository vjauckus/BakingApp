package com.android.udacity.vjauckus.mybackingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.models.CakeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter for cake list used in MasterListFragment
 * Created by veronika on 31.01.18.
 */

public class CakeListAdapter extends RecyclerView.Adapter<CakeListAdapter.CakeAdapterViewHolder>{

    //private static final String TAG = CakeListAdapter.class.getSimpleName();
    private List<CakeModel> mCakeModelList;
    private Context mContext;

    /**
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final CakeAdapterOnClickHandler mClickHandler;


    /**
     * This is our own custom constructor
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     * @param clickHandler The on-click handler makes it easy for an Activity to interface with
     *                     @param context
     * our RecyclerView and works with Adapter. this single handler is called.
     */
    public CakeListAdapter(Context context, CakeAdapterOnClickHandler clickHandler){

        mClickHandler = clickHandler;
        mContext = context;
    }
    /**
     * Cache of the children views for a movie list item.
     */
    public class CakeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView (R.id.cake_image_main) ImageView cakeImage;
        @BindView(R.id.tv_cake_name_main) TextView cakeName;

        /**
         * Constructor
         * @param view The view that was clicked
         */

        CakeAdapterViewHolder(View view){

            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mClickHandler.onClick(mCakeModelList.get(position));

        }

    }
    /**This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent The ViewGroup that these ViewHolders are contained within.
     * @param viewType If our RecyclerView has more than one type of items(which ours does not), we can use this integer to provide a different layout
     * @return A new ViewHolder that holds the View for each list item
     */
    @Override
    public CakeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.cake_master_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new CakeAdapterViewHolder(view) ;

    }
    /**
     * Called by RecyclerView to display the movie data at the specified position
     * @param holder The ViewHolder that should be updated to represent the content of the item at the given position
     * @param position The position of item within adapter's data list
     */

    public void onBindViewHolder(CakeAdapterViewHolder holder, int position) {
        //CakeModel currentCake = mCakeModelList.get(position);
       // Context context = holder.itemView.getContext();
        String currentCakeName = mCakeModelList.get(position).getCakeName();
        String currentCakeImage = mCakeModelList.get(position).getCakeImage();
        int recipePicture;

        switch (currentCakeName){
            case "Nutella Pie":
                recipePicture = R.drawable.nutella_pie;
                break;
            case "Yellow Cake":
                recipePicture = R.drawable.yellow_cake;
                break;
            case "Brownies":
                recipePicture = R.drawable.brownies;
                break;
            case "Cheesecake":
                recipePicture = R.drawable.cheesecake;
                break;
            default:
                recipePicture = R.drawable.place_holder;

        }
        holder.cakeName.setText(currentCakeName);


        if (!currentCakeImage.equals("")){
            Picasso.with(mContext)
                    .load(currentCakeImage)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.user_placeholder_error)
                    .into(holder.cakeImage);
       }
       else {
            Picasso.with(mContext)
                    .load(recipePicture)
                    .placeholder(R.drawable.place_holder)
                    .error(R.drawable.user_placeholder_error)
                    .into(holder.cakeImage);
        }
      // holder.cakeImage.setImageResource(recipePicture);
       // Log.d(TAG, "I am in CakeListAdapter, the Image is: "+ currentCakeName + " "+ recipePicture);
    }

    @Override
    public int getItemCount() {
       if(mCakeModelList == null) return 0;
       return mCakeModelList.size();
    }
    public void setCakeList(List<CakeModel> cakeList){
        mCakeModelList = cakeList;
       // Log.d(TAG, "I am in CakeListAdapter, the data are here: "+ mCakeModelList.size()+"My List at position 0 is: "+mCakeModelList.get(0));
        notifyDataSetChanged();
    }
    /**
     * That interface receives onClick CakeModel object
     */
    public interface CakeAdapterOnClickHandler{

        void onClick(CakeModel currentCake);
    }


}
