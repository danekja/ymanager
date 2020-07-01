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
// import Komponenta1 from './Komponenta1';
// import Komponenta2 from './Komponenta2';
import { BrowserRouter, Route, Switch } from "react-router-dom";

function App() {

  useEffect( () => {
    getData();
  }, []);

  const getData = async () => {
    try {
    const response = await fetch(
      'http://devcz.yoso.fi:8090/ymanager/users/current/profile', {
        headers: {
          Authorization: 1
        }
      }
    );

    if (response.ok) {
    const data = await response.json();
    setUserName({
      name: data.firstName + ' ' + data.lastName,
      role: data.role,
      id: data.id,
      holiday: data.vacationCount,
      sickday: data.sickDayCount
    })
  } else {
    if(response.status === 400) {
      alert('error 400 GET DATA APP')
   }
      else if (response.status === 500) {
         alert ('error 500 GET DATA APP')
      }
      else {
         alert('error GET DATA APP')
      }
  }
} catch (e) {
  console.log(e)
  alert('error catch GET DATA APP')
  }
}

  const [userName, setUserName] = useState([
    {
    name: 'Golstoj Hyhrenciv'
  }
]);

  return (
    <BrowserRouter>
      <div className="App">
        <Nav userName={userName} />
        <div className="container">
        <Switch>
          <Route path="/" exact component={() => <Home userName={userName}/>}/>
          <Route path="/setting"><Setting/></Route>
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

  function getDataUserRequest() {
    return ([]);
  }

  // const [data, setData] = useState([])
  // const [todo, setTodo] = useState([])

  return (
  <div className="container">
    <div className="main-content">
      {props.userName.role === 'EMPLOYER' ? <UpcomingRequests userRequest={userRequest} setUser={setUser} acceptedRequest={acceptedRequest} setRequest={setRequest} /> : <YourRequests userRequest={userRequest} setUser={setUser} acceptedRequest={acceptedRequest} setRequest={setRequest} userName={props.userName}/>}
      <Calendar setUser={setUser} userRequest={userRequest} acceptedRequest={acceptedRequest} setRequest={setRequest} userName={props.userName}/> 
    </div>
    {props.userName.role === 'EMPLOYER' ? <OverviewAdmin /> : <Overview userName={props.userName} />}
  </div>
  )
};

export default App;

  /* <Komponenta1 todo={todo} setTodo={setTodo}/>
  <Komponenta2 todo={todo} setTodo={setTodo}/> */


 