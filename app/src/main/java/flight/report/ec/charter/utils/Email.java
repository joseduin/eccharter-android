package flight.report.ec.charter.utils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;

/**
 * Created by Jose on 23/1/2018.
 */
/**
 * Clase generadora del contenido del EMAIL
 **/
public final class Email {
    public static String EMAIL = "1flightreport@gmail.com";
    public static String EMAIL_REPORT = "ecchmant@gmail.com";
    private static String SPLIT = " - ";

    /**
     * Titulo de reportes de vuelos
     **/
    public static String titleNormalEmail(Report report) {
        return report.getCustomer() + SPLIT +
                report.getCapitan() + SPLIT +
                report.getDate() + SPLIT +
                report.getRoute();
    }

    /**
     * Titulo de reportes de mantenimiento
     **/
    public static String titleReportEmail(Report report) {
        return report.getAircraft() + SPLIT +
                report.getCapitan() + SPLIT +
                report.getDate() + SPLIT +
                report.getRoute();
    }

    /**
     * Contenido del reporte
     **/
    public static String basic(Report report, List<String> filePaths, int cantImg) {
        String emailText = "";
        emailText = emailText.concat("CUSTOMER:                   " + StringUtil.nullTranform(report.getCustomer()) + "\n");
        emailText = emailText.concat("PASSENGERS:               " + StringUtil.nullTranform(report.getPassengers()) + "\n");
        if (report.getPassengers_photo() != null) {
            emailText = emailText.concat("PASSENGERS PHOTO: #" + ++cantImg + "\n");
            filePaths.add(report.getPassengers_photo());
        }
        emailText = emailText.concat("AIRCRAFT:                      " + StringUtil.nullTranform(report.getAircraft()) + "\n");
        emailText = emailText.concat("CAPITAN:                        " + StringUtil.nullTranform(report.getCapitan()) + "\n");
        emailText = emailText.concat("COPILOT:                        " + StringUtil.nullTranform(report.getCopilot()) + "\n");
        if (report.isCockpit())
            emailText = emailText.concat("COCKPIT SPLITTED \n");
        emailText = emailText.concat("DATE:                              " + report.getDate().toUpperCase() + "\n");
        emailText = emailText.concat("ROUTE:                           " + StringUtil.nullTranform(report.getRoute()) + "\n");
        emailText = emailText.concat("GPS FLIGHT TIME:        " + report.getGps_flight_time() + "\n");
        emailText = emailText.concat("HOUR INITIAL:               " + report.getHour_initial() + "\n");
        emailText = emailText.concat("HOUR FINAL:                 " + report.getHour_final() + "\n");
        emailText = emailText.concat("LOG TIME:                      " + Number.decimalFormat( report.getLong_time(), 1) + "\n");

        return emailText;
    }

    /**
     * Buscamos los gatos asociados al reporte
     **/
    public static String expense(ArrayList<Expenses> expenses, List<String> filePaths, int cantImg) {
        String emailText = "";
        emailText = emailText.concat("\n" + "EXPENSES" + "\n");
        for (Expenses expense : expenses) {
            emailText = emailText.concat("   TOTAL:                        " + expense.getTotal() + " " + expense.getCurrency() + "\n");
            emailText = emailText.concat("   DESCRIPTION:           " + expense.getDescription() + "\n");
            if (expense.getPhoto() != null) {
                emailText = emailText.concat("   PHOTO:                       #" + ++cantImg + "\n");
                filePaths.add(expense.getPhoto());
            }
            emailText = emailText.concat("   --------------------------- \n");
        }
        return emailText;
    }

    /**
     * Buscaos los mantenimientos asociados al reporte
     **/
    public static String aircraft(ArrayList<AircraftReport> aircrafts, Report report, List<String> filePaths, int cantImg) {
        String emailText = "";
        emailText = emailText.concat("\n" + "AIRCRAFT REPORT" + "\n");

        if (!aircrafts.isEmpty()) {
            for (AircraftReport aircraf : aircrafts) {
                emailText = emailText.concat("   DESCRIPTION:           " + aircraf.getDescription() + "\n");
                if (aircraf.getPhoto() != null) {
                    emailText = emailText.concat("   PHOTO:                       #" + ++cantImg + "\n");
                    filePaths.add(aircraf.getPhoto());
                }
                emailText = emailText.concat("   --------------------------- \n");
            }
        }

        emailText = emailText.concat("\n");
        if (report.getEngine1() != 0.0)
            emailText = emailText.concat("ENGINE 1:                      " + Number.decimalFormat( report.getEngine1(), 2) + " " + report.getComboEngine1() + "\n");

        if (report.getEngine2() != 0.0)
            emailText = emailText.concat("ENGINE 2:                      " + Number.decimalFormat( report.getEngine2(), 2) + " " + report.getComboEngine2() + "\n");

        return emailText;
    }

    /**
     * Buscamos los planes de vuelos asociados al reporte
     **/
    public static String plan(ArrayList<Plan> plans, List<String> filePaths, int cantImg) {
        String emailText = "";
        emailText = emailText.concat("\n" + "FLIGHT PLANS" + "\n");
        for (Plan plan : plans) {
            emailText = emailText.concat("   ROUTE:                      " + plan.getDescription() + "\n");
            if (plan.getPhoto() != null) {
                emailText = emailText.concat("   PHOTO:                       #" + ++cantImg + "\n");
                filePaths.add(plan.getPhoto());
            }
            emailText = emailText.concat("   --------------------------- \n");
        }
        return emailText;
    }

    /**
     * Preparamos el reporte para ser enviado por alguna aplicacion seleccionada por el usuario
     **/
    public static void send(Fragment fragment, String emailTo, String subject, String emailText,
                            List<String> filePaths, boolean progress, ProgressDialog progressDialog) {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailText);

        if (filePaths.isEmpty()) {
            //Send email without photo attached
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("plain/text");
        } else if (filePaths.size() == 1) {
            //Send email with ONE photo attached
            intent.setAction(Intent.ACTION_SEND);

            String path = Convert.saveScaledPhotoToFile(filePaths.get(0), fragment.getActivity());
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
            intent.setType("image/*");
        } else {
            //Send email with MULTI photo attached
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            ArrayList<Uri> uris = new ArrayList<>();
            for (String u : filePaths) {
                String path = Convert.saveScaledPhotoToFile(u, fragment.getActivity());
                uris.add(Uri.parse(path));
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            intent.setType("image/*");
        }

        if (progress)
            progressDialog.dismiss();
        fragment.startActivityForResult(Intent.createChooser(intent, "Choice App to send email:"), 0);
    }

}
