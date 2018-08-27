package com.example.retrofitcrud_client0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitcrud_client0.bd.Book;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookActivity extends AppCompatActivity {

    BookInterface bookInterface;

    @BindView(R.id.editBookId)
    EditText editFormId;

    @BindView(R.id.editTitle)
    EditText editFormTitle;

    @BindView(R.id.editAuthor)
    EditText editFormAuthor;

    @BindView(R.id.editDescription)
    EditText editFormDescription;

    @BindView(R.id.editDatePublish)
    EditText editFormPublishDate;

    @BindView(R.id.buttonSave)
    Button buttonSave;

    @BindView(R.id.buttonDelete)
    Button buttonDelete;

    Bundle extras;
    String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);


        bookInterface = ApiUtils.getBookInterface();
        extras = getIntent().getExtras();

        bookId = String.valueOf(extras.getLong("id"));
        String title = extras.getString("title");
        String author = extras.getString("author");
        String description = extras.getString("description");
        int published = extras.getInt("published");

        editFormId.setText(bookId);
        editFormTitle.setText(title);
        editFormAuthor.setText(author);
        editFormDescription.setText(description);
        editFormPublishDate.setText(published + "");

        if (bookId != null && bookId.trim().length() > 0) {
            editFormId.setFocusable(false);
        } else {
            buttonDelete.setVisibility(View.INVISIBLE);
            editFormId.setVisibility(View.INVISIBLE);
        }


/*
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setId(editFormId.getId());
                book.setTitle(editFormTitle.getText().toString());
                book.setAuthor(editFormAuthor.getText().toString());
                book.setDescription(editFormDescription.getText().toString());
                book.setPublished(Integer.parseInt(editFormPublishDate.getText().toString()));

                if(bookId!=null && bookId.trim().length()>0 && Integer.valueOf(bookId) != 0){
                    updateBook(Integer.parseInt(bookId), book);
                } else{
                    addBook(book);
                }
            }
        });
*/

/*
        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String userId = String.valueOf(extras.getInt("id"));

                deleteBook(Integer.parseInt(userId));
            }
        });
*/

    }

    @OnClick(R.id.buttonSave)
    public void saveBtnClicked() {
        Book book = new Book();
        book.setId((long) editFormId.getId());
        book.setTitle(editFormTitle.getText().toString());
        book.setAuthor(editFormAuthor.getText().toString());
        book.setDescription(editFormDescription.getText().toString());
        book.setPublished(Integer.parseInt(editFormPublishDate.getText().toString()));

        if (bookId != null && bookId.trim().length() > 0 && Integer.valueOf(bookId) != 0) {
            updateBook(Integer.parseInt(bookId), book);
        } else {
            addBook(book);
        }
    }

    @OnClick(R.id.buttonDelete)
    public void deleteBtnClicked() {
        final String userId = String.valueOf(extras.getLong("id"));
        deleteBook(Long.parseLong(userId));
    }


    public void addBook(Book book) {
        Call<Book> callBook = bookInterface.addBook(book);

        callBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Book created successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(BookActivity.this, "Book not created successfully!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookActivity.this, "Book has not save!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateBook(final int id, final Book book) {
        Call<Book> callBook = bookInterface.updateBook(id, book);
        callBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Book updated succesful! " + book.getId(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookActivity.this, "Book update was unsuccesful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteBook(final long id) {
        Call<Book> callBook = bookInterface.deleteBook(id);
        System.out.println(id);
        callBook.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookActivity.this, "Deletion was successful! " + id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BookActivity.this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(BookActivity.this, "Deletion was not executed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
