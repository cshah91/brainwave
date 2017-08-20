package nksystems.brainwave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * This class is used for displaying list of products fetched from the database
 *
 * @author  Charmy Shah
 * @date    28-04-2017
 * @version 1.0
 */
public class ProductDescriptionActivity extends AppCompatActivity {

    TextView tvProductName,tvProductDescription,tvProductPrice,tvProductId;
    ImageView ivThumbnail;
    Button btView,btRent;
    DatabaseReference mReference;
    StorageReference sReference;
    String productDescription="productDescription";
    String productPrice="productPrice";
    String productName="productName";
    String productId="productId";
    String imageName="";
    String pdfuri="";
    boolean isAllowed=false;
    String shortDescription;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProductDescriptionActivity.this,HomeMenuActivity.class);
        intent.putExtra("active_activity","herbalProducts");
        finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent=new Intent(ProductDescriptionActivity.this,HomeMenuActivity.class);
                intent.putExtra("active_activity","herbalProducts");
                finish();
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_product_description);

        shortDescription = getIntent().getStringExtra("shortDescription");

        tvProductName=(TextView)findViewById(R.id.tvProductName);
        tvProductPrice=(TextView)findViewById(R.id.tvProductPrice);
        tvProductDescription=(TextView)findViewById(R.id.tvProductDescription);
        tvProductId=(TextView)findViewById(R.id.tvProductId);

        ivThumbnail=(ImageView)findViewById(R.id.imageView);

        btView=(Button)findViewById(R.id.btView);
        btRent=(Button)findViewById(R.id.btRent);

        mReference= FirebaseDatabase.getInstance().getReference("products");
        sReference= FirebaseStorage.getInstance().getReference("imagethumbnails");

        final String productName=getIntent().getStringExtra("title");
        this.productName=productName;

        final DatabaseReference productReference=mReference.child(productName);
        productReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productDescription=dataSnapshot.child("dirforuse").getValue().toString();
                productPrice=dataSnapshot.child("price").getValue().toString();
                productId=dataSnapshot.child("productid").getValue().toString();

                imageName=productName.replace(" ","");
                sReference.child(imageName+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(ProductDescriptionActivity.this).load(uri).into(ivThumbnail);
                        ivThumbnail.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /*Toast.makeText(ProductDescriptionActivity.this, "Failed", Toast.LENGTH_SHORT).show();*/
                    }
                });

                sReference.getParent().child("filepdf").child(imageName+".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pdfuri=uri.toString();
                    }
                });

                tvProductName.setText(productName);
                tvProductDescription.setText(productDescription);
                tvProductPrice.setText("Price: " + productPrice + " INR");
                tvProductId.setText(productId);

                /*if(dataSnapshot.child("accesslist").hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    isAllowed=true;

                }else{
                    isAllowed=false;
                }

                if(isAllowed){
                    btView.setEnabled(true);
                    btView.setBackgroundColor(ProductDescriptionActivity.this.getResources().getColor(R.color.colorPrimary));
                    btRent.setBackgroundColor(ProductDescriptionActivity.this.getResources().getColor(R.color.disabledButton));
                    btRent.setEnabled(false);
                }else{
                    btView.setEnabled(false);
                    btView.setBackgroundColor(ProductDescriptionActivity.this.getResources().getColor(R.color.disabledButton));
                    btRent.setBackgroundColor(ProductDescriptionActivity.this.getResources().getColor(R.color.colorPrimary));
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
                finish();
                Intent intent = new Intent(ProductDescriptionActivity.this,ShippingAddressActivity.class);
                intent.putExtra("productName",productName)
                        .putExtra("orderType","product")
                        .putExtra("productDescription",shortDescription)
                        .putExtra("originalAmount",productPrice);
                startActivity(intent);
            }
        });

        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference ref=sReference.getParent().child("filepdf").child(imageName+".pdf");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfuri));
                intent.setPackage("com.android.chrome");
                finish();
                startActivity(intent);
            }
        });
    }


}
