package personal.com.mvpnotepad;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import personal.com.mvpnotepad.MVPInterfaces.*;
public class MainPresenter implements RequiredPresenterOps, ProvidedPresenterOps {
    private WeakReference<RequiredViewOps> mView;
    private ProvidedModelOps providedModelOps;

    public MainPresenter(RequiredViewOps viewOps){
        mView = new WeakReference<>(viewOps);
    }

    private RequiredViewOps getView() throws NullPointerException{
        if (mView!=null){
            return mView.get();
        }else {
            throw new NullPointerException("View is not available");
        }
    }

    @Override
    public void setModel(MainModel model) {
        providedModelOps = model;
    }

    @Override
    public Context getAppContext() {
        try {
            return getView().getAppContext();
        }catch (NullPointerException ex){
            return null;
        }
    }

    @Override
    public Context getActivityContext() {
        try {
            return getView().getContext();
        }catch (NullPointerException ex){
            return null;
        }
    }

    @Override
    public void addNewNote(EditText editText) {
        System.out.println("came to newNote in presenter");
        getView().showProgress();
        final String noteText = editText.getText().toString();
        if (!noteText.isEmpty()){
            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected void onPostExecute(Integer adapterPosition) {
                    try{
                        if (adapterPosition > -1){
                            System.out.println("passed > -1 if");
                            getView().cleanEditingText();
                            getView().notifyItemInserted(adapterPosition);
                            getView().notifyItemRangeChanged(adapterPosition, providedModelOps.getNotesCount());
                        }else {
                            getView().hideProgress();
                            getView().showToast("Error");
                        }
                    }catch (NullPointerException ex){

                    }
                }

                @Override
                protected Integer doInBackground(Void... voids) {
                    return providedModelOps.insertNote(makeNote(noteText));
                }
            }.execute();
        }else {
            try {
                getView().showToast("note is blank, nothing to add");
            }catch (NullPointerException ex){

            }

        }
    }

    @Override
    public int getNoteCount() {
        return providedModelOps.getNotesCount();
    }

    @Override
    public NoteViewHolder createViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void bindViewHolder(NoteViewHolder holder, int position) {
        final Note note = providedModelOps.getNote(position);
        holder.setTextView(note.getContent());
    }

    public Note makeNote(String noteText){
        Note note = new Note();
        note.setContent(noteText);
        note.setName("blah");
        note.setDate("evwv");
        return note;
    }
}