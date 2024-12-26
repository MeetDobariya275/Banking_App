import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function DeleteUser() {
  const navigate = useNavigate();

  // Function to delete the user
  const handleDeleteUser = () => {
    fetch('/deleteUser', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
    })
      .then((res) => {
        if (res.ok) {
          alert('User deleted successfully!');
          navigate('/'); // Redirect to login page after deletion
        } else {
          throw new Error('Failed to delete user');
        }
      })
      .catch((err) => {
        console.error('Error deleting user:', err);
        alert('Failed to delete user');
      });
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Delete User</h1>
      <p>Are you sure you want to delete your account?</p>
      <button
        onClick={handleDeleteUser}
        style={{
          padding: '10px 20px',
          backgroundColor: 'red',
          color: 'white',
          border: 'none',
          cursor: 'pointer',
          fontSize: '16px',
        }}
      >
        Delete Account
      </button>
    </div>
  );
}
