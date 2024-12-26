import React, { useState } from 'react';

export default function CurrencyConversion() {
  const [amount, setAmount] = useState(''); // State for user input
  const [conversionResult, setConversionResult] = useState({}); // State for conversion results
  const [error, setError] = useState(''); // State for error messages

  // Handle conversion logic
  const handleConversion = () => {
    setError(''); // Reset error
    setConversionResult({}); // Reset previous results

    // Input validation
    if (!amount || isNaN(amount) || Number(amount) <= 0) {
      setError('Please enter a valid positive amount.');
      return;
    }

    // Make API call to backend
    fetch('/currencyConversion', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ amount }),
    })
      .then((res) => res.json())
      .then((data) => {
        // Check if API response is valid
        if (data.status && data.data && data.data.length > 0) {
          const conversionValues = data.data[0].conversionValues;
          setConversionResult(conversionValues); // Update conversion results
        } else {
          throw new Error('No conversion data returned.');
        }
      })
      .catch((err) => {
        console.error('Error fetching conversion:', err.message);
        setError('Conversion failed. No conversion data returned.');
      });
  };

  return (
    <div>
      <h1>Currency Conversion</h1>

      {/* Input field and Convert button */}
      <div>
        Amount:
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          placeholder="Enter amount"
        />
        <button onClick={handleConversion}>Convert</button>
      </div>

      {/* Display conversion results */}
      {conversionResult && Object.keys(conversionResult).length > 0 && (
        <div>
          <h2>Conversion Results</h2>
          <ul>
            {Object.entries(conversionResult).map(([currency, value]) => (
              <li key={currency}>
                {currency}: {value}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* Display error message */}
      {error && (
        <div style={{ color: 'red', marginTop: '10px' }}>
          <strong>Error:</strong> {error}
        </div>
      )}
    </div>
  );
}
