package my.edu.mmu.foe.videoplayerdemo;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wenjiun on 4/12/2016.
 */

public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // make sure the orientation of the fragment follows the orientation of the device
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        // inflate the view from a XML Layout file
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // identify the RecyclerView in the view of the fragment
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        // set the RecyclerView to show a single column vertical list
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //  instantiate our custom RecyclerView.Adapter to obtain the data/model, generate the view for each item in the list
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        // hard-corded data/model for each Faculty object
        private Faculty[] faculties = {
                new Faculty("Faculty of Engineering", R.drawable.foe, R.raw.foe),
                new Faculty("Faculty of Computing & Informatics", R.drawable.fci, R.raw.fci),
                new Faculty("Faculty of Creative Multimedia", R.drawable.fcm, R.raw.fcm),
                new Faculty("Faculty of Management", R.drawable.fom, R.raw.fom),
                new Faculty("Faculty of Engineering & Technology", R.drawable.fet, R.raw.fet),
                new Faculty("Faculty of Information Science & Technology ", R.drawable.fist, R.raw.fist),
                new Faculty("Faculty of Business", R.drawable.fob, R.raw.fob),
                new Faculty("Faculty of Law", R.drawable.fol, R.raw.fol)
        };

        // Custom RecyclerView.ViewHolder class to store the UI Widget to be used in each row in the RecyclerView
        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView textView;
            private ImageView imageView;

            // Constructor of MyViewHolder to identify the UI Widget in the view of each row and set the listener for clicking a row
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView)itemView.findViewById(R.id.textView);
                imageView = (ImageView)itemView.findViewById(R.id.imageView);
                itemView.setOnClickListener(this);
            }

            // Clicking a row will replace the MainFragment with ViewVideoFragment
            // newInstance method allows the resource ID of the video to be played to be sent to the ViewVideoFragment
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(android.R.id.content, ViewVideoFragment.newInstance(faculties[getAdapterPosition()].getVideo()))
                        .addToBackStack(null).commit();
            }
        }

        // Generate the view for each row and store the view in MyViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.row, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        // Setup the content of UI widgets in each row via the ViewHolder
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(faculties[position].getTitle());
            holder.imageView.setImageResource(faculties[position].getImage());
        }

        // Obtain the size of the data/model to determine the length of the recyclerview
        @Override
        public int getItemCount() {
            return faculties.length;
        }


    }
}
