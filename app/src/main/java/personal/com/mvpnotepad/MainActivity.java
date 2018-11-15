package personal.com.mvpnotepad;

import personal.com.mvpnotepad.MVPInterfaces.*;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity implements RequiredViewOps,View.OnClickListener{
    private EditText editText;
    private RecyclerView recyclerView;
    private ProvidedPresenterOps providedPresenterOps;
    private NoteListAdapter mListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text_input);
        recyclerView = findViewById(R.id.notes_recycler_view);
        mListAdapter = new NoteListAdapter();
        recyclerView.setAdapter(mListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setUpMVP();
        notifyDataSetChanged();

    }

    private void setUpMVP() {
        // Create the Presenter
        MainPresenter presenter = new MainPresenter(this);
        // Create the Model
        MainModel model = new MainModel(presenter);
        // Set Presenter model
        presenter.setModel(model);
        // Set the Presenter as a interface
        providedPresenterOps = presenter;
    }
    @Override
    public Context getContext() {
        return this;
    }
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void notifyItemInserted(int layoutPosition) {
        mListAdapter.notifyItemInserted(layoutPosition);
    }
    @Override
    public void notifyItemRangeChanged(int startPosition, int itemCount) {
        mListAdapter.notifyItemRangeChanged(startPosition, itemCount);
    }
    @Override
    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress(){

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


    @Override
    public void cleanEditingText() {
        editText.setText("");
    }

    @Override
    public void onClick(View v) {

    }

    public void newNote(View view) {
        System.out.println("clicked form view");
        providedPresenterOps.addNewNote(editText);
    }

    private class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder>{
        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return providedPresenterOps.createViewHolder(parent, viewType);
        }
        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
            providedPresenterOps.bindViewHolder(holder,position);
        }
        @Override
        public int getItemCount() {
            return providedPresenterOps.getNoteCount();
        }
    }
}
