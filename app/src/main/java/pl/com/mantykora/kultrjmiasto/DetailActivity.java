package pl.com.mantykora.kultrjmiasto;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mantykora.kultrjmiasto.model.Event;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_place_tv)
    TextView placeTv;

    @BindView(R.id.detail_date_tv)
    TextView dateTv;

    @BindView(R.id.detail_price_tv)
    TextView priceTv;

    @BindView(R.id.detail_description_tv)
    TextView descriptionTv;

    @BindView(R.id.detail_link_tv)
    TextView linklTv;

    @BindView(R.id.detail_image_view)
    ImageView imageView;

    Event event;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);


       // Bundle bundle = getIntent().getBundleExtra("bundle");
       // Event event = bundle.getParcelable("sindleEvent");
        event = getIntent().getParcelableExtra("singleEvent");
        Log.d("DetailActivity", event.getName());

        populateUi();

    }

    public void populateUi() {

        if (event.getAttachments().size() > 0) {
            Picasso.get().load(event.getAttachments().get(0).getFileName()).into(imageView);
        }
       placeTv.setText(String.valueOf(event.getPlace().getName()));
       dateTv.setText(event.getStartDate());
       //TODO set endTicket
       priceTv.setText(event.getTickets().getStartTicket());
       descriptionTv.setText(event.getDescLong());
       linklTv.setText(event.getUrls().getWww());
    }
}
