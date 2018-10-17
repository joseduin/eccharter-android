package flight.report.ec.charter.bd;

import android.content.Context;

import flight.report.ec.charter.bd.bridge.*;
import flight.report.ec.charter.task.DataBase;

/**
 * Created by Jose on 22/1/2018.
 */

public class BdContructor {
    private Context context;
    private DataBase dataBase;

    public Aircraft aircraft;
    public Report report;
    public Expense expense;
    public Maintenance maintenance;
    public Plan plan;
    public List list;
    public Send send;
    public Document document;
    public Component component;
    public Chat chat;

    public BdContructor(Context context) {
        this.context = context;
        this.dataBase = new DataBase(context);

        this.report = new Report();
        this.aircraft = new Aircraft();
        this.expense = new Expense();
        this.maintenance = new Maintenance();
        this.plan = new Plan();
        this.list = new List();
        this.send = new Send();
        this.document = new Document();
        this.component = new Component();
        this.chat = new Chat();
    }

    public void initDataBase() {
        if (list.listaCurrency().isEmpty()) {
            dataBase.init();
        }
    }

    public class Aircraft extends AircraftBridge {
        public Aircraft() {
            super(context);
        }
    }

    public class Report extends ReportBridge {
        public Report() {
            super(context);
        }
    }

    public class Expense extends ExpenseBridge {
        public Expense() {
            super(context);
        }
    }

    public class Maintenance extends MaintenanceBridge {
        public Maintenance() {
            super(context);
        }
    }

    public class Plan extends PlanBridge {
        public Plan() {
            super(context);
        }
    }

    public class List extends ListBridge {
        public List() {
            super(context);
        }
    }

    public class Send extends SendBridge {
        public Send() {
            super(context);
        }
    }

    public class Document extends DocumentBridge {
        public Document() {
            super(context);
        }
    }
    public class Component extends ComponentBridge {
        public Component() {
            super(context);
        }
    }
    public class Chat extends ChatBridge {
        public Chat() {
            super(context);
        }
    }

}