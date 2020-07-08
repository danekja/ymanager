import React from 'react';
import './App.css';


function Login() {
console.log(window.config)
  return (
    <div className="login-container column">
     
    <a href={window.config.baseUrl + '/login/google?target=' + window.config.redirectUrl}><h1>log in</h1></a>
     

    </div>
  )
}

export default Login;