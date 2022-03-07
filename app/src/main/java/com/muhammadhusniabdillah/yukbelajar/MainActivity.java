package com.muhammadhusniabdillah.yukbelajar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, TextToSpeech.OnInitListener {

    private ZXingScannerView scanner;
    private TextToSpeech tts;
    Button btn_mulai, btn_boutme, btn_download, btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(this, this);
        scanner = new ZXingScannerView(this);
        btn_mulai = findViewById(R.id.btn_home_scan);
        btn_mulai.setOnClickListener(view -> {
            scanner.setResultHandler(this);
            //Minta Izin menggunakan kamera
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, 0);
            }
            scanner.startCamera();
            setContentView(scanner);
        });

        //untuk download materi
        btn_download = findViewById(R.id.btn_home_download);
        btn_download.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://drive.google.com/drive/folders/1u-oA-Q2MiuCtSOqSWcXKGrlk8OCD_mYL?usp=sharing");
            Intent download = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(download);
        });

        //tentang saya
        btn_boutme = findViewById(R.id.btn_home_boutme);
        btn_boutme.setOnClickListener(view -> {
            LayoutInflater fast = LayoutInflater.from(this);
            AlertDialog.Builder boutme = new AlertDialog.Builder(this);
            boutme.setTitle("Tentang Saya");
            View me = fast.inflate(R.layout.aboutme, null);
            boutme.setView(me);
            boutme.setPositiveButton("Kembali", ((dialogInterface, i) -> {
                dialogInterface.cancel();
            }));
            AlertDialog mee = boutme.create();
            mee.show();

        });

        //tombol bantuan
        btn_help = findViewById(R.id.btn_home_howto);
        btn_help.setOnClickListener(view -> {
            LayoutInflater fast = LayoutInflater.from(this);
            AlertDialog.Builder help = new AlertDialog.Builder(this);
            help.setTitle("Bantuan");
            View me = fast.inflate(R.layout.help, null);
            help.setView(me);
            help.setPositiveButton("Kembali", ((dialogInterface, i) -> {
                dialogInterface.cancel();
            }));
            AlertDialog mee = help.create();
            mee.show();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(this, this.getClass());
        a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanner.setResultHandler(this);
        scanner.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scanner.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("TAG", result.getText()); // Prints scan results
        Log.v("TAG", result.getBarcodeFormat().toString());
        LayoutInflater fast = LayoutInflater.from(this);

        //set gambar
            //profesi
        View polisi = fast.inflate(R.layout.polisi, null);
        View dokter = fast.inflate(R.layout.dokter, null);
        View guru = fast.inflate(R.layout.guru, null);
        View pemadam = fast.inflate(R.layout.pemadam, null);
        View petani = fast.inflate(R.layout.petani, null);
            //transportasi
        View bus = fast.inflate(R.layout.bus, null);
        View kereta = fast.inflate(R.layout.kereta, null);
        View mobil = fast.inflate(R.layout.mobil, null);
        View pesawat = fast.inflate(R.layout.pesawat, null);
        View sepeda = fast.inflate(R.layout.sepeda, null);

        //dialog start
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String test = result.getText();

        //cek isi QR code yang di scan
        if (result.getText().startsWith("polisi")) {
            builder.setTitle("Polisi");
            builder.setView(polisi);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("dokter")) {
            builder.setTitle("Dokter");
            builder.setView(dokter);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("guru")) {
            builder.setTitle("Guru");
            builder.setView(guru);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("pemadam")) {
            builder.setTitle("Pemadam Kebakaran");
            builder.setView(pemadam);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("petani")) {
            builder.setTitle("Petani");
            builder.setView(petani);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("bus")) {
            builder.setTitle("Bus");
            builder.setView(bus);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("kereta")) {
            builder.setTitle("Kereta Api");
            builder.setView(kereta);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("mobil")) {
            builder.setTitle("Mobil");
            builder.setView(mobil);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("pesawat")) {
            builder.setTitle("Pesawat");
            builder.setView(pesawat);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else if (result.getText().startsWith("sepeda")) {
            builder.setTitle("Sepeda Motor");
            builder.setView(sepeda);
            builder.setMessage(test.substring(test.lastIndexOf("/") + 1)); //mengambil text tertentu dari hasil scan qrcode
            jelaskan(test.substring(test.lastIndexOf("/") + 1)); //TTS here
            builder.setPositiveButton("Scan lagi :D", (dialog, id) -> {
                scanner.resumeCameraPreview(this);
            });
            builder.setNegativeButton("Sudah Tau kok D:", (dialog, id) -> {
                AlertDialog.Builder aw = new AlertDialog.Builder(this);
                aw.setTitle("Mau Belajar yang lain ?");
                aw.setMessage("Klik Buku di halaman menu ya! untuk belajar yang lain ^-^");
                aw.setPositiveButton("Mau belajar yang lain :)", (dialog1, id1) -> {
                    dialog1.cancel();
                    Intent a = new Intent(this, this.getClass());
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                });
                aw.setNegativeButton("Udahan dulu deh :(", (dialog1, id1) -> {
                    finishAffinity();
                    System.exit(0);
                });
                AlertDialog konf = aw.create();
                konf.show();

            });
        } else {
            jelaskan("Maaf, Tidak ada data Ebook dalam QR code ini. Kalau kamu belum download, download dulu Ebook nya di menu Buku!");
            AlertDialog.Builder aw = new AlertDialog.Builder(this);
            aw.setTitle("Download Ebook ?");
            aw.setMessage("Klik Buku di halaman menu ya! untuk mendownload materi");
            aw.setPositiveButton("Oke!", (dialog1, id1) -> {
                dialog1.cancel();
                Intent a = new Intent(this, this.getClass());
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
            });
            AlertDialog konf = aw.create();
            konf.show();
        }
        AlertDialog dialogwindow = builder.create();
        dialogwindow.show();
    }

    private void jelaskan(String isi) {
        tts.speak(isi, TextToSpeech.QUEUE_FLUSH, null, "");
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            tts.getDefaultVoice();
        }
    }
}