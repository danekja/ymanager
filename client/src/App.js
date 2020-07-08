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
import { BrowserRouter, Route, Switch } from "react-router-dom";
import * as api_fetch from './api'

function App() {

  useEffect(() => {
    if (window.location.pathname === '/login') return;

    api_fetch.getCurrentProfile().then(currentProfile => {
      setUserName(currentProfile);
    }).catch(reason => {
        alert(reason)
    });
  }, []);

  const [userName, setUserName] = useState([
    { name: 'Golstoj Hyhrenciv' }
  ]);



  return (
    <BrowserRouter>
      <div className="App">
        <Nav userName={userName} />
        <div className="container">
        <Switch>
          <Route path="/" exact component={() => <Home userName={userName} setUserName={setUserName}/>}/>
          <Route path="/setting">
            {userName.role === 'EMPLOYER'
            ?
              <Setting/> 
            :
              <div className="permissionText column">
                <p>You don't have permission to access on this server.</p>
              </div>
            }
          </Route> 
          <Route path="/logout"><LogOut/></Route>
          <Route path="/login"><Login/></Route>
        </Switch>  
        </div>
      </div>
    </BrowserRouter>
  );
}

const Home = (props) => {
  const [userRequest, setUser] = useState([]);

  const [acceptedRequest, setRequest] = useState([]);

  useEffect(() => {
    const dataUserRequest = getDataUserRequest();
    setUser(dataUserRequest);
  }, []);

  // OverviewAdmin state 
  const [employees, setEmployees] = useState([
    {
       name: 'Sadam',
       id: 0,
       sickday: 10,
       holiday: 10,
       takenSickday: 2
    }
 ]);

  function getDataUserRequest() {
    return ([]);
  }
  
  return (
    <div className="container">
      <div className="main-content">
        {props.userName.role === 'EMPLOYER'
        ? 
          <UpcomingRequests userRequest={userRequest} setUser={setUser} acceptedRequest={acceptedRequest} setRequest={setRequest} setEmployees={setEmployees}/>
        : 
          <YourRequests userRequest={userRequest} setUser={setUser} acceptedRequest={acceptedRequest} setRequest={setRequest} userName={props.userName}/>
        }
        <Calendar setUser={setUser} userRequest={userRequest} acceptedRequest={acceptedRequest} setRequest={setRequest} userName={props.userName} setEmployees={setEmployees} setUserName={props.setUserName}/> 
      </div>
      {props.userName.role === 'EMPLOYER'
      ?
        <OverviewAdmin employees={employees} setEmployees={setEmployees} />
      :
        <Overview userName={props.userName} employees={employees}  />
      }
    </div>
  )
};

export default App;

