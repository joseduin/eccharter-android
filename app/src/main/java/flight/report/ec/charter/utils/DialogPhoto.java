package flight.report.ec.charter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.mindorks.paracamera.Camera;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;

import flight.report.ec.charter.GalleryActivity;
import flight.report.ec.charter.GalleryPreview;
import flight.report.ec.charter.R;
import flight.report.ec.charter.adaptador.DialogAdapter;
import flight.report.ec.charter.adaptador.DialogAlertAdapter;
import flight.report.ec.charter.adaptador.DialogComboAdapter;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.bd.ConstantesBaseDatos;
import flight.report.ec.charter.modelo.AircraftReport;
import flight.report.ec.charter.modelo.Expenses;
import flight.report.ec.charter.modelo.Plan;
import flight.report.ec.charter.modelo.Report;

/**
 * Created by Jose on 20/1/2018.
 */

public final class DialogPhoto {

    public final static int PICK_PHOTO_CODE = 1046;

    private static CallbackInterfaceDialog mCallback;
    private static CallbackInterfaceImagen iCallback;
    private static CallbackComboUpdate cCallback;

    /**
     * Callback
     */
    public interface CallbackInterfaceDialog {
        void onHandleSelectionButton(boolean b);
    }
    public interface CallbackInterfaceImagen {
        void onHandleSelectionButton(String photopath);
    }
    public interface CallbackComboUpdate {
        void onHandleComboUpdate();
    }

    public static void init(Fragment fragment, Uri photoUri) {
        mCallback = (CallbackInterfaceDialog) fragment;
        iCallback = (CallbackInterfaceImagen) fragment;
        createDialog(fragment.getActivity(), photoUri, fragment, mCallback, iCallback, false).show();
    }

    public static void init(Activity context, Uri photoUri) {
        mCallback = (CallbackInterfaceDialog) context;
        iCallback = (CallbackInterfaceImagen) context;
        createDialog(context, photoUri, null, mCallback, iCallback, false).show();
    }

    public static void init(Fragment fragment, Uri photoUri, boolean originPath) {
        mCallback = (CallbackInterfaceDialog) fragment;
        iCallback = (CallbackInterfaceImagen) fragment;
        createDialog(fragment.getActivity(), photoUri, fragment, mCallback, iCallback, originPath).show();
    }

    /**
     * Creamos un listado para escoger si tomar una foto,
     * seleccionarla de la galeria o visualizarla
     */
    private static DialogPlus createDialog(final Activity context, final Uri photoUri, final Fragment fragment, final CallbackInterfaceDialog mCallback, final CallbackInterfaceImagen iCallback, final boolean originPath) {
        OnItemClickListener OnItemClickListenerGlobal;
            OnItemClickListenerGlobal = new OnItemClickListener() {
                @Override
                public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                    Context contextImg = fragment != null ? fragment.getContext() : context;

                    // Take photo
                    if (position == 0) {
                        Image image = new Image(contextImg);

                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = image.initProvider();
                        Uri imageUri = image.fileUri(f);
                        String mCurrentPhotoPath = image.filePath(f);

                        // CALLBACK
                        iCallback.onHandleSelectionButton(mCurrentPhotoPath);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        if (fragment == null)
                            context.startActivityForResult(takePictureIntent, Camera.REQUEST_TAKE_PHOTO);
                        else
                            fragment.startActivityForResult(takePictureIntent, Camera.REQUEST_TAKE_PHOTO);
                    // Pick Img from gallery
                    } else if (position == 1) {
                        Intent intent = new Intent(contextImg, GalleryActivity.class);
                        intent.putExtra("originPath", originPath);
                        if (fragment==null)
                            context.startActivityForResult(intent, PICK_PHOTO_CODE);
                        else
                            fragment.startActivityForResult(intent, PICK_PHOTO_CODE);
                    } else if (position == 2) {

                        Intent intent = new Intent(contextImg, GalleryPreview.class);
                        intent.putExtra("path", photoUri.toString());
                        intent.putExtra("file", false);
                        if (fragment==null)
                            context.startActivity(intent);
                        else
                            fragment.startActivity(intent);
                    } else {
                        mCallback.onHandleSelectionButton(true);
                    }
                    dialog.dismiss();
                }
            };
        DialogAdapter adapter = new DialogAdapter(context, photoUri != null);
        return DialogPlus.newDialog(context)
                .setHeader(R.layout.dialog_header)
                .setAdapter(adapter)
                .setContentHolder(new ListHolder())
                .setCancelable(true)
                .setOnItemClickListener(OnItemClickListenerGlobal)
                .setExpanded(true)
                .create();
    }

    public static void comboInit(Fragment fragment, ArrayAdapter<String> adapter1, ArrayAdapter<String> adapter2, String tipe) {
        createComboAdd(fragment, adapter1, adapter2, tipe).show();
    }

    public static void comboInit(Fragment fragment, ArrayAdapter<String> adapter, String tipe) {
        createComboAdd(fragment, adapter, null, tipe).show();
    }

    /**
     * Creamos una ventana emergete para ingresar un nuevo valor al combo
     */
    private static DialogPlus createComboAdd(final Fragment fragment, final ArrayAdapter<String> adapterGlobal, final ArrayAdapter<String> adapterGlobal2, final String tipe) {
        final BdContructor bd = new BdContructor(fragment.getActivity());
        cCallback = (CallbackComboUpdate) fragment;

        DialogComboAdapter adapter = new DialogComboAdapter(fragment.getActivity(), tipe);
        return DialogPlus.newDialog(fragment.getActivity())
                .setAdapter(adapter)
                .setHeader(R.layout.dialog_combo_header)
                .setFooter(R.layout.dialog_combo_footer)
                .setContentHolder(new ViewHolder(R.layout.dialog_combo_text))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.dialog_btn_save:
                                EditText dialog_edit = (EditText) dialog.findViewById(R.id.dialog_edit);
                                String edit = dialog_edit.getText().toString().trim();

                                if (edit.equals("")) {
                                    return;
                                }
                                edit = edit.toUpperCase();
                                adapterGlobal.clear();
                                switch (tipe) {
                                    case "customer":
                                        bd.list.listaCustomerInsert(edit);
                                        adapterGlobal.addAll(bd.list.listaCustomer());
                                        break;
                                    case "aircraft":
                                        bd.list.listaAircraftInsert(edit);
                                        adapterGlobal.addAll(bd.list.listaAircraft());
                                        break;
                                    case "capitan":
                                        bd.list.listaCapitanInsert(edit);
                                        adapterGlobal.addAll(bd.list.listaCapitan());
                                        break;
                                    case "copilot":
                                        bd.list.listaCopilotInsert(edit);
                                        adapterGlobal.addAll(bd.list.listaCapitan());
                                        break;
                                }
                                adapterGlobal.notifyDataSetChanged();

                                if (adapterGlobal2 != null) {
                                    adapterGlobal2.clear();
                                    adapterGlobal2.addAll(bd.list.listaCapitan());
                                    adapterGlobal2.notifyDataSetChanged();
                                }
                                cCallback.onHandleComboUpdate();
                                dialog.dismiss();
                                break;
                            case R.id.dialog_btn_cancel:
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setExpanded(true)
                .create();
    }

    public static void alerDeletetInit(Fragment fragment, String content, int delete, String table) {
        mCallback = (CallbackInterfaceDialog) fragment;
        createAlertDelete(fragment.getContext(), content, delete, table, mCallback).show();
    }

    public static void alerDeletetInit(Context context, String content, int delete, String table) {
        mCallback = (CallbackInterfaceDialog) context;
        createAlertDelete(context, content, delete, table, mCallback).show();
    }

    /**
     * Creamos una ventana emergente para confirmar si se desea eliminar el item
     */
    private static DialogPlus createAlertDelete(final Context context, final String content, final int delete, final String table, final CallbackInterfaceDialog mCallback) {
        final BdContructor bd = new BdContructor(context);

        DialogAlertAdapter adapter = new DialogAlertAdapter(context, content);
        return DialogPlus.newDialog(context)
                .setAdapter(adapter)
                .setHeader(R.layout.dialog_alert_header)
                .setFooter(R.layout.dialog_alert_footer)
                .setContentHolder(new ViewHolder(R.layout.dialog_alert_content))
                .setCancelable(true)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.dialog_btn_delete:
                                switch (table) {
                                    case "report":
                                        Report report = new Report();
                                        report.setId(delete);
                                        bd.report.reportDelete(report);
                                        break;
                                    case "expense":
                                        Expenses expenses = new Expenses();
                                        expenses.setId(delete);
                                        bd.expense.expensesDelete(expenses);
                                        break;
                                    case "aircraft":
                                        AircraftReport aircraftReport = new AircraftReport();
                                        aircraftReport.setId(delete);
                                        bd.maintenance.aircraftDelete(aircraftReport);
                                        break;
                                    case "plan":
                                        Plan plan = new Plan();
                                        plan.setId(delete);
                                        bd.plan.planDelete(plan);
                                        break;
                                    case "listCustomer":
                                        bd.list.borrarListado(delete, ConstantesBaseDatos.TABLE_LIST_CUSTOMER);
                                        break;
                                    case "listAircraft":
                                        bd.list.borrarListado(delete, ConstantesBaseDatos.TABLE_LIST_AIRCRAFT);
                                        break;
                                    case "listCapitanCopilot":
                                        bd.list.borrarListado(delete, ConstantesBaseDatos.TABLE_LIST_CAPITAN);
                                        break;
                                }
                                mCallback.onHandleSelectionButton(true);

                                dialog.dismiss();
                                break;
                            case R.id.dialog_btn_cancel:
                                mCallback.onHandleSelectionButton(false);
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setExpanded(true)
                .create();
    }

}
