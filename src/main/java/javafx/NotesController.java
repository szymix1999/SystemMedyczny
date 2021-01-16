package javafx;

import database.DbConnector;
import database.DbStatements;
import javafx.Administration.SQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotesController {

    Connection c = DbConnector.connect();
    ObservableList notes = FXCollections.observableArrayList();

    @FXML
    private ListView<Note> notesListView;
    @FXML
    private TextArea ATxtContents;

    private static class Note {
        private final int id;
        private String contents;
        private final int index;

        public Note(int id, int index, String contents) {
            this.id = id;
            this.index = index;
            this.contents = contents;
        }

        @Override
        public String toString() {
            return (String.format("%03d", index));
        }

        public int getId() {
            return id;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }
    }

    @FXML
    private void initialize() {
        notes.clear();
        notesListView.getItems().clear();

        try {
            ResultSet rs = DbStatements.getNotes(c, DbStatements.id);
            int index = 0;
            while (rs.next()) {
                Note n = new Note(rs.getInt("id"), index, rs.getString("comments"));
                System.out.println("Initialized note id: " + n.getId());
                notes.add(n);
                index++;
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        notesListView.getItems().addAll(notes);
    }

    @FXML
    private void addNote() {
        if(ATxtContents.getText().equals("")) return;

        try {
            int index = DbStatements.addNote(c, DbStatements.id, ATxtContents.getText());
            Note n = new Note(index, notes.size(), ATxtContents.getText());
            System.out.println("Added note id: " + n.getId());
            notes.add(n);
            notesListView.getItems().add(n);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void removeNote() {
        if(notesListView.getSelectionModel().getSelectedItem() == null) return;

        try {
            System.out.println("Removed note id: " + notesListView.getSelectionModel().getSelectedItem().getId());

            DbStatements.deleteNote(c, notesListView.getSelectionModel().getSelectedItem().getId());
            notes.remove(notesListView.getSelectionModel().getSelectedIndex());
            notesListView.getItems().remove(notesListView.getSelectionModel().getSelectedIndex());

            ATxtContents.clear();
            displayNote();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void displayNote() {
        if(notesListView.getSelectionModel().getSelectedItem() == null) return;

        ATxtContents.setText(notesListView.getSelectionModel().getSelectedItem().getContents());
    }

    @FXML
    private void saveChanges() {
        if(notesListView.getSelectionModel().getSelectedItem() == null) return;

        try {
            DbStatements.updateNote(c, notesListView.getSelectionModel().getSelectedItem().getId(), ATxtContents.getText());
            notesListView.getSelectionModel().getSelectedItem().setContents(ATxtContents.getText());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
