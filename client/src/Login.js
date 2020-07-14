import React from 'react';
import './App.css';


function Login() {
  return (
    <div className="login-container column">  
      <a href={window.config.baseUrl + '/login/google?target=' + window.config.redirectUrl}><p>log in</p></a>
    </div>
  )
}

export default Login;