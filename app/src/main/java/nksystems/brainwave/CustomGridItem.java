package nksystems.brainwave;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used for creating individual item for displaying respective product description
 *
 * @author Charmy Shah
 * @version 1.0
 * @date 28-04-2017
 */
public class CustomGridItem extends BaseAdapter {

    LayoutInflater inflater;
    List<DataSnapshot> productsList = new ArrayList<>();
    StorageReference sReference;
    Context mContext;
    DatabaseReference mReference;
    DataSnapshot snapshot;
    int count = 0;
    String app = "CustomGridItem";

    /**
     * @param mContext
     * @param sReference
     * @param mReference
     * @param orderby
     */
    public CustomGridItem(Context mContext, StorageReference sReference, DatabaseReference mReference, String orderby) {
        this.mContext = mContext;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sReference = sReference;
        this.mReference = mReference;

        this.mReference.orderByChild(orderby).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                snapshot = dataSnapshot;
                Iterable<DataSnapshot> childrenIterator = dataSnapshot.getChildren();
                count = 0;
                for (DataSnapshot productName : childrenIterator) {
                    if ((Boolean) productName.child("active").getValue()) {
                        productsList.add(productName);
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @return
     */
    @Override
    public int getCount() {
        return count;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root;
        if (convertView == null) {
            root = inflater.inflate(R.layout.item_pari_herbal_product, null);

        } else {
            root = convertView;
        }

        final ImageView ivThumbnail = (ImageView) root.findViewById(R.id.ivThumbnail);
        DataSnapshot currentsnapshot = productsList.get(position);
        String title = currentsnapshot.getKey();
        String autor = currentsnapshot.child("shortdesc").getValue().toString();

        TextView tvTitle = (TextView) root.findViewById(R.id.tvTitle);
        TextView tvAuthor = (TextView) root.findViewById(R.id.tvAuthor);

        tvTitle.setText(title);
        tvAuthor.setText(autor);

        String imageName = title.replace(" ", "");

        sReference.child(imageName + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).into(ivThumbnail);
                ivThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return root;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return null;
    }
}