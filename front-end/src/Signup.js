import React, { useState } from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

export default function Signup() {
  const [formData, setFormData] = useState({
    userName: '',
    password: '',
    bio: '',
    email: '',
    billingAddress: '',
    mobileNo: '',
  });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = () => {
    setMessage('');
    fetch('/createUser', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(formData),
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status) {
          setMessage('Account created successfully!');
          navigate('/'); // Redirect to Login page
        } else {
          setMessage(data.message || 'Failed to create account.');
        }
      })
      .catch(() => setMessage('Server error occurred.'));
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Signup Page</h1>
      <div style={{ color: 'red', marginBottom: '10px' }}>{message}</div>
      <div>
        <input name="userName" placeholder="Username" onChange={handleChange} />
      </div>
      <div>
        <input name="password" type="password" placeholder="Password" onChange={handleChange} />
      </div>
      <div>
        <input name="bio" placeholder="Bio" onChange={handleChange} />
      </div>
      <div>
        <input name="email" type="email" placeholder="Email" onChange={handleChange} />
      </div>
      <div>
        <input name="billingAddress" placeholder="Billing Address" onChange={handleChange} />
      </div>
      <div>
        <input name="mobileNo" placeholder="Mobile Number" onChange={handleChange} />
      </div>
      <div>
        <button onClick={handleSubmit}>Create Account</button>
      </div>
      <div style={{ marginTop: '10px' }}>
        Already have an account? <button onClick={() => navigate('/')}>Go to Login</button>
      </div>
    </div>
  );
}
