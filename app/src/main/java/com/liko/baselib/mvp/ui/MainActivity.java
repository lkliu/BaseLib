package com.liko.baselib.mvp.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.liko.base.entity.FristBean;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.baselib.R;
import com.liko.baselib.R2;
import com.liko.baselib.mvp.adapter.InterAdapter;
import com.liko.baselib.mvp.contract.MainContract;
import com.liko.baselib.mvp.presenter.MainPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * @author Liko
 * @Date 17/10/27 下午6:28
 * @Description
 */
@Route(path = "/home/main")
public class MainActivity extends BaseActivity<MainContract.presenter> implements MainContract.View {
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R2.id.recy_indicator)
    RecyclerView recyIndicator;
    //    @BindView(R.id.indicator)
//    CircleIndicator indicator;
    private FixedSpeedScroller mScroller;

    private AdSwitchTask adSwitchTask;
    /**
     * 是否开启了自动翻页
     */
    private boolean turning = true;
    /**
     * 自动翻页的时间
     */
    private Long autoTurningTime = 2000L;

    private RxPermissions permissions;
    int[] imgRes = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h, R.mipmap.i};
    public PagerAdapter mAdapter;
    private InterAdapter interAdapter;

    @Override
    protected void initData() {
        initRecyclerView();
        permissions = new RxPermissions(this);
        mViewPager.setPageMargin(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = new ImageView(MainActivity.this);
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                final int realPosition = getRealPosition(position);
                view.setImageResource(imgRes[realPosition]);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "click position= " + realPosition, Toast.LENGTH_SHORT).show();
                    }
                });
                return view;
            }


            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public void startUpdate(ViewGroup container) {
                super.startUpdate(container);
                ViewPager viewPager = (ViewPager) container;
                int position = viewPager.getCurrentItem();
                if (position == 0) {
                    position = getFirstItemPosition();
                } else if (position == getCount() - 1) {
                    position = getLastItemPosition();
                }
                viewPager.setCurrentItem(position, false);

            }

            private int getRealCount() {
                return imgRes.length;
            }

            public int getRealPosition(int position) {
                return position % getRealCount();
            }

            private int getFirstItemPosition() {
                return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount();
            }

            private int getLastItemPosition() {
                return Integer.MAX_VALUE / getRealCount() / 2 * getRealCount() - 1;
            }
        });
        mViewPager.setPageTransformer(true, new ScaleInTransformer(0.75f));
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
            mField.set(mViewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        adSwitchTask = new AdSwitchTask(this);
        startTurning(4000);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                interAdapter.changeDate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case SCROLL_STATE_DRAGGING://按在屏幕上并且开始拖动
                        stopTurning();
                        break;
                    case SCROLL_STATE_IDLE://滑动动画做完的状态。
                        break;
                    case SCROLL_STATE_SETTLING://在“手指离开屏幕”的状态。
                        startTurning(4000);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < imgRes.length; i++) {
            list.add(i);
        }
        interAdapter = new InterAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyIndicator.setLayoutManager(layoutManager);
        recyIndicator.setAdapter(interAdapter);

    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<MainActivity> reference;

        AdSwitchTask(MainActivity activity) {
            this.reference = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void run() {
            MainActivity activity = reference.get();
            if (activity != null) {
                if (activity.mViewPager != null && activity.turning) {
                    int page = activity.mViewPager.getCurrentItem() + 1;
                    activity.mViewPager.setCurrentItem(page);
                    activity.mScroller.setmDuration(500);
                    activity.mViewPager.postDelayed(activity.adSwitchTask, activity.autoTurningTime);
                }
            }
        }
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return
     */
    public void startTurning(long autoTurningTime) {
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        this.autoTurningTime = autoTurningTime;
        turning = true;
        mViewPager.postDelayed(adSwitchTask, autoTurningTime);
    }

    public void stopTurning() {
        turning = false;
        mViewPager.removeCallbacks(adSwitchTask);
    }


    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    public MainContract.presenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Class activityClass, Bundle bundle, int requestCode) {
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void showEmptyLayout() {

    }

    @Override
    public void showErrorLayout() {

    }

    @Override
    public void showNoNetworkLayout() {

    }

    @Override
    public void showSuccessLayout() {
    }

    @Override
    public void setDate(FristBean fristBean) {
        tvContent.setText(fristBean.getResults().size() + "--");
    }

    @OnClick({R2.id.button, R2.id.button1, R2.id.button2})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.button) {
            ARouter.getInstance().build("/launch/test").navigation();
        }
//        switch (view.getId()) {
//            case R.id.button:
//
//                break;
//            case R.id.button1:
//                PermissionsUtil.callPhone(new PermissionsUtil.RequestPermission() {
//                    @Override
//                    public void onRequestPermissionSuccess(boolean flag) {
//                        if (flag) {
//                            Toast.makeText(context, "请求打电话权限成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, permissions, this);
//                break;
//            case R.id.button2:
//                PermissionsUtil.launchCamera(new PermissionsUtil.RequestPermission() {
//                    @Override
//                    public void onRequestPermissionSuccess(boolean flag) {
//                        if (flag) {
//                            Toast.makeText(context, "请求权限成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, permissions, this);
//                break;
//            default:
//                break;
//        }

    }
}
