package flight.report.ec.charter.adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import flight.report.ec.charter.GalleryPreview;
import flight.report.ec.charter.R;
import flight.report.ec.charter.aircrafts.AircraftTabs;
import flight.report.ec.charter.bd.BdContructor;
import flight.report.ec.charter.modelo.components.Aircraft;
import flight.report.ec.charter.modelo.components.Document;
import flight.report.ec.charter.utils.Convert;
import flight.report.ec.charter.utils.Image;
import flight.report.ec.charter.utils.IrA;
import flight.report.ec.charter.utils.Mensaje;
import flight.report.ec.charter.utils.StringUtil;

/**
 * Created by Jose on 23/1/2018.
 */

public class RecyclerDocumentAdapter extends RecyclerView.Adapter<RecyclerDocumentAdapter.AircraftViewHolder> {
    private List<Document> documents;
    private List<Document> documentsFilter;
    private Context context;
    private BdContructor bd;
    private Image image;

    public RecyclerDocumentAdapter(Context context, List<Document> documents) {
        this.documents = documents;
        this.documentsFilter = documents;
        this.context = context;
        this.bd = new BdContructor(context);
        this.image = new Image(context);
    }

    @Override
    public AircraftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_document, parent, false);
        AircraftViewHolder pvh = new AircraftViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final AircraftViewHolder holder, int position) {
        final Document document = documents.get(position);

        if (document.getSrcImgSave()==null)
            holder.progressBar.setVisibility(View.VISIBLE);

        holder.gallery_title.setText(document.getTitle());
        picasso(document)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.galleryImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.progressBar.getVisibility()==View.VISIBLE)
                            holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        if (holder.progressBar.getVisibility()==View.VISIBLE)
                            holder.progressBar.setVisibility(View.GONE);
                        holder.refresh_img.setVisibility(View.VISIBLE);
                    }
                });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.refresh_img.getVisibility()==View.VISIBLE) {
                    Mensaje.mensajeCorto(context, context.getResources().getString(R.string.image_error));
                    return;
                }

                if (document.getIs_img()) {
                    if (document.getSrcImgSave()==null)
                        return;

                    Intent intent = new Intent(context, GalleryPreview.class);
                    intent.putExtra("path", document.getSrcImgSave());
                    intent.putExtra("file", false);
                    context.startActivity(intent);
                } else {
                    if (document.getSrcSave()==null)
                        return;

                    Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                    pdfIntent.setDataAndType(Convert.pdfPath( document.getSrcSave() ), "application/pdf");
                    pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(pdfIntent);
                }
            }
        });
    }

    private RequestCreator picasso(Document document) {
        if (document.getIs_img()) {
            if (document.getSrcImgSave()==null) {
                return Picasso.get().load(R.drawable.placeholder_image);
            } else {
                return Picasso.get().load(Uri.parse( document.getSrcImgSave() ));
            }
        } else {
            return Picasso.get().load(R.drawable.pdf_placeholder);
        }
    }

    public void updateList(List<Document> documents) {
        this.documents = documents;
        notifyDataSetChanged();
    }

    public int filter(String charText) {
        this.documents = new ArrayList<>();
        if (charText.isEmpty()) {
            this.documents.addAll(this.documentsFilter);
        } else {
            for (Document document : this.documents) {
                if (StringUtil.contains(document.getTitle(), charText)) {
                    documents.add(document);
                }
            }
        }
        notifyDataSetChanged();
        return documents.size();
    }

    public List<Document> getDocuments() {
        return documentsFilter;
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class AircraftViewHolder extends RecyclerView.ViewHolder {

        private ImageView galleryImage;
        private ImageButton refresh_img;
        private TextView gallery_title;
        private ProgressBar progressBar;
        private View v;

        public AircraftViewHolder(View itemView) {
            super(itemView);
            galleryImage = itemView.findViewById(R.id.galleryImage);
            gallery_title = itemView.findViewById(R.id.gallery_title);
            progressBar = itemView.findViewById(R.id.progressBar);
            refresh_img = itemView.findViewById(R.id.refresh_img);

            v = itemView;
        }
    }

}
