package nksystems.brainwave;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * This class is used for creating a card view adapter for the About Brainwave page
 *
 * @author Charmy Shah
 * @version 1.0
 * @date 28-04-2017
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.DataObjectHolder> {

    private List<Brainwave> mDataset;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView title;
        TextView content;

        public DataObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtCardAboutBWTitle);
            content = (TextView) itemView.findViewById(R.id.txtCardAboutBWContent);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    /**
     * @param myDataset
     */
    public CardViewAdapter(List<Brainwave> myDataset) {
        mDataset = myDataset;
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_about_brainwave, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getmText1());
        holder.content.setText(mDataset.get(position).getmText2());
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
