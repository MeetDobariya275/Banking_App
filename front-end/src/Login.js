import React, { useState } from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

export default function Login() {
  const [userName, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const updateUserName = (e) => setUserName(e.target.value);
  const updatePassword = (e) => setPassword(e.target.value);

  // Register user
  const register = () => {
    setMessage('');
    const userDto = { userName, password };
    fetch('/createUser', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userDto),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status) {
          setMessage('Account created successfully!');
          setUserName('');
          setPassword('');
        } else {
          setMessage(data.message || 'Failed to create account.');
        }
      })
      .catch(() => setMessage('Error occurred while creating account.'));
  };

  // Login user
  const logIn = () => {
    setMessage('');
    const userDto = { userName, password };
    fetch('/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(userDto),
    })
      .then((res) => {
        if (res.ok) {
          navigate('/home'); // Redirect to home on success
        } else {
          setMessage('Invalid username or password');
        }
      })
      .catch(() => setMessage('Server error. Unable to log in.'));
  };

  return (
    <div>
      <h1>Login Page</h1>
      <div style={{ color: 'red' }}>{message}</div>
      <div>
        Username: <input value={userName} onChange={updateUserName} />
      </div>
      <div>
        Password: <input type="password" value={password} onChange={updatePassword} />
      </div>
      <div>
        <div style={{ marginTop: '10px' }}>
          <button onClick={() => navigate('/signup')}>Create an Account</button>
        </div>
        <button onClick={logIn}>Login</button>
      </div>
    </div>
  );
}
