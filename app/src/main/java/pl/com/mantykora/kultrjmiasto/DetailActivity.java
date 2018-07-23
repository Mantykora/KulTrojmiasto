package pl.com.mantykora.kultrjmiasto;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    FavoriteEntry likedEntryWithoutLiveData;


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


       //LiveData<FavoriteEntry> likedEntry =  mDb.favoriteDao().loadTaskById(event.getId());
//       likedEntry.observe(this, new Observer<FavoriteEntry>() {
//           @Override
//           public void onChanged(@Nullable FavoriteEntry favoriteEntry) {
//               if (favoriteEntry != null) {
//                   if (favoriteEntry.getIsLiked()) {
//                       likeButton.setLiked(true);
//                   }
//               }
//           }
//       });

        favoriteEntry = new FavoriteEntry(event.getId(), event.getName(), event.getPlace().getName(), startTicket, endTicket, event.getStartDate(), event.getDescLong(), event.getUrls().getWww(), fileName, isLiked);

        //TODO bug bug bug bug bug bug bug

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
               likedEntryWithoutLiveData = mDb.favoriteDao().loadTaskByIdWithoutLiveData(event.getId());

                if (likedEntryWithoutLiveData != null) {
                    if (likedEntryWithoutLiveData.getIsLiked()) {
                        likeButton.setLiked(true);
                    } else likeButton.setLiked(false);
                }



            }
        });


        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                isLiked = true;
                favoriteEntry.setIsLiked(isLiked);
                if (event.getAttachments().size() > 0) {
                   fileName = event.getAttachments().get(0).getFileName();
                }
                addFavoriteToDatabase();
                likeButton.setLiked(isLiked);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                isLiked = false;
                //TODO delete not on main thread
                removeFavoriteFromDatabase();
                likeButton.setLiked(false);

            }
        });



    }

    public void addFavoriteToDatabase() {
        //favoriteEntry = new FavoriteEntry(event.getId(), event.getName(), event.getPlace().getName(), startTicket, endTicket, event.getStartDate(), event.getDescLong(), event.getUrls().getWww(), fileName, isLiked);
         AppExecutors.getInstance().diskIO().execute(new Runnable() {
             @Override
             public void run() {
                 mDb.favoriteDao().insertFavorite(favoriteEntry);

             }
         });
    }

    public void removeFavoriteFromDatabase() {
       // favoriteEntry = new FavoriteEntry(event.getId(), event.getName(), event.getPlace().getName(), startTicket, endTicket, event.getStartDate(), event.getDescLong(), event.getUrls().getWww(), fileName, isLiked);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.favoriteDao().deleteTask(favoriteEntry);

            }
        });
    }
    public void populateUi() {

        if (event.getAttachments().size() > 0) {
            Picasso.get().load(event.getAttachments().get(0).getFileName()).into(imageView);
        }
       placeTv.setText(String.valueOf(event.getPlace().getName()));

       DateTime dateTime = new DateTime(event.getStartDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
        String dateString = dateTime.toString(dateTimeFormatter);
       dateTv.setText(dateString);
       //TODO set endTicket


        String ticketType = event.getTickets().getType();

        switch (ticketType) {
            case "free":
                priceTv.setText("darmowe");
                break;
            case "unknown":
                ticket_iv.setVisibility(View.GONE);
                break;
            case "tickets":
                priceTv.setText(startTicket);
                if (endTicket != null) {
                    priceEndTv.setText("- " + endTicket);
                }
                break;
        }
//       if (startTicket != null) {
//           priceTv.setText(startTicket);
//       }
//       if (endTicket != null) {
//           priceEndTv.setText("- " + endTicket);
//       }
//       if (startTicket == null && endTicket == null) {
//           ticket_iv.setVisibility(View.GONE);
//       }

       String eventString = event.getDescLong();


       if (eventString.contains("<p>")) {
           descriptionTv.setText(Html.fromHtml(eventString));
       } else {
           descriptionTv.setText(eventString);
       }
       linklTv.setText(event.getUrls().getWww());
    }
}
