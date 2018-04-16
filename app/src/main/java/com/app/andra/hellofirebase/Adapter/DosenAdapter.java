/*
 * Copyright (c) 2018. This Code is created and writed by Komang Candra Brata (k.candra.brata@ub.ac.id).
 * Inform the writer if you willing to edit or modify it for commercial purpose.
 */

package com.app.andra.hellofirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.andra.hellofirebase.MatkulActivity;
import com.app.andra.hellofirebase.Model.DosenModel;
import com.app.andra.hellofirebase.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.app.andra.hellofirebase.ProfileActivity.DOSEN_ID;
import static com.app.andra.hellofirebase.ProfileActivity.DOSEN_NAME;


/**
 * Created by Komang Candra Brata on 4/15/2018.
 */

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.CustomViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<DosenModel> dosen;
    private Context context;


    public DosenAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                               int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                viewGroup.getContext());
        View v =
                inflater.inflate(R.layout.row_view, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String nama = dosen.get(position).getName();
        final String nip = dosen.get(position).getNip();
        holder.editNama.setText(nama);
        holder.editNip.setText(nip);


        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(dosen.get(position).getNip()==holder.editNip.getText().toString())
                {   dosen.get(position).setName(holder.editNama.getText().toString());
                    dosen.get(position).setNip(holder.editNip.getText().toString());
                    updateitem(dosen.get(position));
                }
                else{

                    deleteitem(dosen.get(position).getNip());
                    dosen.get(position).setName(holder.editNama.getText().toString());
                    dosen.get(position).setNip(holder.editNip.getText().toString());
                    updateitem(dosen.get(position));

                }
                notifyDataSetChanged();

            }
        });
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteitem(dosen.get(position).getNip());

                notifyDataSetChanged();

            }
        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                Intent intent = new Intent(context, MatkulActivity.class);

                //putting artist name and id to intent
                intent.putExtra(DOSEN_ID, dosen.get(position).getNip());
                intent.putExtra(DOSEN_NAME, dosen.get(position).getName());

                //starting the activity with intent
                context.startActivity(intent);
                return false;
            }
        });

    }


    private void updateitem(DosenModel dosen) {
        //getting the specified artist reference

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("dosen").child(dosen.getNip());


        dR.setValue(dosen);


        Toast.makeText(context, "Dosen Updated", Toast.LENGTH_SHORT).show();

    }


    private void deleteitem(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("dosen").child(id);

        //removing data
        dR.removeValue();

        //getting the tracks reference for the specified artist
        //  DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        //  drTracks.removeValue();
        Toast.makeText(context, "Dosen deleted", Toast.LENGTH_SHORT).show();

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dosen.size();
    }

    public void addItem(ArrayList<DosenModel> mData) {
        this.dosen = mData;
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private EditText editNama, editNip;
        private Button btnupdate, btndelete;
        private CardView cv;

        public CustomViewHolder(View itemView) {
            super(itemView);

            editNama = (EditText) itemView.findViewById(R.id.edit_nama);
            editNip = (EditText) itemView.findViewById(R.id.edit_nip);
            btnupdate = (Button) itemView.findViewById(R.id.btn_update);
            btndelete = (Button) itemView.findViewById(R.id.btn_delete);
            cv = (CardView) itemView.findViewById(R.id.cv);


        }

    }


}