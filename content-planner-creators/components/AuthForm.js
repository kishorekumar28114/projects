import React, { useState } from 'react';

const AuthForm = ({ onAuthSuccess }) => {
  const [mode, setMode] = useState('login');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    const endpoint = mode === 'login' ? '/api/auth/login' : '/api/auth/register';
    try {
      const res = await fetch(endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });
      const data = await res.json();
      if (res.ok) {
        if (onAuthSuccess) onAuthSuccess(username);
      } else {
        setError(data.message || 'Something went wrong');
      }
    } catch (err) {
      setError('Network error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-form-container">
      <h2>{mode === 'login' ? 'Login' : 'Register'}</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} required />
        <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required />
        <button type="submit" disabled={loading}>{loading ? 'Loading...' : mode === 'login' ? 'Login' : 'Register'}</button>
      </form>
      <button onClick={() => setMode(mode === 'login' ? 'register' : 'login')} className="toggle-btn">{mode === 'login' ? 'Need an account? Register' : 'Already have an account? Login'}</button>
      {error && <div className="error-msg">{error}</div>}
    </div>
  );
};

export default AuthForm;
