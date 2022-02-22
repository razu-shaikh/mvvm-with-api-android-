package com.android.mvvmretrofitjava.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mvvmretrofitjava.R;
import com.android.mvvmretrofitjava.adapter.ImageSliderAdapter;
import com.android.mvvmretrofitjava.viewmodel.TVShowDetailsViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;

public class TVShowDetailsActivity extends AppCompatActivity {

    private TVShowDetailsViewModel tvShowDetailsViewModel;

    private ViewPager2 slide_view_pager;
    private LinearLayout page_slide_indicators;
    private RoundedImageView imageTVShow;
    private TextView textName,textNetworkCountry,textStatus,textStarted,textDescription,textReadMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);
        allView();
        doInitialization();
    }

   private void doInitialization(){
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
       getTVShowDetails();
    }

    private void getTVShowDetails(){

        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        Log.d("razuid",tvShowId);
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this,tvShowDetailsResponse ->{

               if (tvShowDetailsResponse.getTvShowDetails() != null){
                   if (tvShowDetailsResponse.getTvShowDetails().getPictures() != null){

                       loadImageSlider(tvShowDetailsResponse.getTvShowDetails().getPictures());

                   }
                   Glide.with(getApplicationContext())
                           .load(tvShowDetailsResponse.getTvShowDetails().getImagePath())
                           .apply(RequestOptions.centerCropTransform())
                           .into(imageTVShow);

                   imageTVShow.setVisibility(View.VISIBLE);

                   textDescription.setText(String.valueOf(
                           HtmlCompat.fromHtml(
                                   tvShowDetailsResponse.getTvShowDetails().getDescription(),
                                   HtmlCompat.FROM_HTML_MODE_LEGACY
                           )
                   ));
                   textReadMore.setOnClickListener(view -> {
                       if (textReadMore.getText().toString().equals("Read More")) {
                          textDescription.setMaxLines(Integer.MAX_VALUE);
                          textDescription.setEllipsize(null);
                           textReadMore.setText(R.string.read_less);
                       } else {
                           textDescription.setMaxLines(4);
                           textDescription.setEllipsize(TextUtils.TruncateAt.END);
                           textReadMore.setText(R.string.read_more);
                       }
                   });

                   loadTVShowDetails();
               }

        });

    }

    private void loadImageSlider(String[] sliderImages){
        slide_view_pager.setAdapter(new ImageSliderAdapter(this,sliderImages));
         slide_view_pager.setVisibility(View.VISIBLE);
         //setUp Slide Indicator
        setUpSlideIndicators(sliderImages.length);
        slide_view_pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setUpSlideIndicators(int count){
        ImageView[] indicator = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i=0; i<indicator.length;i++){
           indicator[i] = new ImageView(getApplicationContext());
            indicator[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slider_indictor_inactive
            ));

            indicator[i].setLayoutParams(layoutParams);

            page_slide_indicators.addView(indicator[i]);
        }
        page_slide_indicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position){
        int childCount = page_slide_indicators.getChildCount();
        for (int i=0;i<childCount;i++){
            ImageView imageView = (ImageView) page_slide_indicators.getChildAt(i);

            if (i==position){
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.background_slider_indictor
                ));

            }else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.background_slider_indictor_inactive
                ));
            }
        }
    }

    private void loadTVShowDetails(){
      textName.setText(getIntent().getStringExtra("name"));
      textNetworkCountry.setText((getIntent().getStringExtra("network")
              + "(" + getIntent().getStringExtra("country") + ")"
      ));
      textStatus.setText(getIntent().getStringExtra("status"));
      textStarted.setText(getIntent().getStringExtra("startDate"));


    }

    private void allView(){
        imageTVShow = (RoundedImageView)findViewById(R.id.imageTVShow);
        slide_view_pager = (ViewPager2)findViewById(R.id.slide_view_pager) ;
        page_slide_indicators = (LinearLayout) findViewById(R.id.page_slide_indicators) ;

        textName=(TextView)findViewById(R.id.textName);
        textNetworkCountry=(TextView)findViewById(R.id.textNetworkCountry);
        textStatus=(TextView)findViewById(R.id.textStatus);
        textStarted=(TextView)findViewById(R.id.textStarted);
        textDescription=(TextView)findViewById(R.id.textDescription);
        textReadMore=(TextView)findViewById(R.id.textReadMore);
    }

}