import React, { useState, useEffect } from 'react';
import './App.css';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const [amount, setAmount] = useState('');
  const [transactions, setTransactions] = useState([]);
  const [toId, setToId] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleAmountChange = (e) => setAmount(e.target.value);

  // Fetch transactions
  const getTransactions = () => {
    fetch('/getTransactionHistory', { method: 'POST', credentials: 'include' })
      .then((res) => res.json())
      .then((data) => {
        if (data.status) {
          setTransactions(data.data); // Save all fetched transactions
        } else {
          console.log('Error fetching transactions:', data.message);
        }
      })
      .catch((e) => console.log('Error fetching transactions:', e.message));
  };


  // Deposit money
  const handleDeposit = () => {
    fetch('/createDeposit', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount }),
      credentials: 'include',
    })
      .then(getTransactions)
      .catch(() => console.log('Deposit failed'));
    setAmount('');
  };

  // Withdraw money
  const handleWithdraw = () => {
    fetch('/withdraw', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount }),
      credentials: 'include',
    })
      .then(getTransactions)
      .catch(() => console.log('Withdraw failed'));
    setAmount('');
  };

  // Transfer money
  const handleTransfer = () => {
    fetch('/transfer', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount, toId }),
      credentials: 'include',
    })
      .then((res) => res.json())
      .then((data) => {
        if (data.status) {
          getTransactions();
          setAmount('');
          setError('');
        } else {
          setError(data.message);
        }
      })
      .catch(() => setError('Failed to make transfer'));
  };

  const logOut = () => {
    document.cookie = '';
    navigate('/');
  };

  useEffect(() => getTransactions(), []);

  return (
    <div>
      <h1>Home Page</h1>
      <button onClick={logOut}>Log Out</button>

      <div>
        Amount: <input value={amount} onChange={handleAmountChange} />
        <button onClick={handleDeposit} disabled={!amount}>Deposit</button>
        <button onClick={handleWithdraw} disabled={!amount}>Withdraw</button>
      </div>

      <div>
        Transfer to ID:
        <input value={toId} onChange={(e) => setToId(e.target.value)} />
        <button onClick={handleTransfer} disabled={!amount || !toId}>Transfer</button>
      </div>
      {error && <div style={{ color: 'red' }}>{error}</div>}

      <h2>Transaction History</h2>
      <table border="1">
        <thead>
          <tr>
            <th>Amount</th>
            <th>Type</th>
            <th>Time</th>
            <th>From</th>
            <th>To</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((tx, index) => (
            <tr key={index}>
              <td>{tx.amount}</td>
              <td>{tx.transactionType}</td>
              <td>{new Date(tx.timestamp).toLocaleString()}</td>
              <td>{tx.userId || '-'}</td>
              <td>{tx.toId || '-'}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/currency-conversion')}>Currency Conversion</button>
      <button onClick={() => navigate("/fixed-deposit")}>
        Go to Fixed Deposit
      </button>
      <button onClick={() => navigate("/delete-user")}>
        Delete user
      </button>

    </div>
  );
}
