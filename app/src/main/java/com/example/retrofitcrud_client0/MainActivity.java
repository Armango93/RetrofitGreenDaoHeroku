package com.example.retrofitcrud_client0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.retrofitcrud_client0.bd.Book;
import com.example.retrofitcrud_client0.bd.BookDao;
import com.example.retrofitcrud_client0.bd.DaoSession;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private DaoSession mDaoSession = null;
    private BookDao mBooksItemDao = null;

   @BindView(R.id.btnAddBook)
    Button btnAddBook;

    @BindView(R.id.btnGetBookList)
    Button btnGetBookList;

    @BindView(R.id.listView)
    ListView listView;

    BookInterface bookInterface;
    List<Book> listOfBooks = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDaoSession = ((App)getApplication()).getDaoSession();
        mBooksItemDao = mDaoSession.getBookDao();
        mBooksItemDao.deleteAll();

        bookInterface = ApiUtils.getBookInterface();
    }

    @OnClick(R.id.btnAddBook)
    public void onClick() {
        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        intent.putExtra("bookName", "");
        startActivity(intent);
    }

    @OnClick(R.id.btnGetBookList)
    public void getBookList(){
        Call<List<Book>> call = bookInterface.getBooks();
        call.enqueue(new Callback<List<Book>>(){
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if(response.isSuccessful()){
                    listOfBooks = response.body();
                    List<Book> books = response.body();

                    mBooksItemDao.insertOrReplaceInTx(books);
                }
                listView.setAdapter(new BookAdapter(MainActivity.this, R.layout.listbook, mBooksItemDao.loadAll()));
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });
    }
}
