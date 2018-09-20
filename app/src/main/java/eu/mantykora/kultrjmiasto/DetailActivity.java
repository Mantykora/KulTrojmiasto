package eu.mantykora.kultrjmiasto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.mantykora.kultrjmiasto.AppExecutors;
import eu.mantykora.kultrjmiasto.R;
import eu.mantykora.kultrjmiasto.database.AppDatabase;
import eu.mantykora.kultrjmiasto.database.FavoriteEntry;
import eu.mantykora.kultrjmiasto.model.Event;

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

    @BindView(R.id.detail_title_tv)
    TextView titleTv;

    @BindView(R.id.detail_title_tv2)
    TextView titleTv2;

    @BindView(R.id.detail_ticket_iv)
    ImageView placeIv;

    @BindView(R.id.shareButton)
    ImageButton shareButton;

    private eu.mantykora.kultrjmiasto.database.AppDatabase mDb;
    private String startTicket;
    private String endTicket;
    private boolean isLiked = false;
    private eu.mantykora.kultrjmiasto.database.FavoriteEntry favoriteEntry;
    private String fileName;
    private eu.mantykora.kultrjmiasto.database.FavoriteEntry likedEntryWithoutLiveData;
    private eu.mantykora.kultrjmiasto.model.Event event;
    private eu.mantykora.kultrjmiasto.database.FavoriteEntry fav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar myToolbar = findViewById(R.id.my_toolbar_detail);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ButterKnife.bind(this);


        event = getIntent().getParcelableExtra("singleEvent");
        fav = getIntent().getParcelableExtra("singleFavorite");

        mDb = eu.mantykora.kultrjmiasto.database.AppDatabase.getInstance(getApplicationContext());


        if (event != null) {

            startTicket = event.getTickets().getStartTicket();
            endTicket = event.getTickets().getEndTicket();
            populateUi();

            if (event.getAttachments().size() > 0) {
                fileName = event.getAttachments().get(0).getFileName();
            }
            favoriteEntry = new eu.mantykora.kultrjmiasto.database.FavoriteEntry(event.getId(), event.getName(), event.getPlace().getName(), startTicket, endTicket, event.getStartDate(), event.getDescLong(), event.getUrls().getWww(), fileName, isLiked, event.getTickets().getType(), event.getDescShort());


            eu.mantykora.kultrjmiasto.AppExecutors.getInstance().diskIO().execute(() -> {
                likedEntryWithoutLiveData = mDb.favoriteDao().loadTaskByIdWithoutLiveData(event.getId());

                if (likedEntryWithoutLiveData != null) {
                    if (likedEntryWithoutLiveData.getIsLiked()) {
                        likeButton.setLiked(true);
                    } else likeButton.setLiked(false);
                }


            });
        } else {

            likeButton.setLiked(true);
            favoriteEntry = new eu.mantykora.kultrjmiasto.database.FavoriteEntry(fav.getId(), fav.getTitle(), fav.getPlace(), fav.getStartTicket(), fav.getEndTicket(), fav.getDate(), fav.getDescription(), fav.getLink(), fav.getImage(), fav.getIsLiked(), fav.getTicketType(), fav.getShortDescription());
            populateUiFromFavs();


        }

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                isLiked = true;
                favoriteEntry.setIsLiked(isLiked);

                addFavoriteToDatabase();
                likeButton.setLiked(isLiked);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                isLiked = false;
                removeFavoriteFromDatabase();
                likeButton.setLiked(false);

            }
        });

        shareButton.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            if (event != null) {
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, event.getName());
                shareIntent.putExtra(Intent.EXTRA_TEXT, event.getDescShort() + " " + event.getUrls().getWww());
            } else {
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, fav.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, fav.getShortDescription() + " " + fav.getLink());
            }
            startActivity(Intent.createChooser(shareIntent, "Share your event"));
        });

    }

    private void addFavoriteToDatabase() {
        eu.mantykora.kultrjmiasto.AppExecutors.getInstance().diskIO().execute(() -> mDb.favoriteDao().insertFavorite(favoriteEntry));
    }

    private void removeFavoriteFromDatabase() {
        eu.mantykora.kultrjmiasto.AppExecutors.getInstance().diskIO().execute(() -> mDb.favoriteDao().deleteTask(favoriteEntry));
    }

    private void populateUi() {

        if (event.getAttachments().size() > 0) {
            DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
            int width = displaymetrics.widthPixels;
            int height = getResources().getDimensionPixelOffset(R.dimen.cardview_height);
            Picasso.get().load(event.getAttachments().get(0).getFileName()).resize(width, height).centerCrop().into(imageView);
            titleTv2.setText(event.getName());
            titleTv2.setVisibility(View.GONE);
        }

        String placeString = (String.valueOf(event.getPlace().getName()));

        if (placeString.contains("null")) {
            placeIv.setVisibility(View.GONE);

        } else {
            placeTv.setText(String.valueOf(event.getPlace().getName()));
        }

        DateTime dateTime = new DateTime(event.getStartDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        String dateString = dateTime.toString(dateTimeFormatter);
        dateTv.setText(dateString);


        getTicketType(event.getTickets().getType());

        String eventString = event.getDescLong();


        if (eventString.contains("<p>") || eventString.contains("&nbsp;") || eventString.contains("b") || eventString.contains("br") || eventString.contains("[embed]") || eventString.contains("&quot;")) {
            descriptionTv.setText(Html.fromHtml(eventString));
            titleTv.setText(Html.fromHtml(event.getName()));

        } else {
            descriptionTv.setText(eventString);
            titleTv.setText(event.getName());

        }
        linklTv.setText(event.getUrls().getWww());
    }

    private void populateUiFromFavs() {

        if (fav.getPlace().contains("null")) {
            placeIv.setVisibility(View.GONE);
        } else {
            placeTv.setText(fav.getPlace());
        }

        DateTime dateTime = new DateTime(fav.getDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        String dateString = dateTime.toString(dateTimeFormatter);
        dateTv.setText(dateString);
        getTicketType(fav.getTicketType());

        String describtionString = fav.getDescription();
        if (describtionString.contains("<p>") || describtionString.contains("&nbsp;") || describtionString.contains("b") || describtionString.contains("[embed]") || describtionString.contains("&quot;")) {
            descriptionTv.setText(Html.fromHtml(describtionString));
            titleTv.setText(Html.fromHtml(fav.getTitle()));

        } else {
            descriptionTv.setText(describtionString);
            titleTv.setText(fav.getTitle());
        }
        linklTv.setText(fav.getLink());

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        int width = displaymetrics.widthPixels;
        int height = getResources().getDimensionPixelOffset(R.dimen.cardview_height);
        Picasso.get().load(fav.getImage()).resize(width, height).centerCrop().into(imageView);


    }

    private void getTicketType(String type) {

        switch (type) {
            case "free":
                priceTv.setText(R.string.darmowe);
                break;
            case "unknown":
                ticket_iv.setVisibility(View.GONE);
                break;
            case "tickets":
                priceTv.setText(startTicket);
                if (endTicket != null) {
                    priceEndTv.setText(new StringBuilder().append("- ").append(endTicket).toString());
                }
                break;
        }
    }
}
