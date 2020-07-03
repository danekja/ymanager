import React, { useEffect } from 'react';
import './App.css';
import * as api_fetch from './api'

function YourRequests(props) {

  useEffect( () => {
      getData();
    }, []);

  // get requests from server
  const getData = async () => {

    api_fetch.loadYourRequests().then((data) => {

      props.setUser(data.map(request => {
        const a = request.date;
        const b = [a.slice(0, 4), "-", a.slice(5, 7), "-", a.slice(8, 10)].join('');

      return (
        {
          title: props.userName.name,
          id: request.id,
          start: b,
          status: request.status,
          type: request.type
        }
      )
    }))
    }).catch(reason => {
      alert(reason)
    });
}
  

  return (
    <div className="offs-request column">
      <h3>Your Requests</h3>
      <div className="underline-1"></div>
      <div className="offs-items column">
        <div className="offs-item row">
          <table>
            <tbody>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Date</th>    
                <th>Status</th>    
              </tr>
              {props.userRequest.map(user => (
              <tr key={user.id}>
                <td>{user.title}</td>
                <td>{user.type}</td>    
                <td>{user.end ? user.start + " - " + user.end : user.start}</td>
                <td>{user.status}</td>
              </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}


export default YourRequests;