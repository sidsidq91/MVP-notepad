package personal.com.mvpnotepad;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    public NoteViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.recycler_text);
    }
    public void setTextView(String text){
        textView.setText(text);
    }
}
