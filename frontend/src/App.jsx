import { useEffect, useState } from "react";
import TokenManager from "./services/tokenManager";
import API from "./services/api";
import LoginForm from "./components/LoginForm";
import NotesList from "./components/NoteList";
import NoteDetail from "./components/NoteDetail";
import NoteEditor from "./components/NoteEditor";

const App = () => {
  const [currentView, setCurrentView] = useState('loading');
  const [notes, setNotes] = useState([]);
  const [selectedNote, setSelectedNote] = useState(null);
  const [isSignup, setIsSignup] = useState(false);

  useEffect(() => {
    if (TokenManager.isValid()) {
      loadNotes();
    } else {
      setCurrentView('auth');
    }
  }, []);

  const loadNotes = async () => {
    try {
      const notesData = await API.notes.getAll();
      setNotes(notesData);
      setCurrentView('notes');
    } catch (error) {
      console.error('Failed to load notes:', error);
      setCurrentView('auth');
    }
  };

  const handleLogin = () => {
    loadNotes();
  };

  const handleLogout = () => {
    TokenManager.remove();
    setNotes([]);
    setSelectedNote(null);
    setCurrentView('auth');
  };

  const handleCreateNote = async (noteData) => {
    try {
      const newNote = await API.notes.create(noteData);
      setNotes([...notes, newNote]);
      setCurrentView('notes');
    } catch (error) {
      console.error('Failed to create note:', error);
    }
  };

  const handleUpdateNote = async (noteData) => {
    try {
      const updatedNote = await API.notes.update(selectedNote.id, noteData);
      setNotes(notes.map(note => 
        note.id === selectedNote.id ? updatedNote : note
      ));
      setCurrentView('notes');
    } catch (error) {
      console.error('Failed to update note:', error);
    }
  };

  const handleDeleteNote = async (noteId) => {
    if (!window.confirm('Are you sure you want to delete this note?')) return;
    
    try {
      await API.notes.delete(noteId);
      setNotes(notes.filter(note => note.id !== noteId));
      if (selectedNote?.id === noteId) {
        setSelectedNote(null);
        setCurrentView('notes');
      }
    } catch (error) {
      console.error('Failed to delete note:', error);
    }
  };

  const handleViewNote = (note) => {
    setSelectedNote(note);
    setCurrentView('detail');
  };

  const handleEditNote = (note) => {
    setSelectedNote(note);
    setCurrentView('edit');
  };

  const handleCreateNew = () => {
    setSelectedNote(null);
    setCurrentView('create');
  };

  if (currentView === 'loading') {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <div className="text-center">
          <div className="w-8 h-8 border-4 border-indigo-200 border-top-indigo-600 rounded-full animate-spin mx-auto mb-4"></div>
          <p className="text-gray-600">Loading...</p>
        </div>
      </div>
    );
  }

  if (currentView === 'auth') {
    return (
      <LoginForm
        onLogin={handleLogin}
        isSignup={isSignup}
        toggleMode={() => setIsSignup(!isSignup)}
      />
    );
  }

  if (currentView === 'notes') {
    return (
      <NotesList
        notes={notes}
        onView={handleViewNote}
        onEdit={handleEditNote}
        onDelete={handleDeleteNote}
        onCreateNew={handleCreateNew}
        onLogout={handleLogout}
      />
    );
  }

  if (currentView === 'detail') {
    return (
      <NoteDetail
        note={selectedNote}
        onBack={() => setCurrentView('notes')}
        onEdit={handleEditNote}
        onDelete={handleDeleteNote}
      />
    );
  }

  if (currentView === 'create') {
    return (
      <NoteEditor
        onSave={handleCreateNote}
        onCancel={() => setCurrentView('notes')}
        isEditing={false}
      />
    );
  }

  if (currentView === 'edit') {
    return (
      <NoteEditor
        note={selectedNote}
        onSave={handleUpdateNote}
        onCancel={() => setCurrentView('notes')}
        isEditing={true}
      />
    );
  }

  return null;
};

export default App;