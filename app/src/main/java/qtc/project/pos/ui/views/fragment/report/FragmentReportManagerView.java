package qtc.project.pos.ui.views.fragment.report;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkebanhang.FragmentSalesSummaryManager;
import qtc.project.pos.fragment.report.thongkekho.FragmentReportKhoManager;

public class FragmentReportManagerView extends BaseView<FragmentReportManagerView.UIContainer> implements FragmentReportManagerViewInterface {

    HomeActivity activity;
    FragmentReportManagerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentReportManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        ui.layoutBCTK_KHO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentReportKhoManager(),true,null);
            }
        });

        ui.layoutBCTK_BH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentSalesSummaryManager(),true,null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentReportManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_report_manager;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutBCTK_BH)
        public LinearLayout layoutBCTK_BH;

        @UiElement(R.id.layoutBCTK_KHO)
        public LinearLayout layoutBCTK_KHO;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
