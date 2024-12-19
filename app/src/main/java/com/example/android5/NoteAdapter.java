package com.example.android5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes; // Список заметок, которые нужно отобразить
    private OnNoteClickListener listener; // Интерфейс для обработки кликов и событий чекбокса

    public interface OnNoteClickListener {
        void onNoteClick(Note note); // Вызывается при клике на заметку
        void onCheckboxToggle(Note note, boolean isChecked); // Вызывается при изменении состояния чекбокса
    }

    public NoteAdapter(List<Note> notes, OnNoteClickListener listener) {
        this.notes = notes; // Инициализация списка заметок
        this.listener = listener; // Установка слушателя для обработки событий
    }
    // Вызывается RecyclerView, когда требуется создать новый элемент списка
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()) //Преобразуем XML-файл разметки и преобразует его в объект View
                .inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }
    //Вызывается RecyclerView, чтобы связать данные (объект Note) с уже созданным элементом списка.
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position); // Получаем заметку по позиции
        holder.title.setText(note.getTitle()); // Устанавливаем заголовок заметки
        holder.checkBox.setChecked(note.isCompleted()); // Устанавливаем состояние чекбокса

        // Добавляем обработчик кликов на элемент
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoteClick(note); // Передаем заметку слушателю
            }
        });

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            note.setCompleted(isChecked); // Обновляем состояние заметки
            if (listener != null) {
                listener.onCheckboxToggle(note, isChecked); // Сообщаем слушателю об изменении чекбокса
            }
        });
    }
    //Определяет сколько элементов нужно отобразить в списке
    @Override
    public int getItemCount() {
        return notes.size(); // Возвращает количество заметок в списке
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView content;
        private ImageView image;
        private CheckBox checkBox;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title); // Связываем поле с TextView для заголовка
            content = itemView.findViewById(R.id.note_content); // Для содержимого
            image = itemView.findViewById(R.id.note_image); // Для изображения
            checkBox = itemView.findViewById(R.id.note_checkbox); // Для чекбокса
        }
    }
}