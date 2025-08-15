import TokenManager from './tokenManager';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
const API = {
  request: async (url, options = {}) => {
    const publicPaths = ['/login', '/signup'];
    const isPublic = publicPaths.includes(url);

    const token = TokenManager.get();
    const config = {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...(!isPublic && token ? { Authorization: `Bearer ${token}` } : {}),
        ...options.headers,
      },
    };

    try {
      const response = await fetch(`${API_BASE_URL}${url}`, config);

      if (response.status === 401) {
        TokenManager.remove();
        window.location.reload();
        return;
      }

      // Check if response has content before trying to parse JSON
      const contentType = response.headers.get('content-type');
      const hasJsonContent = contentType && contentType.includes('application/json');
      
      // For successful responses, try to parse JSON
      if (response.ok && hasJsonContent) {
        const data = await response.json();
        return { success: true, data, status: response.status };
      }
      
      // For error responses, try to get error message
      if (!response.ok) {
        let errorMessage = `HTTP ${response.status}`;
        
        if (hasJsonContent) {
          try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorData.error || errorMessage;
          } catch (e) {
            // If JSON parsing fails, use status text
            errorMessage = response.statusText || errorMessage;
          }
        } else {
          // Try to get text response for non-JSON errors
          try {
            const textResponse = await response.text();
            errorMessage = textResponse || response.statusText || errorMessage;
          } catch (e) {
            errorMessage = response.statusText || errorMessage;
          }
        }
        
        return { 
          success: false, 
          error: errorMessage, 
          status: response.status 
        };
      }

      // For successful responses without JSON content
      return { success: true, status: response.status };
      
    } catch (error) {
      console.error('API request failed:', error);
      return { 
        success: false, 
        error: error.message || 'Network error occurred',
        status: 0 
      };
    }
  },

  auth: {
    login: async (credentials) => {
      const response = await API.request('/login', {
        method: 'POST',
        body: JSON.stringify(credentials),
      });
      
      if (response.success) {
        return response.data;
      } else {
        throw new Error(response.error || 'Login failed');
      }
    },
    
    signup: async (credentials) => {
      const response = await API.request('/signup', {
        method: 'POST',
        body: JSON.stringify(credentials),
      });
      
      if (response.success) {
        return response.data;
      } else {
        throw new Error(response.error || 'Signup failed');
      }
    },
  },

  notes: {
    getAll: async () => {
      const response = await API.request('/notes');
      if (response.success) {
        return response.data;
      } else {
        throw new Error(response.error || 'Failed to fetch notes');
      }
    },
    
    create: async (note) => {
      const response = await API.request('/notes', {
        method: 'POST',
        body: JSON.stringify(note),
      });
      if (response.success) {
        return response.data;
      } else {
        throw new Error(response.error || 'Failed to create note');
      }
    },
    
    update: async (id, note) => {
      const response = await API.request(`/notes/${id}`, {
        method: 'PUT',
        body: JSON.stringify(note),
      });
      if (response.success) {
        return response.data;
      } else {
        throw new Error(response.error || 'Failed to update note');
      }
    },
    
    delete: async (id) => {
      const response = await API.request(`/notes/${id}`, { 
        method: 'DELETE' 
      });
      if (!response.success) {
        throw new Error(response.error || 'Failed to delete note');
      }
      return response;
    },
  },
};

export default API;