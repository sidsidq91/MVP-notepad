package personal.com.mvpnotepad;
import java.util.ArrayList;

import personal.com.mvpnotepad.MVPInterfaces.*;

public class MainModel implements ProvidedModelOps {

    private RequiredPresenterOps requiredPresenterOps;
    public ArrayList<Note> notes;
    private DBManager DbManager;

    public MainModel(RequiredPresenterOps requiredPresenterOps){
        this.requiredPresenterOps = requiredPresenterOps;
        DbManager = new DBManager(this.requiredPresenterOps.getActivityContext());
    }

    @Override
    public int getNotesCount() {
        if (notes!=null){
            return notes.size();
        }
        return 0;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int insertNote(Note note) {
        Note insertedNote = DbManager.insertNote(note);
        if (insertedNote!=null){
            loadData();
            return getNotePosition(insertedNote);
        }
        return -1;
    }

    @Override
    public boolean loadData() {
        notes = DbManager.getAllNotes();
        return notes != null;
    }

    private int getNotePosition(Note note){
        System.out.println("from get note position"+notes.indexOf(note));
        return note.getId();
    }
}
