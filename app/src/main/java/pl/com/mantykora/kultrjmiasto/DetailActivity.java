package pl.com.mantykora.kultrjmiasto;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.mantykora.kultrjmiasto.database.AppDatabase;
import pl.com.mantykora.kultrjmiasto.database.FavoriteEntry;
import pl.com.mantykora.kultrjmiasto.model.Event;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detail_place_tv)
    TextView placeTv;

    @BindView(R.id.detail_date_tv)
    TextView dateTv;

    @BindView(R.id.detail_price_tv)
    TextView priceTv;

    @BindView(R.id.detail_price_end_tv)
    TextView priceEndTv;

    @BindView(R.id.detail_description_tv)
    TextView descriptionTv;

    @BindView(R.id.detail_link_tv)
    TextView linklTv;

    @BindView(R.id.detail_image_view)
    ImageView imageView;

    @BindView(R.id.ticket_iv)
     ImageView ticket_iv;

    @BindView(R.id.heart_button)
    LikeButton likeButton;

    private AppDatabase mDb;
    private String startTicket;
    private String endTicket;
    private boolean isLiked = false;
    FavoriteEntry favoriteEntry;
    String fileName;


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

         startTicket = event.getTickets().getStartTicket();
         endTicket = event.getTickets().getEndTicket();
        populateUi();

        mDb = AppDatabase.getInstance(getApplicationContext());


       FavoriteEntry likedEntry =  mDb.favoriteDao().loadTaskById(event.getId());
       if (likedEntry != null) {
           if (likedEntry.getIsLiked()) {
               likeButton.setLiked(true);
           }
       }
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                isLiked = true;
                if (event.getAttachments().size() > 0) {
                   fileName = event.getAttachments().get(0).getFileName();
                }
                favoriteEntry = new FavoriteEntry(event.getId(), event.getName(), event.getPlace().getName(), startTicket, endTicket, event.getStartDate(), event.getDescLong(), event.getUrls().getWww(), fileName, isLiked);
                mDb.favoriteDao().insertFavorite(favoriteEntry);
                likeButton.setLiked(true);

            }

            @Override
            public void unLiked(LikeButton likeButton) {


                isLiked = false;
                //TODO get favorite entry with id?
               favoriteEntry =  mDb.favoriteDao().loadTaskById(event.getId());
                mDb.favoriteDao().deleteTask(favoriteEntry);
                likeButton.setLiked(false);

            }
        });



    }

    public void populateUi() {

        if (event.getAttachments().size() > 0) {
            Picasso.get().load(event.getAttachments().get(0).getFileName()).into(imageView);
        }
       placeTv.setText(String.valueOf(event.getPlace().getName()));
       dateTv.setText(event.getStartDate());
       //TODO set endTicket


       if (startTicket != null) {
           priceTv.setText(startTicket);
       }
       if (endTicket != null) {
           priceEndTv.setText("- " + endTicket);
       }
       if (startTicket == null && endTicket == null) {
           ticket_iv.setVisibility(View.GONE);
       }

       String eventString = event.getDescLong();


       if (eventString.contains("<p>")) {
           descriptionTv.setText(Html.fromHtml(eventString));
       } else {
           descriptionTv.setText(eventString);
       }
       linklTv.setText(event.getUrls().getWww());
    }
}
