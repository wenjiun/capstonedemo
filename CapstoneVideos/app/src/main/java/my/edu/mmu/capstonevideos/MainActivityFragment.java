package my.edu.mmu.capstonevideos;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayList<CapstoneVideo> videos = new ArrayList<CapstoneVideo>();
    private VideoView videoView;
    private View container;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapstoneVideo break_room = new CapstoneVideo("Break Room", R.drawable.break_room, R.raw.break_room);
        CapstoneVideo scary_movie = new CapstoneVideo("Scary Movie", R.drawable.scary_movie, R.raw.scary_movie);
        CapstoneVideo slightly_longer_road_trip = new CapstoneVideo("Slightly Longer Road Trip", R.drawable.slightly_longer_road_trip, R.raw.slightly_longe_road_trip);
        CapstoneVideo garage_band = new CapstoneVideo("Garage Band", R.drawable.garage_band, R.raw.garage_band);
        videos.add(break_room);
        videos.add(scary_movie);
        videos.add(slightly_longer_road_trip);
        videos.add(garage_band);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, viewGroup, false);
        container = view.findViewById(R.id.container);
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                videoView.stopPlayback();
                container.setVisibility(View.GONE);
                return true;
            }
        });
        videoView = (VideoView)view.findViewById(R.id.videoView);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setAdapter(new ArrayAdapter<CapstoneVideo>(getActivity(), android.R.layout.simple_list_item_1, videos) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View grid = inflater.inflate(R.layout.grid, parent, false);
                ImageView iv = (ImageView) grid.findViewById(R.id.imageView);
                iv.setImageResource(videos.get(position).getImageId());
                return grid;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                container.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + videos.get(position).getVideoId());
                videoView.setVideoURI(uri);
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        container.setVisibility(View.GONE);
                    }
                });
            }
        });
        return view;
    }

}
