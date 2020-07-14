package qtc.project.pos.dialog.option;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.dialog.AppDialog;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;

public class OptionSelectDialog extends AppDialog<OptionSelectDialog.OptionSelectDialogListener> {

    private ArrayList<OptionModel> data;

    private ArrayList<OptionModel> listItemSelected;
    private OptionModel itemSelected;
    private boolean isSelectedMulti = false;

    private boolean showFilter = true;

    private String titleHintFilter = "Tìm kiếm";
    private String titleDialog = "Chọn";

    private static DialogType typeDialog;
    private OptionSelectAdapter adapter;
    private View loadingView, layoutEmptyList, layoutRootView;
    private EditText edtSearch;
    private ListView listView;

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private Handler handler;

    private String id_employee_tracking;

    public interface OptionSelectDialogListener {

        void onItemOptionSelected(OptionModel item);

        void onListItemOptionSelected(ArrayList<OptionModel> listItem);
    }

    public static OptionSelectDialog createNewInstance(ArrayList<OptionModel> data, OptionSelectDialogListener listener, boolean isSelectedMulti) {

        OptionSelectDialog dialog = new OptionSelectDialog();

        Bundle bundle = new Bundle();

        bundle.putBoolean("isSelectedMulti", isSelectedMulti);
        if (data != null) {
            bundle.putSerializable("data", data);
        }
        dialog.setArguments(bundle);

        dialog.setListener(listener);

        return dialog;

    }

    public static OptionSelectDialog createNewInstance(ArrayList<OptionModel> data, OptionSelectDialogListener listener, boolean isSelectedMulti, DialogType type) {

        typeDialog = type;
        OptionSelectDialog dialog = new OptionSelectDialog();

        Bundle bundle = new Bundle();

        bundle.putBoolean("isSelectedMulti", isSelectedMulti);
        if (data != null) {
            bundle.putSerializable("data", data);
        }
        dialog.setArguments(bundle);

        dialog.setListener(listener);

        return dialog;

    }

    public OptionSelectDialog() {
        setCancelable(true);
    }

    public void setShowFilter(boolean showFilter) {
        this.showFilter = showFilter;
    }

    public void setTitleHintFilter(String titleHintFilter) {
        this.titleHintFilter = titleHintFilter;
    }

    public void setTitleDialog(String titleDialog) {
        this.titleDialog = titleDialog;
    }

    public void setTypeDialog(DialogType typeDialog) {
        this.typeDialog = typeDialog;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (typeDialog == null)
            typeDialog = DialogType.SELECT_DEFAULT;

        handler = new Handler();
        data = (ArrayList<OptionModel>) getArguments().getSerializable("data");
        if (data == null) {
            data = new ArrayList<>();
        }
        listItemSelected = new ArrayList<>();

        isSelectedMulti = getArguments().getBoolean("isSelectedMulti", false);

        View dialoglayout = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_select_list_item, null);

        listView = dialoglayout.findViewById(R.id.lvListSelect);
        layoutEmptyList = dialoglayout.findViewById(R.id.layoutEmptyList);
        loadingView = dialoglayout.findViewById(R.id.loadingView);
        layoutRootView = dialoglayout.findViewById(R.id.layoutRootView);

        edtSearch = dialoglayout.findViewById(R.id.edtSearch);

        layoutRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppUtils.hideKeyBoard(edtSearch);
                return false;
            }
        });

        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                AppUtils.hideKeyBoard(edtSearch);
                return false;
            }
        });
        if (showFilter)
            edtSearch.setVisibility(View.VISIBLE);
        else
            edtSearch.setVisibility(View.GONE);

        edtSearch.setHint(titleHintFilter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (typeDialog == DialogType.FILTER_SELECT_PRODUCT || typeDialog == DialogType.FILTER_SELECT_CUSTOMER || typeDialog == DialogType.FILTER_SELECT_TASK) {
                    if (timer != null)
                        timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (typeDialog == DialogType.SELECT_DEFAULT) {
                    if (adapter != null)
                        adapter.getFilter().filter(s.toString());
                } else {

                    if (s.length() >= 1) {

                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                String key = s.toString().trim();
                                AppUtils.hideKeyBoard(edtSearch);
                                if (typeDialog == DialogType.FILTER_SELECT_PRODUCT)
                                    requestGetListProduct(key);
                                else if (typeDialog == DialogType.FILTER_SELECT_CUSTOMER)
                                    requestGetListCustomer(key);
                                else if (typeDialog == DialogType.FILTER_SELECT_TASK)
                                    requestGetListTask(key);
                                else if (typeDialog == DialogType.FILTER_SELECT_EMPLOYEE)
                                    requestGetListEmployee(key);
                            }

                        }, DELAY);
                    } else {
                        if (typeDialog == DialogType.FILTER_SELECT_PRODUCT)
                            requestGetListProduct("");
                        else if (typeDialog == DialogType.FILTER_SELECT_CUSTOMER)
                            requestGetListCustomer("");
                        else if (typeDialog == DialogType.FILTER_SELECT_TASK)
                            requestGetListTask("");
                        else if (typeDialog == DialogType.FILTER_SELECT_EMPLOYEE)
                            requestGetListEmployee("");
                    }
                }
            }
        });

        setupListData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);

        builder.setTitle(titleDialog);

        builder.setNegativeButton(getString(R.string.huybo), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                typeDialog = null;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isSelectedMulti) {

                    if (listItemSelected != null) {
                        if (listener != null) {
                            listener.onListItemOptionSelected(listItemSelected);
                        }
                    }
                } else {
                    if (itemSelected != null) {
                        if (listener != null) {
                            listener.onItemOptionSelected(itemSelected);
                        }
                    }
                }
                typeDialog = null;
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                checkStatuButton();
            }
        }, 200);
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (typeDialog == DialogType.FILTER_SELECT_TASK)
            requestGetListTask("");


        super.onActivityCreated(savedInstanceState);
    }

    private void resetItemSelected() {

        int length = data.size();

        for (int i = 0; i < length; i++) {
            data.get(i).setSelected(false);

        }
    }


    @Override
    protected boolean isListenerOptional() {
        return true;
    }

    private void checkStatuButton() {
        if (isSelectedMulti) {
            if (listItemSelected != null && listItemSelected.size() > 0)
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(true);
            else
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(false);
        } else {
            if (itemSelected != null)
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(true);
            else
                ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON1).setEnabled(false);
        }
    }

    public enum DialogType {
        SELECT_DEFAULT,
        FILTER_SELECT_PRODUCT,
        FILTER_SELECT_EMPLOYEE,
        FILTER_SELECT_TASK,
        FILTER_SELECT_CUSTOMER
    }

    private void showLoadingView() {
        if (loadingView != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    loadingView.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    private void hideLoadingView() {
        if (loadingView != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingView.setVisibility(View.GONE);
                }
            });
        }

    }

    private void showEmptyList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutEmptyList.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

    }

    private void hideEmptyList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutEmptyList.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });

    }

    private void requestGetListCustomer(String string_filter) {
        data.clear();

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
        if (TextUtils.isEmpty(string_filter)) {
            showEmptyList();
            return;
        }

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getString(R.string.error_connect_internet), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        showLoadingView();

//        ListCustomerRequest.ApiParams params = new ListCustomerRequest.ApiParams();
//
//        if (!TextUtils.isEmpty(string_filter)) {
//            params.filter = string_filter;
//        }
//
//        params.page = String.valueOf(1);
//        params.limit = "20";
//
//        AppProvider.getApiManagement().call(ListCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<CustomerModel> result) {
//                hideLoadingView();
//                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
//
//                    if (result.getData() != null && result.getData().length > 0) {
//                        hideEmptyList();
//
//                        for (CustomerModel itemCustomer : result.getData()) {
//                            OptionModel itemModel = new OptionModel();
//                            itemModel.setTitle(itemCustomer.getLast_name() + " " + itemCustomer.getFirst_name());
//                            itemModel.setOptionType(OptionModel.OptionType.CUSTOM_TYPE_CUSTOMER);
//                            itemModel.setDtaCustom(itemCustomer);
//
//                            data.add(itemModel);
//                        }
//
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                setupListData();
//                            }
//                        });
//
//
//                    } else {
//                        showEmptyList();
//                    }
//
//                } else {
//                    showEmptyList();
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//        });
    }

    private void requestGetListProduct(String string_filter) {

        clearDataList();

        if (TextUtils.isEmpty(string_filter)) {
            showEmptyList();
            return;
        }

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getString(R.string.error_connect_internet), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        showLoadingView();

//        RequestGetListProduct.ApiParams params = new RequestGetListProduct.ApiParams();
//
//        params.detect = "list_product";
//        params.type_manager = "product_manager";
//        params.product = string_filter;
//        params.get_single_item = "Y";
//
//        params.page = String.valueOf("1");
//        params.limit = "20";
//
//        AppProvider.getApiManagement().call(RequestGetListProduct.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductNinoModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<ProductNinoModel> result) {
//                hideLoadingView();
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    if (result.getData() != null && result.getData().length > 0) {
//                        hideEmptyList();
//
//                        for (ProductNinoModel itemProduct : result.getData()) {
//                            OptionModel itemModel = new OptionModel();
//                            itemModel.setImage(itemProduct.getProduct_avatar());
//                            itemModel.setTitle(itemProduct.getProduct_title());
//                            itemModel.setOptionType(OptionModel.OptionType.CUSTOM_TYPE_PRODUCT);
//                            itemModel.setDtaCustom(itemProduct);
//
//                            data.add(itemModel);
//                        }
//
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                setupListData();
//                            }
//                        });
//
//
//                    } else {
//                        showEmptyList();
//                    }
//
//                } else {
//                    showEmptyList();
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//        });
    }

    private void requestGetListTask(String string_filter) {

        clearDataList();

//        if (TextUtils.isEmpty(string_filter)) {
//            showEmptyList();
//            return;
//        }

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getString(R.string.error_connect_internet), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        showLoadingView();

//        RequestGetListTask.ApiParam params = new RequestGetListTask.ApiParam();
//
//        if (!TextUtils.isEmpty(string_filter)) {
//            params.filter = string_filter;
//        }
//        if (!TextUtils.isEmpty(id_employee_tracking)) {
//            params.id_employee = id_employee_tracking;
//        }
//        params.page = String.valueOf(1);
//        params.limit = "20";
//
//        AppProvider.getApiManagement().call(RequestGetListTask.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TaskModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<TaskModel> result) {
//                hideLoadingView();
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    if (result.getData() != null && result.getData().length > 0) {
//                        hideEmptyList();
//
//                        for (TaskModel itemTask : result.getData()) {
//                            OptionModel itemModel = new OptionModel();
//                            itemModel.setTitle(itemTask.getJob_tracking());
//                            itemModel.setOptionType(OptionModel.OptionType.CUSTOM_TYPE_TASK);
//                            itemModel.setDtaCustom(itemTask);
//
//                            data.add(itemModel);
//                        }
//
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                setupListData();
//                            }
//                        });
//
//
//                    } else {
//                        showEmptyList();
//                    }
//
//                } else {
//                    showEmptyList();
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//        });
    }

    private void requestGetListEmployee(String string_filter) {
        clearDataList();

        if (TextUtils.isEmpty(string_filter)) {
            showEmptyList();
            return;
        }

        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), getString(R.string.error_connect_internet), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }

        showLoadingView();

//        ListEmployeeRequest.ApiParams params = new ListEmployeeRequest.ApiParams();
//
//        if (!TextUtils.isEmpty(string_filter)) {
//            params.filter = string_filter;
//        }
//
//        params.page = String.valueOf("1");
//        params.limit = "20";
//
//        AppProvider.getApiManagement().call(ListEmployeeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<AccountModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<AccountModel> result) {
//                hideLoadingView();
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    if (result.getData() != null && result.getData().length > 0) {
//                        hideEmptyList();
//
//                        for (AccountModel itemEmployee : result.getData()) {
//                            OptionModel itemModel = new OptionModel();
//                            itemModel.setTitle(itemEmployee.getFull_name());
//                            itemModel.setOptionType(OptionModel.OptionType.CUSTOM_TYPE_EMPLOYEE);
//                            itemModel.setDtaCustom(itemEmployee);
//
//                            data.add(itemModel);
//                        }
//
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                setupListData();
//                            }
//                        });
//
//
//                    } else {
//                        showEmptyList();
//                    }
//
//                } else {
//                    showEmptyList();
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                hideLoadingView();
//                showEmptyList();
//            }
//        });
    }

    private void clearDataList() {
        data.clear();

        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }

    public void setId_employee_tracking(String id_employee_tracking) {
        this.id_employee_tracking = id_employee_tracking;
    }

    private void setupListData() {
        adapter = new OptionSelectAdapter(getContext(), data, null);

        adapter.setListener(new OptionSelectAdapter.OptionSelectAdapterListener() {
            @Override
            public void onOptionItemSelected(OptionModel item, View v, int pos) {

            }

            @Override
            public void onItemSelected(OptionModel item, int pos) {

                if (!isSelectedMulti) {
                    resetItemSelected();
                    data.get(pos).setSelected(true);
                    itemSelected = item;
                } else {
                    data.get(pos).setSelected(true);
                    if (!listItemSelected.contains(item)) {
                        listItemSelected.add(item);
                    }
                }

                checkStatuButton();

                adapter.replaceAll(data);
                adapter.notifyDataSetChanged();
                AppUtils.hideKeyBoard(edtSearch);
            }

            @Override
            public void onRemoveItemSelected(OptionModel item, int pos) {
                data.get(pos).setSelected(false);

                if (!isSelectedMulti) {
                    itemSelected = null;
                } else {
                    if (listItemSelected != null && listItemSelected.contains(item)) {
                        listItemSelected.remove(item);
                    }
                }
                checkStatuButton();

                adapter.replaceAll(data);
                adapter.notifyDataSetChanged();
                AppUtils.hideKeyBoard(edtSearch);
            }
        });


        listView.setAdapter(adapter);
    }

    /*
     private void showDialogSelect() {
     if (lisIndustrial == null) {
     Toast.makeText(getContext(), "Danh sách khu công nghiệp trống", Toast.LENGTH_SHORT).show();
     return;
     }

     ArrayList<OptionModel> list = new ArrayList<>();

     for (IndustrialModel item : lisIndustrial) {
     OptionModel optionModel = new OptionModel();

     optionModel.setTitle(item.getZone_industrial());
     optionModel.setOptionType(OptionModel.OptionType.DEFAULT);
     optionModel.setDtaCustom(item);
     list.add(optionModel);
     }

     OptionSelectDialog optionDialog = OptionSelectDialog.createNewInstance(list, new OptionSelectDialog.OptionSelectDialogListener() {
    @Override public void onItemOptionSelected(OptionModel itemSelected) {

    if (itemSelected == null) {
    return;
    }
    IndustrialModel itemIndustrialSelected = (IndustrialModel) itemSelected.getDtaCustom();
    industrialSelected = itemIndustrialSelected;
    ui.tvIndustrialSelect.setText(itemIndustrialSelected.getZone_industrial());
    }

    @Override public void onListItemOptionSelected(ArrayList<OptionModel> listItem) {
    for (OptionModel item : listItem) {
    MyLog.e("OptionDialog", "ListItemSelected: " + item.getTitle() + "\n");
    }
    }
    }, false);
     optionDialog.show(activity.getSupportFragmentManager(), "OptionSelectDialog");
     }
     */
}

