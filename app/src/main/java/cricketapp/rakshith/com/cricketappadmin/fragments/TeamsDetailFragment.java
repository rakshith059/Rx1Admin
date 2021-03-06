package cricketapp.rakshith.com.cricketappadmin.fragments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cricketapp.rakshith.com.cricketapp.R;
import cricketapp.rakshith.com.cricketappadmin.Utils.Constants;
import cricketapp.rakshith.com.cricketappadmin.activities.HomeActivity;
import cricketapp.rakshith.com.cricketappadmin.adapters.TeamMemberDetailAdapter;
import cricketapp.rakshith.com.cricketappadmin.models.TeamList;

/**
 * Created by rakshith on 3/12/17.
 */
public class TeamsDetailFragment extends BaseFragment implements View.OnClickListener {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;

    RecyclerView rvPlayersList;
    TeamList teamDetail;
    private ImageView ivCollapseImage;

    TeamMemberDetailAdapter memberDetailAdapter;
    private int backgroundImageId;
    private TextView tvEditTeamScore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.from(mActivity).inflate(R.layout.fragment_teams_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            teamDetail = bundle.getParcelable(Constants.TEAM_DETAIL);
        }

        ivCollapseImage = (ImageView) view.findViewById(R.id.fragment_teams_detail_iv_collapse_image);
        toolbar = (Toolbar) view.findViewById(R.id.fragment_teams_detail_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar);
        rvPlayersList = (RecyclerView) view.findViewById(R.id.common_recycler_view_rv);
        tvEditTeamScore = (TextView) view.findViewById(R.id.fragment_teams_detail_tv_edit_team_score);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPlayersList.setLayoutManager(linearLayoutManager);

        tvEditTeamScore.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));

        String teamName = teamDetail.getTeamName();
        if (!TextUtils.isEmpty(teamName)) {
            collapsingToolbar.setTitle(teamName);
        }
        setBackgroundImage(teamDetail.getCityName());

        memberDetailAdapter = new TeamMemberDetailAdapter(mActivity, teamName, teamDetail.getTeamMembers());
        rvPlayersList.setAdapter(memberDetailAdapter);
    }

    private void setBackgroundImage(String cityName) {
        if (cityName.equalsIgnoreCase(Constants.TARIKERE)) {
            backgroundImageId = R.drawable.ic_tarikere;
        } else if (cityName.equalsIgnoreCase(Constants.BHADRAVATHI)) {
            backgroundImageId = R.drawable.ic_bhadravati;
        } else if (cityName.equalsIgnoreCase(Constants.KADUR)) {
            backgroundImageId = R.drawable.ic_kadur;
        } else if (cityName.equalsIgnoreCase(Constants.SHIVAMOGA)) {
            backgroundImageId = R.drawable.ic_shimoga;
        } else if (cityName.equalsIgnoreCase(Constants.ARSIKERE)) {
            backgroundImageId = R.drawable.ic_arsikere;
        } else if (cityName.equalsIgnoreCase(Constants.TIPTUR)) {
            backgroundImageId = R.drawable.ic_tiptur;
        } else if (cityName.equalsIgnoreCase(Constants.HASSAN)) {
            backgroundImageId = R.drawable.ic_hassan;
        } else {
            backgroundImageId = R.drawable.gradient_bg;
        }
        Glide.with(mActivity)
                .load(backgroundImageId)
                .into(ivCollapseImage);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TEAM_NAME, teamDetail.getTeamName());
        ((HomeActivity) getActivity()).replaceFragment(new EditTeamScoreFragment(), getResources().getString(R.string.edit_team), bundle);
    }
}
