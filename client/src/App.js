import React, { useEffect, useState } from 'react';
import './App.css';
import Nav from './Nav';
import Overview from './Overview';
import OverviewAdmin from './OverviewAdmin';
import Calendar from './Calendar';
import UpcomingRequests from './UpcomingRequests';
import YourRequests from './YourRequests';
import Setting from './Setting';
import LogOut from './LogOut';
import Login from './Login';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import * as api from './api';

export default function App() {

  useEffect(() => {
    if (window.location.pathname === '/login' || window.location.pathname === '/logout') return;

    api.getCurrentProfile().then(currentProfile => {
      setCurrentUser(currentProfile);
    }).catch(reason => {
      alert(reason);
    });
  }, []);

  const [currentUser, setCurrentUser] = useState();


  return (
    <BrowserRouter>
      <div className="App">
        <Nav currentUser={currentUser} />
        <div className="container">
          <Switch>
            <Route path="/" exact component={() => <Home currentUser={currentUser} setCurrentUser={setCurrentUser}/>}/>
            <Route path="/setting">
              {currentUser !== undefined && currentUser.role === 'EMPLOYER' ? (
                <Setting/>
              ) : (
                <div className="permissionText column">
                  <p>You don&apos;t have permission to access on this server.</p>
                </div>
              )}
            </Route>
            <Route path="/logout"><LogOut/></Route>
            <Route path="/login"><Login/></Route>
          </Switch>
        </div>
      </div>
    </BrowserRouter>
  );
}

function Home(props) {
  const [user, setUser] = useState({});
  const [acceptedRequest, setAcceptedRequest] = useState([]);

  // OverviewAdmin state
  const [employees, setEmployees] = useState([]);

  return (
    <div className="container">
      <div className="main-content">
        {props.currentUser !== undefined && props.currentUser.role === 'EMPLOYER' ? (
          <UpcomingRequests user={user} setUser={setUser} acceptedRequest={acceptedRequest} setAcceptedRequest={setAcceptedRequest} setEmployees={setEmployees}/>
        ) : (
          <YourRequests user={user} setUser={setUser} acceptedRequest={acceptedRequest} setAcceptedRequest={setAcceptedRequest} currentUser={props.currentUser}/>
        )}
        <Calendar setUser={setUser} user={user} acceptedRequest={acceptedRequest} setAcceptedRequest={setAcceptedRequest} currentUser={props.currentUser} setEmployees={setEmployees} setCurrentUser={props.setCurrentUser}/>
      </div>
      {props.currentUser !== undefined && props.currentUser.role === 'EMPLOYER' ? (
        <OverviewAdmin employees={employees} setEmployees={setEmployees} />
      ) : (
        <Overview currentUser={props.currentUser} employees={employees} />
      )}
    </div>
  );
}
