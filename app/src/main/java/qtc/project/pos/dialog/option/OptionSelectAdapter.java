package qtc.project.pos.dialog.option;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import b.laixuantam.myaarlibrary.widgets.superadapter.IMulItemViewType;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;

public class OptionSelectAdapter extends SuperAdapter<OptionModel> {

    private final ArrayList<OptionModel> listData = new ArrayList<>();
    private final ArrayList<OptionModel> listDataBackup = new ArrayList<>();
    private final OptionSelectAdapterFilter filter;
    private OptionSelectAdapterListener listener;

    public OptionSelectAdapter(Context context, List<OptionModel> items, IMulItemViewType<OptionModel> multiItemViewType) {
        super(context, items, multiItemViewType);
        listDataBackup.addAll(items);
        filter = new OptionSelectAdapterFilter();
    }

    public interface OptionSelectAdapterListener {

        void onOptionItemSelected(OptionModel item, View v, int pos);

        void onItemSelected(OptionModel item, int pos);

        void onRemoveItemSelected(OptionModel item, int pos);
    }

    public void setListener(OptionSelectAdapterListener listener) {
        this.listener = listener;
    }

    public ArrayList<OptionModel> getListData() {
        return listData;
    }

    public ArrayList<OptionModel> getListDataBackup() {
        return listDataBackup;
    }

    public OptionSelectAdapterFilter getFilter() {
        return filter;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, final OptionModel item) {

        switch (viewType) {
            case 1:
//                View rLayoutItem = holder.findViewById(R.id.rLayoutItem);
//
//                TextView tvProductName = holder.findViewById(R.id.tvProductName);
//                TextView tvProductCode = holder.findViewById(R.id.tvProductCode);
//                TextView tvBarCode = holder.findViewById(R.id.tvBarCode);
//                TextView tvProductStock = holder.findViewById(R.id.tvProductStock);
//
//                ImageView imgProduct = holder.findViewById(R.id.imgProduct);
//                ImageView selected_layer = holder.findViewById(R.id.selected_layer);
//                View loading_product_image = holder.findViewById(R.id.loading_product_image);
//
//                ProductNinoModel itemProduct = (ProductNinoModel) item.getDtaCustom();
//
//                tvProductName.setText(itemProduct.getProduct_title());
//                tvProductCode.setText(itemProduct.getProduct_code());
//
//                if (!TextUtils.isEmpty(itemProduct.getProduct_price())) {
//                    String price = NumericFormater.formatCurrency(Double.valueOf(itemProduct.getProduct_price()));
//                    tvBarCode.setText(price + "đ");
//                } else {
//                    tvBarCode.setText("---");
//                }
//
//                tvProductStock.setText("Tồn kho: " + itemProduct.getProduct_stock());
//
//                String img = Consts.HOST_API + itemProduct.getProduct_avatar();
//                AppProvider.getImageHelper().displayImage(img, imgProduct, loading_product_image, R.drawable.no_image_full);
//
//                imgProduct.setAlpha(item.isSelected() ? 0.5f : 1f);
//                selected_layer.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
//                rLayoutItem.setBackgroundColor(item.isSelected() ? ContextCompat.getColor(getContext(), R.color.light_gray) : Color.TRANSPARENT);
//
//                rLayoutItem.setOnClickListener(new ScaleTouchListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            if (item.isSelected()) {
//                                listener.onRemoveItemSelected(item, layoutPosition);
//                            } else
//                                listener.onItemSelected(item, layoutPosition);
//                        }
//                    }
//                });

                break;

            case 0:

                View rLayoutItemDefault = holder.findViewById(R.id.rLayoutItem);
                View imvStatusSelected = holder.findViewById(R.id.imvStatusSelected);
                TextView tvTitle = holder.findViewById(R.id.tvTitle);
                ImageView image = holder.findViewById(R.id.image);

                tvTitle.setText(item.getTitle());

                if (!TextUtils.isEmpty(item.getImage())) {
                    image.setVisibility(View.VISIBLE);
                    String imgProductLink = Consts.HOST_API + item.getImage();
                    AppProvider.getImageHelper().displayImage(imgProductLink, image, null, R.drawable.no_image_full);
                } else {
                    image.setVisibility(View.GONE);
                }

                imvStatusSelected.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);

                rLayoutItemDefault.setOnClickListener(new ScaleTouchListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            if (item.isSelected()) {
                                listener.onRemoveItemSelected(item, layoutPosition);
                            } else {
                                listener.onItemSelected(item, layoutPosition);

                            }
                        }
                    }
                });
                break;

//            case 2:
//
//                TaskModel itemTask = (TaskModel) item.getDtaCustom();
//
//                View rowItem = holder.findViewById(R.id.rowItem);
//                TextView tvTaskName = holder.findViewById(R.id.tvTaskName);
//                TextView tvTaskDateBegin = holder.findViewById(R.id.tvTaskDateBegin);
//                TextView tvTaskDateEnd = holder.findViewById(R.id.tvTaskDateEnd);
//                TextView tvTaskTimeCheck = holder.findViewById(R.id.tvTaskTimeCheck);
//                TextView tvTaskStatus = holder.findViewById(R.id.tvTaskStatus);
//                View selected_layer_task = holder.findViewById(R.id.selected_layer);
//
//                tvTaskName.setText(itemTask.getJob_tracking());
//                long time_duration_check = 0;
//                if (!TextUtils.isEmpty(itemTask.getTime_checkpoint())) {
//                    time_duration_check = Long.valueOf(itemTask.getTime_checkpoint());
//                }
//
//                @SuppressLint("DefaultLocale") String hms = String.format("%2d%s",
//                        TimeUnit.SECONDS.toMinutes(time_duration_check) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(time_duration_check)),
//                        " phút");
//
//                tvTaskTimeCheck.setText(hms);
//
//                if (!TextUtils.isEmpty(itemTask.getJob_begin())) {
//                    String[] arrDateBegin = itemTask.getJob_begin().split(" ");
//                    if (arrDateBegin != null && arrDateBegin.length > 1) {
//                        String dateTimeServer = arrDateBegin[0];
//                        String dateNice = ConvertDate.convertDateInput(dateTimeServer, "dd/MM/yyyy");
//                        tvTaskDateBegin.setText(dateNice + "," + arrDateBegin[1]);
//                    }
//                }
//
//                if (!TextUtils.isEmpty(itemTask.getJob_end())) {
//                    String[] arrDateEnd = itemTask.getJob_end().split(" ");
//                    if (arrDateEnd != null && arrDateEnd.length > 1) {
//                        String dateTimeServer = arrDateEnd[0];
//                        String dateNice = ConvertDate.convertDateInput(dateTimeServer, "dd/MM/yyyy");
//                        tvTaskDateEnd.setText(dateNice + "," + arrDateEnd[1]);
//                    }
//                }
//
//                switch (itemTask.getStatus_job()) {
//                    case "start":
//                        tvTaskStatus.setText("Chưa bắt đầu");
//                        break;
//
//                    case "processing":
//                        tvTaskStatus.setText("Đang tiến hành");
//                        break;
//
//                    case "end":
//                        tvTaskStatus.setText("Kết thúc");
//                        break;
//                }
//
//                selected_layer_task.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
//                rowItem.setOnClickListener(new ScaleTouchListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            if (item.isSelected()) {
//                                listener.onRemoveItemSelected(item, layoutPosition);
//                            } else
//                                listener.onItemSelected(item, layoutPosition);
//                        }
//                    }
//                });
//
//                break;

//            case 3:
//                AccountModel itemAccount = (AccountModel) item.getDtaCustom();
//                View rowItemEmployee = holder.findViewById(R.id.rowItem);
//
//                TextView tvEmployeeName = holder.findViewById(R.id.tvEmployeeName);
//                TextView tvEmployeeRole = holder.findViewById(R.id.tvEmployeeRole);
//                View selected_layer_employee = holder.findViewById(R.id.selected_layer);
//
//                tvEmployeeName.setText(itemAccount.getFull_name());
//
//                if (!TextUtils.isEmpty(itemAccount.getLevel())) {
//                    if (itemAccount.getLevel().equalsIgnoreCase("employee"))
//                        tvEmployeeRole.setText("Nhân viên");
//                    if (itemAccount.getLevel().equalsIgnoreCase("admin"))
//                        tvEmployeeRole.setText("Quản lý");
//                }
//
//                selected_layer_employee.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
//                rowItemEmployee.setOnClickListener(new ScaleTouchListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            if (item.isSelected()) {
//                                listener.onRemoveItemSelected(item, layoutPosition);
//                            } else
//                                listener.onItemSelected(item, layoutPosition);
//                        }
//                    }
//                });
//
//                break;
//
//            case 4:
//                CustomerModel customerModel = (CustomerModel) item.getDtaCustom();
//                View rowItemCustomer = holder.findViewById(R.id.rowItem);
//
//                TextView tvCustomerName = holder.findViewById(R.id.tvCustomerName);
//                TextView tvCustomerPhone = holder.findViewById(R.id.tvCustomerPhone);
//                ImageView imgCustomerAvata = holder.findViewById(R.id.imgCustomerAvata);
//                View selected_layer_customer = holder.findViewById(R.id.selected_layer);
//                View imvShowDetail = holder.findViewById(R.id.imvShowDetail);
//                imvShowDetail.setVisibility(View.GONE);
//
//                tvCustomerName.setText(customerModel.getLast_name() + " " + customerModel.getFirst_name());
//                tvCustomerPhone.setText(customerModel.getPhone_number());
//
//                String imgCustomer = Consts.HOST_API + item.getImage();
//                AppProvider.getImageHelper().displayImage(imgCustomer, imgCustomerAvata, null, R.drawable.ic_user_default,true);
//
//                selected_layer_customer.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);
//                rowItemCustomer.setOnClickListener(new ScaleTouchListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            if (item.isSelected()) {
//                                listener.onRemoveItemSelected(item, layoutPosition);
//                            } else
//                                listener.onItemSelected(item, layoutPosition);
//                        }
//                    }
//                });
//
//                break;
        }
    }


    @Override
    protected IMulItemViewType<OptionModel> offerMultiItemViewType() {
        return new IMulItemViewType<OptionModel>() {
            @Override
            public int getViewTypeCount() {
                return 5;
            }

            @Override
            public int getItemViewType(int position, OptionModel model) {
                if (model.getOptionType() == OptionModel.OptionType.CUSTOM_TYPE_PRODUCT) {
                    return 1;
                } else if (model.getOptionType() == OptionModel.OptionType.CUSTOM_TYPE_TASK) {
                    return 2;
                } else if (model.getOptionType() == OptionModel.OptionType.CUSTOM_TYPE_EMPLOYEE) {
                    return 3;
                } else if (model.getOptionType() == OptionModel.OptionType.CUSTOM_TYPE_CUSTOMER) {
                    return 4;
                }
                return 0;
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 1) {
                    return R.layout.row_item_product;
                }
                return R.layout.row_item_option_default;
            }
        };
    }


    public class OptionSelectAdapterFilter extends Filter {
        OptionSelectAdapterFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString;
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listData != null && listData.size() > 0) {
                    int count = listData.size();
                    List<OptionModel> tempItems = new ArrayList<>();

                    // search exactly
                    for (int i = 0; i < count; i++) {
                        String name = listData.get(i).getTitle().toLowerCase();
                        if (name.contains(filterString)) {
                            tempItems.add(listData.get(i));
                        }
                    }
                    // search for no accent if no exactly result
//                    filterString = AccentRemove.removeAccent(filterString);
//                    if (tempItems.size() == 0) {
//                        for (int i = 0; i < count; i++) {
//                            String name = AccentRemove.removeAccent(listData.get(i).getTenSanPham().toLowerCase());
//                            if (name.contains(filterString)) {
//                                tempItems.add(listData.get(i));
//                            }
//                        }
//                    }
                    results.values = tempItems;
                    results.count = tempItems.size();
                    return results;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listData.clear();
            if (results != null) {
                List<OptionModel> listProductResult = (List<OptionModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    listData.addAll(listProductResult);
                }
            } else {
                listData.addAll(listDataBackup);
            }

            replaceAll(listData);
        }
    }

}

