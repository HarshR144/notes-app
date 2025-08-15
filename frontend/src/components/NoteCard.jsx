import { Edit3, Eye, Trash2 } from "lucide-react";

const NoteCard = ({ note, onView, onEdit, onDelete }) => {
  const firstLine = note.content.split('\n')[0].substring(0, 100) + 
    (note.content.length > 100 ? '...' : '');

  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-6 hover:shadow-md transition-all duration-200 group">
      <div className="flex justify-between items-start mb-3">
        <h3 className="text-lg font-semibold text-gray-900 group-hover:text-indigo-600 transition-colors duration-200 cursor-pointer"
            onClick={() => onView(note)}>
          {note.title}
        </h3>
        <div className="flex space-x-2 opacity-0 group-hover:opacity-100 transition-opacity duration-200">
          <button
            onClick={() => onView(note)}
            className="p-2 text-gray-500 hover:text-indigo-600 hover:bg-indigo-50 rounded-lg transition-all duration-200"
          >
            <Eye className="w-4 h-4" />
          </button>
          <button
            onClick={() => onEdit(note)}
            className="p-2 text-gray-500 hover:text-green-600 hover:bg-green-50 rounded-lg transition-all duration-200"
          >
            <Edit3 className="w-4 h-4" />
          </button>
          <button
            onClick={() => onDelete(note.id)}
            className="p-2 text-gray-500 hover:text-red-600 hover:bg-red-50 rounded-lg transition-all duration-200"
          >
            <Trash2 className="w-4 h-4" />
          </button>
        </div>
      </div>
      <p className="text-gray-600 text-sm leading-relaxed cursor-pointer"
         onClick={() => onView(note)}>
        {firstLine}
      </p>
      <div className="mt-4 text-xs text-gray-400">
        {new Date(note.updatedAt || note.createdAt).toLocaleDateString()}
      </div>
    </div>
  );
};

export default NoteCard;