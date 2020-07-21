import React from 'react';
import './App.css';

export default function Login() {
  return (
    <div className="login-container column">
      <a href={window.config.baseUrl + '/login/google?target=' + window.config.redirectUrl}><p>log in</p></a>
    </div>
  );
}
