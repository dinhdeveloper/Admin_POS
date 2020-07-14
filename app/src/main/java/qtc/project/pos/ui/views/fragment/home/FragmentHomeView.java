package qtc.project.pos.ui.views.fragment.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.customer.FragmentCustomer;
import qtc.project.pos.fragment.employee.FragmentEmployeeManager;
import qtc.project.pos.fragment.history.FragmentOrder;
import qtc.project.pos.fragment.levelcustomer.FragmentLevelCustomer;
import qtc.project.pos.fragment.order.FragmentOrderManager;
import qtc.project.pos.fragment.product.FragmentProduct;
import qtc.project.pos.fragment.report.FragmentReportManager;
import qtc.project.pos.fragment.supplier.FragmentSupplierManager;

public class FragmentHomeView  extends BaseView<FragmentHomeView.UIContainer> implements FragmentHomeViewInterface{

    HomeActivity activity;
    FragmentHomeViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentHomeViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClickItem();
    }

    private void onClickItem() {
        ui.layoutQLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new FragmentProduct(),true,null);
            }
        });
        ui.layoutQLCDKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new FragmentLevelCustomer(),true,null);
            }
        });

        ui.layoutQLKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentCustomer(),true,null);
            }
        });

        ui.layoutQLNCU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentSupplierManager(),true,null);
            }
        });

        ui.layoutQLNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentEmployeeManager(),true,null);
            }
        });

        ui.layoutQLDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentOrderManager(),true,null);
            }
        });

        ui.layoutQLTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentReportManager(),true,null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentHomeView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_home;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutQLSP)
        public LinearLayout layoutQLSP;
        @UiElement(R.id.layoutQLKH)
        public LinearLayout layoutQLKH;

        @UiElement(R.id.layoutQLDH)
        public LinearLayout layoutQLDH;

        @UiElement(R.id.layoutQLNV)
        public LinearLayout layoutQLNV;

        @UiElement(R.id.layoutQLCDKH)
        public LinearLayout layoutQLCDKH;

        @UiElement(R.id.layoutQLTK)
        public LinearLayout layoutQLTK;

        @UiElement(R.id.layoutQLNCU)
        public LinearLayout layoutQLNCU;
    }
}
