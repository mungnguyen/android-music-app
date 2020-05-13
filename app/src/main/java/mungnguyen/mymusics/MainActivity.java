package mungnguyen.mymusics;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button playBtn, leftBtn, rightBtn;
    SeekBar positionBar;
    ImageView media;
    TextView playTime, endTime, songName;
    MediaPlayer mp;
    int totalTime;
    int currentSong = 1;
    int[] stt = {1, 2, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        media.startAnimation(animation);

        // Media player
        mp = MediaPlayer.create(this, R.raw.noi_ta_cho_em);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();
        songName.setText("Noi ta cho em");
        mp.start();
        playBtn.setBackgroundResource(R.drawable.pause);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtnClick(v);
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.release();
                switch (currentSong) {
                    case 1:
                        mp = MediaPlayer.create(MainActivity.this, R.raw.yeu_va_yeu);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Yeu va yeu");
                        currentSong = 3;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                    case 2:

                        mp = MediaPlayer.create(MainActivity.this, R.raw.noi_ta_cho_em);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Noi ta cho em");
                        currentSong = 1;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                    case 3:

                        mp = MediaPlayer.create(MainActivity.this, R.raw.vo);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Vo");
                        currentSong = 2;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.release();
                switch (currentSong) {
                    case 1:
                        mp = MediaPlayer.create(MainActivity.this, R.raw.vo);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Vo");
                        currentSong = 2;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                    case 2:
                        mp = MediaPlayer.create(MainActivity.this, R.raw.yeu_va_yeu);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Yeu va yeu");
                        currentSong = 3;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                    case 3:
                        mp = MediaPlayer.create(MainActivity.this, R.raw.noi_ta_cho_em);
                        mp.setLooping(true);
                        mp.seekTo(0);
                        mp.setVolume(0.5f, 0.5f);
                        totalTime = mp.getDuration();
                        songName.setText("Noi ta cho em");
                        currentSong = 1;
                        mp.start();
                        playBtn.setBackgroundResource(R.drawable.pause);
                        break;
                }
            }
        });

        // Seek bar
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mp.seekTo(progress);
                    positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // update progress bar and time
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int current = msg.what;
            positionBar.setProgress(current);

            String playTimeText = createTimeLabel(current);
            playTime.setText(playTimeText);

            String endTimeText = createTimeLabel(totalTime - current);
            endTime.setText(endTimeText);
        }
    };

    public String createTimeLabel(int time) {
        String label = "";

        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        label = min + ":";

        if (sec < 10) label += "0";
        label += sec;

        return label;
    }

    public void playBtnClick(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);

        if(!mp.isPlaying()) {
            // Stop play music state
            mp.start();
            playBtn.setBackgroundResource(R.drawable.pause);
            media.startAnimation(animation);

        } else {
            // running state
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
            media.clearAnimation();
            
        }
    }

    private void map() {
        playBtn     = findViewById(R.id.play);
        leftBtn     = findViewById(R.id.left);
        rightBtn    = findViewById(R.id.right);

        positionBar = findViewById(R.id.position);
        media       = findViewById(R.id.media);

        playTime    = findViewById(R.id.playTime);
        endTime     = findViewById(R.id.endTime);
        songName    = findViewById(R.id.songName);
    }
}
