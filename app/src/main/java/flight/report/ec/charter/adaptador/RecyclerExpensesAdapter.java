package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.Number;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerExpensesAdapter extends RecyclerView.Adapter<RecyclerExpensesAdapter.ExpensesViewHolder> {

    private List<Expenses> expenses;
    private Context context;
    private BdContructor bd;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
        void onHandleSelection(int position, String metohd);
    }

    public RecyclerExpensesAdapter(List<Expenses> expenses, Fragment fragment) {
        this.expenses = expenses;
        this.context = fragment.getContext();
        this.bd = new BdContructor(context);
        this.mCallback = (CallbackInterface) fragment;
    }

    @Override
    public ExpensesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_expenses, parent, false);
        ExpensesViewHolder pvh = new ExpensesViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ExpensesViewHolder holder, int position) {
        final Expenses expense = expenses.get(position);

        holder.expenses_total.setText(Number.decimalFormat(expense.getTotal(), 2) + " " + expense.getCurrency());
        holder.expenses_description.setText(expense.getDescription());
        holder.expenses_photo_path.setText(Convert.photoFormat(expense.getPhoto()));

        if (holder.expenses_photo_path.getText().toString().isEmpty()) {
            holder.expenses_btn_photo.setVisibility(View.GONE);

        }  else {
            holder.expenses_btn_photo.setVisibility(View.VISIBLE);
            holder.expenses_photo_path.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(expense);
                }
            });
            holder.expenses_btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onPhotoPreview(expense);
                }
            });
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExpense(expense, "edit");
            }
        });
        holder.expenses_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExpense(expense, "delete");
            }
        });
    }

    private void goToExpense(Expenses expense, String metodo) {
        if(mCallback != null){
            mCallback.onHandleSelection(expense.getId(), metodo);
        }
    }

    private void onPhotoPreview(Expenses expense) {
        Uri photo = Uri.parse(expense.getPhoto());
        Mensaje.imagePreview(context, photo);
    }

    public void updateList(List<Expenses> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static class ExpensesViewHolder extends RecyclerView.ViewHolder {

        private TextView expenses_photo_path, expenses_total, expenses_description;
        private ImageButton expenses_btn_photo, expenses_btn_delete;
        private View v;

        public ExpensesViewHolder(View itemView) {
            super(itemView);
            expenses_total = itemView.findViewById(R.id.expenses_total);
            expenses_description = itemView.findViewById(R.id.expenses_description);
            expenses_photo_path = itemView.findViewById(R.id.expenses_photo_path);
            expenses_btn_photo = itemView.findViewById(R.id.expenses_btn_photo);
            expenses_btn_delete = itemView.findViewById(R.id.expenses_btn_delete);

            v = itemView;
        }
    }

}
