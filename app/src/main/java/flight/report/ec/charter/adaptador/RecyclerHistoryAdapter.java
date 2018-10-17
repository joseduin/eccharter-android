package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.R;
import flight.report.ec.charter.ReportActivity;
import flight.report.ec.charter.modelo.Report;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.StringUtil;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.HistoryViewHolder> {
    private List<Report> reports;
    private List<Report> reportsFilter;
    private Context context;
    private CallbackInterface mCallback;

    public interface CallbackInterface {
        void onHandleSelection(int position, String metohd);
    }

    public RecyclerHistoryAdapter(List<Report> reports, Context context) {
        this.reports = reports;
        this.reportsFilter = reports;
        this.context = context;
        this.mCallback = (CallbackInterface) context;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_history, parent, false);
        HistoryViewHolder pvh = new HistoryViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final Report report = reports.get(position);

        holder.sentcontainer.setVisibility(View.GONE);
        holder.sent_server.setVisibility(View.GONE);
        holder.sent_mail.setVisibility(View.GONE);
        holder.sent_mail_ope.setVisibility(View.GONE);

        holder.history_customer.setText( StringUtil.nullTranform( report.getCustomer() ) );
        holder.history_aircraft.setText( StringUtil.nullTranform( report.getAircraft() ) );
        holder.history_capitan.setText( StringUtil.nullTranform( report.getCapitan() ) );
        holder.history_copilot.setText( StringUtil.nullTranform( report.getCopilot() ) );
        holder.history_date.setText( report.getDate() );

        if (report.isSentMail() || report.isSentMailOpe() || report.isSentServer()) {
            holder.sentcontainer.setVisibility(View.VISIBLE);
            if (report.isSentMail()) {
                holder.sent_mail.setVisibility(View.VISIBLE);
            }
            if (report.isSentMailOpe()) {
                holder.sent_mail_ope.setVisibility(View.VISIBLE);
            }
            if (report.isSentServer()) {
                holder.sent_server.setVisibility(View.VISIBLE);
            }
        }

        if (!report.isSend()) {
            holder.history_send.setVisibility(View.VISIBLE);
        } else {
            holder.history_send.setVisibility(View.GONE);
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReport(report);
            }
        });
        holder.history_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallback != null){
                    mCallback.onHandleSelection(report.getId(), "");
                }
            }
        });

    }

    private void goToReport(Report report) {
        IrA.vista(context, ReportActivity.class, report.getId());
    }

    public void updateList(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }

    public int filter(String charText) {
        this.reports = new ArrayList<>();
        if (charText.isEmpty()) {
            this.reports.addAll(this.reportsFilter);
        } else {
            for (Report report : this.reportsFilter) {
                if (StringUtil.contains(report.getCustomer(), charText)
                        || StringUtil.contains(report.getAircraft(), charText)
                        || StringUtil.contains(report.getCapitan(), charText)
                        || StringUtil.contains(report.getCopilot(), charText)) {
                    reports.add(report);
                }
            }
        }
        notifyDataSetChanged();
        return reports.size();
    }

    public List<Report> getHistory() {
        return this.reportsFilter;
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView history_customer, history_aircraft, history_capitan, history_copilot, history_date, history_send, sent_mail, sent_server, sent_mail_ope;
        private ImageButton history_btn_delete;
        private LinearLayout sentcontainer;
        private View v;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            history_send = itemView.findViewById(R.id.history_send);
            history_customer = itemView.findViewById(R.id.history_customer);
            history_aircraft = itemView.findViewById(R.id.history_aircraft);
            history_capitan = itemView.findViewById(R.id.history_capitan);
            history_copilot = itemView.findViewById(R.id.history_copilot);
            history_date = itemView.findViewById(R.id.history_date);
            history_btn_delete = itemView.findViewById(R.id.history_btn_delete);
            sentcontainer = itemView.findViewById(R.id.sentcontainer);
            sent_mail = itemView.findViewById(R.id.sent_mail);
            sent_mail_ope = itemView.findViewById(R.id.sent_mail_ope);
            sent_server = itemView.findViewById(R.id.sent_server);
            v = itemView;
        }
    }

}
