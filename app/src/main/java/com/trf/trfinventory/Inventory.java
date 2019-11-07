package com.trf.trfinventory;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;



public class Inventory extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    //widgets
    private RecyclerView recyclerView;
    private EditText searchBar;
    private TextView inventoryTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //vars
    private ArrayList<Product> inventoryList = new ArrayList<>();
    private ProductRecyclerViewAdapter productRecyclerViewAdapter;
    private DocumentSnapshot mLastQueriedDocument;
    private static final String TAG = "Inventory";

    //firebase
    //firebase



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_layout,container,false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.recyclerView);
        // never use recyclerView.setHasFixedSize(true);
        searchBar = view.findViewById(R.id.search_edit_text);
        inventoryTextView = view.findViewById(R.id.inventory_textView);
        //==================================================================
        getFirestoreData();
        setUpSearchBar();
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        if(productRecyclerViewAdapter==null){
            productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getActivity(),inventoryList);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(productRecyclerViewAdapter);

    }

    private void getFirestoreData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference productsCollectionRef = db
                .collection("products");

        Query productsQuery = null;
        if(mLastQueriedDocument != null){
            productsQuery = productsCollectionRef
                    .orderBy("name", Query.Direction.ASCENDING)
                    .startAfter(mLastQueriedDocument);
        }
        else{
            productsQuery = productsCollectionRef
                    .orderBy("name", Query.Direction.ASCENDING);
        }

        productsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(QueryDocumentSnapshot document: task.getResult()){
                        Product product = document.toObject(Product.class);
                        inventoryList.add(product);
                        //Log.d(TAG, "onComplete: got a new note. Position: " + (mNotes.size() - 1));
                    }

                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments()
                                .get(task.getResult().size() -1);
                    }

                    productRecyclerViewAdapter.notifyDataSetChanged();
                }
                else{
                    makeSnackBarMessage("Query Failed. Check Logs.");
                }
            }
        });
    }

    private void makeSnackBarMessage(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void setUpSearchBar(){
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input

                String text = editable.toString();
                ArrayList<Product> filteredProducts = new ArrayList<>();
                for(Product p: inventoryList){
                    //if existing elements contains the search input
                    if(p.getName().toLowerCase().contains(text.toLowerCase())){
                        filteredProducts.add(p);
                    }
                }
                productRecyclerViewAdapter.filterList(filteredProducts);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onRefresh() {
        getFirestoreData();
        mSwipeRefreshLayout.setRefreshing(false);
    }


}



