import { Edit3, Trash2 } from "lucide-react";

const NoteDetail = ({ note, onBack, onEdit, onDelete }) => {
  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-10">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <button
              onClick={onBack}
              className="text-gray-600 hover:text-gray-900 font-medium transition-colors duration-200"
            >
              ← Back to Notes
            </button>
            <div className="flex items-center space-x-3">
              <button
                onClick={() => onEdit(note)}
                className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition-all duration-200 font-medium flex items-center space-x-2"
              >
                <Edit3 className="w-4 h-4" />
                <span>Edit</span>
              </button>
              <button
                onClick={() => onDelete(note.id)}
                className="bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-600 transition-all duration-200 font-medium flex items-center space-x-2"
              >
                <Trash2 className="w-4 h-4" />
                <span>Delete</span>
              </button>
            </div>
          </div>
        </div>
      </header>

      <main className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
          <div className="mb-6">
            <h1 className="text-3xl font-bold text-gray-900 mb-2">{note.title}</h1>
            <p className="text-sm text-gray-500">
              Last updated: {new Date(note.updatedAt || note.createdAt).toLocaleDateString()}
            </p>
          </div>
          <div className="prose max-w-none">
            <div className="whitespace-pre-wrap text-gray-700 leading-relaxed text-base">
              {note.content}
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default NoteDetail;