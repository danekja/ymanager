import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

fetch('config.json')
  .then((response) => {
    if (response.ok) {
      return response.json();
    } else {
      throw new Error('Couldn\'t fetch config.json!');
    }
  })
  .then(config => {
    window.config = config;

    ReactDOM.render(
      <React.StrictMode>
        <App />
      </React.StrictMode>,
      document.getElementById('root'),
    );
  })
  .catch(reason => {
    console.error(reason);
  });

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
