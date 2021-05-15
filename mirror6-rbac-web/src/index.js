import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App'
import memoryUtil from './utils/memoryUtil'
import storageUtil from './utils/storageUtil'

const user = storageUtil.getUser();
memoryUtil.user = user;
ReactDOM.render(
  <App />,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
