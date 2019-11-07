package com.trf.trfinventory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class AddInventory extends Fragment {

    EditText nameEdt, descEdtt,countEdt;
    Button submitBtn;
    String name,desc;
    Integer count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addinventory_layout,container,false);
        nameEdt = view.findViewById(R.id.name_editText);
        descEdtt = view.findViewById(R.id.desc_editText);
        countEdt = view.findViewById(R.id.count_editText);
        submitBtn = view.findViewById(R.id.submit_button);

        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                        sendToFirestore();
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    }
                }
        );

        return view;
    }
    //method to get data from edt text
    public void getData()
    {
         name = nameEdt.getText().toString();
         desc = descEdtt.getText().toString();
         count = Integer.parseInt(countEdt.getText().toString());
    }
    //method to send data to Firestore
    public void sendToFirestore()
    {
        //here goes the code to add the product to database
        Product product = new Product(name,desc,count);
        final String docId = name;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("products").add(product).addOnSuccessListener(
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: DataSnapShot added successfully!!");
                        nameEdt.setText("");
                        descEdtt.setText("");
                        countEdt.setText("");
                        Toast.makeText(getActivity(), "Added!!", Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to add data snapshot!");              
                    }
                }
        );
    }
}
