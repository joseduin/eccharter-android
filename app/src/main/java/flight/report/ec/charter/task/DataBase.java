package flight.report.ec.charter.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import flight.report.ec.charter.R;
import flight.report.ec.charter.bd.bridge.ListBridge;
import flight.report.ec.charter.bd.bridge.SendBridge;
import flight.report.ec.charter.modelo.Send;
import flight.report.ec.charter.utils.Mensaje;

public class DataBase {
    private Context context;
    private ProgressDialog progressDialog;
    private SendBridge sendBridge;
    private ListBridge listBridge;

    public DataBase(Context context) {
        this.sendBridge = new SendBridge(context);
        this.listBridge = new ListBridge(context);
        this.context = context;
    }

    public void init() {
        new DataBaseInit().execute();
    }

    private class DataBaseInit extends AsyncTask<Void, Void, String> {

        public DataBaseInit() {
            progressDialog = Mensaje.progressConsultar(context, context.getResources().getString(R.string.please_wait));
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            cargarListados();
            return "succeess";
        }

        private void cargarListados() {
            /**
             * CURRENCY
             */
            listBridge.listaCurrencyInsert("$");

            /**
             * ENGINE
             */
            listBridge.listaEngineInsert("QUARTS");
            listBridge.listaEngineInsert("LITERS");

            /**
             * CAPITAN AND COPILOT
             */
            listBridge.insertCapitanAndPilot("-----------------");
            listBridge.insertCapitanAndPilot("Juan Ramirez".toUpperCase());
            listBridge.insertCapitanAndPilot("Eduardo Almanzor".toUpperCase());
            listBridge.insertCapitanAndPilot("Armando Travieso".toUpperCase());
            listBridge.insertCapitanAndPilot("Fernando Figueredo".toUpperCase());
            listBridge.insertCapitanAndPilot("Gustavo Reyes".toUpperCase());
            listBridge.insertCapitanAndPilot("Henrique Pocaterra".toUpperCase());
            listBridge.insertCapitanAndPilot("Juan Carlos Belandria".toUpperCase());
            listBridge.insertCapitanAndPilot("Luis Branger".toUpperCase());
            listBridge.insertCapitanAndPilot("Emely Fernandez".toUpperCase());
            listBridge.insertCapitanAndPilot("Esthefania Rojas".toUpperCase());

            /**
             *   CUSTOMER
             **/
            listBridge.listaCustomerInsert("-----------------");
            listBridge.listaCustomerInsert("AGUSTO MERINO");
            listBridge.listaCustomerInsert("ALBERTO DIAZ");
            listBridge.listaCustomerInsert("ALBERTO FINOL");
            listBridge.listaCustomerInsert("ALEJANDRO GARCIA");
            listBridge.listaCustomerInsert("ALEJANDRO PROSPERI");
            listBridge.listaCustomerInsert("ALEJANDRO REBOLLEDO");
            listBridge.listaCustomerInsert("ALEJANDRO SILVA");
            listBridge.listaCustomerInsert("ALESSANDRO BAZZONI");
            listBridge.listaCustomerInsert("ALEXIS NAVARRETE");
            listBridge.listaCustomerInsert("ALIETTE SALAZAR");
            listBridge.listaCustomerInsert("ALVARO ROTONDARO");
            listBridge.listaCustomerInsert("ANIBAL SOUKI");
            listBridge.listaCustomerInsert("ANTONIO MORAZZANI");
            listBridge.listaCustomerInsert("ARMANDO BRIQUET");
            listBridge.listaCustomerInsert("BERNARDO PEREZ BEICOS");
            listBridge.listaCustomerInsert("BRILAND");
            listBridge.listaCustomerInsert("CARLOS BENSHIMOL");
            listBridge.listaCustomerInsert("CARLOS MARTINS");
            listBridge.listaCustomerInsert("CEO");
            listBridge.listaCustomerInsert("COMMAN AIR.C.A");
            listBridge.listaCustomerInsert("CPVEN");
            listBridge.listaCustomerInsert("DANIEL DE GRAZIA");
            listBridge.listaCustomerInsert("DANIEL FINOL");
            listBridge.listaCustomerInsert("DAVID LOPEZ");
            listBridge.listaCustomerInsert("DAVID RODRIGUEZ");
            listBridge.listaCustomerInsert("DIEGO DIAZ");
            listBridge.listaCustomerInsert("EDUARDO ORTIZ");
            listBridge.listaCustomerInsert("EDUARDO PANTIN");
            listBridge.listaCustomerInsert("EDUARDO WALLIS");
            listBridge.listaCustomerInsert("ENRIQUE CASTRO");
            listBridge.listaCustomerInsert("ENRIQUE CONDE");
            listBridge.listaCustomerInsert("ERNESTO BRANGER");
            listBridge.listaCustomerInsert("EUDES RODRIGUEZ");
            listBridge.listaCustomerInsert("FEDERICO SHEMEL");
            listBridge.listaCustomerInsert("FERNANDO NAVARRO");
            listBridge.listaCustomerInsert("FRANCISCO SACCINI");
            listBridge.listaCustomerInsert("HECTOR VALECILLO");
            listBridge.listaCustomerInsert("HENRIQUE POCATERRA");
            listBridge.listaCustomerInsert("HUMBERTO DIAZ");
            listBridge.listaCustomerInsert("JAHROLD MAIZO");
            listBridge.listaCustomerInsert("JAVIER SANGUINO");
            listBridge.listaCustomerInsert("GRUPO ATAHUALPA");
            listBridge.listaCustomerInsert("JOBEL HERRERA");
            listBridge.listaCustomerInsert("JOHAN HOFFMANN");
            listBridge.listaCustomerInsert("JOHAN SCHNELL");
            listBridge.listaCustomerInsert("JORGE CAMPINS");
            listBridge.listaCustomerInsert("JORGE PINEDA");
            listBridge.listaCustomerInsert("JORGE PLAZA");
            listBridge.listaCustomerInsert("JOSE BORTONES");
            listBridge.listaCustomerInsert("JOSE LUIS MERINO");
            listBridge.listaCustomerInsert("JOSE RAFAEL PARRA");
            listBridge.listaCustomerInsert("JUAN CARLOS BRIQUET");
            listBridge.listaCustomerInsert("JUAN CHOURIO");
            listBridge.listaCustomerInsert("JUAN PAREDES");
            listBridge.listaCustomerInsert("JUAN PONCE");
            listBridge.listaCustomerInsert("LEONARDO BARRIOS");
            listBridge.listaCustomerInsert("LERYS MICCIOLO");
            listBridge.listaCustomerInsert("LOWIS MICIOLO");
            listBridge.listaCustomerInsert("MANUEL ARAUJO");
            listBridge.listaCustomerInsert("MARIA ELENA VALERO");
            listBridge.listaCustomerInsert("MARIA GABRIELA SENIOR");
            listBridge.listaCustomerInsert("MARIO HERRERA");
            listBridge.listaCustomerInsert("MAXIMO SACCINI");
            listBridge.listaCustomerInsert("MICKHAIL ALVAREZ");
            listBridge.listaCustomerInsert("MILOS MANAGEMENT");
            listBridge.listaCustomerInsert("OIL CONSULTING ENTERPRISE, INC");
            listBridge.listaCustomerInsert("PABLO TRONCONE");
            listBridge.listaCustomerInsert("ROBERTO CAVALLIN");
            listBridge.listaCustomerInsert("PEPE LEGGIO");
            listBridge.listaCustomerInsert("PIER ");
            listBridge.listaCustomerInsert("PROAGRO, C.A.");
            listBridge.listaCustomerInsert("SAID CAMACHO");
            listBridge.listaCustomerInsert("RAFAEL ANGULO");
            listBridge.listaCustomerInsert("RAFAEL GUZMAN");
            listBridge.listaCustomerInsert("ROMULO LANDER");
            listBridge.listaCustomerInsert("ROELVIS RESTREPO");
            listBridge.listaCustomerInsert("ROMINA PETROCELLI");
            listBridge.listaCustomerInsert("SAMIR BAZZI");
            listBridge.listaCustomerInsert("SAMUEL MERIDA");
            listBridge.listaCustomerInsert("DUBRAZKA DAZA");
            listBridge.listaCustomerInsert("TOMAS TRONCONE");
            listBridge.listaCustomerInsert("TULIO HINESTROSA");
            listBridge.listaCustomerInsert("UNIKA INTERNATIONAL INVESTMENT");
            listBridge.listaCustomerInsert("RICARDO MELENDEZ");
            listBridge.listaCustomerInsert("VESERCA");
            listBridge.listaCustomerInsert("VHICOA");
            listBridge.listaCustomerInsert("GENERAL ZAMBRANO");
            listBridge.listaCustomerInsert("VICTOR MARTINS");
            listBridge.listaCustomerInsert("WILLIAM PACANINS");
            listBridge.listaCustomerInsert("CARMELO DE GRAZIA");
            listBridge.listaCustomerInsert("ARMANDO TRAVIESO");
            listBridge.listaCustomerInsert("JORGE VALDEZ");
            listBridge.listaCustomerInsert("JOANNA GLOD");
            listBridge.listaCustomerInsert("JOAQUIN SARRIA");
            listBridge.listaCustomerInsert("DANIEL DURAN");
            listBridge.listaCustomerInsert("JUAN CARLOS ANGLADE");
            listBridge.listaCustomerInsert("ARGENIS AZUAJE");
            listBridge.listaCustomerInsert("JOSE LARA");
            listBridge.listaCustomerInsert("RONALD MAGALLANES");
            listBridge.listaCustomerInsert("EDUARDO ALMANSOR");
            listBridge.listaCustomerInsert("MARITIME CONTRACTORS");
            listBridge.listaCustomerInsert("JEANNOT DUCOURNAU");
            listBridge.listaCustomerInsert("LUIS RODRIGUEZ");
            listBridge.listaCustomerInsert("JUAN CARLOS BELANDRIA");
            listBridge.listaCustomerInsert("ALEXANDER LIRA");
            listBridge.listaCustomerInsert("XIGA TOURS 1069, C.A.");
            listBridge.listaCustomerInsert("MODABALY C.A.");
            listBridge.listaCustomerInsert("FABRITZIO DELLA POLLA");
            listBridge.listaCustomerInsert("ALEJANDRO QUINTAVALLE");

            /**
             *   AIRCRAFT
             **/
            listBridge.listaAircraftInsert("-----------------");
            listBridge.listaAircraftInsert("YV3346");
            listBridge.listaAircraftInsert("YV2949");
            listBridge.listaAircraftInsert("YV2951");
            listBridge.listaAircraftInsert("YV3310");
            listBridge.listaAircraftInsert("YV2853");
            listBridge.listaAircraftInsert("YV3048");
            listBridge.listaAircraftInsert("YV1039");

            /**
             *   SEND [Server, Mail]
             **/
            sendBridge.sendInsert(new Send(false, true));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}
