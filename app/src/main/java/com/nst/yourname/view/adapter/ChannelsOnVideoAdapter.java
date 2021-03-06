package com.nst.yourname.view.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nst.yourname.R;
import com.nst.yourname.miscelleneious.common.AppConst;
import com.nst.yourname.model.LiveStreamsDBModel;
import com.nst.yourname.model.database.LiveStreamDBHandler;
import com.nst.yourname.model.pojo.XMLTVProgrammePojo;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChannelsOnVideoAdapter extends RecyclerView.Adapter<ChannelsOnVideoAdapter.MyViewHolder> {
    private static SharedPreferences loginPreferencesSharedPref_time_format;
    private int adapterPosition;
    public List<LiveStreamsDBModel> completeList;
    public Context context;
    public List<LiveStreamsDBModel> filterList = new ArrayList();
    private boolean firstTimeFlag = true;
    private LiveStreamDBHandler liveStreamDBHandler;
    public List<LiveStreamsDBModel> moviesListl;
    private SimpleDateFormat programTimeFormat;
    public int text_last_size;
    public int text_size;

    /*public class MyViewHolder_ViewBinding implements Unbinder {
        private MyViewHolder target;

        @UiThread
        public MyViewHolder_ViewBinding(MyViewHolder myViewHolder, View view) {
            this.target = myViewHolder;
            myViewHolder.tvMovieCategoryName = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_movie_category_name, "field 'tvMovieCategoryName'", TextView.class);
            myViewHolder.pbPagingLoader = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.pb_paging_loader, "field 'pbPagingLoader'", ProgressBar.class);
            myViewHolder.rlOuter = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_outer, "field 'rlOuter'", RelativeLayout.class);
            myViewHolder.rlListOfCategories = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.rl_list_of_categories, "field 'rlListOfCategories'", RelativeLayout.class);
            myViewHolder.testing = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.testing, "field 'testing'", RelativeLayout.class);
            myViewHolder.tvChannelId = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_channel_id, "field 'tvChannelId'", TextView.class);
            myViewHolder.tvTime = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_time, "field 'tvTime'", TextView.class);
            myViewHolder.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
            myViewHolder.tvCurrentLive = (TextView) Utils.findRequiredViewAsType(view, R.id.tv_current_live, "field 'tvCurrentLive'", TextView.class);
            myViewHolder.ivChannelLogo = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_tv_icon, "field 'ivChannelLogo'", ImageView.class);
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            MyViewHolder myViewHolder = this.target;
            if (myViewHolder != null) {
                this.target = null;
                myViewHolder.tvMovieCategoryName = null;
                myViewHolder.pbPagingLoader = null;
                myViewHolder.rlOuter = null;
                myViewHolder.rlListOfCategories = null;
                myViewHolder.testing = null;
                myViewHolder.tvChannelId = null;
                myViewHolder.tvTime = null;
                myViewHolder.progressBar = null;
                myViewHolder.tvCurrentLive = null;
                myViewHolder.ivChannelLogo = null;
                return;
            }
            throw new IllegalStateException("Bindings already cleared.");
        }
    }*/

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_tv_icon)
        ImageView ivChannelLogo;
        @BindView(R.id.pb_paging_loader)
        ProgressBar pbPagingLoader;
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.rl_list_of_categories)
        RelativeLayout rlListOfCategories;
        @BindView(R.id.rl_outer)
        RelativeLayout rlOuter;
        @BindView(R.id.testing)
        RelativeLayout testing;
        @BindView(R.id.tv_channel_id)
        TextView tvChannelId;
        @BindView(R.id.tv_current_live)
        TextView tvCurrentLive;
        @BindView(R.id.tv_movie_category_name)
        TextView tvMovieCategoryName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            setIsRecyclable(false);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.util.List<com.nst.yourname.model.LiveStreamsDBModel>} */
    /* JADX WARNING: Multi-variable type inference failed */
    public ChannelsOnVideoAdapter(List<LiveStreamsDBModel> list, Context context2) {
        this.filterList.addAll(list);
        this.completeList = list;
        this.moviesListl = list;
        this.context = context2;
        this.liveStreamDBHandler = new LiveStreamDBHandler(context2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [?, android.view.ViewGroup, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate((int) R.layout.layout_channels_on_video, viewGroup, false));
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        int percentageLeft;
        LiveStreamsDBModel liveStreamsDBModel = this.moviesListl.get(i);
        String name = liveStreamsDBModel.getName();
        liveStreamsDBModel.getStreamId();
        String num = liveStreamsDBModel.getNum();
        String epgChannelId = liveStreamsDBModel.getEpgChannelId();
        String streamIcon = liveStreamsDBModel.getStreamIcon();
        if (name != null && !name.equals("") && !name.isEmpty()) {
            myViewHolder.tvMovieCategoryName.setText(name);
        }
        if (myViewHolder.tvChannelId != null) {
            myViewHolder.tvChannelId.setText(num);
        }
        myViewHolder.tvTime.setText("");
        myViewHolder.progressBar.setVisibility(8);
        myViewHolder.tvCurrentLive.setText("");
        if (!(epgChannelId == null || epgChannelId.equals("") || this.liveStreamDBHandler == null)) {
            ArrayList<XMLTVProgrammePojo> epg = this.liveStreamDBHandler.getEPG(epgChannelId);
            if (epg != null) {
                int i2 = 0;
                while (true) {
                    if (i2 >= epg.size()) {
                        break;
                    }
                    String start = epg.get(i2).getStart();
                    String stop = epg.get(i2).getStop();
                    String title = epg.get(i2).getTitle();
                    epg.get(i2).getDesc();
                    Long valueOf = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(start));
                    Long valueOf2 = Long.valueOf(com.nst.yourname.miscelleneious.common.Utils.epgTimeConverter(stop));
                    if (!com.nst.yourname.miscelleneious.common.Utils.isEventVisible(valueOf.longValue(), valueOf2.longValue(), this.context) || (percentageLeft = com.nst.yourname.miscelleneious.common.Utils.getPercentageLeft(valueOf.longValue(), valueOf2.longValue(), this.context)) == 0) {
                        i2++;
                    } else {
                        int i3 = 100 - percentageLeft;
                        if (i3 == 0 || title == null || title.equals("")) {
                            myViewHolder.tvTime.setVisibility(8);
                            myViewHolder.progressBar.setVisibility(8);
                            myViewHolder.tvCurrentLive.setVisibility(8);
                        } else {
                            if (AppConst.LIVE_FLAG == 0) {
                                myViewHolder.tvTime.setVisibility(0);
                                loginPreferencesSharedPref_time_format = this.context.getSharedPreferences(AppConst.LOGIN_PREF_TIME_FORMAT, 0);
                                this.programTimeFormat = new SimpleDateFormat(loginPreferencesSharedPref_time_format.getString(AppConst.LOGIN_PREF_TIME_FORMAT, ""));
                                TextView textView = myViewHolder.tvTime;
                                textView.setText(this.programTimeFormat.format(valueOf) + " - " + this.programTimeFormat.format(valueOf2));
                            }
                            myViewHolder.progressBar.setVisibility(0);
                            myViewHolder.progressBar.setProgress(i3);
                            myViewHolder.tvCurrentLive.setVisibility(0);
                            myViewHolder.tvCurrentLive.setText(title);
                        }
                    }
                }
            }
            myViewHolder.ivChannelLogo.setImageDrawable(null);
            if (streamIcon != null && !streamIcon.equals("")) {
                Picasso.with(this.context).load(streamIcon).placeholder((int) R.drawable.tv_icon).into(myViewHolder.ivChannelLogo);
            } else if (Build.VERSION.SDK_INT >= 21) {
                myViewHolder.ivChannelLogo.setImageDrawable(this.context.getResources().getDrawable(R.drawable.tv_icon, null));
            } else {
                myViewHolder.ivChannelLogo.setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.tv_icon));
            }
        }
        myViewHolder.rlOuter.setOnFocusChangeListener(new OnFocusChangeAccountListener(myViewHolder.rlOuter));
        if (i == 0 && this.firstTimeFlag) {
            myViewHolder.rlOuter.requestFocus();
            this.firstTimeFlag = false;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.moviesListl.size();
    }

    public void setVisibiltygone(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
    }

    public void filter(final String str, final TextView textView) {
        new Thread(new Runnable() {
            /* class com.nst.yourname.view.adapter.ChannelsOnVideoAdapter.AnonymousClass1 */

            public void run() {
                List unused = ChannelsOnVideoAdapter.this.filterList = new ArrayList();
                int unused2 = ChannelsOnVideoAdapter.this.text_size = str.length();
                if (ChannelsOnVideoAdapter.this.filterList != null) {
                    ChannelsOnVideoAdapter.this.filterList.clear();
                }
                if (TextUtils.isEmpty(str)) {
                    ChannelsOnVideoAdapter.this.filterList.addAll(ChannelsOnVideoAdapter.this.completeList);
                } else {
                    if ((ChannelsOnVideoAdapter.this.moviesListl != null && ChannelsOnVideoAdapter.this.moviesListl.size() == 0) || ChannelsOnVideoAdapter.this.text_last_size > ChannelsOnVideoAdapter.this.text_size) {
                        List unused3 = ChannelsOnVideoAdapter.this.moviesListl = ChannelsOnVideoAdapter.this.completeList;
                    }
                    if (ChannelsOnVideoAdapter.this.moviesListl != null) {
                        for (LiveStreamsDBModel liveStreamsDBModel : ChannelsOnVideoAdapter.this.moviesListl) {
                            if (liveStreamsDBModel.getName() != null && liveStreamsDBModel.getName().toLowerCase().contains(str.toLowerCase())) {
                                ChannelsOnVideoAdapter.this.filterList.add(liveStreamsDBModel);
                            }
                        }
                    }
                }
                ((Activity) ChannelsOnVideoAdapter.this.context).runOnUiThread(new Runnable() {
                    /* class com.nst.yourname.view.adapter.ChannelsOnVideoAdapter.AnonymousClass1.AnonymousClass1 */

                    public void run() {
                        if (TextUtils.isEmpty(str)) {
                            List unused = ChannelsOnVideoAdapter.this.moviesListl = ChannelsOnVideoAdapter.this.completeList;
                        } else if (!ChannelsOnVideoAdapter.this.filterList.isEmpty() || ChannelsOnVideoAdapter.this.filterList.isEmpty()) {
                            List unused2 = ChannelsOnVideoAdapter.this.moviesListl = ChannelsOnVideoAdapter.this.filterList;
                        }
                        if (ChannelsOnVideoAdapter.this.moviesListl != null && ChannelsOnVideoAdapter.this.moviesListl.size() == 0) {
                            textView.setVisibility(0);
                        }
                        int unused3 = ChannelsOnVideoAdapter.this.text_last_size = ChannelsOnVideoAdapter.this.text_size;
                        ChannelsOnVideoAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {
        private final View view;

        public OnFocusChangeAccountListener(View view2) {
            this.view = view2;
        }

        @SuppressLint({"ResourceType"})
        public void onFocusChange(View view2, boolean z) {
            float f = 1.0f;
            if (z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                Log.e("id is", "" + this.view.getTag());
                this.view.setBackgroundResource(R.drawable.shape_list_categories_focused);
            } else if (!z) {
                if (z) {
                    f = 1.09f;
                }
                performScaleXAnimation(f);
                performScaleYAnimation(f);
                performAlphaAnimation(z);
                this.view.setBackgroundResource(R.drawable.shape_list_categories);
            }
        }

        private void performScaleXAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleX", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performScaleYAnimation(float f) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "scaleY", f);
            ofFloat.setDuration(150L);
            ofFloat.start();
        }

        private void performAlphaAnimation(boolean z) {
            if (z) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.view, "alpha", z ? 0.6f : 0.5f);
                ofFloat.setDuration(150L);
                ofFloat.start();
            }
        }
    }
}
