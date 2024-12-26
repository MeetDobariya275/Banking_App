import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Login from './Login';
import Signup from './Signup';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './Home';
import CurrencyConversion from './CurrencyConversion';
import FixedDeposit from './FixedDeposit';
import DeleteUser from './DeleteUser';

const router = createBrowserRouter([
  { path: '/', element: <Login /> },
  { path: '/signup', element: <Signup /> },
  { path: '/home', element: <Home /> },
  { path: '/currency-conversion', element: <CurrencyConversion /> },
  { path: "/fixed-deposit", element: <FixedDeposit /> },
  { path: '/delete-user', element: <DeleteUser /> }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

reportWebVitals();
