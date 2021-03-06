package cricketapp.rakshith.com.cricketappadmin.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cricketapp.rakshith.com.cricketapp.R;
import cricketapp.rakshith.com.cricketappadmin.Utils.Constants;
import cricketapp.rakshith.com.cricketappadmin.adapters.PlayerStatsAdapter;
import cricketapp.rakshith.com.cricketappadmin.models.MemberStats;

/**
 * Created by rakshith on 3/10/17.
 */
public class BatsmenFragment extends BaseFragment implements View.OnClickListener {

    TextView tvPlayerName;
    TextView tvMatchesPlayed;
    TextView tvBallsOverCatches;
    TextView tvRunsWicketsRunOuts;
    TextView tvFoursMaidenStump;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    PlayerStatsAdapter playerStatsAdapter;
    RecyclerView rvPlayerStats;
    ProgressBar pbProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_fragment_stats, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        rvPlayerStats = (RecyclerView) view.findViewById(R.id.common_recycler_view_rv);
        pbProgress = (ProgressBar) view.findViewById(R.id.common_recycler_view_pb_progress);

        tvPlayerName = (TextView) view.findViewById(R.id.player_stats_item_tv_player_name);
        tvMatchesPlayed = (TextView) view.findViewById(R.id.player_stats_item_tv_matches);
        tvBallsOverCatches = (TextView) view.findViewById(R.id.player_stats_item_tv_balls_faced);
        tvRunsWicketsRunOuts = (TextView) view.findViewById(R.id.player_stats_item_tv_runs_scored);
        tvFoursMaidenStump = (TextView) view.findViewById(R.id.player_stats_item_tv_fours);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlayerStats.setLayoutManager(linearLayoutManager);

        tvBallsOverCatches.setText(getResources().getString(R.string.balls_faced));
        tvRunsWicketsRunOuts.setText(getResources().getString(R.string.runs));
        tvFoursMaidenStump.setText(getResources().getString(R.string.fours));


        tvBallsOverCatches.setOnClickListener(this);
        tvRunsWicketsRunOuts.setOnClickListener(this);
        tvFoursMaidenStump.setOnClickListener(this);
        getPlayerStats(Constants.DB_PLAYER_STATS_CHILD_RUNS);
    }

    private void getPlayerStats(String orderByChildValue) {
        databaseReference.child(Constants.DB_PLAYER_STATS).orderByChild(orderByChildValue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MemberStats> memberStatsList = new ArrayList<MemberStats>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MemberStats memberStats = snapshot.getValue(MemberStats.class);
                    memberStatsList.add(memberStats);
                }
                Collections.reverse(memberStatsList);
                playerStatsAdapter = new PlayerStatsAdapter(mActivity, Constants.ROLE_BATSMEN, memberStatsList);
                rvPlayerStats.setAdapter(playerStatsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_stats_item_tv_balls_faced:
                getPlayerStats(Constants.DB_PLAYER_STATS_CHILD_BALLS_FACED);
                break;
            case R.id.player_stats_item_tv_runs_scored:
                getPlayerStats(Constants.DB_PLAYER_STATS_CHILD_RUNS);
                break;
            case R.id.player_stats_item_tv_fours:
                getPlayerStats(Constants.DB_PLAYER_STATS_CHILD_FOURS);
                break;
            default:
                break;
        }
    }
}
