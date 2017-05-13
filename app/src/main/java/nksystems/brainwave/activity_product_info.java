package nksystems.brainwave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by Charmy on 28/04/2017.
 */

public class activity_product_info extends AppCompatActivity {

    TextView tvTitle,tvAuthor,tvYear,tvGenre;
    ImageView ivThumbnail;
    Button btView,btRent;
    DatabaseReference mReference;
    StorageReference sReference;
    String author="author";
    String year="year";
    String title="title";
    String genre="genre";
    String imageName="";
    String pdfuri="";
    boolean isAllowed=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_info_layout);



        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvYear=(TextView)findViewById(R.id.tvYear);
        tvAuthor=(TextView)findViewById(R.id.tvAuthor);
        tvGenre=(TextView)findViewById(R.id.tvGenre);

        ivThumbnail=(ImageView)findViewById(R.id.imageView);

        btView=(Button)findViewById(R.id.btView);
        btRent=(Button)findViewById(R.id.btRent);

        mReference= FirebaseDatabase.getInstance().getReference("products");
        sReference= FirebaseStorage.getInstance().getReference("imagethumbnails");

        final String title=getIntent().getStringExtra("title");
        this.title=title;

        final DatabaseReference productReference=mReference.child(title);
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                author=dataSnapshot.child("dirforuse").getValue().toString();
                year=dataSnapshot.child("price").getValue().toString();
                genre=dataSnapshot.child("productid").getValue().toString();

                imageName=title.replace(" ","");
                sReference.child(imageName+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(activity_product_info.this).load(uri).into(ivThumbnail);
                        ivThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*Toast.makeText(activity_product_info.this, "Failed", Toast.LENGTH_SHORT).show();*/
                    }
                });

                sReference.getParent().child("filepdf").child(imageName+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pdfuri=uri.toString();
                    }
                });

                tvTitle.setText(title);
                tvAuthor.setText(author);
                tvYear.setText("Price: " + year + " INR");
                tvGenre.setText(genre);

                /*if(dataSnapshot.child("accesslist").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    isAllowed=true;

                }else{
                    isAllowed=false;
                }

                if(isAllowed){
                    btView.setEnabled(true);
                    btView.setBackgroundColor(activity_product_info.this.getResources().getColor(R.color.colorPrimary));
                    btRent.setBackgroundColor(activity_product_info.this.getResources().getColor(R.color.disabledButton));
                    btRent.setEnabled(false);
                }else{
                    btView.setEnabled(false);
                    btView.setBackgroundColor(activity_product_info.this.getResources().getColor(R.color.disabledButton));
                    btRent.setBackgroundColor(activity_product_info.this.getResources().getColor(R.color.colorPrimary));
                    btRent.setEnabled(true);
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productReference.child("accesslist").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("allow");
                finish();
                startActivity(new Intent(activity_product_info.this,activity_product_info.class).putExtra("title",title));
            }
        });

        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ref=sReference.getParent().child("filepdf").child(imageName+".pdf");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfuri));
                intent.setPackage("com.android.chrome");
                startActivity(intent);
            }
        });
    }


}
