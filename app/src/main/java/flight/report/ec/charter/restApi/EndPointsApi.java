package flight.report.ec.charter.restApi;

import flight.report.ec.charter.modelo.PhotoRequest;
import flight.report.ec.charter.modelo.request.ChatRequest;
import flight.report.ec.charter.modelo.request.ReportRequest;
import flight.report.ec.charter.restApi.modelo.AircraftChatResponse;
import flight.report.ec.charter.restApi.modelo.AircraftComponentResponse;
import flight.report.ec.charter.restApi.modelo.AircraftDocumentResponse;
import flight.report.ec.charter.restApi.modelo.AircraftResponse;
import flight.report.ec.charter.restApi.modelo.ComponentResponse;
import flight.report.ec.charter.restApi.modelo.CrewResponse;
import flight.report.ec.charter.restApi.modelo.CustomerResponse;
import flight.report.ec.charter.restApi.modelo.MessageResponse;
import flight.report.ec.charter.restApi.modelo.ReportResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jose on 18/4/2018.
 */

public interface EndPointsApi {

    @Headers("Content-Type: application/json")
    @POST(ConstantesRestApi.URL_REPORT_REQUEST)
    Call<MessageResponse> reportRequest(@Body ReportRequest request);

    @Headers("Content-Type: application/json")
    @POST(ConstantesRestApi.URL_REPORT_PHOTO_REQUEST)
    Call<MessageResponse> reportPhotoRequest(@Body PhotoRequest request);

    @GET(ConstantesRestApi.URL_CUSTOMER)
    Call<CustomerResponse> getCustomers();

    @GET(ConstantesRestApi.URL_AIRCRAFT)
    Call<AircraftResponse> getAircrafts();

    @GET(ConstantesRestApi.URL_CREW)
    Call<CrewResponse> getCrews();

    @GET(ConstantesRestApi.URL_HISTORY)
    Call<ReportResponse> getHistory(@Path("username") String username);

    @GET(ConstantesRestApi.URL_AIRCRAFT_COMPONENT)
    Call<AircraftComponentResponse> getAircraftsTab();

    @GET(ConstantesRestApi.URL_AIRCRAFT_DOCUMENTS)
    Call<AircraftDocumentResponse> getAircraftDocuments(@Path("aircraft_id") Long aircraftId);

    @GET(ConstantesRestApi.URL_AIRCRAFT_COMPONENTS)
    Call<ComponentResponse> getAircraftComponents(@Path("aircraft_id") Long aircraftId);

    @GET(ConstantesRestApi.URL_AIRCRAFT_GET_CHAT)
    Call<AircraftChatResponse> getChat(@Path("master_type") int masterType,
                                       @Path("master_id") Long masterId);

    @Headers("Content-Type: application/json")
    @POST(ConstantesRestApi.URL_AIRCRAFT_CHAT)
    Call<MessageResponse> setChat(@Body ChatRequest request);
}
