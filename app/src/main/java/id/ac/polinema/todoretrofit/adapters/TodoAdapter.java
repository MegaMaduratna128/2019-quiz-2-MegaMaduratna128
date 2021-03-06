package id.ac.polinema.todoretrofit.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import id.ac.polinema.todoretrofit.R;
import id.ac.polinema.todoretrofit.models.Todo;

//public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
//
//    private Context context;
//    private List<Todo> items;
//    private OnTodoClickedListener listener;
//
//    public TodoAdapter(Context context, OnTodoClickedListener listener) {
//        this.context = context;
//        this.listener = listener;
//    }
//
//    public void setItems(List<Todo> items) {
//        this.items = items;
//        this.notifyDataSetChanged();
//    }
//
//    public void setListener(OnTodoClickedListener listener) {
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context)
//                .inflate(R.layout.item_todo, viewGroup, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        Todo todo = items.get(i);
//        viewHolder.bind(todo, listener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return (items != null) ? items.size() : 0;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView todoText;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            todoText = itemView.findViewById(R.id.text_todo);
//        }
//
//        public void bind(final Todo todo, final OnTodoClickedListener listener) {
//            todoText.setText(todo.getTodo());
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onClick(todo);
//                }
//            });
//        }
//    }
//
//    public interface OnTodoClickedListener {
//        void onClick(Todo todo);
//    }
//}

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    private List<Todo> items;
    private OnTodoClickedListener listener;
    private OnTodoClickedDeletedListener listener1;

    public TodoAdapter(Context context, OnTodoClickedListener listener, OnTodoClickedDeletedListener listener1) {
        this.context = context;
        this.listener = listener;
        this.listener1 = listener1;
    }

    public void setItems(List<Todo> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public List<Todo> getItems() {
        return items;
    }

    public void setListener(OnTodoClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_todo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Todo todo = items.get(i);
        viewHolder.bind(todo, listener, listener1);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        CardView cardView;
        AppCompatCheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.text_todo);
            checkbox = itemView.findViewById(R.id.done);
        }

        public void bind(final Todo todo, final OnTodoClickedListener listener, final OnTodoClickedDeletedListener listener1) {
            todoText.setText(todo.getTodo());
            checkbox.setChecked(todo.getDone());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder db = new AlertDialog.Builder(context);
                    db.setTitle("Choice");

                    db.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface db, int which) {
                            listener.onClick(todo);
                        }
                    });
                    db.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface db, int which) {
                            listener1.onClickDeleted(todo);
                        }
                    });
                    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (compoundButton.isChecked()) {
                                todo.setDone(true);
                                Toast.makeText(context, "todo is done", Toast.LENGTH_SHORT).show();
                                listener.onCheck(todo);
                            } else {
                                todo.setDone(false);
                                Toast.makeText(context, "todo isn't done", Toast.LENGTH_SHORT).show();
                                listener.onCheck(todo);
                            }
                        }
                    });
                    db.create().show();
                }
            });
        }
    }

    public interface OnTodoClickedListener {
        void onClick(Todo todo);
        void onCheck(Todo todo);
    }
    public interface OnTodoClickedDeletedListener {
        void onClickDeleted(Todo todo);
    }


}
