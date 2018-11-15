package personal.com.mvpnotepad;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;

public interface MVPInterfaces {

    interface RequiredViewOps{
        Context getContext();
        Context getAppContext();
        void notifyItemInserted(int layoutPosition);
        void notifyItemRangeChanged(int startPosition, int itemCount);
        void notifyDataSetChanged();
        void showProgress();
        void cleanEditingText();
        void hideProgress();
        void showToast(String message);
    }

    interface RequiredPresenterOps{
        void setModel(MainModel model);
        Context getAppContext();
        Context getActivityContext();
    }

    interface ProvidedPresenterOps{
        void addNewNote(EditText editText);
        int getNoteCount();
        NoteViewHolder createViewHolder(ViewGroup parent, int viewType);
        void bindViewHolder(NoteViewHolder holder, int position);
    }

    interface ProvidedModelOps{
        int getNotesCount();
        Note getNote(int position);
        int insertNote(Note note);
        boolean loadData();
    }
}
