package flight.report.ec.charter.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import flight.report.ec.charter.restApi.ConstantesRestApi;
import flight.report.ec.charter.restApi.EndPointsApi;
import flight.report.ec.charter.restApi.deserializador.AircraftDeserializador;
import flight.report.ec.charter.restApi.deserializador.AircraftsComponentDeserializador;
import flight.report.ec.charter.restApi.deserializador.AircraftsDocumentDeserializador;
import flight.report.ec.charter.restApi.deserializador.ChatDeserializador;
import flight.report.ec.charter.restApi.deserializador.ComponentDeserializador;
import flight.report.ec.charter.restApi.deserializador.CrewDeserializador;
import flight.report.ec.charter.restApi.deserializador.CustomerDeserializador;
import flight.report.ec.charter.restApi.deserializador.MessageDeserializador;
import flight.report.ec.charter.restApi.deserializador.ReportDeserializador;
import flight.report.ec.charter.restApi.modelo.AircraftChatResponse;
import flight.report.ec.charter.restApi.modelo.AircraftComponentResponse;
import flight.report.ec.charter.restApi.modelo.AircraftDocumentResponse;
import flight.report.ec.charter.restApi.modelo.AircraftResponse;
import flight.report.ec.charter.restApi.modelo.ComponentResponse;
import flight.report.ec.charter.restApi.modelo.CrewResponse;
import flight.report.ec.charter.restApi.modelo.CustomerResponse;
import flight.report.ec.charter.restApi.modelo.MessageResponse;
import flight.report.ec.charter.restApi.modelo.ReportResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jose on 18/4/2018.
 */

public class RestApiAdapter {

    public EndPointsApi establecerConexionApi(Gson gson) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(EndPointsApi.class);
    }

    public Gson message(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MessageResponse.class, new MessageDeserializador());
        return gsonBuilder.create();
    }

    public Gson customer(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CustomerResponse.class, new CustomerDeserializador());
        return gsonBuilder.create();
    }

    public Gson crew(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(CrewResponse.class, new CrewDeserializador());
        return gsonBuilder.create();
    }

    public Gson aircraft(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AircraftResponse.class, new AircraftDeserializador());
        return gsonBuilder.create();
    }

    public Gson history(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ReportResponse.class, new ReportDeserializador());
        return gsonBuilder.create();
    }

    public Gson aircraftsTab(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AircraftComponentResponse.class, new AircraftsComponentDeserializador());
        return gsonBuilder.create();
    }

    public Gson aircraftsDocuments(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AircraftDocumentResponse.class, new AircraftsDocumentDeserializador());
        return gsonBuilder.create();
    }

    public Gson aircraftsComponents(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ComponentResponse.class, new ComponentDeserializador());
        return gsonBuilder.create();
    }

    public Gson aircraftsChats(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AircraftChatResponse.class, new ChatDeserializador());
        return gsonBuilder.create();
    }
}
