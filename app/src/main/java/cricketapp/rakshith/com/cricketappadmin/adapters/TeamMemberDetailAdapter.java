package cricketapp.rakshith.com.cricketappadmin.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cricketapp.rakshith.com.cricketapp.R;
import cricketapp.rakshith.com.cricketappadmin.Utils.Constants;
import cricketapp.rakshith.com.cricketappadmin.activities.HomeActivity;
import cricketapp.rakshith.com.cricketappadmin.fragments.EditTeamMemberStatsFragment;
import cricketapp.rakshith.com.cricketappadmin.models.MemberItem;

/**
 * Created by rakshith on 3/12/17.
 */
public class TeamMemberDetailAdapter extends RecyclerView.Adapter<TeamMemberDetailAdapter.MemberViewHolder> implements View.OnClickListener {
    Activity mActivity;
    List<MemberItem> teamMemberList;
    private int roleImageId;
    String teamName;

    public TeamMemberDetailAdapter(Activity mActivity, String teamName, List<MemberItem> teamMembers) {
        this.mActivity = mActivity;
        this.teamName = teamName;
        this.teamMemberList = teamMembers;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.team_member_detail_item, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        MemberItem memberItem = teamMemberList.get(position);

        String playerName = memberItem.getName();
        String playerRole = memberItem.getRole();

        if (!TextUtils.isEmpty(playerName)) {
//            if (position == 0 && playerRole.equalsIgnoreCase(Constants.ROLE_KEEPER))
//                holder.tvPlayerName.setText(playerName + "(C)" + "(Wkt)");
//            else if (playerRole.equalsIgnoreCase(Constants.ROLE_KEEPER))
//                holder.tvPlayerName.setText(playerName + "(Wkt)");
            if (position == 0)
                holder.tvPlayerName.setText(playerName + "(C)");
            else
                holder.tvPlayerName.setText(playerName);
        }
        if (!TextUtils.isEmpty(playerRole)) {
            if (playerRole.equalsIgnoreCase(Constants.ROLE_BATSMEN))
                roleImageId = R.drawable.vector_batsmen;
            else if (playerRole.equalsIgnoreCase(Constants.ROLE_BOWLER))
                roleImageId = R.drawable.vector_ball;
            else if (playerRole.equalsIgnoreCase(Constants.ROLE_ALL_ROUNDER))
                roleImageId = R.drawable.vector_all_rounder;
            else if (playerRole.equalsIgnoreCase(Constants.ROLE_KEEPER))
                roleImageId = R.drawable.vector_keeper;
            else roleImageId = R.drawable.ic_rx1_logo;

            Glide.with(mActivity)
                    .load(roleImageId)
                    .placeholder(R.drawable.ic_rx1_logo)
                    .into(holder.ivPlayerRole);
        }

        Constants constants = new Constants();
        constants.setFadeAnimation(holder.itemView);

        holder.llMainContainer.setOnClickListener(this);
        holder.llMainContainer.setTag(playerName);
    }

    @Override
    public int getItemCount() {
        if (teamMemberList == null)
            return 0;
        else
            return teamMemberList.size();
    }

    @Override
    public void onClick(View v) {
        String memberName = (String) v.getTag();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TEAM_NAME, teamName);
        bundle.putString(Constants.PLAYER_NAME, memberName);
        ((HomeActivity) mActivity).replaceFragment(new EditTeamMemberStatsFragment(),
                mActivity.getResources().getString(R.string.member_stats), bundle);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llMainContainer;
        TextView tvPlayerName;
        ImageView ivPlayerRole;

        public MemberViewHolder(View itemView) {
            super(itemView);

            llMainContainer = (LinearLayout) itemView.findViewById(R.id.team_member_detail_item_ll_main_container);
            tvPlayerName = (TextView) itemView.findViewById(R.id.team_member_detail_item_tv_name);
            ivPlayerRole = (ImageView) itemView.findViewById(R.id.team_member_detail_item_iv_role);
        }
    }
}
