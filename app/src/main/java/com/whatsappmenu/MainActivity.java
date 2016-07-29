package com.whatsappmenu;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import android.widget.Toast;
import android.annotation.TargetApi;

/**
 * Shayan Rais (http://shanraisshan.com)
 * created on 7/28/2016
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Tutorial Help
    //@link http://www.tutorialsbuzz.com/2015/11/android-circular-reveal-effect-whatsapp.html

    private LinearLayout attachmentLayout;
    private boolean isHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //for API-8 (doing only in main activity)
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        attachmentLayout = (LinearLayout) findViewById(R.id.menu_attachments);

        ImageButton btnDocument = (ImageButton) findViewById(R.id.menu_attachment_document);
        ImageButton btnCamera = (ImageButton) findViewById(R.id.menu_attachment_camera);
        ImageButton btnGallery = (ImageButton) findViewById(R.id.menu_attachment_gallery);
        ImageButton btnAudio = (ImageButton) findViewById(R.id.menu_attachment_audio);
        ImageButton btnLocation = (ImageButton) findViewById(R.id.menu_attachment_location);
        ImageButton btnContact = (ImageButton) findViewById(R.id.menu_attachment_contact);

        btnDocument.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnAudio.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideMenu();

        switch (v.getId()) {
            case R.id.menu_attachment_document:
                Toast.makeText(MainActivity.this, "Document Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attachment_camera:
                Toast.makeText(MainActivity.this, "Camera Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attachment_gallery:
                Toast.makeText(MainActivity.this, "Gallery Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attachment_audio:
                Toast.makeText(MainActivity.this, "Audio Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attachment_location:
                Toast.makeText(MainActivity.this, "Location Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_attachment_contact:
                Toast.makeText(MainActivity.this, "Contact Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//__________________________________________________________________________________________________
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clip:
                //[os_lollipop_: play animation accordingly]
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    showMenuBelowLollipop();
                else
                    showMenu();
                return true;

            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//__________________________________________________________________________________________________
    void showMenuBelowLollipop() {
        int cx = (attachmentLayout.getLeft() + attachmentLayout.getRight());
        int cy = attachmentLayout.getTop();
        int radius = Math.max(attachmentLayout.getWidth(), attachmentLayout.getHeight());

        try {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(300);

            if (isHidden) {
                //Log.e(getClass().getSimpleName(), "showMenuBelowLollipop");
                attachmentLayout.setVisibility(View.VISIBLE);
                animator.start();
                isHidden = false;
            } else {
                SupportAnimator animatorReverse = animator.reverse();
                animatorReverse.start();
                animatorReverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                        //Log.e("MainActivity", "onAnimationEnd");
                        isHidden = true;
                        attachmentLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel() {
                    }

                    @Override
                    public void onAnimationRepeat() {
                    }
                });
            }
        } catch (Exception e) {
            //Log.e(getClass().getSimpleName(), "try catch");
            isHidden = true;
            attachmentLayout.setVisibility(View.INVISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void showMenu() {
        int cx = (attachmentLayout.getLeft() + attachmentLayout.getRight());
        int cy = attachmentLayout.getTop();
        int radius = Math.max(attachmentLayout.getWidth(), attachmentLayout.getHeight());

        if (isHidden) {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, 0, radius);
            attachmentLayout.setVisibility(View.VISIBLE);
            anim.start();
            isHidden = false;
        } else {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    attachmentLayout.setVisibility(View.INVISIBLE);
                    isHidden = true;
                }
            });
            anim.start();
        }
    }

    private void hideMenu() {
        attachmentLayout.setVisibility(View.GONE);
        isHidden = true;
    }
}
