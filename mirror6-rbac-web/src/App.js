import React from 'react';
import './App.css';
import './reset.css';
import { ConfigProvider } from 'antd';

import { BrowserRouter, Route, Switch } from 'react-router-dom'
import Login from './pages/login/login'
import Index from './pages/index/index'

import zhCN from 'antd/es/locale/zh_CN';
import moment from 'moment';
import 'moment/locale/zh-cn';

moment.locale('zh-cn');
function App() {
  return (
    <ConfigProvider locale={zhCN}>
      <BrowserRouter>
        <Switch>
          <Route path='/login' component={Login} key="/login"></Route>
          <Route path='/' component={Index} key="/"></Route>
        </Switch>
      </BrowserRouter>
    </ConfigProvider>
  );
}

export default App;
