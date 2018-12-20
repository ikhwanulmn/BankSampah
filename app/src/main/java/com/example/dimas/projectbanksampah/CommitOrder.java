/*
    Ditulis oleh Ikhwanul Murtadlo
    Editor: Android Studio
    Compiler dan lib yang digunakan: Android Studio,
                  JRE 1.8.0_152-release-1024-b02 amd64
                  JVM OpenJDK 64-Bit Server VM by JetBrains.s.r.o
    Versi dan Upgrade History: 3.1.4
    Tanggal pembuatan software: 24 Juli 2018
    Deskripsi singkat tentang software: Android Studio adalah Integrated Development Enviroment (IDE) untuk sistem operasi Android
*/

package com.example.dimas.projectbanksampah;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommitOrder extends Fragment implements View.OnClickListener {

    private TextView name, address, tanggal, waktu, phone;
    private Button proceed, cancel;
    private ProgressBar progressBar;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId, data;
    private UserData dataUser;
    private OrderData dataOrder;

    public CommitOrder() {
    }

    // Memasukkan data order, user, dan order ke
    public CommitOrder(String orderId, UserData dataUser, OrderData dataOrder) {
        this.data = orderId;
        this.dataUser = dataUser;
        this.dataOrder = dataOrder;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Mengembalikan ke tampilan
        //change R.layout.yourlayoutfilename for each of your fragments
        View v = inflater.inflate(R.layout.createorder_fragment, container, false);
        super.onCreate(savedInstanceState);

        //Mendapatkan instance autentikasi firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        userId=user.getUid();

        //mendapatkan data dari tabel order pada database
        mFirebaseDatabase = mFirebaseInstance.getReference("order");

        // get reference to 'users' node
        userId = user.getUid();

        // Aksi jika memilih "Lanjutkan" pada rincian permintaan
        proceed = (Button) v.findViewById(R.id.proceed);
        proceed.setOnClickListener(this);

        // Aksi jika memilih "Batal" pada rincian permintaan
        cancel = (Button) v.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        // Memasukkan data order ke database
        name = (TextView) v.findViewById(R.id.namaPemesan);
        address = (TextView) v.findViewById(R.id.alamatPemesan);
        tanggal = (TextView) v.findViewById(R.id.tanggalPengPemesan);
        waktu = (TextView) v.findViewById(R.id.waktuPengPemesan);
        phone = (TextView) v.findViewById(R.id.noHPPemesan);

        // Menampilkan data ke tampilan rincian permintaan
        name.setText(dataUser.name);
        address.setText(dataOrder.alamat);
        tanggal.setText(dataOrder.tanggal);
        waktu.setText(dataOrder.waktu);
        phone.setText(dataUser.phone);

        return v;
    }

    // Ketentuan jumlah poin yang akan masuk untuk setiap kategori sampah
    public void addPoints(OrderData data){

        if (data.tipeSampah.equals("Botol Kaca (1 poin/kg)") ) {
            dataUser.points += data.berat;
        }
        else if (data.tipeSampah.equals("Botol Platik (2 poin/kg)")){
            dataUser.points += data.berat * 2;
        }
        else if (data.tipeSampah.equals("Paralon (3 poin/kg)")){
            dataUser.points += data.berat * 3;
        }
        else if (data.tipeSampah.equals("Kardus (4 poin/kg)")){
            dataUser.points += data.berat * 4;
        }
        else if (data.tipeSampah.equals("Kertas (5 poin/kg)")){
            dataUser.points += data.berat * 5;
        }

        // Mengambil users dari database untuk diproses
        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        // Menambahkan poin ke tabel user pada database
        mFirebaseDatabase.child(userId).child("points").setValue(dataUser.points);
    }
    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()) {
            // Perintah jika tombol "lanjutkan" dipilih
            // Menyimpan data order yang telah masuk di database
            case R.id.proceed:
                fragment = new HomeActivity();
                addPoints(this.dataOrder);
                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;
            // Perintah jika tombol "Batal" dipilih
            // Menghapus data order yang telah masuk ke database.
            // Karena semua data sebenarnya akan dimasukkan terlebih dahulu ke database saat pengguna melanjutkan ke rincian permintaan.
            // Jika pengguna menyetujui, maka data akan disimpan.
            // jika tidak dan pengguna memilih "batal", maka data order yang telah masuk sebeumnya dihapus karena dibatalkan oleh pengguna
            case R.id.cancel:

                mFirebaseDatabase.child(data).removeValue();
                fragment = new HomeActivity();

                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }
                break;
        }
    }
}
