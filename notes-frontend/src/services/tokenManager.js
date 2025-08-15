const TokenManager = {
  get: () => localStorage.getItem('jwt_token'),
  set: (token) => localStorage.setItem('jwt_token', token),
  remove: () => localStorage.removeItem('jwt_token'),
  isValid: () => {
    const token = TokenManager.get();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }
};

export default TokenManager;
