import React, { useEffect } from 'react';
import './App.css';
import moment from 'moment';
import * as api_fetch from './api'
import convertVacationType from './convertVacationType'

function YourRequests(props) {

  useEffect( () => {
    if (props.currentUser !== undefined) {
      getData();
    }
  }, [props.currentUser]);

  // get requests from server
  const getData = async () => {

    api_fetch.loadYourRequests(props.currentUser).then((data) => {

      props.setUser(data.map(request => {
        const convertedDate = request.date.split("/").join("-");

      return (
        {
          title: props.currentUser.name,
          id: request.id,
          start: moment(convertedDate).format("D.M.YYYY"),
          status: request.status = request.status.toLowerCase(),
          type: convertVacationType(request.type)
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
            {props.user.length > 0 ?
            
            <tbody>
              <tr>
                <th>Name</th>
                <th>Type</th>
                <th>Date</th>    
                <th>Status</th>    
              </tr>
              {props.user.map(user => (
                <tr key={user.id}>
                <td>{user.title}</td>
                <td>{user.type}</td>    
                <td>{user.end ? user.start + " - " + user.end : user.start}</td>
                <td>{user.status}</td>
              </tr>
              ))}
            </tbody>
            :
            <tbody>
              <div>No requests</div>
            </tbody>}
          </table>
        </div>
      </div>
    </div>
  )
}


export default YourRequests;