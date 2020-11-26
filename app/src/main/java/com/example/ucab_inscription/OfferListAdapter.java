package com.example.ucab_inscription;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class OfferListAdapter extends AppCompatActivity {
 private RecyclerView mFirestorelist;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_offers);

        firebaseFirestore=FirebaseFirestore.getInstance();
        mFirestorelist = findViewById(R.id.firestore_list);

        //Query
        Query query = FirebaseFirestore.getInstance().collection("users");
        //RecyclerOptions
        FirestoreRecyclerOptions<OfferModel> response = new FirestoreRecyclerOptions.Builder<OfferModel>()
                .setQuery(query, OfferModel.class).setLifecycleOwner(this)
                .build();

         adapter = new FirestoreRecyclerAdapter<OfferModel, OfferHolder>(response) {

            @NonNull
            @Override
            public OfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent,false);
                return new OfferHolder(view);
            }

            @Override
            protected void onBindViewHolder(OfferHolder offerHolder, int i, @NonNull OfferModel offerModel) {

                offerHolder.list_name.setText(offerModel.getDeparture());
                offerHolder.list_price.setText(offerModel.getDestination());

            }
            //ViewHholder
            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

         mFirestorelist.setHasFixedSize(true);
        mFirestorelist.setLayoutManager(new LinearLayoutManager(this));
        mFirestorelist.setAdapter(adapter);
    }


    private class OfferHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_price;

        public OfferHolder(@NonNull View itemView) {
            super(itemView);

            list_name= itemView.findViewById(R.id.list_name);
            list_price=itemView.findViewById(R.id.list_price);
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
