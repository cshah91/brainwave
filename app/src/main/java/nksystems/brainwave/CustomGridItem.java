package nksystems.brainwave;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by Charmy on 28/04/2017.
 */

public class CustomGridItem extends BaseAdapter{

    LayoutInflater inflater;
    List<DataSnapshot> productsList=new ArrayList<>();
    StorageReference sReference;
    Context mContext;
    DatabaseReference mReference;
    DataSnapshot snapshot;
    int count=0;
    String app = "CustomGridItem";

    public CustomGridItem(Context mContext, StorageReference sReference,DatabaseReference mReference, String orderby){
        this.mContext=mContext;
        inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sReference=sReference;
        this.mReference=mReference;

        this.mReference.orderByChild(orderby).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=(int)dataSnapshot.getChildrenCount();
                snapshot=dataSnapshot;
                Iterable<DataSnapshot> childrenIterator= dataSnapshot.getChildren();
                for(DataSnapshot productName : childrenIterator){
                    productsList.add(productName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View root;
        if(convertView==null){
            root=inflater.inflate(R.layout.custom_grid_products_layout,null);

        }
        else{
            root = convertView;
        }

        final ImageView ivThumbnail= (ImageView)root.findViewById(R.id.ivThumbnail);
        DataSnapshot currentsnapshot=productsList.get(position);
        String title=currentsnapshot.getKey();
        String autor=currentsnapshot.child("shortdesc").getValue().toString();

        TextView tvTitle=(TextView)root.findViewById(R.id.tvTitle);
        TextView tvAuthor=(TextView)root.findViewById(R.id.tvAuthor);

        tvTitle.setText(title);
        tvAuthor.setText(autor);

        String imageName=title.replace(" ","");

        sReference.child(imageName+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(mContext).load(uri).into(ivThumbnail);
                ivThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    /*Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();*/
            }
        });
        return root;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
