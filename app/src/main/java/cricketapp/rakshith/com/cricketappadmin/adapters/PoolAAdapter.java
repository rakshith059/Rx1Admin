package cricketapp.rakshith.com.cricketappadmin.adapters;

/**
 * Created by rakshith on 3/12/17.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cricketapp.rakshith.com.cricketapp.R;
import cricketapp.rakshith.com.cricketappadmin.Utils.Constants;
import cricketapp.rakshith.com.cricketappadmin.activities.HomeActivity;
import cricketapp.rakshith.com.cricketappadmin.fragments.TeamsDetailFragment;
import cricketapp.rakshith.com.cricketappadmin.models.TeamList;

/**
 * Created by rakshith on 3/11/17.
 */
public class PoolAAdapter extends RecyclerView.Adapter<PoolAAdapter.TeamsViewHolder> implements View.OnClickListener {
    List<TeamList> teamLists;
    Activity mActivity;

    int backgroundImageId;

    @Override
    public TeamsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item, parent, false);
        return new TeamsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamsViewHolder holder, int position) {
        TeamList team = teamLists.get(position);

        String teamName = team.getTeamName();
        String cityName = team.getCityName();
        String captainName = team.getTeamMembers().get(0).getName();
        String contactNumber = team.getContactNo();

        if (!TextUtils.isEmpty(teamName)) {
            holder.tvTeamName.setText(teamName);
        }
        if (!TextUtils.isEmpty(cityName)) {
            holder.tvCityName.setText(cityName);
            setBackgroundImage(cityName, holder);
        }
        if (!TextUtils.isEmpty(captainName)) {
            holder.tvCaptainName.setText(captainName);
        }
        if (!TextUtils.isEmpty(contactNumber)) {
            holder.tvContactNumber.setText(contactNumber);
        }

        holder.cvMainContainer.setOnClickListener(this);
        holder.cvMainContainer.setTag(team);

        Constants constants = new Constants();
        constants.setFadeAnimation(holder.itemView);
    }

    private void setBackgroundImage(String cityName, TeamsViewHolder holder) {
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
                .into(holder.ivBgImage);
    }

    @Override
    public int getItemCount() {
        if (teamLists == null)
            return 0;
        else
            return teamLists.size();
    }

    public void updatePoolAList(Activity mActivity, List teams) {
        this.mActivity = mActivity;
        teamLists = teams;
    }

    @Override
    public void onClick(View v) {
        TeamList teamDetail = (TeamList) v.getTag();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.TEAM_DETAIL, teamDetail);
        ((HomeActivity) mActivity).replaceFragment(new TeamsDetailFragment(), mActivity.getResources().getString(R.string.teams_detail), bundle);
    }

    public class TeamsViewHolder extends RecyclerView.ViewHolder {
        CardView cvMainContainer;
        ImageView ivBgImage;
        TextView tvTeamName;
        TextView tvCityName;
        TextView tvCaptainName;
        TextView tvContactNumber;

        public TeamsViewHolder(View itemView) {
            super(itemView);

            cvMainContainer = (CardView) itemView.findViewById(R.id.teams_item_cv_main_container);
            ivBgImage = (ImageView) itemView.findViewById(R.id.teams_item_iv_bg_image);
            tvTeamName = (TextView) itemView.findViewById(R.id.teams_item_tv_team_name);
            tvCityName = (TextView) itemView.findViewById(R.id.teams_item_tv_city_name);
            tvCaptainName = (TextView) itemView.findViewById(R.id.teams_item_tv_captain_name);
            tvContactNumber = (TextView) itemView.findViewById(R.id.teams_item_tv_contact_number);
        }
    }
}

