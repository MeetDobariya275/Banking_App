import React, { useState } from "react";
import './App.css';

export default function FixedDeposit() {
  const [amount, setAmount] = useState("");
  const [durationYears, setDurationYears] = useState("");
  const [result, setResult] = useState("");

  //Calculation and Submission of fixed deposit
  const handleFixedDeposit = () => {
    fetch("/fixedDeposit", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        amount: parseFloat(amount),
        durationYears: parseInt(durationYears),
      }),
    })
      .then((res) => res.text()) // Plain text response from backend
      .then((data) => {
        console.log(data); // ' Fixed Deposit Created Successfully. Maturity Amount: 100.00 '
        setResult(data); // Display result on frontend
      })
      .catch((err) => {
        console.error("Error:", err);
        setResult("Failed to create Fixed Deposit.");
      });
  };

  return (
    <div>
      <h1>Fixed Deposit</h1>
      <div>
        Amount:
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}  //Update amount
          placeholder="Enter amount"
        />
      </div>
      <div>
        Duration (Years):
        <input
          type="number"
          value={durationYears}
          onChange={(e) => setDurationYears(e.target.value)}
          placeholder="Enter duration"
        />
      </div>
      <button onClick={handleFixedDeposit}>Calculate Maturity Amount</button>

      {result && (
        <div style={{ marginTop: "20px", fontWeight: "bold", color: "green" }}>
          {result}   //Display maturity amount
        </div>
      )}
    </div>
  );
}
