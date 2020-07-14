package qtc.project.pos.ui.views.fragment.employee;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;

public class FragmentEmployeeManagerView extends BaseView<FragmentEmployeeManagerView.UIContainer> implements FragmentEmployeeManagerViewInterface {
    HomeActivity activity;
    FragmentEmployeeManagerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentEmployeeManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    private void onClick() {
        //BACK
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
        //danh sach nhan vien
        ui.layoutDSNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.goToDSNV();
            }
        });

//        //kiem soat lich su ban hang
//        ui.layoutKSLSBH.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback!=null)
//                    callback.goToKSLSBH();
//            }
//        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentEmployeeManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_employee_manager;
    }

    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutDSNV)
        public LinearLayout layoutDSNV;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

    }
}
