package com.jawline.exercises.faceyoga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.text.Html;

import com.bumptech.glide.Glide;

public class ActivityArticleRead extends AppCompatActivity {
    public AppCompatTextView tvHeader;
    public AppCompatTextView tvArticle;
    AppCompatImageView articleImageView;
    public String str;
    public int strImageArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(8192);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_read);

        tvHeader = findViewById(R.id.headerArticleTV);
        tvArticle = findViewById(R.id.bodyArticleTV);
        str = getIntent().getStringExtra("Article Title");
        strImageArticle = getIntent().getIntExtra("Article Image", R.drawable.can_face_yoga_improve_image);

        findViewById(R.id.iv_close).setOnClickListener(view -> {
            finish();
        });

        this.tvHeader.setText(this.str);

        articleImageView = findViewById(R.id.articleImageView);
        Glide.with(ActivityArticleRead.this).load(Integer.valueOf(this.strImageArticle)).into(articleImageView);
        if (this.str.equals(getString(R.string.title_can_face_yoga_improve_your_appearance))) {

            str = "<p>Research has found face yoga may be effective in improving the structural appearance of your face by strengthening the muscles of the cheeks and face. Practitioners report a younger appearance as well.</p>\n<p>It&rsquo;s important to sleep well, reduce stress, and eat healthily. Holistic health and self-care are key to radiant skin since taking care of yourself inside will be reflected in your outer shine.</p>\n<p>You can create a routine to target a specific area of concern in your face, such as frown lines, forehead lines, or crow&rsquo;s feet. Or you can focus on concerns related to issues such as stress, anxiety, or sleep.</p>\n<p><br></p>";
        } else if (this.str.equals(getString(R.string.title_does_your_face_need_a_workout))) {

            str = "<p>Facial exercises are being touted as a way to reverse signs of aging. A workout can&apos;t hurt and might even help. But there&apos;s little evidence of benefit.</p>\n<p>Face exercises, including stretching and movement, can be used to loosen up and lessen the appearance of a tight scar.</p>\n<p>Massage and exercises that stretch the skin affected by scarring can make a thick scar become thinner and more pliable.</p>\n<p>This is a clear instance where facial exercises are recommended and likely to be effective.</p>\n<p>Face exercises might also help improve muscle tone in the face and could theoretically help with gravity-related fat loss or redistribution on the face.</p>";
        } else if (this.str.equals(getString(R.string.title_face_yoga_and_its_benefits))) {

            str = "<p>Face yoga exercises stimulate microcirculation and bring more oxygen to cells. This makes the complexion look more balanced, even and radiant.</p>\n<p>With age, natural collagen levels decrease. As a result, the skin starts to sag and loses tone, and wrinkles become more pronounced. The advantage of face yoga exercises is that they don&apos;t just stimulate muscles; they also stimulate the production of collagen and elastin.</p>\n<p>When you perform face yoga exercises, you&apos;ll probably find that you feel more relaxed! These exercises are good for both the body and mind, as they relax facial muscles while acting on the nervous system, reducing stress.</p>\n<p><br></p>";
        } else if (this.str.equals(getString(R.string.title_best_food_for_healthy_and_glowing_skin))) {

            str = "<p>Healthy skin comes from within. using cosmetic products can tackle various problems. but eating healthy food not only will help you to glow your skin but also helps to keep you healthy.</p>\n<p>To achieve this, you&apos;ll need to consume nutritious food and at the same time you need to avoid oily, processed and deep fried foods.</p>\n<p>You can glow your skin by adding following foods into your diet:</p>\n<p style=\"color:#000000\"><strong>1. Fatty Fish</strong></p>\n<p>Fish like salmon and mackerel are great sources of omega-3 fatty acids which helps you to look supple and radiant.</p>\n<p style=\"color:#000000\"><strong>2. Walnuts</strong></p>\n<p>Walnuts contain vitamins like Vitamin E and Vitamin C, zinc and selenium which promotes healthy skin.</p>\n<p style=\"color:#000000\"><strong>3. Carrots</strong></p>\n<p>It contains Vitamin A which fight against sun burn, dead cells and wrinkles.</p>\n<p style=\"color:#000000\"><strong>4. Dark Chocolate</strong></p>\n<p>Dark chocolate contains cocoa powder which helps to boasts a bunch of antioxidants and this will helps you to smoothen your skin.</p>\n<p><br></p>";
        } else if (this.str.equals(getString(R.string.title_does_face_yoga_actually_works))) {

            str = "<p>Face yoga is the latest skincare trend and it is all about various face exercises.</p>\n<p style=\"color:#000000\"><strong>What is face yoga?</strong></p>\n<p>As we grow older, our skin starts to show perfectly natural signs of ageing including sagging, wrinkles and fine lines.</p>\n<p>Face yoga has been touted as a natural way to avoid having filler injections or going under the knife for a face lift.</p>\n<p style=\"color:#000000\"><strong>Does face yoga actually works?</strong></p>\n<p>There are lots of studies has been conducted on Face Yoga to show the result whether it actually works or not.</p>\n<p>A 2018 study from Northwestern University showed that 20 weeks of daily facial exercises did result in firmer skin and fuller upper and lower cheeks.</p>\n<p>This is an optimistic sign that face yoga could work. But the best way to find out is to give it a try and see if you see any face yoga results!</p>";
        } else {
            return;
        }
        tvArticle.setText(Html.fromHtml(str));
    }
}